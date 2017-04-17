package net.jiawa.jobhunter.module.douban;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import net.jiawa.debughelper.XLog;
import net.jiawa.jobhunter.utils.EncodingUtils;

/**
 * Created by zhaoxin5 on 2017/4/17.
 */

public class DouBanAPI {

    private static AsyncHttpClient mClient = new AsyncHttpClient();
    static {
        mClient.setURLEncodingEnabled(false);
    }

    public static void getTheaters(TextHttpResponseHandler handler) {
        final String getStr = "https://api.douban.com/v2/movie/in_theaters";
        // String.format("https://api.douban.com/v2/movie/in_theaters", owner, repoName);
        mClient.get(getStr, handler);

        RequestParams params = new RequestParams();
        params.put("apikey", "0b2bdeda43b5688921839c8ecb20399b");
        params.put("city",   "南京");
        params.put("start",  "0");
        params.put("count",  "100");
        params.put("client", "");
        params.put("udid",   "");
        mClient.get(getStr, params, handler);
    }
}
