package org.fhict.fontys.kingsen;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import org.fhict.fontys.kingsen.Objects.Card;
import org.fhict.fontys.kingsen.Objects.DatabaseReference;
import org.fhict.fontys.kingsen.Objects.Group;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {


    List<String> cards = new ArrayList<>();
    ImageView imgcard;
    TextView tvplayertomove;
    Group currentgroup;
    Integer playertomoveid = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        tvplayertomove = findViewById(R.id.tvplayertomove);

        currentgroup = (Group) getIntent().getSerializableExtra("GROUP");

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

        getCurrentPlayer();
    }

    public void ShowRandomCard(View view)
    {
        imgcard.setImageDrawable(getRandomcard());
        getCurrentPlayer();
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
}
