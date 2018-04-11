package org.fhict.fontys.kingsen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import org.fhict.fontys.kingsen.Objects.BalancedGame.BalanceConstants;
import org.fhict.fontys.kingsen.Objects.BalancedGame.BalanceGamePanel;

public class BalanceGameMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        BalanceConstants.SCREEN_WIDTH = dm.widthPixels;
        BalanceConstants.SCREEN_HEIGHT = dm.heightPixels;

        setContentView(new BalanceGamePanel(this));
    }
}
