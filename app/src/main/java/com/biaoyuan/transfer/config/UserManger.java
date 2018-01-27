package com.biaoyuan.transfer.config;

import com.and.yzy.frame.config.HttpConfig;
import com.and.yzy.frame.config.UserInfoManger;
import com.and.yzy.frame.util.SPUtils;

/**
 * Title  :用户状态管理
 * Create : 2017/5/27
 * Author ：chen
 */

public class UserManger extends UserInfoManger {


    public static final String ADDRESS = "address";
    public static final String ISFIRST = "isFirst";

    public static final String ISDELIVER = "isDeliver";
    public static final String DELIVERSTATUS = "DeliverStatus";
    public static final String ACOUNT = "acount";
    public static final String PWD = "pwd";
    public static final String URL = "url";

    public static final String ISPUSH = "isPush";
    public static final String PHONE = "phone";


    // 微信支付
    public static final String WECHAT_APPID = "wxd8df062b65fba21f";
    /**
     * 每页显示条数
     */
    public static final String pageSize = "10";

    /**
     * 上传图片的最大值
     */
    public static final int MAXSIZE = 100 * 1024;
    //开通的城市街道
    public static final String SENDADDRESS = "sendAddress";

    public static final String LAT = "lat";
    public static final String LNG = "lng";


    /**
     * 微信支付服务
     */
    public static final String PAY_SERVER = "http://pay2.qmcs-china.com/";

    /**
     * 设置与获取是否开启推送
     * 0 开启
     * 1 关闭
     * 默认为 0
     *
     * @param isPush
     */
    public static void setIsPush(String isPush) {
        SPUtils spUtils = new SPUtils(FILE);
        spUtils.put(ISPUSH, isPush);
    }

    public static String getIsPush() {
        SPUtils spUtils = new SPUtils(FILE);
        return (String) spUtils.get(ISPUSH, "0");
    }


    public static void setURL(String url) {
        SPUtils spUtils = new SPUtils(FILE);
        spUtils.put(URL, url);
    }

    public static String getURL() {
        SPUtils spUtils = new SPUtils(FILE);
        return (String) spUtils.get(URL, HttpConfig.BASE_URL);
    }

    public static void setPhone(String phone) {
        SPUtils spUtils = new SPUtils(FILE);
        spUtils.put(PHONE, phone);
    }

    public static String getPhone() {
        SPUtils spUtils = new SPUtils(FILE);
        return (String) spUtils.get(PHONE, "");
    }

    public static void setAcount(String acount) {
        SPUtils spUtils = new SPUtils(FILE);
        spUtils.put(ACOUNT, acount);
    }

    public static void setPwd(String pwd) {
        SPUtils spUtils = new SPUtils(FILE);
        spUtils.put(PWD, pwd);
    }


    public static void setLat(String lat) {
        SPUtils spUtils = new SPUtils(FILE);
        spUtils.put(LAT, lat);
    }

    public static String getLat() {
        SPUtils spUtils = new SPUtils(FILE);
        return (String) spUtils.get(LAT, "-1");
    }

    public static String getAcount() {
        SPUtils spUtils = new SPUtils(FILE);
        return (String) spUtils.get(ACOUNT, "");
    }


    public static String getPwd() {
        SPUtils spUtils = new SPUtils(FILE);
        return (String) spUtils.get(PWD, "");
    }

    public static String getLng() {
        SPUtils spUtils = new SPUtils(FILE);
        return (String) spUtils.get(LNG, "-1");
    }

    public static void setLng(String lng) {
        SPUtils spUtils = new SPUtils(FILE);
        spUtils.put(LNG, lng);
    }

    /**
     * 设置是否是传送员
     *
     * @param isDeliver
     */
    public static void setDeliver(int isDeliver) {
        SPUtils spUtils = new SPUtils(FILE);
        spUtils.put(ISDELIVER, isDeliver);
    }

    /**
     * 设置传送员状态
     */
    public static void setDeliverStatus(int deliverStatus) {
        SPUtils spUtils = new SPUtils(FILE);
        spUtils.put(DELIVERSTATUS, deliverStatus);
    }

    /**
     * 得到是否是传送员
     */
    public static boolean isDeliver() {
        SPUtils spUtils = new SPUtils(FILE);
        int b = (int) spUtils.get(ISDELIVER, 0);
        if (b == 0) {
            return false;
        }
        return true;
    }

    /**
     * 得到是否是传送员
     */
    public static int getDeliver() {
        SPUtils spUtils = new SPUtils(FILE);
        int b = (int) spUtils.get(ISDELIVER, 0);
        return b;
    }

    /**
     * 得到传送员状态
     */
    public static boolean isDeliverOpen() {
        SPUtils spUtils = new SPUtils(FILE);
        int b = (int) spUtils.get(DELIVERSTATUS, 0);
        if (b == 1) {
            return false;
        }
        return true;
    }


    /**
     * 设置是否第一次打开
     *
     * @param isFirst
     */
    public static void setIsFirst(boolean isFirst) {
        SPUtils spUtils = new SPUtils(FILE);
        spUtils.put(ISFIRST, isFirst);
    }

    public static boolean getIsFirst() {
        SPUtils spUtils = new SPUtils(FILE);
        return (boolean) spUtils.get(ISFIRST, true);
    }


    public static void setAddress(String address) {
        SPUtils spUtils = new SPUtils(FILE);
        spUtils.put(ADDRESS, address);
    }

    public static void setSendAddressVersion(long version) {
        SPUtils spUtils = new SPUtils(FILE);
        spUtils.put(SENDADDRESS, version);
    }


    public static long getSendAddressVersion() {
        SPUtils spUtils = new SPUtils(FILE);
        return (long) spUtils.get(SENDADDRESS, 0L);
    }


    public static String getAddress() {
        SPUtils spUtils = new SPUtils(FILE);
        return (String) spUtils.get(ADDRESS, "11_11_11");
    }


}
