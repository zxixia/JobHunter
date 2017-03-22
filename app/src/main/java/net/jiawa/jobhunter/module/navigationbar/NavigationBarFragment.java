package net.jiawa.jobhunter.module.navigationbar;

import android.view.View;
import android.widget.TextView;

import net.jiawa.jobhunter.R;
import net.jiawa.jobhunter.base.fragments.BaseFragment;
import net.jiawa.jobhunter.widgets.NavigationButton;

import butterknife.Bind;

/**
 * Created by xixia on 2017/3/22.
 */

public class NavigationBarFragment extends BaseFragment{

    @Bind(R.id.tv_navigationbar_item_job)
    public NavigationButton mItemJob;
    @Bind(R.id.tv_navigationbar_item_coder)
    public NavigationButton mItemCoder;
    @Bind(R.id.tv_navigationbar_item_news)
    public NavigationButton mItemNews;
    @Bind(R.id.tv_navigationbar_item_about)
    public NavigationButton mItemAbout;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_navigationbar;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);

        mItemJob.init(R.drawable.tab_icon_explore);
        mItemCoder.init(R.drawable.tab_icon_tweet);
        mItemNews.init(R.drawable.tab_icon_new);
        mItemAbout.init(R.drawable.tab_icon_me);
    }
}
