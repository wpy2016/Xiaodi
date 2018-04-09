package com.wpy.cqu.xiaodi.util;

import android.support.v7.app.AppCompatActivity;

import com.orhanobut.logger.Logger;

import java.util.Stack;

public class ActivityManager {

    private static final String TAG = "ActivityManager";

    private static Stack<AppCompatActivity> activityStack;

    private static ActivityManager instance;

    private ActivityManager() {

    }

    public synchronized static ActivityManager getActivityManager() {
        if (instance == null) {
            instance = new ActivityManager();
        }
        return instance;
    }

    //将当前Activity推入栈中
    public void pushActivity(AppCompatActivity activity) {
        if (null == activityStack) {
            activityStack = new Stack<AppCompatActivity>();
        }
        activityStack.add(activity);
    }

    //退出栈顶Activity
    public void popActivity() {
        activityStack.pop();
    }

    //退出栈中除指定的Activity外所有
    public void popAllActivityExceptTop(AppCompatActivity top) {
        while (true) {
            AppCompatActivity activity = activityStack.firstElement();
            if (null == activity) {
                return;
            }
            if (activity == top) {
                break;
            }
            activity.finish();
            activityStack.remove(activity);
        }
    }

    public int getActivityStackSize() {
        int size = 0;
        if (null != activityStack) {
            size = activityStack.size();
        }
        return size;
    }
}