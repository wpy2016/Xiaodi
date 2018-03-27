package com.wpy.cqu.xiaodi.welcome;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.orhanobut.logger.Logger;
import com.wpy.cqu.xiaodi.R;
import com.wpy.cqu.xiaodi.base_activity.StatusBarAppComptActivity;

public class AcWelcome extends StatusBarAppComptActivity {

    private static final int STATUE_BAR_COLOR = Color.parseColor("#00ff00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = new Bundle();
        bundle.putInt(StatusBarAppComptActivity.STATUS_COLOR_STR, STATUE_BAR_COLOR);
        super.onCreate(bundle);

        Logger.i("AcWelcome.onCreate.color is %d",STATUE_BAR_COLOR);
        setContentView(R.layout.ac_welcome);
    }



}
