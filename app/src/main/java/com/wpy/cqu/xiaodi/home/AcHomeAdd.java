package com.wpy.cqu.xiaodi.home;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wpy.cqu.xiaodi.R;
import com.wpy.cqu.xiaodi.base_activity.StatusBarAppComptActivity;
import com.wpy.cqu.xiaodi.goout.AcGoOut;
import com.wpy.cqu.xiaodi.reward.AcReward;

public class AcHomeAdd extends StatusBarAppComptActivity {

    private static int STATUS_BAR_COLOR = Color.parseColor("#369e94");

    private ImageView mivClose;

    private LinearLayout mllSendReward;

    private LinearLayout mllSendGoOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = new Bundle();
        bundle.putInt(StatusBarAppComptActivity.STATUS_COLOR_STR,STATUS_BAR_COLOR);
        super.onCreate(bundle);
        setContentView(R.layout.ac_home_add);
        bindView();
        bindEvent();
    }

    private void bindView() {
        mivClose = (ImageView) findViewById(R.id.id_ac_add_iv_close);
        mllSendGoOut = (LinearLayout) findViewById(R.id.id_ac_add_ll_send_travel);
        mllSendReward = (LinearLayout) findViewById(R.id.id_ac_add_ll_send_reward);
    }

    private void bindEvent() {
        mivClose.setOnClickListener(v->finish());
        mllSendReward.setOnClickListener(v->toNext(AcReward.class));
        mllSendGoOut.setOnClickListener(v->toNext(AcGoOut.class));
    }

    private void toNext(Class<?> next) {
        Intent intent = new Intent(this,next);
        startActivity(intent);
        finish();
    }
}
