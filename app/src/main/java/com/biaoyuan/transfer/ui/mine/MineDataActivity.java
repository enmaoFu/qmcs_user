package com.biaoyuan.transfer.ui.mine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.and.yzy.frame.util.RetrofitUtils;
import com.and.yzy.frame.view.dialog.Effectstype;
import com.and.yzy.frame.view.dialog.FormBotomDefaultDialogBuilder;
import com.and.yzy.frame.view.dialog.MaterialDialog;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.base.BasePhotoAty;
import com.biaoyuan.transfer.config.UserManger;
import com.biaoyuan.transfer.domain.PicInfo;
import com.biaoyuan.transfer.domain.UserInfo;
import com.biaoyuan.transfer.http.Mine;
import com.biaoyuan.transfer.ui.login.LoginAty;
import com.biaoyuan.transfer.ui.send.SendCommonAddressActivity;
import com.biaoyuan.transfer.util.JPushSetAliasUtil;
import com.biaoyuan.transfer.util.MyNumberFormat;
import com.biaoyuan.transfer.util.UpImageUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.LubanOptions;
import com.jph.takephoto.model.TResult;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Title  :账号管理
 * Create : 2017/4/26
 * Author ：chen
 */

public class MineDataActivity extends BasePhotoAty {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.mine_img)
    SimpleDraweeView mMineImg;
    @Bind(R.id.tv_name)
    TextView mTvName;
    @Bind(R.id.tv_phone)
    TextView mTvPhone;


    private UserInfo mInfo;


    private FormBotomDefaultDialogBuilder mDefaultDialogBuilder;
    private UpImageUtils mUtils;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_data;
    }

    @Override
    public void initData() {
        initToolbar(mToolbar, "账号管理");
        mInfo = getIntent().getParcelableExtra("data");

        if (!TextUtils.isEmpty(mInfo.getRawUserPortraitUrl())) {
            mMineImg.setImageURI(Uri.parse(mInfo.getUserPortraitUrl()));
        }


        mTvPhone.setText(MyNumberFormat.toPwdPhone(mInfo.getUserTelphone()));
        if (!TextUtils.isEmpty(mInfo.getUserLoginName())) {
            mTvName.setText(mInfo.getUserLoginName());
        }

    }

    @Override
    public void requestData() {

    }

    @OnClick({R.id.ll_set_name, R.id.tv_address, R.id.tv_pwd, R.id.tv_commit, R.id.ll_phone, R.id.ll_img})
    @Override
    public void btnClick(View view) {

        switch (view.getId()) {
            case R.id.ll_img:

                showPicDialog();


                break;
            case R.id.ll_set_name:
               /* Bundle b = new Bundle();
                b.putString("name", mTvName.getText().toString().trim());
                startActivityForResult(MineSetNameActivity.class, b, 2);*/
                break;
            case R.id.ll_phone:
                startActivityForResult(MineSetPhoneAty.class, null, 3);
                break;
            case R.id.tv_address:
                Bundle b1 = new Bundle();
                b1.putInt("type", 1);
                b1.putBoolean("click", false);
                startActivity(SendCommonAddressActivity.class, b1);
                break;
            case R.id.tv_pwd:
                Bundle bundle = new Bundle();
                bundle.putString("phone", mTvPhone.getText().toString().trim());
                startActivityForResult(MinePwdActivity.class, bundle, 1);
                break;
            case R.id.tv_commit:

                MaterialDialog dialog = new MaterialDialog(this);

                dialog.setMDMessage("是否立即退出当前账号?").setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                    @Override
                    public void dialogBtnOnClick() {

                        JPushSetAliasUtil jPushSetAliasUtil = new JPushSetAliasUtil(MineDataActivity.this);
                        jPushSetAliasUtil.setAlias("");
                        UserManger.setIsPush("0");
                        UserManger.setToken("");
                        UserManger.setUUid("");
                        Intent intent = new Intent(MineDataActivity.this, LoginAty.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        setHasAnimiation(false);
                        UserManger.setIsLogin(false);
                        finish();
                    }
                }).setMDEffect(Effectstype.Shake).show();


                break;

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            switch (requestCode) {
                case 1:

                    break;
                case 2:
                    //设置昵称
                    mTvName.setText(data.getStringExtra("name"));
                    break;
                case 3:
                    //设置电话
                    mTvPhone.setText(MyNumberFormat.toPwdPhone(Long.parseLong(data.getStringExtra("phone"))));
                    break;
            }


        }

    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what) {

            case 1:
                //记录提交状态必须
                mUtils.isCommitSuccess = true;
                showToast("头像上传成功");
                //删除以前的图
                if (!TextUtils.isEmpty(mInfo.getRawUserPortraitUrl())) {
                    mUtils.deleteFile(mInfo.getRawUserPortraitUrl());
                }
                MineFgt.isChangeImage = true;
                break;
        }
    }


    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onFailure(result, call, response, what);

        switch (what) {
            case 1:
                if (mUtils != null) {
                    mUtils.deleteFile();
                }
                showErrorToast("头像上传失败");
                break;

        }
    }

    @Override
    public void onError(Call<ResponseBody> call, Throwable t, int what) {
        super.onError(call, t, what);
        if (mUtils != null) {
            mUtils.deleteFile();
        }
        showErrorToast("头像上传失败");
    }


    @Override
    public void onDestroy() {
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
        }
    }

    /**
     * 照片选择弹出框
     */
    private void showPicDialog() {

        if (mDefaultDialogBuilder == null) {

            mDefaultDialogBuilder = new FormBotomDefaultDialogBuilder(this);
            mDefaultDialogBuilder.setFBFirstBtnText("拍照");
            mDefaultDialogBuilder.setFBLastBtnText("相册");
            //压缩
            LubanOptions option = new LubanOptions.Builder()
                    .setMaxSize(UserManger.MAXSIZE)//设置压缩的最大大小，上传照片的最大值
                    .create();
            //进行压缩配置
            final CompressConfig config = CompressConfig.ofLuban(option);

            //进行剪裁配置
            final CropOptions cropOptions = new CropOptions.Builder().setAspectX(1).setAspectY(1).create();

            //点击进行拍照
            mDefaultDialogBuilder.setFBFirstBtnClick(new FormBotomDefaultDialogBuilder.DialogBtnCallBack() {
                @Override
                public void dialogBtnOnClick() {
                    //启用图片压缩,设置上面的压缩配置，不显示进度对话框
                    getTakePhoto().onEnableCompress(config, false);
                    //启用裁剪，设置裁剪路径，设置上面的裁剪配置
                    getTakePhoto().onPickFromCaptureWithCrop(getImageUri(), cropOptions);

                }
            });
            //点击进行相册选择
            mDefaultDialogBuilder.setFBLastBtnClick(new FormBotomDefaultDialogBuilder.DialogBtnCallBack() {
                @Override
                public void dialogBtnOnClick() {

                    getTakePhoto().onEnableCompress(config, false);
                    getTakePhoto().onPickFromGalleryWithCrop(getImageUri(), cropOptions);
                }
            });

        }

        mDefaultDialogBuilder.show();

    }

    /**
     * 成功
     *
     * @param result
     */
    @Override
    public void takeSuccess(final TResult result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Logger.v("path==" + result.getImage().getCompressPath());
                mMineImg.setImageURI(Uri.parse("file://" + result.getImage().getCompressPath()));
                PicInfo picInfo = new PicInfo();
                //设置压缩路径到实体类
                picInfo.setPath(result.getImage().getCompressPath());
                List<PicInfo> picInfoList = new ArrayList<PicInfo>();
                picInfoList.add(picInfo);
                showLoadingDialog(null);
                if (mUtils == null) {
                    mUtils = new UpImageUtils(MineDataActivity.this, UpImageUtils.TAG_QMCS_U_HEADER, UserManger.getUUid(), new UpImageUtils.UpImageListener() {
                        @Override
                        public void onUpSuccess() {
                            doHttp(RetrofitUtils.createApi(Mine.class).setHeader(mUtils.getImagePath()), 1);
                        }

                        @Override
                        public void onUpFailure() {
                            dismissLoadingDialog();
                        }

                        @Override
                        public void onUpLoading(int progress) {
                        }
                    });
                }

                mUtils.upPhoto(picInfoList);
            }
        });
    }

    /**
     * 失败
     *
     * @param result
     * @param msg
     */
    @Override
    public void takeFail(TResult result, String msg) {

    }

    /**
     * 取消
     */
    @Override
    public void takeCancel() {

    }
}
