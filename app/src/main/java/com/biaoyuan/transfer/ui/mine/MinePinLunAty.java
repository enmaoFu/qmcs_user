package com.biaoyuan.transfer.ui.mine;

import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.and.yzy.frame.adapter.recyclerview.BaseQuickAdapter;
import com.and.yzy.frame.util.AppManger;
import com.and.yzy.frame.util.PtrInitHelper;
import com.and.yzy.frame.util.RetrofitUtils;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.adapter.PinLunAdapter;
import com.biaoyuan.transfer.base.BaseAty;
import com.biaoyuan.transfer.config.UserManger;
import com.biaoyuan.transfer.domain.PinLunItem;
import com.biaoyuan.transfer.http.Mine;
import com.biaoyuan.transfer.ui.mine.send.MineAddPinLunAty;
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
 * 我的评论
 */

public class MinePinLunAty extends BaseAty {
    @Bind(R.id.rv_data)
    RecyclerView mRvData;
    @Bind(R.id.ptr_frame)
    PtrFrameLayout mPtrFrame;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    private PinLunAdapter mAdapter;


    private int pageSize = Integer.parseInt(UserManger.pageSize);

    private int targetPage = 1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_pinlun;
    }

    @Override
    public void initData() {
        initToolbar(mToolbar, "我的评价");

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                AppManger.getInstance().killActivity(MineAddPinLunAty.class);
            }
        }, 500);


        PtrInitHelper.initPtr(this, mPtrFrame);

        mPtrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                targetPage = 1;
                doHttp(RetrofitUtils.createApi(Mine.class).toMyAssessment(pageSize, targetPage), 1);
            }
        });

        LinearLayoutManager mRecyclerViewlayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        List<PinLunItem> list = new ArrayList<>();

        mAdapter = new PinLunAdapter(R.layout.item_mine_pinlun, list);


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
                        doHttp(RetrofitUtils.createApi(Mine.class).toMyAssessment(pageSize, targetPage), 2);
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


        setEmptyView(mAdapter, null);
    }

    @Override
    public boolean setIsInitRequestData() {
        return true;
    }

    @Override
    public void requestData() {
        showLoadingContentDialog();
        doHttp(RetrofitUtils.createApi(Mine.class).toMyAssessment(pageSize, targetPage), 1);
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);

        switch (what) {
            case 1:
                mPtrFrame.refreshComplete();
                mAdapter.removeAll();
                List<PinLunItem> packageorder = AppJsonUtil.getArrayList(result, "comments", PinLunItem.class);
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
                List<PinLunItem> packageorder2 = AppJsonUtil.getArrayList(result, "comments", PinLunItem.class);
                if (packageorder2 != null && packageorder2.size() > 0) {
                    mAdapter.addDatas(packageorder2);
                    mAdapter.loadMoreComplete();
                } else {
                    mAdapter.loadMoreEnd();
                }
                //增加页码
                targetPage++;
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
