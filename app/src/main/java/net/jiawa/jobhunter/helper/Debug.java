package net.jiawa.jobhunter.helper;

import android.view.MotionEvent;

/**
 * Created by zhaoxin5 on 2017/5/4.
 */

public class Debug {
    public static String getMotionEvent(MotionEvent event) {
        String rtValue = "";
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                rtValue = "DOWN";
                break;
            case MotionEvent.ACTION_UP:
                rtValue = "UP";
                break;
            case MotionEvent.ACTION_MOVE:
                rtValue = "MOVE";
                break;
        }
        return rtValue;
    }
}
