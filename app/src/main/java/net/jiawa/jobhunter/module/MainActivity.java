package net.jiawa.jobhunter.module;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import net.jiawa.debughelper.XLog;
import net.jiawa.debughelper.flag.XFlag;
import net.jiawa.jobhunter.R;
import net.jiawa.jobhunter.base.activities.BaseActivity;
import net.jiawa.jobhunter.module.navigationbar.NavigationBarFragment;

import butterknife.Bind;

public class MainActivity extends BaseActivity {

    NavigationBarFragment mNavigationBarFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        XLog.setup(1, XLog.Flag(
                new XFlag(1, true, "DogFood"),
                new XFlag(2, false, "Animate"),
                new XFlag(3, false,  "Http")
        ));
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    // 定义在BaseActivity中的方法
    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        FragmentManager manager = getSupportFragmentManager();
        mNavigationBarFragment = (NavigationBarFragment) manager.findFragmentById(R.id.fag_navigationbar);
        mNavigationBarFragment.setup(this, manager, R.id.fag_maincontent);
    }
}
