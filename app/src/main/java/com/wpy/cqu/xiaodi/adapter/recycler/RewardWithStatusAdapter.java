package com.wpy.cqu.xiaodi.adapter.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.wpy.cqu.xiaodi.R;
import com.wpy.cqu.xiaodi.model.Reward;
import com.wpy.cqu.xiaodi.model.Thing;
import com.wpy.cqu.xiaodi.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangpeiyu on 2018/4/7.
 */

public class RewardWithStatusAdapter extends RecyclerView.Adapter<RewardHolderWithStatus> {

    List<Reward> rewards = new ArrayList<>();

    private Context mContext;

    private OnItemClickListener<Reward> mOnItemClickListener;

    public RewardWithStatusAdapter(Context context) {
        mContext = context;
    }

    @Override
    public RewardHolderWithStatus onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.reward_item_with_status, parent, false);
        RewardHolderWithStatus rewardHolder = new RewardHolderWithStatus(view);
        return rewardHolder;
    }

    @Override
    public void onBindViewHolder(RewardHolderWithStatus holder, int position) {
        Reward reward = rewards.get(position);
        holder.mivImg.setImageResource(Thing.DEFAULT_TYPE_IMG[reward.thing.type]);
        Picasso.with(mContext).load(reward.thing.thumbnail).
                error(Thing.DEFAULT_TYPE_IMG[reward.thing.type]).into(holder.mivImg);
        holder.mtvThingType.setText(Thing.THING_TYPE[reward.thing.type]);
        holder.mtvThingWeight.setText(reward.thing.weight);
        holder.mtvStartPlace.setText(reward.originLocation);
        if (reward.Publisher.Id.equals("000000000")) {//说明是笑递中心
            holder.mtvDstPlace.setText(reward.Receiver.NickName);
        } else {
            holder.mtvDstPlace.setText(reward.dstLocation);
        }
        holder.mtvDeadline.setText(reward.deadline);
        holder.mtvXiaodian.setText(reward.xiaodian + "");

        //设置状态
        holder.mtvStatus.setText(Reward.REWARD_STATUS_STRING[reward.state]);
        holder.mtvStatus.setTextColor(Reward.REWARD_STATUS_COLOR_TEXT[reward.state]);

        if (null == mOnItemClickListener) {
            return;
        }
        View rootView = holder.mivImg.getRootView();
        rootView.setOnClickListener(view -> mOnItemClickListener.onClick(reward));
    }

    @Override
    public int getItemCount() {
        return rewards.size();
    }

    /**
     * @param rewardList
     * @param isShowTip  保证在fragment不可见时不进行提示
     */
    public void refresh(List<Reward> rewardList, boolean isShowTip) {
        rewards.clear();
        if (null == rewardList || rewardList.isEmpty()) {
            notifyDataSetChanged();
            if (isShowTip) {
                ToastUtil.toast(mContext, mContext.getResources().getString(R.string.no_more_data));
            }
            return;
        }
        rewards.addAll(rewardList);
        notifyDataSetChanged();
    }

    public void loadMore(List<Reward> rewardList) {
        if (null == rewardList || rewardList.isEmpty()) {
            ToastUtil.toast(mContext, mContext.getResources().getString(R.string.no_more_data));
            return;
        }
        rewards.addAll(rewardList);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener<Reward> onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

}
