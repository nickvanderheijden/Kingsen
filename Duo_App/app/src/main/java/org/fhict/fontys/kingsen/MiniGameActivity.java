package org.fhict.fontys.kingsen;

import android.content.Intent;
import android.graphics.Point;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class MiniGameActivity extends AppCompatActivity {

    private TextView scoreLabel;
    private TextView startLabel;
    private ImageView mouth;
    private ImageView yellow;
    private ImageView black;
    private ImageView red;
    private ImageView green;

    private int frameHeight;
    private int mouthSize;
    private int screenWidth;
    private int screenHeight;


    private int mouthY;
    private int yellowX;
    private int yellowY;
    private int blackX;
    private int blackY;
    private int greenX;
    private int greenY;
    private int redX;
    private int redY;

    private int score = 0;


    private Handler handler = new Handler();
    private int secondsPast = 0;
    private Timer timer = new Timer();



    private boolean action_flag = false;
    private boolean start_flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_game);

        scoreLabel =  findViewById(R.id.scoreLabel);
        startLabel =  findViewById(R.id.startLabel);
        mouth = findViewById(R.id.mouth);
        yellow = findViewById(R.id.yellow);
        black = findViewById(R.id.black);
        green = findViewById(R.id.green);
        red = findViewById(R.id.red);

        WindowManager wm = getWindowManager();
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        screenWidth = size.x;
        screenHeight = size.y;

        yellow.setX(-80);
        yellow.setY(-80);
        black.setX(-80);
        black.setY(-80);
        green.setX(-80);
        green.setY(-80);
        red.setX(-80);
        red.setY(-80);

        scoreLabel.setText("Score:"+ score);




    }
    public void  changePos() {

        drinkCheck();

        //Yellow
        yellowX -= 12;
        if(yellowX <0){
            yellowX = screenWidth + 20;
            yellowY = (int)Math.floor(Math.random()*(frameHeight - yellow.getHeight()));
        }
        yellow.setX(yellowX);
        yellow.setY(yellowY);

        //black
        blackX -=16;
        if(blackX<0){
            blackX = screenWidth +10;
            blackY = (int)Math.floor(Math.random()*(frameHeight - black.getHeight()));
        }
        black.setX(blackX);
        black.setY(blackY);

        // green
        greenX -=40;
        if(greenX < 0){
            greenX = screenWidth + 30;
            greenY = (int)Math.floor(Math.random()*(frameHeight - green.getHeight()));
        }
        green.setX(greenX);
        green.setY(greenY);

        // red
        redX -= 25;
        if(redX < 0){
            redX = screenWidth + 40;
            redY = (int)Math.floor(Math.random()*(frameHeight - red.getHeight()));

        }
        red.setX(redX);
        red.setY(redY);

        if (action_flag == true) {
            mouthY -= 20;
        } else {
          mouthY +=20;
        }
        if(mouthY <0){
            mouthY = 0;
        }
        if(mouthY > frameHeight -mouthSize){
            mouthY = frameHeight - mouthSize;
        }
        mouth.setY(mouthY);
        scoreLabel.setText("Score:"+ score);
    }


    public void drinkCheck(){


        int yellowCenterX = yellowX + yellow.getWidth() /2;
        int yellowCenterY = yellowY + yellow.getHeight() /2 ;
        if(0 <= yellowCenterX && yellowCenterX <= mouthSize&&
            mouthY <= yellowCenterY && yellowCenterY <= mouthY +mouthSize){

            score +=10;
            yellowX = -10;
        }

        int blackCenterX = blackX + black.getWidth() /2;
        int blackCenterY = blackY + black.getHeight() /2 ;

        if(0 <= blackCenterX && blackCenterX <= mouthSize&&
                mouthY <= blackCenterY && blackCenterY <= mouthY +mouthSize){

            score +=5;
            blackX = -10;
        }

        int greenCenterX = greenX + green.getWidth() /2;
        int greenCenterY = greenY + green.getHeight() /2 ;

        if(0 <= greenCenterX && greenCenterX <= mouthSize&&
                mouthY <= greenCenterY && greenCenterY <= mouthY +mouthSize){

            score +=15;
            greenX = -10;
        }

        int redCenterX = redX + red.getWidth() /2;
        int redCenterY = redY + red.getHeight() /2 ;

        if(0 <= redCenterX && redCenterX <= mouthSize&&
                mouthY <= redCenterY && redCenterY <= mouthY +mouthSize){

                timer.cancel();
                timer = null;

                // show result
                Intent intent = new Intent(getApplicationContext(), MiniGameResultActivity.class);
                intent.putExtra("SCORE", score);
                startActivity(intent);
                finish();






        }

    }
    public boolean onTouchEvent(MotionEvent me){

        if(start_flag == false){
            start_flag = true;

            FrameLayout frame= (FrameLayout)findViewById(R.id.frame);
            frameHeight =frame.getHeight();

            mouthY = (int)mouth.getY();

            mouthSize = mouth.getHeight();



            startLabel.setVisibility(View.GONE);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            changePos();


                        }
                    });
                }
            },0,20);


        }else {


            if (me.getAction() == MotionEvent.ACTION_DOWN) {
                action_flag = true;
            } else if (me.getAction() == MotionEvent.ACTION_UP) {
                action_flag = false;
            }
        }

        return true;
    }

}
