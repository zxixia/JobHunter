package net.jiawa.jobhunter.helper;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by zhaoxin5 on 2017/5/4.
 */

public class Debug {

    public static String MeasureSpec(int measureSpec) {
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);

        StringBuilder sb = new StringBuilder();
        sb.append("specMode: " + getMeasureSpecMode(specMode));
        sb.append(",  ");
        sb.append("specSize: " + specSize);
        return sb.toString();
    }

    public static String get(int visibility) {
        String rtValue = "";
        switch(visibility) {
            case View.VISIBLE:
                rtValue = "VISIBLE";
                break;
            case View.INVISIBLE:
                rtValue = "INVISIBLE";
                break;
            case View.GONE:
                rtValue = "GONE";
                break;
        }
        return rtValue;
    }

    public static String getMeasureSpecMode(int mode) {
        String rtValue = "";
        switch (mode) {
            case View.MeasureSpec.EXACTLY:
                rtValue = "EXACTLY";
                break;
            case View.MeasureSpec.AT_MOST:
                rtValue = "AT_MOST";
                break;
            case View.MeasureSpec.UNSPECIFIED:
                rtValue = "UNSPECIFIED";
                break;
        }
        return rtValue;
    }

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
