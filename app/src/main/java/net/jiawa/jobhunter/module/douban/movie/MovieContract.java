package net.jiawa.jobhunter.module.douban.movie;

import net.jiawa.jobhunter.base.mvp.presenter.BaseListPresenter;
import net.jiawa.jobhunter.base.mvp.presenter.BasePresenter;
import net.jiawa.jobhunter.base.mvp.view.BaseListView;
import net.jiawa.jobhunter.base.mvp.view.BaseView;
import net.jiawa.jobhunter.bean.douban.Casts;
import net.jiawa.jobhunter.bean.douban.Directors;
import net.jiawa.jobhunter.bean.douban.Subject;

import java.util.List;

/**
 * Created by zhaoxin5 on 2017/4/20.
 */

public class MovieContract {
    interface MoviePresenter extends BasePresenter {
        void getSubject(String id);
        void getCastsList(List<Casts> casts, List<Directors> directorses);
    }

    interface BasicInfoView extends BaseView<MoviePresenter> {
        void onGetSubjectSuccessful(Subject subject);
        void onGetSubjectFailed();
        void onGetCasts(List<Object> list);
    }

    interface CastsPresenter extends BaseListPresenter {
        void getCastsList(List<Casts> casts, List<Directors> directorses);
    }

    interface CastsView extends BaseListView<CastsPresenter, Casts> {
        void onGetCasts(List<Object> list);
    }
}
