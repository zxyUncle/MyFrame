package com.normal.main;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * Created by zsf on 2022/9/14 14:53
 * *******************
 *    滑动反弹，放大
 * *******************
 */
public class BounceScrollView extends ScrollView {
    private View mZoomView;
    private int mZoomViewWidth;
    private int mZoomViewHeight;
    private float mReplyRate = 0.5f;//回调系数，越大，回调越慢
    private float mZoomRate = 0.5f;//缩放系数，缩放系数越大，变化的越大
    private int changeValue;//开始放大的变化值
    private boolean isMagnify = false;//是否开始放大
    private float firstPosition;//记录第一次按下的位置


    private float mScrolRate = 1.5f;//滑动系数
    private View inner;// 孩子View
    private float y;// 点击时y坐标
    private Rect normal = new Rect();    // 矩形(这里仅仅是个形式，仅仅是用于推断是否须要动画.)
    private boolean isScrooll = false;// 是否滑动开始计算

    public BounceScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setmZoomView(View mZoomView, int changeValue) {
        mZoomViewWidth = mZoomView.getMeasuredWidth();
        mZoomViewHeight = mZoomView.getMeasuredHeight();
        this.mZoomView = mZoomView;
        this.changeValue = changeValue;
    }

    /***
     * 依据 XML 生成视图工作完毕.该函数在生成视图的最后调用，在全部子视图加入完之后. 即使子类覆盖了 onFinishInflate
     * 方法，也应该调用父类的方法，使该方法得以运行.
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0) {
            inner = getChildAt(0);
        }
    }

    /***
     * 监听touch
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (inner != null) {
            commOnTouchEvent(ev);
        }
        return super.onTouchEvent(ev);
    }

    /***
     * 触摸事件
     *
     * @param ev
     */
    public void commOnTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                // 手指松开.
                if (isNeedAnimation()) {
                    setScrollAnimaotr();
                    isScrooll = false;
                    zoomReplyImage();
                }
                break;
            /***
             * 排除出第一次移动计算。由于第一次无法得知y坐标。 在MotionEvent.ACTION_DOWN中获取不到，
             * 由于此时是MyScrollView的touch事件传递到到了LIstView的孩子item上面.所以从第二次计算開始.
             * 然而我们也要进行初始化，就是第一次移动的时候让滑动距离归0. 之后记录准确了就正常运行.
             */
            case MotionEvent.ACTION_MOVE:
                final float preY = y;// 按下时的y坐标
                float nowY = ev.getY();// 时时y坐标
                int deltaY = (int) (preY - nowY);// 滑动距离
                if (!isScrooll) {
                    deltaY = 0; // 在这里要归0.
                }

                y = nowY;
                // 当滚动到最上或者最下时就不会再滚动，这时移动布局
                int topDetaY = inner.getTop() - (int) (deltaY / mScrolRate);
                if (isNeedMove()) {
                    if (topDetaY > changeValue) {
                        int distance = (int) ((ev.getY() - firstPosition) * mZoomRate); // 滚动距离乘以一个系数
                        if (distance < 0) { // 当前位置比记录位置要小，正常返回
                            break;
                        }
                        isMagnify = true;
                        setZoom(distance);
                    } else {
                        if (!isMagnify)
                            firstPosition = ev.getY();// 开始放大的时候的坐标
                    }
                    //滑动
                    setScrollValue(deltaY);
                }
                isScrooll = true;
                break;

            default:
                break;
        }
    }

    /**
     * 设置滑动的动画
     * @param deltaY
     */
    private void setScrollValue(int deltaY){
        // 初始化头部矩形
        if (normal.isEmpty()) {
            // 保存正常的布局位置
            normal.set(inner.getLeft(), inner.getTop(),
                    inner.getRight(), inner.getBottom());
        }
        // 移动布局
        inner.layout(inner.getLeft(), inner.getTop() - (int) (deltaY /mScrolRate),
                inner.getRight(), inner.getBottom() - (int) (deltaY / mScrolRate));
    }

    /***
     * 滑动回缩动画
     */
    public void setScrollAnimaotr() {
        // 开启移动动画
        TranslateAnimation ta = new TranslateAnimation(0, 0, inner.getTop(),
                normal.top);
        ta.setDuration(200);
        inner.startAnimation(ta);
        // 设置回到正常的布局位置
        inner.layout(normal.left, normal.top, normal.right, normal.bottom);
        normal.setEmpty();

    }

    // 是否须要开启动画
    public boolean isNeedAnimation() {
        return !normal.isEmpty();
    }

    /***
     * 是否须要移动布局 inner.getMeasuredHeight():获取的是控件的总高度
     * getHeight()：获取的是屏幕的高度
     * @return
     */
    public boolean isNeedMove() {
        int offset = inner.getMeasuredHeight() - getHeight();
        int scrollY = getScrollY();
//		Log.e("jj", "scrolly=" + scrollY);
        // 0是顶部。后面那个是底部
        if (scrollY == 0 || scrollY == offset) {
            return true;
        }
        return false;
    }

    //缩放回弹动画
    private void zoomReplyImage() {
        isMagnify = false;//停止放大
        float distance = mZoomView.getMeasuredWidth() - mZoomViewWidth;
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(distance, 0f).setDuration((long) (distance * mReplyRate));
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setZoom((Float) animation.getAnimatedValue());
            }
        });
        valueAnimator.start();
    }

    //扩大缩放动画
    public void setZoom(float zoom) {
        if (mZoomViewWidth <= 0 || mZoomViewHeight <= 0) {
            return;
        }
        ViewGroup.LayoutParams lp = mZoomView.getLayoutParams();
        lp.width = (int) (mZoomViewWidth + zoom);
        lp.height = (int) (mZoomViewHeight * ((mZoomViewWidth + zoom) / mZoomViewWidth));
        ((MarginLayoutParams) lp).setMargins(-(lp.width - mZoomViewWidth) / 2, 0, 0, 0);
        mZoomView.setLayoutParams(lp);
    }

}
