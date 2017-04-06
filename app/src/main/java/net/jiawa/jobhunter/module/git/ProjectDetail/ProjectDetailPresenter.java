package net.jiawa.jobhunter.module.git.projectdetail;

/**
 * Created by lenovo on 2017/3/25.
 */

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;

import net.jiawa.debughelper.XLog;
import net.jiawa.jobhunter.bean.git.projectdetail.Repository;
import net.jiawa.jobhunter.module.git.GitHubAPI;
import net.jiawa.jobhunter.utils.EncodingUtils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.LinkedList;

import cz.msebera.android.httpclient.Header;

/***
 * ProjectDetail的MVP架构中的P
 */
public class ProjectDetailPresenter implements ProjectDetailContract.Presenter {

    // 执行视图操作的View对象
    private ProjectDetailContract.View mView;
    private TextHttpResponseHandler mHandler;
    // 注意只和接口进行关联
    private ProjectDetailContract.EmptyView mEmptyView;

    public ProjectDetailPresenter(ProjectDetailContract.View view, ProjectDetailContract.EmptyView emptyView) {
        mEmptyView = emptyView;
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
                mEmptyView.showGetDetailFailure();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Repository bean = new Gson().fromJson(responseString, Repository.class);
                if (null != bean) {
                    XLog.d(true, 1, "private: " + bean.getPrivate() + ", " + bean.getId());
                    mView.showGetDetailSuccess(bean);
                    mEmptyView.showGetDetailSuccess();
                }
            }
        };
    }

    @Override
    public void getRepository(String owner, String repoName) {
        GitHubAPI.getRepository(owner, repoName, mHandler);
    }

    @Override
    public void getContents(final String contents_url, String path, final ProjectDetailContract.EmptyView emptyView) {
        GitHubAPI.getContents(contents_url, path, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                XLog.d(true, 3, "failure");
                emptyView.showGetDetailFailure();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                emptyView.showGetDetailSuccess();
                XLog.d(true, 3, responseString);
            }
        });
    }

    @Override
    public void getReadMe() {

    }
}
