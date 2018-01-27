package com.biaoyuan.transfer.http;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Title  :
 * Create : 2017/5/27
 * Author ：chen
 */

public interface Login {


    /**
     * 登录
     *
     * @param loginName
     * @param password
     * @return
     */
    @POST("login")
    @FormUrlEncoded
    Call<ResponseBody> login(@Field("loginName") String loginName, @Field("password") String password,
                             @Field("loginLng") double loginLng,@Field("loginLat") double loginLat,
                             @Field("loginAddress") String loginAddress,@Field("loginDeviceCode") String loginDeviceCode
    );

    /**
     * 发送验证码
     */
    @POST("unauth/sendSms")
    @FormUrlEncoded
    Call<ResponseBody> sendSms(@Field("userTelphone") String userTelphone, @Field("sendType") String sendType);


    /**
     * 注册第一步
     *
     * @param userLoginName
     * @param userTelphone
     * @param verificationCode
     * @return
     */
    @POST("unauth/mobileVerification")
    @FormUrlEncoded
    Call<ResponseBody> mobileVerification(@Field("userLoginName") String userLoginName, @Field("userTelphone") String userTelphone
            , @Field("verificationCode") String verificationCode
    );

    /**
     * 注册第二步
     */
    @POST("unauth/register")
    @FormUrlEncoded
    Call<ResponseBody> register(@Field("userLoginName") String userLoginName, @Field("userTelphone") String userTelphone
            , @Field("userPassword") String userPassword, @Field("reportUserPassword") String reportUserPassword,
                                @Field("loginLng") double loginLng,@Field("loginLat") double loginLat,
    @Field("loginAddress") String loginAddress,@Field("loginDeviceCode") String loginDeviceCode,@Field("userCurrentAreaName") String userCurrentAreaName
    );

    /**
     * 退出登录
     */
    @POST("unauth/logout")
    Call<ResponseBody> logout();
    /**
     * 找回密码第一步
     */
    @POST("unauth/findPasswordVerification")
    @FormUrlEncoded
    Call<ResponseBody> findPasswordVerification(@Field("userTelphone") String userTelphone, @Field("verificationCode") String verificationCode
    );

    /**
     * 找回密码第二步
     */
    @POST("unauth/findPassword")
    @FormUrlEncoded
    Call<ResponseBody> findPassword(@Field("userTelphone") String userTelphone, @Field("newUserPassword") String newUserPassword
                                   ,@Field("reportNewUserPasswprd") String reportNewUserPasswprd
    );

}
