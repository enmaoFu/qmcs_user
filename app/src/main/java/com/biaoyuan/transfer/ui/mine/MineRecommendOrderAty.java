package com.biaoyuan.transfer.ui.mine;

import android.graphics.Color;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.and.yzy.frame.adapter.recyclerview.BaseQuickAdapter;
import com.and.yzy.frame.adapter.recyclerview.listener.OnItemClickListener;
import com.and.yzy.frame.util.PtrInitHelper;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.adapter.TransferRecyclerViewAdapter;
import com.biaoyuan.transfer.base.BaseAty;
import com.biaoyuan.transfer.domain.TransferRecItem;
import com.biaoyuan.transfer.ui.transfer.TransferOrderDetailsActivity;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Title  :推荐传送单
 * Create : 2017/4/26
 * Author ：chen
 */

public class MineRecommendOrderAty extends BaseAty {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tv_day)
    TextView mTvDay;
    @Bind(R.id.tv_month)
    TextView mTvMonth;
    @Bind(R.id.time_bg)
    LinearLayout mTimeBg;
    @Bind(R.id.tv_address)
    TextView mTvAddress;
    @Bind(R.id.tv_time)
    TextView mTvTime;
    @Bind(R.id.rv_data)
    RecyclerView mTransferRecyclerview;
    @Bind(R.id.ptr_frame)
    PtrFrameLayout mPtrFrame;

    private TransferRecyclerViewAdapter adapter;

    //数据源
    private List<TransferRecItem> itemList;
    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_recommend_list_layout;
    }

    @Override
    public void initData() {
        initToolbar(mToolbar,"推荐传送单");

        PtrInitHelper.initPtr(this, mPtrFrame);

        mPtrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPtrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPtrFrame.refreshComplete();
                    }
                }, 2000);
            }
        });

        LinearLayoutManager mRecyclerViewlayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //实例化MyRecyclerViewAdapter，并加入数据
        adapter = new TransferRecyclerViewAdapter(R.layout.item_transfer_main, setData());
        //设置布局管理器
        mTransferRecyclerview.setLayoutManager(mRecyclerViewlayoutManager);
        //设置间隔样式
        mTransferRecyclerview.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(MineRecommendOrderAty.this)
                        .color(Color.parseColor(getResources().getString(R.string.parseColor)))
                        .sizeResId(R.dimen.size_0_5p)
                        .build());
        mTransferRecyclerview.setHasFixedSize(true);

        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mTransferRecyclerview.setItemAnimator(new DefaultItemAnimator());

        //设置adapter
        mTransferRecyclerview.setAdapter(adapter);
        mTransferRecyclerview.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(TransferOrderDetailsActivity.class,null);
            }
        });
    }

    /**
     * 最新传送单的recyclerview数据源
     * @return
     */
    public List<TransferRecItem> setData() {
        itemList = new ArrayList<>();
        TransferRecItem ie = null;
        for (int i = 0; i < 20; i++) {
            ie = new TransferRecItem();
            itemList.add(ie);
        }
        return itemList;
    }
    @Override
    public void requestData() {

    }
    @Override
    public void onError(Call<ResponseBody> call, Throwable t, int what) {
        super.onError(call, t, what);
        if (mPtrFrame != null) {
            mPtrFrame.refreshComplete();
        }
    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onFailure(result, call, response, what);
        if (mPtrFrame != null) {
            mPtrFrame.refreshComplete();
        }
    }
}
