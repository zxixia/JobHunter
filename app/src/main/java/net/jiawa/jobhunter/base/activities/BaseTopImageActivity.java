package net.jiawa.jobhunter.base.activities;

import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;

import net.jiawa.debughelper.XLog;
import net.jiawa.jobhunter.R;
import net.jiawa.jobhunter.widgets.PullNestedScrollView;
import net.jiawa.jobhunter.widgets.TitleBar;

import butterknife.Bind;

/**
 * Created by lenovo on 2017/4/22.
 */

public abstract class BaseTopImageActivity extends BaseActivity implements
        NestedScrollView.OnScrollChangeListener {

    @Bind(R.id.pnv_scroll_view)
    PullNestedScrollView mPullNestedScrollView;
    @Bind(R.id.iv_header_image)
    ImageView mImageView;
    @Bind(R.id.nav_title_bar)
    TitleBar mTitleBar;

    @Override
    protected int getContentView() {
        return R.layout.activity_base_top_image;
    }

    @Override
    protected void initWindow() {
        super.initWindow();
        // 全屏显示
        setFullScreen(true);
        // on before initWindow call
        ViewStub stub = (ViewStub) findViewById(R.id.lay_content);
        stub.setLayoutResource(getChildContentViewId());
        stub.inflate();
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        if (null == mPullNestedScrollView) {
            mPullNestedScrollView = (PullNestedScrollView) findViewById(R.id.pnv_scroll_view);
            mImageView = (ImageView) findViewById(R.id.iv_header_image);
            mTitleBar = (TitleBar) findViewById(R.id.nav_title_bar);
        }
        mPullNestedScrollView.setHeader(mImageView);
        mPullNestedScrollView.setOnScrollChangeListener(this);
        mTitleBar.setClickable(false);
        mTitleBar.setAlpha(0f);
        mTitleBar.setBackOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XLog.d(true, 1, "mBackClickListener");
                finish();
            }
        });
        mTitleBar.setIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XLog.d(true, 1, "mIconClickListener");
            }
        });

        // 隐藏Icon
        mTitleBar.setIcon(-1);
    }

    protected abstract
    int getChildContentViewId();

    protected ImageView getImageView() {
        return mImageView;
    }

    public interface TitleBarOnScroll {
        float onScroll(int scrollY);
    }

    BaseTitleActivity.TitleBarOnScroll mTitleBarOnScroll = new BaseTitleActivity.TitleBarOnScroll() {
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

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        mTitleBar.setAlpha(mTitleBarOnScroll.onScroll(scrollY));
        if (mTitleBar.getAlpha() == 1f) {
            mTitleBar.setClickable(true);
        } else {
            mTitleBar.setClickable(false);
        }
    }

    protected  void setTitle(String str) {
        mTitleBar.setTitleString(str);
    }
}
