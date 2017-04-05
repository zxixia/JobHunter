package net.jiawa.jobhunter.base.adapter;

import android.content.Context;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;

import com.bumptech.glide.RequestManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by huanghaibin_dev
 * on 2016/8/18.
 */

public abstract class BaseGeneralRecyclerAdapter<T> extends BaseRecyclerAdapter<T> {
    protected Callback mCallBack;
    private List<T> mPreItems;

    public BaseGeneralRecyclerAdapter(Callback callback, int mode) {
        super(callback.getContext(), mode);
        mCallBack = callback;
        setState(STATE_LOADING, true);
    }

    /*protected void parseAtUserContent(TweetTextView textView, String text) {
        String content;
        if (TextUtils.isEmpty(text)) return;
        content = text.replaceAll("[\n\\s]+", " ").replaceAll("&nbsp;", " ");
        Spannable spannable = AssimilateUtils.assimilateOnlyAtUser(mCallBack.getContext(), content);
        spannable = AssimilateUtils.assimilateOnlyTag(mCallBack.getContext(), spannable);
        spannable = AssimilateUtils.assimilateOnlyLink(mCallBack.getContext(), spannable);
        spannable = AssimilateUtils.assimilateOnlyTeamTask(mCallBack.getContext(), spannable);
        spannable = InputHelper.displayEmoji(mCallBack.getContext().getResources(), spannable);
        textView.setText(spannable);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setFocusable(false);
        textView.setDispatchToParent(true);
        textView.setLongClickable(false);
    }*/

    @SuppressWarnings("UnusedReturnValue")
    public int addItems(List<T> items) {
        int filterOut = 0;
        if (items != null && !items.isEmpty()) {
            List<T> date = new ArrayList<>();
            if (mPreItems != null) {
                for (T d : items) {
                    if (!mPreItems.contains(d)) {
                        date.add(d);
                    } else {
                        filterOut++;
                    }
                }
            } else {
                date = items;
            }
            mPreItems = items;
            addAll(date);
        }
        return filterOut;
    }

    public void clearPreItems() {
        mPreItems = null;
    }

    public interface Callback {
        RequestManager getImgLoader();

        Context getContext();

        Date getSystemTime();
    }
}
