package com.biaoyuan.transfer.ui.mine;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.and.yzy.frame.adapter.recyclerview.BaseQuickAdapter;
import com.and.yzy.frame.adapter.recyclerview.listener.OnItemClickListener;
import com.and.yzy.frame.util.DateTool;
import com.and.yzy.frame.util.RetrofitUtils;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.adapter.MineBalanceDetailedAdapter;
import com.biaoyuan.transfer.base.BaseAty;
import com.biaoyuan.transfer.domain.MineBalanceDetailedInfo;
import com.biaoyuan.transfer.http.Mine;
import com.biaoyuan.transfer.util.AppJsonUtil;
import com.biaoyuan.transfer.util.MyNumberFormat;
import com.bigkoo.pickerview.TimePickerView;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * @title :余额明细页面
 * @create : 2017/4/27
 * @author ：enmaoFu
 */
public class MineBalanceDetailedActivity extends BaseAty {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.balance_detailed_recyl)
    RecyclerView mBalanceDetailedRecyl;
    @Bind(R.id.text1)
    TextView text1;
    @Bind(R.id.text2)
    TextView text2;
    @Bind(R.id.recharge_price)
    TextView rechargePrice;
    @Bind(R.id.income_price)
    TextView incomePrice;

    //适配器
    private MineBalanceDetailedAdapter madapter;
    //数据源
    private List<MineBalanceDetailedInfo> itemList;

    private String getNowDate;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_balance_detailed;
    }

    @Override
    public void initData() {
        initToolbar(mToolbar, "账户明细");

        //实例化布局管理器
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //实例化适配器
        madapter = new MineBalanceDetailedAdapter(R.layout.item_mine_balance_detailed,new ArrayList<MineBalanceDetailedInfo>());
        //设置布局管理器
        mBalanceDetailedRecyl.setLayoutManager(layoutManager);
        //设置间隔样式
        /*mTekeRecyclerview.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getActivity())
                        .color(Color.parseColor(getResources().getString(R.string.parseColor)))
                        .sizeResId(R.dimen.size_0_5p)
                        .build());*/
        //大小不受适配器影响
        mBalanceDetailedRecyl.setHasFixedSize(true);
        //设置加载动画类型
  //      madapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        //设置删除动画类型
        mBalanceDetailedRecyl.setItemAnimator(new DefaultItemAnimator());
        //设置没有数据的页面
        setEmptyView(madapter,"暂无数据");
        //设置adapter
        mBalanceDetailedRecyl.setAdapter(madapter);


        mBalanceDetailedRecyl.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                long balanceIdLong = madapter.getItem(position).getBalanceId();
                int type = madapter.getItem(position).getBalanceTansactionType();
                Bundle bundle = new Bundle();
                bundle.putLong("balanceIdLong",balanceIdLong);
                bundle.putInt("type",type);
                startActivity(MineTransactionDetailslActivity.class,bundle);
            }
        });
    }


    @Override
    public boolean setIsInitRequestData() {
        return true;
    }

    @Override
    public void requestData() {
        //得到现在的时间
        getNowDate = DateTool.timesToStrTime(System.currentTimeMillis() + "", "yyyy-MM-dd ");
        showLoadingContentDialog();
        doHttp(RetrofitUtils.createApi(Mine.class).monthBalance(getNowDate),1);
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what){
            case 1:

                //充值金额
                String expenditure = new StringBuilder().append(AppJsonUtil.getString(AppJsonUtil.getString(result,"data"),"expenditure")).toString();
                //收入金额
                String getIncomePrice = new StringBuilder().append(AppJsonUtil.getString(AppJsonUtil.getString(result,"data"),"income")).toString();
                List<MineBalanceDetailedInfo> mineBalanceDetailedInfoList = AppJsonUtil.getArrayList(result,"balanceBeanList",MineBalanceDetailedInfo.class);
                text1.setText(getNowDate.substring(0,4) + "年" + getNowDate.substring(5,7) + "月收支明细");
                rechargePrice.setText(MyNumberFormat.m2(Double.parseDouble(expenditure)));
                incomePrice.setText(MyNumberFormat.m2(Double.parseDouble(getIncomePrice)));

                madapter.removeAll();

                if (mineBalanceDetailedInfoList != null) {
                    madapter.setNewData(mineBalanceDetailedInfoList);
                }

                break;

            /*case 2:

                List<MineBalanceDetailedInfo> mineBalanceDetailedInfoListTwo = AppJsonUtil.getArrayList(result,"balanceBeanList",MineBalanceDetailedInfo.class);
                madapter.removeAll();

                if (mineBalanceDetailedInfoListTwo != null) {
                    madapter.addDatas(mineBalanceDetailedInfoListTwo);
                }

                break;*/
        }
    }

    @OnClick({R.id.text2})
    @Override
    public void btnClick(View view) {
        switch (view.getId()){
            case R.id.text2:

                Calendar calendar = Calendar.getInstance();
                TimePickerView mPickerViewDate = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {

                        showLoadingDialog(null);

                        //String dateNew = DateTool.timesToStrTime(System.currentTimeMillis() + "", "yyyy") + "-" + DateTool.dateToStr(date, "MM-dd");
                        String dateNew = DateTool.dateToStr(date, "yyyy-MM") + "-" +DateTool.timesToStrTime(System.currentTimeMillis() + "", "dd");
                        doHttp(RetrofitUtils.createApi(Mine.class).monthBalance(dateNew),1);

                    }
                }).setType(new boolean[]{true, true, false, false, false, false}).setTitleText("选择日期").
                        setContentSize(14).
                        setOffSize(30).
                        build();
                mPickerViewDate.show();

                break;
        }
    }
}
