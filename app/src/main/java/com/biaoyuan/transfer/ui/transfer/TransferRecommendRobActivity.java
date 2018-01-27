package com.biaoyuan.transfer.ui.transfer;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.base.BaseAty;
import com.biaoyuan.transfer.base.BaseFgt;
import com.biaoyuan.transfer.domain.ConditionItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Title  : 推荐传送抢单页面
 * Create : 2017/4/26
 * Author ：enmaoFu
 */
public class TransferRecommendRobActivity extends BaseAty {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tab)
    TabLayout mTab;
    @Bind(R.id.view_pager)
    ViewPager mViewPager;
    @Bind(R.id.tv_address)
    TextView mTvAddress;
    @Bind(R.id.tv_date)
    TextView mTvDate;
    @Bind(R.id.tv_time)
    TextView mTvTime;

    private List<BaseFgt> mFragments;
    private List<String> mTabsString;

    private ConditionItem mItem;

    @Override
    public int getLayoutId() {
        return R.layout.activity_transfer_search_result_layout;
    }

    @Override
    public void initData() {
        initToolbar(mToolbar, "推荐传送单");


        mFragments = new ArrayList<>();
        mTabsString = new ArrayList<>();

        mTabsString.add("网点远近");
        mTabsString.add("传送收益");
        mTabsString.add("取件时间");

        mItem = getIntent().getParcelableExtra("data");

        mTvDate.setText(mItem.getDate());
        mTvTime.setText(mItem.getHour() + "出发");


        mTvAddress.setText(mItem.getStartAddress() + "-" + mItem.getEndAddress());

        addFgt(new TransferRobFgt(), "3");
        addFgt(new TransferRobFgt(), "2");
        addFgt(new TransferRobFgt(), "1");

        mTab.setTabMode(TabLayout.MODE_FIXED);


        TransferRecommendRobActivity.pageAdapter pageAdapter = new TransferRecommendRobActivity.pageAdapter(getSupportFragmentManager());
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(pageAdapter);
        mTab.setupWithViewPager(mViewPager);

    }

    private void addFgt(TransferRobFgt transferRobFgt, String s) {


        Bundle bundle = new Bundle();
        //条件查询
        bundle.putString("sortNumber", "0");
        bundle.putString("filterSort", s);
        bundle.putParcelable("data", mItem);
        transferRobFgt.setArguments(bundle);

        mFragments.add(transferRobFgt);
    }

    @Override
    public void requestData() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        // super.onSaveInstanceState(outState, outPersistentState);
    }

    class pageAdapter extends FragmentStatePagerAdapter {
        public pageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabsString.get(position);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // super.onSaveInstanceState(outState);
    }

}
