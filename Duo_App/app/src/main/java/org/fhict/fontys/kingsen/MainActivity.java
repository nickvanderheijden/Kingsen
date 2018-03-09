package org.fhict.fontys.kingsen;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.fhict.fontys.kingsen.Objects.AuthenticationReference;

public class MainActivity extends AppCompatActivity {


    EditText tbusername;
    EditText tbpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new  AuthenticationReference();

        this.tbusername = findViewById(R.id.tbusername);
        this.tbpassword = findViewById(R.id.tbpassword);
    }

    public void Login(View view)
    {
        AuthenticationReference.getAuth().signInWithEmailAndPassword(tbusername.getText().toString(),tbpassword.getText().toString());
        FirebaseUser current = AuthenticationReference.getAuth().getCurrentUser();

        if (current == null)
        {
            System.out.println("yep");
        }
        else
        {
            System.out.println("nope");
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
