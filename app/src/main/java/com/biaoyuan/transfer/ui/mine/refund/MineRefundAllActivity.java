package com.biaoyuan.transfer.ui.mine.refund;

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
import com.biaoyuan.transfer.util.TabLayoutHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Title  :退款售后
 * Create : 2017/4/25
 * Author ：chen
 */
public class MineRefundAllActivity extends BaseAty {
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
        initToolbar(mToolbar, "退款售后");

        mFragments = new ArrayList<>();
        mTabsString = new ArrayList<>();

        mTabsString.add("退款订单");
        mTabsString.add("赔偿订单");


        addFgt(new MineRefundFgt(), "refund");
        addFgt(new MineRefundFgt(), "compensate");


        pageAdapter pageAdapter = new pageAdapter(getSupportFragmentManager());
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(pageAdapter);
        mTab.setupWithViewPager(mViewPager);

        mTab.post(new Runnable() {
            @Override
            public void run() {

                TabLayoutHelper.setIndicator(mTab, 50, 50);


            }
        });
    }

    private void addFgt(BaseFgt baseFgt, String state) {

        Bundle bundle = new Bundle();
        bundle.putString("state", state);
        baseFgt.setArguments(bundle);
        mFragments.add(baseFgt);

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
