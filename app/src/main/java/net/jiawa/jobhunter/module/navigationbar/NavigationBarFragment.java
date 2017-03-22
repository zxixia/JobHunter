package net.jiawa.jobhunter.module.navigationbar;
import android.view.View;

import net.jiawa.debughelper.XLog;
import net.jiawa.jobhunter.R;
import net.jiawa.jobhunter.base.fragments.BaseFragment;
import net.jiawa.jobhunter.widgets.NavigationButton;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by xixia on 2017/3/22.
 */

public class NavigationBarFragment extends BaseFragment implements View.OnClickListener {

    @Bind(R.id.tv_navigationbar_item_job)
    public NavigationButton mItemJob;
    @Bind(R.id.tv_navigationbar_item_coder)
    public NavigationButton mItemCoder;
    @Bind(R.id.tv_navigationbar_item_news)
    public NavigationButton mItemNews;
    @Bind(R.id.tv_navigationbar_item_about)
    public NavigationButton mItemAbout;

    private NavigationButton mCurrentNavButton = null;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_navigationbar;
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        XLog.d(false, 1);

        mItemJob.init(R.drawable.tab_icon_explore);
        mItemCoder.init(R.drawable.tab_icon_tweet);
        mItemNews.init(R.drawable.tab_icon_new);
        mItemAbout.init(R.drawable.tab_icon_me);

        // set default select item
        doSelect(mItemJob);
    }

    /**
     * Use butterknife to bind click events
     * @param v
     */
    @OnClick({R.id.tv_navigationbar_item_job, R.id.tv_navigationbar_item_coder,
            R.id.tv_navigationbar_item_news, R.id.tv_navigationbar_item_about})
    @Override
    public void onClick(View v) {
        if (v instanceof NavigationButton) {
            NavigationButton nav = (NavigationButton) v;
            doSelect(nav);
        }
    }

    private void onReselect(NavigationButton reselectNavButton) {

    }

    private void doTabChanged(NavigationButton oldNavButton, NavigationButton newNavButton) {

    }

    private void doSelect(NavigationButton newNavButton) {
        NavigationButton oldNavButton = null;
        if (mCurrentNavButton != null) {
            oldNavButton = mCurrentNavButton;
            if (oldNavButton == newNavButton) {
                onReselect(oldNavButton);
                return;
            }
            oldNavButton.setSelected(false);
        }
        newNavButton.setSelected(true);
        doTabChanged(oldNavButton, newNavButton);
        mCurrentNavButton = newNavButton;
    }
}
