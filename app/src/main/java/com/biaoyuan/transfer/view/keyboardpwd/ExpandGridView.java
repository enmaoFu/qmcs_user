package com.biaoyuan.transfer.view.keyboardpwd;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @title  : 底部弹出密码输入
 * @create : 2017/5/25
 * @author ：enmaoFu
 */
public class ExpandGridView extends GridView {

    public ExpandGridView(Context context) {
        super(context);
    }

    public ExpandGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }


}
