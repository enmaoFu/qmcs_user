package com.biaoyuan.transfer.ui.mine;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.and.yzy.frame.adapter.recyclerview.BaseQuickAdapter;
import com.and.yzy.frame.adapter.recyclerview.listener.OnItemChildClickListener;
import com.and.yzy.frame.adapter.recyclerview.listener.OnItemClickListener;
import com.and.yzy.frame.util.PtrInitHelper;
import com.and.yzy.frame.util.RetrofitUtils;
import com.and.yzy.frame.view.dialog.Effectstype;
import com.and.yzy.frame.view.dialog.MaterialDialog;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.adapter.MineCommonBankAdapter;
import com.biaoyuan.transfer.base.BaseAty;
import com.biaoyuan.transfer.domain.MineCommonBankInfo;
import com.biaoyuan.transfer.http.Mine;
import com.biaoyuan.transfer.util.AppJsonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * @author ：enmaoFu
 * @title : 常用银行卡页面
 * @create : 2017/07/05
 */
public class MineCommonBankActivity extends BaseAty {

    @Bind(R.id.rv_data)
    RecyclerView mRecyclerView;
    @Bind(R.id.ptr_frame)
    PtrFrameLayout mPtrFrame;

    private MineCommonBankAdapter mAdapter;

    //用来标记是否在加载
    private boolean isLoading = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mien_common_bank;
    }

    @Override
    public void initData() {

        PtrInitHelper.initPtr(this, mPtrFrame);

        mPtrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mAdapter.loadMoreComplete();
                doHttp(RetrofitUtils.createApi(Mine.class).cardList(), 1);
            }
        });

        //实例化布局管理器
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //实例化适配器
        mAdapter = new MineCommonBankAdapter(R.layout.item_mien_common_bank, new ArrayList<MineCommonBankInfo>());
        //设置布局管理器
        mRecyclerView.setLayoutManager(layoutManager);
        //设置间隔样式
        /*mTekeRecyclerview.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getActivity())
                        .color(Color.parseColor(getResources().getString(R.string.parseColor)))
                        .sizeResId(R.dimen.size_0_5p)
                        .build());*/
        //大小不受适配器影响
        mRecyclerView.setHasFixedSize(true);
        //设置加载动画类型
        //      mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        //设置删除动画类型
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置没有数据的页面
        setEmptyView(mAdapter, null);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                String cardNo = mAdapter.getItem(position).getCardNo();
                String cardName = mAdapter.getItem(position).getCardName();
                Intent intent = new Intent();
                intent.putExtra("cardNo", cardNo);
                intent.putExtra("cardName", cardName);
                setResult(1, intent);
                finish();
            }
        });
        mRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.delete:
                        final long cardId = mAdapter.getItem(position).getCardId();
                        MaterialDialog dialog = new MaterialDialog(MineCommonBankActivity.this);
                        dialog.setMDMessage("是否删除该银行卡?").setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                            @Override
                            public void dialogBtnOnClick() {

                                showLoadingDialog(null);
                                doHttp(RetrofitUtils.createApi(Mine.class).cardList(cardId), 2);

                            }
                        }).setMDEffect(Effectstype.Shake).show();
                        break;
                }
            }
        });
        //设置adapter
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public boolean setIsInitRequestData() {
        return true;
    }

    @Override
    public void requestData() {
        showLoadingContentDialog();
        doHttp(RetrofitUtils.createApi(Mine.class).cardList(), 1);
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what) {
            case 1:

                mPtrFrame.refreshComplete();
                List<MineCommonBankInfo> mineCommonBankInfoList = AppJsonUtil.getArrayList(result, MineCommonBankInfo.class);
                mAdapter.removeAll();

                if (mineCommonBankInfoList != null) {
                    mAdapter.setNewData(mineCommonBankInfoList);
                }

                break;

            case 2:

                doHttp(RetrofitUtils.createApi(Mine.class).cardList(), 1);

                break;
        }
    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onFailure(result, call, response, what);
        isLoading = false;
        if (mPtrFrame != null) {
            mPtrFrame.refreshComplete();
            mAdapter.loadMoreComplete();
        }
    }

    @Override
    public void onError(Call<ResponseBody> call, Throwable t, int what) {
        super.onError(call, t, what);
        isLoading = false;
        if (mPtrFrame != null) {
            mPtrFrame.refreshComplete();
            mAdapter.loadMoreComplete();
        }
    }

    @OnClick({R.id.returns, R.id.right})
    @Override
    public void btnClick(View view) {
        switch (view.getId()) {
            case R.id.returns:
                finish();
                break;
            case R.id.right:
                startActivityForResult(MineAddBankActivity.class, null, 1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (data.getStringExtra("key").equals("success")) {
                showLoadingContentDialog();
                doHttp(RetrofitUtils.createApi(Mine.class).cardList(), 1);
            }
        }
    }
}
