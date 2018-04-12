package org.fhict.fontys.kingsen.Objects.BalancedGame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

/**
 * Created by Nick on 6-4-2018.
 */

public class BalanceObstacleManager {

    //higher index = lower on screen = higher y value
    private ArrayList<BalanceObstacle> obstacles;
    private int playerGap;
    private int obstacleGap;
    private int obstacleHeight;
    private int color;

    private long startTime;
    private long initTime;

    private int score = 0;

    public BalanceObstacleManager(int playerGap, int obstacleGap, int obstacleHeight, int color) {
        this.playerGap = playerGap;
        this.obstacleGap = obstacleGap;
        this.obstacleHeight = obstacleHeight;
        this.color = color;

        startTime = initTime = System.currentTimeMillis();

        obstacles = new ArrayList<>();

        populateObstacles();
    }

    public boolean playerCollide(RectPlayer player) {
        for(BalanceObstacle ob : obstacles) {
            if(ob.playerCollide(player))
                return true;
        }
        return false;
    }

    private void populateObstacles() {
        int currY = -5*BalanceConstants.SCREEN_HEIGHT/4;
        while(currY < 0) {
            int xStart = (int)(Math.random()*(BalanceConstants.SCREEN_WIDTH - playerGap));
            obstacles.add(new BalanceObstacle(obstacleHeight, color, xStart, currY, playerGap));
            currY += obstacleHeight + obstacleGap;
        }
    }

    public void update() {
        if(startTime < BalanceConstants.INIT_TIME)
            startTime = BalanceConstants.INIT_TIME;
        int elapsedTime = (int)(System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();
        float speed = (float)(Math.sqrt(1 + (startTime - initTime)/2000.0))*BalanceConstants.SCREEN_HEIGHT/(10000.0f);
        for(BalanceObstacle ob : obstacles) {
            ob.incrementY(speed * elapsedTime);
        }
        if(obstacles.get(obstacles.size() - 1).getRectangle().top >= BalanceConstants.SCREEN_HEIGHT) {
            int xStart = (int)(Math.random()*(BalanceConstants.SCREEN_WIDTH - playerGap));
            obstacles.add(0, new BalanceObstacle(obstacleHeight, color, xStart, obstacles.get(0).getRectangle().top - obstacleHeight - obstacleGap, playerGap));
            obstacles.remove(obstacles.size() - 1);
            score++;
        }
    }

    public void draw(Canvas canvas) {
        for(BalanceObstacle ob : obstacles)
            ob.draw(canvas);
        Paint paint = new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.MAGENTA);
        canvas.drawText("" + score, 50, 50 + paint.descent() - paint.ascent(), paint);
    }
}
