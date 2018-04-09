package com.wpy.cqu.xiaodi.clip;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.wpy.cqu.xiaodi.R;
import com.wpy.cqu.xiaodi.application.XiaodiApplication;
import com.wpy.cqu.xiaodi.base_activity.CheckPermissionsActivity;
import com.wpy.cqu.xiaodi.base_activity.StatusBarAppComptActivity;
import com.wpy.cqu.xiaodi.base_activity.TopBarAppComptAcitity;
import com.wpy.cqu.xiaodi.clip.clip_image.ClipImageLayout;
import com.wpy.cqu.xiaodi.clip.clip_image.ImageTools;
import com.wpy.cqu.xiaodi.util.ToastUtil;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AcClipImg extends TopBarAppComptAcitity {

    private static final int STATUS_BAR_COLOR = Color.parseColor("#00dec9");

    private ClipImageLayout mClipImageLayout;

    private ProgressDialog mProgressDialog;

    private static final String[] PERMISSION = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.READ_PHONE_STATE,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = new Bundle();
        bundle.putInt(StatusBarAppComptActivity.STATUS_COLOR_STR, STATUS_BAR_COLOR);
        bundle.putStringArray(CheckPermissionsActivity.PEMISSION, PERMISSION);
        super.onCreate(bundle);
        setContentView(R.layout.ac_clip_img);

        bindView();
        initView();
        bindEvent();
        loadImg();
    }

    private void saveImg() {
        mProgressDialog.show();
        Observable.just("")
                .map(s -> {
                    Bitmap clipBitmap = mClipImageLayout.clip();
                    File file = new File(XiaodiApplication.IMG_SAVE_PATH);
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    Bitmap compressBitmap = ImageTools.compressBitmap(clipBitmap);
                    String savePath = XiaodiApplication.IMG_SAVE_PATH + "/" + System.currentTimeMillis() + ".png";
                    ImageTools.saveBitmapToSDCard(compressBitmap, savePath);
                    return savePath;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(savePath -> {
                    mProgressDialog.dismiss();
                    Intent intent = new Intent();
                    intent.putExtra("path", savePath);
                    setResult(RESULT_OK, intent);
                    finish();
                });
    }

    private void loadImg() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("提示");
        mProgressDialog.setMessage("正在读取图片，请稍后...");
        String path;
        path = getIntent().getStringExtra("path");
        if (path != null)
            if (TextUtils.isEmpty(path) || !(new File(path).exists())) {
                ToastUtil.toast(this, getResources().getString(R.string.photo_load_fail));
                return;
            }
        Bitmap bitmap = ImageTools.convertToBitmap(path, 800, 1000);
        if (bitmap == null) {
            ToastUtil.toast(this,getResources().getString(R.string.photo_load_fail));
            return;
        }
        mClipImageLayout.setBitmap(bitmap);
    }

    private void bindView() {
        mivBack = (ImageView) findViewById(R.id.id_top_back_iv_img);
        mtvBack = (TextView) findViewById(R.id.id_top_back_tv);
        mtvContent = (TextView) findViewById(R.id.id_top_tv_content);
        mrlTop = (RelativeLayout) findViewById(R.id.id_top_rl);
        mivRight = (ImageView) findViewById(R.id.id_top_right_iv_img);
        mClipImageLayout = (ClipImageLayout) findViewById(R.id.id_ac_clipimage_clipimagelayout);
    }

    private void initView() {
        mivBack.setImageResource(R.drawable.go_back_white);
        mtvBack.setText(getResources().getString(R.string.register));
        mtvBack.setTextColor(Color.WHITE);
        mtvContent.setText(getResources().getString(R.string.clip));
        mClipImageLayout.setBackgroundResource(R.drawable.register_background);
        mivRight.setImageResource(R.drawable.right);
        mivRight.setVisibility(View.VISIBLE);
    }

    private void bindEvent() {
        mivBack.setOnClickListener(v -> finish());
        mtvBack.setOnClickListener(v -> finish());
        mivRight.setOnClickListener(v->{
            try {
                saveImg();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
