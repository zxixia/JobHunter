package net.jiawa.jobhunter.module;

import android.app.Activity;
import android.os.Bundle;

import net.jiawa.debughelper.XLog;
import net.jiawa.debughelper.flag.XFlag;
import net.jiawa.jobhunter.R;
import net.jiawa.jobhunter.base.activities.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        XLog.setup(1, XLog.Flag(
                new XFlag(1, true, "DogFood")
        ));
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }
}
