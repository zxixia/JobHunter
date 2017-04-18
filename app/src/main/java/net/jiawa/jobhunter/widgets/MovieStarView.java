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

        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mStarsHeight = mStarBitmap.getHeight() / 11 + 0.5f;

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
        canvas.drawBitmap(mCurrentStartBitmap,0,0,null);
    }

    private float getDy(int stars) {
        int temp = stars/5;
        return mStarsHeight * (10-temp) + 0.5f;
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
        mCurrentStartBitmap = Bitmap.createBitmap(mStarBitmap, 0, (int)getDy(mStars), mStarBitmap.getWidth(), (int) mStarsHeight, matrix, false);
    }

    public void setStars(int stars) {
        mStars = stars;
        invalidate();
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
