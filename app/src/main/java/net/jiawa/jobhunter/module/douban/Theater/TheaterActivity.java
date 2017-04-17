package net.jiawa.jobhunter.module.douban.Theater;

import net.jiawa.jobhunter.R;
import net.jiawa.jobhunter.base.activities.BaseBackActivity;

/**
 * Created by zhaoxin5 on 2017/4/17.
 */

public class TheaterActivity extends BaseBackActivity implements TheaterContract.BasicInfoView {

    TheaterContract.TheaterPresenter mPresenter;

    @Override
    protected void initData() {
        super.initData();


    }

    @Override
    protected int getContentView() {
        return R.layout.activity_douban_theaters;
    }

    @Override
    public void onGetTheatersSuccess() {

    }

    @Override
    public void onGetTheatersFailed() {

    }

    @Override
    public void setPresenter(TheaterContract.TheaterPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showNetworkError(int strId) {

    }
}
