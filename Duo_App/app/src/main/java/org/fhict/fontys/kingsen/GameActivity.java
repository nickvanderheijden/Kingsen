package org.fhict.fontys.kingsen;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import org.fhict.fontys.kingsen.Objects.AuthenticationReference;
import org.fhict.fontys.kingsen.Objects.Card;
import org.fhict.fontys.kingsen.Objects.DatabaseReference;
import org.fhict.fontys.kingsen.Objects.Group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

   final Context context = this;
   private List<String> cards = new ArrayList<>();
   private ImageView imgcard;
   private TextView tvplayertomove;
   private TextView tvchallenge;
   private Group currentgroup;
   private Integer playertomoveid = 0;
   private HashMap<String,String> rulepercard = new HashMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        tvplayertomove = findViewById(R.id.tvplayertomove);
        tvchallenge = findViewById(R.id.tvchallenge);
        imgcard = findViewById(R.id.imgviewcard);

        //retrieve group and rules
        currentgroup = (Group) getIntent().getSerializableExtra("GROUP");
        retrieveRules(currentgroup);

        //create list of images via name
        for (Card.Number number : Card.Number.values())
        {
            for (Card.Type type : Card.Type.values())
            {
                cards.add(number.toString() + "_of_" + type.toString());
            }
        }

        cards.add("red_joker");
        cards.add("black_joker");

        ShowRandomCard(tvchallenge.getRootView());
    }

    //execute each time button is pressed
    public void ShowRandomCard(View view)
    {
        if (cards.size() > 0) {
            imgcard.setImageDrawable(getRandomcard());
            getCurrentPlayer();
        }
        else{
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);

            alertDialogBuilder.setTitle("Game Finished");
            alertDialogBuilder.setMessage("All cards have been drawn, the game is over");
            alertDialogBuilder.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(context,HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            alertDialogBuilder.show();
        }
    }

    private Drawable getRandomcard()
    {
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(cards.size());
        String card = cards.get(index);
        cards.remove(index);

        if(card.equals("red_joker")){

            Intent miniGame = new Intent(this, GameExplainActivity.class);
            startActivity(miniGame);
        }

        if (card.equals("black_joker")){
            Intent minigame2 = new Intent(this,BalanceGameMainActivity.class);
            startActivity(minigame2);
        }

        String result = card.split("_")[0];
        tvchallenge.setText(rulepercard.get(result.toString()));


        Context context = this.getApplicationContext();
        int resourceId = context.getResources().getIdentifier(card, "drawable", this.getApplicationContext().getPackageName());
        return context.getResources().getDrawable(resourceId);
    }

    private void getCurrentPlayer()
    {
        if (playertomoveid + 1 <= currentgroup.getUsers().size())
        {
            tvplayertomove.setText("It's " + currentgroup.getUsers().get(playertomoveid) + " turn!");
            playertomoveid++;
        }
        else
        {
            tvplayertomove.setText("It's " + currentgroup.getUsers().get(0) + " turn!");
            playertomoveid = 1;
        }
    }

    private void retrieveRules(Group currentgroup)
    {
        String username = AuthenticationReference.getAuth().getCurrentUser().getEmail().replace(".",",");
        DatabaseReference.getDatabase().child("users").child(username).child(currentgroup.getName()).child("rules").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                rulepercard.put(dataSnapshot.getKey(),dataSnapshot.getValue().toString());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
