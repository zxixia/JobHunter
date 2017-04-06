package net.jiawa.jobhunter.module.navigationbar;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import net.jiawa.debughelper.XLog;
import net.jiawa.jobhunter.R;
import net.jiawa.jobhunter.base.fragments.BaseFragment;
import net.jiawa.jobhunter.helper.AnimatorListenerHelper;
import net.jiawa.jobhunter.module.git.project_detail.ProjectDetailActivity;
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
        doReSelectAnimate();
    }

    private void doTabChanged(NavigationButton oldNavButton, NavigationButton newNavButton) {
        if (newNavButton.getId() == R.id.tv_navigationbar_item_about) {
            startActivity(ProjectDetailActivity.class);
        }
    }

    private void doSelect(NavigationButton newNavButton) {
        if (null != mAnimatorSet && isAnimating) {
            XLog.d(false, 2);
            return;
        }
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
        doSelectAnimate();
    }

    private void updateAnimating(boolean animating) {
        synchronized (mAnimatingObject) {
            isAnimating = animating;
        }
    }
    private boolean isAnimating = false;
    private Object mAnimatingObject = new Object();
    private AnimatorListenerHelper mAnimatorListenerHelper = new AnimatorListenerHelper() {
        @Override
        public void onAnimationCancel(Animator animation) {
            super.onAnimationCancel(animation);
            updateAnimating(false);
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            updateAnimating(false);
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
            super.onAnimationRepeat(animation);
            updateAnimating(true);
        }

        @Override
        public void onAnimationStart(Animator animation) {
            super.onAnimationStart(animation);
            updateAnimating(true);
        }
    };

    private AnimatorSet mAnimatorSet = new AnimatorSet();
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
    private void doSelectAnimate() {
        if (null == mCurrentNavButton) return;
        if (getChildCount() < 0) return;
        int currentIndex = indexOf(mCurrentNavButton);
        if (currentIndex < 0) return;

        /**
         * 每次都需要new一个AnimatorSet,否则动画会有卡顿
         */
        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.addListener(mAnimatorListenerHelper);

        for (int i=0; i<getChildCount(); i++) {
            // if (i == currentIndex) continue;
            View view = getChildAt(i);
            if (null == view) continue;
            int distance = Math.abs(i - currentIndex);
            XLog.d(false, 1, "i: " + i + ", distance: " + distance + ", currentIndex: " + currentIndex);
            mAnimatorSet.play(getNavigationItemClickAnimator(distance, view, 100));
        }
        mAnimatorSet.start();
    }

    private void doReSelectAnimate() {
        if (null == mCurrentNavButton) return;
        /**
         * 每次都需要new一个AnimatorSet,否则动画会有卡顿
         */
        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.addListener(mAnimatorListenerHelper);
        mAnimatorSet.play(getNavigationItemClickAnimator(0, mCurrentNavButton, 100));
        mAnimatorSet.start();
    }

    ValueAnimator getNavigationItemClickAnimator(int distance, final View view, final float animateMove) {
        if (distance < 0) return null;
        final ValueAnimator anim = new ValueAnimator();
        // 这样能生成一条从0到1,然后在从1到0的序列
        // 然后在onAnimationUpdate函数中执行Y轴方向的
        // 变化,即可完成动画
        anim.setFloatValues(0.0f, 1.0f, 0.0f);
        anim.setDuration(700);
        anim.setInterpolator(new DecelerateInterpolator());
        anim.setStartDelay(distance * 200);

        final float oriY = view.getY();
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                view.setY(oriY + value * animateMove);
            }
        });

        anim.addListener(new AnimatorListenerHelper() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                XLog.d(false, 1, "onAnimationEnd");
                view.setY(oriY);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                view.setY(oriY);
            }
        });
        return anim;
    }
}
