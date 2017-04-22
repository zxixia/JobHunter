package net.jiawa.jobhunter.module.douban;

import android.widget.ImageView;

import net.jiawa.jobhunter.R;
import net.jiawa.jobhunter.base.activities.BaseActivity;
import net.jiawa.jobhunter.widgets.PullNestedScrollView;

import butterknife.Bind;

/**
 * Created by zhaoxin5 on 2017/4/21.
 */

public class TopImageActivity extends BaseActivity {

    @Bind(R.id.pnv_scroll_view)
    PullNestedScrollView mPullNestedScrollView;
    @Bind(R.id.iv_header_image)
    ImageView mImageView;

    @Override
    protected int getContentView() {
        return R.layout.activity_top_image;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mPullNestedScrollView.setHeader(mImageView);
    }

    @Override
    protected void initWindow() {
        super.initWindow();
        setFullScreen(true);
    }
}
