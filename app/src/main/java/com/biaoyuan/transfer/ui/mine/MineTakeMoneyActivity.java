package com.biaoyuan.transfer.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.and.yzy.frame.util.RetrofitUtils;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.base.BaseAty;
import com.biaoyuan.transfer.http.Mine;
import com.biaoyuan.transfer.http.Send;
import com.biaoyuan.transfer.util.AppJsonUtil;
import com.biaoyuan.transfer.util.Encryption;
import com.biaoyuan.transfer.view.keyboardpwd.PassValitationPopwindow;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * @author ：enmaoFu
 * @title :余额提现页面
 * @create : 2017/4/27
 */
public class MineTakeMoneyActivity extends BaseAty {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.number)
    EditText number;
    @Bind(R.id.username)
    EditText username;
    @Bind(R.id.price_con)
    TextView price_con;
    @Bind(R.id.input_money)
    EditText inputMoney;
    @Bind(R.id.text_but)
    TextView textBut;

    private PassValitationPopwindow mPopup;

    private String price;

    private String getNumber;
    private String getUsername;
    private String getInputMoney;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_take_money;
    }

    @Override
    public void initData() {
        initToolbar(mToolbar, "余额提现");

        Bundle bundle = getIntent().getExtras();
        price = bundle.getString("price");
        price_con.setText("可用提现金额  ¥" + price + "元");

        inputMoney.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable edt) {
                String temp = edt.toString();
                int posDot = temp.indexOf(".");
                if (posDot <= 0)
                    return;
                if (temp.length() - posDot - 1 > 2) {
                    edt.delete(posDot + 3, posDot + 4);
                }
            }

            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
        });


        mPopup = new PassValitationPopwindow(this, textBut, new PassValitationPopwindow.OnInputNumberCodeCallback() {
            @Override
            public void onSuccess(String numberString) {
                /*showToast(numberString);
                startActivity(SendPaymentSuccessActivity.class, null);*/

                showLoadingDialog(null);
                try {
                    //Logger.v("调用余额支付接口");

                    doHttp(RetrofitUtils.createApi(Mine.class).cashTakeOutApply(getUsername, getNumber, Double.parseDouble(getInputMoney),
                            Encryption.encode(numberString)), 3);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        //忘记密码
        mPopup.getForget_pwd().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopup.dismiss();

                //先判断是否设置了支付密码
                doHttp(RetrofitUtils.createApi(Send.class).getPayPassword(), 2);

            }
        });

    }

    @Override
    public void requestData() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mine_feedback_toolbar_menu, menu);
        menu.getItem(0).setTitle("常用银行卡");
        menu.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivityForResult(MineCommonBankActivity.class, null, 1);
                return false;
            }
        });
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK) {
            mPopup.showDialog();
        } else if (requestCode == 1) {
            if (data != null) {
                String getCardNumber = data.getStringExtra("cardNo");
                String getCardName = data.getStringExtra("cardName");
                number.setText(getCardNumber);
                username.setText(getCardName);
                //将光标设置到最后
                number.setSelection(number.getText().toString().length());
                username.setSelection(username.getText().toString().length());
            }
        }
    }

    @OnClick({R.id.text_but, R.id.withdrawals})
    @Override
    public void btnClick(View view) {
        switch (view.getId()) {
            case R.id.text_but:
                getNumber = number.getText().toString().trim();
                getUsername = username.getText().toString().trim();
                getInputMoney = inputMoney.getText().toString().trim();
                if (getNumber.length() == 0) {
                    showErrorToast("请输入银行卡号");
                } else if (getNumber.length() < 16 || getNumber.length() > 19) {
                    showErrorToast("请输入正确的银行卡号");
                } else if (getUsername.length() == 0) {
                    showErrorToast("请输入银行卡预留姓名");
                } else if (getInputMoney.length() == 0) {
                    showErrorToast("请输入提现金额");
                } else if (getInputMoney.equals("0")) {
                    showErrorToast("提现金额不能为0");
                } else if (Double.parseDouble(getInputMoney) < 100) {
                    showErrorToast("提现金额不能少于100");
                } else if(Double.parseDouble(getInputMoney)>Double.parseDouble(price)){
                    showErrorToast("提现金额不能超过"+price+"元");
                }else {

                    doHttp(RetrofitUtils.createApi(Send.class).getPayPassword(), 1);
                }

                break;
            case R.id.withdrawals:
                inputMoney.setText("");
                String money = price_con.getText().toString().trim().substring(9,
                        price_con.getText().toString().trim().length() - 1);
                inputMoney.setText(money);
                inputMoney.setSelection(inputMoney.length());
                break;
        }
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what) {
            case 1:
                boolean isSetting = AppJsonUtil.getBoolean(result, "data");

                if (isSetting) {
                    mPopup.showDialog();
                } else {
                    Bundle bundle = new Bundle();

                    bundle.putBoolean("isSetting", isSetting);
                    //支付密码类型
                    bundle.putInt("pwd", 1);
                    startActivityForResult(MineUpdatePwdFristAty.class, bundle, 2);

                }

                break;

            case 2:

                boolean isSet = AppJsonUtil.getBoolean(result, "data");
                //忘记密码
                Bundle bun = new Bundle();
                bun.putBoolean("isSetting", isSet);
                //支付密码类型
                bun.putInt("pwd", 1);
                startActivityForResult(MineUpdatePwdFristAty.class, bun, 2);
                break;

            case 3:
                Bundle bundle = new Bundle();
                bundle.putString("getInputMoney", getInputMoney);
                startActivity(MineTakeMoneyResultActivity.class, bundle);
                break;
        }


    }

}
