package com.wpy.cqu.xiaodi.base_activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wpy.cqu.xiaodi.R;
import com.wpy.cqu.xiaodi.application.XiaodiApplication;
import com.wpy.cqu.xiaodi.clip.AcClipImg;
import com.wpy.cqu.xiaodi.util.BitmapUtil;

import java.io.File;

/**
 * Created by wangpeiyu on 2018/3/30.
 */

public abstract class ClipBaseFragment extends Fragment {

    //图片保存的文件夹
    private String PHOTOSAVEPATH = XiaodiApplication.IMG_SAVE_PATH + "/";

    private final static int PHOTOBYGALLERY = 0;//从相册获取照片

    private final static int PHOTOTACK = 1;//拍照获取

    private final static int PHOTOCOMPLETEBYGALLERY = 3;//完成

    private final static int PHOTOCOMPLETEBYTAKE = 2;//完成

    //以当前时间的毫秒数当做文件名，设置好的图片的路径
    private String photoname = System.currentTimeMillis() + ".png";

    private String mPath;  //要找的图片路径

    private PopupWindow mPopWindow;

    private LayoutInflater mLayoutInflater;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutInflater = LayoutInflater.from(getActivity());
    }

    protected void showPopupWindow(ImageView img) {
        if (mPopWindow == null) {
            View view = mLayoutInflater.inflate(R.layout.pop_select_photo, null);
            mPopWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT, true);
            initPopupWindow(view);
        }
        mPopWindow.showAtLocation(img, Gravity.CENTER, 0, 0);
    }


    protected void initPopupWindow(View view) {
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                if (DocumentsContract.isDocumentUri(getActivity(), uri)) {
                    String wholeID = DocumentsContract.getDocumentId(uri);
                    String id = wholeID.split(":")[1];
                    String[] column = {MediaStore.Images.Media.DATA};
                    String sel = MediaStore.Images.Media._ID + "=?";
                    Cursor cursor = getActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column,
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
                    Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
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
                Intent intentGalley = new Intent(getActivity(), AcClipImg.class);
                intentGalley.putExtra("path", mPath);
                startActivityForResult(intentGalley, PHOTOCOMPLETEBYGALLERY);
                break;
            case PHOTOTACK:
                mPath = PHOTOSAVEPATH + photoname;
                Intent intentTake = new Intent(getActivity(), AcClipImg.class);
                intentTake.putExtra("path", mPath);
                startActivityForResult(intentTake, PHOTOCOMPLETEBYTAKE);
                break;
            case PHOTOCOMPLETEBYTAKE:
                final String temppath = data.getStringExtra("path");
                setImg(BitmapUtil.getBitmapFormPath(getActivity(), temppath),temppath);
                //删除旧文件
                File file = new File(mPath);
                file.delete();
                mPath = temppath;
                break;
            case PHOTOCOMPLETEBYGALLERY:
                final String temppathgallery = data.getStringExtra("path");
                setImg(BitmapUtil.getBitmapFormPath(getActivity(), temppathgallery),temppathgallery);
                mPath = temppathgallery;
                break;
        }
    }

    public abstract void errorLoadImg();

    public abstract void setImg(Bitmap img,String path);
}
