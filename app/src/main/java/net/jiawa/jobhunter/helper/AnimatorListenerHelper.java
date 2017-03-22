package net.jiawa.jobhunter.helper;

import android.animation.Animator;

/**
 * Created by xixia on 2017/3/22.
 *
 * 这个类实现了Animator.AnimatorListener全部方法
 * 然后其他类使用这个类,可以有选择性的实现其中的某些方法
 */
public class AnimatorListenerHelper implements Animator.AnimatorListener {
    @Override
    public void onAnimationStart(Animator animation) {}

    @Override
    public void onAnimationEnd(Animator animation) {}

    @Override
    public void onAnimationCancel(Animator animation) {}

    @Override
    public void onAnimationRepeat(Animator animation) {}
}
