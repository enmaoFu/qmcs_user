package com.biaoyuan.transfer.ui.mine.send;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.and.yzy.frame.util.Arith;
import com.and.yzy.frame.util.DateTool;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.base.BaseAty;
import com.biaoyuan.transfer.domain.MineSendOrderDetail;
import com.biaoyuan.transfer.util.MyNumberFormat;

import butterknife.Bind;

/**
 * Title  :订单详情
 * Create : 2017/6/21
 * Author ：chen
 */

public class MineOrderDetail extends BaseAty {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tv_code)
    TextView mTvCode;
    @Bind(R.id.tv_comiit_time)
    TextView mTvComiitTime;
    @Bind(R.id.textView3)
    TextView mTextView3;
    @Bind(R.id.tv_type)
    TextView mTvType;
    @Bind(R.id.tv_size)
    TextView mTvSize;
    @Bind(R.id.tv_send_name)
    TextView mTvSendName;
    @Bind(R.id.tv_send_phone)
    TextView mTvSendPhone;
    @Bind(R.id.tv_send_address)
    TextView mTvSendAddress;
    @Bind(R.id.textView5)
    TextView mTextView5;
    @Bind(R.id.tv_time)
    TextView mTvTime;
    @Bind(R.id.tv_price)
    TextView mTvPrice;
    @Bind(R.id.tv_hase_price)
    TextView mTvHasePrice;
    @Bind(R.id.rl_hase)
    RelativeLayout mRlHase;


    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_order_detail;
    }

    @Override
    public void initData() {
        initToolbar(mToolbar, "订单详情");

        MineSendOrderDetail orderDetail = getIntent().getParcelableExtra("data");

        mTvCode.setText("订单编号：" + orderDetail.getOrderNo());
        mTvComiitTime.setText("下单时间：" + DateTool.timesToStrTime(orderDetail.getOrderCreatTime() + "", "yyyy.MM.dd HH:mm"));

        switch (orderDetail.getOrderGoodsType()) {
            case 1:
                mTvType.setText("文件");
                break;
            case 2:
                mTvType.setText("办公/居家");
                break;
            case 3:
                mTvType.setText("鲜花");
                break;
            case 4:
                mTvType.setText("包裹");
                break;
            case 5:
                mTvType.setText("蛋糕");
                break;
        }
        mTvSize.setText("最长边≤" + orderDetail.getOrderGoodsSize() + "cm  " + orderDetail.getOrderGoodsWeight() + "kg");

        mTvSendName.setText(orderDetail.getOrderSenderName());
        mTvSendAddress.setText(orderDetail.getOrderSendAddr().replace("|", ""));
        mTvSendPhone.setText(orderDetail.getOrderSenderTel() + "");

        String time = DateTool.getTimeType(orderDetail.getOrderRequiredTime());
        if (time == null) {
            mTvTime.setText(DateTool.timesToStrTime(orderDetail.getOrderRequiredTime() + "", "yyyy.MM.dd HH:mm") + "之前");
        } else {
            mTvTime.setText(time + DateTool.timesToStrTime(orderDetail.getOrderRequiredTime() + "", "HH:mm") + "之前");
        }

        mTvPrice.setText("¥" + MyNumberFormat.m2(orderDetail.getOrderTotalPrice()));

        //判断是否有追加金额
        if (orderDetail.getOrderPriceAddition()==0){
            mRlHase.setVisibility(View.GONE);
        }else {
            mRlHase.setVisibility(View.VISIBLE);
            mTvHasePrice.setText("¥"+ MyNumberFormat.m2(Arith.sub(orderDetail.getOrderTotalPrice(),orderDetail.getOrderPriceAddition())));

        }



    }

    @Override
    public void requestData() {

    }
}
