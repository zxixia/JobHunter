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
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import net.jiawa.debughelper.XLog;
import net.jiawa.jobhunter.R;

/**
 * 仿照OSChina的SolarSystemView写的一个
 * 类似水波纹从中心散开的View,
 * 可以用来做为Loading的View
 */
public class CircleLoadingView extends View implements Runnable {

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
    private Paint mBallPaint;
    private final int BALL_RADIUS = 10;
    private Ball mBall;

    // 动画时间
    final long DURATION = (long) (1.5 * 1000);

    public CircleLoadingView(Context context) {
        super(context);
    }

    public CircleLoadingView(Context context, AttributeSet attrs) {
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
        mCircleRadius = minCanvasWidth/2 - 5 - BALL_RADIUS;
        XLog.d(true, 1, "mCircleRadius: " + mCircleRadius + ", width: " + getWidth() + ", height: " + getHeight());
    }

    private void initBall() {
        mBall = new Ball(BALL_RADIUS, 0, mCircleRadius, mCircleStrokeWidth);
        mBallPaint = new Paint();
        mBallPaint.setAntiAlias(true);
        mBallPaint.setColor(getContext().getResources().getColor(R.color.jiawa_main));
    }

    private void startAnim() {
        final ValueAnimator anim = new ValueAnimator();
        // 小球滚动的度数
        anim.setIntValues(0, 360);
        anim.setDuration(DURATION);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.setRepeatMode(ValueAnimator.RESTART);
        // 不停的循环动画
        anim.setRepeatCount(-1);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mBall.update((Integer) animation.getAnimatedValue());
                XLog.d(false, 1, "angle: " + animation.getAnimatedValue());
            }
        });
        anim.start();
    }

    private synchronized void prepare() {
        initCircle();
        initBall();

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
        canvas.translate(mBall.x, mBall.y);
        //canvas.translate(mBall.x, mBall.y);
        canvas.drawCircle(0, 0, mBall.radius, mBallPaint);
        canvas.translate(-mBall.x, -mBall.y);
        //canvas.translate(-mCenterX, -mCenterY);
        // 恢复原始canvas状态
        canvas.restoreToCount(count);
    }

    class Ball {
        float x;
        float y;
        float circleRadius;
        float circleWidth;
        final float radius;
        Ball(float radius, int angle, float circleRadius, float circleWidth) {
            this.circleRadius = circleRadius;
            this.circleWidth = circleWidth;
            this.radius = radius;
            update(angle);
        }
        void update(int _angle) {
            XLog.d(false, 1, "angle: " + _angle);
            // 需要注意角度与弧度的转换
            float angle = (float) (_angle * 2 * Math.PI / 360);
            if (angle < 90) {
                x = (float) (mCenterX + circleRadius * Math.sin(angle) + circleWidth/2);
                y = (float) (mCenterY - circleRadius * Math.cos(angle) + circleWidth/2);
            } else if (angle < 180) {
                x = (float) (mCenterX + circleRadius * Math.sin(angle) + circleWidth/2);
                y = (float) (mCenterY + circleRadius * Math.cos(angle) + circleWidth/2);
            } else if (angle < 270) {
                x = (float) (mCenterX - circleRadius * Math.sin(angle) + circleWidth/2);
                y = (float) (mCenterY + circleRadius * Math.cos(angle) + circleWidth/2);
            } else {
                x = (float) (mCenterX - circleRadius * Math.sin(angle) + circleWidth/2);
                y = (float) (mCenterY - circleRadius * Math.cos(angle) + circleWidth/2);
            }
        }
    }
}
