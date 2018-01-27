package com.biaoyuan.transfer.ui.mine;

import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.and.yzy.frame.util.AppManger;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.base.BaseAty;
import com.biaoyuan.transfer.ui.WebViewActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Title  :传送员申请状态
 * Create : 2017/7/4
 * Author ：chen
 */

public class MineTransferStateAty extends BaseAty {

    // 提交资料成功
    public static final int TYPE_COMMIT = 0;


    //审核通过
    public static final int TYPE_SUCCESS = 1;

    //审核被拒绝
    public static final int TYPE_ERROR = 2;
    @Bind(R.id.img_type)
    ImageView mImgType;
    @Bind(R.id.tv_msg)
    TextView mTvMsg;
    @Bind(R.id.tv_commit)
    TextView mTvCommit;
    @Bind(R.id.tv_left)
    TextView mTvLeft;
    @Bind(R.id.tv_center)
    TextView mTvCenter;
    @Bind(R.id.tv_right)
    TextView mTvRight;
    @Bind(R.id.tv_go)
    TextView mTvGo;

    private int type = 0;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;


    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_transfer_state;
    }

    @Override
    public void initData() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                AppManger.getInstance().killActivity(MineAuthenticationExamineActivity.class);
            }
        }, 500);


        type = getIntent().getIntExtra("type", 0);


        switch (type) {
            case TYPE_COMMIT:
                //提交成功

                mTvMsg.setText("认证资料已提交，请耐心等待审核");
                mImgType.setImageResource(R.drawable.examine_wait);
                mTvLeft.setText("等待太无聊？");
                mTvRight.setText("get新技能变身专业达人>>");

                initToolbar(mToolbar, "认证审核");
                break;

            case TYPE_SUCCESS:
                //审核通过


                mTvMsg.setText("恭喜您通过审核，加入传送天使大队");
                mImgType.setImageResource(R.drawable.examine_sucess);
                mTvLeft.setText("想要更专业？");
                mTvRight.setText("更加系统的了解快递知识>>");
                initToolbar(mToolbar, "申请成功");
                break;
            case TYPE_ERROR:
                //拒绝
                mTvMsg.setText("很遗憾您未通过审核，您可以");
                mImgType.setImageResource(R.drawable.examine_fail);
                mTvLeft.setText("暂时不想认证？");
                mTvRight.setText("先掌握理论知识也是可以的喔>>");

                initToolbar(mToolbar, "申请失败");
                mTvGo.setVisibility(View.VISIBLE);
                mTvGo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(MineAuthenticationActivity.class,null);
                    }
                });

                break;

        }


    }

    @OnClick({R.id.tv_commit,R.id.ll_go})
    @Override
    public void btnClick(View view) {
        super.btnClick(view);
        switch(view.getId()){
            case R.id.tv_commit:
                finish();
                break;
            case R.id.ll_go:
                startActivity(WebViewActivity.class,null);
                break;
        }
    }

    @Override
    public void requestData() {

    }
}
