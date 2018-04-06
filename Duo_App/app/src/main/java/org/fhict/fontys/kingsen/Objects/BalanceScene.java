package org.fhict.fontys.kingsen.Objects;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by Nick on 6-4-2018.
 */

public interface BalanceScene {

    public void update();
    public  void draw(Canvas canvas);
    public void terminate();
    public void recieveTouch(MotionEvent event);

}
