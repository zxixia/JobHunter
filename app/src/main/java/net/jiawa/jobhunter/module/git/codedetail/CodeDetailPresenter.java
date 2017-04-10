package net.jiawa.jobhunter.module.git.codedetail;

import com.google.gson.Gson;
import com.loopj.android.http.TextHttpResponseHandler;

import net.jiawa.jobhunter.bean.git.codedetail.Code;
import net.jiawa.jobhunter.bean.git.projectdetail.Repository;
import net.jiawa.jobhunter.module.git.GitHubAPI;

import cz.msebera.android.httpclient.Header;

/**
 * Created by zhaoxin5 on 2017/4/10.
 */

public class CodeDetailPresenter implements CodeDetailContract.Presenter {

    CodeDetailContract.EmptyView mEmptyView;
    CodeDetailContract.View mView;
    Repository mRepository;

    public CodeDetailPresenter(CodeDetailContract.EmptyView emptyView, CodeDetailContract.View view, Repository repository) {
        mEmptyView = emptyView;
        mView = view;
        mRepository = repository;

        mEmptyView.setPresenter(this);
        mView.setPresenter(this);
    }

    @Override
    public void getCode(String path) {
        GitHubAPI.getCode(mRepository.getContentsUrl(), path, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                mEmptyView.showError();
                mView.onLoadCodeFailed();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Code code = new Gson().fromJson(responseString, Code.class);
                mEmptyView.hideLoading();
                mView.onLoadCodeSuccess(code);
            }
        });
    }
}
