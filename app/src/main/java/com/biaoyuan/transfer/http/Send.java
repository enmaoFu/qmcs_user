package com.biaoyuan.transfer.http;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Title  :
 * Create : 2017/6/1
 * Author ：enmaoFu
 */

public interface Send {

    /**
     * 查找常用地址
     *
     * @return
     */
    @POST("u/addressList")
    @FormUrlEncoded
    Call<ResponseBody> addressList(@Field("isSend") int type);

    /**
     * 删除常用地址
     *
     * @return
     */
    @POST("u/updateAddressIsDel")
    @FormUrlEncoded
    Call<ResponseBody> updateAddressIsDel(@Field("id") int id);

    /**
     * 设置默认地址
     *
     * @return
     */
    @POST("u/upDefaultAddress")
    @FormUrlEncoded
    Call<ResponseBody> upDefaultAddress(@Field("isSend") int type, @Field("id") long id);

    /**
     * 计算价格
     *
     * @return
     */
    @POST("u/orderFee")
    @FormUrlEncoded
    Call<ResponseBody> orderFee(@Field("type") int type, @Field("size") int size, @Field("weight") int weight
            , @Field("distance") double distance, @Field("sendAreaCode") int sendAreaCode, @Field("endAreaCode") int endAreaCode
    );

    /**
     * 添加常用地址
     */
    @POST("u/addAddress")
    @FormUrlEncoded
    Call<ResponseBody> addAddress(@Field("name") String name, @Field("phone") long phone,
                                  @Field("address") String address, @Field("lng") double lng
            , @Field("lat") double lat, @Field("type") int type, @Field("areaCode") int areaCode, @Field("areaId") long areaId
    );

    /**
     * 编辑常用地址
     */
    @POST("u/upUserAddress")
    @FormUrlEncoded
    Call<ResponseBody> upUserAddress(@Field("name") String name, @Field("phone") long phone,
                                     @Field("address") String address, @Field("lng") double lng
            , @Field("lat") double lat, @Field("id") int id, @Field("areaCode") int areaCode, @Field("areaId") long areaId
    );

    /**
     * 生成订单
     */
    @POST("u/generateOrder")
    @FormUrlEncoded
    Call<ResponseBody> generateOrder(@Field("senderName") String senderName, @Field("senderTel") long senderTel,
                                     @Field("sendAreaId") int sendAreaId, @Field("sendAddr") String sendAddr
            , @Field("sendLng") double sendLng, @Field("sendLat") double sendLat,

                                     @Field("receiverName") String receiverName, @Field("receiverTel") long receiverTel,
                                     @Field("receiverAreaId") int receiverAreaId, @Field("receiverAddr") String receiverAddr,
                                     @Field("receiverLng") double receiverLng, @Field("receiverLat") double receiverLat,

                                     @Field("goodsSize") int goodsSize, @Field("goodsWeight") int goodsWeight,
                                     @Field("goodsType") int goodsType, @Field("requiredTime") long requiredTime,
                                     @Field("totalPrice") double totalPrice, @Field("distance") double distance
    );


    /**
     * 得到开通城市
     */
    @POST("unauth/getAddress")
    Call<ResponseBody> getAddress();

    /**
     * 得到开通城市版本号
     */
    @POST("unauth/getAddressVersion")
    Call<ResponseBody> getAddressVersion();

    /**
     * 获取系统设置的订单类型及参数
     */
    @POST("setting/getOrderType")
    Call<ResponseBody> getOrderType();

    /**
     * 判断支付密码是否为空
     */
    @POST("u/getPayPassword")
    Call<ResponseBody> getPayPassword();

    /**
     * 设置支付密码
     */
    @POST("u/setPayPassword")
    @FormUrlEncoded
    Call<ResponseBody> setPayPassword(@Field("pwd") String pwd);

    /**
     * 余额支付
     */
    @POST("u/payOrder")
    @FormUrlEncoded
    Call<ResponseBody> payOrder(@Field("pwd") String pwd, @Field("orderId") long orderNo, @Field("money") double money, @Field("type") int type);

    /**
     * 我的发件
     */
    @POST("personOrder/orders")
    @FormUrlEncoded
    Call<ResponseBody> orders(@Field("type") int type, @Field("limitStart") int limitStart, @Field("pageSize") int pageSize);

    /**
     * 取消订单
     */
    @POST("personOrder/orderCancel")
    @FormUrlEncoded
    Call<ResponseBody> orderCancel(@Field("orderId") long orderId);

    /**
     * 订单详情
     */
    @POST("personOrder/orderInfo")
    @FormUrlEncoded
    Call<ResponseBody> orderInfo(@Field("orderId") long orderId);

    /**
     * 增加评论
     */
    @POST("personOrder/orderComment")
    @FormUrlEncoded
    Call<ResponseBody> orderComment(@Field("orderId") long orderId, @Field("score") int score,
                                    @Field("speed") byte speed, @Field("service") byte service, @Field("content") String content);


    /**
     * 消息推送给收件网点所有员工
     *
     * @param orderId
     * @return
     */
    @POST("u/pushMsg")
    @FormUrlEncoded
    Call<ResponseBody> pushMsg(@Field("orderId") long orderId);

}
