package com.biaoyuan.transfer.ui.mine.transfer;

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
import com.biaoyuan.transfer.adapter.MineTransferAdapter;
import com.biaoyuan.transfer.base.BaseFgt;
import com.biaoyuan.transfer.config.UserManger;
import com.biaoyuan.transfer.domain.MineTransferInfo;
import com.biaoyuan.transfer.http.Transfer;
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
 * 我的传送
 */

public class MineTransferFgt extends BaseFgt {
    @Bind(R.id.rv_data)
    RecyclerView mRvData;
    @Bind(R.id.ptr_frame)
    PtrFrameLayout mPtrFrame;

    private MineTransferAdapter mAdapter;

    private int type = 1;

    private int pageSize = Integer.parseInt(UserManger.pageSize);

    private int targetPage = 1;

    @Override
    public int getLayoutId() {
        return R.layout.ptr_recyclerview;
    }

    @Override
    public void initData() {
        PtrInitHelper.initPtr(getActivity(), mPtrFrame);

        mPtrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                targetPage = 1;
                doHttp(RetrofitUtils.createApi(Transfer.class).transmit(pageSize, targetPage, type), 1);
            }
        });

        LinearLayoutManager mRecyclerViewlayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        List<MineTransferInfo> list = new ArrayList<>();

        mAdapter = new MineTransferAdapter(R.layout.item_transfer_mine, list, type);

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
                        doHttp(RetrofitUtils.createApi(Transfer.class).transmit(pageSize, targetPage, type), 2);
                    }
                });

            }
        }, mRvData);

        //设置布局管理器
        mRvData.setLayoutManager(mRecyclerViewlayoutManager);
        mRvData.setHasFixedSize(true);
  //      mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mRvData.setItemAnimator(new DefaultItemAnimator());
        //设置adapter
        mRvData.setAdapter(mAdapter);

        setEmptyView(mAdapter, null);
        mRvData.addOnItemTouchListener(new SimpleClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MineTransferInfo transferInfo = mAdapter.getItem(position);

                Bundle bundle = new Bundle();


                bundle.putLong("packageId", transferInfo.getPackageId());


                switch (type) {
                    case 1:
                        //待取件
                        bundle.putInt("type", 3);
                        startActivity(MineTransferTakeDetailAty.class, bundle);
                        break;
                    case 2:

                        //进行中
                        bundle.putInt("type", 1);
                        startActivity(MineTransferDetailAty.class, bundle);
                        break;
                    case 3:
                        //已完成
                        bundle.putInt("type", 2);
                        startActivity(MineTransferDetailAty.class, bundle);
                        break;
                    case 4:
                        //异常件

                        switch (transferInfo.getExcepType()) {
                            case 7:
                                //超时
                                bundle.putLong("orderId",transferInfo.getPackageId());
                                startActivity(MineTransferOutTimeDetailAty.class, bundle);

                                break;
                            case 5:
                                //丢失
                                bundle.putLong("orderId",transferInfo.getOrderId());
                                startActivity(MineTransferOneNullAty.class, bundle);

                                break;
                            case 6:
                                //破损
                                bundle.putLong("orderId",transferInfo.getOrderId());
                                startActivity(MineTransferWornlAty.class, bundle);
                                break;
                        }

                        break;
                }
            }

            @Override
            public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                final MineTransferInfo transferInfo = mAdapter.getItem(position);


                if (view.getId()==R.id.tv_left&&type==3){

                    //删除
                    new MaterialDialog(getActivity()).setMDMessage("是否确认删除该传送单？").setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                        @Override
                        public void dialogBtnOnClick() {
                            showLoadingDialog(null);
                            doHttp(RetrofitUtils.createApi(Transfer.class).delCarryById(transferInfo.getPackageId()), 3);
                        }
                    }).show();

                }




            }

            @Override
            public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        type = bundle.getInt("type", 1);
        super.onActivityCreated(savedInstanceState);
        showLoadingContentDialog();
    }

    @Override
    public void requestData() {
        doHttp(RetrofitUtils.createApi(Transfer.class).transmit(pageSize, targetPage, type), 1);
    }

    @Override
    public void onUserVisible() {
        super.onUserVisible();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                targetPage = 1;
                doHttp(RetrofitUtils.createApi(Transfer.class).transmit(pageSize, targetPage, type), 1);
            }
        }, 500);

    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);

        switch (what) {
            case 1:
                mPtrFrame.refreshComplete();
                mAdapter.removeAll();
                List<MineTransferInfo> packageorder = AppJsonUtil.getArrayList(result, MineTransferInfo.class);
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

                break;
            case 2:

                //加载更多
                List<MineTransferInfo> packageorder2 = AppJsonUtil.getArrayList(result, MineTransferInfo.class);
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
                showToast("删除成功");
                targetPage = 1;
                mAdapter.loadMoreComplete();
                doHttp(RetrofitUtils.createApi(Transfer.class).transmit(pageSize, targetPage, type), 1);
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
