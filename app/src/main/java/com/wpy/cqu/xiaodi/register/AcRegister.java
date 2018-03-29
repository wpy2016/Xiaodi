package com.wpy.cqu.xiaodi.register;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wpy.cqu.xiaodi.R;
import com.wpy.cqu.xiaodi.application.XiaodiApplication;
import com.wpy.cqu.xiaodi.base_activity.CheckPermissionsActivity;
import com.wpy.cqu.xiaodi.base_activity.StatusBarAppComptActivity;
import com.wpy.cqu.xiaodi.base_activity.TopBarAppComptAcitity;
import com.wpy.cqu.xiaodi.clip.AcClipImg;
import com.wpy.cqu.xiaodi.util.BitmapUtil;

import java.io.File;

public class AcRegister extends TopBarAppComptAcitity {

    private static final int STATUS_BAR_COLOR = Color.parseColor("#00dec9");

    private ImageView mivImg;

    private EditText metName;

    private EditText metPass;

    private ImageView mivSee;

    private Button mbtnRegister;

    private boolean mdisplayPwd = false;

    private PopupWindow mPopWindow;

    private LayoutInflater mLayoutInflater;

    //图片保存的文件夹
    private String PHOTOSAVEPATH = XiaodiApplication.IMG_SAVE_PATH + "/";

    //以当前时间的毫秒数当做文件名，设置好的图片的路径
    private String photoname = System.currentTimeMillis() + ".png";

    private String mPath;  //要找的图片路径

    private final static int PHOTOBYGALLERY = 0;//从相册获取照片

    private final static int PHOTOTACK = 1;//拍照获取

    private final static int PHOTOCOMPLETEBYTAKE = 2;//完成

    private final static int PHOTOCOMPLETEBYGALLERY = 3;//完成

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
        bundle.putInt(StatusBarAppComptActivity.STATUS_COLOR_STR, STATUS_BAR_COLOR);
        bundle.putStringArray(CheckPermissionsActivity.PEMISSION, PERMISSION);
        super.onCreate(bundle);
        setContentView(R.layout.ac_register_create_acount);
        bindView();
        initView();
        bindEvent();

        mLayoutInflater = LayoutInflater.from(this);
    }

    private void register(View v) {

    }

    private void setImg(View v) {
        showPopupWindow(mivImg);
    }

    private void showPopupWindow(ImageView img) {
        if (mPopWindow == null) {
            View view = mLayoutInflater.inflate(R.layout.pop_select_photo, null);
            mPopWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT, true);
            initPopupWindow(view);
        }
        mPopWindow.showAtLocation(img, Gravity.CENTER, 0, 0);
    }

    private void initPopupWindow(View view) {
        TextView gallery = view.findViewById(R.id.id_pop_select_photo_tv_from_gallery);
        TextView take = view.findViewById(R.id.id_pop_select_photo_tv_take_photo);
        TextView cancel = view.findViewById(R.id.id_pop_select_photo_tv_cancel);
        gallery.setOnClickListener(v -> {
            mPopWindow.dismiss();
            startToGetPhotoByGallery();
        });
        take.setOnClickListener(v -> {
            mPopWindow.dismiss();
            startToGetPhotoByTack();
        });

        cancel.setOnClickListener(v -> {
            if (mPopWindow.isShowing())
                mPopWindow.dismiss();
        });
        mPopWindow.setAnimationStyle(android.R.style.Animation_InputMethod);
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    private void startToGetPhotoByGallery() {
        Intent openGalleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        openGalleryIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(openGalleryIntent, PHOTOBYGALLERY);
    }

    private void startToGetPhotoByTack() {
        photoname = String.valueOf(System.currentTimeMillis()) + ".png";
        Uri imageUri;
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imageUri = Uri.fromFile(new File(PHOTOSAVEPATH, photoname));
        openCameraIntent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(openCameraIntent, PHOTOTACK);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        Uri uri;
        switch (requestCode) {
            case PHOTOBYGALLERY:
                uri = data.getData();
                if (null == uri) {
                    // TODO: 2018/3/30  图片加载失败,提示
                    break;
                }
                if (DocumentsContract.isDocumentUri(this, uri)) {
                    String wholeID = DocumentsContract.getDocumentId(uri);
                    String id = wholeID.split(":")[1];
                    String[] column = {MediaStore.Images.Media.DATA};
                    String sel = MediaStore.Images.Media._ID + "=?";
                    Cursor cursor = this.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column,
                            sel, new String[]{id}, null);
                    int columnIndex;
                    try {
                        columnIndex = cursor.getColumnIndex(column[0]);
                    } catch (NullPointerException e) {
                        errorLoadImg();
                        break;
                    }
                    if (cursor.moveToFirst()) {
                        mPath = cursor.getString(columnIndex);
                    }
                    cursor.close();
                } else {
                    String[] projection = {MediaStore.Images.Media.DATA};
                    Cursor cursor = this.getContentResolver().query(uri, projection, null, null, null);
                    int column_index;
                    try {
                        column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    } catch (NullPointerException e) {
                        errorLoadImg();
                        break;
                    }
                    cursor.moveToFirst();
                    mPath = cursor.getString(column_index);
                    cursor.close();
                }
                // 获取到照片之后调用裁剪acticity
                Intent intentGalley = new Intent(this, AcClipImg.class);
                intentGalley.putExtra("path", mPath);
                startActivityForResult(intentGalley, PHOTOCOMPLETEBYGALLERY);
                break;
            case PHOTOTACK:
                mPath = PHOTOSAVEPATH + photoname;
                Intent intentTake = new Intent(this, AcClipImg.class);
                intentTake.putExtra("path", mPath);
                startActivityForResult(intentTake, PHOTOCOMPLETEBYTAKE);
                break;
            case PHOTOCOMPLETEBYTAKE:
                final String temppath = data.getStringExtra("path");
                mivImg.setImageBitmap(BitmapUtil.getBitmapFormPath(this, temppath));
                //删除旧文件
                File file = new File(mPath);
                file.delete();
                mPath = temppath;
                break;
            case PHOTOCOMPLETEBYGALLERY:
                final String temppathgallery = data.getStringExtra("path");
                mivImg.setImageBitmap(BitmapUtil.getBitmapFormPath(this, temppathgallery));
                mPath = temppathgallery;
                break;
        }
    }

    private void errorLoadImg() {

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
        mivImg.setOnClickListener(this::setImg);
        mivSee.setOnClickListener(v -> {
            if (!mdisplayPwd) {
                mdisplayPwd = true;
                mivSee.setImageResource(R.drawable.eye);
                metPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                mdisplayPwd = false;
                mivSee.setImageResource(R.drawable.eye_close);
                metPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });
        mbtnRegister.setOnClickListener(this::register);
    }
}
