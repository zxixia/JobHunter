package net.jiawa.jobhunter.module.douban.theater;

import android.view.View;

import net.jiawa.jobhunter.base.adapter.BaseRecyclerAdapter;
import net.jiawa.jobhunter.base.fragments.BaseRecyclerFragment;
import net.jiawa.jobhunter.bean.douban.Subjects;

/**
 * Created by zhaoxin5 on 2017/4/17.
 */

public class TheaterFragment extends BaseRecyclerFragment<TheaterContract.TheaterPresenter, Subjects>
        implements TheaterContract.TheaterView {

    static TheaterFragment newInstance() {
        return new TheaterFragment();
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mRefreshLayout.setEnabled(false);
    }

    @Override
    protected BaseRecyclerAdapter getAdapter() {
        return new TheaterAdapter(this);
    }

    @Override
    protected void onItemClick(Subjects subjects, int position) {

    }
}
