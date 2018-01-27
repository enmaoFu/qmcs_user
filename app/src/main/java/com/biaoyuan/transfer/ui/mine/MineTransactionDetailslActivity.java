package com.biaoyuan.transfer.ui.mine;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.and.yzy.frame.util.DateTool;
import com.and.yzy.frame.util.RetrofitUtils;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.base.BaseAty;
import com.biaoyuan.transfer.http.Mine;
import com.biaoyuan.transfer.util.AppJsonUtil;
import com.biaoyuan.transfer.util.MyNumberFormat;
import com.iflytek.cloud.thirdparty.V;

import butterknife.Bind;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * @title :交易详情页面
 * @create : 2017/4/27
 * @author ：enmaoFu
 */
public class MineTransactionDetailslActivity extends BaseAty {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.price_type)
    TextView priceType;
    @Bind(R.id.price)
    TextView price;
    @Bind(R.id.date)
    TextView date;
    @Bind(R.id.zf_type)
    TextView zfType;
    @Bind(R.id.jy)
    TextView jy;
    @Bind(R.id.dd)
    TextView dd;
    @Bind(R.id.remarks)
    TextView remarks;
    @Bind(R.id.zf_type_re)
    RelativeLayout zf_type_re;
    @Bind(R.id.hr)
    View hr;
    @Bind(R.id.sy_re)
    RelativeLayout sy_re;
    @Bind(R.id.sy)
    TextView sy;
    @Bind(R.id.hr_sy)
    View hr_sy;
    @Bind(R.id.tx_hr)
    View tx_hr;
    @Bind(R.id.tx_re)
    RelativeLayout tx_re;
    @Bind(R.id.tx_text)
    TextView tx_text;
    @Bind(R.id.sq_date_hr)
    View sq_date_hr;
    @Bind(R.id.sq_re)
    RelativeLayout sq_re;
    @Bind(R.id.sq_text)
    TextView sq_text;
    @Bind(R.id.je_hr)
    View je_hr;
    @Bind(R.id.je_re)
    RelativeLayout je_re;
    @Bind(R.id.je_text)
    TextView je_text;
    @Bind(R.id.sjje_hr)
    View sjje_hr;
    @Bind(R.id.sjje_re)
    RelativeLayout sjje_re;
    @Bind(R.id.sjje_text)
    TextView sjje_text;
    @Bind(R.id.sh_hr)
    View sh_hr;
    @Bind(R.id.sh_re)
    RelativeLayout sh_re;
    @Bind(R.id.sh_text)
    TextView sh_text;
    @Bind(R.id.namedh)
    TextView namedh;
    @Bind(R.id.ddre)
    RelativeLayout ddre;
    @Bind(R.id.ddhr)
    View ddhr;
    @Bind(R.id.jysj)
    TextView jysj;
    @Bind(R.id.a)
    View a;
    private long balanceIdLong;

    private final int EXPENDITURE = 1;
    private final int RECHARGE = 2;
    private final int REFUND = 3;
    private final int PAYMNET = 4;
    private final int PROFIT = 5;
    private final int DEDUCT = 6;
    private final int WITHDRAWALS = 7;

    private final int WEIXIN = 10;
    private final int ZHIFUBAO = 20;
    private final int YUE = 30;

    private int type;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_transaction_details;
    }

    @Override
    public void initData() {

        Bundle bundle = getIntent().getExtras();
        type = bundle.getInt("type");
        balanceIdLong = bundle.getLong("balanceIdLong");
        initToolbar(mToolbar,"交易明细");
    }

    @Override
    public boolean setIsInitRequestData() {
        return true;
    }

    @Override
    public void requestData() {
        showLoadingContentDialog();
        doHttp(RetrofitUtils.createApi(Mine.class).balanceInfo(balanceIdLong,type),1);
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what) {
            case 1:
                if (type == 1 || type == 2) {
                    //操作类型
                    String getPriceType = new StringBuilder().append(AppJsonUtil.getString(AppJsonUtil.getString(result, "data"), "balanceTansactionType")).toString();
                    int getPriceTypeInt = Integer.parseInt(getPriceType);
                    //操作金额
                    String getPrice = new StringBuilder().append(AppJsonUtil.getString(AppJsonUtil.getString(result, "data"), "balanceAmount")).toString();
                    //交易时间
                    String getDate = new StringBuilder().append(DateTool.timesToStrTime(AppJsonUtil.getString(AppJsonUtil.getString(result, "data"), "balanceOperationTime")
                            + "", "yyyy-MM-dd HH:mm")).toString();
                    //交易单号
                    String getJy = new StringBuilder().append(AppJsonUtil.getString(AppJsonUtil.getString(result, "data"), "balanceObjNo")).toString();
                    //订单编号
                    String getDd = new StringBuilder().append(AppJsonUtil.getString(AppJsonUtil.getString(result, "data"), "balanceObjNo")).toString();
                    //支付类型
                    String ztType = new StringBuilder().append(AppJsonUtil.getString(AppJsonUtil.getString(result, "data"), "paymentWay")).toString();
                    //备注
                    String getRemarks = new StringBuilder().append(AppJsonUtil.getString(AppJsonUtil.getString(result, "data"), "balanceDetail")).toString();
                    price.setText("¥" + MyNumberFormat.m2(Double.parseDouble(getPrice)));
                    date.setText(getDate);
                    jy.setText(getJy);
                    dd.setText(getDd);
                    zf_type_re.setVisibility(View.VISIBLE);

                    remarks.setText(getRemarks);
                    sy_re.setVisibility(View.GONE);
                    hr_sy.setVisibility(View.GONE);
                    switch (Integer.parseInt(ztType)){
                        case WEIXIN:
                            zfType.setText("微信");
                            break;
                        case ZHIFUBAO:
                            zfType.setText("支付宝");
                            break;
                        case YUE:
                            zfType.setText("余额");
                            break;
                    }
                    switch (getPriceTypeInt) {
                        case EXPENDITURE:
                            priceType.setText("订单支出");
                            break;
                        case RECHARGE:
                            priceType.setText("充值");
                            break;
                        case REFUND:
                            priceType.setText("退款");
                            break;
                        case PAYMNET:
                            priceType.setText("赔付");
                            break;
                        case PROFIT:
                            priceType.setText("传送收益");
                            break;
                        case DEDUCT:
                            priceType.setText("扣手续费退款");
                            break;
                        case WITHDRAWALS:
                            priceType.setText("提现");
                            break;
                    }
                } else if(type == 3 || type == 4 || type == 6) {
                    //操作类型
                    String getPriceType = new StringBuilder().append(AppJsonUtil.getString(AppJsonUtil.getString(result, "data"), "balanceTansactionType")).toString();
                    int getPriceTypeInt = Integer.parseInt(getPriceType);
                    //操作金额
                    String getPrice = new StringBuilder().append(AppJsonUtil.getString(AppJsonUtil.getString(result, "data"), "balanceAmount")).toString();
                    //交易时间
                    String getDate = new StringBuilder().append(DateTool.timesToStrTime(AppJsonUtil.getString(AppJsonUtil.getString(result, "data"), "balanceOperationTime")
                            + "", "yyyy-MM-dd HH:mm")).toString();
                    //交易单号
                    String getJy = new StringBuilder().append(AppJsonUtil.getString(AppJsonUtil.getString(result, "data"), "balanceObjNo")).toString();
                    //订单编号
                    String getDd = new StringBuilder().append(AppJsonUtil.getString(AppJsonUtil.getString(result, "data"), "balanceObjNo")).toString();
                    //备注
                    String getRemarks = new StringBuilder().append(AppJsonUtil.getString(AppJsonUtil.getString(result, "data"), "balanceDetail")).toString();
                    //订单价格
                    String getJg = new StringBuilder().append(AppJsonUtil.getString(AppJsonUtil.getString(result, "data"), "totalPrice")).toString();
                    price.setText("¥" + MyNumberFormat.m2(Double.parseDouble(getPrice)));
                    date.setText(getDate);
                    jy.setText(getJy);
                    dd.setText(getDd);
                    remarks.setText(getRemarks);
                    zf_type_re.setVisibility(View.GONE);
                    hr.setVisibility(View.GONE);
                    sy_re.setVisibility(View.GONE);
                    hr_sy.setVisibility(View.GONE);
                    tx_hr.setVisibility(View.VISIBLE);
                    tx_re.setVisibility(View.VISIBLE);
                    tx_text.setText("¥" + MyNumberFormat.m2(Double.parseDouble(getJg)));
                    switch (getPriceTypeInt) {
                        case EXPENDITURE:
                            priceType.setText("订单支出");
                            break;
                        case RECHARGE:
                            priceType.setText("充值");
                            break;
                        case REFUND:
                            priceType.setText("退款");
                            break;
                        case PAYMNET:
                            priceType.setText("赔付");
                            break;
                        case PROFIT:
                            priceType.setText("传送收益");
                            break;
                        case DEDUCT:
                            priceType.setText("扣手续费退款");
                            break;
                        case WITHDRAWALS:
                            priceType.setText("提现");
                            break;
                    }
                } else if(type == 5){
                    //操作类型
                    String getPriceType = new StringBuilder().append(AppJsonUtil.getString(AppJsonUtil.getString(result, "data"), "balanceTansactionType")).toString();
                    int getPriceTypeInt = Integer.parseInt(getPriceType);
                    //操作金额
                    String getPrice = new StringBuilder().append(AppJsonUtil.getString(AppJsonUtil.getString(result, "data"), "balanceAmount")).toString();
                    //交易时间
                    String getDate = new StringBuilder().append(DateTool.timesToStrTime(AppJsonUtil.getString(AppJsonUtil.getString(result, "data"), "balanceOperationTime")
                            + "", "yyyy-MM-dd HH:mm")).toString();
                    //交易单号
                    String getJy = new StringBuilder().append(AppJsonUtil.getString(AppJsonUtil.getString(result, "data"), "balanceObjNo")).toString();
                    //订单编号
                    String getDd = new StringBuilder().append(AppJsonUtil.getString(AppJsonUtil.getString(result, "data"), "balanceObjNo")).toString();
                    //备注
                    String getRemarks = new StringBuilder().append(AppJsonUtil.getString(AppJsonUtil.getString(result, "data"), "balanceDetail")).toString();
                    //传送收益
                    String getSy = new StringBuilder().append(AppJsonUtil.getString(AppJsonUtil.getString(result, "data"), "carrierReward")).toString();
                    price.setText("¥" + MyNumberFormat.m2(Double.parseDouble(getPrice)));
                    date.setText(getDate);
                    namedh.setText("包裹编号");
                    jy.setText(getJy);
                    ddre.setVisibility(View.GONE);
                    ddhr.setVisibility(View.GONE);
                    remarks.setText(getRemarks);
                    zf_type_re.setVisibility(View.GONE);
                    hr.setVisibility(View.GONE);
                    sy_re.setVisibility(View.VISIBLE);
                    hr_sy.setVisibility(View.VISIBLE);
                    sy.setText("¥" + MyNumberFormat.m2(Double.parseDouble(getSy)));
                    tx_hr.setVisibility(View.GONE);
                    tx_re.setVisibility(View.GONE);
                    switch (getPriceTypeInt) {
                        case EXPENDITURE:
                            priceType.setText("订单支出");
                            break;
                        case RECHARGE:
                            priceType.setText("充值");
                            break;
                        case REFUND:
                            priceType.setText("退款");
                            break;
                        case PAYMNET:
                            priceType.setText("赔付");
                            break;
                        case PROFIT:
                            priceType.setText("传送收益");
                            break;
                        case DEDUCT:
                            priceType.setText("扣手续费退款");
                            break;
                        case WITHDRAWALS:
                            priceType.setText("提现");
                            break;
                    }
                } else if(type == 7){
                    //操作类型
                    String getPriceType = new StringBuilder().append(AppJsonUtil.getString(AppJsonUtil.getString(result, "data"), "balanceTansactionType")).toString();
                    int getPriceTypeInt = Integer.parseInt(getPriceType);
                    //操作金额
                    String getPrice = new StringBuilder().append(AppJsonUtil.getString(AppJsonUtil.getString(result, "data"), "balanceAmount")).toString();
                    //交易时间
                    String getDate = new StringBuilder().append(DateTool.timesToStrTime(AppJsonUtil.getString(AppJsonUtil.getString(result, "data"), "balanceOperationTime")
                            + "", "yyyy-MM-dd HH:mm")).toString();
                    //交易单号
                    String getJy = new StringBuilder().append(AppJsonUtil.getString(AppJsonUtil.getString(result, "data"), "balanceObjNo")).toString();
                    //订单编号
                    String getDd = new StringBuilder().append(AppJsonUtil.getString(AppJsonUtil.getString(result, "data"), "balanceObjNo")).toString();
                    //备注
                    String getRemarks = new StringBuilder().append(AppJsonUtil.getString(AppJsonUtil.getString(result, "data"), "balanceDetail")).toString();
                    //提现申请时间
                    String getSqDate = new StringBuilder().append(DateTool.timesToStrTime(AppJsonUtil.getString(AppJsonUtil.getString(result, "data"), "withdrawlTime")
                            + "", "yyyy-MM-dd HH:mm")).toString();
                    //提现金额
                    String getJe = new StringBuilder().append(AppJsonUtil.getString(AppJsonUtil.getString(result, "data"), "withdrawlAmount")).toString();
                    //实际金额
                    String getSjJe = new StringBuilder().append(AppJsonUtil.getString(AppJsonUtil.getString(result, "data"), "actualAmount")).toString();
                    //审核时间
                    String getSh = new StringBuilder().append(DateTool.timesToStrTime(AppJsonUtil.getString(AppJsonUtil.getString(result, "data"), "operationTime")
                            + "", "yyyy-MM-dd HH:mm")).toString();
                    price.setText("¥" + MyNumberFormat.m2(Double.parseDouble(getPrice)));
                    a.setVisibility(View.GONE);
                    ddhr.setVisibility(View.GONE);
                    hr_sy.setVisibility(View.GONE);
                    tx_hr.setVisibility(View.GONE);
                    jysj.setText("提现时间");
                    date.setText(getDate);
                    ddre.setVisibility(View.GONE);
                    jy.setText(getJy);
                    dd.setText(getDd);
                    remarks.setText(getRemarks);
                    zf_type_re.setVisibility(View.GONE);
                    sq_date_hr.setVisibility(View.GONE);
                    sq_re.setVisibility(View.GONE);
                    sq_text.setText(getSqDate);
                    je_hr.setVisibility(View.VISIBLE);
                    je_re.setVisibility(View.VISIBLE);
                    je_text.setText("¥" + MyNumberFormat.m2(Double.parseDouble(getJe)));
                    sjje_hr.setVisibility(View.GONE);
                    sjje_re.setVisibility(View.GONE);
                    sjje_text.setText("¥" + MyNumberFormat.m2(Double.parseDouble(getSjJe)));
                    sh_hr.setVisibility(View.GONE);
                    sh_re.setVisibility(View.GONE);
                    sh_text.setText(getSh);
                    switch (getPriceTypeInt) {
                        case EXPENDITURE:
                            priceType.setText("订单支出");
                            break;
                        case RECHARGE:
                            priceType.setText("充值");
                            break;
                        case REFUND:
                            priceType.setText("退款");
                            break;
                        case PAYMNET:
                            priceType.setText("赔付");
                            break;
                        case PROFIT:
                            priceType.setText("传送收益");
                            break;
                        case DEDUCT:
                            priceType.setText("扣手续费退款");
                            break;
                        case WITHDRAWALS:
                            priceType.setText("提现");
                            break;
                    }
                }
        }
    }
}
