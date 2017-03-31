package net.jiawa.jobhunter.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import net.jiawa.jobhunter.R;

/**
 * Created by lenovo on 2017/3/25.
 */

/**
 * 这个View这样使用
 *
 * Activity以FrameLayout为根
 * 将这个View添加到最顶部
 * 然后这个View会显示一个Loading的界面
 * 提示用户当前状态
 * 当加载完成后,隐藏这个界面
 * 显示用户数据
 */
public class EmptyLayout extends LinearLayout {

    public static final int HIDE_LAYOUT = 4;
    public static final int NETWORK_ERROR = 1;
    public static final int NETWORK_LOADING = 2;
    public static final int NODATA = 3;
    public static final int NODATA_ENABLE_CLICK = 5;
    public static final int NO_LOGIN = 6;

    public EmptyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EmptyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public EmptyLayout(Context context) {
        super(context);
        init();
    }

    TextView mStatus;
    // ProgressBar mLoading;
    // RippleLoadingView mLoading;
    CircleLoadingView mLoading;

    /**
     * 用代码动态创建这个EmptyLayout,
     * 减少依赖文件
     */
    private void init() {

        // 设置当前EmptyLayout的orientation为垂直方向
        this.setOrientation(LinearLayout.VERTICAL);
        // 设置两个子View居中显示
        this.setGravity(Gravity.CENTER);
        // 设置空白页的背景色是白色
        this.setBackgroundColor(getContext().getResources().getColor(R.color.white_ffffff));
        // 让里面的两个控件往上靠一点
        this.setPadding(this.getPaddingLeft(), this.getPaddingTop(), this.getPaddingRight(), 350);

        /*mLoading = new ProgressBar(getContext());
        LinearLayout.LayoutParams lpLoading = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mLoading.setLayoutParams(lpLoading);*/

        mLoading = new CircleLoadingView(getContext());
        LinearLayout.LayoutParams lpLoading = new LinearLayout.LayoutParams(
                200, 200);
        mLoading.setLayoutParams(lpLoading);

        mStatus = new TextView(getContext());
        LinearLayout.LayoutParams lpStatus = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        // 让下面的字离上面的Loading框有一段距离
        lpStatus.topMargin = 30;
        mStatus.setLayoutParams(lpStatus);

        this.addView(mLoading);
        this.addView(mStatus);
    }

    public void setType(int id) {
        // 先显示当前的View
        setVisibility(View.VISIBLE);
        switch (id) {
            case NETWORK_LOADING:
                // do nothing
                mStatus.setText("加载中...");
                break;
            case NETWORK_ERROR:
                // do nothing
                // 一直显示Loading状态
                mStatus.setText("加载中...");
                break;
            case HIDE_LAYOUT:
                // 加载成功
                // 隐藏当前View
                setVisibility(View.GONE);
                break;
        }
    }
}
