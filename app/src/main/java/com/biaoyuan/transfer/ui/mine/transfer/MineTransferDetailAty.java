package com.biaoyuan.transfer.ui.mine.transfer;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.and.yzy.frame.util.DateTool;
import com.and.yzy.frame.util.RetrofitUtils;
import com.and.yzy.frame.view.dialog.MaterialDialog;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.base.BaseAty;
import com.biaoyuan.transfer.config.UserManger;
import com.biaoyuan.transfer.domain.MineTransferDetailResult;
import com.biaoyuan.transfer.http.Transfer;
import com.biaoyuan.transfer.ui.transfer.TransferMapAty;
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
 * Title  :进行中 已完成
 * Create : 2017/6/24
 * Author ：chen
 */

public class MineTransferDetailAty extends BaseAty {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;


    @Bind(R.id.tv_size)
    TextView mTvSize;

    @Bind(R.id.tv_start_name)
    TextView mTvStartName;
    @Bind(R.id.take)
    LinearLayout mTake;
    @Bind(R.id.tv_start_phone)
    TextView mTvStartPhone;
    @Bind(R.id.take_phone)
    LinearLayout mTakePhone;
    @Bind(R.id.tv_start_address)
    TextView mTvStartAddress;
    @Bind(R.id.ll_start_address)
    LinearLayout mLlStartAddress;

    @Bind(R.id.tv_end_name)
    TextView mTvEndName;
    @Bind(R.id.send)
    LinearLayout mSend;
    @Bind(R.id.tv_end_phone)
    TextView mTvEndPhone;
    @Bind(R.id.send_phone)
    LinearLayout mSendPhone;
    @Bind(R.id.tv_end_address)
    TextView mTvEndAddress;
    @Bind(R.id.ll_end_address)
    LinearLayout mLlEndAddress;
    @Bind(R.id.arrive_phone_msg_text)
    TextView mArrivePhoneMsgText;
    @Bind(R.id.tv_start_time)
    TextView mTvStartTime;
    @Bind(R.id.take_time)
    LinearLayout mTakeTime;
    @Bind(R.id.tv_end_time)
    TextView mTvEndTime;
    @Bind(R.id.send_time)
    LinearLayout mSendTime;
    @Bind(R.id.tv_price)
    TextView mTvPrice;
    @Bind(R.id.main)
    LinearLayout mMain;
    @Bind(R.id.tv_get_time)
    TextView mTvGetTime;
    @Bind(R.id.tv_take_time)
    TextView mTvTakeTime;
    @Bind(R.id.tv_send_time)
    TextView mTvSendTime;
    @Bind(R.id.tv_code)
    TextView mTvCode;
    @Bind(R.id.type)
    LinearLayout mType;
    @Bind(R.id.order_dot_msg_text)
    TextView mOrderDotMsgText;
    @Bind(R.id.v_diver01)
    View mVDiver01;
    @Bind(R.id.textView7)
    TextView mTextView7;


    private long startPhone;
    private long endPhone;
    private double startLat;
    private double startLng;
    private double endLat;
    private double endLng;


    //打电话的type
    private int type = 0;


    //1进行中 2已结束

    private int detailType = 1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_transfer_order_details;
    }

    @Override
    public void initData() {

        detailType = getIntent().getIntExtra("type", 1);
        switch (detailType) {
            case 1:
                initToolbar(mToolbar, "进行中");
                mTvGetTime.setVisibility(View.VISIBLE);
                mTvTakeTime.setVisibility(View.GONE);
                mTvSendTime.setVisibility(View.GONE);
                break;
            case 2:
                initToolbar(mToolbar, "已完成");
                mTvGetTime.setVisibility(View.GONE);
                mTvTakeTime.setVisibility(View.VISIBLE);
                mTvSendTime.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public boolean setIsInitRequestData() {
        return true;
    }

    @Override
    public void requestData() {
        showLoadingContentDialog();
        doHttp(RetrofitUtils.createApi(Transfer.class).packageInfo(getIntent().getIntExtra("type", 1), getIntent().getLongExtra("packageId", 0)), 2);

    }

    @OnClick({R.id.ll_start_address, R.id.ll_end_address, R.id.tv_start_phone, R.id.tv_end_phone})
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
                                   AndPermission.rationaleDialog(MineTransferDetailAty.this, rationale).show();
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
                    new MaterialDialog(MineTransferDetailAty.this)
                            .setMDMessage("是否立即拨打发件网点电话?")
                            .setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                                @Override
                                public void dialogBtnOnClick() {
                                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + startPhone));
                                    startActivity(intent);
                                }
                            }).show();
                } else {

                    new MaterialDialog(MineTransferDetailAty.this)
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
                showToast("取件成功");
                finish();
                break;
            case 2:
                MineTransferDetailResult detailResult = AppJsonUtil.getObject(result, MineTransferDetailResult.class);
                mTvSize.setText("最长边≤" + detailResult.getPackageSize() + "cm  " + detailResult.getPackageWeight() + "kg");


                mTvStartName.setText(detailResult.getSendBasicName());
                mTvStartPhone.setText(detailResult.getSendBasicTelphone() + "");
                mTvStartAddress.setText(detailResult.getSendBasicAddress());

                mTvEndName.setText(detailResult.getReceiveBasicName());
                mTvEndPhone.setText(detailResult.getReceiveBasicTelphone() + "");
                mTvEndAddress.setText(detailResult.getReceiveBasicAddress());

                mTvCode.setText(detailResult.getPackageCode());

                String startTime = DateTool.getTimeType(detailResult.getPublishReqPickupTime());
                String endTime = DateTool.getTimeType(detailResult.getPublishReqDelivTime());

                if (startTime == null) {
                    mTvStartTime.setText(DateTool.timesToStrTime(detailResult.getPublishReqPickupTime() + "", "yyyy.MM.dd HH:mm"));
                } else {
                    mTvStartTime.setText(startTime + DateTool.timesToStrTime(detailResult.getPublishReqPickupTime() + "", "HH:mm") + "之前");
                }

                if (endTime == null) {
                    mTvEndTime.setText(DateTool.timesToStrTime(detailResult.getPublishReqDelivTime() + "", "yyyy.MM.dd HH:mm"));
                } else {
                    mTvEndTime.setText(endTime + DateTool.timesToStrTime(detailResult.getPublishReqDelivTime() + "", "HH:mm") + "之前");
                }
                //
                startPhone = detailResult.getSendBasicTelphone();
                endPhone = detailResult.getReceiveBasicTelphone();

                mTvPrice.setText("¥" + MyNumberFormat.m2(detailResult.getPublishReward()));


                startLat = detailResult.getSendLat();
                startLng = detailResult.getSendLng();

                endLat = detailResult.getReceiveLat();
                endLng = detailResult.getReceiveLng();


                switch (detailType) {
                    case 1:
                        //进行中
                        mTvGetTime.setText("接单时间：" + DateTool.timesToStrTime(detailResult.getCarryPickupTime() + "", "yyyy.MM.dd HH:mm"));

                        break;
                    case 2:
                        //已完成
                        mTvTakeTime.setText("接单时间：" + DateTool.timesToStrTime(detailResult.getCarryPickupTime() + "", "yyyy.MM.dd HH:mm"));
                        mTvSendTime.setText("送达时间：" + DateTool.timesToStrTime(detailResult.getCarryCheckTime() + "", "yyyy.MM.dd HH:mm"));
                        break;
                }

                break;

        }
    }


}
