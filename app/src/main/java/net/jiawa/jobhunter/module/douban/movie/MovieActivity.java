package net.jiawa.jobhunter.module.douban.movie;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;

import net.jiawa.debughelper.XLog;
import net.jiawa.jobhunter.R;
import net.jiawa.jobhunter.base.activities.BaseTopImageActivity;
import net.jiawa.jobhunter.base.adapter.BaseGeneralRecyclerAdapter;
import net.jiawa.jobhunter.base.adapter.BaseRecyclerAdapter;
import net.jiawa.jobhunter.bean.douban.Casts;
import net.jiawa.jobhunter.bean.douban.Directors;
import net.jiawa.jobhunter.bean.douban.PopularComments;
import net.jiawa.jobhunter.bean.douban.Subject;
import net.jiawa.jobhunter.bean.douban.Subjects;
import net.jiawa.jobhunter.module.douban.theater.TheaterAdapter;
import net.jiawa.jobhunter.widgets.MovieStarView;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.Bind;

/**
 * Created by zhaoxin5 on 2017/4/20.
 */

public class MovieActivity extends BaseTopImageActivity implements MovieContract.BasicInfoView,
        BaseGeneralRecyclerAdapter.Callback {

    @Bind(R.id.tv_title)
    TextView mTitle;
    @Bind(R.id.tv_year_type)
    TextView mYearType;
    @Bind(R.id.tv_pub_dates)
    TextView mPubDate;
    @Bind(R.id.tv_duration)
    TextView mDuration;
    @Bind(R.id.tv_score)
    TextView mScores;
    @Bind(R.id.tv_people_count)
    TextView mPeopleCount;
    @Bind(R.id.ms_stars)
    MovieStarView mStars;
    @Bind(R.id.tv_summary)
    TextView mSummary;
    protected RecyclerView mCasts;
    @Bind(R.id.rv_popular_comments)
    RecyclerView mPopularComments;
    Subjects mSubjects;

    MovieContract.MoviePresenter mPresenter;
    BaseRecyclerAdapter<Object> mCastsAdapter;
    BaseRecyclerAdapter<PopularComments> mPopularCommentsAdapter;

    private List<Object> mCastsList;
    List<PopularComments> mPopularCommentsList;

    @Override
    protected int getChildContentViewId() {
        return R.layout.activity_douban_movie;
    }

    // 提供给外界直接调用的静态方法
    public static void show(Context context, Subjects subjects) {
        Intent intent = new Intent(context, MovieActivity.class);
        intent.putExtra("subjects", subjects);
        context.startActivity(intent);
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        mCasts = (RecyclerView) findViewById(R.id.rv_casts);
        mCasts.setLayoutManager(getLayoutManager(LinearLayoutManager.HORIZONTAL));

        mPopularComments = (RecyclerView) findViewById(R.id.rv_popular_comments);
        mPopularComments.setLayoutManager(getLayoutManager(LinearLayoutManager.VERTICAL));
    }

    @Override
    protected void initData() {
        super.initData();
        Intent intent = getIntent();
        mSubjects = (Subjects) intent.getSerializableExtra("subjects");
        getImageLoader()
                .load(mSubjects.getImages().getLarge())
                .asBitmap()
                .placeholder(R.mipmap.ic_launcher)
                .into(getImageView());
        setTitle(mSubjects.getTitle());
        MoviePresenter presenter = new MoviePresenter(this, mSubjects);
        presenter.getSubject(mSubjects.getId());
    }

    @Override
    public void onGetSubjectSuccessful(Subject subject) {
        int photoIndex = subject.getPhotosCount() > 1 ? 1 : 0;
        getImageLoader()
                .load(subject.getPhotos().get(photoIndex).getImage())
                .asBitmap()
                .placeholder(R.mipmap.ic_launcher)
                .into(getImageView());
        mTitle.setText(subject.getTitle());
        mYearType.setText(subject.getYear() + " / " + TheaterAdapter.generateString(subject.getGenres()));
        mPubDate.setText(subject.getPubdates().get(0));
        mDuration.setText("片长: " + subject.getDurations().get(0));
        mScores.setText("" + mSubjects.getRating().getAverage());
        mStars.setStars(Integer.valueOf(mSubjects.getRating().getStars()));
        mPeopleCount.setText(subject.getRatingsCount() + "人");
        mSummary.setText(subject.getSummary().replace("\n", ""));

        mCastsAdapter = new CastsAdapter(this);
        mCastsAdapter.setOnItemClickListener(mCastsClickListener);
        mCasts.setAdapter(mCastsAdapter);

        mPopularCommentsAdapter = new PopularCommentsAdapter(this);
        mPopularCommentsAdapter.setOnItemClickListener(mPopularCommentsClickListener);
        mPopularComments.setAdapter(mPopularCommentsAdapter);

        mPresenter.getCastsList(subject.getCasts(), subject.getDirectors());
        mPresenter.getPopularComments();
    }

    @Override
    public void onGetSubjectFailed() {

    }

    @Override
    public void onGetCasts(List<Object> list) {
        XLog.d(true, 1, Arrays.toString(list.toArray()));
        mCastsList = list;
        mCastsAdapter.resetItem(mCastsList);
    }

    @Override
    public void onGetPopularComments(List<PopularComments> list) {
        mPopularCommentsList = list;
        mPopularCommentsAdapter.resetItem(mPopularCommentsList);
    }

    @Override
    public void setPresenter(MovieContract.MoviePresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showNetworkError(int strId) {}

    @Override
    public RequestManager getImgLoader() {
        return getImageLoader();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public Date getSystemTime() {
        return new Date();
    }

    protected RecyclerView.LayoutManager getLayoutManager(int orientation) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(orientation);
        return linearLayoutManager;
    }


    BaseRecyclerAdapter.OnItemClickListener mCastsClickListener = new BaseRecyclerAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int position, long itemId) {
            Object o = mCastsList.get(position);
            if (o instanceof Directors) {
                XLog.d(true, 1, position + ", " + ((Directors) o).getNameEn());
            }
            if (o instanceof Casts) {
                XLog.d(true, 1, position + ", " + ((Casts) o).getNameEn());
            }
        }
    };

    BaseRecyclerAdapter.OnItemClickListener mPopularCommentsClickListener = new BaseRecyclerAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int position, long itemId) {
            PopularComments p = mPopularCommentsList.get(position);
            XLog.d(true, 1, position + ", " + p.getCreatedAt());
        }
    };
}
