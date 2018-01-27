package com.biaoyuan.transfer.ui.mine;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
 * @title  :意见反馈页面
 * @create : 2017/4/24
 * @author  ：enmaoFu
 */
public class MineFeedbackActivity extends BaseAty {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.inpiut_translate)
    EditText inputTranslate;
    @Bind(R.id.textView)
    TextView textView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_feedback;
    }

    @Override
    public void initData() {
        initToolbar(mToolbar,"意见反馈");
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mine_feedback_toolbar_menu,menu);
        menu.getItem(0).setTitle("提交");
        return true;
    }*/

    @Override
    public boolean setIsInitRequestData() {
        return false;
    }

    @Override
    public void requestData() {

    }

    @OnClick({R.id.textView})
    @Override
    public void btnClick(View view) {
        switch (view.getId()){
            case R.id.textView:
                String getTranslate = inputTranslate.getText().toString().trim();
                if(getTranslate.length() == 0){
                    showErrorToast("请输入您的意见");
                }else if(getTranslate.length() > 300){
                    showErrorToast("字数请控制在300字以内");
                }else{
                    showLoadingDialog(null);
                    doHttp(RetrofitUtils.createApi(Mine.class).addFeedback(getTranslate),1);
                }
                break;
        }
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what){
            case 1:
                showToast("反馈成功,感谢您的意见");
                inputTranslate.setText("");
                break;
        }
    }

}
