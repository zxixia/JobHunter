package net.jiawa.jobhunter.module.douban.movie;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.jiawa.jobhunter.R;
import net.jiawa.jobhunter.base.adapter.BaseGeneralRecyclerAdapter;
import net.jiawa.jobhunter.base.adapter.BaseRecyclerAdapter;
import net.jiawa.jobhunter.bean.douban.Casts;
import net.jiawa.jobhunter.bean.douban.Directors;
import net.jiawa.jobhunter.bean.douban.PopularComments;

/**
 * Created by zhaoxin5 on 2017/4/27.
 */

public class PopularCommentsAdapter extends BaseRecyclerAdapter<PopularComments> {

    public PopularCommentsAdapter(Context context) {
        // 没有头尾的列表样式
        super(context, BaseRecyclerAdapter.NEITHER);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new PopularCommentsViewHolder(mInflater.inflate(R.layout.item_list_douban_popular_comments, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, PopularComments item, int position) {
        PopularCommentsAdapter.PopularCommentsViewHolder h = (PopularCommentsAdapter.PopularCommentsViewHolder) holder;
        h.author.setText(item.getAuthor().getName());
        h.createdTime.setText(item.getCreatedAt());
        h.content.setText(item.getContent());
    }

    private static class PopularCommentsViewHolder extends RecyclerView.ViewHolder {

        TextView author;
        TextView createdTime;
        TextView content;

        public PopularCommentsViewHolder(View itemView) {
            super(itemView);
            author = (TextView) itemView.findViewById(R.id.tv_author);
            createdTime = (TextView) itemView.findViewById(R.id.tv_created_time);
            content = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }
}
