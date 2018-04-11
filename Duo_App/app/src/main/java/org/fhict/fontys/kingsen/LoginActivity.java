package org.fhict.fontys.kingsen;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import org.fhict.fontys.kingsen.Objects.AuthenticationReference;
import org.fhict.fontys.kingsen.Objects.DatabaseReference;
import org.fhict.fontys.kingsen.Objects.SimpleDialog;

public class LoginActivity extends AppCompatActivity {

    //Controls
    EditText tbusername;
    EditText tbpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //initialize authenticationreference and databasereference
        new  AuthenticationReference();
        new DatabaseReference();

        //assign controls
        this.tbusername = findViewById(R.id.tbusername);
        this.tbpassword = findViewById(R.id.tbpassword);

        //if user had already been logged in, skip loginscreen and go straight to groupscreen
        if (AuthenticationReference.getAuth().getCurrentUser() != null)
        {
            Intent homescreen = new Intent(this,HomeActivity.class);
            startActivity(homescreen);
            finish();
        }
    }

    public void Login(View view) {

        //if fields are empty give dialog
        if (tbusername.getText().toString().equals("") || tbpassword.getText().toString().equals("")) {
            new SimpleDialog(this,"No Credentials","Please fill in both the required fields");
        }
        //if fields are not empty, try to login
        else if (tbusername.getText().toString().isEmpty() == false || tbpassword.getText().toString().isEmpty() == false){
            AuthenticationReference.getAuth().signInWithEmailAndPassword(tbusername.getText().toString(), tbpassword.getText().toString());
            FirebaseUser current = AuthenticationReference.getAuth().getCurrentUser();

            //if login was unnsuccesfully,give message
            if (current == null) {
                new SimpleDialog(this,"Wrong credentials","Please fill in the right credentials");
            } else {
                //return to groupscreen
                Intent homescreen = new Intent(this, HomeActivity.class);
                startActivity(homescreen);
                finish();
            }
        }
    }

    public void Register(View view)
    {
        //if fields are empty give dialog
        if (tbusername.getText().toString().equals("") || tbpassword.getText().toString().equals(""))
        {
            new SimpleDialog(this,"No Credentials","Please fill in both the required fields");
        }
        else if (tbusername.getText().toString().isEmpty() == false || tbpassword.getText().toString().isEmpty() == false){
            //try to register, give error message if it fails
        AuthenticationReference.getAuth().createUserWithEmailAndPassword(tbusername.getText().toString(),tbpassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        String username = tbusername.getText().toString();
                        username.replace(".",",");
                        DatabaseReference.getDatabase().child("users").setValue(username);

                        if(task.isSuccessful()){
                            Toast correctmessage = Toast.makeText(tbusername.getContext(),"Account succesfully created",Toast.LENGTH_SHORT);
                            correctmessage.show();

                        }else{
                            Toast Wrongmessage = Toast.makeText(tbusername.getContext(),"Unable to create account because : " + task.getException(),Toast.LENGTH_SHORT);
                            Wrongmessage.show();
                        }
                    }
                });
        }}
}
