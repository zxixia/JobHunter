package net.jiawa.jobhunter.module.git.codedetail;

import net.jiawa.jobhunter.base.fragments.BaseFragment;
import net.jiawa.jobhunter.bean.git.codedetail.Code;

/**
 * Created by lenovo on 2017/4/9.
 */

public class CodeDetailFragment extends BaseFragment implements CodeDetailContract.View {

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    public void onLoadCodeSuccess(Code code) {

    }

    @Override
    public void onLoadCodeFailed() {

    }

    @Override
    public void setPresenter(CodeDetailContract.Presenter presenter) {

    }

    @Override
    public void showNetworkError(int strId) {}
}
