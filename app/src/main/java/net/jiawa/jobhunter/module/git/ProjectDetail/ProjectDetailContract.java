package net.jiawa.jobhunter.module.git.projectdetail;

/**
 * Created by xixia on 2017/3/25.
 */

import net.jiawa.jobhunter.base.mvp.presenter.BasePresenter;
import net.jiawa.jobhunter.base.mvp.view.BaseView;
import net.jiawa.jobhunter.bean.git.projectdetail.Repository;

/**
 * 参考OSChina客户端的MVP架构
 * 这个Contract类定义了V和P的接口
 *
 * 1,通常让Activity实现View的接口
 * 2,然后通过View的setPresenter保存Presenter的引用
 *   View可通过这个引用调用Presenter的回调执行一些操作
 * 3,新建一个实现了这里定义的Presenter接口的类作为Presenter,
 *   然后这个Presenter的构造函数中通常传有View的引用
 *   Presenter可以在自身中通过这个引用调用View的回调
 */
public interface ProjectDetailContract {

    interface Presenter extends BasePresenter {
        // 类似：https://api.github.com/repos/zxixia/JobHunter
        void getRepository(String owner, String repoName);

        void getContents(String contents_url, String path);

        void getReadMe();
    }

    interface View extends BaseView<Presenter> {
        void showGetDetailSuccess(Repository repository);

        void showGetDetailFailure(String str);
    }

    // ProjectDetailActivity
    // 继承这个,处理网络请求相关状态
    interface EmptyView extends BaseView<Presenter> {
        void showGetDetailSuccess();
        void showGetDetailFailure();
    }
}