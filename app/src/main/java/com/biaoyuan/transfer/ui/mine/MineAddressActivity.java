package com.biaoyuan.transfer.ui.mine;

import android.graphics.Color;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.and.yzy.frame.adapter.recyclerview.BaseQuickAdapter;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.adapter.AddressAdapter;
import com.biaoyuan.transfer.base.BaseAty;
import com.biaoyuan.transfer.domain.AddressInfo;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Title  :常用地址
 * Create : 2017/4/26
 * Author ：chen
 */
public class MineAddressActivity extends BaseAty {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.rv_data)
    RecyclerView mRvData;

    private AddressAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_address_list;
    }

    @Override
    public void initData() {
        initToolbar(mToolbar, "常用地址");

        LinearLayoutManager mRecyclerViewlayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        List<AddressInfo> list = new ArrayList<>();
        list.add(new AddressInfo());
        list.add(new AddressInfo());
        list.add(new AddressInfo());
        list.add(new AddressInfo());


        mAdapter = new AddressAdapter(R.layout.item_address, list);

        //设置间隔样式
        mRvData.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(MineAddressActivity.this)
                        .color(Color.parseColor(getResources().getString(R.string.parseColor)))
                        .sizeResId(R.dimen.size_0_5p)
                        .build());
        //设置布局管理器
        mRvData.setLayoutManager(mRecyclerViewlayoutManager);
        mRvData.setHasFixedSize(true);
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mRvData.setItemAnimator(new DefaultItemAnimator());
        //设置adapter
        mRvData.setAdapter(mAdapter);
    }

    @OnClick({R.id.tv_add_address})
    @Override
    public void btnClick(View view) {
        super.btnClick(view);
        switch(view.getId()){
            case R.id.tv_add_address:
                startActivity(MineAddAddressActivity.class,null);
                break;

        }
    }

    @Override
    public void requestData() {

    }
}
