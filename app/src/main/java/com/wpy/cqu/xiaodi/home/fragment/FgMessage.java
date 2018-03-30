package com.wpy.cqu.xiaodi.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wpy.cqu.xiaodi.R;
import com.wpy.cqu.xiaodi.view.refreshListView;

/**
 * Created by wangpeiyu on 2018/3/30.
 */

public class FgMessage extends Fragment {


    private TextView mtvContent;

    private refreshListView mrefreshListView;

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
    }

    private void initView() {
        mtvContent.setText(getResources().getString(R.string.message));
    }

    private void bindView(View view) {
        mtvContent = view.findViewById(R.id.id_top_tv_content);
        mrefreshListView = view.findViewById(R.id.id_fg_communicate_lv_msg);
    }


}
