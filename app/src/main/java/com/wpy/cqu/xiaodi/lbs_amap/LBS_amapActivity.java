package com.wpy.cqu.xiaodi.lbs_amap;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.orhanobut.logger.Logger;
import com.wpy.cqu.xiaodi.R;
import com.wpy.cqu.xiaodi.application.XiaodiApplication;
import com.wpy.cqu.xiaodi.base_activity.CheckPermissionsActivity;
import com.wpy.cqu.xiaodi.base_activity.StatusBarAppComptActivity;
import com.wpy.cqu.xiaodi.base_activity.TopBarAppComptAcitity;
import com.wpy.cqu.xiaodi.model.ResultResp;
import com.wpy.cqu.xiaodi.model.Reward;
import com.wpy.cqu.xiaodi.net.RewardRequst;
import com.wpy.cqu.xiaodi.net.resp.IResp;
import com.wpy.cqu.xiaodi.util.ToastUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LBS_amapActivity extends TopBarAppComptAcitity
        implements LocationSource, AMapLocationListener, AMap.OnInfoWindowClickListener,
        AMap.InfoWindowAdapter, GeocodeSearch.OnGeocodeSearchListener {

    private static final int STATUS_BAR_COLOR = Color.parseColor("#00dec9");

    private static final String SCHOOL = "重庆大学";
    private static final String SEARCH_CITY = "023";

    public static final String[] PERMISSION = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
    };

    private MapView mMapView;

    private AMap mMap;

    // 定位相关
    private AMapLocationClient mLocationClient;

    private OnLocationChangedListener mOnLocationChangedListener;
    //判断是否是首次进入
    private boolean mIsFirst = true;

    //文本转换经纬度
    private GeocodeSearch mGeocodeSearch;

    //同步地址转经纬度
    private Semaphore semaphoreSync;
    private String currentKey;
    private List<Reward> rewardList; //保存原始数据
    private Map<String, AmapRewards> amapRewardsMap = new HashMap<>();

    //切换出发地,目的地显示
    private ImageView mivRight;
    private boolean isShowByOrigin = true;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = new Bundle();
        bundle.putInt(StatusBarAppComptActivity.STATUS_COLOR_STR, STATUS_BAR_COLOR);
        bundle.putStringArray(CheckPermissionsActivity.PEMISSION, PERMISSION);
        super.onCreate(bundle);
        setContentView(R.layout.ac_amap);
        bindView();
        initView();
        initMap(savedInstanceState);
        bindEvent();
    }

    private void bindEvent() {
        mivBack.setOnClickListener(view -> finish());
        mtvBack.setOnClickListener(view -> finish());
        mivRight.setOnClickListener(this::changeShow);
    }

    private void changeShow(View view) {
        amapRewardsMap.clear();
        mMap.clear();
        if (isShowByOrigin) {
            //切换为按目的地显示
            isShowByOrigin = false;
            ToastUtil.toast(this, getResources().getString(R.string.show_dst));
            mtvContent.setText(getResources().getString(R.string.nearby_dst));
            handlerRewards(false);
            return;
        }
        isShowByOrigin = true;
        ToastUtil.toast(this, getResources().getString(R.string.show_origin));
        mtvContent.setText(getResources().getString(R.string.nearby_origin));
        handlerRewards(true);
    }

    private void bindView() {
        mtvBack = findViewById(R.id.id_top_back_tv);
        mivBack = findViewById(R.id.id_top_back_iv_img);
        mtvContent = findViewById(R.id.id_top_tv_content);
        mMapView = findViewById(R.id.id_ac_amap_map);
        mivRight = findViewById(R.id.id_top_right_iv_img);
    }

    private void initView() {
        mtvBack.setText(getResources().getString(R.string.reward_hall));
        mtvBack.setTextColor(Color.WHITE);
        mivBack.setImageResource(R.drawable.go_back_white);
        mtvContent.setText(getResources().getString(R.string.nearby_origin));
        mivRight.setImageResource(R.drawable.change);
        mivRight.setVisibility(View.VISIBLE);
    }

    private void initMap(Bundle savedInstanceState) {
        mMapView.onCreate(savedInstanceState);
        if (null == mMap) {
            mMap = mMapView.getMap();
            mMap.setOnInfoWindowClickListener(this);// 设置点击infoWindow事件监听器
            mGeocodeSearch = new GeocodeSearch(this);
            mGeocodeSearch.setOnGeocodeSearchListener(this);
            showMyLocation();
            requestRewards();
        }
    }

    private void showMyLocation() {
        // 定义定位图标
        setMyLocationIcon();
        mMap.setLocationSource(this);// 设置定位监听
        mMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        mMap.setMyLocationEnabled(true);
        // 显示比例尺
        mMap.getUiSettings().setScaleControlsEnabled(true);
        //设置缩放比例
        mMap.moveCamera(CameraUpdateFactory.zoomTo(16f));
    }

    private void requestRewards() {
        Logger.i("requestRewards");
        RewardRequst.ShowAllRewards(XiaodiApplication.mCurrentUser.Id,
                XiaodiApplication.mCurrentUser.Token, new IResp<List<Reward>>() {
                    @Override
                    public void success(List<Reward> object) {
                        rewardList = object;
                        handlerRewards(true);
                    }

                    @Override
                    public void fail(ResultResp resp) {
                        ToastUtil.toast(LBS_amapActivity.this, resp.message);
                    }
                });
    }

    private void handlerRewards(boolean showByOrigin) {
        //此时处于mainThread,因此换线程
        if (null == rewardList || rewardList.isEmpty()) {
            ToastUtil.toast(this, getResources().getString(R.string.no_more_data));
            return;
        }
        semaphoreSync = new Semaphore(0);
        Observable.just("")
                .doOnNext(s -> {
                    for (Reward reward : rewardList) {
                        if (amapRewardsMap.containsKey(reward.originLocation)) {
                            amapRewardsMap.get(reward.originLocation).rewards.add(reward);
                            continue;
                        }
                        //需要进行地址转换
                        AmapRewards amapRewards = new AmapRewards();
                        if (showByOrigin) {
                            currentKey = reward.originLocation;
                        } else {
                            currentKey = reward.dstLocation;
                        }
                        amapRewards.key = currentKey;
                        amapRewards.rewards.add(reward);
                        amapRewardsMap.put(currentKey, amapRewards);
                        GeocodeQuery query = new GeocodeQuery(SCHOOL + currentKey, SEARCH_CITY);
                        mGeocodeSearch.getFromLocationNameAsyn(query);
                        semaphoreSync.acquire();
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> addMarks());
    }

    //地址转经纬度
    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
        List<GeocodeAddress> geocodeAddressList = geocodeResult.getGeocodeAddressList();
        if (null == geocodeAddressList || geocodeAddressList.isEmpty()) {
            semaphoreSync.release();
            return;
        }
        GeocodeAddress geocodeAddress = geocodeAddressList.get(0);
        amapRewardsMap.get(currentKey).lat = geocodeAddress.getLatLonPoint().getLatitude();
        amapRewardsMap.get(currentKey).lon = geocodeAddress.getLatLonPoint().getLongitude();
        semaphoreSync.release();
    }

    public void addMarks() {
        Logger.i("addMarks");
        //根据返回的悬赏信息，在地图上标记出来，动画效果
        //根据数量排序
        List<AmapRewards> amapRewardsList = CollectionToList(amapRewardsMap.values());
        Collections.sort(amapRewardsList);

        //显示频率，根据数量不同,越小显示越快
        int period = amapRewardsList.size();
        //层数，作用于显示mark 覆盖物
        for (AmapRewards amapRewards : amapRewardsList) {
            ArrayList<BitmapDescriptor> giflist = new ArrayList<>();
            giflist.add(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED));
            giflist.add(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            giflist.add(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
            MarkerOptions options = new MarkerOptions();
            options.anchor(0.5f, 0.5f)
                    .position(new LatLng(amapRewards.lat, amapRewards.lon))
                    .icons(giflist)
                    .title(amapRewards.key)
                    .snippet("悬赏有 " + amapRewards.rewards.size() + "个")
                    .visible(true)
                    .period(period);
            Marker marker = mMap.addMarker(options);
            marker.showInfoWindow();
            period--;
        }

        //再将镜头移动到最多的那个地方
        AmapRewards amapRewardsForPosition = amapRewardsList.get(amapRewardsList.size() - 1);
        CameraUpdate cameraUpdate = CameraUpdateFactory.changeLatLng(new LatLng(amapRewardsForPosition.lat, amapRewardsForPosition.lon));
        mMap.moveCamera(cameraUpdate);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    private <T> List<T> CollectionToList(Collection<T> collection) {
        List<T> list = new ArrayList<>();
        for (T t : collection) {
            list.add(t);
        }
        return list;
    }

    private void setMyLocationIcon() {
        // 定义定位图标
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.drawable.myposition));// 设置我的位置的图标
        myLocationStyle.strokeColor(Color.TRANSPARENT);// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.TRANSPARENT);// 设置圆形的填充颜色
        myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
        mMap.setMyLocationStyle(myLocationStyle);
    }

    //地址转经纬度
    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (null == mOnLocationChangedListener || null == aMapLocation) {
            String errText = "定位失败";
            ToastUtil.toast(this, errText);
            return;
        }
        if (0 == aMapLocation.getErrorCode()) {
            mOnLocationChangedListener.onLocationChanged(aMapLocation);
            if (mIsFirst) {
                CameraUpdate cameraUpdate = CameraUpdateFactory.changeLatLng(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude()));
                mMap.moveCamera(cameraUpdate);
                mIsFirst = false;
            }
        }
    }

    /**
     * 自定义的Infowindow
     */
    @Override
    public View getInfoWindow(Marker marker) {
        View view = getLayoutInflater().inflate(R.layout.amapinfowindow, null);
        TextView mtvNumber = view.findViewById(R.id.id_amap_info_number);
        mtvNumber.append(marker.getTitle() + " " + marker.getSnippet());
        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        //处理点击info事件
        String key = marker.getTitle();
        AmapRewards amapRewards = amapRewardsMap.get(key);
        Intent intent = new Intent();
        intent.putExtra("rewards", amapRewards);
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * LocationSource方法
     * 用于在按下地图上的“我的位置”按钮时进行回调
     */
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mOnLocationChangedListener = onLocationChangedListener;
        setMyLocationIcon();
        if (mLocationClient == null) {
            mLocationClient = new AMapLocationClient(this);
            AMapLocationClientOption clientOption = new AMapLocationClientOption();
            //设置定位监听
            mLocationClient.setLocationListener(this);
            //设置为高精度定位模式
            clientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mLocationClient.setLocationOption(clientOption);
            mLocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        mOnLocationChangedListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }
}
