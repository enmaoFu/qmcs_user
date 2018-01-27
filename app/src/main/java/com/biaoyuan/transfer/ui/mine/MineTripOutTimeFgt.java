package com.biaoyuan.transfer.ui.mine;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.and.yzy.frame.adapter.recyclerview.BaseQuickAdapter;
import com.and.yzy.frame.adapter.recyclerview.listener.OnItemChildClickListener;
import com.and.yzy.frame.util.PtrInitHelper;
import com.and.yzy.frame.util.RetrofitUtils;
import com.and.yzy.frame.view.dialog.MaterialDialog;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.adapter.MineTripOutTimeAdapter;
import com.biaoyuan.transfer.base.BaseFgt;
import com.biaoyuan.transfer.config.UserManger;
import com.biaoyuan.transfer.domain.MineTripInfo;
import com.biaoyuan.transfer.http.Mine;
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
 * 我的行程超时过期
 */

public class MineTripOutTimeFgt extends BaseFgt {
    @Bind(R.id.rv_data)
    RecyclerView mRvData;
    @Bind(R.id.ptr_frame)
    PtrFrameLayout mPtrFrame;

    private MineTripOutTimeAdapter mAdapter;

    private int pageSize = Integer.parseInt(UserManger.pageSize);

    private int targetPage = 1;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showLoadingContentDialog();
    }

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
                doHttp(RetrofitUtils.createApi(Mine.class).toPersonalStroke(pageSize, targetPage, "expired"), 1);

            }
        });

        LinearLayoutManager mRecyclerViewlayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        List<MineTripInfo> list = new ArrayList<>();


        mAdapter = new MineTripOutTimeAdapter(R.layout.item_transfer_mine_out_time_trip, list);

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
                        doHttp(RetrofitUtils.createApi(Mine.class).toPersonalStroke(pageSize, targetPage, "expired"), 2);
                    }
                });

            }
        }, mRvData);


        //设置布局管理器
        mRvData.setLayoutManager(mRecyclerViewlayoutManager);
        mRvData.setHasFixedSize(true);
   //     mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mRvData.setItemAnimator(new DefaultItemAnimator());
        //设置adapter
        mRvData.setAdapter(mAdapter);

        mRvData.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                new MaterialDialog(getActivity()).setMDMessage("是否确认删除该行程？").setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                    @Override
                    public void dialogBtnOnClick() {
                        showLoadingDialog(null);
                        doHttp(RetrofitUtils.createApi(Mine.class).deletePathByid(mAdapter.getItem(position).getPathId()), 3);
                    }
                }).show();
            }
        });
        setEmptyView(mAdapter, null);

    }

    @Override
    public void onUserVisible() {
        super.onUserVisible();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                targetPage = 1;
                doHttp(RetrofitUtils.createApi(Mine.class).toPersonalStroke(pageSize, targetPage, "expired"), 1);
            }
        }, 200);
    }

    @Override
    public void requestData() {
        doHttp(RetrofitUtils.createApi(Mine.class).toPersonalStroke(pageSize, targetPage, "expired"), 1);
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);

        switch (what) {
            case 1:
                mPtrFrame.refreshComplete();
                mAdapter.removeAll();
                List<MineTripInfo> packageorder = AppJsonUtil.getArrayList(result, "paths", MineTripInfo.class);
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
                List<MineTripInfo> packageorder2 = AppJsonUtil.getArrayList(result, "paths", MineTripInfo.class);
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
                doHttp(RetrofitUtils.createApi(Mine.class).toPersonalStroke(pageSize, targetPage, "expired"), 1);
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
