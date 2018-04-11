package org.fhict.fontys.kingsen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MiniGameResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_game_result);

        //assign controls
        TextView scoreLabel =  findViewById(R.id.scoreLabel);
        TextView highScoreLabel =  findViewById(R.id.highScoreLabel);

        //get and view score from game
        int score = getIntent().getIntExtra("SCORE",0);
        scoreLabel.setText(score + "");

        //get highscore from storage
        SharedPreferences settings = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        int highScore = settings.getInt("HIGH_SCORE",0);

        //if you have highscore, save it in storage
        if(score > highScore){
            highScoreLabel.setText("High Score:"+ score);


            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("HIGH_SCORE", score);
            editor.commit();
            
        }else{
            highScoreLabel.setText("High Score " + highScore);
        }
    }

    public void backToTheGame(View view){
        finish();
    }
}
