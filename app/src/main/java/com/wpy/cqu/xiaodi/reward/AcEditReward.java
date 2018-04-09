package com.wpy.cqu.xiaodi.reward;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.wpy.cqu.xiaodi.R;
import com.wpy.cqu.xiaodi.application.XiaodiApplication;
import com.wpy.cqu.xiaodi.clip.clip_image.ImageTools;
import com.wpy.cqu.xiaodi.listener.DoubleClickDoListener;
import com.wpy.cqu.xiaodi.model.ResultResp;
import com.wpy.cqu.xiaodi.model.Reward;
import com.wpy.cqu.xiaodi.model.Thing;
import com.wpy.cqu.xiaodi.net.RewardRequst;
import com.wpy.cqu.xiaodi.net.resp.IResp;
import com.wpy.cqu.xiaodi.util.BitmapUtil;
import com.wpy.cqu.xiaodi.util.ToastUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AcEditReward extends AcReward {

    private Reward reward;

    private ImageView mivDelete;

    private boolean isImgDownload = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newInitData();
        newInitView();
        newBindEvent();
    }

    private void newInitData() {
        reward = (Reward) getIntent().getSerializableExtra("reward");
        if (null == reward) {
            finish();
        }
        miThingType = reward.thing.type;
        Map<String, Integer> weightMap = new HashMap<>();
        weightMap.put("轻", 0);
        weightMap.put("中", 1);
        weightMap.put("重", 2);
        miWeight = weightMap.get(reward.thing.weight);
        download(reward.thing.thumbnail,reward.thing.type);
    }

    private void updateReward(View view) {
        if (!isImgDownload) {
            ToastUtil.toast(this,getResources().getString(R.string.please_wait));
            return;
        }
        Reward newReward = createReward();
        newReward.id = reward.id;
        RewardRequst.UpdateReward(newReward, XiaodiApplication.mCurrentUser.Id, XiaodiApplication.mCurrentUser.Token, new IResp<ResultResp>() {
            @Override
            public void success(ResultResp object) {
                ToastUtil.toast(AcEditReward.this, getResources().getString(R.string.edit_reward_success));
                finish();
            }

            @Override
            public void fail(ResultResp resp) {
                ToastUtil.toast(AcEditReward.this, resp.message);
            }
        });
    }

    private void deleteReward(View view) {
        RewardRequst.DeleteRewards(reward.id, XiaodiApplication.mCurrentUser.Id, XiaodiApplication.mCurrentUser.Token, new IResp<ResultResp>() {
            @Override
            public void success(ResultResp object) {
                ToastUtil.toast(AcEditReward.this, getResources().getString(R.string.delete_success));
                finish();
            }

            @Override
            public void fail(ResultResp resp) {
                ToastUtil.toast(AcEditReward.this, resp.message);
            }
        });
    }

    private void newInitView() {
        mivDelete = (ImageView) findViewById(R.id.id_top_right_iv_img);
        mivDelete.setVisibility(View.VISIBLE);
        mivDelete.setImageResource(R.drawable.delete);
        mbtnSend.setText(getResources().getString(R.string.update));
        mtvBack.setText(getResources().getString(R.string.carry_recode));
        mtvContent.setText(getResources().getString(R.string.edit_reward));

        ImageView[] imageViews = {mivExpress, mivFood, mivPaper, mivOther};
        int typePos = reward.thing.type;
        Picasso.with(this).load(reward.thing.thumbnail)
                .error(Thing.DEFAULT_TYPE_IMG[typePos])
                .into(imageViews[typePos]);
        imageViews[typePos].setBackgroundResource(R.drawable.circle_stroke_green_60);

        Map<String, Integer> weightMapCheck = new HashMap<>();
        weightMapCheck.put("轻", R.id.id_ac_send_reward_rb_light);
        weightMapCheck.put("中", R.id.id_ac_send_reward_rb_medium);
        weightMapCheck.put("重", R.id.id_ac_send_reward_rb_heavy);
        mrgWeight.check(weightMapCheck.get(reward.thing.weight));

        mtvStartPlace.setText(reward.originLocation);
        mtvDstPlace.setText(reward.dstLocation);
        metXiaodian.setText(reward.xiaodian + "");
        metphone.setText(reward.phone);
        mtvDeadLine.setText(reward.deadline);
        metDescribe.setText(reward.describe);
    }

    private void newBindEvent() {
        mbtnSend.setOnClickListener(this::updateReward);
        mivDelete.setOnClickListener(new DoubleClickDoListener(this,this::deleteReward,getResources().getString(R.string.double_click_to_delete)));
    }

    private void download(String thumbnail, int thingtype) {
        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Observable.just("")
                        .doOnNext(s -> save(bitmap))
                        .subscribeOn(Schedulers.io())
                        .subscribe(s -> isImgDownload = true);
            }

            private void save(Bitmap bitmap) {
                File file = new File(XiaodiApplication.IMG_SAVE_PATH);
                if (!file.exists()) {
                    file.mkdir();
                }
                AcEditReward.this.mThumbnail = XiaodiApplication.IMG_SAVE_PATH + "/" + System.currentTimeMillis() + ".png";
                ImageTools.saveBitmapToSDCard(bitmap, mThumbnail);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                isImgDownload = true;
                AcEditReward.this.mThumbnail = "";
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        };
        Picasso.with(this).load(thumbnail).error(Thing.DEFAULT_TYPE_IMG[thingtype]).into(target);
    }
}
