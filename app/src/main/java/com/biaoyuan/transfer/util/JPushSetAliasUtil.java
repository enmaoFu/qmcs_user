package com.biaoyuan.transfer.util;

import android.content.Context;
import android.os.Handler;
import android.util.Log;


import com.biaoyuan.transfer.config.UserManger;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by Administrator on 2017/7/4.
 */

public class JPushSetAliasUtil {

    private Context context;

    public JPushSetAliasUtil(Context context) {
        this.context = context;
    }

    public void setAlias(String alias) {

        Log.v("print","别名" + alias);
        // 调用 Handler 来异步设置别名
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));

    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs ;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.v("print",logs);
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    break;
                /*case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.v("print",logs);
                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;*/
                default:
                    logs = "Failed with errorCode = " + code;
                    Log.v("print",logs);
            }
            //ExampleUtil.showToast(logs, context);
        }
    };
    private static final int MSG_SET_ALIAS = 1001;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    Log.v("print","Set alias in handler.");
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(context,
                            (String) msg.obj,
                            null,
                            mAliasCallback);
                    break;
                default:
                    Log.v("print","Unhandled msg - " + msg.what);
            }
        }
    };

}
