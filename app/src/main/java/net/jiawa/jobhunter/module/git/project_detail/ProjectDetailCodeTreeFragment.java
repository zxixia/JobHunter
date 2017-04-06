package net.jiawa.jobhunter.module.git.project_detail;

import android.view.View;

import net.jiawa.jobhunter.base.adapter.BaseRecyclerAdapter;
import net.jiawa.jobhunter.base.fragments.BaseRecyclerFragment;
import net.jiawa.jobhunter.bean.git.projectdetail.File;
import net.jiawa.jobhunter.bean.git.projectdetail.Repository;
import net.jiawa.jobhunter.module.git.project_detail.ProjectDetailContract.CodeTreeView;

/**
 * Created by zhaoxin5 on 2017/4/6.
 */

public class ProjectDetailCodeTreeFragment extends BaseRecyclerFragment<ProjectDetailContract.CodeTreePresenter, File>
        implements CodeTreeView {

    static ProjectDetailCodeTreeFragment newInstance() {
        return new ProjectDetailCodeTreeFragment();
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mRefreshLayout.setEnabled(false);
    }

    @Override
    protected BaseRecyclerAdapter getAdapter() {
        return new ProjectDetaiCodeTreeAdapter(getContext());
    }

    @Override
    protected void onItemClick(File o, int position) {

    }
}
