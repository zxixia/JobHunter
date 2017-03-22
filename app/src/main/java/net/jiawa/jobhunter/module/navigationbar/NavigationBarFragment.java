package net.jiawa.jobhunter.module.navigationbar;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

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
        animateSwitch();
    }


    private AnimatorSet mAnimatorSet = null;
    /***
     * 底部导航切换以后的动画,动画示意:
     * touch                    touch
     *   |                        |
     *   *        *               *
     *   *     *  *            *  *  *
     *   *  *  *  *            *  *  *  *
     *
     *   就是以当前点击的item为中心,
     *   向两边扩散,其他点先降落然后再升起
     */
    private void animateSwitch() {
        if (null == mCurrentNavButton) return;
        if (getChildCount() < 0) return;
        int currentIndex = findPosition(mCurrentNavButton);
        if (currentIndex < 0) return;

        int longDistance = -1;
        // 保存每个View与当前点击的View的距离
        int[] animateViewDistance = new int[getChildCount()];
        for (int i=0; i<getChildCount(); i++) {
            animateViewDistance[i] = Math.abs(i - currentIndex);
            if (longDistance < animateViewDistance[i]) {
                longDistance = animateViewDistance[i];
            }
        }
        if (longDistance < 0) return;

        ValueAnimator anim = new ValueAnimator();
        anim.setIntValues(0, 10);
        anim.setDuration(200);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int now = (int) animation.getAnimatedValue();
                XLog.d(true, 1, "now: " + now);
            }
        });
        anim.start();

        //初始化
        Animation translateAnimation = new TranslateAnimation(0.0f, 0.0f,0.0f,10.0f);
        translateAnimation.setFillAfter(true);
        //设置动画时间                translateAnimation.setDuration(1000);
        mCurrentNavButton.startAnimation(translateAnimation);
    }
}
