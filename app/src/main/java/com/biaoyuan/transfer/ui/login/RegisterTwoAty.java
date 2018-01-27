package com.biaoyuan.transfer.ui.login;

import android.Manifest;
import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.amap.api.location.AMapLocation;
import com.and.yzy.frame.util.AppManger;
import com.and.yzy.frame.util.RetrofitUtils;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.base.BaseAty;
import com.biaoyuan.transfer.config.UserManger;
import com.biaoyuan.transfer.http.Login;
import com.biaoyuan.transfer.ui.MainAty;
import com.biaoyuan.transfer.util.AppJsonUtil;
import com.biaoyuan.transfer.util.JPushSetAliasUtil;
import com.biaoyuan.transfer.util.MyLocationUtil;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.List;
import java.util.UUID;

import butterknife.Bind;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Title  : 用户注册第二步页面
 * Create : 2017/5/23
 * Author ：enmaoFu
 */
public class RegisterTwoAty extends BaseAty {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    //输入的登录密码
    @Bind(R.id.input_login_pwd)
    EditText inputLoginPwd;

    //再次输入的登录密码
    @Bind(R.id.input_login_again_pwd)
    EditText inputLoginAgainPwd;

    //是否同意协议的图片选择
    @Bind(R.id.is_agree)
    RelativeLayout isAgree;
    @Bind(R.id.is_img_agree)
    ImageView isImgAgerss;
    //默认为没有同意
    private boolean isAgreeFlag = false;
    private String mGetLoginPwd;
    private String mGetLoginAgainPwd;
    private String mName;
    private String mPhone;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register_two;
    }

    @Override
    public void initData() {
        initToolbar(mToolbar, "密码设置");
        AppManger.getInstance().killActivity(RegisterOneAty.class);
    }

    @Override
    public boolean setIsInitRequestData() {
        return false;
    }

    @Override
    public void requestData() {
    }

    @OnClick({R.id.btn_register, R.id.is_agree})
    @Override
    public void btnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                //判断是否点击同意了全民传送协议
                if (isAgreeFlag) {
                    mGetLoginPwd = inputLoginPwd.getText().toString().trim();
                    mGetLoginAgainPwd = inputLoginAgainPwd.getText().toString().trim();
                    if (mGetLoginPwd.length() < 8) {
                        showErrorToast(getResources().getString(R.string.pwd_tip));
                        inputLoginPwd.requestFocus();
                        return;
                    }
                    if (mGetLoginAgainPwd.length() < 8) {
                        showErrorToast(getResources().getString(R.string.r_pwd_tip));
                        inputLoginAgainPwd.requestFocus();
                        return;

                    }
                    if (!mGetLoginPwd.equals(mGetLoginAgainPwd)) {
                        showErrorToast(getResources().getString(R.string.pwd_not_same));
                        inputLoginAgainPwd.requestFocus();
                        return;

                    }

                    //注册
                    mName = getIntent().getStringExtra("name");
                    mPhone = getIntent().getStringExtra("phone");
                    opCheckPermission();


                } else {
                    showErrorToast("请同意《全民传送协议》");
                }
                break;
            case R.id.is_agree:
                //判断是否点击同意了全民传送协议
                if (!isAgreeFlag) {
                    isImgAgerss.setImageResource(R.drawable.btn_select_active);
                } else {
                    isImgAgerss.setImageResource(R.drawable.btn_select_normal);
                }
                isAgreeFlag = !isAgreeFlag;
                break;
        }
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what) {
            case 1:
                showToast("注册成功");
                //注册成功
                AppManger.getInstance().killAllActivity();
                //记录登录状态
                UserManger.setIsLogin(true);
                UserManger.setAcount(mPhone);
                UserManger.setPwd(mGetLoginPwd);

                //保存token uuid
                UserManger.setToken(AppJsonUtil.getString(AppJsonUtil.getString(result, "data"), "token"));
                UserManger.setUUid(AppJsonUtil.getString(AppJsonUtil.getString(result, "data"), "uuid"));
                UserManger.setPhone(String.valueOf(AppJsonUtil.getLong(AppJsonUtil.getString(result, "data"), "userTelphone")));

         //       RetrofitUtils.init( UserManger.getURL());

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

                UserManger.setIsPush("0");
                JPushInterface.init(this);
                JPushInterface.setDebugMode(false);
                JPushSetAliasUtil jPushSetAliasUtil = new JPushSetAliasUtil(this);
                jPushSetAliasUtil.setAlias(UserManger.getUUid());

                break;

        }
    }

    // 权限
    public void opCheckPermission() {


        // 申请多个权限。
        AndPermission.with(this)
                .requestCode(200)
                .permission(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE
                )
                // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框，避免用户勾选不再提示。
                .rationale(new RationaleListener() {
                               @Override
                               public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                                   // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                                   AndPermission.rationaleDialog(RegisterTwoAty.this, rationale).show();
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

            if (requestCode == 200) {


                TelephonyManager tm = (TelephonyManager) getBaseContext()
                        .getSystemService(Context.TELEPHONY_SERVICE);

                String tmDevice = "" + tm.getDeviceId();
                String tmSerial = "" + tm.getSimSerialNumber();
                String androidId = ""
                        + android.provider.Settings.Secure.getString(
                        getContentResolver(),
                        android.provider.Settings.Secure.ANDROID_ID);
                UUID deviceUuid = new UUID(androidId.hashCode(),
                        ((long) tmDevice.hashCode() << 32)
                                | tmSerial.hashCode());
                System.out.println(deviceUuid.toString().length());
                final String uniqueId = deviceUuid.toString();


                showLoadingDialog(null);

                new MyLocationUtil(RegisterTwoAty.this, new MyLocationUtil.MyLocationListener() {
                    @Override
                    public void sussessLocation(double lat, double lon, AMapLocation location) {


                        doHttp(RetrofitUtils.createApi(Login.class).register(mName, mPhone, mGetLoginPwd, mGetLoginAgainPwd,
                                lon, lat, location.getCity() + location.getCountry() + location.getStreet(), uniqueId,
                        location.getCity()), 1);
                    }

                    @Override
                    public void error() {
                        doHttp(RetrofitUtils.createApi(Login.class).register(mName, mPhone, mGetLoginPwd, mGetLoginAgainPwd,
                                0, 0, "", uniqueId, null
                        ), 1);
                    }
                });


            }


        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            showLoadingDialog(null);
            doHttp(RetrofitUtils.createApi(Login.class).register(mName, mPhone, mGetLoginPwd, mGetLoginAgainPwd,
                    0, 0, "", "",null
            ), 1);

        }
    };
}
