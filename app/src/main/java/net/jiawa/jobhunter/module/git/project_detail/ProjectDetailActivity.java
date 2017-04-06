package net.jiawa.jobhunter.module.git.project_detail;

import android.widget.TextView;

import net.jiawa.jobhunter.R;
import net.jiawa.jobhunter.base.activities.BaseBackActivity;
import net.jiawa.jobhunter.bean.git.projectdetail.Repository;
import net.jiawa.jobhunter.widgets.EmptyLayout;

import butterknife.Bind;

/**
 * Created by zhaoxin5 on 2017/4/6.
 */

public class ProjectDetailActivity extends BaseBackActivity implements ProjectDetailContract.BasicInfoView {

    @Bind(R.id.tv_projectdetail_name)
    TextView mProjectName;
    @Bind(R.id.tv_projectdetail_language)
    TextView mLanguage;
    @Bind(R.id.tv_projectdetail_stars)
    TextView mStars;
    @Bind(R.id.tv_projectdetail_watchers)
    TextView mWathers;
    @Bind(R.id.tv_projectdetail_forks)
    TextView mForks;
    @Bind(R.id.tv_projectdetail_desc)
    TextView mDescriptions;
    @Bind(R.id.el_projectdetail_loading)
    EmptyLayout mEmptyLayout;

    private ProjectDetailContract.BasicInfoPresenter mPresenter;

    @Override
    protected int getContentView() {
        return R.layout.activity_project_detail_2;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mEmptyLayout.updateStatus(EmptyLayout.STATUS.START, "拼命加载中...");
        mPresenter = new ProjectDetailBasicInfoPresenter(this);
        mPresenter.getRepository("zxixia", "JobHunter");
    }

    @Override
    protected void initWindow() {
        super.initWindow();

    }

    @Override
    public void onGetRepositorySuccess(Repository repository) {
        mEmptyLayout.updateStatus(EmptyLayout.STATUS.STOP);

        mProjectName.setText(String.valueOf(repository.getFullName()));
        mLanguage.setText(String.valueOf(repository.getLanguage()));
        mStars.setText(String.valueOf(repository.getStargazersCount()));
        mForks.setText(String.valueOf(repository.getForksCount()));
        mWathers.setText(String.valueOf(repository.getWatchersCount()));
        mDescriptions.setText(String.valueOf(repository.getDescription()));
    }

    @Override
    public void onGetRepositoryFailed() {
        mEmptyLayout.updateStatus(EmptyLayout.STATUS.ERROR);
    }

    @Override
    public void setPresenter(ProjectDetailContract.BasicInfoPresenter presenter) {}

    @Override
    public void showNetworkError(int strId) {}
}
