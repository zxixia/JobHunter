package net.jiawa.jobhunter.module.git.codedetail;

import net.jiawa.jobhunter.base.mvp.presenter.BasePresenter;
import net.jiawa.jobhunter.base.mvp.view.BaseView;
import net.jiawa.jobhunter.bean.git.codedetail.Code;

/**
 * Created by lenovo on 2017/4/9.
 */

public class CodeDetailContract {

    interface EmptyView extends BaseView<Presenter> {
        void showError();
        void hideLoading();
    }

    interface View extends BaseView<Presenter> {
        void onLoadCodeSuccess(Code code);
        void onLoadCodeFailed();
    }

    interface Presenter extends BasePresenter {
        void getCode(String path);
    }
}
