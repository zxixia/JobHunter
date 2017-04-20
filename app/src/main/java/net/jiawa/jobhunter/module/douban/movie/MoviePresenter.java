package net.jiawa.jobhunter.module.douban.movie;

import com.google.gson.Gson;
import com.loopj.android.http.TextHttpResponseHandler;

import net.jiawa.debughelper.XLog;
import net.jiawa.jobhunter.bean.douban.Subject;
import net.jiawa.jobhunter.bean.douban.Subjects;
import net.jiawa.jobhunter.module.douban.DouBanAPI;

import cz.msebera.android.httpclient.Header;

/**
 * Created by zhaoxin5 on 2017/4/20.
 */

public class MoviePresenter implements MovieContract.MoviePresenter {

    Subjects mSubjects;
    Subject mSubject;
    MovieContract.BasicInfoView mBasicInfoView;

    public MoviePresenter(MovieContract.BasicInfoView basicInfoView, Subjects subjects) {
        mSubjects = subjects;
        mBasicInfoView = basicInfoView;

        mBasicInfoView.setPresenter(this);
    }

    @Override
    public void getSubject(String id) {
        DouBanAPI.getSubject(id, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                mBasicInfoView.onGetSubjectFailed();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                mSubject = new Gson().fromJson(responseString, Subject.class);
                XLog.d(true, 1, mSubject.getDoubanSite());
                mBasicInfoView.onGetSubjectSuccessful(mSubject);
            }
        });
    }
}
