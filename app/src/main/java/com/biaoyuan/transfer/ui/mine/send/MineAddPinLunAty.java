package com.biaoyuan.transfer.ui.mine.send;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.and.yzy.frame.util.RetrofitUtils;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.base.BaseAty;
import com.biaoyuan.transfer.domain.MineSendOrderDetail;
import com.biaoyuan.transfer.http.Send;
import com.biaoyuan.transfer.ui.mine.MinePinLunAty;
import com.biaoyuan.transfer.util.AppJsonUtil;

import am.widget.drawableratingbar.DrawableRatingBar;
import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Title  :
 * Create : 2017/5/22
 * Author ：chen
 */

public class MineAddPinLunAty extends BaseAty {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tv_code)
    TextView mTvCode;
    @Bind(R.id.rb_all)
    DrawableRatingBar mRbAll;
    @Bind(R.id.tv_all)
    TextView mTvAll;
    @Bind(R.id.et_content)
    EditText mEtContent;
    @Bind(R.id.rb_time)
    DrawableRatingBar mRbTime;
    @Bind(R.id.tv_time)
    TextView mTvTime;
    @Bind(R.id.rb_server)
    DrawableRatingBar mRbServer;
    @Bind(R.id.tv_server)
    TextView mTvServer;

    private MineSendOrderDetail mOrderDetail;
    private long orderId;
    private String orderNo;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_add_pinlun;
    }

    @Override
    public boolean setIsInitRequestData() {
        return true;
    }

    @OnClick({R.id.tv_code,R.id.tv_commit})
    @Override
    public void btnClick(View view) {
        super.btnClick(view);
        switch (view.getId()) {
            case R.id.tv_code:
                Bundle bundle = new Bundle();
                bundle.putParcelable("data", mOrderDetail);
                startActivity(MineOrderDetail.class, bundle);
                break;
            case R.id.tv_commit:

                showLoadingDialog(null);

                String content=mEtContent.getText().toString().trim();

                if (TextUtils.isEmpty(content)) {
                    content=null;
                }
                int score=mRbAll.getRating();
                byte speed;
                if (mRbTime.getRating()<3){
                    speed=0;
                }else {
                    speed=1;
                }

                byte server;
                if (mRbServer.getRating()<3){
                    server=0;
                }else {
                    server=1;
                }

             doHttp(RetrofitUtils.createApi(Send.class).orderComment(orderId,score,speed,server,content),2);


                break;

        }
    }

    @Override
    public void initData() {
        initToolbar(mToolbar, "发表评论");

        orderId = getIntent().getLongExtra("orderId", 0);
        orderNo = getIntent().getStringExtra("orderNo");

        mTvCode.setText(orderNo);

        mRbAll.setOnRatingChangeListener(new DrawableRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChanged(int rating, int oldRating) {

                switch(rating){
                    case 0:
                        mTvAll.setText("非常差");
                        break;
                    case 1:
                        mTvAll.setText("很差");
                        break;
                    case 2:
                        mTvAll.setText("一般");
                        break;
                    case 3:
                        mTvAll.setText("好");
                        break;
                    case 4:
                        mTvAll.setText("很好");
                        break;
                    case 5:
                        mTvAll.setText("非常好");
                        break;
                }


            }

            @Override
            public void onRatingSelected(int rating) {

            }
        });
        mRbTime.setOnRatingChangeListener(new DrawableRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChanged(int rating, int oldRating) {
                switch(rating){
                    case 0:
                        mTvTime.setText("非常差");
                        break;
                    case 1:
                        mTvTime.setText("很差");
                        break;
                    case 2:
                        mTvTime.setText("一般");
                        break;
                    case 3:
                        mTvTime.setText("好");
                        break;
                    case 4:
                        mTvTime.setText("很好");
                        break;
                    case 5:
                        mTvTime.setText("非常好");
                        break;
                }
            }

            @Override
            public void onRatingSelected(int rating) {

            }
        });

        mRbServer.setOnRatingChangeListener(new DrawableRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChanged(int rating, int oldRating) {
                switch(rating){
                    case 0:
                        mTvServer.setText("非常差");
                        break;
                    case 1:
                        mTvServer.setText("很差");
                        break;
                    case 2:
                        mTvServer.setText("一般");
                        break;
                    case 3:
                        mTvServer.setText("好");
                        break;
                    case 4:
                        mTvServer.setText("很好");
                        break;
                    case 5:
                        mTvServer.setText("非常好");
                        break;
                }
            }

            @Override
            public void onRatingSelected(int rating) {

            }
        });




    }




    @Override
    public void requestData() {
        showLoadingContentDialog();
        doHttp(RetrofitUtils.createApi(Send.class).orderInfo(orderId), 1);
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what) {
            case 1:

                mOrderDetail = AppJsonUtil.getObject(result, "order", MineSendOrderDetail.class);

                break;

            case 2:

                showToast("评论成功");
                startActivity(MinePinLunAty.class,null);

                break;


        }
    }




}
