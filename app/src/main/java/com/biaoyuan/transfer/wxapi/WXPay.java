package com.biaoyuan.transfer.wxapi;


import com.and.yzy.frame.util.AppManger;
import com.orhanobut.logger.Logger;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by admin on 2016/9/9.
 */
public class WXPay {


    private IWXAPI iwxapi;


    public WXPay() {
        iwxapi = WXAPIFactory.createWXAPI(AppManger.getInstance().getTopActivity(), null);
    }

    public void pay(WxParams params) {
        Logger.v("pay==" + params.getSign());
        PayReq payReq = new PayReq();
        payReq.appId = params.getAppId();
        payReq.partnerId = params.getPartnerId();
        payReq.prepayId = params.getPrepayId();
        payReq.packageValue = "Sign=WXPay";
        payReq.nonceStr = params.getNonceStr();
        payReq.timeStamp = params.getTimeStamp();
        payReq.sign = params.getSign();
        iwxapi.sendReq(payReq);
    }
}
