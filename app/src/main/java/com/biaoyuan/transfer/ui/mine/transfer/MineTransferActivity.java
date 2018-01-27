package com.biaoyuan.transfer.ui.mine.transfer;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.base.BaseAty;
import com.biaoyuan.transfer.base.BaseFgt;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Title  :我的传送
 * Create : 2017/4/25
 * Author ：chen
 */

public class MineTransferActivity extends BaseAty {
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
        return R.layout.tab_viewpager_layout;
    }

    @Override
    public void initData() {
        initToolbar(mToolbar, "我的传送");

        mFragments = new ArrayList<>();
        mTabsString = new ArrayList<>();
        mTabsString.add("待取件");
        mTabsString.add("进行中");
        mTabsString.add("已完成");
        mTabsString.add("异常件");


        addFgt(new MineTransferFgt(), 1);
        addFgt(new MineTransferFgt(), 2);
        addFgt(new MineTransferFgt(), 3);
        addFgt(new MineTransferFgt(), 4);


        pageAdapter pageAdapter = new pageAdapter(getSupportFragmentManager());
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(pageAdapter);
        mTab.setupWithViewPager(mViewPager);
        mTab.setTabMode(TabLayout.MODE_FIXED);

    }


    private void addFgt(BaseFgt baseFgt, int type) {


        Bundle bundle = new Bundle();
        //条件查询
        bundle.putInt("type", type);
        baseFgt.setArguments(bundle);
        mFragments.add(baseFgt);
    }


    @Override
    public void requestData() {

    }

    @Override
    public boolean setIsInitRequestData() {
        return false;
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
