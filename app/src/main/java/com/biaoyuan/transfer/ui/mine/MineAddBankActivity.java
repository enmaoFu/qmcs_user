package com.biaoyuan.transfer.ui.mine;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

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
 * @author ：enmaoFu
 * @title : 添加银行卡页面
 * @create : 2017/07/05
 */
public class MineAddBankActivity extends BaseAty {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.number)
    EditText number;
    @Bind(R.id.input_name)
    EditText inputName;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mien_add_bank;
    }

    @Override
    public void initData() {
        initToolbar(mToolbar, "添加银行卡");
    }

    @Override
    public boolean setIsInitRequestData() {
        return false;
    }

    @Override
    public void requestData() {

    }

    @OnClick({R.id.text_but})
    @Override
    public void btnClick(View view) {
        switch (view.getId()) {
            case R.id.text_but:
                String getNumber = number.getText().toString().trim();
                String getName = inputName.getText().toString().trim();
                if (getNumber.length() == 0) {
                    showErrorToast("银行卡号不能为空");
                } else if (getNumber.length() < 16 || getNumber.length() > 19) {
                    showErrorToast("请输入正确的银行卡号");
                } else if (getName.length() == 0) {
                    showErrorToast("开户名不能为空");
                } else {
                    showLoadingDialog(null);
                    doHttp(RetrofitUtils.createApi(Mine.class).addCard(getNumber, getName), 1);
                }
                break;
        }
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what) {
            case 1:
                showToast("添加银行卡成功");
                Intent intent = new Intent();
                intent.putExtra("key", "success");
                setResult(1, intent);
                finish();
                break;
        }
    }
}
