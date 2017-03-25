package net.jiawa.jobhunter.module.git.projectdetail;

import net.jiawa.debughelper.XLog;
import net.jiawa.jobhunter.R;
import net.jiawa.jobhunter.base.activities.BaseBackActivity;
import net.jiawa.jobhunter.bean.git.projectdetail.Repository;
import net.jiawa.jobhunter.widgets.EmptyLayout;

import butterknife.Bind;

/**
 * Created by xixia on 2017/3/25.
 */

/**
 * 实现了View的方法,通过View的回调进行视图的更新
 * 这里不需要考虑具体业务逻辑的处理
 */
public class ProjectDetailActivity extends BaseBackActivity implements ProjectDetailContract.EmptyView {

    @Bind(R.id.el_projectdetail_loading)
    EmptyLayout mEmptyLayout;

    @Override
    protected int getContentView() {
        return R.layout.activity_project_detail;
    }

    // 这是定义在BaseActivity中的方法
    @Override
    protected void initWidget() {
        super.initWidget();
        mEmptyLayout.setType(EmptyLayout.NETWORK_LOADING);
        ProjectDetailFragment fragment = ProjectDetailFragment.newInstance();
        final ProjectDetailContract.Presenter presenter = new ProjectDetailPresenter(fragment, this);
        presenter.getRepository("zxixia", "JobHunter");
        // presenter.getContents("https://api.github.com/repos/zxixia/JobHunter/contents/{+path}", null);
        // presenter.getContents("https://api.github.com/repos/zxixia/JobHunter/contents/{+path}", "");
        addFragment(R.id.fl_content, fragment);
    }

    @Override
    public void showGetDetailSuccess() {
        mEmptyLayout.setType(EmptyLayout.HIDE_LAYOUT);
    }

    @Override
    public void showGetDetailFailure() {
        mEmptyLayout.setType(EmptyLayout.NETWORK_ERROR);
    }

    @Override
    public void setPresenter(ProjectDetailContract.Presenter presenter) {

    }

    @Override
    public void showNetworkError(int strId) {

    }
}
