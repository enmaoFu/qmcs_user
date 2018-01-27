package com.biaoyuan.transfer.ui.transfer;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.and.yzy.frame.util.DateTool;
import com.and.yzy.frame.util.RetrofitUtils;
import com.and.yzy.frame.view.dialog.MaterialDialog;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.base.BaseAty;
import com.biaoyuan.transfer.config.UserManger;
import com.biaoyuan.transfer.domain.TransferResultInfo;
import com.biaoyuan.transfer.http.Mine;
import com.biaoyuan.transfer.http.Transfer;
import com.biaoyuan.transfer.ui.login.LoginAty;
import com.biaoyuan.transfer.ui.mine.MineAuthenticationActivity;
import com.biaoyuan.transfer.ui.mine.MineTransferStateAty;
import com.biaoyuan.transfer.util.AppJsonUtil;
import com.biaoyuan.transfer.util.MyNumberFormat;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Title  : 传送单详情页面
 * Create : 2017/4/26
 * Author ：enmaoFu
 */
public class TransferOrderDetailsActivity extends BaseAty {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tv_size)
    TextView mTvSize;
    @Bind(R.id.tv_start_name)
    TextView mTvStartName;
    @Bind(R.id.tv_start_phone)
    TextView mTvStartPhone;
    @Bind(R.id.tv_start_address)
    TextView mTvStartAddress;
    @Bind(R.id.tv_end_name)
    TextView mTvEndName;
    @Bind(R.id.tv_end_phone)
    TextView mTvEndPhone;
    @Bind(R.id.tv_end_address)
    TextView mTvEndAddress;
    @Bind(R.id.tv_start_time)
    TextView mTvStartTime;
    @Bind(R.id.tv_end_time)
    TextView mTvEndTime;
    @Bind(R.id.tv_price)
    TextView mTvPrice;

    private String publishId;

    private long startPhone;
    private long endPhone;
    private int type = 0;


    private double startLat;
    private double startLng;
    private double endLat;
    private double endLng;

    @Override
    public int getLayoutId() {
        return R.layout.activity_transfer_order_details;
    }

    @Override
    public boolean setIsInitRequestData() {
        return true;
    }

    @Override
    public void initData() {
        initToolbar(mToolbar, "包裹详情");
        publishId = getIntent().getStringExtra("publishId");

    }

    @Override
    public void requestData() {
        showLoadingContentDialog();
        doHttp(RetrofitUtils.createApi(Transfer.class).packageObj(publishId), 1);

    }

    @OnClick({R.id.tv_commit, R.id.tv_start_phone, R.id.tv_end_phone, R.id.ll_start_address, R.id.ll_end_address})
    @Override
    public void btnClick(View view) {
        super.btnClick(view);
        switch (view.getId()) {
            case R.id.tv_start_phone:
                type = 0;
                opCheckPermission();
                break;
            case R.id.tv_end_phone:
                type = 1;
                opCheckPermission();
                break;
            case R.id.tv_commit:
                //先判断是否登录
                if (UserManger.isLogin()) {

                    doHttp(RetrofitUtils.createApi(Transfer.class).isDeliver(UserManger.getDeliver() + ""), 4);

                } else {
                    startActivity(LoginAty.class, null);
                }
                break;
            case R.id.ll_start_address:
                //点击取件网点
                Bundle bundle = new Bundle();

                bundle.putDouble("startLat", Double.parseDouble(UserManger.getLat()));
                bundle.putDouble("endLat", startLat);
                bundle.putDouble("startLng", Double.parseDouble(UserManger.getLng()));
                bundle.putDouble("endLng", startLng);


                startActivity(TransferMapAty.class, bundle);

                break;
            case R.id.ll_end_address:
                //点击目的地网点


                Bundle bun = new Bundle();

                bun.putDouble("startLat", Double.parseDouble(UserManger.getLat()));
                bun.putDouble("endLat", endLat);
                bun.putDouble("startLng", Double.parseDouble(UserManger.getLng()));
                bun.putDouble("endLng", endLng);
                startActivity(TransferMapAty.class, bun);


                break;


        }
    }

    // 权限
    public void opCheckPermission() {


        // 申请多个权限。
        AndPermission.with(this)
                .requestCode(200)
                .permission(Manifest.permission.CALL_PHONE
                )
                // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框，避免用户勾选不再提示。
                .rationale(new RationaleListener() {
                               @Override
                               public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                                   // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                                   AndPermission.rationaleDialog(TransferOrderDetailsActivity.this, rationale).show();
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


                if (type == 0) {
                    new MaterialDialog(TransferOrderDetailsActivity.this)
                            .setMDMessage("是否立即拨打发件网点电话?")
                            .setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                                @Override
                                public void dialogBtnOnClick() {
                                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + startPhone));
                                    startActivity(intent);
                                }
                            }).show();
                } else {

                    new MaterialDialog(TransferOrderDetailsActivity.this)
                            .setMDMessage("是否立即拨打目的网点电话?")
                            .setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                                @Override
                                public void dialogBtnOnClick() {
                                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + endPhone));
                                    startActivity(intent);
                                }
                            }).show();

                }


            }

        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // initMap();
            showErrorToast("未授权");

        }
    };

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what) {
            case 1:
                TransferResultInfo resultInfo = AppJsonUtil.getObject(result, "packageobj", TransferResultInfo.class);
                mTvSize.setText("最长边≤" + resultInfo.getPackageSize() + "cm   " + resultInfo.getPackageWeight() + "kg");
                mTvStartName.setText(resultInfo.getOutBasicAreaName() + resultInfo.getOutBasicName());
                mTvStartPhone.setText(resultInfo.getOutBasicTelphone() + "");
                mTvStartAddress.setText(resultInfo.getOutBasicAddress());

                mTvEndName.setText(resultInfo.getEntBasicName());
                mTvEndPhone.setText(resultInfo.getEntBasicTelphone() + "");
                mTvEndAddress.setText(resultInfo.getEntBasicAreaName() + resultInfo.getEntBasicAddress());


                String startTime = DateTool.getTimeType(resultInfo.getPublishReqPickupTime());
                String endTime = DateTool.getTimeType(resultInfo.getPublishReqDelivTime());

                if (startTime == null) {
                    mTvStartTime.setText(DateTool.timesToStrTime(resultInfo.getPublishReqPickupTime() + "", "yyyy.MM.dd HH:mm"));
                } else {
                    mTvStartTime.setText(startTime + DateTool.timesToStrTime(resultInfo.getPublishReqPickupTime() + "", "HH:mm") + "之前");
                }

                if (endTime == null) {
                    mTvEndTime.setText(DateTool.timesToStrTime(resultInfo.getPublishReqDelivTime() + "", "yyyy.MM.dd HH:mm"));
                } else {
                    mTvEndTime.setText(endTime + DateTool.timesToStrTime(resultInfo.getPublishReqDelivTime() + "", "HH:mm") + "之前");
                }
                //
                startPhone = resultInfo.getOutBasicTelphone();
                endPhone = resultInfo.getEntBasicTelphone();

                mTvPrice.setText("¥" + MyNumberFormat.m2(resultInfo.getPublishReward()));


                startLat = resultInfo.getOutBasicLat();
                startLng = resultInfo.getOutBasicLng();

                endLat = resultInfo.getEntBasicLat();
                endLng = resultInfo.getEntBasicLng();


                break;
            case 2:

                Bundle bundle = new Bundle();
                bundle.putString("publishId", publishId);
                startActivity(TransferGetSuccessActivity.class, bundle);

                break;

            case 3:
                int state = AppJsonUtil.getInt(AppJsonUtil.getString(result, "data"), "reviewStatus");


                Bundle bb = new Bundle();
                switch (state) {
                    case 0:
                        //申请中
                        bb.putInt("type", MineTransferStateAty.TYPE_COMMIT);

                        break;
                    case 1:

                        //申请成功
                        bb.putInt("type", MineTransferStateAty.TYPE_SUCCESS);
                        break;
                    case 2:
                        //被拒绝

                        bb.putInt("type", MineTransferStateAty.TYPE_ERROR);

                        break;
                }

                startActivity(MineTransferStateAty.class, bb);

                break;
            case 4:
                UserManger.setDeliver(AppJsonUtil.getInt(AppJsonUtil.getString(result, "data"), "isDeliver"));
                UserManger.setDeliverStatus(AppJsonUtil.getInt(AppJsonUtil.getString(result, "data"), "DeliverStatus"));
                //判断是否是传送员
                if (UserManger.isDeliver()) {
                    //判断传送员状态
                    if (UserManger.isDeliverOpen()) {

                        //抢单
                        isShowOnFailureToast = true;
                        showLoadingDialog(null);
                        doHttp(RetrofitUtils.createApi(Transfer.class).updatePackage(publishId), 2);

                    } else {
                        showErrorToast(getResources().getString(R.string.deliver_close_tip));
                    }

                } else {

                    //判断认证状态
                    isShowOnFailureToast = false;
                    showLoadingDialog(null);
                    doHttp(RetrofitUtils.createApi(Mine.class).queryCarrierReview(), 3);


                }
                break;

        }
    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onFailure(result, call, response, what);
        switch (what) {

            case 3:
                new MaterialDialog(this).setMDMessage(getResources().getString(R.string.no_transfer_tip))
                        .setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                            @Override
                            public void dialogBtnOnClick() {
                                startActivity(MineAuthenticationActivity.class, null);
                            }
                        }).show();

                break;

        }
    }
}
