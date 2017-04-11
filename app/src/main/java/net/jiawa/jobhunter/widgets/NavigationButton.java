package net.jiawa.jobhunter.widgets;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.widget.TextView;

import net.jiawa.debughelper.XLog;
import net.jiawa.jobhunter.R;

/**
 * Created by zhaoxin5 on 2017/3/22.
 */

public class NavigationButton extends TextView {

    private Fragment mFragment = null;
    private Class<?> mClx;
    private String mTag;

    public NavigationButton(Context context) {
        super(context);
    }

    public NavigationButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NavigationButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(int id, Class<?> cls) {
        if (id > 0) {
            Drawable drawable = getResources().getDrawable(id);
            if (null != drawable) this.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
        }
        mClx = cls;
        mTag = mClx.getName();
    }

    public Fragment getFragment() {
        return mFragment;
    }

    public void setFragment(Fragment fragment) {
        this.mFragment = fragment;
    }

    public Class<?> getClx() {
        return mClx;
    }

    public String getTag() {
        return mTag;
    }
}
