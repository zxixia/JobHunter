package net.jiawa.jobhunter.module.git.projectdetail;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;

import net.jiawa.jobhunter.bean.git.projectdetail.File;
import net.jiawa.jobhunter.bean.git.projectdetail.Repository;
import net.jiawa.jobhunter.module.git.GitHubAPI;

import java.lang.reflect.Type;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by zhaoxin5 on 2017/4/6.
 */

public class ProjectDetailCodeTreePresenter implements ProjectDetailContract.CodeTreePresenter {

    final ProjectDetailContract.CodeTreeView mView;
    final Repository mRepository;

    public ProjectDetailCodeTreePresenter(ProjectDetailContract.CodeTreeView view, Repository repository) {
        this.mView = view;
        this.mRepository = repository;
        mView.setPresenter(this);
    }

    @Override
    public void getCodeTree(String path) {
        GitHubAPI.getContents(mRepository.getContentsUrl(), path, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Type listType = new TypeToken<List<File>>(){}.getType();
                List<File> files = new Gson().fromJson(responseString, listType);
                mView.onRefreshSuccess(files);
            }
        });
    }

    @Override
    public void onRefreshing() {
        // 第一次是在BaseRecyclerView的initData中调用的
        getCodeTree(null);
    }

    @Override
    public void onLoadMore() {}
}
