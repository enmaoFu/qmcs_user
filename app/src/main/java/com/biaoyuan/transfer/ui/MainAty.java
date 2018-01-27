package com.biaoyuan.transfer.ui;

import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.and.yzy.frame.base.BaseFrameAty;
import com.and.yzy.frame.base.BaseFrameLazyFgt;
import com.and.yzy.frame.util.AppManger;
import com.and.yzy.frame.util.RetrofitUtils;
import com.and.yzy.frame.view.other.BottomMenuView;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.config.UserManger;
import com.biaoyuan.transfer.http.Send;
import com.biaoyuan.transfer.ui.index.IndexFgt;
import com.biaoyuan.transfer.ui.mine.MineFgt;
import com.biaoyuan.transfer.ui.send.SendFgt;
import com.biaoyuan.transfer.ui.transfer.TransferFgt;
import com.biaoyuan.transfer.util.AppJsonUtil;
import com.biaoyuan.transfer.util.ChooseAddresDataHelper;
import com.biaoyuan.transfer.util.ChooseSendAddresDataHelper;
import com.tencent.bugly.beta.Beta;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 主界面
 */
public class MainAty extends BaseFrameAty {

    @Bind(R.id.menu)

    BottomMenuView mMenu;


    public static List<RadioButton> radioButtons;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public boolean setIsInitRequestData() {
        return false;
    }


    /**
     * 解决切换到HomeActivity界面的发送界面黑屏问题
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {


        radioButtons = mMenu.getRadioButtons();
        //如果程序重启发生崩溃重新载入城市数据
        if (!ChooseAddresDataHelper.isBegin) {
            new ChooseAddresDataHelper().initAddressData();
        }
        if (!ChooseSendAddresDataHelper.isBegin) {
            //获取城市地址版本号
            doHttp(RetrofitUtils.createApi(Send.class).getAddressVersion(), 1);
        }


        List<Drawable> drawables = new ArrayList<>();
        drawables.add(getResources().getDrawable(R.drawable.selector_tab_index));
        drawables.add(getResources().getDrawable(R.drawable.selector_tab_send));
        drawables.add(getResources().getDrawable(R.drawable.selector_tab_transfer));
        drawables.add(getResources().getDrawable(R.drawable.selector_tab_mine));


        List<String> menus = new ArrayList<>();
        menus.add("首页");
        menus.add("发件");
        menus.add("传送");
        menus.add("我的");


        List<BaseFrameLazyFgt> fgts = new ArrayList<>();
        fgts.add(new IndexFgt());
        fgts.add(new SendFgt());
        fgts.add(new TransferFgt());
        fgts.add(new MineFgt());

        mMenu.init(drawables, R.color.selector_main_rb_text, fgts, menus, getSupportFragmentManager());

        mMenu.getViewPager().setOffscreenPageLimit(4);
        mMenu.setListener(new BottomMenuView.CheckChangeListener() {
            @Override
            public void onCheckChanged(ViewPager pager, RadioGroup group, int checkedPosition) {
                switch (checkedPosition) {
                    case 0:
                        pager.setCurrentItem(0);
                        break;
                    case 1:
                        pager.setCurrentItem(1);
                        break;
                    case 2:
                        pager.setCurrentItem(2);
                        break;
                    case 3:
                        pager.setCurrentItem(3);
                        break;
                }
            }
        });


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Beta.checkUpgrade(false, false);
            }
        }, 3000);

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        // super.onSaveInstanceState(outState);
    }

    @Override
    public void requestData() {

    }

    @Override
    protected void onPause() {
        super.onPause();

        mMenu.getFgts().get(0).onUserInvisible();

    }

    @Override
    public void btnClick(View view) {

    }

    @Override
    public void onError(Call<ResponseBody> call, Throwable t, int what) {
        super.onError(call, t, what);
        ChooseSendAddresDataHelper.isBegin = false;
    }

    long version;

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what) {
            case 1:
                version = AppJsonUtil.getLong(result, "data");
                if (AppJsonUtil.getLong(result, "data") != UserManger.getSendAddressVersion()) {

                    doHttp(RetrofitUtils.createApi(Send.class).getAddress(), 2);
                } else {
                    new ChooseSendAddresDataHelper().initAddressData(null, version);
                }
                break;
            case 2:
                new ChooseSendAddresDataHelper().initAddressData(result, version);
                break;


        }
    }

    private long oldTime;

    @Override
    public void onBackPressed() {
        //        super.onBackPressed();
        long newtime = System.currentTimeMillis();
        if (newtime - oldTime > 3000) {
            oldTime = newtime;
            showToast("连按两次返回桌面");
        } else {
            setHasAnimiation(false);
            AppManger.getInstance().AppExit(this);
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.aty_in, R.anim.aty_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ChooseAddresDataHelper.clearData();
        ChooseSendAddresDataHelper.clearData();
        radioButtons.clear();
        MineFgt.isChangeImage = true;
    }


}
