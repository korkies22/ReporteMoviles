/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.v7.widget;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

public static abstract class RecyclerView.ItemAnimator {
    public static final int FLAG_APPEARED_IN_PRE_LAYOUT = 4096;
    public static final int FLAG_CHANGED = 2;
    public static final int FLAG_INVALIDATED = 4;
    public static final int FLAG_MOVED = 2048;
    public static final int FLAG_REMOVED = 8;
    private long mAddDuration = 120L;
    private long mChangeDuration = 250L;
    private ArrayList<ItemAnimatorFinishedListener> mFinishedListeners = new ArrayList();
    private ItemAnimatorListener mListener = null;
    private long mMoveDuration = 250L;
    private long mRemoveDuration = 120L;

    static int buildAdapterChangeFlagsForAnimations(RecyclerView.ViewHolder viewHolder) {
        int n = viewHolder.mFlags & 14;
        if (viewHolder.isInvalid()) {
            return 4;
        }
        int n2 = n;
        if ((n & 4) == 0) {
            int n3 = viewHolder.getOldPosition();
            int n4 = viewHolder.getAdapterPosition();
            n2 = n;
            if (n3 != -1) {
                n2 = n;
                if (n4 != -1) {
                    n2 = n;
                    if (n3 != n4) {
                        n2 = n | 2048;
                    }
                }
            }
        }
        return n2;
    }

    public abstract boolean animateAppearance(@NonNull RecyclerView.ViewHolder var1, @Nullable ItemHolderInfo var2, @NonNull ItemHolderInfo var3);

    public abstract boolean animateChange(@NonNull RecyclerView.ViewHolder var1, @NonNull RecyclerView.ViewHolder var2, @NonNull ItemHolderInfo var3, @NonNull ItemHolderInfo var4);

    public abstract boolean animateDisappearance(@NonNull RecyclerView.ViewHolder var1, @NonNull ItemHolderInfo var2, @Nullable ItemHolderInfo var3);

    public abstract boolean animatePersistence(@NonNull RecyclerView.ViewHolder var1, @NonNull ItemHolderInfo var2, @NonNull ItemHolderInfo var3);

    public boolean canReuseUpdatedViewHolder(@NonNull RecyclerView.ViewHolder viewHolder) {
        return true;
    }

    public boolean canReuseUpdatedViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, @NonNull List<Object> list) {
        return this.canReuseUpdatedViewHolder(viewHolder);
    }

    public final void dispatchAnimationFinished(RecyclerView.ViewHolder viewHolder) {
        this.onAnimationFinished(viewHolder);
        if (this.mListener != null) {
            this.mListener.onAnimationFinished(viewHolder);
        }
    }

    public final void dispatchAnimationStarted(RecyclerView.ViewHolder viewHolder) {
        this.onAnimationStarted(viewHolder);
    }

    public final void dispatchAnimationsFinished() {
        int n = this.mFinishedListeners.size();
        for (int i = 0; i < n; ++i) {
            this.mFinishedListeners.get(i).onAnimationsFinished();
        }
        this.mFinishedListeners.clear();
    }

    public abstract void endAnimation(RecyclerView.ViewHolder var1);

    public abstract void endAnimations();

    public long getAddDuration() {
        return this.mAddDuration;
    }

    public long getChangeDuration() {
        return this.mChangeDuration;
    }

    public long getMoveDuration() {
        return this.mMoveDuration;
    }

    public long getRemoveDuration() {
        return this.mRemoveDuration;
    }

    public abstract boolean isRunning();

    public final boolean isRunning(ItemAnimatorFinishedListener itemAnimatorFinishedListener) {
        boolean bl = this.isRunning();
        if (itemAnimatorFinishedListener != null) {
            if (!bl) {
                itemAnimatorFinishedListener.onAnimationsFinished();
                return bl;
            }
            this.mFinishedListeners.add(itemAnimatorFinishedListener);
        }
        return bl;
    }

    public ItemHolderInfo obtainHolderInfo() {
        return new ItemHolderInfo();
    }

    public void onAnimationFinished(RecyclerView.ViewHolder viewHolder) {
    }

    public void onAnimationStarted(RecyclerView.ViewHolder viewHolder) {
    }

    @NonNull
    public ItemHolderInfo recordPostLayoutInformation(@NonNull RecyclerView.State state, @NonNull RecyclerView.ViewHolder viewHolder) {
        return this.obtainHolderInfo().setFrom(viewHolder);
    }

    @NonNull
    public ItemHolderInfo recordPreLayoutInformation(@NonNull RecyclerView.State state, @NonNull RecyclerView.ViewHolder viewHolder, int n, @NonNull List<Object> list) {
        return this.obtainHolderInfo().setFrom(viewHolder);
    }

    public abstract void runPendingAnimations();

    public void setAddDuration(long l) {
        this.mAddDuration = l;
    }

    public void setChangeDuration(long l) {
        this.mChangeDuration = l;
    }

    void setListener(ItemAnimatorListener itemAnimatorListener) {
        this.mListener = itemAnimatorListener;
    }

    public void setMoveDuration(long l) {
        this.mMoveDuration = l;
    }

    public void setRemoveDuration(long l) {
        this.mRemoveDuration = l;
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface AdapterChanges {
    }

    public static interface ItemAnimatorFinishedListener {
        public void onAnimationsFinished();
    }

    static interface ItemAnimatorListener {
        public void onAnimationFinished(RecyclerView.ViewHolder var1);
    }

    public static class ItemHolderInfo {
        public int bottom;
        public int changeFlags;
        public int left;
        public int right;
        public int top;

        public ItemHolderInfo setFrom(RecyclerView.ViewHolder viewHolder) {
            return this.setFrom(viewHolder, 0);
        }

        public ItemHolderInfo setFrom(RecyclerView.ViewHolder viewHolder, int n) {
            viewHolder = viewHolder.itemView;
            this.left = viewHolder.getLeft();
            this.top = viewHolder.getTop();
            this.right = viewHolder.getRight();
            this.bottom = viewHolder.getBottom();
            return this;
        }
    }

}
