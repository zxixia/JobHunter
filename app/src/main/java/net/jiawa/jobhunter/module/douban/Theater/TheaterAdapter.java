package net.jiawa.jobhunter.module.douban.Theater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import net.jiawa.jobhunter.base.adapter.BaseRecyclerAdapter;
import net.jiawa.jobhunter.bean.douban.Subjects;
import net.jiawa.jobhunter.bean.douban.Theaters;

/**
 * Created by zhaoxin5 on 2017/4/17.
 */

public class TheaterAdapter extends BaseRecyclerAdapter<Subjects> {

    public TheaterAdapter(Context context) {
        // 没有头尾的列表样式
        super(context, BaseRecyclerAdapter.NEITHER);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return null;
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, Subjects item, int position) {

    }
}
