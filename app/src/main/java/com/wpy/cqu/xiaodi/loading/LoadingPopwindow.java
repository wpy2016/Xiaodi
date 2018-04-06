package com.wpy.cqu.xiaodi.loading;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.ldoublem.loadingviewlib.view.LVPlayBall;
import com.wpy.cqu.xiaodi.R;

public class LoadingPopwindow extends PopupWindow {

    public LoadingPopwindow(View view,AppCompatActivity activity) {
        super(view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT, true);
        LVPlayBall lvPlayBall = view.findViewById(R.id.loading);
        lvPlayBall.setBallColor(Color.parseColor("#369e94"));
        lvPlayBall.setViewColor(activity.getResources().getColor(R.color.darkred));
        lvPlayBall.startAnim();
    }

}
