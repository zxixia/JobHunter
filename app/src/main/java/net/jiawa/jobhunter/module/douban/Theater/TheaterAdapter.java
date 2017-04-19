package net.jiawa.jobhunter.module.douban.theater;

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
import net.jiawa.jobhunter.bean.douban.Subjects;
import net.jiawa.jobhunter.widgets.MovieStarView;

import java.util.List;

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
        h.mTextName.setText(subjects.getTitle() + " / " + subjects.getYear());
        h.mRating.setText(String.valueOf(subjects.getRating().getAverage() + "分"));
        h.mMovieStarView.setStars(Integer.valueOf(subjects.getRating().getStars()));
        String pubdates = generateString(subjects.getPubdates());
        if ("".equals(pubdates)) {
            h.mPubdates.setVisibility(View.GONE);
        } else {
            h.mPubdates.setText(pubdates);
        }
        h.mCasts.setText(generateString(subjects.getCasts()));
        h.mGenres.setText(generateString(subjects.getGenres()));
        h.mDirectors.setText(generateString(subjects.getDirectors()));
    }

    private String castObject(Object o) {
        if (o instanceof String) return (String)o;
        if (o instanceof Casts) return ((Casts)o).getName();
        if (o instanceof Directors) return ((Directors)o).getName();
        return o.toString();
    }

    private <Model> String generateString(List<Model> models) {
        if (null == models) return "";
        if (models.size() == 0) return "";
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<models.size()-1; i++) {
            sb.append(castObject(models.get(i)));
            sb.append(" / ");
        }
        sb.append(castObject(models.get(models.size()-1)));
        return sb.toString();
    }

    private static class TheaterViewHolder extends RecyclerView.ViewHolder {
        ImageView mPoster;
        TextView mTextName;
        TextView mRating;
        MovieStarView mMovieStarView;
        TextView mPubdates;
        TextView mCasts;
        TextView mGenres;
        TextView mDirectors;

        TheaterViewHolder(View itemView) {
            super(itemView);
            mRating = (TextView) itemView.findViewById(R.id.tv_rating);
            mPoster = (ImageView) itemView.findViewById(R.id.iv_poster);
            mTextName = (TextView) itemView.findViewById(R.id.tv_name);
            mMovieStarView = (MovieStarView) itemView.findViewById(R.id.ms_stars);
            mPubdates = (TextView) itemView.findViewById(R.id.tv_pubdates);
            mCasts = (TextView) itemView.findViewById(R.id.tv_casts);
            mGenres = (TextView) itemView.findViewById(R.id.tv_genres);
            mDirectors = (TextView) itemView.findViewById(R.id.tv_directors);
        }
    }
}
