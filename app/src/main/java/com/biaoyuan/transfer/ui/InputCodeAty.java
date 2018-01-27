package com.biaoyuan.transfer.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.base.BaseAty;
import com.biaoyuan.transfer.view.MyAutoCompleteTextView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Title  :手动输入
 * Create : 2017/6/3
 * Author ：chen
 */

public class InputCodeAty extends BaseAty {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tv_tip)
    TextView mTvTip;


    @Bind(R.id.et_content)
    MyAutoCompleteTextView mEtContent;
    private int mType;


    @Override
    public int getLayoutId() {
        return R.layout.activity_input_code;
    }

    @Override
    public void initData() {

        initToolbar(mToolbar, "手动输入");

        mType = getIntent().getIntExtra("type", 0);

        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,new String[]{"QMWD"});
        mEtContent.setAdapter(arrayAdapter);
        mEtContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(mEtContent,InputMethodManager.SHOW_FORCED);
            }
        });

    }

    @OnClick({R.id.btn_commit})
    @Override
    public void btnClick(View view) {
        super.btnClick(view);
        switch (view.getId()) {
            case R.id.btn_commit:
               String mm=mEtContent.getText().toString().trim();

                if (TextUtils.isEmpty(mm)){

                    showErrorToast("请填写正确的打包码");
                    return;
                }
                if (!mm.contains("QMWD")||mm.length()<16){
                    showErrorToast("请填写正确的打包码");
                    return;
                }


                Intent intent=getIntent();
                intent.putExtra("codeResult",mm);
                setResult(RESULT_OK,intent);
                finish();
                break;

        }

    }

    @Override
    public void requestData() {

    }
}
