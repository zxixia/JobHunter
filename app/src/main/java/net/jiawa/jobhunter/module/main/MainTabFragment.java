package net.jiawa.jobhunter.module.main;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import net.jiawa.debughelper.XLog;
import net.jiawa.jobhunter.R;
import net.jiawa.jobhunter.base.fragments.BaseTitleFragment;
import net.jiawa.jobhunter.helper.AnimatorListenerHelper;

import butterknife.Bind;

/**
 * Created by zhaoxin5 on 2017/4/11.
 */

public class MainTabFragment extends BaseTitleFragment implements View.OnClickListener {

    @Bind(R.id.tb_main)
    TabLayout mTabLayout;
    @Bind(R.id.iv_arrow_down)
    ImageView mTabAdd;
    @Bind(R.id.view_pager)
    ViewPager mViewPager;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_tab_container;
    }

    @Override
    protected int getTitleRes() {
        return R.string.main_tab_title;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mTabLayout.addTab(mTabLayout.newTab().setText("豆瓣电影"));
        mTabLayout.addTab(mTabLayout.newTab().setText("全国天气"));
        mTabLayout.addTab(mTabLayout.newTab().setText("精品图文"));
        mTabLayout.addTab(mTabLayout.newTab().setText("每日一乐"));
        mTabLayout.addTab(mTabLayout.newTab().setText("精选段子"));
        mTabLayout.addTab(mTabLayout.newTab().setText("搞笑内涵"));

        mTabAdd.setOnClickListener(this);
    }

    ValueAnimator getRotateAnimator(
            boolean open, final View view) {
        if (null == view) return null;
        final ValueAnimator anim = new ValueAnimator();
        final float startDegree = view.getRotation();
        final float endDegree = open ? startDegree + 90+45 : startDegree -90-45;
        // 生成旋转角度
        anim.setFloatValues(startDegree, endDegree);
        anim.setDuration(200);
        anim.setInterpolator(new DecelerateInterpolator());

        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                view.setRotation(value);
            }
        });

        anim.addListener(new AnimatorListenerHelper() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setRotation(endDegree);
                isTabManagerClosed = !isTabManagerClosed;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                view.setRotation(endDegree);
                isTabManagerClosed = !isTabManagerClosed;
            }
        });
        return anim;
    }
    private AnimatorSet mAnimatorSet = new AnimatorSet();
    private boolean isTabManagerClosed = true;
    private void onTabAddClick() {
        if (null != mAnimatorSet && mAnimatorSet.isRunning()) {
            return;
        }
        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.play(getRotateAnimator(isTabManagerClosed, mTabAdd));
        mAnimatorSet.start();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mTabAdd.getId()) {
            onTabAddClick();
        }
    }
}
