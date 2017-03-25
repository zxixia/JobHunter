package net.jiawa.jobhunter.module.git.ProjectDetail;

/**
 * Created by lenovo on 2017/3/25.
 */

import com.loopj.android.http.TextHttpResponseHandler;

import net.jiawa.debughelper.XLog;
import net.jiawa.jobhunter.module.git.GitHubAPI;

import cz.msebera.android.httpclient.Header;

/***
 * ProjectDetail的MVP架构中的P
 */
public class ProjectDetailPresenter implements ProjectDetailContract.Presenter {

    // 执行视图操作的View对象
    private ProjectDetailContract.View mView;
    private TextHttpResponseHandler mHandler;

    public ProjectDetailPresenter(ProjectDetailContract.View view) {
        mView = view;
        initHandler();
        mView.setPresenter(this);
    }

    // 异步监听Http Response返回的结果
    private void initHandler() {
        mHandler = new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                XLog.d(true, 1, statusCode + ", " + headers + ", " + responseString + ", " + throwable);
                mView.showGetDetailFailure("请求数据失败");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                mView.showGetDetailSuccess(responseString);
            }
        };
    }

    @Override
    public void getRepository(String owner, String repoName) {
        GitHubAPI.getRepository(owner, repoName, mHandler);
    }

    @Override
    public void getContents(String contents_url, String path) {
        GitHubAPI.getContents(contents_url, path, mHandler);
    }
}
