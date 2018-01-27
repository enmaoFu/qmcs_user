package com.biaoyuan.transfer.ui.login;

import android.Manifest;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.and.yzy.frame.util.MD5;
import com.and.yzy.frame.util.RetrofitUtils;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.base.BaseAty;
import com.biaoyuan.transfer.config.UserManger;
import com.biaoyuan.transfer.ui.MainAty;
import com.orhanobut.logger.Logger;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Title  :
 * Create : 2017/7/3
 * Author ：chen
 */

public class TestAty extends BaseAty {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;


    @Override
    public int getLayoutId() {
        return R.layout.test_layout;
    }

    @Override
    public void initData() {

       /* TreeMap<String,Integer> map=new TreeMap<>();

        map.put("name",1);
        map.put("pwd",1);
        map.put("lat",1);
        map.put("lng",1);
        map.put("singtime",1);
        map.put("secretsign",1);
        map.put("loginAddress",1);
        map.put("userTelphone",1);
        map.put("userLoginName",1);
        map.put("userCurrentAreaName",1);
        map.put("reportNewUserPasswprd",1);
        map.put("verificationCode",1);
        map.put("limitStart",1);
        map.put("additionalId",1);

        Logger.v("josn=="+ JSON.toJSONString(map));*/


        initToolbar(mToolbar, "选择服务器");
        opCheckPermission();

    }

    // 权限
    public void opCheckPermission() {

        Logger.v("sign==", MD5.getMD5("{\"type\":\"3\",signtime:\"1501828232026\"}$#@12345EFGHI6fghanc"));

        // 申请多个权限。
        AndPermission.with(this)
                .requestCode(200)
                .permission(Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.CAMERA,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_SMS,
                        Manifest.permission.READ_PHONE_STATE

                )
                // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框，避免用户勾选不再提示。
                .rationale(new RationaleListener() {
                               @Override
                               public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                                   // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                                   AndPermission.rationaleDialog(TestAty.this, rationale).show();
                               }
                           }
                )
                .send();


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 只需要调用这一句，其它的交给AndPermission吧，最后一个参数是PermissionListener。
        AndPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, listener);
    }

    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // 权限申请成功回调。

        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {


        }
    };

    @OnClick({R.id.tv_zd, R.id.tv_syn, R.id.tv_cc, R.id.tv_tz, R.id.tv_200, R.id.tv_online, R.id.tv_online_test})
    @Override
    public void btnClick(View view) {
        super.btnClick(view);
        String url = null;
        switch (view.getId()) {
            case R.id.tv_zd:
                url = "http://192.168.2.4:8080/u/";
                break;
            case R.id.tv_syn:
                url = "http://192.168.2.17:8080/qmcs/";
                break;
            case R.id.tv_cc:
                url = "http://192.168.2.33:8080/qmcs-u/";
                break;
            case R.id.tv_tz:
                url = "http://192.168.2.21:9999/";
                break;
            case R.id.tv_200:
                url = "http://192.168.2.200:8080/qmcs2-u-api/";
                break;
            case R.id.tv_online:
                url = "http://u2.qmcs-china.com/";
                break;
            case R.id.tv_online_test:
                url = "http://www2.qmcs-china.com/qmcs2-u-api/";
                break;
        }
        UserManger.setURL(url);
        RetrofitUtils.init(url);
        setHasAnimiation(false);

        startActivity(MainAty.class, null);

        overridePendingTransition(R.anim.aty_in, R.anim.activity_alpha_out);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 2000);
    }

    @Override
    public void requestData() {

    }
}
