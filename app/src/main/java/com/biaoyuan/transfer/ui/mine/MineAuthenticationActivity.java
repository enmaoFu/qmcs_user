package com.biaoyuan.transfer.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.and.yzy.frame.util.AppManger;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.base.BaseAty;
import com.biaoyuan.transfer.ui.WebViewActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author ：enmaoFu
 * @title :身份认证页面
 * @create : 2017/4/24
 */
public class MineAuthenticationActivity extends BaseAty {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.et_user_name)
    EditText mEtUserName;
    @Bind(R.id.et_code)
    EditText mEtCode;
    @Bind(R.id.et_other_name)
    EditText mEtOtherName;
    @Bind(R.id.et_other_phone)
    EditText mEtOtherPhone;
    @Bind(R.id.tv_commit)
    TextView mTvCommit;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_authentication;
    }

    @Override
    public void initData() {
        initToolbar(mToolbar, "身份认证");

        mEtUserName.addTextChangedListener(textWatch);
        mEtCode.addTextChangedListener(textWatch);
        mEtOtherName.addTextChangedListener(textWatch);
        mEtOtherPhone.addTextChangedListener(textWatch);

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                AppManger.getInstance().killActivity(MineTransferStateAty.class);
            }
        },500);

    }


    @Override
    public void requestData() {

    }

    @OnClick({R.id.tv_commit,R.id.tv_study})
    @Override
    public void btnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_commit:

                String name = mEtUserName.getText().toString().trim();
                String code = mEtCode.getText().toString().trim();
                String otherName = mEtOtherName.getText().toString().trim();
                String phone = mEtOtherPhone.getText().toString().trim();

                if (name.length() < 2) {
                    showErrorToast("姓名至少两个字符");
                    mEtUserName.requestFocus();
                    return;
                }
                if (code.length() < 15) {
                    showErrorToast("身份证长度不对");
                    mEtCode.requestFocus();
                    return;
                }
                if (otherName.length() < 2) {
                    showErrorToast("联系人姓名至少两个字符");
                    mEtOtherName.requestFocus();
                    return;
                }
                if (phone.length() < 8) {
                    showErrorToast("联系人电话长度不对");
                    mEtOtherPhone.requestFocus();
                    return;
                }
                Bundle bundle = new Bundle();

                bundle.putString("name", name);
                bundle.putString("code", code);
                bundle.putString("otherName", otherName);
                bundle.putString("phone", phone);


                startActivityForResult(MineAuthenticationExamineActivity.class, bundle, 1);
                break;
            case R.id.tv_study:

                startActivity(WebViewActivity.class,null);

                break;

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            finish();
        }
    }

    /**
     * 用于判断下一步是否可点击
     */
    private TextWatcher textWatch = new TextWatcher() {

        /**
         * 变化前
         * @param s
         * @param start
         * @param count
         * @param after
         */
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        /**
         * 变化中
         * @param s
         * @param start
         * @param before
         * @param count
         */
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        /**
         * 变化后
         * @param s
         */
        @Override
        public void afterTextChanged(Editable s) {
            //s:变化后的所有字符
            if (mEtUserName.getText().toString().trim().length() >= 2 && mEtCode.getText().toString().trim().length() >= 15 && mEtOtherName.getText().toString().trim().length() >= 2 && mEtOtherPhone.getText().toString().trim().length() >=8) {
                mTvCommit.setBackgroundResource(R.drawable.shape_commit_btn_bg);
            } else {
                mTvCommit.setBackgroundResource(R.drawable.shape_commit_gray_btn_bg);
            }
        }
    };


}
