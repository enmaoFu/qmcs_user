package com.biaoyuan.transfer.ui.mine;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.and.yzy.frame.config.SavePath;
import com.and.yzy.frame.util.AppUtils;
import com.and.yzy.frame.util.FileUtils;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.base.BaseAty;
import com.biaoyuan.transfer.config.UserManger;
import com.biaoyuan.transfer.util.JPushSetAliasUtil;
import com.biaoyuan.transfer.util.MyNumberFormat;
import com.suke.widget.SwitchButton;
import com.tencent.bugly.beta.Beta;

import butterknife.Bind;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 * @author ：enmaoFu
 * @title :设置页面
 * @create : 2017/4/24
 */
public class MineSettingActivity extends BaseAty {

    @Bind(R.id.mine_swc)
    SwitchButton mMineSwc;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tv_size)
    TextView mTvSize;
    @Bind(R.id.rl_clear)
    RelativeLayout mRlClear;
    @Bind(R.id.tv_version)
    TextView mTvVersion;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_setting;
    }

    @Override
    public void initData() {
        initToolbar(mToolbar, "设置");
        /*mMineSwc.setChecked(true);
        mMineSwc.isChecked();
        mMineSwc.toggle(true);
        mMineSwc.setShadowEffect(true);*/
        mTvVersion.setText("v" + AppUtils.getVersionName(this));
        mMineSwc.isChecked();
        mMineSwc.toggle();     //switch state
        mMineSwc.toggle(true);//switch without animation
        mMineSwc.setShadowEffect(false);//disable shadow effect
        mMineSwc.setEnableEffect(true);//disable the switch animation
        if (UserManger.getIsPush().equals("0")) {
            mMineSwc.setChecked(true);
        } else {
            mMineSwc.setChecked(false);
        }
        mMineSwc.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked) {
                    //开启推送
                    Log.v("print", "开启推送");
                    JPushInterface.init(MineSettingActivity.this);
                    JPushInterface.setDebugMode(false);
                    //判断是否登录
                    if (UserManger.isLogin()) {
                        Log.v("print", "已登录，进行初始化并且设置别名");
                        //设置别名
                        JPushSetAliasUtil jPushSetAliasUtil = new JPushSetAliasUtil(MineSettingActivity.this);
                        jPushSetAliasUtil.setAlias(UserManger.getUUid());
                    }


                    UserManger.setIsPush("0");
                    JPushInterface.resumePush(MineSettingActivity.this);
                } else {
                    //关闭推送
                    Log.v("print", "关闭推送");
                    UserManger.setIsPush("1");
                    JPushInterface.stopPush(MineSettingActivity.this);
                }
            }
        });

        mTvSize.setText(MyNumberFormat.m2(FileUtils.getFileOrFilesSize(SavePath.SAVE_PATH, 3)) + "M");
        mRlClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileUtils.delFolder(SavePath.SAVE_PATH);
                mTvSize.setText("0.00M");
            }
        });

    }

    @Override
    public boolean setIsInitRequestData() {
        return false;
    }

    @Override
    public void requestData() {

    }

    @OnClick({R.id.about_we, R.id.rl_update})
    @Override
    public void btnClick(View view) {
        switch (view.getId()) {
            case R.id.about_we:
                startActivity(MineAboutWeActivity.class, null);
                break;
            case R.id.rl_update:
                Beta.checkUpgrade(true, false);
                break;
        }
    }
}
