package com.biaoyuan.transfer.http;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Title  :
 * Create : 2017/6/1
 * Author ：chen
 */

public interface IndexAddress {

    /**
     * 通过经纬度获取5千内的网点
     */
    @POST("unauth/getAffilliate")
    @FormUrlEncoded
    Call<ResponseBody> getAffilliate(@Field("lng") double lng, @Field("lat") double lat);

    /**
     * 通过城市编码获取
     */
    @POST("unauth/home")
    @FormUrlEncoded
    Call<ResponseBody> home(@Field("code") int code);

    /**
     * 得到开通城市
     */
    @POST("unauth/getAddress")
    @FormUrlEncoded
    Call<ResponseBody> getAddress(@Field("type") int type);
}
