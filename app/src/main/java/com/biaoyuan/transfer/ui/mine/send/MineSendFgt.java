package com.biaoyuan.transfer.ui.mine.send;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.and.yzy.frame.adapter.recyclerview.BaseQuickAdapter;
import com.and.yzy.frame.adapter.recyclerview.listener.SimpleClickListener;
import com.and.yzy.frame.util.PtrInitHelper;
import com.and.yzy.frame.util.RetrofitUtils;
import com.and.yzy.frame.view.dialog.MaterialDialog;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.adapter.MineSendAdapter;
import com.biaoyuan.transfer.base.BaseFgt;
import com.biaoyuan.transfer.config.UserManger;
import com.biaoyuan.transfer.domain.MineSendInfo;
import com.biaoyuan.transfer.http.Index;
import com.biaoyuan.transfer.http.Send;
import com.biaoyuan.transfer.ui.send.SendPaymentOrderActivity;
import com.biaoyuan.transfer.util.AppJsonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 我的发件
 */
public class MineSendFgt extends BaseFgt {
    @Bind(R.id.rv_data)
    RecyclerView mRvData;
    @Bind(R.id.ptr_frame)
    PtrFrameLayout mPtrFrame;

    private MineSendAdapter mAdapter;

    private int type = 0;

    private int pageSize = Integer.parseInt(UserManger.pageSize);

    private int targetPage = 1;

    private long mOrderId;

    @Override
    public int getLayoutId() {
        return R.layout.ptr_recyclerview;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        type = getArguments().getInt("type", 0);
        super.onActivityCreated(savedInstanceState);
        showLoadingContentDialog();
    }

    @Override
    public boolean setIsInitRequestData() {
        return true;
    }

