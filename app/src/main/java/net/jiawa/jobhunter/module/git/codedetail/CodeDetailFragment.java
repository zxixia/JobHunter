package net.jiawa.jobhunter.module.git.codedetail;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import net.jiawa.debughelper.XLog;
import net.jiawa.jobhunter.R;
import net.jiawa.jobhunter.base.fragments.BaseFragment;
import net.jiawa.jobhunter.bean.git.codedetail.Code;
import net.jiawa.jobhunter.utils.MarkdownUtils;
import net.jiawa.jobhunter.utils.SourceEditor;

import butterknife.Bind;

/**
 * Created by lenovo on 2017/4/9.
 */

public class CodeDetailFragment extends BaseFragment implements CodeDetailContract.View {

    @Bind(R.id.tv_file_name)
    TextView mTitle;
    @Bind(R.id.webView)
    WebView mWebView;
    private SourceEditor mEditor;
    private CodeDetailContract.Presenter mPresenter;
    String mFileName;
    String mPath;

    static CodeDetailFragment newInstance(String fileName, String path) {
        CodeDetailFragment fragment = new CodeDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("fileName", fileName);
        bundle.putString("path", path);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initData() {
        super.initData();
        mTitle.setText(getArguments().getString("fileName"));
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDefaultFontSize(10);
        settings.setAllowContentAccess(true);
        mWebView.setWebChromeClient(new WebChromeClient() {
        });
        mEditor = new SourceEditor(mWebView);

        mPresenter.getCode(getArguments().getString("path"));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_code_detail;
    }

    @Override
    public void onLoadCodeSuccess(Code code) {
        // 请求代码成功
        XLog.d(true, 1, code.getEncoding() + ", " + code.getContent());
        String fileName = getArguments().getString("fileName");
        mEditor.setMarkdown(MarkdownUtils.isMarkdown(fileName));
        mEditor.setSource(fileName, code);
    }

    @Override
    public void onLoadCodeFailed() {}

    @Override
    public void setPresenter(CodeDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showNetworkError(int strId) {}
}
