package net.jiawa.jobhunter.module.git.project_detail;

import net.jiawa.jobhunter.base.adapter.BaseRecyclerAdapter;
import net.jiawa.jobhunter.base.fragments.BaseRecyclerFragment;
import net.jiawa.jobhunter.bean.git.projectdetail.File;
import net.jiawa.jobhunter.bean.git.projectdetail.Repository;
import net.jiawa.jobhunter.module.git.project_detail.ProjectDetailContract.CodeTreeView;

/**
 * Created by zhaoxin5 on 2017/4/6.
 */

public class ProjectDetailFragment extends BaseRecyclerFragment<ProjectDetailContract.CodeTreePresenter, File> implements CodeTreeView {

    @Override
    protected BaseRecyclerAdapter getAdapter() {
        return null;
    }

    @Override
    protected void onItemClick(File o, int position) {

    }

    @Override
    public void onGetCodeTreeSuccess() {

    }
}
