package com.wpy.cqu.xiaodi.im_chat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;
import com.wpy.cqu.xiaodi.R;
import com.wpy.cqu.xiaodi.model.Reward;
import com.wpy.cqu.xiaodi.model.Thing;
import com.wpy.cqu.xiaodi.model.User;

/**
 * Created by wangpeiyu on 2018/4/10.
 */

public class FgRewardDetail extends Fragment {

    private ImageView mivThingImg;

    private TextView mtvXiaodian;

    private TextView mtvWeight;

    private TextView mtvStartPlace;

    private TextView mtvDstPlace;

    private TextView mtvDeadline;

    private TextView mtvDescribe;

    private ImageView mivPublisherImg;

    private TextView mtvPublisherNickName;


    private TextView mtvAuthStatus;

    private TextView mtvCredit;

    protected Button mbtnArray;

    private Reward reward;

    public FgRewardDetail() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reward = (Reward) getArguments().getSerializable("reward");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ac_details, null);
        bindView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mbtnArray.setOnClickListener(this::doWithButton);
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    /**
     * 根据状态进行操作
     *
     * @param view
     */
    private void doWithButton(View view) {

    }

    private void bindView(View view) {
        mivThingImg = (ImageView) view.findViewById(R.id.id_ac_details_iv_good_picture);
        mtvXiaodian = (TextView) view.findViewById(R.id.id_ac_details_tv_grade_num);
        mtvWeight = (TextView) view.findViewById(R.id.id_ac_details_tv_type);
        mtvStartPlace = (TextView) view.findViewById(R.id.id_ac_details_tv_start_place);
        mtvDstPlace = (TextView) view.findViewById(R.id.id_ac_details_tv_arrive_place);
        mtvDeadline = (TextView) view.findViewById(R.id.id_ac_details_tv_start_stop_time);
        mtvDescribe = (TextView) view.findViewById(R.id.id_ac_details_tv_text_des);
        mivPublisherImg = (ImageView) view.findViewById(R.id.id_ac_details_iv_head_picture);
        mtvPublisherNickName = (TextView) view.findViewById(R.id.id_ac_details_tv_owner_name);
        mtvAuthStatus = (TextView) view.findViewById(R.id.id_xd_des_tv_real_name);
        mtvCredit = (TextView) view.findViewById(R.id.id_xd_des_tv_credit);
        mbtnArray = (Button) view.findViewById(R.id.id_xd_des_btn_receive);
        view.findViewById(R.id.id_top_rl).setVisibility(View.GONE);
    }

    private void initView() {
        //mbtnArray根据reward状态进行更新
        // TODO: 2018/4/10

        if (null == reward) {
            return;
        }
        mivThingImg.setImageResource(Thing.DEFAULT_TYPE_IMG[reward.thing.type]);
        Picasso.with(getActivity()).load(reward.thing.thumbnail).error(Thing.DEFAULT_TYPE_IMG[reward.thing.type]).into(mivThingImg);
        mtvXiaodian.setText(reward.xiaodian + "");
        mtvWeight.setText(reward.thing.weight);
        mtvStartPlace.setText(reward.originLocation);
        mtvDstPlace.setText(reward.dstLocation);
        mtvDeadline.setText(reward.deadline);
        mtvDescribe.setText(reward.describe);
        mivPublisherImg.setImageResource(R.drawable.default_headimg);
        Picasso.with(getActivity()).load(reward.Publisher.ImgUrl).error(R.drawable.default_headimg).into(mivPublisherImg);
        mtvPublisherNickName.setText(reward.Publisher.NickName);
        mtvAuthStatus.setText(User.AuthStatus[reward.Publisher.UserType]);
        mtvCredit.setText(reward.Publisher.Creditibility + "");
    }
}