    @Override
    public void onUserVisible() {
        super.onUserVisible();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                targetPage = 1;
                doHttp(RetrofitUtils.createApi(Send.class).orders(type, targetPage, pageSize), 1);
            }
        }, 500);

    }

    @Override
    public void requestData() {
        doHttp(RetrofitUtils.createApi(Send.class).orders(type, targetPage, pageSize), 1);
    }

    @Override
    public void initData() {
        PtrInitHelper.initPtr(getActivity(), mPtrFrame);

        mPtrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

                targetPage = 1;
                doHttp(RetrofitUtils.createApi(Send.class).orders(type, targetPage, pageSize), 1);
            }
        });

        LinearLayoutManager mRecyclerViewlayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        List<MineSendInfo> list = new ArrayList<>();
        mAdapter = new MineSendAdapter(R.layout.item_mine_send_list, list);


        //上拉加载更多
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (mRvData == null) {
                    return;
                }
                mRvData.post(new Runnable() {
                    @Override
                    public void run() {

                        if (targetPage == 1) {
                            mAdapter.loadMoreEnd();
                            return;
                        }
                        doHttp(RetrofitUtils.createApi(Send.class).orders(type, targetPage, pageSize), 2);
                    }
                });

            }
        }, mRvData);


        //设置布局管理器
        mRvData.setLayoutManager(mRecyclerViewlayoutManager);
        mRvData.setHasFixedSize(true);
        //        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mRvData.setItemAnimator(new DefaultItemAnimator());
        //设置adapter
        mRvData.setAdapter(mAdapter);


        setEmptyView(mAdapter, null);


        mRvData.addOnItemTouchListener(new SimpleClickListener() {


            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {


                final MineSendInfo item = mAdapter.getItem(position);


                Bundle bundle = new Bundle();

                bundle.putInt("payStatus", item.getPayStatus());
                bundle.putInt("orderStatus", item.getOrderStatus());
                bundle.putLong("orderId", item.getOrderId());
                bundle.putString("orderNo", item.getOrderNo());

                switch (item.getPayStatus()) {
                    case 0:
                        //未支付

                        startActivity(MineSendNoPayAty.class, bundle);

                        break;
                    case 1:

                        //追加支付
                        bundle.putLong("addtionId", item.getAddtionId());
                        startActivity(MineSendNoPayAty.class, bundle);


                        break;
                    case 2:


                        //判断订单最新状态
                        mOrderId = item.getOrderId();
                        doHttp(RetrofitUtils.createApi(Index.class).getOrderStatus(mOrderId), 4);

                        break;
                }


            }

            @Override
            public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                final MineSendInfo item = mAdapter.getItem(position);


                switch (item.getPayStatus()) {
                    case 0:
                        //未支付
                        switch (view.getId()) {
                            case R.id.tv_left:
                                //取消订单
                                new MaterialDialog(getActivity()).setMDMessage("是否确认取消该订单？").setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                                    @Override
                                    public void dialogBtnOnClick() {
                                        showLoadingDialog(null);
                                        doHttp(RetrofitUtils.createApi(Send.class).orderCancel(item.getOrderId()), 3);
                                    }
                                }).show();

                                break;
                            case R.id.tv_right:
                                //去支付
                                Bundle bundle = new Bundle();
                                bundle.putString("orderNo", item.getOrderNo());
                                bundle.putLong("orderId", item.getOrderId());
                                bundle.putDouble("price", item.getTotalPrice());
                                startActivity(SendPaymentOrderActivity.class, bundle);
                                break;
                        }


                        break;
                    case 1:

                        //追加支付

                        //去支付
                        Bundle bundle = new Bundle();
                        bundle.putString("orderNo", item.getOrderNo());
                        bundle.putLong("orderId", item.getOrderId());
                        bundle.putLong("addtionId", item.getAddtionId());
                        bundle.putDouble("price", item.getPriceAddition());
                        startActivity(SendPaymentOrderActivity.class, bundle);


                        break;
                    case 2:

                        switch (item.getOrderStatus()) {
                            case 1:
                            case 2:
                                //待确认
                                //取消订单
                                new MaterialDialog(getActivity()).setMDMessage("是否确认取消该订单？").setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                                    @Override
                                    public void dialogBtnOnClick() {
                                        showLoadingDialog(null);
                                        doHttp(RetrofitUtils.createApi(Send.class).orderCancel(item.getOrderId()), 3);
                                    }
                                }).show();


                                break;


                            case 8:
                                //待评价
                                Bundle bun = new Bundle();
                                bun.putString("orderNo", item.getOrderNo());
                                bun.putLong("orderId", item.getOrderId());
                                startActivity(MineAddPinLunAty.class, bun);


                                break;
                        }


                        break;
                }


            }

            @Override
            public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });

    }


    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);

        switch (what) {
            case 1:

                mAdapter.removeAll();
                List<MineSendInfo> packageorder = AppJsonUtil.getArrayList(result, MineSendInfo.class);
                if (packageorder != null) {
                    mAdapter.setNewData(packageorder);
                    if (packageorder.size() < pageSize) {
                        mAdapter.loadMoreEnd();
                    }
                } else {
                    mAdapter.loadMoreEnd();
                }
                //增加页码
                targetPage++;
                mPtrFrame.refreshComplete();
                break;
            case 2:

                //加载更多
                List<MineSendInfo> packageorder2 = AppJsonUtil.getArrayList(result, MineSendInfo.class);
                if (packageorder2 != null && packageorder2.size() > 0) {
                    mAdapter.addDatas(packageorder2);
                    mAdapter.loadMoreComplete();
                } else {
                    mAdapter.loadMoreEnd();
                }
                //增加页码
                targetPage++;
                break;
            case 3:
                showToast("取消成功");
                targetPage = 1;
                mAdapter.loadMoreComplete();
                doHttp(RetrofitUtils.createApi(Send.class).orders(type, targetPage, pageSize), 1);

                break;
            case 4:

                int getPay = AppJsonUtil.getInt(AppJsonUtil.getString(result, "data"), "pay");
                int getStatus = AppJsonUtil.getInt(AppJsonUtil.getString(result, "data"), "status");
                String orderNo = AppJsonUtil.getString(AppJsonUtil.getString(result, "data"), "orderNo");


                Bundle bundle = new Bundle();
                bundle.putLong("orderId", mOrderId);
                bundle.putInt("orderStatus", getStatus);
                bundle.putInt("payStatus", getPay);
                bundle.putString("orderNo", orderNo);


                switch (getStatus) {
                    case 1:
                        //待确认
                        startActivity(MineSendWaitTakeAty.class, bundle);

                        break;
                    case 2:
                        //待取件
                        startActivity(MineSendWaitTakeAty.class, bundle);

                        break;
                    case 3:
                    case 4:
                    case 15:
                        //取件派送
                    case 5:
                        //传送员传送中
                    case 6:
                    case 7:
                    case 12:
                        //送达网点派送
                        startActivity(MineSendWaitSignAty.class, bundle);
                        break;


                    case 8:
                        //待评价
                        startActivity(MineAddPinLunAty.class, bundle);
                        break;
                    case 9:
                        //已完成
                        startActivity(MineSendWaitSignAty.class, bundle);
                        break;
                }


                break;


        }
    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onFailure(result, call, response, what);
        if (mPtrFrame != null) {
            mPtrFrame.refreshComplete();
            mAdapter.loadMoreComplete();
        }
    }

    @Override
    public void onError(Call<ResponseBody> call, Throwable t, int what) {
        super.onError(call, t, what);
        if (mPtrFrame != null) {
            mPtrFrame.refreshComplete();
            mAdapter.loadMoreComplete();
        }
    }


}
