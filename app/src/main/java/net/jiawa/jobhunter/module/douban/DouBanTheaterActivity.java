package net.jiawa.jobhunter.module.douban;

import com.google.gson.Gson;
import com.loopj.android.http.TextHttpResponseHandler;

import net.jiawa.debughelper.XLog;
import net.jiawa.jobhunter.R;
import net.jiawa.jobhunter.base.activities.BaseBackActivity;
import net.jiawa.jobhunter.bean.douban.Theaters;

import java.util.Arrays;

import cz.msebera.android.httpclient.Header;

/**
 * Created by zhaoxin5 on 2017/4/17.
 */

public class DouBanTheaterActivity extends BaseBackActivity {

    @Override
    protected void initData() {
        super.initData();

        DouBanAPI.getTheaters(new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                XLog.d(true, 1, Arrays.toString(headers));
                XLog.d(true, 1, responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                XLog.d(true, 1, Arrays.toString(headers));
                Theaters theaters = new Gson().fromJson(responseString, Theaters.class);
                for (int i=0; i< theaters.getCount(); i++) {
                    XLog.d(true, 1, theaters.getCount() + ", " + theaters.getSubjects().get(i).getTitle());
                }
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_douban_theaters;
    }
}
