package com.wpy.cqu.xiaodi.home.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wpy.cqu.xiaodi.R;
import com.wpy.cqu.xiaodi.adapter.recycler.RewardAdapter;
import com.wpy.cqu.xiaodi.application.XiaodiApplication;
import com.wpy.cqu.xiaodi.home.AcHomeAdd;
import com.wpy.cqu.xiaodi.model.ResultResp;
import com.wpy.cqu.xiaodi.model.Reward;
import com.wpy.cqu.xiaodi.net.RewardRequst;
import com.wpy.cqu.xiaodi.net.resp.IResp;
import com.wpy.cqu.xiaodi.reward.AcRewardDetail;
import com.wpy.cqu.xiaodi.util.DpUtil;
import com.wpy.cqu.xiaodi.util.ToastUtil;

import java.util.List;

/**
 * Created by wangpeiyu on 2018/3/30.
 */

public class FgHall extends Fragment {

    private static enum ShowType {
        NEWS, KEY_WORD, XIAODIAN_DEC
    }

    private TextView mtvContent;

    private ImageView mivAdd;

    private TextView mtvSortXiaodian;

    private TextView mtvNearby;

    private TextView mtvSearch;

    private SmartRefreshLayout smartRefreshLayout;

    private RecyclerView recyclerView;

    private RewardAdapter rewardAdapter;

    private PopupWindow mPopSearch;

    private ShowType mShowType = ShowType.NEWS;

    //标记加载第几页数据，随着loadmore的进行会不断增大
    private int mPage = 0;

    private String mKeyword = "";

    public static FgHall newInstance() {
        FgHall fragment = new FgHall();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private RewardResp rewardResp = new RewardResp();

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
        refresh();
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

    private void nearby(View view) {

    }

    private void sortByXiaoDian(View view) {
        mShowType = ShowType.XIAODIAN_DEC;
        mPage = 0;
        mtvSortXiaodian.setBackgroundColor(Color.parseColor("#ffdab9"));
        RewardRequst.ShowRewardsSortXiaodian(mPage, XiaodiApplication.mCurrentUser.Id,
                XiaodiApplication.mCurrentUser.Token, rewardResp.setIsRefresh(true));
    }

    private void SearchByKeyWord(String sKeyword) {
        mKeyword = sKeyword;
        mShowType = ShowType.KEY_WORD;
        mPage = 0;
        RewardRequst.ShowRewardsKeyword(mPage, sKeyword, XiaodiApplication.mCurrentUser.Id,
                XiaodiApplication.mCurrentUser.Token, rewardResp.setIsRefresh(true));
    }

    private void refresh() {
        mShowType = ShowType.NEWS;
        mPage = 0;
        mtvSortXiaodian.setBackgroundResource(R.color.background_color_gary);
        RewardRequst.ShowRewards(mPage, XiaodiApplication.mCurrentUser.Id,
                XiaodiApplication.mCurrentUser.Token, rewardResp.setIsRefresh(true));
    }

    private void loadMore() {
        mPage++;
        switch (mShowType) {
            case NEWS:
                RewardRequst.ShowRewards(mPage, XiaodiApplication.mCurrentUser.Id,
                        XiaodiApplication.mCurrentUser.Token, rewardResp.setIsRefresh(false));
                break;
            case XIAODIAN_DEC:
                RewardRequst.ShowRewardsSortXiaodian(mPage, XiaodiApplication.mCurrentUser.Id,
                        XiaodiApplication.mCurrentUser.Token, rewardResp.setIsRefresh(false));
                break;
            case KEY_WORD:
                RewardRequst.ShowRewardsKeyword(mPage, mKeyword, XiaodiApplication.mCurrentUser.Id,
                        XiaodiApplication.mCurrentUser.Token, rewardResp.setIsRefresh(false));
                break;
        }
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
        EditText etKeyWordSearch = popSearchView.findViewById(R.id.id_pop_search_et_search);
        Button btnMattch = popSearchView.findViewById(R.id.id_pop_btn_search);
        ImageView ivClose = popSearchView.findViewById(R.id.id_pop_search_iv_close);
        btnMattch.setOnClickListener(v -> {
            String sKeyword = etKeyWordSearch.getText().toString();
            if ("".equals(sKeyword)) {
                ToastUtil.toast(getActivity(), getResources().getString(R.string.validator_empty));
                return;
            }
            SearchByKeyWord(sKeyword);
            mPopSearch.dismiss();
        });
        ivClose.setOnClickListener(v -> mPopSearch.dismiss());
        mPopSearch.setOutsideTouchable(true);
        mPopSearch.setBackgroundDrawable(new BitmapDrawable());
    }

    private void initView() {
        mtvContent.setText(getResources().getString(R.string.reward_hall));
        mivAdd.setVisibility(View.VISIBLE);
        mivAdd.setImageResource(R.drawable.hall_add);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        rewardAdapter = new RewardAdapter(getContext());
        recyclerView.setAdapter(rewardAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }


    private void bindView(View view) {
        mtvContent = view.findViewById(R.id.id_top_tv_content);
        mtvSortXiaodian = view.findViewById(R.id.id_fg_hall_tv_smilepoint);
        mtvNearby = view.findViewById(R.id.id_fg_hall_tv_nearby);
        mtvSearch = view.findViewById(R.id.id_fg_hall_tv_placematch);
        mivAdd = view.findViewById(R.id.id_top_right_iv_img);
        smartRefreshLayout = view.findViewById(R.id.id_fg_hall_refreshlayout);
        recyclerView = view.findViewById(R.id.id_fg_hall_refreshview);
    }


    private void bindEvent() {
        mtvSortXiaodian.setOnClickListener(this::sortByXiaoDian);
        mtvNearby.setOnClickListener(this::nearby);
        mtvSearch.setOnClickListener(v -> showPopSearch());
        mivAdd.setOnClickListener(v -> toNext(AcHomeAdd.class, null));
        smartRefreshLayout.setOnRefreshListener(layout -> refresh());
        smartRefreshLayout.setOnLoadMoreListener(layout -> loadMore());
        rewardAdapter.setOnItemClickListener(this::toDetail);
    }

    private void toDetail(Reward reward) {
        Intent intent = new Intent(getActivity(), AcRewardDetail.class);
        intent.putExtra("reward", reward);
        startActivity(intent);
    }

    private void toNext(Class<?> next, Bundle bundle) {
        Intent intent = new Intent(getActivity(), next);
        intent.putExtra("home", bundle);
        startActivity(intent);
    }

    private class RewardResp implements IResp<List<Reward>> {

        private boolean isRefresh = true;

        @Override
        public void success(List<Reward> rewards) {
            if (isRefresh) {
                smartRefreshLayout.finishRefresh();
                rewardAdapter.refresh(rewards, FgHall.this.getUserVisibleHint());
                return;
            }
            smartRefreshLayout.finishLoadMore();
            rewardAdapter.loadMore(rewards);
        }

        @Override
        public void fail(ResultResp resp) {
            if (FgHall.this.getUserVisibleHint()) {
                ToastUtil.toast(getActivity(), resp.message);
            }
            if (isRefresh) {
                smartRefreshLayout.finishRefresh();
                return;
            }
            smartRefreshLayout.finishLoadMore();
        }

        private IResp<List<Reward>> setIsRefresh(boolean isRefresh) {
            this.isRefresh = isRefresh;
            return this;
        }
    }
}
