package net.jiawa.jobhunter.module.douban.movie;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import net.jiawa.jobhunter.R;
import net.jiawa.jobhunter.base.activities.BaseTitleActivity;
import net.jiawa.jobhunter.base.activities.BaseTopImageActivity;
import net.jiawa.jobhunter.bean.douban.Subject;
import net.jiawa.jobhunter.bean.douban.Subjects;
import net.jiawa.jobhunter.module.douban.theater.TheaterAdapter;
import net.jiawa.jobhunter.widgets.MovieStarView;

import butterknife.Bind;

/**
 * Created by zhaoxin5 on 2017/4/20.
 */

public class MovieActivity extends BaseTopImageActivity implements MovieContract.BasicInfoView {

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
    Subjects mSubjects;

    MovieContract.MoviePresenter mPresenter;

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
        mSummary.setText(subject.getSummary());
    }

    @Override
    public void onGetSubjectFailed() {

    }

    @Override
    public void setPresenter(MovieContract.MoviePresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showNetworkError(int strId) {}
}
