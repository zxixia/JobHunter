package net.jiawa.jobhunter.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import net.jiawa.debughelper.XLog;
import net.jiawa.jobhunter.R;

/**
 * Created by zhaoxin5 on 2017/4/18.
 */

/***
 * 显示豆瓣电影星级的View
 */
public class MovieStarView extends View {

    private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
    private static final int COLORDRAWABLE_DIMENSION = 2;

    private Bitmap mStarBitmap = null;
    private Bitmap mCurrentStartBitmap = null;
    private float mWidth;
    private float mHeight;
    // 绘制星星的画笔
    private Paint mPaint;
    // 默认的星星数数目是0
    // 0,5,10,15,20,25,30,35,40,45,50
    private int mStars = 0;
    private float mStarsHeight = 0;

    public MovieStarView(Context context) {
        super(context);
        init(null, 0, 0);
    }

    public MovieStarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MovieStarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setup();
    }

    private void init(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        Context context = getContext();
        if (attrs != null) {
            // Load attributes
            final TypedArray a = context.obtainStyledAttributes(
                    attrs, R.styleable.MovieStar, defStyleAttr, defStyleRes);
            mStars = a.getInteger(R.styleable.MovieStar_MovieStars, 0);
            a.recycle();
        }
    }

    private void setup() {
        // 星级Bitmap
        mStarBitmap = getBitmapFromDrawable(getResources().getDrawable(R.mipmap.ic_rating_m));
        mWidth = getWidth();
        mHeight = getHeight();

        // 单个星级的图片高度
        // 这样获取的高度才是最准确的
        mStarsHeight = (float)mStarBitmap.getHeight() / 11f;
        XLog.d(true, 1, "mStarBitmap.getHeight(): " + mStarBitmap.getHeight() + ", mStarsHeight: " + mStarsHeight);

        updateCurrentStarBitmap();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mStarBitmap == null) {
            setup();
        }
        if (mCurrentStartBitmap == null) {
            updateCurrentStarBitmap();
        }
        canvas.drawBitmap(mCurrentStartBitmap, 0, 0,null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        float d = getResources().getDisplayMetrics().density;
        setMeasuredDimension(measureWidth(widthMeasureSpec, d),
                measureHeight(heightMeasureSpec, d));
    }

    /**
     * Determines the width of this view
     * @param measureSpec A measureSpec packed into an int
     * @return The width of the view, honoring constraints from measureSpec
     */
    private int measureWidth(int measureSpec, float density) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            // 这里针对的就是指定了width和height的情形了
            result = specSize;
        } else {
            // 指定最小宽度
            // 04-18 17:33:02.814 D/xixia-1 ( 9173): [1][DogFood][2       ][  124][measureWidth][net.jiawa.jobhunter.widgets.MovieStarView][density * 50: 150.0, specSize: 1080]
            // 04-18 17:33:02.814 D/xixia-1 ( 9173): [1][DogFood][3       ][  145][measureHeight][net.jiawa.jobhunter.widgets.MovieStarView][density * 15: 45.0, specSize: 1776]
            // 这里传的是父控件的大小
            // 所以要取最小值
            result = (int) Math.min(density * 60, specSize);
        }
        XLog.d(false, 1, "density * 60: " + density * 60 + ", specSize: " + specSize);
        return result;
    }

    /**
     * Determines the height of this view
     * @param measureSpec A measureSpec packed into an int
     * @return The height of the view, honoring constraints from measureSpec
     */
    private int measureHeight(int measureSpec, float density) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            // 这里针对的就是指定了width和height的情形了
            result = specSize;
        } else {
            // 指定最小高度
            // 04-18 17:33:02.814 D/xixia-1 ( 9173): [1][DogFood][2       ][  124][measureWidth][net.jiawa.jobhunter.widgets.MovieStarView][density * 50: 150.0, specSize: 1080]
            // 04-18 17:33:02.814 D/xixia-1 ( 9173): [1][DogFood][3       ][  145][measureHeight][net.jiawa.jobhunter.widgets.MovieStarView][density * 15: 45.0, specSize: 1776]
            // 这里传的是父控件的大小
            // 所以要取最小值
            result = (int) Math.min(density * 12, specSize);
        }
        XLog.d(false, 1, "density * 15: " + density * 15 + ", specSize: " + specSize);
        return result;
    }


    /**
     * 裁剪当前显示的星星,星级的图片
     */
    private void updateCurrentStarBitmap() {
        if (mStarBitmap == null) {
            return;
        }

        float scaleWidth = mWidth / mStarBitmap.getWidth();
        float scaleHeight = mHeight / mStarsHeight;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);// 使用后乘

        final int x = 0;
        int y = (int) (mStarsHeight * (10-mStars/5) + 0.5f);
        final int width = mStarBitmap.getWidth();
        final int height = (int) mStarsHeight;
        // 下面是避免出现越界的情形出现
        if (y + height > mStarBitmap.getHeight()) {
            y = mStarBitmap.getHeight() - height;
        }

        XLog.d(true, 1, "x: " + x + ", y: " + y + ", width: " + width + ", height: " + height);
        mCurrentStartBitmap = Bitmap.createBitmap(mStarBitmap, x, y, width, height, matrix, false);
    }

    public void setStars(int stars) {
        mStars = stars;
        updateCurrentStarBitmap();
        invalidate();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        // 不能有下面的处理
        // 否则在RecyclerView中重用的时候会报错
        /*if (mStarBitmap != null) {
            mStarBitmap.recycle();
            mStarBitmap = null;
        }
        if (mCurrentStartBitmap != null) {
            mCurrentStartBitmap.recycle();
            mCurrentStartBitmap = null;
        }*/
    }

    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        try {
            Bitmap bitmap;

            if (drawable instanceof ColorDrawable) {
                bitmap = Bitmap.createBitmap(COLORDRAWABLE_DIMENSION, COLORDRAWABLE_DIMENSION, BITMAP_CONFIG);
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), BITMAP_CONFIG);
            }

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
