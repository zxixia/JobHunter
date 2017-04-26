package net.jiawa.jobhunter.widgets;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import net.jiawa.debughelper.XLog;
import net.jiawa.jobhunter.R;
import net.jiawa.jobhunter.utils.TDevice;
import net.jiawa.jobhunter.utils.UiUtil;

/**
 * Created by JuQiu
 * on 16/9/5.
 */
public class TitleBar extends FrameLayout implements View.OnClickListener {
    private static int EXT_PADDING_TOP;
    private TextView mTitle;
    private ImageView mIcon;
    private ImageView mBack;
    private boolean mConsiderStatusBar = false;


    public TitleBar(Context context) {
        super(context);
        init(null, 0, 0);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs, defStyleAttr, defStyleRes);
    }


    private void init(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        Context context = getContext();

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.lay_title_bar, this, true);

        mTitle = (TextView) findViewById(R.id.tv_title);
        mIcon = (ImageView) findViewById(R.id.iv_icon);
        mBack = (ImageView) findViewById(R.id.iv_back);

        if (attrs != null) {
            // Load attributes
            final TypedArray a = context.obtainStyledAttributes(
                    attrs, R.styleable.TitleBar, defStyleAttr, defStyleRes);

            String title = a.getString(R.styleable.TitleBar_aTitle);
            Drawable drawable = a.getDrawable(R.styleable.TitleBar_aIcon);
            Drawable drawableBack = a.getDrawable(R.styleable.TitleBar_aIconBack);
            boolean considerStatusBar = a.getBoolean(R.styleable.TitleBar_aConsiderStatusBar, false);
            a.recycle();

            mTitle.setText(title);
            mIcon.setImageDrawable(drawable);
            mBack.setImageDrawable(drawableBack);
            mConsiderStatusBar = considerStatusBar;
        } else {
            mIcon.setVisibility(GONE);
            mBack.setVisibility(GONE);
        }

        // Set Background
        setBackgroundColor(getResources().getColor(R.color.jiawa_main));

        // Init padding
        // 这里设置topPadding
        // 让当前的title文字空出上方的statusbar的高度
        if (mConsiderStatusBar) {
            setPadding(getLeft(), getTop() + UiUtil.getStatusBarHeight(getContext()), getRight(), getBottom());
        }

        mBack.setOnClickListener(this);
        mIcon.setOnClickListener(this);

        setAlpha(getAlpha());
    }

    public void setTitleString(String title) {
        if (null == title)
            return;
        mTitle.setText(title);
    }

    public void setTitle(@StringRes int titleRes) {
        if (titleRes <= 0)
            return;
        mTitle.setText(titleRes);
    }

    public void setIconBack(@DrawableRes int iconRes) {
        if (iconRes <= 0) {
            mBack.setVisibility(GONE);
            return;
        }
        mBack.setImageResource(iconRes);
        mBack.setVisibility(VISIBLE);
    }

    public void setIcon(@DrawableRes int iconRes) {
        if (iconRes <= 0) {
            mIcon.setVisibility(GONE);
            return;
        }
        mIcon.setImageResource(iconRes);
        mIcon.setVisibility(VISIBLE);
    }

    OnClickListener mIconClickListener = null;
    OnClickListener mBackClickListener = null;

    public void setIconOnClickListener(OnClickListener listener) {
        mIconClickListener = listener;
    }

    public void setBackOnClickListener(OnClickListener listener) {
        mBackClickListener = listener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        float d = getResources().getDisplayMetrics().density;
        // 48dp是ActionBar的最小高度
        int extHeight = mConsiderStatusBar ? UiUtil.getStatusBarHeight(getContext()) : 0;
        int minH = (int) (d * 48 + extHeight);

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(minH, MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public static int getExtPaddingTop(Resources resources) {
        if (EXT_PADDING_TOP > 0)
            return EXT_PADDING_TOP;

        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            EXT_PADDING_TOP = resources.getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
            return (int) TDevice.dp2px(25, resources);
        }
        return EXT_PADDING_TOP;
    }

    /*@Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        *//**
         * return true, 表示当前View要处理这个touch事件
         * return false, 表示当前View不处理这个touch事件,
         *               交回给父控件重新进行这个touch的分配
         *//*
        // 只有当当前View的Alpha是1f的时候
        // 也就是全部显示的时候,才吃掉这个Touch
        boolean swallowThisTouch = getAlpha() == 1f;
        XLog.d(true, 1, "getAlpha(): " + getAlpha() + ", swallowThisTouch: " + swallowThisTouch);
        return swallowThisTouch;
    }*/

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == mBack.getId() && null != mBackClickListener) {
            mBackClickListener.onClick(v);
        }
        if (id == mIcon.getId() && null != mIconClickListener) {
            mIconClickListener.onClick(v);
        }
    }

    @Override
    public void setAlpha(float alpha) {
        super.setAlpha(alpha);
        if (mIcon == null || mBack == null) return;
        if (alpha > 0.3f) {
            mIcon.setClickable(true);
            mBack.setClickable(true);
        } else {
            mIcon.setClickable(false);
            mBack.setClickable(false);
        }
    }
}
