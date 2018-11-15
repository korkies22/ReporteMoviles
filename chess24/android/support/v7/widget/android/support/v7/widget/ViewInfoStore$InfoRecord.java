/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.widget;

import android.support.annotation.Nullable;
import android.support.v4.util.Pools;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ViewInfoStore;

static class ViewInfoStore.InfoRecord {
    static final int FLAG_APPEAR = 2;
    static final int FLAG_APPEAR_AND_DISAPPEAR = 3;
    static final int FLAG_APPEAR_PRE_AND_POST = 14;
    static final int FLAG_DISAPPEARED = 1;
    static final int FLAG_POST = 8;
    static final int FLAG_PRE = 4;
    static final int FLAG_PRE_AND_POST = 12;
    static Pools.Pool<ViewInfoStore.InfoRecord> sPool = new Pools.SimplePool<ViewInfoStore.InfoRecord>(20);
    int flags;
    @Nullable
    RecyclerView.ItemAnimator.ItemHolderInfo postInfo;
    @Nullable
    RecyclerView.ItemAnimator.ItemHolderInfo preInfo;

    private ViewInfoStore.InfoRecord() {
    }

    static void drainCache() {
        while (sPool.acquire() != null) {
        }
    }

    static ViewInfoStore.InfoRecord obtain() {
        ViewInfoStore.InfoRecord infoRecord;
        ViewInfoStore.InfoRecord infoRecord2 = infoRecord = sPool.acquire();
        if (infoRecord == null) {
            infoRecord2 = new ViewInfoStore.InfoRecord();
        }
        return infoRecord2;
    }

    static void recycle(ViewInfoStore.InfoRecord infoRecord) {
        infoRecord.flags = 0;
        infoRecord.preInfo = null;
        infoRecord.postInfo = null;
        sPool.release(infoRecord);
    }
}
