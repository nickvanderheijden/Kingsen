package org.fhict.fontys.kingsen.Objects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Nick on 5-4-2018.
 */

public class BalanceObstacle implements BalanceGameObject{

    private Rect rectangle;
    private int color;

    public BalanceObstacle(Rect rectangle , int color){
        this.rectangle = rectangle;
        this.color = color;
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

    }
    @Override
    public void update(){

    }
}
