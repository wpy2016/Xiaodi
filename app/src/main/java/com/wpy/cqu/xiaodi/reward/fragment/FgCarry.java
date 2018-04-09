package com.wpy.cqu.xiaodi.reward.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wpy.cqu.xiaodi.R;
import com.wpy.cqu.xiaodi.adapter.recycler.RewardAdapter;
import com.wpy.cqu.xiaodi.application.XiaodiApplication;
import com.wpy.cqu.xiaodi.model.ResultResp;
import com.wpy.cqu.xiaodi.model.Reward;
import com.wpy.cqu.xiaodi.net.RewardRequst;
import com.wpy.cqu.xiaodi.net.resp.IResp;
import com.wpy.cqu.xiaodi.reward.AcCarryRecord;
import com.wpy.cqu.xiaodi.reward.AcRewardDetail;
import com.wpy.cqu.xiaodi.util.ToastUtil;

import java.util.List;

/**
 * Created by wangpeiyu on 2018/3/30.
 */

public class FgCarry extends Fragment {

    private RecyclerView recyclerView;

    private RewardAdapter rewardAdapter;

    private int carryRecordType = AcCarryRecord.MySend;

    private IResp<List<Reward>> resp = new IResp<List<Reward>>() {
        @Override
        public void success(List<Reward> rewards) {
            rewardAdapter.refresh(rewards);
        }

        @Override
        public void fail(ResultResp resp) {
            if (FgCarry.this.isResumed()) {
                ToastUtil.toast(getActivity(),resp.message);
            }
        }
    };

    public static FgCarry newInstance(int carryRecordType) {
        FgCarry fragment = new FgCarry();
        fragment.carryRecordType = carryRecordType;
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
        View view = inflater.inflate(R.layout.fg_carry_record, null);
        bindView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bindEvent();
        bindData();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void bindData() {
        switch (carryRecordType) {
            case AcCarryRecord.MySend:
                RewardRequst.ShowRewardsMySend(XiaodiApplication.mCurrentUser.Id,XiaodiApplication.mCurrentUser.Token,resp);
                break;
            case  AcCarryRecord.MyCarry:
                RewardRequst.ShowRewardsMyCarry(XiaodiApplication.mCurrentUser.Id,XiaodiApplication.mCurrentUser.Token,resp);
                break;
        }
    }

    private void bindView(View view) {
        recyclerView = view.findViewById(R.id.id_fg_carry_record_refreshview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        rewardAdapter = new RewardAdapter(getContext());
        recyclerView.setAdapter(rewardAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    private void bindEvent() {
        rewardAdapter.setOnItemClickListener(this::itemClick);
    }

    private void itemClick(Reward reward) {
        switch (carryRecordType) {
            case AcCarryRecord.MyCarry:
                toDetailAc(reward);
                break;
            case AcCarryRecord.MySend:
                if (Reward.REWARD_STATE_SEND == reward.thing.type) {
                    // TODO: 2018/4/9 去修改界面
                    return;
                }
                toDetailAc(reward);
                break;
        }
    }

    private void toDetailAc(Reward reward) {
        Intent intent = new Intent(getContext(), AcRewardDetail.class);
        intent.putExtra("reward", reward);
        startActivity(intent);
    }
}
