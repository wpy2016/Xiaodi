package com.wpy.cqu.xiaodi.adapter.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wpy.cqu.xiaodi.R;

/**
 * Created by wangpeiyu on 2018/4/7.
 */

public class RewardHolder extends RecyclerView.ViewHolder {

    public ImageView mivImg;

    public TextView mtvThingType;

    public TextView mtvThingWeight;

    public TextView mtvStartPlace;

    public TextView mtvDstPlace;

    public TextView mtvDeadline;

    public TextView mtvXiaodian;

    public RewardHolder(View itemView) {
        super(itemView);
        mivImg = itemView.findViewById(R.id.id_reward_iv_img);
        mtvThingType = itemView.findViewById(R.id.id_reward_tv_name);
        mtvThingWeight = itemView.findViewById(R.id.id_reward_tv_weight);
        mtvStartPlace = itemView.findViewById(R.id.id_reward_tv_start_place);
        mtvDstPlace = itemView.findViewById(R.id.id_reward_tv_end_place);
        mtvDeadline = itemView.findViewById(R.id.id_reward_tv_limit_time);
        mtvXiaodian = itemView.findViewById(R.id.id_reward_tv_credit);
    }
}
