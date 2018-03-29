package org.fhict.fontys.kingsen;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import org.fhict.fontys.kingsen.Objects.Card;
import org.fhict.fontys.kingsen.Objects.DatabaseReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {


    List<String> cards = new ArrayList<>();
    ImageView imgcard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        for (Card.Number number : Card.Number.values())
        {
            for (Card.Type type : Card.Type.values())
            {
                cards.add(number.toString() + "_of_" + type.toString());
            }
        }

        cards.add("red_joker");
        cards.add("black_joker");

        imgcard = findViewById(R.id.imgviewcard);


    }

    public void ShowRandomCard(View view)
    {
        imgcard.setImageDrawable(getRandomcard());
    }

    public Drawable getRandomcard()
    {
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(cards.size());
        String card = cards.get(index);
        cards.remove(index);

        if(card.equals("red_joker")){

            Intent miniGame = new Intent(GameActivity.this, MiniGameActivity.class);
        startActivity(miniGame);
;
        }

        Context context = this.getApplicationContext();
        int resourceId = context.getResources().getIdentifier(card, "drawable", this.getApplicationContext().getPackageName());
        return context.getResources().getDrawable(resourceId);

    }
}
