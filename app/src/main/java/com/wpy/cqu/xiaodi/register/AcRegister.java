package com.wpy.cqu.xiaodi.register;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wpy.cqu.xiaodi.R;
import com.wpy.cqu.xiaodi.application.XiaodiApplication;
import com.wpy.cqu.xiaodi.base_activity.CheckPermissionsActivity;
import com.wpy.cqu.xiaodi.base_activity.ClipBaseActivity;
import com.wpy.cqu.xiaodi.base_activity.StatusBarAppComptActivity;
import com.wpy.cqu.xiaodi.encrypt.AESEncrypt;
import com.wpy.cqu.xiaodi.home.AcHome;
import com.wpy.cqu.xiaodi.im_chat.Rongyun;
import com.wpy.cqu.xiaodi.loading.Loading;
import com.wpy.cqu.xiaodi.model.ResultResp;
import com.wpy.cqu.xiaodi.model.User;
import com.wpy.cqu.xiaodi.net.UserRequest;
import com.wpy.cqu.xiaodi.net.resp.IResp;
import com.wpy.cqu.xiaodi.util.ToastUtil;

public class AcRegister extends ClipBaseActivity {

    private static final int STATUS_BAR_COLOR = Color.parseColor("#00dec9");

    public static final String TAG = AcRegister.class.getName();

    private static final String[] PERMISSION = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.READ_PHONE_STATE,
    };

    private ImageView mivImg;

    private EditText metName;

    private EditText metPass;

    private ImageView mivSee;

    private Button mbtnRegister;

    private boolean mdisplayPwd = false;

    private String mImgPath = "";

    private PopupWindow mLoadingPopWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = new Bundle();
        bundle.putInt(StatusBarAppComptActivity.STATUS_COLOR_STR, STATUS_BAR_COLOR);
        bundle.putStringArray(CheckPermissionsActivity.PEMISSION, PERMISSION);
        super.onCreate(bundle);
        setContentView(R.layout.ac_register_create_acount);
        bindView();
        initView();
        bindEvent();
    }

    @Override
    public void errorLoadImg() {

    }

    @Override
    public void setImg(Bitmap img,String path) {
        mivImg.setImageBitmap(img);
        mImgPath = path;
    }

    private void register(View v) {
        String phone = getIntent().getStringExtra("phone");
        String nickName = metName.getText().toString();
        String pass = metPass.getText().toString();
        String encryptPass = AESEncrypt.Base64AESEncrypt(pass);

        if (null == mLoadingPopWindow) {
            mLoadingPopWindow = Loading.getLoadingPopwindown(this);
        }
        Loading.showLoading(this,mLoadingPopWindow);
        UserRequest.Register(phone, encryptPass, nickName, mImgPath, new IResp<User>() {
            @Override
            public void success(User user) {
                mLoadingPopWindow.dismiss();
                user.ImgLocalPath = mImgPath;
                user.Pass = encryptPass;
                saveUser(user);
                Rongyun.toHomeAc(AcRegister.this,true,true);
            }

            @Override
            public void fail(ResultResp resp) {
                mLoadingPopWindow.dismiss();
                ToastUtil.toast(AcRegister.this,resp.message);
            }
        });
    }

    private void saveUser(User user) {
        XiaodiApplication.mCurrentUser = user;
        user.saveToLocalFile();
    }


    private void bindView() {
        mivBack = (ImageView) findViewById(R.id.id_top_back_iv_img);
        mtvBack = (TextView) findViewById(R.id.id_top_back_tv);
        mtvContent = (TextView) findViewById(R.id.id_top_tv_content);
        mrlTop = (RelativeLayout) findViewById(R.id.id_top_rl);
        mivImg = (ImageView) findViewById(R.id.id_ac_register_iv_img);
        metName = (EditText) findViewById(R.id.id_ac_register_et_nickname);
        metPass = (EditText) findViewById(R.id.id_ac_register_et_pass);
        mivSee = (ImageView) findViewById(R.id.id_ac_register_iv_pwd_display);
        mbtnRegister = (Button) findViewById(R.id.id_ac_register_btn_confirm);
    }

    private void initView() {
        mivBack.setImageResource(R.drawable.go_back_white);
        mtvBack.setText(getResources().getString(R.string.login));
        mtvBack.setTextColor(Color.WHITE);
        mtvContent.setText(getResources().getString(R.string.register));
    }

    private void bindEvent() {
        mivBack.setOnClickListener(v -> finish());
        mtvBack.setOnClickListener(v -> finish());
        mivImg.setOnClickListener(v -> showPopupWindow(mivImg));
        mivSee.setOnClickListener(v -> {
            if (!mdisplayPwd) {
                mdisplayPwd = true;
                mivSee.setImageResource(R.drawable.eye);
                metPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                return;
            }
            mdisplayPwd = false;
            mivSee.setImageResource(R.drawable.eye_close);
            metPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
        });
        mbtnRegister.setOnClickListener(this::register);
    }

    private void toHome() {
        Intent intent = new Intent(AcRegister.this, AcHome.class);
        startActivity(intent);
        finish();
    }
}
