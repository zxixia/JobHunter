package net.jiawa.jobhunter.module.douban.Theater;

import net.jiawa.jobhunter.base.adapter.BaseRecyclerAdapter;
import net.jiawa.jobhunter.base.fragments.BaseRecyclerFragment;
import net.jiawa.jobhunter.bean.douban.Subjects;

/**
 * Created by zhaoxin5 on 2017/4/17.
 */

public class TheaterFragment extends BaseRecyclerFragment<TheaterContract.TheaterPresenter, Subjects> {

    @Override
    protected BaseRecyclerAdapter getAdapter() {
        return new TheaterAdapter(getContext());
    }

    @Override
    protected void onItemClick(Subjects subjects, int position) {

    }

    @Override
    public void setPresenter(TheaterContract.TheaterPresenter presenter) {

    }
}
