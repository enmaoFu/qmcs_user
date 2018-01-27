package com.biaoyuan.transfer.ui.send;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.and.yzy.frame.adapter.recyclerview.BaseQuickAdapter;
import com.and.yzy.frame.adapter.recyclerview.listener.SimpleClickListener;
import com.and.yzy.frame.util.RetrofitUtils;
import com.and.yzy.frame.view.dialog.Effectstype;
import com.and.yzy.frame.view.dialog.MaterialDialog;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.adapter.SendCommonAddressAdapter;
import com.biaoyuan.transfer.base.BaseAty;
import com.biaoyuan.transfer.domain.AddAddressItem;
import com.biaoyuan.transfer.domain.SendCommonAddressInfo;
import com.biaoyuan.transfer.http.Send;
import com.biaoyuan.transfer.util.AppJsonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Title  : 常用地址
 * Create : 2017/6/01
 * Author ：enmaoFu
 */
public class SendCommonAddressActivity extends BaseAty {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.rv_data)
    RecyclerView mRecyclerView;
    @Bind(R.id.tv_commit)
    TextView mTvCommit;

    private SendCommonAddressInfo mAddressInfo;

    //适配器
    private SendCommonAddressAdapter adapter;
    //recyclerView布局管理器
    private RecyclerView.LayoutManager mRecyclerViewlayoutManager;
    //1发件 2收件
    private int mType;

    @Override
    public int getLayoutId() {
        return R.layout.activity_send_common_address;
    }

    @Override
    public void initData() {
        initToolbar(mToolbar, "常用地址");
        mType = getIntent().getIntExtra("type", 1);

        if (mType == 2) {
            mTvCommit.setText("添加新收件人");
        } else {
            mTvCommit.setText("添加新寄件人");
        }


        //实例化布局管理器
        mRecyclerViewlayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //实例化适配器
        adapter = new SendCommonAddressAdapter(R.layout.item_send_common_address, new ArrayList<SendCommonAddressInfo>());

        setEmptyView(adapter, "暂无数据");

        //设置布局管理器
        mRecyclerView.setLayoutManager(mRecyclerViewlayoutManager);
        //设置间隔样式
        /*mTekeRecyclerview.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getActivity())
                        .color(Color.parseColor(getResources().getString(R.string.parseColor)))
                        .sizeResId(R.dimen.size_0_5p)
                        .build());*/
        //大小不受适配器影响
        mRecyclerView.setHasFixedSize(true);
        //设置加载动画类型
        //       adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        //设置删除动画类型
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置adapter
        mRecyclerView.setAdapter(adapter);

        mRecyclerView.addOnItemTouchListener(new SimpleClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                if (!getIntent().getBooleanExtra("click",true)){
                    return;
                }
                //   AppManger.getInstance().killActivity(SendAddAddressAty.class);

                SendCommonAddressInfo addressInfo = SendCommonAddressActivity.this.adapter.getItem(position);

                AddAddressItem addAddressItem = new AddAddressItem();
                addAddressItem.setName(addressInfo.getAddressName());
                addAddressItem.setPhone(addressInfo.getAddressPhone());
                addAddressItem.setAddress(addressInfo.getAddressAddress());
                addAddressItem.setLat(addressInfo.getAddressLat());
                addAddressItem.setLng(addressInfo.getAddressIng());
                addAddressItem.setAreaCode(addressInfo.getAddressAreaCode());
                addAddressItem.setAreaId(addressInfo.getAddressAreaId());

                Intent intent = getIntent();
                intent.putExtra("data", addAddressItem);
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

            }

            @Override
            public void onItemChildClick(final BaseQuickAdapter adapter, View view, final int position) {

                switch (view.getId()) {
                    case R.id.tv_edit:
                        mAddressInfo = SendCommonAddressActivity.this.adapter.getItem(position);
                        Bundle bundle = new Bundle();

                        bundle.putParcelable("data", mAddressInfo);
                        bundle.putInt("type", mType);
                        startActivityForResult(CommonAddAddressAty.class, bundle, 1);

                        break;
                    case R.id.cb_save:
                        mAddressInfo = SendCommonAddressActivity.this.adapter.getItem(position);
                        if (SendCommonAddressActivity.this.adapter.getSelectAddressInfo() == null) {
                            showLoadingDialog(null);
                            doHttp(RetrofitUtils.createApi(Send.class).upDefaultAddress(mType,
                                    mAddressInfo.getAddressId()), 3);
                        } else {
                            //如果是当前选中的不做操作
                            if (SendCommonAddressActivity.this.adapter.getSelectAddressInfo().getAddressId()
                                    == SendCommonAddressActivity.this.adapter.getItem(position).getAddressId()
                                    ) {
                                return;
                            }
                            showLoadingDialog(null);
                            doHttp(RetrofitUtils.createApi(Send.class).upDefaultAddress(mType,
                                    mAddressInfo.getAddressId()), 3);
                        }


                        break;
                    case R.id.tv_dalete:

                        new MaterialDialog(SendCommonAddressActivity.this)
                                .setMDEffect(Effectstype.Shake)
                                .setMDMessage("是否删除该地址")
                                .setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                                    @Override
                                    public void dialogBtnOnClick() {
                                        mAddressInfo = SendCommonAddressActivity.this.adapter.getItem(position);
                                        showLoadingDialog(null);
                                        doHttp(RetrofitUtils.createApi(Send.class).updateAddressIsDel(
                                                mAddressInfo.getAddressId()), 2);
                                    }
                                }).show();

                        break;
                }


            }

            @Override
            public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            doHttp(RetrofitUtils.createApi(Send.class).addressList(mType), 1);
        }
    }

    @OnClick({R.id.tv_commit})
    @Override
    public void btnClick(View view) {
        super.btnClick(view);
        switch (view.getId()) {
            case R.id.tv_commit:
                Bundle bundle = new Bundle();
                bundle.putInt("type", mType);
                startActivityForResult(CommonAddAddressAty.class, bundle, 1);

                break;

        }
    }

    @Override
    public boolean setIsInitRequestData() {
        return true;
    }

    @Override
    public void requestData() {
        showLoadingContentDialog();


        doHttp(RetrofitUtils.createApi(Send.class).addressList(mType), 1);
    }


    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what) {
            case 1:
                adapter.removeAll();
                List<SendCommonAddressInfo> sendCommonAddressInfos = AppJsonUtil.getArrayList(result, "addressList", SendCommonAddressInfo.class);
                if (sendCommonAddressInfos != null && sendCommonAddressInfos.size() > 0) {
                    adapter.addDatas(sendCommonAddressInfos);
                }


                break;
            case 2:
                //删除地址
                showToast("删除成功");
                adapter.remove(mAddressInfo);

                break;
            case 3:
                if (adapter.getSelectAddressInfo() != null) {
                    adapter.getSelectAddressInfo().setAddressIsDefault(0);
                }
                adapter.remove(mAddressInfo);
                mAddressInfo.setAddressIsDefault(1);
                adapter.addData(0, mAddressInfo);
                adapter.notifyDataSetChanged();

                break;

        }
    }


}
