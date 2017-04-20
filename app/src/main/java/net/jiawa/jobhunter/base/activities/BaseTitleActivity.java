package net.jiawa.jobhunter.base.activities;

import android.support.v4.widget.NestedScrollView;
import android.view.ViewStub;

import net.jiawa.jobhunter.R;
import net.jiawa.jobhunter.widgets.TitleBar;

import butterknife.Bind;

/**
 * Created by zhaoxin5 on 2017/4/20.
 */

public abstract class BaseTitleActivity extends BaseActivity implements
        NestedScrollView.OnScrollChangeListener {

    @Bind(R.id.nav_title_bar)
    TitleBar mTitleBar;
    @Bind(R.id.nsv_root)
    NestedScrollView mScroll;

    @Override
    protected int getContentView() {
        return R.layout.activity_base_title;
    }

    @Override
    protected void initWindow() {
        super.initWindow();
        setFullScreen(false);
        // on before initWindow call
        ViewStub stub = (ViewStub) findViewById(R.id.lay_content);
        stub.setLayoutResource(getChildContentViewId());
        stub.inflate();
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mScroll.setOnScrollChangeListener(this);

        // 隐藏图标
        mTitleBar.setIcon(0);
    }

    protected abstract
    int getChildContentViewId();

    protected void setTitle(String title) {
        mTitleBar.setTitleString(title);
    }

    public interface OnScroll {
        float onScroll(int scrollY);
    }

    OnScroll mOnScroll = new OnScroll() {
        @Override
        public float onScroll(int scrollY) {
            if (scrollY < 1) {
                return 0f;
            }
            if (scrollY > 500) {
                return 1f;
            }
            return ((float) scrollY) / 500f;
        }
    };
    protected void setOnScroll(OnScroll onScroll) {
        mOnScroll = onScroll;
    }

    NestedScrollView.OnScrollChangeListener mOnScrollChangeListener;
    protected void setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener l) {
        mOnScrollChangeListener = l;
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

        mTitleBar.setAlpha(mOnScroll.onScroll(scrollY));

        if (null != mOnScrollChangeListener) {
            mOnScrollChangeListener.onScrollChange(v, scrollX, scrollY, oldScrollX, oldScrollY);
        }
    }
}
