package com.biaoyuan.transfer.http;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Title  :传送
 * Create : 2017/6/14
 * Author ：chen
 */

public interface Transfer {

    /**
     * 发布行程
     *
     * @return
     */
    @POST("u/addPath")
    @FormUrlEncoded
    Call<ResponseBody> addPath(@Field("pathDepartureTime") String pathDepartureTime, @Field("pathDepartureId") String pathDepartureId
            , @Field("pathDestinationId") String pathDestinationId
    );

    /**
     * 动态条件筛选包裹
     *
     * @return
     */
    @POST("package/packageView")
    @FormUrlEncoded
    Call<ResponseBody> packageView(@Field("sortNumber") String sortNumber, @Field("pageSize") String pageSize
            , @Field("targetPage") String targetPage, @Field("myLongitude") String myLongitude, @Field("myLatitude") String myLatitude
            , @Field("departureTime") String departureTime, @Field("outregionId") String outregionId, @Field("outstreetId") String outstreetId, @Field("entregionId") String entregionId
            , @Field("entstreetId") String entstreetId, @Field("filterSort") String filterSort
    );

    /**
     * 行程列表
     *
     * @return
     */
    @POST("u/pathView")
    @FormUrlEncoded
    Call<ResponseBody> pathview(@Field("pageSize") String pageSize, @Field("targetPage") String targetPage);

    /**
     * 包裹详情
     *
     * @return
     */
    @POST("package/packageObj")
    @FormUrlEncoded
    Call<ResponseBody> packageObj(@Field("publishId") String publishId);

    /**
     * 传送员抢单
     *
     * @return
     */
    @POST("package/updatePackage")
    @FormUrlEncoded
    Call<ResponseBody> updatePackage(@Field("publishId") String publishId);

    /**
     * 是否是传送员
     *
     * @return
     */
    @POST("unauth/isDeliver")
    @FormUrlEncoded
    Call<ResponseBody> isDeliver(@Field("isDeliver") String isDeliver);


    /**
     * 我的传送
     *
     * @return
     */
    @POST("transmit/transmit")
    @FormUrlEncoded
    Call<ResponseBody> transmit(@Field("pageSize") int pageSize, @Field("limitStart") int limitStart, @Field("type") int type);

    /**
     * 传送员扫描取件
     *
     * @return
     */
    @POST("transmit/takePackage")
    @FormUrlEncoded
    Call<ResponseBody> takePackage(@Field("packageCode") String packageCode, @Field("packageId") long packageId, @Field("lat") double lat, @Field("lng") double lng);

    /**
     * 我的传送详情
     *
     * @return
     */
    @POST("transmit/packageInfo")
    @FormUrlEncoded
    Call<ResponseBody> packageInfo(@Field("type") int type, @Field("packageId") long packageId);

    /**
     * 我的传送异常订单详情
     *
     * @return
     */
    @POST("transmit/excepPackageInfo")
    @FormUrlEncoded
    Call<ResponseBody> excepPackageInfo(@Field("type") int type, @Field("packageId") long packageId);

    /**
     * 我的传送删除
     *
     * @return
     */
    @POST("transmit/delCarryById")
    @FormUrlEncoded
    Call<ResponseBody> delCarryById(@Field("packageId") long packageId);


}
