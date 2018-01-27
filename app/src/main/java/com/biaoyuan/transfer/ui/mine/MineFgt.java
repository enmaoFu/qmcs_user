package com.biaoyuan.transfer.ui.mine;

import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.and.yzy.frame.util.DateTool;
import com.and.yzy.frame.util.RetrofitUtils;
import com.and.yzy.frame.view.dialog.MaterialDialog;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.base.BaseFgt;
import com.biaoyuan.transfer.config.UserManger;
import com.biaoyuan.transfer.domain.UserInfo;
import com.biaoyuan.transfer.http.Mine;
import com.biaoyuan.transfer.http.Transfer;
import com.biaoyuan.transfer.ui.MainAty;
import com.biaoyuan.transfer.ui.index.IndexMessageActivity;
import com.biaoyuan.transfer.ui.login.LoginAty;
import com.biaoyuan.transfer.ui.mine.refund.MineRefundAllActivity;
import com.biaoyuan.transfer.ui.mine.send.MineSendAllActivity;
import com.biaoyuan.transfer.ui.mine.transfer.MineTransferActivity;
import com.biaoyuan.transfer.util.AppJsonUtil;
import com.biaoyuan.transfer.util.MyNumberFormat;
import com.facebook.drawee.view.SimpleDraweeView;
import com.orhanobut.logger.Logger;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Title  :
 * Create : 2017/4/24
 * Author ：chen
 */
public class MineFgt extends BaseFgt {


    @Bind(R.id.mine_img)
    SimpleDraweeView mMineImg;
    @Bind(R.id.tv_phone)
    TextView mTvPhone;
    @Bind(R.id.tv_time)
    TextView mTvTime;
    @Bind(R.id.tv_banlce)
    TextView mTvBanlce;
    @Bind(R.id.mine_money_re)
    LinearLayout mMineMoneyRe;
    @Bind(R.id.tv_mine_send)
    TextView mTvMineSend;
    @Bind(R.id.tv_transfer)
    TextView mTvTransfer;
    @Bind(R.id.tv_trip)
    TextView mTvTrip;
    @Bind(R.id.tv_pinlun)
    TextView mTvPinlun;
    @Bind(R.id.angel)
    TextView mAngel;
    @Bind(R.id.tv_coupon)
    TextView mTvCoupon;
    @Bind(R.id.rl_data)
    RelativeLayout mRlData;
    @Bind(R.id.tv_tuikuan)
    TextView mTvTuikuan;
    @Bind(R.id.feedback)
    TextView mFeedback;
    @Bind(R.id.setting)
    TextView mSetting;
    @Bind(R.id.scrollView)
    ScrollView mScrollView;
    private UserInfo mInfo;

    public static boolean isChangeImage = true;

    private boolean onLoadingData = false;


    @Override
    public int getLayoutId() {
        return R.layout.fgt_mine_main;
    }

    @Override
    public void initData() {
    }

