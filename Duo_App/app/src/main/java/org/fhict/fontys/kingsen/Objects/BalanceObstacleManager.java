package org.fhict.fontys.kingsen.Objects;

import android.graphics.Canvas;

import java.util.ArrayList;

/**
 * Created by Nick on 6-4-2018.
 */

public class BalanceObstacleManager {

    private ArrayList<BalanceObstacle>obstacles;
    private int playerGap;
    private int obstacleGap;
    private int obstacleHeight;
    private int color;

    private long startTime;

    public BalanceObstacleManager(int playergap , int obstacleGap, int obstacleHeight, int color){
        this.playerGap = playergap;
        this.obstacleGap = obstacleGap;
        this.obstacleHeight = obstacleHeight;
        this.color =color;
        obstacles = new ArrayList<>();

        startTime = System.currentTimeMillis();

        prepareObstacles();

    }
    public void prepareObstacles(){

        int currentY = -5*BalanceConstants.SCREEN_HEIGHT/4;
        while(currentY <0){
            int xStart = (int)Math.random()*(BalanceConstants.SCREEN_WIDTH - playerGap);
        obstacles.add(new BalanceObstacle(obstacleHeight,color ,xStart, currentY, playerGap));
        currentY +=obstacleHeight + obstacleGap;
        }
    }
    public void update(){
        int elaspedTime  = (int)(System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();
        float speed = BalanceConstants.SCREEN_HEIGHT/10000.0f;

        for(BalanceObstacle ob: obstacles){
            ob.incrementY(speed *elaspedTime );
        }
        if(obstacles.get(obstacles.size()-1).getRectangle().top>= BalanceConstants.SCREEN_HEIGHT){
            int xStart = (int)Math.random()*(BalanceConstants.SCREEN_WIDTH - playerGap);
            obstacles.add(0,new BalanceObstacle(obstacleHeight, color, xStart,obstacles.get(0).getRectangle().top - obstacleHeight - obstacleGap, playerGap));
            obstacles.remove(obstacles.size()-1);
        }
    }
    public void draw(Canvas canvas){
        for(BalanceObstacle ob: obstacles){
            ob.draw(canvas);
        }
    }
}
