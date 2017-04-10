package net.jiawa.jobhunter.module.git.ProjectDetail;

import android.view.View;

import net.jiawa.jobhunter.base.adapter.BaseRecyclerAdapter;
import net.jiawa.jobhunter.base.fragments.BaseRecyclerFragment;
import net.jiawa.jobhunter.bean.git.projectdetail.File;
import net.jiawa.jobhunter.bean.git.projectdetail.Repository;
import net.jiawa.jobhunter.module.git.codedetail.CodeDetailActivity;
import net.jiawa.jobhunter.module.git.ProjectDetail.ProjectDetailContract.CodeTreeView;
import net.jiawa.jobhunter.utils.CodeFileUtil;

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
    protected void onItemClick(File file, int position) {
        if (!file.getType().equals("file")) {
            final String path = file.getPath();
            // 显示google的下拉刷新控件
            mRefreshLayout.setRefreshing(true);
            mPresenter.getCodeTree(path);
        } else {
            // 文件
            if (CodeFileUtil.isCodeTextFile(file.getName())) {
                // 代码文件
                CodeDetailActivity.show(getContext(),
                        (Repository) getArguments().getSerializable("repository"),
                        file.getPath(),
                        file.getName());
            }
        }
    }
}