    @Override
    public void requestData() {
        if (UserManger.isLogin()) {
            isShowOnFailureToast = true;

            doHttp(RetrofitUtils.createApi(Mine.class).toPersonalCenter(), 1);
        } else {
            setLoginView(false);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (UserManger.isLogin()) {
            showLoadingContentDialog();
        }


    }

    @Override
    public boolean setIsInitRequestData() {
        return true;
    }

    /**
     * 设置登录 ，未登录需要显示的内容
     *
     * @param b
     */
    private void setLoginView(boolean b) {
        if (b) {
            mMineMoneyRe.setVisibility(View.VISIBLE);
            mMineMoneyRe.setClickable(true);
            mTvTime.setVisibility(View.VISIBLE);
            mTvPhone.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        } else {
            mMineMoneyRe.setVisibility(View.INVISIBLE);
            mMineMoneyRe.setClickable(false);
            mTvTime.setVisibility(View.GONE);
            mTvPhone.setText("请点击登录");
            mTvPhone.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        }


    }

    @OnClick({R.id.setting, R.id.feedback, R.id.tv_mine_send, R.id.angel
            , R.id.tv_transfer, R.id.tv_trip, R.id.rl_data, R.id.ll_yue, R.id.tv_pinlun, R.id.tv_tuikuan, R.id.messgae, R.id.ll_coupon})
    @Override
    public void btnClick(View view) {
        super.btnClick(view);

        if (!UserManger.isLogin()) {
            startActivity(LoginAty.class, null);
            return;
        }

        switch (view.getId()) {
            case R.id.setting:
                //设置
                startActivity(MineSettingActivity.class, null);
                break;
            case R.id.messgae:
                //消息
                startActivity(IndexMessageActivity.class, null);
                break;
            case R.id.feedback:
                //意见反馈
                startActivity(MineFeedbackActivity.class, null);
                break;
            case R.id.angel:


                //请求传送员状态
                doHttp(RetrofitUtils.createApi(Transfer.class).isDeliver(UserManger.getDeliver() + ""), 4);


                break;
            case R.id.tv_mine_send:
                //我的发件
                startActivity(MineSendAllActivity.class, null);
                break;
            case R.id.ll_coupon:
                //优惠券
                showErrorToast("优惠券暂时不能使用");
                break;
            case R.id.tv_transfer:
                //判断是否是传送员
                doHttp(RetrofitUtils.createApi(Transfer.class).isDeliver(UserManger.getDeliver() + ""), 5);


                break;
            case R.id.tv_trip:

                //判断是否是传送员
                doHttp(RetrofitUtils.createApi(Transfer.class).isDeliver(UserManger.getDeliver() + ""), 6);

                break;
            case R.id.rl_data:
                //我的资料
                Bundle bundle = new Bundle();
                bundle.putParcelable("data", mInfo);
                startActivity(MineDataActivity.class, bundle);
                break;
            case R.id.ll_yue:
                //我的余额
                startActivity(MineBalanceActivity.class, null);
                break;
            case R.id.tv_pinlun:
                //我的评论
                startActivity(MinePinLunAty.class, null);
                break;
            case R.id.tv_tuikuan:
                //退款售后
                startActivity(MineRefundAllActivity.class, null);
                break;
        }
    }


    @Override
    public void onUserVisible() {
        super.onUserVisible();


        if (MainAty.radioButtons!=null&&MainAty.radioButtons.size()>3&&MainAty.radioButtons.get(3).isChecked()) {

            if (UserManger.isLogin()) {
                if (onLoadingData) {
                    return;
                }
                onLoadingData = true;
                doHttp(RetrofitUtils.createApi(Mine.class).toPersonalCenter(), 1);
            } else {
                setLoginView(false);
            }
        }
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        onLoadingData = false;
        switch (what) {
            case 1:
                setLoginView(true);
                mInfo = AppJsonUtil.getObject(result, UserInfo.class);

                if (isChangeImage) {
                    mMineImg.setImageURI(Uri.parse(mInfo.getUserPortraitUrl()));
                    isChangeImage = false;
                }
                UserManger.setPhone(String.valueOf(mInfo.getUserTelphone()));

                mTvPhone.setText(MyNumberFormat.toPwdPhone(mInfo.getUserTelphone()));

                mTvBanlce.setText("¥" + MyNumberFormat.m2(mInfo.getAccountBalanceBefore()));

                mTvCoupon.setText(mInfo.getUserCoupon() + "");
                if (mInfo.getUserLastLogin() == 0) {
                    mTvTime.setText("上次登录时间：" + DateTool.timesToStrTime(System.currentTimeMillis() + "", "yyyy.MM.dd HH:mm"));
                } else {
                    mTvTime.setText("上次登录时间：" + DateTool.timesToStrTime(mInfo.getUserLastLogin() + "", "yyyy.MM.dd HH:mm"));
                }


                break;

            case 2:

                int state = AppJsonUtil.getInt(AppJsonUtil.getString(result, "data"), "reviewStatus");

                Logger.v("state==" + state);

                Bundle bundle = new Bundle();
                switch (state) {
                    case 0:
                        //申请中
                        bundle.putInt("type", MineTransferStateAty.TYPE_COMMIT);

                        break;
                    case 1:

                        //申请成功
                        bundle.putInt("type", MineTransferStateAty.TYPE_SUCCESS);
                        break;
                    case 2:
                        //被拒绝

                        bundle.putInt("type", MineTransferStateAty.TYPE_ERROR);

                        break;
                }

                startActivity(MineTransferStateAty.class, bundle);


                break;

            case 4:
                //传送天使
                UserManger.setDeliver(AppJsonUtil.getInt(AppJsonUtil.getString(result, "data"), "isDeliver"));
                UserManger.setDeliverStatus(AppJsonUtil.getInt(AppJsonUtil.getString(result, "data"), "DeliverStatus"));

                if (UserManger.isDeliver()) {
                    //判断传送员状态
                    if (UserManger.isDeliverOpen()) {

                        Bundle bb = new Bundle();
                        bb.putInt("type", MineTransferStateAty.TYPE_SUCCESS);
                        startActivity(MineTransferStateAty.class, bb);


                    } else {
                        showErrorToast(getResources().getString(R.string.deliver_close_tip));
                    }

                } else {

                    //判断认证状态
                    isShowOnFailureToast = false;
                    showLoadingDialog(null);
                    doHttp(RetrofitUtils.createApi(Mine.class).queryCarrierReview(), 2);
                }


                break;

            case 5:
                //进入我的传送
                UserManger.setDeliver(AppJsonUtil.getInt(AppJsonUtil.getString(result, "data"), "isDeliver"));
                UserManger.setDeliverStatus(AppJsonUtil.getInt(AppJsonUtil.getString(result, "data"), "DeliverStatus"));
                //判断是否是传送员
                if (UserManger.isDeliver()) {
                    //判断传送员状态
                    if (UserManger.isDeliverOpen()) {
                        //我的传送
                        startActivity(MineTransferActivity.class, null);

                    } else {
                        showErrorToast(getResources().getString(R.string.deliver_close_tip));
                    }

                } else {

                    //判断认证状态
                    isShowOnFailureToast = false;
                    showLoadingDialog(null);
                    doHttp(RetrofitUtils.createApi(Mine.class).queryCarrierReview(), 2);

                }


                break;
            case 6:
                //进入我的行程

                UserManger.setDeliver(AppJsonUtil.getInt(AppJsonUtil.getString(result, "data"), "isDeliver"));
                UserManger.setDeliverStatus(AppJsonUtil.getInt(AppJsonUtil.getString(result, "data"), "DeliverStatus"));
                //判断是否是传送员
                if (UserManger.isDeliver()) {
                    //判断传送员状态
                    if (UserManger.isDeliverOpen()) {
                        //我的行程
                        startActivity(MineTripActivity.class, null);

                    } else {
                        showErrorToast(getResources().getString(R.string.deliver_close_tip));
                    }

                } else {
                    //判断认证状态
                    isShowOnFailureToast = false;
                    showLoadingDialog(null);
                    doHttp(RetrofitUtils.createApi(Mine.class).queryCarrierReview(), 2);

                }


                break;


        }


    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onFailure(result, call, response, what);
        onLoadingData = false;
        switch (what) {
            case 2:
                //  没有提交过申请
                startActivity(MineAuthenticationActivity.class, null);

                break;
            case 3:
                new MaterialDialog(getActivity()).setMDMessage(getResources().getString(R.string.no_transfer_tip))
                        .setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                            @Override
                            public void dialogBtnOnClick() {
                                startActivity(MineAuthenticationActivity.class, null);
                            }
                        }).show();

                break;

        }
    }

    @Override
    public void onError(Call<ResponseBody> call, Throwable t, int what) {
        super.onError(call, t, what);
        onLoadingData = false;

    }
}
