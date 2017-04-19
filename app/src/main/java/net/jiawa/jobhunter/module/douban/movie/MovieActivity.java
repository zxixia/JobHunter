package net.jiawa.jobhunter.module.douban.movie;

/**
 * Created by zhaoxin5 on 2017/4/19.
 */

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import net.jiawa.debughelper.XLog;
import net.jiawa.jobhunter.R;
import net.jiawa.jobhunter.base.activities.BaseActivity;
import net.jiawa.jobhunter.bean.douban.Subjects;
import net.jiawa.jobhunter.utils.UiUtil;

import butterknife.Bind;

/***
 * 这个Activity最上面是一张海报
 */
public class MovieActivity extends BaseActivity {

    @Bind(R.id.iv_top_image)
    ImageView mTopImage;
    Subjects mSubjects;

    @Override
    protected int getContentView() {
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
        Intent intent = getIntent();
        mSubjects = (Subjects) intent.getSerializableExtra("subjects");
        XLog.d(true, 1, "" + mSubjects.getId() + ", " + mSubjects.getTitle());
    }

    @Override
    protected void initData() {
        super.initData();
        getImageLoader()
            .load(mSubjects.getImages().getLarge())
            .asBitmap()
            .placeholder(R.mipmap.ic_launcher)
            .into(mTopImage);
    }

    @Override
    protected void initWindow() {
        super.initWindow();
        activateLightStatusBar(true);
        setFullScreen(false);
    }
}
