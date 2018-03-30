package com.wpy.cqu.xiaodi.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wpy.cqu.xiaodi.R;
import com.wpy.cqu.xiaodi.view.refreshListView;

/**
 * Created by wangpeiyu on 2018/3/30.
 */

public class FgHall extends Fragment {


    private TextView mtvContent;

    private ImageView mivTypeDown;

    private ImageView mivAdd;

    private TextView mtvSortXiaodian;

    private TextView mtvNearby;

    private TextView mtvSearch;

    private refreshListView mrefreshListView;

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

    private void bindEvent() {
        mtvContent.setOnClickListener(this::changeType);
        mivTypeDown.setOnClickListener(this::changeType);
        mtvSortXiaodian.setOnClickListener(this::sortByXiaoDian);
        mtvNearby.setOnClickListener(this::nearby);
        mtvSearch.setOnClickListener(this::search);
        mivAdd.setOnClickListener(v->{

        });
    }

    private void changeType(View view) {

    }

    private void sortByXiaoDian(View view) {


    }

    private void nearby(View view) {

    }

    private void search(View view) {

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


}
