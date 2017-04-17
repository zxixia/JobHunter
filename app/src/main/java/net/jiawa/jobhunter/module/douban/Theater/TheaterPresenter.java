package net.jiawa.jobhunter.module.douban.Theater;

import com.google.gson.Gson;
import com.loopj.android.http.TextHttpResponseHandler;

import net.jiawa.jobhunter.bean.douban.Theaters;
import net.jiawa.jobhunter.module.douban.DouBanAPI;

import cz.msebera.android.httpclient.Header;

/**
 * Created by zhaoxin5 on 2017/4/17.
 */

public class TheaterPresenter implements TheaterContract.TheaterPresenter {
    TheaterContract.BasicInfoView mEmptyView;
    TheaterContract.TheaterView mView;

    public TheaterPresenter(TheaterContract.BasicInfoView basicInfoView, TheaterContract.TheaterView douBanTheaterView) {
        mEmptyView = basicInfoView;
        mView = douBanTheaterView;

        mEmptyView.setPresenter(this);
        mView.setPresenter(this);
    }

    @Override
    public void getTheaters() {
        DouBanAPI.getTheaters(new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                mEmptyView.onGetTheatersFailed();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                mEmptyView.onGetTheatersSuccess();
                Theaters theaters = new Gson().fromJson(responseString, Theaters.class);
                if (null != theaters.getSubjects()) {
                    mView.onRefreshSuccess(theaters.getSubjects());
                    mView.onComplete();
                }
            }
        });
    }

    @Override
    public void onRefreshing() {
        getTheaters();
    }

    @Override
    public void onLoadMore() {}
}
