package com.wpy.cqu.xiaodi.home.fragment;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.wpy.cqu.xiaodi.R;
import com.wpy.cqu.xiaodi.home.AcHomeAdd;
import com.wpy.cqu.xiaodi.util.DpUtil;
import com.wpy.cqu.xiaodi.util.ToastUtil;
import com.wpy.cqu.xiaodi.view.refreshListView;

/**
 * Created by wangpeiyu on 2018/3/30.
 */

public class FgHall extends Fragment {

    private static enum ShowType {
        NEWS,KEY_WORD,XIAODIAN_DEC,XIAODIAN_AEC
    }

    private static enum SERVICE_TYPE {REWARD,GOOUT}



    private TextView mtvContent;

    private ImageView mivTypeDown;

    private ImageView mivAdd;

    private TextView mtvSortXiaodian;

    private TextView mtvNearby;

    private TextView mtvSearch;

    private refreshListView mrefreshListView;

    private PopupWindow mPopSearch;

    private String msKeyWord;

    private ShowType mShowType = ShowType.NEWS;

    private SERVICE_TYPE mServiceType = SERVICE_TYPE.REWARD;

    public static FgHall newInstance() {
        FgHall fragment = new FgHall();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_hall, null);
        bindView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        bindEvent();
    }

    @Override
    public void onResume() {
        super.onResume();
        mivAdd.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPause() {
        super.onPause();
        mivAdd.setVisibility(View.INVISIBLE);
    }

    private void changeType(View view) {

    }

    private void sortByXiaoDian(View view) {


    }

    private void nearby(View view) {

    }

    private void SearchByKeyWord(String sKeyword) {

    }

    private void showPopSearch() {
        if (null == mPopSearch) {
            View popSearchView = LayoutInflater.from(getActivity()).inflate(R.layout.pop_search, null);
            mPopSearch = new PopupWindow(popSearchView, LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT, true);
            initPopSearch(popSearchView);
        }
        mPopSearch.showAsDropDown(mtvSearch, DpUtil.dip2px(getActivity(), 30), 0);
    }

    private void initPopSearch(View popSearchView) {
        EditText etKeyWordSearch = (EditText) popSearchView.findViewById(R.id.id_pop_search_et_search);
        Button btnMattch = (Button) popSearchView.findViewById(R.id.id_pop_btn_search);
        ImageView ivClose = (ImageView) popSearchView.findViewById(R.id.id_pop_search_iv_close);
        btnMattch.setOnClickListener(v -> {
            String sKeyword = etKeyWordSearch.getText().toString();
            if ("".equals(sKeyword)) {
                ToastUtil.toast(getActivity(), getResources().getString(R.string.validator_empty));
                return;
            }
            SearchByKeyWord(sKeyword);
            msKeyWord = sKeyword;
            mShowType = ShowType.KEY_WORD;
            mPopSearch.dismiss();
        });
        ivClose.setOnClickListener(v->mPopSearch.dismiss());
        mPopSearch.setOutsideTouchable(true);
        mPopSearch.setBackgroundDrawable(new BitmapDrawable());
    }

    private void initView() {
        mtvContent.setText(getResources().getString(R.string.reward_hall));
        mivTypeDown.setVisibility(View.VISIBLE);
        mivTypeDown.setImageResource(R.drawable.hall_down_green);
        mivAdd.setVisibility(View.VISIBLE);
        mivAdd.setImageResource(R.drawable.hall_add);
    }

    private void bindView(View view) {
        mtvContent = view.findViewById(R.id.id_top_tv_content);
        mivTypeDown = view.findViewById(R.id.id_top_iv_down);
        mtvSortXiaodian = view.findViewById(R.id.id_fg_hall_tv_smilepoint);
        mtvNearby = view.findViewById(R.id.id_fg_hall_tv_nearby);
        mtvSearch = view.findViewById(R.id.id_fg_hall_tv_placematch);
        mivAdd = view.findViewById(R.id.id_top_right_iv_img);
        mrefreshListView = view.findViewById(R.id.id_fg_hall_lv_reward);
    }

    private void bindEvent() {
        mtvContent.setOnClickListener(this::changeType);
        mivTypeDown.setOnClickListener(this::changeType);
        mtvSortXiaodian.setOnClickListener(this::sortByXiaoDian);
        mtvNearby.setOnClickListener(this::nearby);
        mtvSearch.setOnClickListener(v->showPopSearch());
        mivAdd.setOnClickListener(v -> toNext(AcHomeAdd.class,null));
    }

    private void toNext(Class<?> next,Bundle bundle) {
        Intent intent = new Intent(getActivity(),next);
        intent.putExtra("home",bundle);
        startActivity(intent);
    }
}
