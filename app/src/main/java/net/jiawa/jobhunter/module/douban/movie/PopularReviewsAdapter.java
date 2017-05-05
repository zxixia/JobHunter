package net.jiawa.jobhunter.module.douban.movie;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.jiawa.debughelper.XLog;
import net.jiawa.jobhunter.R;
import net.jiawa.jobhunter.base.adapter.BaseRecyclerAdapter;
import net.jiawa.jobhunter.bean.douban.PopularComments;
import net.jiawa.jobhunter.bean.douban.PopularReviews;
import net.jiawa.jobhunter.widgets.MovieStarView;

/**
 * Created by zhaoxin5 on 2017/4/27.
 */

public class PopularReviewsAdapter extends BaseRecyclerAdapter<PopularReviews> {

    public PopularReviewsAdapter(Context context) {
        // 没有头尾的列表样式
        super(context, BaseRecyclerAdapter.NEITHER);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new PopularReviewsViewHolder(mInflater.inflate(R.layout.item_list_douban_popular_reviews, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, PopularReviews item, int position) {
        PopularReviewsViewHolder h = (PopularReviewsViewHolder) holder;
        h.author.setText(item.getAuthor().getName());
        h.content.setText(item.getSummary());
        h.title.setText("《" + item.getTitle() + "》");
        // h.starView.setStars(25);
        if (position == getItemCount() - 1) {
            h.dividerLine.setVisibility(View.GONE);
        }
        XLog.d(true, 1, "position: " + position + ", stars: " + item.getRating().getStars() + ", average: " + item.getRating().getAverage());
    }

    private static class PopularReviewsViewHolder extends RecyclerView.ViewHolder {

        TextView author;
        TextView title;
        TextView content;
        // MovieStarView starView;
        View dividerLine;

        public PopularReviewsViewHolder(View itemView) {
            super(itemView);
            author = (TextView) itemView.findViewById(R.id.tv_author);
            content = (TextView) itemView.findViewById(R.id.tv_content);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            // starView = (MovieStarView) itemView.findViewById(R.id.ms_stars);
            dividerLine = itemView.findViewById(R.id.v_divider_line);
        }
    }
}
