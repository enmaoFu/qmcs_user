package com.biaoyuan.transfer.ui.mine.refund;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.and.yzy.frame.util.DateTool;
import com.and.yzy.frame.util.RetrofitUtils;
import com.and.yzy.frame.view.dialog.MaterialDialog;
import com.and.yzy.frame.view.linearlistview.LinearListView;
import com.and.yzy.frame.view.listview.ListViewForScrollView;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.adapter.RefundStepAdapter;
import com.biaoyuan.transfer.adapter.TestPicAdapter;
import com.biaoyuan.transfer.base.BaseAty;
import com.biaoyuan.transfer.config.UserManger;
import com.biaoyuan.transfer.domain.MineRefundDetail;
import com.biaoyuan.transfer.domain.PicInfo;
import com.biaoyuan.transfer.http.Mine;
import com.biaoyuan.transfer.ui.ShowBigImgaeAty;
import com.biaoyuan.transfer.ui.transfer.TransferMapAty;
import com.biaoyuan.transfer.util.AppJsonUtil;
import com.biaoyuan.transfer.util.EasyTransition;
import com.biaoyuan.transfer.util.EasyTransitionOptions;
import com.biaoyuan.transfer.util.MyNumberFormat;
import com.biaoyuan.transfer.view.ExpandLayout;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Title  :赔偿快件丢失 破损详情
 * Create : 2017/7/7
 * Author ：chen
 */

