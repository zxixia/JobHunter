package net.jiawa.jobhunter.module.git.projectdetail;

import com.google.gson.Gson;
import com.loopj.android.http.TextHttpResponseHandler;

import net.jiawa.debughelper.XLog;
import net.jiawa.jobhunter.bean.git.projectdetail.Repository;
import net.jiawa.jobhunter.module.git.GitHubAPI;

import cz.msebera.android.httpclient.Header;

/**
 * Created by zhaoxin5 on 2017/4/6.
 */

public class ProjectDetailBasicInfoPresenter implements ProjectDetailContract.BasicInfoPresenter {

    private ProjectDetailContract.BasicInfoView mBasicInfoView;

    public ProjectDetailBasicInfoPresenter(ProjectDetailContract.BasicInfoView mBasicInfoView) {
        this.mBasicInfoView = mBasicInfoView;
    }

    @Override
    public void getRepository(String owner, String repoName) {
        GitHubAPI.getRepository(owner, repoName, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                for (int i=0; i<headers.length; i++) {
                    XLog.d(false, 3, headers[i] + "");
                }
                mBasicInfoView.onGetRepositoryFailed();
                XLog.d(false, 3, responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                for (int i=0; i<headers.length; i++) {
                    XLog.d(false, 3, headers[i] + "");
                }

                Repository bean = new Gson().fromJson(responseString, Repository.class);
                if (null != bean) {
                    XLog.d(true, 1, "private: " + bean.getPrivate() + ", " + bean.getId());
                    mBasicInfoView.onGetRepositorySuccess(bean);
                }
            }
        });
    }
}
