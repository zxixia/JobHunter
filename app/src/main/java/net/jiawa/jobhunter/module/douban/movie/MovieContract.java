package net.jiawa.jobhunter.module.douban.movie;

import net.jiawa.jobhunter.base.mvp.presenter.BasePresenter;
import net.jiawa.jobhunter.base.mvp.view.BaseView;
import net.jiawa.jobhunter.bean.douban.Subject;

/**
 * Created by zhaoxin5 on 2017/4/20.
 */

public class MovieContract {
    interface MoviePresenter extends BasePresenter {
        void getSubject(String id);
    }

    interface BasicInfoView extends BaseView<MoviePresenter> {
        void onGetSubjectSuccessful(Subject subject);
        void onGetSubjectFailed();
    }
}
