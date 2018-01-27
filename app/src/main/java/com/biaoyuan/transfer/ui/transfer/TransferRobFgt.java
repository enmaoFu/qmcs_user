package com.biaoyuan.transfer.ui.transfer;

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
import com.biaoyuan.transfer.adapter.TransferRecyclerViewAdapter;
import com.biaoyuan.transfer.base.BaseFgt;
import com.biaoyuan.transfer.config.UserManger;
import com.biaoyuan.transfer.domain.ConditionItem;
import com.biaoyuan.transfer.domain.TransferRecItem;
import com.biaoyuan.transfer.http.Transfer;
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
 * Title  : 传送抢单页面的布局fragment
 * Create : 2017/4/26
 * Author ：enmaoFu
 */
public class TransferRobFgt extends BaseFgt {

    @Bind(R.id.rv_data)
    RecyclerView mRvData;
    @Bind(R.id.ptr_frame)
    PtrFrameLayout mPtrFrame;

    //适配器
    private TransferRecyclerViewAdapter adapter;
    //recyclerView布局管理器
    private RecyclerView.LayoutManager mRecyclerViewlayoutManager;


    //条件筛选
    private String sortNumber = "3";
    private String filterSort = null;

    private String departureTime = null;
    private String outregionId = null;
    private String outstreetId = null;
    private String entregionId = null;
    private String entstreetId = null;


    private String pageSize = UserManger.pageSize;
    private int targetPage = 1;

    private ConditionItem mItem = null;

    @Override
    public int getLayoutId() {
        return R.layout.ptr_recyclerview;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        sortNumber = bundle.getString("sortNumber", "3");
        filterSort = bundle.getString("filterSort", null);
        mItem = bundle.getParcelable("data");

        Logger.v("mitem==" + (mItem == null));
        if (mItem != null) {
            departureTime = mItem.getTime();
            outregionId = mItem.getStartCityCode() + "";
            entregionId = mItem.getEndCityCode() + "";

            if (mItem.getStartStreetCode() != 0) {
                outstreetId = mItem.getStartStreetCode() + "";
            }
            if (mItem.getEndStreetCode() != 0) {
                entstreetId = mItem.getEndStreetCode() + "";
            }


        }
        super.onActivityCreated(savedInstanceState);
        showLoadingContentDialog();

    }

    @Override
    public boolean setIsInitRequestData() {
        return true;

    }


    @Override
    public void initData() {
        PtrInitHelper.initPtr(getActivity(), mPtrFrame);

        mPtrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

                targetPage = 1;
                doHttp(RetrofitUtils.createApi(Transfer.class).packageView(sortNumber, pageSize, "1", UserManger.getLng(), UserManger.getLat(), departureTime, outregionId, outstreetId, entregionId, entstreetId, filterSort), 1);

            }
        });


        mRecyclerViewlayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        //实例化MyRecyclerViewAdapter，并加入数据
        adapter = new TransferRecyclerViewAdapter(R.layout.item_transfer_main, new ArrayList<TransferRecItem>());
        //设置布局管理器
        mRvData.setLayoutManager(mRecyclerViewlayoutManager);

        mRvData.setHasFixedSize(true);
     //   adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mRvData.setItemAnimator(new DefaultItemAnimator());
        //设置adapter
        mRvData.setAdapter(adapter);
        //上拉加载更多
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (mRvData==null){
                    return;
                }
                mRvData.post(new Runnable() {
                    @Override
                    public void run() {

                        if (targetPage == 1) {
                            adapter.loadMoreEnd();
                            return;
                        }

                        doHttp(RetrofitUtils.createApi(Transfer.class).packageView(sortNumber, pageSize, "" + targetPage, UserManger.getLng(), UserManger.getLat(), departureTime, outregionId, outstreetId, entregionId, entstreetId, filterSort), 2);

                    }
                });

            }
        }, mRvData);

        mRvData.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

                TransferRecItem recItem = (TransferRecItem) adapter.getItem(position);

                Bundle bundle = new Bundle();
                bundle.putString("publishId", recItem.getPublishId() + "");
                startActivity(TransferOrderDetailsActivity.class, bundle);
            }
        });
        setEmptyView(adapter, null);
    }


    @Override
    public void requestData() {
        doHttp(RetrofitUtils.createApi(Transfer.class).packageView(sortNumber, pageSize, "1", UserManger.getLng(), UserManger.getLat(), departureTime, outregionId, outstreetId, entregionId, entstreetId, filterSort), 1);

    }

    @Override
    public void onUserVisible() {
        super.onUserVisible();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (adapter != null) {
                    targetPage = 1;
                    doHttp(RetrofitUtils.createApi(Transfer.class).packageView(sortNumber, pageSize, "1", UserManger.getLng(), UserManger.getLat(), departureTime, outregionId, outstreetId, entregionId, entstreetId, filterSort), 1);

                }
            }
        }, 500);

    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what) {
            case 1:

                mPtrFrame.refreshComplete();
                adapter.removeAll();
                List<TransferRecItem> packageorder = AppJsonUtil.getArrayList(result, "packageorder", TransferRecItem.class);
                if (packageorder != null) {
                    adapter.setNewData(packageorder);
                    if (packageorder.size() < Integer.parseInt(pageSize)) {
                        adapter.loadMoreEnd();
                    }
                } else {
                    adapter.loadMoreEnd();
                }
                //增加页码
                targetPage++;

                break;
            case 2:

                //加载更多
                List<TransferRecItem> packageorder2 = AppJsonUtil.getArrayList(result, "packageorder", TransferRecItem.class);
                if (packageorder2 != null && packageorder2.size() > 0) {
                    adapter.addDatas(packageorder2);
                    adapter.loadMoreComplete();
                } else {
                    adapter.loadMoreEnd();
                }
                //增加页码
                targetPage++;

                break;
        }
    }

    @Override
    public void onError(Call<ResponseBody> call, Throwable t, int what) {
        super.onError(call, t, what);
        if (mPtrFrame != null) {
            mPtrFrame.refreshComplete();
            adapter.loadMoreComplete();
        }
    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onFailure(result, call, response, what);
        if (mPtrFrame != null) {
            mPtrFrame.refreshComplete();
            adapter.loadMoreComplete();
        }
    }

}
