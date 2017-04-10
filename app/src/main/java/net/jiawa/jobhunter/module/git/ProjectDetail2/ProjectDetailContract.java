package net.jiawa.jobhunter.module.git.ProjectDetail2;

import net.jiawa.jobhunter.base.mvp.presenter.BaseListPresenter;
import net.jiawa.jobhunter.base.mvp.presenter.BasePresenter;
import net.jiawa.jobhunter.base.mvp.view.BaseListView;
import net.jiawa.jobhunter.base.mvp.view.BaseView;
import net.jiawa.jobhunter.bean.git.projectdetail.File;
import net.jiawa.jobhunter.bean.git.projectdetail.Repository;

/**
 * Created by zhaoxin5 on 2017/4/6.
 */

public interface ProjectDetailContract {

    interface BasicInfoPresenter extends BasePresenter {
        void getRepository(String owner, String repoName);
    }

    interface BasicInfoView extends BaseView<BasicInfoPresenter> {
        void onGetRepositorySuccess(Repository repository);
        void onGetRepositoryFailed();
    }

    // Presenter to
    interface CodeTreePresenter extends BaseListPresenter {
        void getCodeTree(String path);
        // 能否响应back键事件
        boolean canBack();
        // 响应back事件,
        // 回退到上一级目录
        void onBack();
    }

    interface CodeTreeView extends BaseListView<CodeTreePresenter, File> {
        /**
         * 这里无需实现是么回调
         * 主要的刷新都在BaseListView里面了，
         */
    }
}
