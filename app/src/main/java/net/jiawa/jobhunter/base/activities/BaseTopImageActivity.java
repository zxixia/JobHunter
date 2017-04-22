package net.jiawa.jobhunter.base.activities;

import android.view.ViewStub;
import android.widget.ImageView;

import net.jiawa.jobhunter.R;
import net.jiawa.jobhunter.widgets.PullNestedScrollView;

import butterknife.Bind;

/**
 * Created by lenovo on 2017/4/22.
 */

public abstract class BaseTopImageActivity extends BaseActivity {

    @Bind(R.id.pnv_scroll_view)
    PullNestedScrollView mPullNestedScrollView;
    @Bind(R.id.iv_header_image)
    ImageView mImageView;

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
        }
        mPullNestedScrollView.setHeader(mImageView);
    }

    protected abstract
    int getChildContentViewId();

    protected ImageView getImageView() {
        return mImageView;
    }
}
