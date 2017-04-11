package net.jiawa.jobhunter.module.main;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import net.jiawa.jobhunter.R;
import net.jiawa.jobhunter.base.fragments.BaseTitleFragment;

import butterknife.Bind;

/**
 * Created by zhaoxin5 on 2017/4/11.
 */

public class MainTabFragment extends BaseTitleFragment {

    @Bind(R.id.tb_main)
    TabLayout mTabLayout;
    @Bind(R.id.tv_main_add)
    TextView mTabAdd;
    @Bind(R.id.view_pager)
    ViewPager mViewPager;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_tab_container;
    }

    @Override
    protected int getTitleRes() {
        return R.string.main_tab_title;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mTabLayout.addTab(mTabLayout.newTab().setText("电影"));
        mTabLayout.addTab(mTabLayout.newTab().setText("天气"));
        mTabLayout.addTab(mTabLayout.newTab().setText("精品图文"));
        mTabLayout.addTab(mTabLayout.newTab().setText("每日一乐"));
        mTabLayout.addTab(mTabLayout.newTab().setText("段子"));
        mTabLayout.addTab(mTabLayout.newTab().setText("内涵"));
    }
}
