/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.v7.widget;

import android.support.v7.widget.RecyclerView;
import android.view.View;

private class RecyclerView.ItemAnimatorRestoreListener
implements RecyclerView.ItemAnimator.ItemAnimatorListener {
    RecyclerView.ItemAnimatorRestoreListener() {
    }

    @Override
    public void onAnimationFinished(RecyclerView.ViewHolder viewHolder) {
        viewHolder.setIsRecyclable(true);
        if (viewHolder.mShadowedHolder != null && viewHolder.mShadowingHolder == null) {
            viewHolder.mShadowedHolder = null;
        }
        viewHolder.mShadowingHolder = null;
        if (!viewHolder.shouldBeKeptAsChild() && !RecyclerView.this.removeAnimatingView(viewHolder.itemView) && viewHolder.isTmpDetached()) {
            RecyclerView.this.removeDetachedView(viewHolder.itemView, false);
        }
    }
}
