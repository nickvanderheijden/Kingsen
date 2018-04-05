package org.fhict.fontys.kingsen.Objects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Point;
import android.support.constraint.solver.widgets.Rectangle;

/**
 * Created by Nick on 5-4-2018.
 */

public class RectPlayer implements BalanceGameObject{

    private Rect rectangle;
    private int color;
    public Rect getRect(){
        return rectangle;
    }


    public RectPlayer(Rect rectangle, int color){
        this.rectangle =rectangle;
        this.color = color;

    }
    @Override
    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle, paint);

    }
    public void update(){

    }
    public void update(Point point){
        rectangle.set(point.x - rectangle.width()/2, point.y - rectangle.height(), point.x + rectangle.width()/2,point.y - rectangle.height()/2);

    }

}
