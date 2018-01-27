package com.biaoyuan.transfer.app;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.and.yzy.frame.application.BaseApplication;
import com.biaoyuan.transfer.config.UserManger;
import com.biaoyuan.transfer.util.JPushSetAliasUtil;

import cn.jpush.android.api.JPushInterface;

/**
 * Title  :
 * Create : 2017/4/24
 * Author ：chen
 */

public class MyApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        //开启推送
        if (UserManger.getIsPush().equals("0")) {
            JPushInterface.init(this);
            JPushInterface.setDebugMode(false);
            //判断是否登录
            if (UserManger.isLogin()) {
                Log.v("print", "已登录，进行初始化并且设置别名");
                //设置别名
                JPushSetAliasUtil jPushSetAliasUtil = new JPushSetAliasUtil(this);
                jPushSetAliasUtil.setAlias(UserManger.getUUid());
            }

        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
