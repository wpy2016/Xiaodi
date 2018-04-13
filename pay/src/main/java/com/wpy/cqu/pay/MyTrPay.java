package com.wpy.cqu.pay;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.base.bj.paysdk.domain.TrPayResult;
import com.base.bj.paysdk.listener.PayResultListener;
import com.base.bj.paysdk.utils.TrPay;

/**
 * Created by wangpeiyu on 2018/4/13.
 */

public class MyTrPay implements IPay {

    private static final String KEY = "75afda214e204c7b81e71f02f85c419f";

    /**
     * 1.发起快捷支付调用(打开TrPay收银台页面，用户自己选择支付方式）
     *
     * @param activity   上下文
     * @param tradename  商品名称
     * @param outtradeno 商户系统订单号(商户系统内唯一)
     * @param amount     商品价格（单位：分。如1.5元传150）
     * @param backparams 商户系统回调参数
     * @param notifyurl  商户系统回调地址
     * @param userid     商户系统用户ID(如：trpay@52yszd.com，商户系统内唯一)
     * @param resp       请求结果回调
     */
    @Override
    public void Pay(AppCompatActivity activity, String tradename, String outtradeno, Long amount, String backparams, String notifyurl, String userid, final IPayResp resp) {

        /**
         * 初始化PaySdk(context请传入当前Activity对象(如：MainActivity.this))
         * 第一个参数:是您在trPay后面应用的appkey（需要先提交应用资料(若应用未上线，需上传测试APK文件)，审核通过后appkey生效）
         * 第二个参数:是您的渠道，一般是baidu,360,xiaomi等
         */
        TrPay.getInstance(activity).initPaySdk(KEY, "baidu");

        /**
         * 1.发起快捷支付调用(打开TrPay收银台页面，用户自己选择支付方式）
         * @param tradename   商品名称
         * @param outtradeno   商户系统订单号(商户系统内唯一)
         * @param amount        商品价格（单位：分。如1.5元传150）
         * @param backparams 商户系统回调参数
         * @param notifyurl       商户系统回调地址
         * @param userid          商户系统用户ID(如：trpay@52yszd.com，商户系统内唯一)
         */
        TrPay.getInstance(activity).callPay(tradename, outtradeno, amount, backparams, notifyurl, userid, new PayResultListener() {
            /**
             * 支付完成回调
             * @param context        上下文
             * @param outtradeno   商户系统订单号
             * @param resultCode   支付状态(RESULT_CODE_SUCC：支付成功、RESULT_CODE_FAIL：支付失败)
             * @param resultString  支付结果
             * @param payType      支付类型（1：支付宝 2：微信 3：银联）
             * @param amount       支付金额
             * @param tradename   商品名称
             */
            @Override
            public void onPayFinish(Context context, String outtradeno, int resultCode, String resultString, int payType, Long amount, String tradename) {
                if (resultCode == TrPayResult.RESULT_CODE_SUCC.getId()) {
                    resp.onSuccess();
                } else if (resultCode == TrPayResult.RESULT_CODE_FAIL.getId()) {
                    resp.onFail(resultCode + ":" + resultString);
                }
            }
        });
    }
}
