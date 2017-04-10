package net.jiawa.jobhunter.module.git.projectdetail;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;

import net.jiawa.debughelper.XLog;
import net.jiawa.jobhunter.bean.git.projectdetail.File;
import net.jiawa.jobhunter.bean.git.projectdetail.Repository;
import net.jiawa.jobhunter.module.git.GitHubAPI;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by zhaoxin5 on 2017/4/6.
 */

public class ProjectDetailCodeTreePresenter implements ProjectDetailContract.CodeTreePresenter {

    final ProjectDetailContract.CodeTreeView mView;
    final Repository mRepository;
    // 缓存已经加载过的代码目录
    private HashMap<String, List<File>> mFileMap = new HashMap<>();
    // 记录当前代码的深度
    // 根目录为0,
    // 每点击一次,相当于增加一个深度
    // 没回退一次,减去一个深度
    // 存放的是当前深度对应的Path
    private List<String> mPaths = new ArrayList<>();

    public ProjectDetailCodeTreePresenter(ProjectDetailContract.CodeTreeView view, Repository repository) {
        this.mView = view;
        this.mRepository = repository;
        mView.setPresenter(this);
        // 清空缓存的目录信息
        mPaths.clear();
        mFileMap.clear();
    }

    private String getKey(final String path) {
        return null == path ? "cache_root" : path;
    }

    private void cacheFile(final String path, List<File> files) {
        String key = getKey(path);
        if (mFileMap.containsKey(key)) return;
        mFileMap.put(key, files);
    }

    @Override
    public void getCodeTree(final String path) {
        final String key = getKey(path);

        if (mFileMap.containsKey(key)) {
            // 已经加载过这个目录,直接返回
            mView.onRefreshSuccess(mFileMap.get(key));
            // 暂停Google下拉刷新控件的刷新
            mView.onComplete();

            // 记录当前目录深度
            mPaths.add(key);
            return;
        }

        GitHubAPI.getContents(mRepository.getContentsUrl(), path, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                for (int i=0; i<headers.length; i++) {
                    XLog.d(false, 3, headers[i] + "");
                }
                // 失败处理
                // 隐藏google下拉刷新控件
                mView.onComplete();
                XLog.d(false, 3, responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                for (int i=0; i<headers.length; i++) {
                    XLog.d(false, 3, headers[i] + "");
                }

                Type listType = new TypeToken<List<File>>(){}.getType();
                List<File> files = new Gson().fromJson(responseString, listType);
                // 缓存这个目录
                cacheFile(path, files);
                mView.onRefreshSuccess(files);
                // 暂停Google下拉刷新控件的刷新
                mView.onComplete();

                // 记录当前目录深度
                mPaths.add(key);
            }
        });
    }

    @Override
    public boolean canBack() {
        // 如果mPaths的长度只有1
        // 表明只有0位置放了一个根目录
        // 此时不可回退
        return mPaths.size() > 1;
    }

    @Override
    public void onBack() {
        // 移除当前目录深度
        mPaths.remove(mPaths.size()-1);
        // 移除父亲目录深度
        // 因为调用getCodeTree的时候,调用成功的话会保存目录
        // 所以暂时移除父亲目录
        String parentPath = mPaths.remove(mPaths.size()-1);
        getCodeTree(parentPath);
    }

    @Override
    public void onRefreshing() {
        // 第一次是在BaseRecyclerView的initData中调用的
        getCodeTree(null);
    }

    @Override
    public void onLoadMore() {}
}
