package com.wpy.cqu.xiaodi.lbs_amap;

import android.support.annotation.NonNull;

import com.wpy.cqu.xiaodi.model.Reward;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AmapRewards implements Comparable<AmapRewards>, Serializable {
    public String key;
    public List<Reward> rewards = new ArrayList<>();
    public double lat;
    public double lon;

    @Override
    public int compareTo(@NonNull AmapRewards amapRewards) {
        return rewards.size() - amapRewards.rewards.size();
    }
}