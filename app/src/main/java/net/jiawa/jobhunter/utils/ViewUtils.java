package net.jiawa.jobhunter.utils;

import android.view.View;

/**
 * Created by lenovo on 2017/3/25.
 */

public class ViewUtils {
    public static <T> T findViewById(View view, int id) {
        return (T) view.findViewById(id);
    }
}
