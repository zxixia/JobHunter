package net.jiawa.jobhunter.module.git.ProjectDetail;

import net.jiawa.debughelper.XLog;
import net.jiawa.jobhunter.R;
import net.jiawa.jobhunter.base.activities.BaseBackActivity;

/**
 * Created by xixia on 2017/3/25.
 */

/**
 * 实现了View的方法,通过View的回调进行视图的更新
 * 这里不需要考虑具体业务逻辑的处理
 */
public class ProjectDetailActivity extends BaseBackActivity implements ProjectDetailContract.View {

    @Override
    protected int getContentView() {
        return R.layout.activity_project_detail;
    }

    // 这是定义在BaseActivity中的方法
    @Override
    protected void initWidget() {
        super.initWidget();
        final ProjectDetailContract.Presenter presenter = new ProjectDetailPresenter(this);
        presenter.getRepository("zxixia", "JobHunter");
        presenter.getContents("https://api.github.com/repos/zxixia/JobHunter/contents/{+path}", null);
        presenter.getContents("https://api.github.com/repos/zxixia/JobHunter/contents/{+path}", "");
    }

    @Override
    public void showGetDetailSuccess(String str) {
        XLog.d(true, 1, str);
    }

    @Override
    public void showGetDetailFailure(String str) {
        XLog.d(true, 1, str);
    }

    @Override
    public void setPresenter(ProjectDetailContract.Presenter presenter) {

    }

    @Override
    public void showNetworkError(int strId) {

    }
}
