package net.jiawa.jobhunter.module;

import android.app.Activity;
import android.os.Bundle;

import net.jiawa.jobhunter.R;
import net.jiawa.jobhunter.base.activities.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }
}
