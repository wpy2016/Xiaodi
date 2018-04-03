package com.wpy.cqu.xiaodi.goout;

import android.Manifest;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.wpy.cqu.xiaodi.R;
import com.wpy.cqu.xiaodi.base_activity.CheckPermissionsActivity;
import com.wpy.cqu.xiaodi.base_activity.StatusBarAppComptActivity;
import com.wpy.cqu.xiaodi.base_activity.TopBarAppComptAcitity;

public class AcGoOut extends TopBarAppComptAcitity {


    private static final int STATUS_BAR_COLOR = Color.parseColor("#00dec9");

    private static final String[] PERMISSION = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.READ_PHONE_STATE,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = new Bundle();
        bundle.putInt(StatusBarAppComptActivity.STATUS_COLOR_STR,STATUS_BAR_COLOR);
        bundle.putStringArray(CheckPermissionsActivity.PEMISSION,PERMISSION);
        super.onCreate(bundle);
        setContentView(R.layout.ac_go_out);
        bindView();
        initView();
        bindEvent();
    }

    private void bindView() {
        mivBack = (ImageView) findViewById(R.id.id_top_back_iv_img);
        mtvBack = (TextView) findViewById(R.id.id_top_back_tv);
        mtvContent = (TextView) findViewById(R.id.id_top_tv_content);

    }

    private void initView() {
        mivBack.setImageResource(R.drawable.go_back_white);
        mtvBack.setText(getResources().getString(R.string.hall));
        mtvBack.setTextColor(Color.WHITE);
        mtvContent.setText(getResources().getString(R.string.send_travel));
    }

    private void bindEvent() {
        mivBack.setOnClickListener(v->finish());
        mtvBack.setOnClickListener(v->finish());
    }
}
