package com.biaoyuan.transfer.ui.login;

import android.Manifest;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.and.yzy.frame.transformer.BGAPageTransformer;
import com.and.yzy.frame.transformer.TransitionEffect;
import com.and.yzy.frame.util.AppUtils;
import com.and.yzy.frame.util.SPUtils;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.base.BaseAty;
import com.biaoyuan.transfer.base.BaseFgt;
import com.biaoyuan.transfer.ui.MainAty;
import com.biaoyuan.transfer.util.ChooseAddresDataHelper;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Title  :启动页
 * Create : 2017/5/25
 * Author ：chen
 */

public class FirstAty extends BaseAty {


    private static final int REQUEST_CODE_PERMISSION_OTHER = 101;
    @Bind(R.id.view_pager)
    ViewPager mViewPager;
    @Bind(R.id.tv_go)
    TextView mTvGo;
    @Bind(R.id.rg_mian)
    RadioGroup mRgMian;
    @Bind(R.id.rl_home)
    RelativeLayout mRlHome;
    @Bind(R.id.rl_welcome)
    RelativeLayout mRlWelcome;
    @Bind(R.id.tv_commit)
    TextView mTvCommit;


    private List<BaseFgt> mFgts;

    @Override
    public int getLayoutId() {
        return R.layout.first_layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {

        //载入城市数据
        if (!ChooseAddresDataHelper.isLoad) {
            new ChooseAddresDataHelper().initAddressData();
        }

        //判断版本
        SPUtils spUtils = new SPUtils("code");
        int code = (int) spUtils.get("code", 0);
        if (AppUtils.getVersionCode(FirstAty.this) > code) {


            mFgts = new ArrayList<>();
            mFgts.add(new WelcomeOneFgt());
            mFgts.add(new WelcomeTwoFgt());
            mFgts.add(new WelcomeThreeFgt());

            MainFragmentAdapter fragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager());
            mViewPager.setAdapter(fragmentAdapter);
            mViewPager.setPageTransformer(true, BGAPageTransformer.getPageTransformer(TransitionEffect.Accordion));

            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

                    switch (position) {
                        case 0:
                            mRgMian.check(R.id.rb_01);
                            mTvCommit.setVisibility(View.GONE);
                            mTvGo.setVisibility(View.VISIBLE);
                            break;
                        case 1:
                            mRgMian.check(R.id.rb_02);
                            mTvCommit.setVisibility(View.GONE);
                            mTvGo.setVisibility(View.VISIBLE);
                            break;
                        case 2:
                            mRgMian.check(R.id.rb_03);
                            mTvCommit.setVisibility(View.VISIBLE);
                            mTvGo.setVisibility(View.GONE);
                            break;
                    }

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }


        Handler mHandle = new Handler();
        mHandle.postDelayed(new Runnable() {
            @Override
            public void run() {
                opCheckPermission();
            }
        }, 2000);


    }

    // 权限
    public void opCheckPermission() {


        // 申请多个权限。
        AndPermission.with(this)
                .requestCode(REQUEST_CODE_PERMISSION_OTHER)
                .permission(Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.CAMERA,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_SMS,
                        Manifest.permission.READ_PHONE_STATE

                )
                // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框，避免用户勾选不再提示。
                .rationale(new RationaleListener() {
                               @Override
                               public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                                   // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                                   AndPermission.rationaleDialog(FirstAty.this, rationale).show();
                               }
                           }
                )
                .send();


    }

    @OnClick({R.id.tv_commit, R.id.tv_go})
    @Override
    public void btnClick(View view) {
        super.btnClick(view);
        switch (view.getId()) {
            case R.id.tv_commit:
            case R.id.tv_go:
                goAty();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 只需要调用这一句，其它的交给AndPermission吧，最后一个参数是PermissionListener。
        AndPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, listener);
    }

    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // 权限申请成功回调。
            if (requestCode == REQUEST_CODE_PERMISSION_OTHER) {

                //判断版本
                SPUtils spUtils = new SPUtils("code");
                int code = (int) spUtils.get("code", 0);
                if (AppUtils.getVersionCode(FirstAty.this) > code) {

                    spUtils.put("code", AppUtils.getVersionCode(FirstAty.this));
                    if (mRlWelcome != null) {


                        ObjectAnimator anim = ObjectAnimator.ofFloat(mRlWelcome, "alpha", 1f, 0f);

                        anim.setDuration(1000);// 动画持续时间
                        anim.start();
                        anim.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                mRlWelcome.setVisibility(View.GONE);
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        });

                    }


                } else {


                    goAty();

                }


            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            if (isFinishing()) {
                return;
            }

            SPUtils spUtils = new SPUtils("code");
            int code = (int) spUtils.get("code", 0);
            if (AppUtils.getVersionCode(FirstAty.this) > code) {
                spUtils.put("code", AppUtils.getVersionCode(FirstAty.this));
                if (mRlWelcome != null) {
                    ObjectAnimator anim = ObjectAnimator.ofFloat(mRlWelcome, "alpha", 1f, 0f);

                    anim.setDuration(1000);// 动画持续时间
                    anim.start();
                    anim.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mRlWelcome.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                }
            } else {
                goAty();
            }

        }
    };

    private void goAty() {
        setHasAnimiation(false);
        startActivity(MainAty.class, null);
        overridePendingTransition(R.anim.aty_in, R.anim.activity_alpha_out);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 2000);
    }

    @Override
    public void requestData() {

    }

    class MainFragmentAdapter extends FragmentPagerAdapter {
        public MainFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFgts.get(position);
        }

        @Override
        public int getCount() {
            return mFgts.size();
        }
    }


}
