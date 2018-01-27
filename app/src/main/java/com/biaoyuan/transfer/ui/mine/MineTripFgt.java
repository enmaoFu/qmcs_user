package com.biaoyuan.transfer.ui.mine;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.and.yzy.frame.adapter.recyclerview.BaseQuickAdapter;
import com.and.yzy.frame.adapter.recyclerview.listener.OnItemClickListener;
import com.and.yzy.frame.util.DateTool;
import com.and.yzy.frame.util.PtrInitHelper;
import com.and.yzy.frame.util.RetrofitUtils;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.adapter.MineTripAdapter;
import com.biaoyuan.transfer.base.BaseFgt;
import com.biaoyuan.transfer.config.UserManger;
import com.biaoyuan.transfer.domain.ConditionItem;
import com.biaoyuan.transfer.domain.MineTripInfo;
import com.biaoyuan.transfer.http.Mine;
import com.biaoyuan.transfer.ui.transfer.TransferRecommendRobActivity;
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
 * 我的行程
 */

public class MineTripFgt extends BaseFgt {
    @Bind(R.id.rv_data)
    RecyclerView mRvData;
    @Bind(R.id.ptr_frame)
    PtrFrameLayout mPtrFrame;

    private MineTripAdapter mAdapter;

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
                doHttp(RetrofitUtils.createApi(Mine.class).toPersonalStroke(pageSize, targetPage, "haveInHand"), 1);

            }
        });

        LinearLayoutManager mRecyclerViewlayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        List<MineTripInfo> list = new ArrayList<>();


        mAdapter = new MineTripAdapter(R.layout.item_transfer_mine_trip, list);

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
                        doHttp(RetrofitUtils.createApi(Mine.class).toPersonalStroke(pageSize, targetPage, "haveInHand"), 2);
                    }
                });

            }
        }, mRvData);
        mRvData.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                MineTripInfo info = mAdapter.getItem(position);

                ConditionItem conditionItem = new ConditionItem();

                conditionItem.setDate(DateTool.timesToStrTime(info.getPathDepartureTime() + "", "yyyy.MM.dd"));
                conditionItem.setHour(DateTool.timesToStrTime(info.getPathDepartureTime() + "", "HH:mm"));
                conditionItem.setTime(info.getPathDepartureTime() + "");
                conditionItem.setStartAddress(info.getPathDeparture().getEntCity() + info.getPathDeparture().getEntArea()+info.getPathDeparture().getStreet());
                conditionItem.setEndAddress(info.getPathDestination().getEntCity() + info.getPathDestination().getEntArea()+info.getPathDestination().getStreet());

                //判断是否有街道id
                if (info.getParentPathDepartureId() == 0) {
                    //只选择了区

                    conditionItem.setStartCityCode(info.getPathDepartureId());
                    conditionItem.setStartStreetCode(0);
                } else {
                    conditionItem.setStartCityCode(info.getParentPathDepartureId());
                    conditionItem.setStartStreetCode(info.getPathDepartureId());
                }

                if (info.getParentPathDestinationId() == 0) {
                    //只选择了区

                    conditionItem.setEndCityCode(info.getPathDestinationId());
                    conditionItem.setEndStreetCode(0);
                } else {
                    conditionItem.setEndCityCode(info.getParentPathDestinationId());
                    conditionItem.setEndStreetCode(info.getPathDestinationId());
                }

                Bundle bundle1 = new Bundle();

                bundle1.putParcelable("data", conditionItem);


                startActivity(TransferRecommendRobActivity.class, bundle1);
            }
        });

        //设置布局管理器
        mRvData.setLayoutManager(mRecyclerViewlayoutManager);
        mRvData.setHasFixedSize(true);
   //     mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mRvData.setItemAnimator(new DefaultItemAnimator());
        //设置adapter
        mRvData.setAdapter(mAdapter);


        setEmptyView(mAdapter,null);
    }


    @Override
    public void onUserVisible() {
        super.onUserVisible();
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                targetPage = 1;
                doHttp(RetrofitUtils.createApi(Mine.class).toPersonalStroke(pageSize, targetPage, "haveInHand"), 1);
            }
        },400);
    }

    @Override
    public void requestData() {
        doHttp(RetrofitUtils.createApi(Mine.class).toPersonalStroke(pageSize, targetPage, "haveInHand"), 1);
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
