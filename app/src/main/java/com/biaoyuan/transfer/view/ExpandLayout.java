package com.biaoyuan.transfer.view;

/**
 * Title  :
 * Create : 2017/6/24
 * Author ：chen
 */

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.orhanobut.logger.Logger;

/**
 * 带过渡动画的折叠收缩布局
 */
public class ExpandLayout extends LinearLayout {

    public ExpandLayout(Context context) {
        this(context, null);

    }

    public ExpandLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private View layoutView;
    private int viewHeight;
    private boolean isExpand;
    private long animationDuration;

    public void setExpandListener(onExpandListener expandListener) {
        mExpandListener = expandListener;
    }

    private onExpandListener mExpandListener;



    private void initView() {
        layoutView = this;
        isExpand = true;
        animationDuration = 1000;
        setViewDimensions();
    }

    /**
     * @param isExpand 初始状态是否折叠
     */
    public void initExpand(boolean isExpand) {
        this.isExpand = isExpand;
        requestLayout();
        viewHeight=getMeasuredHeight();
        Logger.v("getHeight==="+getHeight());
        Logger.v("viewHeight==="+viewHeight);
        if (!isExpand) {
            animateToggle(10);
        }
    }

    /**
     * 设置动画时间
     *
     * @param animationDuration 动画时间
     */
    public void setAnimationDuration(long animationDuration) {
        this.animationDuration = animationDuration;
    }

    /**
     * 获取subView的总高度
     * View.post()的runnable对象中的方法会在View的measure、layout等事件后触发
     */
    private void setViewDimensions() {
        layoutView.post(new Runnable() {
            @Override
            public void run() {
                if (viewHeight <= 0) {
                    layoutView.requestLayout();

                    viewHeight = layoutView.getMeasuredHeight();
                    Logger.v("height=="+viewHeight);
                }
            }
        });
    }


    public static void setViewHeight(View view, int height) {
        final ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = height;

        view.requestLayout();
    }

    /**
     * 切换动画实现
     */
    private void animateToggle(long animationDuration) {

        ValueAnimator heightAnimation = isExpand ?
                ValueAnimator.ofFloat(0f, viewHeight) : ValueAnimator.ofFloat(viewHeight, 0f);
        heightAnimation.setDuration(animationDuration / 2);
        heightAnimation.setStartDelay(animationDuration / 2);

        heightAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float val = (float) animation.getAnimatedValue();
                setViewHeight(layoutView, (int) val);
            }
        });

        heightAnimation.start();
        heightAnimation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Logger.v("onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Logger.v("onAnimationEnd");

                if (mExpandListener!=null){
                    if (isExpand){
                        mExpandListener.expand();
                    }else {
                        mExpandListener.collapse();
                    }
                }


            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

    public boolean isExpand() {
        return isExpand;
    }

    /**
     * 折叠view
     */
    public void collapse() {
        isExpand = false;
        animateToggle(animationDuration);
    }

    /**
     * 展开view
     */
    public void expand() {
        isExpand = true;
        animateToggle(animationDuration);
    }

    public void toggleExpand() {

        if (isExpand) {
            collapse();
        } else {
            expand();
        }
    }

    public interface  onExpandListener{
        void expand();
        void  collapse();
    }
}