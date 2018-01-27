package com.biaoyuan.transfer.ui.mine;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.and.yzy.frame.util.RetrofitUtils;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.base.BaseAty;
import com.biaoyuan.transfer.http.Mine;

import butterknife.Bind;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Title  :设置昵称
 * Create : 2017/4/26
 * Author ：chen
 */

public class MineSetNameActivity extends BaseAty {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.et_name)
    EditText mEtName;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_set_name;
    }

    @Override
    public void initData() {
        initToolbar(mToolbar, "修改昵称");
        String name=getIntent().getStringExtra("name");
        if (!TextUtils.isEmpty(name)){
            mEtName.setText(name);
        }

    }

    @Override
    public void requestData() {

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.menu_edit){

            String name=mEtName.getText().toString().trim();

            if (name.length()<2){
                showErrorToast("昵称长度不够");
                return super.onOptionsItemSelected(item);
            }

            showLoadingDialog(null);
            doHttp(RetrofitUtils.createApi(Mine.class).setName(name),1);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mine_feedback_toolbar_menu, menu);
        menu.getItem(0).setTitle("保存");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        showToast("修改成功");
        Intent intent=getIntent();
        intent.putExtra("name",mEtName.getText().toString().trim());
        setResult(RESULT_OK,intent);
        finish();
    }
}
