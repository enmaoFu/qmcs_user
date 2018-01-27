package com.biaoyuan.transfer.ui.mine;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.and.yzy.frame.util.RetrofitUtils;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.base.BaseAty;
import com.biaoyuan.transfer.http.Mine;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Title  :
 * Create : 2017/7/4
 * Author ：chen
 */

public class MineSetPhoneAty extends BaseAty {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.phone)
    EditText mPhone;
    @Bind(R.id.tv_commit)
    TextView mTvCommit;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_set_login_pwd_first;
    }

    @Override
    public void initData() {
        initToolbar(mToolbar, "修改手机号码");
        mPhone.addTextChangedListener(textWatch);

    }

    @OnClick({R.id.tv_commit})
    @Override
    public void btnClick(View view) {
        super.btnClick(view);
        switch (view.getId()) {
            case R.id.tv_commit:
                String pwd = mPhone.getText().toString().trim();
                if (pwd.length() < 8) {
                    showErrorToast("密码长度不对");
                    return;
                }
                showLoadingDialog(null);

                doHttp(RetrofitUtils.createApi(Mine.class).userasswordVerification(pwd), 1);


                break;

        }
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        startActivityForResult(MineUpdatePhoneTwoAty.class, null, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            setResult(RESULT_OK, data);
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
            if (mPhone.getText().toString().length() >=8) {
                mTvCommit.setBackgroundResource(R.drawable.shape_commit_btn_bg);
            } else {
                mTvCommit.setBackgroundResource(R.drawable.shape_commit_gray_btn_bg);
            }
        }
    };

    @Override
    public void requestData() {

    }
}
