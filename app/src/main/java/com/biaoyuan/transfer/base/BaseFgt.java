package com.biaoyuan.transfer.base;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.and.yzy.frame.adapter.recyclerview.BaseQuickAdapter;
import com.and.yzy.frame.base.BaseFrameLazyFgt;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.config.UserManger;
import com.biaoyuan.transfer.ui.login.LoginAty;
import com.biaoyuan.transfer.util.AppJsonUtil;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by yzy on 2017/3/28.
 */

public abstract class BaseFgt extends BaseFrameLazyFgt {

    public boolean isShowOnFailureToast = true;

    @Override
    public void btnClick(View view) {

    }


    @Override
    public boolean setIsInitRequestData() {
        return true;
    }


    @Override
    public void onUserVisible() {

    }

    @Override
    public void onUserInvisible() {
        super.onUserInvisible();

    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onFailure(result, call, response, what);
        if (isShowOnFailureToast) {
            String msg = AppJsonUtil.getString(result, "msg");
            if (msg != null) {
                showErrorToast(msg);
            }
        }
        int code = AppJsonUtil.getInt(result, "code");

        if (code == 401) {
            showErrorToast("登录已失效");
            //      AppManger.getInstance().killAllActivity();
            setHasAnimiation(false);
            UserManger.setToken("");
            UserManger.setUUid("");
            UserManger.setIsLogin(false);
            Intent intent = new Intent(getActivity(), LoginAty.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            getActivity().finish();
        }

    }


    /**
     * 设置空数据
     *
     * @param quickAdapter
     * @param noDate
     */
    public void setEmptyView(BaseQuickAdapter quickAdapter, String noDate) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.public_defect_main, null, false);

        if (noDate != null && noDate.length() > 0) {
            TextView tv_nodate = (TextView) view.findViewById(R.id.tv_no_data);
            tv_nodate.setText(noDate);
        }
        quickAdapter.setEmptyView(view);

    }
}
