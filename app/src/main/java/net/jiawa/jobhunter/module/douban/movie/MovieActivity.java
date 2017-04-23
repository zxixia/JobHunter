package net.jiawa.jobhunter.module.douban.movie;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;

import net.jiawa.jobhunter.R;
import net.jiawa.jobhunter.base.activities.BaseTitleActivity;
import net.jiawa.jobhunter.base.activities.BaseTopImageActivity;
import net.jiawa.jobhunter.bean.douban.Subject;
import net.jiawa.jobhunter.bean.douban.Subjects;

import butterknife.Bind;

/**
 * Created by zhaoxin5 on 2017/4/20.
 */

public class MovieActivity extends BaseTopImageActivity implements MovieContract.BasicInfoView {

    @Bind(R.id.iv_top_image)
    ImageView mTopImage;
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
        getImageLoader()
                .load(subject.getPhotos().get(0).getImage())
                .asBitmap()
                .placeholder(R.mipmap.ic_launcher)
                .into(getImageView());
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
