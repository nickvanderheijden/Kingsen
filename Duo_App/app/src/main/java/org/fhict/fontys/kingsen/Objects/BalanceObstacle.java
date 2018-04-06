package org.fhict.fontys.kingsen.Objects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Nick on 5-4-2018.
 */

public class BalanceObstacle implements BalanceGameObject{

    private Rect rectangle;
    private Rect rectangle2;
    private int color;


    public Rect getRectangle(){
        return rectangle;
    }
    public void incrementY(float y){
        rectangle.top += y;
        rectangle.bottom += y;

        rectangle2.top += y;
        rectangle2.bottom += y;
    }
    public BalanceObstacle(int rectHeight , int color, int startX, int startY, int playerGap){

        this.color = color;
        rectangle = new Rect(0, startY, startX, startY + rectHeight);
        rectangle2 = new Rect(startX + playerGap, startY,BalanceConstants.SCREEN_WIDTH,startY+rectHeight);


    }
    public boolean playerCollide(RectPlayer player){
        if(rectangle.contains(player.getRect().left,player.getRect().top)
                ||rectangle.contains(player.getRect().right,player.getRect().top)
                ||rectangle.contains(player.getRect().left,player.getRect().bottom)
                ||rectangle.contains(player.getRect().right,player.getRect().bottom))
            return true;
        return false;
    }
    @Override
    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle,paint);
        canvas.drawRect(rectangle2, paint);

    }
    @Override
    public void update(){

    }
}
