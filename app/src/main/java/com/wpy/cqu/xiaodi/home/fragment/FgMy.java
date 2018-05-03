package com.wpy.cqu.xiaodi.home.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wpy.cqu.xiaodi.R;
import com.wpy.cqu.xiaodi.application.XiaodiApplication;
import com.wpy.cqu.xiaodi.auth.AcAuth;
import com.wpy.cqu.xiaodi.base_activity.ClipBaseFragment;
import com.wpy.cqu.xiaodi.model.ResultResp;
import com.wpy.cqu.xiaodi.model.User;
import com.wpy.cqu.xiaodi.net.UserRequest;
import com.wpy.cqu.xiaodi.net.resp.IResp;
import com.wpy.cqu.xiaodi.reward.AcCarryRecord;
import com.wpy.cqu.xiaodi.setting.AcSetting;
import com.wpy.cqu.xiaodi.sign.AcSign;
import com.wpy.cqu.xiaodi.util.ToastUtil;
import com.wpy.cqu.xiaodi.wallet.AcWallet;

import java.io.File;

public class FgMy extends ClipBaseFragment {

    private TextView mtvContent;

    private ImageView mivImg;

    private ImageView mivSetImg;

    private TextView mtvNickName;

    private TextView mtvRealName;

    private RelativeLayout mrlSignature;

    private RelativeLayout mrlMoney;

    private TextView mtvMoney;

    private RelativeLayout mrlCarryRecord;

    private RelativeLayout mrlAttestation;

    private TextView mtvAttestationStatus;

    private RelativeLayout mrlSetting;

    private User user;

    private PopupWindow mPopWindowModifyNickName;

    public FgMy() {
    }

    public static FgMy newInstance() {
        FgMy fragment = new FgMy();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_my, null);
        bindView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bindEvent();
    }

    @Override
    public void onResume() {
        super.onResume();
        user = XiaodiApplication.mCurrentUser;
        initView();
    }

    private void initView() {
        mivImg.setImageResource(R.drawable.default_headimg);
        Picasso.with(getActivity())
                .load(user.ImgUrl)
                .placeholder(R.drawable.default_headimg)
                .error(R.drawable.default_headimg)
                .into(mivImg);

        mtvContent.setText(getResources().getString(R.string.my));
        mtvNickName.setText(user.getNickName());
        float money = user.getGoldMoney() + user.getSilverMoney();
        mtvMoney.setText("" + money);
        if (user.getUserType() == User.NORMAL) {
            mtvAttestationStatus.setText(getResources().getString(R.string.xiaodi_attestation_not));
            mtvRealName.setText(getResources().getString(R.string.no_real_name_identificate));
            return;
        }
        mtvAttestationStatus.setText(getResources().getString(R.string.xiaodi_attestation));
        mtvRealName.setText(user.getRealName());
    }

    @Override
    public void setImg(Bitmap img, String path) {
        UserRequest.UpdateImg(XiaodiApplication.mCurrentUser.Id,
                XiaodiApplication.mCurrentUser.Token, path, new IResp<ResultResp>() {
                    @Override
                    public void success(ResultResp object) {
                        mivImg.setImageBitmap(img);
                        ToastUtil.toast(getActivity(), getResources().getString(R.string.update_img_success));
                        //立即更新url
                        UserRequest.updateUserInfo();
                    }

                    @Override
                    public void fail(ResultResp resp) {
                        ToastUtil.toast(getActivity(), resp.message);
                    }
                });
    }

    private void setNickName(View view) {
        if (mPopWindowModifyNickName == null) {
            View mView = LayoutInflater.from(getActivity()).inflate(R.layout.pop_modifyname, null);
            mPopWindowModifyNickName = new PopupWindow(mView, RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT, true);
            initModifyPopupWindow(mView);
        }
        mPopWindowModifyNickName.showAtLocation(mtvNickName, Gravity.CENTER, 0, 0);
    }

    private void initModifyPopupWindow(View view) {
        EditText etNewNickName = view.findViewById(R.id.id_pop_modifyname_et_nickname);
        Button btnConfirm = view.findViewById(R.id.id_pop_modifyname_btn_confirm);
        Button btnCancel = view.findViewById(R.id.id_pop_modifyname_btn_cancel);

        btnCancel.setOnClickListener(v -> mPopWindowModifyNickName.dismiss());
        btnConfirm.setOnClickListener(v -> {
            String newNickName = etNewNickName.getText().toString();
            if (TextUtils.isEmpty(newNickName)) {
                ToastUtil.toast(getActivity(), getResources().getString(R.string.nickname_not_black));
                return;
            }
            UserRequest.UpdateNickname(XiaodiApplication.mCurrentUser.Id, XiaodiApplication.mCurrentUser.Token,
                    newNickName, new IResp<ResultResp>() {
                        @Override
                        public void success(ResultResp object) {
                            mPopWindowModifyNickName.dismiss();
                            XiaodiApplication.mCurrentUser.NickName = newNickName;
                            mtvNickName.setText(newNickName);
                            ToastUtil.toast(getActivity(), getResources().getString(R.string.update_nickname_successful));
                        }

                        @Override
                        public void fail(ResultResp resp) {
                            ToastUtil.toast(getActivity(), resp.message);
                            mPopWindowModifyNickName.dismiss();
                        }
                    });
        });
    }

    @Override
    public void errorLoadImg() {

    }

    private void bindEvent() {
        mivSetImg.setOnClickListener(v -> showPopupWindow(mivImg));
        mtvNickName.setOnClickListener(this::setNickName);
        mrlSetting.setOnClickListener(v -> toNext(AcSetting.class));
        mrlAttestation.setOnClickListener(v -> toNext(AcAuth.class));
        mrlMoney.setOnClickListener(v -> toNext(AcWallet.class));
        mrlCarryRecord.setOnClickListener(v -> toNext(AcCarryRecord.class));
        mrlSignature.setOnClickListener(v -> toNext(AcSign.class));
    }

    private void toNext(Class<?> next) {
        Intent intent = new Intent(getActivity(), next);
        startActivity(intent);
    }

    private void bindView(View view) {
        mivImg = view.findViewById(R.id.id_fg_my_me_iv_img);
        mivSetImg = view.findViewById(R.id.id_fg_my_me_iv_setimg);
        mtvNickName = view.findViewById(R.id.id_ac_my_me_tv_name);
        mrlSignature = view.findViewById(R.id.id_my_rl_everyday_sign);
        mrlMoney = view.findViewById(R.id.id_my_rl_wallet);
        mtvMoney = view.findViewById(R.id.id_my_tv_wallet);
        mrlCarryRecord = view.findViewById(R.id.id_ac_my_rl_carryrecode);
        mrlAttestation = view.findViewById(R.id.id_fg_my_rl_xiaodi_attestation);
        mtvRealName = view.findViewById(R.id.id_ac_my_tv_realName);
        mrlSetting = view.findViewById(R.id.id_ac_my_rl_setting);
        mtvAttestationStatus = view.findViewById(R.id.id_fg_my_tv_is_xiaodi_attestation);
        mtvContent = view.findViewById(R.id.id_top_tv_content);
    }
}
