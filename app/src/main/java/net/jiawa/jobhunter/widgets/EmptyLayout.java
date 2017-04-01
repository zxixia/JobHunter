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
public class EmptyLayout extends LinearLayout implements View.OnClickListener {

    public enum STATUS {
        START,
        STOP,
        ERROR
    }

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

    // 显示错误提示的TextView
    TextView mErrorTips;
    TextView mStatus;
    // ProgressBar mLoading;
    // RippleLoadingView mLoading;
    // CircleLoadingView mLoading;
    ArcLoadingView mLoading;

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

        mErrorTips = new TextView(getContext());
        LinearLayout.LayoutParams lpErrorTips = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mErrorTips.setText("出错啦!");
        mErrorTips.setLayoutParams(lpErrorTips);
        // 启动时先隐藏
        mErrorTips.setVisibility(View.GONE);
        mErrorTips.setTextSize(30);

        /*mLoading = new ProgressBar(getContext());
        LinearLayout.LayoutParams lpLoading = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mLoading.setLayoutParams(lpLoading);*/

        mLoading = new ArcLoadingView(getContext());
        LinearLayout.LayoutParams lpLoading = new LinearLayout.LayoutParams(
                200, 200);
        mLoading.setLayoutParams(lpLoading);

        mStatus = new TextView(getContext());
        LinearLayout.LayoutParams lpStatus = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        // 让下面的字离上面的Loading框有一段距离
        lpStatus.topMargin = 30;
        mStatus.setLayoutParams(lpStatus);

        this.addView(mErrorTips);
        this.addView(mLoading);
        this.addView(mStatus);
        this.setOnClickListener(this);
    }

    public void updateStatus(STATUS status) {
        if (status == STATUS.START)
            throw new RuntimeException("Can not call with the 'STATUS.START' status!");
        updateStatus(status, "");
    }

    public void updateStatus(STATUS status, String text) {
        // 先显示当前的View
        setVisibility(View.VISIBLE);
        boolean isSTART = status == STATUS.START;
        boolean isSTOP = status == STATUS.STOP;
        boolean isERROR = status == STATUS.ERROR;

        if (isSTART) {
            // 禁止响应点击事件
            clickEnable = false;
            mStatus.setVisibility(View.VISIBLE);
            mLoading.setVisibility(View.VISIBLE);
            mErrorTips.setVisibility(View.GONE);
            mStatus.setText(text);
            setVisibility(View.VISIBLE);
        }
        if (isSTOP) {
            // 禁止响应点击事件
            clickEnable = false;
            setVisibility(View.INVISIBLE);
        }
        if (isERROR) {
            // 允许响应点击事件
            clickEnable = true;
            // 隐藏Loading转圈和status
            mStatus.setVisibility(View.GONE);
            mLoading.setVisibility(View.GONE);
            mErrorTips.setVisibility(View.VISIBLE);
        }
    }

    public interface onErrorListener {
        void onError();
    }
    private boolean clickEnable = false;
    private onErrorListener mListener = null;
    public void setOnErrorListener(onErrorListener l) {
        this.mListener = l;
    }
    @Override
    public void onClick(View v) {
        if (clickEnable && null != mListener) {
            mListener.onError();
        }
    }
}
