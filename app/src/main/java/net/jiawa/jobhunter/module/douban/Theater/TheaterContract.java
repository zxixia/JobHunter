package net.jiawa.jobhunter.module.douban.theater;

import net.jiawa.jobhunter.base.mvp.presenter.BaseListPresenter;
import net.jiawa.jobhunter.base.mvp.view.BaseListView;
import net.jiawa.jobhunter.base.mvp.view.BaseView;
import net.jiawa.jobhunter.bean.douban.Subjects;

/**
 * Created by zhaoxin5 on 2017/4/17.
 */

public interface TheaterContract {

    /**
     *
     */
    interface BasicInfoView extends BaseView<TheaterPresenter> {
        void onGetTheatersSuccess();
        void onGetTheatersFailed();
    }

    interface TheaterPresenter extends BaseListPresenter {
        void getTheaters();
    }

    interface TheaterView extends BaseListView<TheaterPresenter, Subjects> {

    }
}
