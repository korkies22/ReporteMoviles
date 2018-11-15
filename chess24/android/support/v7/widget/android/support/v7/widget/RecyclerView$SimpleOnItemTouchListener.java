/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.MotionEvent
 */
package android.support.v7.widget;

import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;

public static class RecyclerView.SimpleOnItemTouchListener
implements RecyclerView.OnItemTouchListener {
    @Override
    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean bl) {
    }

    @Override
    public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
    }
}
