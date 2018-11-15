/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.GestureDetector
 *  android.view.GestureDetector$SimpleOnGestureListener
 *  android.view.MotionEvent
 *  android.view.View
 */
package android.support.v7.widget.helper;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

private class ItemTouchHelper.ItemTouchHelperGestureListener
extends GestureDetector.SimpleOnGestureListener {
    private boolean mShouldReactToLongPress = true;

    ItemTouchHelper.ItemTouchHelperGestureListener() {
    }

    void doNotReactToLongPress() {
        this.mShouldReactToLongPress = false;
    }

    public boolean onDown(MotionEvent motionEvent) {
        return true;
    }

    public void onLongPress(MotionEvent object) {
        if (!this.mShouldReactToLongPress) {
            return;
        }
        Object object2 = ItemTouchHelper.this.findChildView((MotionEvent)object);
        if (object2 != null && (object2 = ItemTouchHelper.this.mRecyclerView.getChildViewHolder((View)object2)) != null) {
            if (!ItemTouchHelper.this.mCallback.hasDragFlag(ItemTouchHelper.this.mRecyclerView, (RecyclerView.ViewHolder)object2)) {
                return;
            }
            if (object.getPointerId(0) == ItemTouchHelper.this.mActivePointerId) {
                int n = object.findPointerIndex(ItemTouchHelper.this.mActivePointerId);
                float f = object.getX(n);
                float f2 = object.getY(n);
                ItemTouchHelper.this.mInitialTouchX = f;
                ItemTouchHelper.this.mInitialTouchY = f2;
                object = ItemTouchHelper.this;
                ItemTouchHelper.this.mDy = 0.0f;
                object.mDx = 0.0f;
                if (ItemTouchHelper.this.mCallback.isLongPressDragEnabled()) {
                    ItemTouchHelper.this.select((RecyclerView.ViewHolder)object2, 2);
                }
            }
        }
    }
}
