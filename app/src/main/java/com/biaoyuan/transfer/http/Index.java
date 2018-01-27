package com.biaoyuan.transfer.http;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/7/6.
 */

public interface Index {

    /**
     * 我的消息列表
     * @param pageSize
     * @param targetPage
     * @return
     */
    @POST("u/userMessage")
    @FormUrlEncoded
    Call<ResponseBody> userMessage(@Field("pageSize") String pageSize, @Field("targetPage") int targetPage);

    /**
     * 获取订单最新状态
     * @param orderId
     * @return
     */
    @POST("u/getOrderStatus")
    @FormUrlEncoded
    Call<ResponseBody> getOrderStatus(@Field("orderId") long orderId);

    /**
     * 消息删除
     * @param umessageId
     * @return
     */
    @POST("u/deleteMessage")
    @FormUrlEncoded
    Call<ResponseBody> deleteMessage(@Field("umessageId") String umessageId);

}
