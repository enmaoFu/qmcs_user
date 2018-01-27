package com.biaoyuan.transfer.domain;

import android.text.TextUtils;

import com.and.yzy.frame.util.DateTool;

/**
 * Title  :
 * Create : 2017/6/21
 * Author ：chen
 */

public class WaitSignWuniuItem {

    /**
     * msg : 您的订单开始处理
     * tel : 0
     * time : 1498803078243
     */

    private String msg;
    private long tel;
    private long time;
    /**
     * time : 2017-07-10 13:14:59
     * ftime : 2017-07-10 13:14:59
     * context : [江北] [重庆市] [江北]的派件已签收 感谢使用中通快递,期待再次为您服务!
     */

    private String ftime;
    private String context;


    public String getMsg() {
        if (msg == null) {
            if (!TextUtils.isEmpty(context)) {

                return context;
            }

            return "暂无详细信息";
        }
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getTel() {
        return tel;
    }

    public void setTel(long tel) {
        this.tel = tel;
    }

    public long getTime() {
        if (time == 0) {
            if (!TextUtils.isEmpty(ftime)) {

                return DateTool.strTimeToTimestamp(ftime, "yyyy-MM-dd HH:mm:ss");
            }

            return System.currentTimeMillis();

        }


        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getFtime() {
        return ftime;
    }

    public void setFtime(String ftime) {
        this.ftime = ftime;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
