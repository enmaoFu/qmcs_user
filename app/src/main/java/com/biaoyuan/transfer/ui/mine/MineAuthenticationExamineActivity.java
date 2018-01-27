package com.biaoyuan.transfer.ui.mine;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.and.yzy.frame.util.AppManger;
import com.and.yzy.frame.util.RetrofitUtils;
import com.and.yzy.frame.view.dialog.FormBotomDefaultDialogBuilder;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.base.BasePhotoAty;
import com.biaoyuan.transfer.config.UserManger;
import com.biaoyuan.transfer.domain.PicInfo;
import com.biaoyuan.transfer.http.Image;
import com.biaoyuan.transfer.http.Mine;
import com.biaoyuan.transfer.ui.WebViewActivity;
import com.biaoyuan.transfer.util.AppJsonUtil;
import com.biaoyuan.transfer.util.UpImageUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.LubanOptions;
import com.jph.takephoto.model.TResult;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * @author ：enmaoFu
 * @title :身份认证审核页面
 * @create : 2017/4/24
 */
public class MineAuthenticationExamineActivity extends BasePhotoAty {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.img_zhen)
    SimpleDraweeView mImgZhen;
    @Bind(R.id.ll_zhen)
    LinearLayout mLlZhen;
    @Bind(R.id.img_fan)
    SimpleDraweeView mImgFan;
    @Bind(R.id.ll_fan)
    LinearLayout mLlFan;
    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;
    @Bind(R.id.tv_progress)
    TextView mTvProgress;
    @Bind(R.id.Rl_commit)
    RelativeLayout mRlCommit;
    @Bind(R.id.ll_shou)
    LinearLayout mLlShou;
    @Bind(R.id.img_shou)
    SimpleDraweeView mImgShou;


    private List<PicInfo> mPicList;

    private FormBotomDefaultDialogBuilder mDefaultDialogBuilder;

    private UpImageUtils mUtils;

    //0正面 1反面 2手持
    private int chooseType = 0;


    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_authentication_examine;
    }

    @Override
    public void initData() {
        initToolbar(mToolbar, "身份认证");
        mPicList = new ArrayList<>();
        mPicList.add(new PicInfo());
        mPicList.add(new PicInfo());
        mPicList.add(new PicInfo());


    }

    @Override
    public boolean setIsInitRequestData() {
        return true;
    }

    @Override
    public void requestData() {
        doHttp(RetrofitUtils.createApi(Image.class).findKey(UpImageUtils.TAG_QMCS_U_IDENTITY + "/" + UserManger.getUUid()), 2);
    }

    @OnClick({R.id.ll_zhen, R.id.ll_fan, R.id.img_zhen, R.id.img_fan, R.id.Rl_commit, R.id.ll_shou, R.id.img_shou, R.id.tv_study})
    @Override
    public void btnClick(View view) {

        switch (view.getId()) {
            case R.id.tv_study:
                startActivity(WebViewActivity.class, null);
                break;
            case R.id.ll_zhen:
            case R.id.img_zhen:
                chooseType = 0;
                showPicDialog();

                break;
            case R.id.ll_fan:
            case R.id.img_fan:
                chooseType = 1;
                showPicDialog();
                break;
            case R.id.ll_shou:
            case R.id.img_shou:
                chooseType = 2;
                showPicDialog();
                break;
            case R.id.Rl_commit:

                //
                if (mPicList.get(0).getPath() == null) {
                    showErrorToast("请上传身份证正面照");
                    return;
                }
                if (mPicList.get(1).getPath() == null) {
                    showErrorToast("请上传身份证反面照");
                    return;
                }
                if (mPicList.get(2).getPath() == null) {
                    showErrorToast("请上传手持身份证照");
                    return;
                }
                showLoadingDialog(null);
                if (mUtils == null) {
                    mUtils = new UpImageUtils(this, UpImageUtils.TAG_QMCS_U_IDENTITY, UserManger.getUUid(), new UpImageUtils.UpImageListener() {
                        @Override
                        public void onUpSuccess() {
                            String identityIdPicFront = mUtils.getSavePath() + "[" + (new File(mPicList.get(0).getPath())).getName() + "]";
                            String identityIdPicBack = mUtils.getSavePath() + "[" + (new File(mPicList.get(1).getPath())).getName() + "]";
                            String identityIdPicTake = mUtils.getSavePath() + "[" + (new File(mPicList.get(2).getPath())).getName() + "]";

                            doHttp(RetrofitUtils.createApi(Mine.class).applyTransportAngel(getIntent().getStringExtra("name"), getIntent().getStringExtra("code"),
                                    getIntent().getStringExtra("otherName"), Long.parseLong(getIntent().getStringExtra("phone")), identityIdPicTake, identityIdPicBack, identityIdPicFront), 1);
                        }

                        @Override
                        public void onUpFailure() {
                            dismissLoadingDialog();
                            mProgressBar.setProgress(100);
                            mTvProgress.setText("提交认证");
                        }

                        @Override
                        public void onUpLoading(int progress) {
                            if (mProgressBar != null) {
                                mProgressBar.setProgress(progress);
                                mTvProgress.setText("正在请求(" + progress + "%)");
                            }
                        }
                    });
                }

                mUtils.upPhoto(mPicList);
                break;

        }
    }


    private void showPicDialog() {

        if (mDefaultDialogBuilder == null) {

            mDefaultDialogBuilder = new FormBotomDefaultDialogBuilder(this);
            mDefaultDialogBuilder.setFBFirstBtnText("拍照");
            mDefaultDialogBuilder.setFBLastBtnText("相册");
            LubanOptions option = new LubanOptions.Builder()
                    .setMaxSize(UserManger.MAXSIZE * 5)
                    .create();
            final CompressConfig config = CompressConfig.ofLuban(option);

            final CropOptions cropOptions = new CropOptions.Builder().setAspectX(1).setAspectY(1).create();
            mDefaultDialogBuilder.setFBFirstBtnClick(new FormBotomDefaultDialogBuilder.DialogBtnCallBack() {
                @Override
                public void dialogBtnOnClick() {
                    getTakePhoto().onEnableCompress(config, false);
                    getTakePhoto().onPickFromCapture(getImageUri());

                }
            });
            mDefaultDialogBuilder.setFBLastBtnClick(new FormBotomDefaultDialogBuilder.DialogBtnCallBack() {
                @Override
                public void dialogBtnOnClick() {

                    getTakePhoto().onEnableCompress(config, false);
                    getTakePhoto().onPickFromGallery();
                }
            });

        }

        mDefaultDialogBuilder.show();

    }


    @Override
    public void takeSuccess(final TResult result) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mPicList.get(chooseType).setPath(result.getImage().getCompressPath());
                switch (chooseType) {
                    case 0:
                        if (mImgZhen.getVisibility() == View.GONE) {
                            mImgZhen.setVisibility(View.VISIBLE);
                            mLlZhen.setVisibility(View.GONE);
                        }
                        mImgZhen.setImageURI(Uri.parse("file://" + result.getImage().getCompressPath()));


                        Logger.v("path==" + result.getImage().getCompressPath());
                        break;
                    case 1:
                        if (mImgFan.getVisibility() == View.GONE) {
                            mImgFan.setVisibility(View.VISIBLE);
                            mLlFan.setVisibility(View.GONE);
                        }
                        mImgFan.setImageURI(Uri.parse("file://" + result.getImage().getCompressPath()));
                        break;
                    case 2:
                        if (mImgShou.getVisibility() == View.GONE) {
                            mImgShou.setVisibility(View.VISIBLE);
                            mLlShou.setVisibility(View.GONE);
                        }
                        mImgShou.setImageURI(Uri.parse("file://" + result.getImage().getCompressPath()));
                        break;
                }

            }
        });


    }

    @Override
    public void takeFail(TResult result, String msg) {

        showErrorToast("该图片已破损，请选择其他图片");
    }

    @Override
    public void takeCancel() {

    }


    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);

        switch (what) {
            case 2:
                //删掉以前的图片
                List<String> objKey = AppJsonUtil.getArrayList(result, "listKey", String.class);
                new UpImageUtils().deleteOtherFile(objKey, UpImageUtils.TAG_QMCS_U_IDENTITY);


                break;
            case 1:
                UpImageUtils.isCommitSuccess = true;
                Bundle bundle = new Bundle();
                bundle.putInt("type", MineTransferStateAty.TYPE_COMMIT);
                AppManger.getInstance().killActivity(MineAuthenticationActivity.class);
                startActivity(MineTransferStateAty.class, bundle);
                break;
        }


    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onFailure(result, call, response, what);


        if (mUtils != null) {
            mUtils.deleteFile();
        }
        if (mProgressBar != null) {
            mProgressBar.setProgress(100);
            mTvProgress.setText("提交认证");
        }


    }

    @Override
    public void onError(Call<ResponseBody> call, Throwable t, int what) {
        super.onError(call, t, what);
        if (mUtils != null) {
            mUtils.deleteFile();
            if (mProgressBar != null) {
                mProgressBar.setProgress(100);
                mTvProgress.setText("提交认证");
            }

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUtils != null) {
            mUtils.isOnDestoryDoing();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mUtils != null) {
            mUtils.isOnBackPressedDoing();
            if (mProgressBar != null) {
                mProgressBar.setProgress(100);
                mTvProgress.setText("提交认证");
            }
        }
    }


}
