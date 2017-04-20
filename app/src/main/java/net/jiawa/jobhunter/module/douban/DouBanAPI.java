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

    private static final String API_KEY = "0b2bdeda43b5688921839c8ecb20399b";

    private static AsyncHttpClient mClient = new AsyncHttpClient();
    static {
        mClient.setURLEncodingEnabled(false);
    }

    private static void onCreateBaseParams(RequestParams params) {
        params.put("apikey", API_KEY);
        params.put("city", "南京");
        params.put("client", "");
        params.put("udid",   "");
    }

    public static void getSubject(String id, TextHttpResponseHandler handler) {
        final String url = String.format("http://api.douban.com/v2/movie/subject/%s", id);
        // "http://api.douban.com/v2/movie/subject/26865690?apikey=0b2bdeda43b5688921839c8ecb20399b&city=%E5%8C%97%E4%BA%AC&client=&udid=";
        RequestParams params = new RequestParams();
        onCreateBaseParams(params);
        mClient.get(url, params, handler);
    }

    public static void getTheaters(TextHttpResponseHandler handler) {
        final String url = "https://api.douban.com/v2/movie/in_theaters";
        // String.format("https://api.douban.com/v2/movie/in_theaters", owner, repoName);

        // https://api.douban.com/v2/movie/in_theaters?apikey=0b2bdeda43b5688921839c8ecb20399b&city=南京&start=0&count=100&client=&udid=
        RequestParams params = new RequestParams();
        onCreateBaseParams(params);
        params.put("start",  "0");
        params.put("count",  "100");
        mClient.get(url, params, handler);
    }
}
