package com.biaoyuan.transfer.ui.transfer;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.and.yzy.frame.util.DensityUtils;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.base.BaseAty;
import com.biaoyuan.transfer.base.BaseFgt;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Title  : 传送抢单页面
 * Create : 2017/4/26
 * Author ：enmaoFu
 */
public class TransferRobActivity extends BaseAty {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tab)
    TabLayout mTab;
    @Bind(R.id.view_pager)
    ViewPager mViewPager;

    private List<BaseFgt> mFragments;
    private List<String> mTabsString;

    @Override
    public int getLayoutId() {
        return R.layout.activity_transfer_layout;
    }

    @Override
    public void initData() {
        initToolbar(mToolbar, "传送抢单");


        mFragments = new ArrayList<>();
        mTabsString = new ArrayList<>();

        mTabsString.add("取件网点");
        mTabsString.add("收件网点");
        mTabsString.add("传送收益");
        mTabsString.add("取件时间");


        addFgt(new TransferRobFgt(), "3");
        addFgt(new TransferRobFgt(), "4");
        addFgt(new TransferRobFgt(), "2");
        addFgt(new TransferRobFgt(), "1");


        if (DensityUtils.getScreenWidth(this) < 720) {
            mTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        } else {
            mTab.setTabMode(TabLayout.MODE_FIXED);
        }

        TransferRobActivity.pageAdapter pageAdapter = new TransferRobActivity.pageAdapter(getSupportFragmentManager());
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(pageAdapter);
        mTab.setupWithViewPager(mViewPager);

    }

    private void addFgt(TransferRobFgt transferRobFgt, String s) {


        Bundle bundle = new Bundle();
        bundle.putString("sortNumber", s);
        transferRobFgt.setArguments(bundle);

        mFragments.add(transferRobFgt);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_search) {
            startActivityForResult(TransferSearchAty.class, null, 1);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu_search, menu);
        return super.onCreateOptionsMenu(menu);
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