public class MineRefundDetailAty extends BaseAty {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tv_order)
    TextView mTvOrder;
    @Bind(R.id.order_dot_msg_text)
    TextView mOrderDotMsgText;
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
    @Bind(R.id.v_diver01)
    View mVDiver01;
    @Bind(R.id.textView7)
    TextView mTextView7;
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
    @Bind(R.id.tv_price)
    TextView mTvPrice;
    @Bind(R.id.tv_tui_price)
    TextView mTvTuiPrice;
    @Bind(R.id.expandLayout)
    ExpandLayout mExpandLayout;
    @Bind(R.id.lv_data)
    ListViewForScrollView mLvData;
    @Bind(R.id.lv_pic)
    LinearListView mLvPic;
    @Bind(R.id.tv_error)
    TextView mTvError;

    @Bind(R.id.img_shadow)
    ImageView mImgShadow;
    @Bind(R.id.img_state)
    ImageView mImgState;
    @Bind(R.id.tv_state)
    TextView mTvState;
    @Bind(R.id.tv_state_msg)
    TextView mTvStateMsg;
    @Bind(R.id.tv_code)
    TextView mTvCode;
    @Bind(R.id.tv_error_type)
    TextView mTvErrorType;
    @Bind(R.id.hr)
    View mHr;
    @Bind(R.id.rl_price)
    RelativeLayout mRlPrice;
    @Bind(R.id.textView3)
    TextView mTextView3;
    @Bind(R.id.tv_type)
    TextView mTvType;
    @Bind(R.id.tv_size)
    TextView mTvSize;
    @Bind(R.id.ll_wron)
    LinearLayout mLlWron;
    @Bind(R.id.order_divier)
    ImageView mOrderDivier;
    @Bind(R.id.tv_shop_end_name)
    TextView mTvShopEndName;


    private int excepType;
    private long orderId;


    private long startPhone;
    private long endPhone;
    private double startLat;
    private double startLng;
    private double endLat;
    private double endLng;


    //打电话的type
    private int type = 0;

    private TestPicAdapter mPicAdapter;

    private RefundStepAdapter mStepAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_refund_one_null_details;
    }

    @Override
    public void initData() {


        mExpandLayout.setExpandListener(new ExpandLayout.onExpandListener() {
            @Override
            public void expand() {
                mTvOrder.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.icon_shrink_up), null);
            }

            @Override
            public void collapse() {
                mTvOrder.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.icon_shrink_down), null);
            }
        });


        List<MineRefundDetail.RefundProcessBean> processBeen = new ArrayList<>();

        mStepAdapter = new RefundStepAdapter(this, processBeen, R.layout.item_wuniu);
        mLvData.setAdapter(mStepAdapter);


        excepType = getIntent().getIntExtra("excepType", 0);
        orderId = getIntent().getLongExtra("orderId", 0);

        switch (excepType) {
            case 1:

                //取件拒收详情
                initToolbar(mToolbar, "取件拒收详情");
                mTvCode.setText("拒收原因");

                mPicAdapter = new TestPicAdapter(this, new ArrayList<PicInfo>(), R.layout.item_list_pic);
                mLvPic.setAdapter(mPicAdapter);

                mLvPic.setOnItemClickListener(new LinearListView.OnItemClickListener() {
                    @Override
                    public void onItemClick(LinearListView parent, View view, int position, long id) {
                        PicInfo picInfo = mPicAdapter.getItem(position);

                        Intent intent = new Intent(MineRefundDetailAty.this, ShowBigImgaeAty.class);
                        intent.putExtra("url", picInfo.getPath());

                        // ready for transition options
                        EasyTransitionOptions options =
                                EasyTransitionOptions.makeTransitionOptions(
                                        MineRefundDetailAty.this,
                                        view.findViewById(R.id.img));


                        // start transition
                        EasyTransition.startActivity(intent, options);


                    }
                });

                break;
            case 2:

                //无偿取消
                initToolbar(mToolbar, "取消订单详情");

                mTvCode.setText("取消类型");
                mTvErrorType.setText("全额退款（未扣费）");
                mLlWron.setVisibility(View.GONE);
                mImgShadow.setVisibility(View.GONE);
                break;
            case 9:

                //有偿偿取消
                initToolbar(mToolbar, "取消订单详情");
                mTvCode.setText("取消类型");
                mTvErrorType.setText("扣除部分金额退款");
                mLlWron.setVisibility(View.GONE);
                mImgShadow.setVisibility(View.GONE);
                break;
            case 10:

                //配送超时
                initToolbar(mToolbar, "配送超时详情");
                mTvErrorType.setText("网点配送超时");
                mLlWron.setVisibility(View.GONE);
                mImgShadow.setVisibility(View.GONE);

                break;
            case 12:

                //收件人拒收
                initToolbar(mToolbar, "接件人拒收详情");
                mTvErrorType.setText("接件人拒绝签收快件");
                mLlWron.setVisibility(View.GONE);
                mImgShadow.setVisibility(View.GONE);

                break;


            case 5:
                //丢失
                initToolbar(mToolbar, "快件丢失详情");
                //隐藏破损快件
                mImgShadow.setVisibility(View.GONE);
                mLlWron.setVisibility(View.GONE);

                mTvErrorType.setText("收件网点未检测到该快件");

                break;
            case 6:
                //破损
                initToolbar(mToolbar, "快件破损详情");

                mTvErrorType.setText("该件存在严重破损情况");
                List<PicInfo> picInfos = new ArrayList<>();


                mPicAdapter = new TestPicAdapter(this, picInfos, R.layout.item_list_pic);
                mLvPic.setAdapter(mPicAdapter);

                mLvPic.setOnItemClickListener(new LinearListView.OnItemClickListener() {
                    @Override
                    public void onItemClick(LinearListView parent, View view, int position, long id) {
                        PicInfo picInfo = mPicAdapter.getItem(position);

                        Intent intent = new Intent(MineRefundDetailAty.this, ShowBigImgaeAty.class);
                        intent.putExtra("url", picInfo.getPath());

                        // ready for transition options
                        EasyTransitionOptions options =
                                EasyTransitionOptions.makeTransitionOptions(
                                        MineRefundDetailAty.this,
                                        view.findViewById(R.id.img));


                        // start transition
                        EasyTransition.startActivity(intent, options);


                    }
                });


                break;
        }


    }


    @OnClick({R.id.ll_start_address, R.id.ll_end_address, R.id.tv_start_phone, R.id.tv_end_phone, R.id.tv_order})
    @Override
    public void btnClick(View view) {
        super.btnClick(view);
        switch (view.getId()) {
            case R.id.tv_order:

                mExpandLayout.toggleExpand();
                break;
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
                                   AndPermission.rationaleDialog(MineRefundDetailAty.this, rationale).show();
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
                    new MaterialDialog(MineRefundDetailAty.this)
                            .setMDMessage("是否立即拨打发件网点电话?")
                            .setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                                @Override
                                public void dialogBtnOnClick() {
                                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + startPhone));
                                    startActivity(intent);
                                }
                            }).show();
                } else {

                    new MaterialDialog(MineRefundDetailAty.this)
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
    public boolean setIsInitRequestData() {
        return true;
    }

    @Override
    public void requestData() {

        showLoadingContentDialog();

        doHttp(RetrofitUtils.createApi(Mine.class).selectRefundAfterSaleDetailsByOrderId(orderId), 1);


    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);


        MineRefundDetail detailResult = AppJsonUtil.getObject(result, MineRefundDetail.class);


        mTvStartName.setText(detailResult.getIssueAffiliateAreaCode() + detailResult.getIssueAffiliateName());
        mTvStartPhone.setText(detailResult.getIssueAffiliateTelphone() + "");

      /*  StringBuffer stringBuffer = new StringBuffer();
        String[] str = detailResult.getOrderSendAddr().split("\\|");
        for (int i = 1; i < str.length; i++) {
            stringBuffer.append(str[i]);
        }*/
        mTvStartAddress.setText(detailResult.getIssueAffiliateAddress());


        //判断有是否有目的网点
        if (detailResult.getAcceptAffiliateTelphone() == 0) {
            mSend.setVisibility(View.GONE);
            mSendPhone.setVisibility(View.GONE);
            mTvShopEndName.setText("收货地址");

            StringBuffer endBuffer = new StringBuffer();
            String[] end = detailResult.getOrderReceiveAddr().split("\\|");
            for (int i = 1; i < end.length; i++) {
                endBuffer.append(end[i]);
            }
            mTvEndAddress.setText(endBuffer.toString());
        } else {
            mTvEndName.setText(detailResult.getAcceptAffiliateAreaCode() + detailResult.getAcceptAffiliateName());
            mTvEndPhone.setText(detailResult.getAcceptAffiliateTelphone() + "");
            mTvEndAddress.setText(detailResult.getAcceptAffiliateAddress());
        }


        String startTime = DateTool.getTimeType(detailResult.getOrderRequiredTime());

        if (startTime == null) {
            mTvStartTime.setText(DateTool.timesToStrTime(detailResult.getOrderRequiredTime() + "", "yyyy.MM.dd HH:mm"));
        } else {
            mTvStartTime.setText(startTime + DateTool.timesToStrTime(detailResult.getOrderRequiredTime() + "", "HH:mm") + "之前");
        }

        switch (detailResult.getOrderGoodsType()) {
            case 1:
                mTvType.setText("文件");
                break;
            case 2:
                mTvType.setText("办公/居家");
                break;
            case 3:
                mTvType.setText("鲜花");
                break;
            case 4:
                mTvType.setText("包裹");
                break;
            case 5:
                mTvType.setText("蛋糕");
                break;
        }
        mTvSize.setText("最长边≤" + detailResult.getOrderGoodsSize() + "cm  " + detailResult.getOrderGoodsWeight() + "kg");


        //
        startPhone = detailResult.getIssueAffiliateTelphone();
        endPhone = detailResult.getAcceptAffiliateTelphone();

        //  mTvPrice.setText("￥" + MyNumberFormat.m2(detailResult.getPublishReward()));


        startLat = detailResult.getOrderSendLat();
        startLng = detailResult.getOrderSendLng();

        endLat = detailResult.getOrderReceiveLat();
        endLng = detailResult.getOrderReceiveLng();


        mTvPrice.setText("¥" + MyNumberFormat.m2(detailResult.getOrderTotalPrice()));
        //判断处理状态
        switch (detailResult.getExcepHandleStatus()) {
            case 1:
            case 2:
                //处理中
                mImgState.setImageResource(R.drawable.icon_progress);
                mRlPrice.setVisibility(View.GONE);

                break;
            case 3:
                mImgState.setImageResource(R.drawable.icon_correct);
                mRlPrice.setVisibility(View.VISIBLE);
                mTvTuiPrice.setText("¥" + MyNumberFormat.m2(detailResult.getHandleAmount()));

                break;
        }


        switch (excepType) {
            case 1:
                //拒收原因

                mTvErrorType.setText(detailResult.getExcepRejectionReason());

                //破损

                if (TextUtils.isEmpty(detailResult.getExcepReason())) {

                    mTvError.setText("无描述");

                } else {
                    mTvError.setText(detailResult.getExcepReason());
                }

                //添加图片
                String url2 = detailResult.getExcepPicUrl();


                if (url2 != null && url2.length() > 0) {

                    String[] paths = url2.split(",");

                    for (int i = 0; i < paths.length; i++) {
                        PicInfo picInfo = new PicInfo();

                        picInfo.setPath(paths[i]);
                        mPicAdapter.addInfo(picInfo);
                    }

                }
                break;

            case 5:
                //丢失
                mTvCode.setText("快件码 " + detailResult.getOrderSignCode());
                break;
            case 10:
                //超时
                mTvCode.setText("快件码 " + detailResult.getOrderSignCode());
            case 12:
                //拒绝签收
                mTvCode.setText("快件码 " + detailResult.getOrderSignCode());

                break;
            case 6:
                mTvCode.setText("快件码 " + detailResult.getOrderSignCode());
                //破损

                if (TextUtils.isEmpty(detailResult.getExcepReason())) {

                    mTvError.setText("无描述");

                } else {
                    mTvError.setText(detailResult.getExcepReason());
                }

                //添加图片
                String url = detailResult.getExcepPicUrl();


                if (url != null && url.length() > 0) {

                    String[] paths = url.split(",");

                    for (int i = 0; i < paths.length; i++) {
                        PicInfo picInfo = new PicInfo();

                        picInfo.setPath(paths[i]);
                        mPicAdapter.addInfo(picInfo);
                    }

                }

                break;

        }

        Collections.reverse(detailResult.getRefundProcess());

        mStepAdapter.addAll(detailResult.getRefundProcess());

        mTvState.setText(detailResult.getRefundProcess().get(0).getTitle());
        mTvStateMsg.setText(detailResult.getRefundProcess().get(0).getContent());


        mExpandLayout.post(new Runnable() {
            @Override
            public void run() {
                mExpandLayout.initExpand(false);
            }
        });

    }
}
