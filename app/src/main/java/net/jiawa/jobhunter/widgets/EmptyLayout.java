package net.jiawa.jobhunter.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import net.jiawa.jobhunter.R;
import net.jiawa.jobhunter.utils.ViewUtils;

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
public class EmptyLayout extends FrameLayout {

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
    ProgressBar mLoading;
    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_empty_layout, this);
        mStatus = ViewUtils.findViewById(view, R.id.tv_emptylayout_status);
        mLoading = ViewUtils.findViewById(view, R.id.pb_emptylayout_loading);
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
