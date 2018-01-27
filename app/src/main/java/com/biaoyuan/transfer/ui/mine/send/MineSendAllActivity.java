package com.biaoyuan.transfer.ui.mine.send;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.and.yzy.frame.util.DensityUtils;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.base.BaseAty;
import com.biaoyuan.transfer.base.BaseFgt;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Title  :我的发件所有界面
 * Create : 2017/4/25
 * Author ：chen
 */
public class MineSendAllActivity extends BaseAty {
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
        initToolbar(mToolbar, "我的发件");

        mFragments = new ArrayList<>();
        mTabsString = new ArrayList<>();
        mTabsString.add("全部");
        mTabsString.add("待支付");
        mTabsString.add("待取件");
        mTabsString.add("待签收");
        mTabsString.add("待评价");


        addFgt(new MineSendFgt(), 0);
        addFgt(new MineSendFgt(), 1);
        addFgt(new MineSendFgt(), 2);
        addFgt(new MineSendFgt(), 3);
        addFgt(new MineSendFgt(), 4);


        pageAdapter pageAdapter = new pageAdapter(getSupportFragmentManager());
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setAdapter(pageAdapter);
        mTab.setupWithViewPager(mViewPager);

        if (DensityUtils.getScreenWidth(this) < 750) {
            mTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        } else {
            mTab.setTabMode(TabLayout.MODE_FIXED);
        }

    }

    private void addFgt(BaseFgt fgt, int type) {
        Bundle bundle = new Bundle();

        bundle.putInt("type", type);
        fgt.setArguments(bundle);
        mFragments.add(fgt);
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
