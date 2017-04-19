package net.jiawa.jobhunter.module.douban.theater;

import net.jiawa.jobhunter.R;
import net.jiawa.jobhunter.base.activities.BaseBackActivity;
import net.jiawa.jobhunter.widgets.EmptyLayout;

import butterknife.Bind;

/**
 * Created by zhaoxin5 on 2017/4/17.
 */

public class TheaterActivity extends BaseBackActivity implements TheaterContract.BasicInfoView {

    TheaterContract.TheaterPresenter mPresenter;

    @Bind(R.id.el_loading)
    EmptyLayout mEmptyLayout;

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        mEmptyLayout.updateStatus(EmptyLayout.STATUS.START, "拼命加载中...");
        mEmptyLayout.setOnErrorListener(new EmptyLayout.onErrorListener() {
            @Override
            public void onError() {
                mPresenter.getTheaters();
            }
        });

        TheaterFragment fragment = TheaterFragment.newInstance();
        addFragment(R.id.fl_content, fragment);
        TheaterContract.TheaterPresenter presenter = new TheaterPresenter(this, fragment);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_douban_theaters;
    }

    @Override
    public void onGetTheatersSuccess() {
        mEmptyLayout.updateStatus(EmptyLayout.STATUS.STOP);
    }

    @Override
    public void onGetTheatersFailed() {
        mEmptyLayout.updateStatus(EmptyLayout.STATUS.ERROR);
    }

    @Override
    public void setPresenter(TheaterContract.TheaterPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showNetworkError(int strId) {}
}
