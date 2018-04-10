package org.fhict.fontys.kingsen;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.fhict.fontys.kingsen.Objects.DatabaseReference;
import org.fhict.fontys.kingsen.Objects.Group;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomRulesActivity extends AppCompatActivity {

    private String groupname;
    private String username;

    private List<String> cardstoshow;
    private ImageView imgcardforrule;
    private TextView tvcardnumbeexample;
    private EditText etrule;
    private Button btnnextcard;

    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_rules);

        groupname = getIntent().getStringExtra("GROUPNAME");
        username =  getIntent().getStringExtra("USERNAME");

        imgcardforrule = findViewById(R.id.imgcardforrule);
        tvcardnumbeexample = findViewById(R.id.tvcardnumbeexample);
        etrule = findViewById(R.id.etrule);
        btnnextcard = findViewById(R.id.btnnextcard);

        cardstoshow = new ArrayList<>(Arrays.asList("ace_of_clubs", "two_of_clubs","three_of_clubs","four_of_clubs","five_of_clubs","six_of_clubs","seven_of_clubs","eight_of_clubs",
                "nine_of_clubs","ten_of_clubs","jack_of_clubs","queen_of_clubs","king_of_clubs"));

    }

    public void SaveAndNext(View view)
    {
        System.out.println(index);

        if (index < 12) {
            //save current rule
            DatabaseReference.getDatabase().child("users").child(username).child(groupname).child("rules").child(tvcardnumbeexample.getText().toString()).setValue(etrule.getText().toString());

            //prepare next card
            imgcardforrule.setImageDrawable(getRandomcard());
            etrule.setText("");
        }
        else
        {
            DatabaseReference.getDatabase().child("users").child(username).child(groupname).child("rules").child(tvcardnumbeexample.getText().toString()).setValue(etrule.getText().toString());
            Toast t = Toast.makeText(this,"The rules have been saved", Toast.LENGTH_SHORT);
            t.show();
            finish();
        }
    }

    private Drawable getRandomcard()
    {
        index++;

        //get cardview
        Context context = this.getApplicationContext();
        int resourceId = context.getResources().getIdentifier(cardstoshow.get(index), "drawable", this.getApplicationContext().getPackageName());


        //update cardtext
        String result = cardstoshow.get(index).split("_")[0];
        tvcardnumbeexample.setText(result);

        //updatebutton
        if (index == 12)
        {
            btnnextcard.setText("finish");
        }

        return context.getResources().getDrawable(resourceId);
    }
}
