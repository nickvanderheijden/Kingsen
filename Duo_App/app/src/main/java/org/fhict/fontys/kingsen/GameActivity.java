package org.fhict.fontys.kingsen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;

import org.fhict.fontys.kingsen.Objects.Card;
import org.fhict.fontys.kingsen.Objects.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    ArrayList<Card> cards = new ArrayList<Card>();


    private void fetchData(DataSnapshot dataSnapshot) {
        cards.clear();

        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            Card card = ds.getValue(Card.class);
            cards.add(card);
        }
    }

    public ArrayList<Card> retrieve() {
        DatabaseReference db;
        return null;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }
}
