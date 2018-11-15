/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.view.GestureDetector
 *  android.view.GestureDetector$SimpleOnGestureListener
 *  android.view.MotionEvent
 *  android.view.View
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.AdapterView$OnItemLongClickListener
 *  android.widget.AdapterView$OnItemSelectedListener
 *  android.widget.ListAdapter
 */
package de.cisha.android.ui.view;

import android.graphics.Rect;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;

class HorizontalListView
extends GestureDetector.SimpleOnGestureListener {
    HorizontalListView() {
    }

    private boolean isEventWithinView(MotionEvent motionEvent, View view) {
        Rect rect = new Rect();
        int[] arrn = new int[2];
        view.getLocationOnScreen(arrn);
        int n = arrn[0];
        int n2 = view.getWidth();
        int n3 = arrn[1];
        rect.set(n, n3, n2 + n, view.getHeight() + n3);
        return rect.contains((int)motionEvent.getRawX(), (int)motionEvent.getRawY());
    }

    public boolean onDown(MotionEvent motionEvent) {
        return HorizontalListView.this.onDown(motionEvent);
    }

    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        return HorizontalListView.this.onFling(motionEvent, motionEvent2, f, f2);
    }

    public void onLongPress(MotionEvent motionEvent) {
        int n = HorizontalListView.this.getChildCount();
        for (int i = 0; i < n; ++i) {
            View view = HorizontalListView.this.getChildAt(i);
            if (!this.isEventWithinView(motionEvent, view)) continue;
            if (HorizontalListView.this.mOnItemLongClicked == null) break;
            HorizontalListView.this.mOnItemLongClicked.onItemLongClick((AdapterView)HorizontalListView.this, view, HorizontalListView.this.mLeftViewIndex + 1 + i, HorizontalListView.this.mAdapter.getItemId(HorizontalListView.this.mLeftViewIndex + 1 + i));
            return;
        }
    }

    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        for (int i = 0; i < HorizontalListView.this.getChildCount(); ++i) {
            View view = HorizontalListView.this.getChildAt(i);
            if (!this.isEventWithinView(motionEvent, view)) continue;
            if (HorizontalListView.this.mOnItemClicked != null) {
                HorizontalListView.this.mOnItemClicked.onItemClick((AdapterView)HorizontalListView.this, view, HorizontalListView.this.mLeftViewIndex + 1 + i, HorizontalListView.this.mAdapter.getItemId(HorizontalListView.this.mLeftViewIndex + 1 + i));
            }
            if (HorizontalListView.this.mOnItemSelected == null) break;
            HorizontalListView.this.mOnItemSelected.onItemSelected((AdapterView)HorizontalListView.this, view, HorizontalListView.this.mLeftViewIndex + 1 + i, HorizontalListView.this.mAdapter.getItemId(HorizontalListView.this.mLeftViewIndex + 1 + i));
            return true;
        }
        return true;
    }
}
