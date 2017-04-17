package net.jiawa.jobhunter.module.douban.Theater;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.jiawa.jobhunter.R;
import net.jiawa.jobhunter.base.adapter.BaseGeneralRecyclerAdapter;
import net.jiawa.jobhunter.base.adapter.BaseRecyclerAdapter;
import net.jiawa.jobhunter.bean.douban.Subjects;

/**
 * Created by zhaoxin5 on 2017/4/17.
 */

public class TheaterAdapter extends BaseGeneralRecyclerAdapter<Subjects> {

    public TheaterAdapter(Callback callback) {
        // 没有头尾的列表样式
        super(callback, BaseRecyclerAdapter.NEITHER);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new TheaterViewHolder(mInflater.inflate(R.layout.item_list_douban_theater, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, Subjects subjects, int position) {
        TheaterViewHolder h = (TheaterViewHolder) holder;
        // 海报
        mCallBack.getImgLoader()
                .load(subjects.getImages().getLarge())
                .asBitmap()
                .placeholder(R.mipmap.ic_launcher)
                .into(h.mPoster);
        h.mTextName.setText(subjects.getTitle());
        h.mRating.setText(subjects.getRating().getStars());
    }

    private static class TheaterViewHolder extends RecyclerView.ViewHolder {
        ImageView mPoster;
        TextView mTextName;
        TextView mRating;

        TheaterViewHolder(View itemView) {
            super(itemView);
            mRating = (TextView) itemView.findViewById(R.id.tv_rating);
            mPoster = (ImageView) itemView.findViewById(R.id.iv_poster);
            mTextName = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}
