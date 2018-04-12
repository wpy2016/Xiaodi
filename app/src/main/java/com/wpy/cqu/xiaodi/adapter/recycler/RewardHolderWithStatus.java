package com.wpy.cqu.xiaodi.adapter.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wpy.cqu.xiaodi.R;

/**
 * Created by wangpeiyu on 2018/4/7.
 */

public class RewardHolderWithStatus extends RewardHolder {

    public TextView mtvStatus;

    public RewardHolderWithStatus(View itemView) {
        super(itemView);
        mtvStatus = itemView.findViewById(R.id.id_reward_tv_status);
    }
}
