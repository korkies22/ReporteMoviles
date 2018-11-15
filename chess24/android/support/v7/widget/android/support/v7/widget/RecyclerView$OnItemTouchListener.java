/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.MotionEvent
 */
package android.support.v7.widget;

import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;

public static interface RecyclerView.OnItemTouchListener {
    public boolean onInterceptTouchEvent(RecyclerView var1, MotionEvent var2);

    public void onRequestDisallowInterceptTouchEvent(boolean var1);

    public void onTouchEvent(RecyclerView var1, MotionEvent var2);
}
