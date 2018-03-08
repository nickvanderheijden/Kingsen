package org.fhict.fontys.kingsen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.FirebaseDatabase;

import org.fhict.fontys.kingsen.Objects.DatabaseReference;
import org.fhict.fontys.kingsen.Objects.User;

public class MainActivity extends AppCompatActivity {

    EditText tbusername;
    EditText tbpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new DatabaseReference();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.tbusername = findViewById(R.id.tbusername);
        this.tbpassword = findViewById(R.id.tbpassword);
    }

    public void Login(View view)
    {
        User tologin = new User(tbusername.getText().toString(),tbpassword.getText().toString());
        DatabaseReference.getDatabase().child("users").setValue(tologin);
    }
}
