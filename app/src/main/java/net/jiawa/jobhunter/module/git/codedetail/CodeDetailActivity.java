package net.jiawa.jobhunter.module.git.codedetail;

import android.content.Context;
import android.content.Intent;

import net.jiawa.jobhunter.R;
import net.jiawa.jobhunter.base.activities.BaseBackActivity;
import net.jiawa.jobhunter.bean.git.projectdetail.Repository;
import net.jiawa.jobhunter.widgets.EmptyLayout;

import butterknife.Bind;

/**
 * Created by lenovo on 2017/4/9.
 */

public class CodeDetailActivity extends BaseBackActivity implements CodeDetailContract.EmptyView {

    @Bind(R.id.el_codedetail_loading)
    EmptyLayout mLoading;

    @Override
    protected int getContentView() {
        return R.layout.activity_code_detail;
    }

    // 提供给外界直接调用的静态方法
    public static void show(Context context, Repository repository, String path, String branch) {
        Intent intent = new Intent(context, CodeDetailActivity.class);
        // Log.e("path", path);
        intent.putExtra("path", path);
        intent.putExtra("branch", branch);
        intent.putExtra("repository", repository);
        context.startActivity(intent);
    }

    // 重写父类方法
    @Override
    protected void initWidget() {
        super.initWidget();
        Intent intent = getIntent();

    }

    @Override
    public void showError() {
        mLoading.updateStatus(EmptyLayout.STATUS.ERROR);
    }

    @Override
    public void hideLoading() {
        mLoading.updateStatus(EmptyLayout.STATUS.STOP);
    }

    @Override
    public void setPresenter(CodeDetailContract.Presenter presenter) {}

    @Override
    public void showNetworkError(int strId) {}
}
