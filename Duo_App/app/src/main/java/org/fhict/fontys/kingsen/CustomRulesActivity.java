package org.fhict.fontys.kingsen;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
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
import org.fhict.fontys.kingsen.Objects.SimpleDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomRulesActivity extends AppCompatActivity {

    private String groupname;
    private String username;
    private List<String> cardstoshow;

    //Controls
    private ImageView imgcardforrule;
    private TextView tvcardnumbeexample;
    private EditText etrule;
    private Button btnnextcard;

    //currentcardnumber
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_rules);

        //get group values
        groupname = getIntent().getStringExtra("GROUPNAME");
        username =  getIntent().getStringExtra("USERNAME");

        //find controls
        imgcardforrule = findViewById(R.id.imgcardforrule);
        tvcardnumbeexample = findViewById(R.id.tvcardnumbeexample);
        etrule = findViewById(R.id.etrule);
        btnnextcard = findViewById(R.id.btnnextcard);

        //fill list
        cardstoshow = new ArrayList<>(Arrays.asList("ace_of_clubs", "two_of_clubs","three_of_clubs","four_of_clubs","five_of_clubs","six_of_clubs","seven_of_clubs","eight_of_clubs",
                "nine_of_clubs","ten_of_clubs","jack_of_clubs","queen_of_clubs","king_of_clubs"));

        setTitle(groupname + " custom rules");

    }

    public void SaveAndNext(View view) {
        //check if edittext is empty
        if (etrule.getText().toString() == "") {
            new SimpleDialog(view.getContext(),"Warning","Please fill in a rule");
        } else {
            //are all cards filled in?
            if (index < 12) {
                //save current rule
                DatabaseReference.getDatabase().child("users").child(username).child(groupname).child("rules").child(tvcardnumbeexample.getText().toString()).setValue(etrule.getText().toString());

                //prepare next card
                imgcardforrule.setImageDrawable(getRandomcard());
                etrule.setText("");
            }
            //save last card and give user a message > go back to mainscreen
            else {
                DatabaseReference.getDatabase().child("users").child(username).child(groupname).child("rules").child(tvcardnumbeexample.getText().toString()).setValue(etrule.getText().toString());
                Toast t = Toast.makeText(this, "The rules have been saved", Toast.LENGTH_SHORT);
                t.show();
                finish();
            }
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

        //updatebutton (if last card is shown)
        if (index == 12)
        {
            btnnextcard.setText("finish");
        }

        return context.getResources().getDrawable(resourceId);
    }

    public  void Cancel(View view){
        //build dialog for warning
        final AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(this);
        dialogbuilder.setTitle("Warning");
        dialogbuilder.setMessage("If you leave now, the game will not be saved." + "\n" + "\n" + "Are you sure?");
        dialogbuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //remove game from firebase and return to groupscreen
                DatabaseReference.getDatabase().child("users").child(username).child(groupname).removeValue();
                dialogInterface.dismiss();
                finish();
            }
        });
        dialogbuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //close dialog
                dialogInterface.dismiss();
            }
        });

        dialogbuilder.show();
    }
}
