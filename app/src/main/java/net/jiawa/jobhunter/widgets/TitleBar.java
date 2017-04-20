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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import net.jiawa.jobhunter.R;
import net.jiawa.jobhunter.utils.TDevice;
import net.jiawa.jobhunter.utils.UiUtil;

/**
 * Created by JuQiu
 * on 16/9/5.
 */
public class TitleBar extends FrameLayout {
    private static int EXT_PADDING_TOP;
    private TextView mTitle;
    private ImageView mIcon;
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


        if (attrs != null) {
            // Load attributes
            final TypedArray a = context.obtainStyledAttributes(
                    attrs, R.styleable.TitleBar, defStyleAttr, defStyleRes);

            String title = a.getString(R.styleable.TitleBar_aTitle);
            Drawable drawable = a.getDrawable(R.styleable.TitleBar_aIcon);
            boolean considerStatusBar = a.getBoolean(R.styleable.TitleBar_aConsiderStatusBar, false);
            a.recycle();

            mTitle.setText(title);
            mIcon.setImageDrawable(drawable);
            mConsiderStatusBar = considerStatusBar;
        } else {
            mIcon.setVisibility(GONE);
        }

        // Set Background
        setBackgroundColor(getResources().getColor(R.color.jiawa_main));

        // Init padding
        // 这里设置topPadding
        // 让当前的title文字空出上方的statusbar的高度
        if (mConsiderStatusBar) {
            setPadding(getLeft(), getTop() + UiUtil.getStatusBarHeight(getContext()), getRight(), getBottom());
        }
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

    public void setIcon(@DrawableRes int iconRes) {
        if (iconRes <= 0) {
            mIcon.setVisibility(GONE);
            return;
        }
        mIcon.setImageResource(iconRes);
        mIcon.setVisibility(VISIBLE);
    }

    public void setIconOnClickListener(OnClickListener listener) {
        mIcon.setOnClickListener(listener);
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
}
