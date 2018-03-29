package org.fhict.fontys.kingsen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class GameExplainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_explain);
    }

    public void  startGame(View view){
        startActivity(new Intent(getApplicationContext(),MiniGameActivity.class));
    }
}
