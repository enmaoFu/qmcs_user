package com.biaoyuan.transfer.ui.mine.refund;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.and.yzy.frame.adapter.recyclerview.BaseQuickAdapter;
import com.and.yzy.frame.adapter.recyclerview.listener.OnItemClickListener;
import com.and.yzy.frame.util.PtrInitHelper;
import com.and.yzy.frame.util.RetrofitUtils;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.adapter.RefoundAdapter;
import com.biaoyuan.transfer.base.BaseFgt;
import com.biaoyuan.transfer.config.UserManger;
import com.biaoyuan.transfer.domain.RefoundInfo;
import com.biaoyuan.transfer.http.Mine;
import com.biaoyuan.transfer.util.AppJsonUtil;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 退款售后
 */
public class MineRefundFgt extends BaseFgt {
    @Bind(R.id.rv_data)
    RecyclerView mRvData;
    @Bind(R.id.ptr_frame)
    PtrFrameLayout mPtrFrame;

    private RefoundAdapter mAdapter;

    private int pageSize = Integer.parseInt(UserManger.pageSize);

    private int targetPage = 1;

    private String state;

    @Override
    public int getLayoutId() {
        return R.layout.ptr_recyclerview;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        state = getArguments().getString("state");
        super.onActivityCreated(savedInstanceState);
        showLoadingContentDialog();
    }

    @Override
    public void initData() {
        PtrInitHelper.initPtr(getActivity(), mPtrFrame);

        mPtrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

                targetPage = 1;
                doHttp(RetrofitUtils.createApi(Mine.class).toRefundAfterSale(pageSize, targetPage, state), 1);
            }
        });

        LinearLayoutManager mRecyclerViewlayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        List<RefoundInfo> list = new ArrayList<>();

        mAdapter = new RefoundAdapter(R.layout.item_mine_refund_list, list,state);


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

                        Logger.v("targetPage=="+targetPage);
                        if (targetPage == 1) {
                            mAdapter.loadMoreEnd();
                            return;
                        }
                        doHttp(RetrofitUtils.createApi(Mine.class).toRefundAfterSale(pageSize, targetPage, state), 2);
                    }
                });

            }
        }, mRvData);

        //设置布局管理器
        mRvData.setLayoutManager(mRecyclerViewlayoutManager);
        mRvData.setHasFixedSize(true);
      //  mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mRvData.setItemAnimator(new DefaultItemAnimator());
        //设置adapter
        mRvData.setAdapter(mAdapter);

        mRvData.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                RefoundInfo item=mAdapter.getItem(position);

                Bundle bundle=new Bundle();
                bundle.putLong("orderId",item.getOrder().getOrderId());
                bundle.putInt("excepType",item.getOrderExcep().getExcepType());

                switch (item.getOrderExcep().getExcepType()) {

                    case 1:
                     //   helper.setTextViewText(R.id.tv_state, "取件拒收");

                        startActivity(MineRefundDetailAty.class,bundle);


                        break;
                    case 2:
                        //无偿取消

                        startActivity(MineRefundDetailAty.class,bundle);



                        break;

                    case 5:
                    //    helper.setTextViewText(R.id.tv_state, "快件丢失");
                        startActivity(MineRefundDetailAty.class,bundle);

                        break;
                    case 6:
                    //    helper.setTextViewText(R.id.tv_state, "快件破损");
                        startActivity(MineRefundDetailAty.class,bundle);
                        break;
                    case 9:
                        //有偿取消

                        startActivity(MineRefundDetailAty.class,bundle);

                        break;
                    case 10:
                     //   helper.setTextViewText(R.id.tv_state, "配送超时");


                        startActivity(MineRefundDetailAty.class,bundle);


                        break;


                }


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
                doHttp(RetrofitUtils.createApi(Mine.class).toRefundAfterSale(pageSize, targetPage, state), 1);
            }
        }, 500);
    }

    @Override
    public void requestData() {
        doHttp(RetrofitUtils.createApi(Mine.class).toRefundAfterSale(pageSize, targetPage, state), 1);
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);

        switch (what) {
            case 1:
                mPtrFrame.refreshComplete();
                mAdapter.removeAll();
                List<RefoundInfo> packageorder = AppJsonUtil.getArrayList(result, "userExcepHandleBeans", RefoundInfo.class);
                if (packageorder != null) {
                    mAdapter.setNewData(packageorder);
                    if (packageorder.size() < pageSize) {
                        mAdapter.loadMoreEnd();
                    }
                } else {
                    mAdapter.loadMoreEnd();
                    Logger.v("1==loadMoreEnd");
                }
                //增加页码
                targetPage++;

                break;
            case 2:

                //加载更多
                List<RefoundInfo> packageorder2 = AppJsonUtil.getArrayList(result, "userExcepHandleBeans", RefoundInfo.class);
                if (packageorder2 != null && packageorder2.size() > 0) {
                    mAdapter.addDatas(packageorder2);
                    mAdapter.loadMoreComplete();
                } else {
                    mAdapter.loadMoreEnd();
                    Logger.v("2==loadMoreEnd");
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
