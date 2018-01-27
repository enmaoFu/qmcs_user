package com.biaoyuan.transfer.http;

import com.biaoyuan.transfer.config.UserManger;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Title  :
 * Create : 2017/6/20
 * Author ：chen
 */

public interface Mine {

    /**
     * 个人中心
     */
    @POST("personalcenter/toPersonalCenter")
    Call<ResponseBody> toPersonalCenter();


    /**
     * 我的行程
     */
    @POST("personalstroke/toPersonalStroke")
    @FormUrlEncoded
    Call<ResponseBody> toPersonalStroke(@Field("pageSize") int pageSize, @Field("targetPage") int targetPage, @Field("state") String state);

    /**
     * 退款售后
     */
    @POST("personalrefundaftersale/toRefundAfterSale")
    @FormUrlEncoded
    Call<ResponseBody> toRefundAfterSale(@Field("pageSize") int pageSize, @Field("targetPage") int targetPage, @Field("state") String state);

    /**
     * 我的评价
     */
    @POST("personalcomments/toMyAssessment")
    @FormUrlEncoded
    Call<ResponseBody> toMyAssessment(@Field("pageSize") int pageSize, @Field("targetPage") int targetPage);

    /**
     * 换绑手机号
     */
    @POST("personalcenter/setUserInformation")
    @FormUrlEncoded
    Call<ResponseBody> changePhone(@Field("userTelphone") String userTelphone, @Field("phoneCode") String phoneCode);

    /**
     * 设置登录密码
     */
    @POST("personalcenter/setUserInformation")
    @FormUrlEncoded
    Call<ResponseBody> changePwd(@Field("userPassword") String userPassword);

    /**
     * 设置用户名
     */
    @POST("personalcenter/setUserInformation")
    @FormUrlEncoded
    Call<ResponseBody> setName(@Field("userLoginName") String userLoginName);

    /**
     * 设置头像
     */
    @POST("personalcenter/setUserInformation")
    @FormUrlEncoded
    Call<ResponseBody> setHeader(@Field("userPortraitUrl") String userPortraitUrl);


    /**
     * 申请传送天使
     */
    @POST("transportangel/applyTransportAngel")
    @FormUrlEncoded
    Call<ResponseBody> applyTransportAngel(@Field("identityRealName") String identityRealName, @Field("identityIdCode") String identityIdCode, @Field("identityEmergrncyContact") String identityEmergrncyContact
            , @Field("identityEmergrncyPhone") long identityEmergrncyPhone, @Field("identityIdPicTake") String identityIdPicTake, @Field("identityIdPicBack") String identityIdPicBack, @Field("identityIdPicFront") String identityIdPicFront
    );


    /**
     * 删除行程
     */
    @POST("personalstroke/deletePathByid")
    @FormUrlEncoded
    Call<ResponseBody> deletePathByid(@Field("pathId") long orderId);

    /**
     * 比对密码
     */
    @POST("personalcenter/userPasswordVerification")
    @FormUrlEncoded
    Call<ResponseBody> userasswordVerification(@Field("password") String password);

    /**
     * 修改手机号发送验证码
     */
    @POST("unauth/sendSmsNotSignin")
    @FormUrlEncoded
    Call<ResponseBody> sendSmsNotSignin(@Field("staffTephone") String staffTephone);

    /**
     * 查看传送员申请状态
     */
    @POST("transportangel/queryCarrierReview")
    Call<ResponseBody> queryCarrierReview();


    /**
     * 我的余额
     *
     * @return
     */
    @POST("payment/acount")
    Call<ResponseBody> acount();

    /**
     * 按月份查询交易记录
     *
     * @param month
     * @return
     */
    @POST("payment/monthBalance")
    @FormUrlEncoded
    Call<ResponseBody> monthBalance(@Field("month") String month);

    /**
     * 资金流向详情
     *
     * @param balanceId
     * @return
     */
    @POST("payment/balanceInfo")
    @FormUrlEncoded
    Call<ResponseBody> balanceInfo(@Field("balanceId") long balanceId, @Field("type") int type);

    /**
     * 添加银行卡
     *
     * @param cardNo
     * @param name
     * @return
     */
    @POST("bankCard/addCard")
    @FormUrlEncoded
    Call<ResponseBody> addCard(@Field("cardNo") String cardNo, @Field("name") String name);

    /**
     * 获取银行卡
     *
     * @return
     */
    @POST("bankCard/cardList")
    Call<ResponseBody> cardList();

    /**
     * 删除银行卡
     *
     * @param cardId
     * @return
     */
    @POST("bankCard/delBankCard")
    @FormUrlEncoded
    Call<ResponseBody> cardList(@Field("cardId") long cardId);

    /**
     * 退款订单详情
     *
     * @return
     */
    @POST("personalrefundaftersale/selectRefundAfterSaleDetailsByOrderId")
    @FormUrlEncoded
    Call<ResponseBody> selectRefundAfterSaleDetailsByOrderId(@Field("orderId") long orderId);

    /**
     * 意见与反馈
     *
     * @return
     */
    @POST("personalfeedback/addFeedback")
    @FormUrlEncoded
    Call<ResponseBody> addFeedback(@Field("feedbackContent") String feedbackContent);

    /**
     * 微信支付
     *
     * @return
     */
    @POST(UserManger.PAY_SERVER + "wechatpayment/weChatOrderPayment")
    @FormUrlEncoded
    Call<ResponseBody> weChatOrderPayment(@Field("orderId") long orderId);

    /**
     * 微信支付查询
     *
     * @return
     */
    @POST(UserManger.PAY_SERVER + "wechatpayment/selectWeChatOrderByOrderId")
    @FormUrlEncoded
    Call<ResponseBody> selectWeChatOrderByOrderId(@Field("orderId") long orderId);

    /**
     * 部分微信支付查询
     *
     * @return
     */
    @POST(UserManger.PAY_SERVER + "wechatpayment/selectWeChatOrderByAdditionalId")
    @FormUrlEncoded
    Call<ResponseBody> selectWeChatOrderByAdditionalId(@Field("additionalId") long additionalId);

    /**
     * 充值微信支付查询
     *
     * @return
     */
    @POST(UserManger.PAY_SERVER + "wechatrecharge/selectWeChatOrderByPaymentId")
    @FormUrlEncoded
    Call<ResponseBody> selectWeChatOrderByPaymentId(@Field("paymentId") long paymentId);

    /**
     * 微信追加支付
     *
     * @return
     */
    @POST(UserManger.PAY_SERVER + "wechatpayment/weChatOrderPaymentAdditional")
    @FormUrlEncoded
    Call<ResponseBody> weChatOrderPaymentAdditional(@Field("orderPriceAddtionId") long orderPriceAddtionId);

    /**
     * 微信充值
     *
     * @return
     */
    @POST(UserManger.PAY_SERVER + "wechatrecharge")
    @FormUrlEncoded
    Call<ResponseBody> wechatrecharge(@Field("userId") long userId, @Field("moeny") int moeny);

    /**
     * 申请提现
     *
     * @param cardName
     * @param cardNo
     * @param moeny
     * @return
     */
    @POST("personalcenter/cashTakeOutApply")
    @FormUrlEncoded
    Call<ResponseBody> cashTakeOutApply(@Field("cardName") String cardName, @Field("cardNo") String cardNo, @Field("moeny") double moeny, @Field("password") String password);
}