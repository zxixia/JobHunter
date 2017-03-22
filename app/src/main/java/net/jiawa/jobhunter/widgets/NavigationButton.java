package net.jiawa.jobhunter.widgets;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

import net.jiawa.debughelper.XLog;
import net.jiawa.jobhunter.R;

/**
 * Created by zhaoxin5 on 2017/3/22.
 */

public class NavigationButton extends TextView {

    public NavigationButton(Context context) {
        super(context);
    }

    public NavigationButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NavigationButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(int id) {
        if (id > 0) {
            Drawable drawable = getResources().getDrawable(id);
            if (null != drawable) this.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
        }
    }
}
