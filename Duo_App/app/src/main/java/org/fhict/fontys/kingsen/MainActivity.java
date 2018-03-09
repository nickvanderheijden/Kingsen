package org.fhict.fontys.kingsen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    EditText tbusername;
    EditText tbpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.tbusername = findViewById(R.id.tbusername);
        this.tbpassword = findViewById(R.id.tbpassword);
    }

    public void Login(View view)
    {

    }
}
