/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.MotionEvent
 *  android.view.VelocityTracker
 *  android.view.View
 */
package android.support.v7.widget.helper;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import java.util.List;

class ItemTouchHelper
implements RecyclerView.OnItemTouchListener {
    ItemTouchHelper() {
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView object, MotionEvent motionEvent) {
        ItemTouchHelper.this.mGestureDetector.onTouchEvent(motionEvent);
        int n = motionEvent.getActionMasked();
        if (n == 0) {
            ItemTouchHelper.this.mActivePointerId = motionEvent.getPointerId(0);
            ItemTouchHelper.this.mInitialTouchX = motionEvent.getX();
            ItemTouchHelper.this.mInitialTouchY = motionEvent.getY();
            ItemTouchHelper.this.obtainVelocityTracker();
            if (ItemTouchHelper.this.mSelected == null && (object = ItemTouchHelper.this.findAnimation(motionEvent)) != null) {
                android.support.v7.widget.helper.ItemTouchHelper itemTouchHelper = ItemTouchHelper.this;
                itemTouchHelper.mInitialTouchX -= object.mX;
                itemTouchHelper = ItemTouchHelper.this;
                itemTouchHelper.mInitialTouchY -= object.mY;
                ItemTouchHelper.this.endRecoverAnimation(object.mViewHolder, true);
                if (ItemTouchHelper.this.mPendingCleanup.remove((Object)object.mViewHolder.itemView)) {
                    ItemTouchHelper.this.mCallback.clearView(ItemTouchHelper.this.mRecyclerView, object.mViewHolder);
                }
                ItemTouchHelper.this.select(object.mViewHolder, object.mActionState);
                ItemTouchHelper.this.updateDxDy(motionEvent, ItemTouchHelper.this.mSelectedFlags, 0);
            }
        } else if (n != 3 && n != 1) {
            int n2;
            if (ItemTouchHelper.this.mActivePointerId != -1 && (n2 = motionEvent.findPointerIndex(ItemTouchHelper.this.mActivePointerId)) >= 0) {
                ItemTouchHelper.this.checkSelectForSwipe(n, motionEvent, n2);
            }
        } else {
            ItemTouchHelper.this.mActivePointerId = -1;
            ItemTouchHelper.this.select(null, 0);
        }
        if (ItemTouchHelper.this.mVelocityTracker != null) {
            ItemTouchHelper.this.mVelocityTracker.addMovement(motionEvent);
        }
        if (ItemTouchHelper.this.mSelected != null) {
            return true;
        }
        return false;
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean bl) {
        if (!bl) {
            return;
        }
        ItemTouchHelper.this.select(null, 0);
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public void onTouchEvent(RecyclerView object, MotionEvent motionEvent) {
        ItemTouchHelper.this.mGestureDetector.onTouchEvent(motionEvent);
        if (ItemTouchHelper.this.mVelocityTracker != null) {
            ItemTouchHelper.this.mVelocityTracker.addMovement(motionEvent);
        }
        if (ItemTouchHelper.this.mActivePointerId == -1) {
            return;
        }
        int n = motionEvent.getActionMasked();
        int n2 = motionEvent.findPointerIndex(ItemTouchHelper.this.mActivePointerId);
        if (n2 >= 0) {
            ItemTouchHelper.this.checkSelectForSwipe(n, motionEvent, n2);
        }
        if ((object = ItemTouchHelper.this.mSelected) == null) {
            return;
        }
        int n3 = 0;
        if (n != 6) {
            switch (n) {
                default: {
                    return;
                }
                case 3: {
                    if (ItemTouchHelper.this.mVelocityTracker == null) break;
                    ItemTouchHelper.this.mVelocityTracker.clear();
                    break;
                }
                case 2: {
                    if (n2 < 0) return;
                    ItemTouchHelper.this.updateDxDy(motionEvent, ItemTouchHelper.this.mSelectedFlags, n2);
                    ItemTouchHelper.this.moveIfNecessary((RecyclerView.ViewHolder)object);
                    ItemTouchHelper.this.mRecyclerView.removeCallbacks(ItemTouchHelper.this.mScrollRunnable);
                    ItemTouchHelper.this.mScrollRunnable.run();
                    ItemTouchHelper.this.mRecyclerView.invalidate();
                    return;
                }
                case 1: 
            }
            ItemTouchHelper.this.select(null, 0);
            ItemTouchHelper.this.mActivePointerId = -1;
            return;
        }
        n = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(n) != ItemTouchHelper.this.mActivePointerId) return;
        if (n == 0) {
            n3 = 1;
        }
        ItemTouchHelper.this.mActivePointerId = motionEvent.getPointerId(n3);
        ItemTouchHelper.this.updateDxDy(motionEvent, ItemTouchHelper.this.mSelectedFlags, n);
    }
}
