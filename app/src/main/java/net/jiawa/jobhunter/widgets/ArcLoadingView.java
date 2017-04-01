package net.jiawa.jobhunter.widgets;

/**
 * Created by lenovo on 2017/3/26.
 */

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.DecelerateInterpolator;

import net.jiawa.debughelper.XLog;
import net.jiawa.jobhunter.R;

/**
 * 仿照OSChina的SolarSystemView写的一个
 * 类似水波纹从中心散开的View,
 * 可以用来做为Loading的View
 */
public class ArcLoadingView extends View implements Runnable {

    // View的刷新间隔
    public static final int FLUSH_RATE = 40;
    private int mFlushRate = FLUSH_RATE;
    // 绘制背景图片的缓存Bitmap
    private Bitmap mCacheBitmap;
    // 图片的中心点坐标
    private float mCenterX = -1;
    private float mCenterY = -1;
    // 中间的初始的圆心
    private Paint mCirclePaint;
    // 设定初始的中心圆心的半径
    private int mCircleRadius;
    private final int mCircleStrokeWidth = 2;
    // 运动的小球
    private Paint mArcPaint;
    private final int mArcWidth = 10;
    private Arc mArc;

    // 动画时间
    final long DURATION = (long) (10 * 1000);

    public ArcLoadingView(Context context) {
        super(context);
    }

    public ArcLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
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

    private void initCircle() {
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(getContext().getResources().getColor(R.color.jiawa_main));
        mCirclePaint.setStrokeWidth(mCircleStrokeWidth);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setAlpha(160);

        final int minCanvasWidth = getWidth() < getHeight() ? getWidth() : getHeight();
        mCircleRadius = minCanvasWidth/2 - 5 - mArcWidth;
        XLog.d(true, 1, "mCircleRadius: " + mCircleRadius + ", width: " + getWidth() + ", height: " + getHeight());
    }

    private void initArc() {
        mArc = new Arc();
    }

    private void startAnim() {
        final ValueAnimator anim = new ValueAnimator();
        // 小球滚动的度数
        anim.setIntValues(0, 720);
        anim.setDuration(DURATION);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.setRepeatMode(ValueAnimator.RESTART);
        // 不停的循环动画
        anim.setRepeatCount(-1);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // mBall.update((Integer) animation.getAnimatedValue());
                mArc.update((Integer) animation.getAnimatedValue());
                XLog.d(false, 1, "angle: " + animation.getAnimatedValue());
            }
        });
        anim.start();
    }

    private synchronized void prepare() {
        initCircle();
        initArc();

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
        startAnim();
        postRepaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 这样所有的View都需要我们自己来完成绘制
        // super.onDraw(canvas);
        // 保存原始绘制状态
        int count = canvas.save();
        if (mCacheBitmap != null) canvas.drawBitmap(mCacheBitmap, 0, 0, mCirclePaint);
        // canvas.drawOval(mArc.oval, mArc.paint);
        canvas.drawArc(mArc.oval, mArc.startAngle, mArc.sweepAngle, false, mArc.paint);

        // 恢复原始canvas状态
        canvas.restoreToCount(count);
    }

    /**
     *
     *  下面是drawArc要实现的参数
        public void drawArc(@NonNull RectF oval, float startAngle, float sweepAngle, boolean useCenter,
                                        @NonNull Paint paint) {
            drawArc(oval.left, oval.top, oval.right, oval.bottom, startAngle, sweepAngle, useCenter, paint);
        }
     */
    class Arc {
        final RectF oval;
        float startAngle;
        float sweepAngle;
        final Paint paint;
        Arc() {
            update(0);
            // 创建oval,
            // 就是背景的圆
            oval = new RectF(mCenterX - mCircleRadius, mCenterY - mCircleRadius, mCenterX + mCircleRadius, mCenterY + mCircleRadius);
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(getContext().getResources().getColor(R.color.jiawa_main));
            paint.setAlpha(255);
            paint.setStrokeWidth(mArcWidth);
            paint.setStyle(Paint.Style.STROKE);
        }
        void update(int angle) {
            if (angle < 360) {
                // drawArc的0是最右边的点
                // 270才是最上面的点
                startAngle = 270;
                // 表示从startAngle开始要扫过角度,这里是从0到360
                sweepAngle = angle;
            } else if (angle < 360 + 90) {
                startAngle = angle - 360 + 270;
                sweepAngle = 270 + (90 - (startAngle - 270));
            } else {
                startAngle = angle - 360 - 90;
                sweepAngle = 360 - startAngle - 90;
            }
        }
    }
}
