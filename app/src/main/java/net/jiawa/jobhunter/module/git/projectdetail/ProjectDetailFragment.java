package net.jiawa.jobhunter.module.git.projectdetail;

import android.widget.TextView;

import net.jiawa.jobhunter.R;
import net.jiawa.jobhunter.base.fragments.BaseFragment;
import net.jiawa.jobhunter.bean.git.projectdetail.Repository;

import butterknife.Bind;

/**
 * Created by lenovo on 2017/3/25.
 */

public class ProjectDetailFragment extends BaseFragment implements ProjectDetailContract.View {

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

    public static ProjectDetailFragment newInstance() {
        ProjectDetailFragment fragment = new ProjectDetailFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_project_detail;
    }

    @Override
    public void showGetDetailSuccess(Repository repository) {
        initProject(repository);
    }

    @Override
    public void showGetDetailFailure(String str) {

    }

    @Override
    public void setPresenter(ProjectDetailContract.Presenter presenter) {

    }

    @Override
    public void showNetworkError(int strId) {

    }

    private void initProject(Repository repository) {
        mProjectName.setText(String.valueOf(repository.getName()));
        mLanguage.setText(String.valueOf(repository.getLanguage()));
        mStars.setText(String.valueOf(repository.getStargazersCount()));
        mForks.setText(String.valueOf(repository.getForksCount()));
        mWathers.setText(String.valueOf(repository.getWatchersCount()));
    }
}
