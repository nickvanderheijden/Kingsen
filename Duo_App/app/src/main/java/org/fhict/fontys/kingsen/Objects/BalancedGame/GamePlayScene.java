package org.fhict.fontys.kingsen.Objects.BalancedGame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

/**
 * Created by Nick on 6-4-2018.
 */

public class GamePlayScene implements BalanceScene {
    private Rect r = new Rect();
    private RectPlayer player;
    private Point playerPoint;
    private BalanceObstacleManager obstacleManager;

    private boolean movingPlayer;

    private boolean gameOver = false;
    private long gameOverTime;

    private OrientationData orientationData;
    private long frameTime;

    public GamePlayScene(){
        player = new RectPlayer(new Rect(100, 100, 200, 200), Color.rgb(255, 0, 0));
        playerPoint = new Point(BalanceConstants.SCREEN_WIDTH / 2, 3 * BalanceConstants.SCREEN_WIDTH / 4);
        player.update(playerPoint);

        obstacleManager = new BalanceObstacleManager(200, 350, 75, Color.BLACK);
        orientationData = new OrientationData();
        orientationData.register();
        frameTime = System.currentTimeMillis();
    }
    public void resetGame() {
        playerPoint = new Point(BalanceConstants.SCREEN_WIDTH / 2, 3 * BalanceConstants.SCREEN_WIDTH / 4);
        player.update(playerPoint);
        obstacleManager = new BalanceObstacleManager(200, 350, 75, Color.BLACK);
        movingPlayer = false;

    }
    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);

        player.draw(canvas);
        obstacleManager.draw(canvas);

        if (gameOver) {
            Paint paint = new Paint();
            paint.setTextSize(100);
            paint.setColor(Color.BLUE);
            drawCenterText(canvas, paint, "Game over");

        }
    }

    @Override
    public void update() {
        if (!gameOver) {

            if(frameTime < BalanceConstants.INIT_TIME)
                frameTime = BalanceConstants.INIT_TIME;


            int eplisedTime = (int)(System.currentTimeMillis() -frameTime);
            frameTime = System.currentTimeMillis();
            if(orientationData.getOrientation() !=null && orientationData.getStartOrientation()!= null) {
                float pitch = orientationData.getOrientation()[1] - orientationData.getStartOrientation()[1];
                float roll = orientationData.getOrientation()[2] - orientationData.getStartOrientation()[2];


                float xSpeed = 2 *roll *BalanceConstants.SCREEN_WIDTH/1000f;
                float ySpeed = pitch * BalanceConstants.SCREEN_HEIGHT/1000f;

                playerPoint.x+=Math.abs(xSpeed *eplisedTime)>5? xSpeed *eplisedTime:0;
                playerPoint.y-=Math.abs(ySpeed *eplisedTime)>5? ySpeed *eplisedTime:0;

            }
            if(playerPoint.x <0) {
                playerPoint.x = 0;
            }else if(playerPoint.x > BalanceConstants.SCREEN_WIDTH){
                playerPoint.x = BalanceConstants.SCREEN_WIDTH;
            }

            if(playerPoint.y <0) {
                playerPoint.y = 0;
            }else if(playerPoint.y > BalanceConstants.SCREEN_HEIGHT){
                playerPoint.y = BalanceConstants.SCREEN_HEIGHT;
            }
            player.update(playerPoint);
            obstacleManager.update();
            if (obstacleManager.playerCollide(player)) {
                gameOver = true;
                gameOverTime = System.currentTimeMillis();
            }
        }
    }

    @Override
    public void terminate() {
        SceneManager.ACTIVE_SCENE =0;
    }

    @Override
    public void recieveTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!gameOver && player.getRect().contains((int) event.getX(), (int) event.getY()))
                    movingPlayer = true;
                if (gameOver && System.currentTimeMillis() - gameOverTime >= 2000) {
                    resetGame();
                    gameOver = false;
                    orientationData.newGame();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (!gameOver && movingPlayer)
                    playerPoint.set((int) event.getX(), (int) event.getY());
                break;

            case MotionEvent.ACTION_UP:
                movingPlayer = false;
                break;

        }

    }
    private void drawCenterText(Canvas canvas, Paint paint, String text) {
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y, paint);

    }
}
