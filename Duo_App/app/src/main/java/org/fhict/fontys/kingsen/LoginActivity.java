package org.fhict.fontys.kingsen;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import org.fhict.fontys.kingsen.Objects.AuthenticationReference;

public class LoginActivity extends AppCompatActivity {


    EditText tbusername;
    EditText tbpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        new  AuthenticationReference();

        this.tbusername = findViewById(R.id.tbusername);
        this.tbpassword = findViewById(R.id.tbpassword);

        if (AuthenticationReference.getAuth().getCurrentUser() != null)
        {
            Intent homescreen = new Intent(this,HomeActivity.class);
            startActivity(homescreen);
            finish();
        }
    }

    public void Login(View view)
    {
        AuthenticationReference.getAuth().signInWithEmailAndPassword(tbusername.getText().toString(),tbpassword.getText().toString());
        FirebaseUser current = AuthenticationReference.getAuth().getCurrentUser();

        if (current == null)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Wrong credentials");
            builder.setMessage("Please fill in the right credentials");
            builder.setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                  dialog.cancel();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else
        {
            Intent homescreen = new Intent(this,HomeActivity.class);
            startActivity(homescreen);
            finish();
        }
    }

    public void Register(View view)
    {
        AuthenticationReference.getAuth().createUserWithEmailAndPassword(tbusername.getText().toString(),tbpassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast correctmessage = Toast.makeText(tbusername.getContext(),"Account succesfully created",Toast.LENGTH_SHORT);
                            correctmessage.show();

                        }else{
                            Toast Wrongmessage = Toast.makeText(tbusername.getContext(),"Unable to create account because : " + task.getException(),Toast.LENGTH_SHORT);
                            Wrongmessage.show();
                        }
                    }
                });
    }
}
