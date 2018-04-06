package com.wpy.cqu.xiaodi.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wpy.cqu.xiaodi.R;

/**
 * Created by wangpeiyu on 2018/3/30.
 */

public class FgMessage extends Fragment {


    private TextView mtvContent;

    private SmartRefreshLayout smartRefreshLayout;

    private RecyclerView recyclerView;



    public static FgMessage newInstance() {
        FgMessage fragment = new FgMessage();
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
        View view = inflater.inflate(R.layout.fg_message, null);
        bindView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        bindEvent();
    }

    private void refresh() {
        smartRefreshLayout.finishRefresh(2000);
    }

    private void loadMore() {
        smartRefreshLayout.finishLoadMore(2000);
    }

    private void initView() {
        mtvContent.setText(getResources().getString(R.string.message));
    }

    private void bindView(View view) {
        mtvContent = view.findViewById(R.id.id_top_tv_content);
        smartRefreshLayout = view.findViewById(R.id.id_fg_message_refreshlayout);
        recyclerView = view.findViewById(R.id.id_fg_message_refreshview);
    }

    private void bindEvent() {
        smartRefreshLayout.setOnRefreshListener(layout->{
            refresh();
        });
        smartRefreshLayout.setOnLoadMoreListener(layout->{
            loadMore();
        });
    }


}
