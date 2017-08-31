package com.youli.zbetuch.jingan.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by liutao on 2017/8/23.
 */

public class JobInfoListView extends ListView {

    public JobInfoListView(Context context) {
        super(context);
    }
    public JobInfoListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public JobInfoListView(Context context, AttributeSet attrs,
                      int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }
}
