package net.jiawa.jobhunter.widgets;

/**
 * Created by lenovo on 2017/3/26.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import net.jiawa.debughelper.XLog;
import net.jiawa.jobhunter.R;

/**
 * 仿照OSChina的SolarSystemView写的一个
 * 类似水波纹从中心散开的View,
 * 可以用来做为Loading的View
 */
public class RippleLoadingView extends View implements Runnable {

    // View的刷新间隔
    public static final int FLUSH_RATE = 40;
    private int mFlushRate = FLUSH_RATE;
    // 同时只有三条水波纹
    private static final int RIPPLE_COUNT = 3;
    private Bitmap mCacheBitmap;
    // 水波纹的中心点X和Y
    private float mCenterX = -1;
    private float mCenterY = -1;
    // 中间的初始的圆心
    private Paint mCirclePaint;
    // 设定初始的中心圆心的半径
    private int mCircleRadius;

    // 下面分别是水波纹运动的最小和最大半径
    private float mRippleMinRadius;
    private float mRippleMaxRadius;
    private Paint mRipplePaint;

    public RippleLoadingView(Context context) {
        super(context);
        //setWillNotDraw(false);
    }

    public RippleLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //setWillNotDraw(false);
    }

    private void postRepaint() {
        removeCallbacks(this);
        postDelayed(this, mFlushRate);
    }

    @Override
    public void run() {
        // 强制View刷新,
        // 触发onDraw进行View的绘制
        invalidate();
        postRepaint();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mCacheBitmap != null) {
            mCacheBitmap.recycle();
            mCacheBitmap = null;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if ( mCenterX < 0 || mCenterY < 0) {
            // 获取当前水波纹的中心坐标
            Rect r = new Rect(0, 0, w, h);
            mCenterX = r.exactCenterX();
            mCenterY = r.exactCenterY();
        }
        // 从这里开始进行View的绘制
        prepare();
    }

    /**
     * 初始化中心圆心的半径
     */
    private void initCircleRadius() {
        final int minRadius = 20;
        final int minCanvas = getWidth() < getHeight() ? getWidth() : getHeight();
        mCircleRadius = minCanvas/6 > minRadius ? minCanvas/6 : minRadius;
        XLog.d(false, 1, "width: " + getWidth() + ", height: " + getHeight() + ", minCanvas/4: " + minCanvas/4);
    }

    /**
     * 初始化Ripple水波纹运动的运动半径轨迹
     */
    private void initRippleRadius() {
        // 以中间圆心的半径作为运动的最小半径
        mRippleMinRadius = mCircleRadius;
        // 最大运动半径就是当前View的中间斜连线的一半
        final int width2 = getWidth() * getWidth();
        final int height2 = getHeight() * getHeight();
        mRippleMaxRadius = (float) (Math.sqrt(width2 + height2) / 2);
    }

    private synchronized void prepare() {
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(getContext().getResources().getColor(R.color.gray_666));
        initCircleRadius();
        initRippleRadius();
        // 初始将中心园绘制在mCacheBitmap上面,
        // 这样不需要每次在onDraw中都绘制一遍这个中心园,
        // 只需要更新每条水波纹即可
        if (mCacheBitmap != null) {
            mCacheBitmap.recycle();
            mCacheBitmap = null;
        }
        mCacheBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mCacheBitmap);
        // 绘制View的background
        if (getBackground() != null) {
            getBackground().draw(canvas);
        }
        // 绘制中心园
        canvas.drawCircle(mCenterX, mCenterY, mCircleRadius, mCirclePaint);

        mRipplePaint = new Paint();
        mRipplePaint.setAntiAlias(true);
        mRipplePaint.setColor(getContext().getResources().getColor(R.color.colorAccent));
        mRipplePaint.setStyle(Paint.Style.STROKE);
        postRepaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 这样所有的View都需要我们自己来完成绘制
        // super.onDraw(canvas);
        // 保存原始绘制状态
        int count = canvas.save();
        if (mCacheBitmap != null) canvas.drawBitmap(mCacheBitmap, 0, 0, mCirclePaint);

        // 尝试绘制两条极端的运动半径下的水波纹
        canvas.drawCircle(mCenterX, mCenterY, mRippleMaxRadius - 10, mRipplePaint);
        canvas.drawCircle(mCenterX, mCenterY, mRippleMinRadius + 10, mRipplePaint);

        // 恢复原始canvas状态
        canvas.restoreToCount(count);
    }
}
