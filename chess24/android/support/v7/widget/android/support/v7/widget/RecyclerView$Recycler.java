/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 */
package android.support.v7.widget;

import android.support.annotation.Nullable;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AdapterHelper;
import android.support.v7.widget.ChildHelper;
import android.support.v7.widget.GapWorker;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerViewAccessibilityDelegate;
import android.support.v7.widget.ViewInfoStore;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class RecyclerView.Recycler {
    static final int DEFAULT_CACHE_SIZE = 2;
    final ArrayList<RecyclerView.ViewHolder> mAttachedScrap = new ArrayList();
    final ArrayList<RecyclerView.ViewHolder> mCachedViews = new ArrayList();
    ArrayList<RecyclerView.ViewHolder> mChangedScrap = null;
    RecyclerView.RecycledViewPool mRecyclerPool;
    private int mRequestedCacheMax = 2;
    private final List<RecyclerView.ViewHolder> mUnmodifiableAttachedScrap = Collections.unmodifiableList(this.mAttachedScrap);
    private RecyclerView.ViewCacheExtension mViewCacheExtension;
    int mViewCacheMax = 2;

    private void attachAccessibilityDelegateOnBind(RecyclerView.ViewHolder viewHolder) {
        if (RecyclerView.this.isAccessibilityEnabled()) {
            View view = viewHolder.itemView;
            if (ViewCompat.getImportantForAccessibility(view) == 0) {
                ViewCompat.setImportantForAccessibility(view, 1);
            }
            if (!ViewCompat.hasAccessibilityDelegate(view)) {
                viewHolder.addFlags(16384);
                ViewCompat.setAccessibilityDelegate(view, RecyclerView.this.mAccessibilityDelegate.getItemDelegate());
            }
        }
    }

    private void invalidateDisplayListInt(RecyclerView.ViewHolder viewHolder) {
        if (viewHolder.itemView instanceof ViewGroup) {
            this.invalidateDisplayListInt((ViewGroup)viewHolder.itemView, false);
        }
    }

    private void invalidateDisplayListInt(ViewGroup viewGroup, boolean bl) {
        int n;
        for (n = viewGroup.getChildCount() - 1; n >= 0; --n) {
            View view = viewGroup.getChildAt(n);
            if (!(view instanceof ViewGroup)) continue;
            this.invalidateDisplayListInt((ViewGroup)view, true);
        }
        if (!bl) {
            return;
        }
        if (viewGroup.getVisibility() == 4) {
            viewGroup.setVisibility(0);
            viewGroup.setVisibility(4);
            return;
        }
        n = viewGroup.getVisibility();
        viewGroup.setVisibility(4);
        viewGroup.setVisibility(n);
    }

    private boolean tryBindViewHolderByDeadline(RecyclerView.ViewHolder viewHolder, int n, int n2, long l) {
        viewHolder.mOwnerRecyclerView = RecyclerView.this;
        int n3 = viewHolder.getItemViewType();
        long l2 = RecyclerView.this.getNanoTime();
        if (l != Long.MAX_VALUE && !this.mRecyclerPool.willBindInTime(n3, l2, l)) {
            return false;
        }
        RecyclerView.this.mAdapter.bindViewHolder(viewHolder, n);
        l = RecyclerView.this.getNanoTime();
        this.mRecyclerPool.factorInBindTime(viewHolder.getItemViewType(), l - l2);
        this.attachAccessibilityDelegateOnBind(viewHolder);
        if (RecyclerView.this.mState.isPreLayout()) {
            viewHolder.mPreLayoutPosition = n2;
        }
        return true;
    }

    void addViewHolderToRecycledViewPool(RecyclerView.ViewHolder viewHolder, boolean bl) {
        RecyclerView.clearNestedRecyclerViewIfNotNested(viewHolder);
        if (viewHolder.hasAnyOfTheFlags(16384)) {
            viewHolder.setFlags(0, 16384);
            ViewCompat.setAccessibilityDelegate(viewHolder.itemView, null);
        }
        if (bl) {
            this.dispatchViewRecycled(viewHolder);
        }
        viewHolder.mOwnerRecyclerView = null;
        this.getRecycledViewPool().putRecycledView(viewHolder);
    }

    public void bindViewToPosition(View object, int n) {
        RecyclerView.ViewHolder viewHolder = RecyclerView.getChildViewHolderInt((View)object);
        if (viewHolder == null) {
            object = new StringBuilder();
            object.append("The view does not have a ViewHolder. You cannot pass arbitrary views to this method, they should be created by the Adapter");
            object.append(RecyclerView.this.exceptionLabel());
            throw new IllegalArgumentException(object.toString());
        }
        int n2 = RecyclerView.this.mAdapterHelper.findPositionOffset(n);
        if (n2 >= 0 && n2 < RecyclerView.this.mAdapter.getItemCount()) {
            this.tryBindViewHolderByDeadline(viewHolder, n2, n, Long.MAX_VALUE);
            object = viewHolder.itemView.getLayoutParams();
            if (object == null) {
                object = (RecyclerView.LayoutParams)RecyclerView.this.generateDefaultLayoutParams();
                viewHolder.itemView.setLayoutParams((ViewGroup.LayoutParams)object);
            } else if (!RecyclerView.this.checkLayoutParams((ViewGroup.LayoutParams)object)) {
                object = (RecyclerView.LayoutParams)RecyclerView.this.generateLayoutParams((ViewGroup.LayoutParams)object);
                viewHolder.itemView.setLayoutParams((ViewGroup.LayoutParams)object);
            } else {
                object = (RecyclerView.LayoutParams)((Object)object);
            }
            boolean bl = true;
            object.mInsetsDirty = true;
            object.mViewHolder = viewHolder;
            if (viewHolder.itemView.getParent() != null) {
                bl = false;
            }
            object.mPendingInvalidate = bl;
            return;
        }
        object = new StringBuilder();
        object.append("Inconsistency detected. Invalid item position ");
        object.append(n);
        object.append("(offset:");
        object.append(n2);
        object.append(").");
        object.append("state:");
        object.append(RecyclerView.this.mState.getItemCount());
        object.append(RecyclerView.this.exceptionLabel());
        throw new IndexOutOfBoundsException(object.toString());
    }

    public void clear() {
        this.mAttachedScrap.clear();
        this.recycleAndClearCachedViews();
    }

    void clearOldPositions() {
        int n;
        int n2 = this.mCachedViews.size();
        int n3 = 0;
        for (n = 0; n < n2; ++n) {
            this.mCachedViews.get(n).clearOldPosition();
        }
        n2 = this.mAttachedScrap.size();
        for (n = 0; n < n2; ++n) {
            this.mAttachedScrap.get(n).clearOldPosition();
        }
        if (this.mChangedScrap != null) {
            n2 = this.mChangedScrap.size();
            for (n = n3; n < n2; ++n) {
                this.mChangedScrap.get(n).clearOldPosition();
            }
        }
    }

    void clearScrap() {
        this.mAttachedScrap.clear();
        if (this.mChangedScrap != null) {
            this.mChangedScrap.clear();
        }
    }

    public int convertPreLayoutPositionToPostLayout(int n) {
        if (n >= 0 && n < RecyclerView.this.mState.getItemCount()) {
            if (!RecyclerView.this.mState.isPreLayout()) {
                return n;
            }
            return RecyclerView.this.mAdapterHelper.findPositionOffset(n);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("invalid position ");
        stringBuilder.append(n);
        stringBuilder.append(". State ");
        stringBuilder.append("item count is ");
        stringBuilder.append(RecyclerView.this.mState.getItemCount());
        stringBuilder.append(RecyclerView.this.exceptionLabel());
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    void dispatchViewRecycled(RecyclerView.ViewHolder viewHolder) {
        if (RecyclerView.this.mRecyclerListener != null) {
            RecyclerView.this.mRecyclerListener.onViewRecycled(viewHolder);
        }
        if (RecyclerView.this.mAdapter != null) {
            RecyclerView.this.mAdapter.onViewRecycled(viewHolder);
        }
        if (RecyclerView.this.mState != null) {
            RecyclerView.this.mViewInfoStore.removeViewHolder(viewHolder);
        }
    }

    RecyclerView.ViewHolder getChangedScrapViewForPosition(int n) {
        if (this.mChangedScrap != null) {
            RecyclerView.ViewHolder viewHolder;
            int n2 = this.mChangedScrap.size();
            if (n2 == 0) {
                return null;
            }
            int n3 = 0;
            for (int i = 0; i < n2; ++i) {
                viewHolder = this.mChangedScrap.get(i);
                if (viewHolder.wasReturnedFromScrap() || viewHolder.getLayoutPosition() != n) continue;
                viewHolder.addFlags(32);
                return viewHolder;
            }
            if (RecyclerView.this.mAdapter.hasStableIds() && (n = RecyclerView.this.mAdapterHelper.findPositionOffset(n)) > 0 && n < RecyclerView.this.mAdapter.getItemCount()) {
                long l = RecyclerView.this.mAdapter.getItemId(n);
                for (n = n3; n < n2; ++n) {
                    viewHolder = this.mChangedScrap.get(n);
                    if (viewHolder.wasReturnedFromScrap() || viewHolder.getItemId() != l) continue;
                    viewHolder.addFlags(32);
                    return viewHolder;
                }
            }
            return null;
        }
        return null;
    }

    RecyclerView.RecycledViewPool getRecycledViewPool() {
        if (this.mRecyclerPool == null) {
            this.mRecyclerPool = new RecyclerView.RecycledViewPool();
        }
        return this.mRecyclerPool;
    }

    int getScrapCount() {
        return this.mAttachedScrap.size();
    }

    public List<RecyclerView.ViewHolder> getScrapList() {
        return this.mUnmodifiableAttachedScrap;
    }

    RecyclerView.ViewHolder getScrapOrCachedViewForId(long l, int n, boolean bl) {
        RecyclerView.ViewHolder viewHolder;
        int n2;
        for (n2 = this.mAttachedScrap.size() - 1; n2 >= 0; --n2) {
            viewHolder = this.mAttachedScrap.get(n2);
            if (viewHolder.getItemId() != l || viewHolder.wasReturnedFromScrap()) continue;
            if (n == viewHolder.getItemViewType()) {
                viewHolder.addFlags(32);
                if (viewHolder.isRemoved() && !RecyclerView.this.mState.isPreLayout()) {
                    viewHolder.setFlags(2, 14);
                }
                return viewHolder;
            }
            if (bl) continue;
            this.mAttachedScrap.remove(n2);
            RecyclerView.this.removeDetachedView(viewHolder.itemView, false);
            this.quickRecycleScrapView(viewHolder.itemView);
        }
        for (n2 = this.mCachedViews.size() - 1; n2 >= 0; --n2) {
            viewHolder = this.mCachedViews.get(n2);
            if (viewHolder.getItemId() != l) continue;
            if (n == viewHolder.getItemViewType()) {
                if (!bl) {
                    this.mCachedViews.remove(n2);
                }
                return viewHolder;
            }
            if (bl) continue;
            this.recycleCachedViewAt(n2);
            return null;
        }
        return null;
    }

    RecyclerView.ViewHolder getScrapOrHiddenOrCachedHolderForPosition(int n, boolean bl) {
        int n2;
        Object object;
        RecyclerView.ViewHolder viewHolder;
        int n3 = this.mAttachedScrap.size();
        int n4 = 0;
        for (n2 = 0; n2 < n3; ++n2) {
            viewHolder = this.mAttachedScrap.get(n2);
            if (viewHolder.wasReturnedFromScrap() || viewHolder.getLayoutPosition() != n || viewHolder.isInvalid() || !RecyclerView.this.mState.mInPreLayout && viewHolder.isRemoved()) continue;
            viewHolder.addFlags(32);
            return viewHolder;
        }
        if (!bl && (object = RecyclerView.this.mChildHelper.findHiddenNonRemovedView(n)) != null) {
            viewHolder = RecyclerView.getChildViewHolderInt((View)object);
            RecyclerView.this.mChildHelper.unhide((View)object);
            n = RecyclerView.this.mChildHelper.indexOfChild((View)object);
            if (n == -1) {
                object = new StringBuilder();
                object.append("layout index should not be -1 after unhiding a view:");
                object.append(viewHolder);
                object.append(RecyclerView.this.exceptionLabel());
                throw new IllegalStateException(object.toString());
            }
            RecyclerView.this.mChildHelper.detachViewFromParent(n);
            this.scrapView((View)object);
            viewHolder.addFlags(8224);
            return viewHolder;
        }
        n3 = this.mCachedViews.size();
        for (n2 = n4; n2 < n3; ++n2) {
            viewHolder = this.mCachedViews.get(n2);
            if (viewHolder.isInvalid() || viewHolder.getLayoutPosition() != n) continue;
            if (!bl) {
                this.mCachedViews.remove(n2);
            }
            return viewHolder;
        }
        return null;
    }

    View getScrapViewAt(int n) {
        return this.mAttachedScrap.get((int)n).itemView;
    }

    public View getViewForPosition(int n) {
        return this.getViewForPosition(n, false);
    }

    View getViewForPosition(int n, boolean bl) {
        return this.tryGetViewHolderForPositionByDeadline((int)n, (boolean)bl, (long)Long.MAX_VALUE).itemView;
    }

    void markItemDecorInsetsDirty() {
        int n = this.mCachedViews.size();
        for (int i = 0; i < n; ++i) {
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)this.mCachedViews.get((int)i).itemView.getLayoutParams();
            if (layoutParams == null) continue;
            layoutParams.mInsetsDirty = true;
        }
    }

    void markKnownViewsInvalid() {
        int n = this.mCachedViews.size();
        for (int i = 0; i < n; ++i) {
            RecyclerView.ViewHolder viewHolder = this.mCachedViews.get(i);
            if (viewHolder == null) continue;
            viewHolder.addFlags(6);
            viewHolder.addChangePayload(null);
        }
        if (RecyclerView.this.mAdapter == null || !RecyclerView.this.mAdapter.hasStableIds()) {
            this.recycleAndClearCachedViews();
        }
    }

    void offsetPositionRecordsForInsert(int n, int n2) {
        int n3 = this.mCachedViews.size();
        for (int i = 0; i < n3; ++i) {
            RecyclerView.ViewHolder viewHolder = this.mCachedViews.get(i);
            if (viewHolder == null || viewHolder.mPosition < n) continue;
            viewHolder.offsetPosition(n2, true);
        }
    }

    void offsetPositionRecordsForMove(int n, int n2) {
        int n3;
        int n4;
        int n5;
        if (n < n2) {
            n4 = n2;
            n5 = -1;
            n3 = n;
        } else {
            n4 = n;
            n5 = 1;
            n3 = n2;
        }
        int n6 = this.mCachedViews.size();
        for (int i = 0; i < n6; ++i) {
            RecyclerView.ViewHolder viewHolder = this.mCachedViews.get(i);
            if (viewHolder == null || viewHolder.mPosition < n3 || viewHolder.mPosition > n4) continue;
            if (viewHolder.mPosition == n) {
                viewHolder.offsetPosition(n2 - n, false);
                continue;
            }
            viewHolder.offsetPosition(n5, false);
        }
    }

    void offsetPositionRecordsForRemove(int n, int n2, boolean bl) {
        for (int i = this.mCachedViews.size() - 1; i >= 0; --i) {
            RecyclerView.ViewHolder viewHolder = this.mCachedViews.get(i);
            if (viewHolder == null) continue;
            if (viewHolder.mPosition >= n + n2) {
                viewHolder.offsetPosition(- n2, bl);
                continue;
            }
            if (viewHolder.mPosition < n) continue;
            viewHolder.addFlags(8);
            this.recycleCachedViewAt(i);
        }
    }

    void onAdapterChanged(RecyclerView.Adapter adapter, RecyclerView.Adapter adapter2, boolean bl) {
        this.clear();
        this.getRecycledViewPool().onAdapterChanged(adapter, adapter2, bl);
    }

    void quickRecycleScrapView(View object) {
        object = RecyclerView.getChildViewHolderInt((View)object);
        ((RecyclerView.ViewHolder)object).mScrapContainer = null;
        ((RecyclerView.ViewHolder)object).mInChangeScrap = false;
        object.clearReturnedFromScrapFlag();
        this.recycleViewHolderInternal((RecyclerView.ViewHolder)object);
    }

    void recycleAndClearCachedViews() {
        for (int i = this.mCachedViews.size() - 1; i >= 0; --i) {
            this.recycleCachedViewAt(i);
        }
        this.mCachedViews.clear();
        if (ALLOW_THREAD_GAP_WORK) {
            RecyclerView.this.mPrefetchRegistry.clearPrefetchPositions();
        }
    }

    void recycleCachedViewAt(int n) {
        this.addViewHolderToRecycledViewPool(this.mCachedViews.get(n), true);
        this.mCachedViews.remove(n);
    }

    public void recycleView(View view) {
        RecyclerView.ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
        if (viewHolder.isTmpDetached()) {
            RecyclerView.this.removeDetachedView(view, false);
        }
        if (viewHolder.isScrap()) {
            viewHolder.unScrap();
        } else if (viewHolder.wasReturnedFromScrap()) {
            viewHolder.clearReturnedFromScrapFlag();
        }
        this.recycleViewHolderInternal(viewHolder);
    }

    void recycleViewHolderInternal(RecyclerView.ViewHolder object) {
        boolean bl = object.isScrap();
        boolean bl2 = false;
        int n = 0;
        if (!bl && object.itemView.getParent() == null) {
            int n2;
            if (object.isTmpDetached()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Tmp detached view should be removed from RecyclerView before it can be recycled: ");
                stringBuilder.append(object);
                stringBuilder.append(RecyclerView.this.exceptionLabel());
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            if (object.shouldIgnore()) {
                object = new StringBuilder();
                object.append("Trying to recycle an ignored view holder. You should first call stopIgnoringView(view) before calling recycle.");
                object.append(RecyclerView.this.exceptionLabel());
                throw new IllegalArgumentException(object.toString());
            }
            bl2 = ((RecyclerView.ViewHolder)object).doesTransientStatePreventRecycling();
            int n3 = RecyclerView.this.mAdapter != null && bl2 && RecyclerView.this.mAdapter.onFailedToRecycleView(object) ? 1 : 0;
            if (n3 == 0 && !object.isRecyclable()) {
                n3 = 0;
                n2 = n;
                n = n3;
            } else {
                if (this.mViewCacheMax > 0 && !object.hasAnyOfTheFlags(526)) {
                    n3 = n2 = this.mCachedViews.size();
                    if (n2 >= this.mViewCacheMax) {
                        n3 = n2;
                        if (n2 > 0) {
                            this.recycleCachedViewAt(0);
                            n3 = n2 - 1;
                        }
                    }
                    n2 = n3;
                    if (ALLOW_THREAD_GAP_WORK) {
                        n2 = n3;
                        if (n3 > 0) {
                            n2 = n3--;
                            if (!RecyclerView.this.mPrefetchRegistry.lastPrefetchIncludedPosition(object.mPosition)) {
                                while (n3 >= 0 && RecyclerView.this.mPrefetchRegistry.lastPrefetchIncludedPosition(n2 = this.mCachedViews.get((int)n3).mPosition)) {
                                    --n3;
                                }
                                n2 = n3 + 1;
                            }
                        }
                    }
                    this.mCachedViews.add(n2, (RecyclerView.ViewHolder)object);
                    n3 = 1;
                } else {
                    n3 = 0;
                }
                n2 = n;
                n = n3;
                if (n3 == 0) {
                    this.addViewHolderToRecycledViewPool((RecyclerView.ViewHolder)object, true);
                    n2 = 1;
                    n = n3;
                }
            }
            RecyclerView.this.mViewInfoStore.removeViewHolder((RecyclerView.ViewHolder)object);
            if (n == 0 && n2 == 0 && bl2) {
                object.mOwnerRecyclerView = null;
            }
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Scrapped or attached views may not be recycled. isScrap:");
        stringBuilder.append(object.isScrap());
        stringBuilder.append(" isAttached:");
        if (object.itemView.getParent() != null) {
            bl2 = true;
        }
        stringBuilder.append(bl2);
        stringBuilder.append(RecyclerView.this.exceptionLabel());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    void recycleViewInternal(View view) {
        this.recycleViewHolderInternal(RecyclerView.getChildViewHolderInt(view));
    }

    void scrapView(View object) {
        if (!(object = RecyclerView.getChildViewHolderInt((View)object)).hasAnyOfTheFlags(12) && object.isUpdated() && !RecyclerView.this.canReuseUpdatedViewHolder((RecyclerView.ViewHolder)object)) {
            if (this.mChangedScrap == null) {
                this.mChangedScrap = new ArrayList();
            }
            object.setScrapContainer(this, true);
            this.mChangedScrap.add((RecyclerView.ViewHolder)object);
            return;
        }
        if (object.isInvalid() && !object.isRemoved() && !RecyclerView.this.mAdapter.hasStableIds()) {
            object = new StringBuilder();
            object.append("Called scrap view with an invalid view. Invalid views cannot be reused from scrap, they should rebound from recycler pool.");
            object.append(RecyclerView.this.exceptionLabel());
            throw new IllegalArgumentException(object.toString());
        }
        object.setScrapContainer(this, false);
        this.mAttachedScrap.add((RecyclerView.ViewHolder)object);
    }

    void setRecycledViewPool(RecyclerView.RecycledViewPool recycledViewPool) {
        if (this.mRecyclerPool != null) {
            this.mRecyclerPool.detach();
        }
        this.mRecyclerPool = recycledViewPool;
        if (recycledViewPool != null) {
            this.mRecyclerPool.attach(RecyclerView.this.getAdapter());
        }
    }

    void setViewCacheExtension(RecyclerView.ViewCacheExtension viewCacheExtension) {
        this.mViewCacheExtension = viewCacheExtension;
    }

    public void setViewCacheSize(int n) {
        this.mRequestedCacheMax = n;
        this.updateViewCacheSize();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Nullable
    RecyclerView.ViewHolder tryGetViewHolderForPositionByDeadline(int var1_1, boolean var2_2, long var3_3) {
        block28 : {
            block35 : {
                block34 : {
                    block32 : {
                        block33 : {
                            block31 : {
                                block30 : {
                                    block29 : {
                                        if (var1_1 < 0 || var1_1 >= RecyclerView.this.mState.getItemCount()) break block28;
                                        var10_4 = RecyclerView.this.mState.isPreLayout();
                                        var9_5 = true;
                                        if (!var10_4) break block29;
                                        var15_7 = var16_6 = this.getChangedScrapViewForPosition(var1_1);
                                        if (var16_6 == null) break block30;
                                        var6_9 = 1;
                                        break block31;
                                    }
                                    var15_7 = null;
                                }
                                var6_9 = 0;
                                var16_6 = var15_7;
                            }
                            var15_7 = var16_6;
                            var5_10 = var6_9;
                            if (var16_6 == null) {
                                var15_7 = var16_6 = this.getScrapOrHiddenOrCachedHolderForPosition(var1_1, var2_2);
                                var5_10 = var6_9;
                                if (var16_6 != null) {
                                    if (!this.validateViewHolderForOffsetPosition((RecyclerView.ViewHolder)var16_6)) {
                                        if (!var2_2) {
                                            var16_6.addFlags(4);
                                            if (var16_6.isScrap()) {
                                                RecyclerView.this.removeDetachedView(var16_6.itemView, false);
                                                var16_6.unScrap();
                                            } else if (var16_6.wasReturnedFromScrap()) {
                                                var16_6.clearReturnedFromScrapFlag();
                                            }
                                            this.recycleViewHolderInternal((RecyclerView.ViewHolder)var16_6);
                                        }
                                        var15_7 = null;
                                        var5_10 = var6_9;
                                    } else {
                                        var5_10 = 1;
                                        var15_7 = var16_6;
                                    }
                                }
                            }
                            var17_11 = var15_7;
                            var7_12 = var5_10;
                            if (var15_7 != null) break block32;
                            var7_12 = RecyclerView.this.mAdapterHelper.findPositionOffset(var1_1);
                            if (var7_12 < 0 || var7_12 >= RecyclerView.this.mAdapter.getItemCount()) break block33;
                            var8_13 = RecyclerView.this.mAdapter.getItemViewType(var7_12);
                            var16_6 = var15_7;
                            var6_9 = var5_10;
                            if (RecyclerView.this.mAdapter.hasStableIds()) {
                                var16_6 = var15_7 = this.getScrapOrCachedViewForId(RecyclerView.this.mAdapter.getItemId(var7_12), var8_13, var2_2);
                                var6_9 = var5_10;
                                if (var15_7 != null) {
                                    var15_7.mPosition = var7_12;
                                    var6_9 = 1;
                                    var16_6 = var15_7;
                                }
                            }
                            var15_7 = var16_6;
                            if (var16_6 == null) {
                                var15_7 = var16_6;
                                if (this.mViewCacheExtension != null) {
                                    var17_11 = this.mViewCacheExtension.getViewForPositionAndType(this, var1_1, var8_13);
                                    var15_7 = var16_6;
                                    if (var17_11 != null) {
                                        var16_6 = RecyclerView.this.getChildViewHolder((View)var17_11);
                                        if (var16_6 == null) {
                                            var15_7 = new StringBuilder();
                                            var15_7.append("getViewForPositionAndType returned a view which does not have a ViewHolder");
                                            var15_7.append(RecyclerView.this.exceptionLabel());
                                            throw new IllegalArgumentException(var15_7.toString());
                                        }
                                        var15_7 = var16_6;
                                        if (var16_6.shouldIgnore()) {
                                            var15_7 = new StringBuilder();
                                            var15_7.append("getViewForPositionAndType returned a view that is ignored. You must call stopIgnoring before returning this view.");
                                            var15_7.append(RecyclerView.this.exceptionLabel());
                                            throw new IllegalArgumentException(var15_7.toString());
                                        }
                                    }
                                }
                            }
                            var16_6 = var15_7;
                            if (var15_7 == null) {
                                var16_6 = var15_7 = this.getRecycledViewPool().getRecycledView(var8_13);
                                if (var15_7 != null) {
                                    var15_7.resetInternal();
                                    var16_6 = var15_7;
                                    if (RecyclerView.FORCE_INVALIDATE_DISPLAY_LIST) {
                                        this.invalidateDisplayListInt((RecyclerView.ViewHolder)var15_7);
                                        var16_6 = var15_7;
                                    }
                                }
                            }
                            var17_11 = var16_6;
                            var7_12 = var6_9;
                            if (var16_6 != null) break block32;
                            var11_14 = RecyclerView.this.getNanoTime();
                            if (var3_3 != Long.MAX_VALUE && !this.mRecyclerPool.willCreateInTime(var8_13, var11_14, var3_3)) {
                                return null;
                            }
                            var16_6 = RecyclerView.this.mAdapter.createViewHolder(RecyclerView.this, var8_13);
                            if (RecyclerView.access$800() && (var15_7 = RecyclerView.findNestedRecyclerView(var16_6.itemView)) != null) {
                                var16_6.mNestedRecyclerView = new WeakReference<Object>(var15_7);
                            }
                            var13_15 = RecyclerView.this.getNanoTime();
                            this.mRecyclerPool.factorInCreateTime(var8_13, var13_15 - var11_14);
                            break block34;
                        }
                        var15_7 = new StringBuilder();
                        var15_7.append("Inconsistency detected. Invalid item position ");
                        var15_7.append(var1_1);
                        var15_7.append("(offset:");
                        var15_7.append(var7_12);
                        var15_7.append(").");
                        var15_7.append("state:");
                        var15_7.append(RecyclerView.this.mState.getItemCount());
                        var15_7.append(RecyclerView.this.exceptionLabel());
                        throw new IndexOutOfBoundsException(var15_7.toString());
                    }
                    var16_6 = var17_11;
                    var6_9 = var7_12;
                }
                if (var6_9 != 0 && !RecyclerView.this.mState.isPreLayout() && var16_6.hasAnyOfTheFlags(8192)) {
                    var16_6.setFlags(0, 8192);
                    if (RecyclerView.this.mState.mRunSimpleAnimations) {
                        var5_10 = RecyclerView.ItemAnimator.buildAdapterChangeFlagsForAnimations((RecyclerView.ViewHolder)var16_6);
                        var15_7 = RecyclerView.this.mItemAnimator.recordPreLayoutInformation(RecyclerView.this.mState, (RecyclerView.ViewHolder)var16_6, var5_10 | 4096, var16_6.getUnmodifiedPayloads());
                        RecyclerView.this.recordAnimationInfoIfBouncedHiddenView((RecyclerView.ViewHolder)var16_6, (RecyclerView.ItemAnimator.ItemHolderInfo)var15_7);
                    }
                }
                if (!RecyclerView.this.mState.isPreLayout() || !var16_6.isBound()) break block35;
                var16_6.mPreLayoutPosition = var1_1;
                ** GOTO lbl-1000
            }
            if (var16_6.isBound() && !var16_6.needsUpdate() && !var16_6.isInvalid()) lbl-1000: // 2 sources:
            {
                var2_2 = false;
            } else {
                var2_2 = this.tryBindViewHolderByDeadline((RecyclerView.ViewHolder)var16_6, RecyclerView.this.mAdapterHelper.findPositionOffset(var1_1), var1_1, var3_3);
            }
            var15_7 = var16_6.itemView.getLayoutParams();
            if (var15_7 == null) {
                var15_7 = (RecyclerView.LayoutParams)RecyclerView.this.generateDefaultLayoutParams();
                var16_6.itemView.setLayoutParams((ViewGroup.LayoutParams)var15_7);
            } else if (!RecyclerView.this.checkLayoutParams((ViewGroup.LayoutParams)var15_7)) {
                var15_7 = (RecyclerView.LayoutParams)RecyclerView.this.generateLayoutParams((ViewGroup.LayoutParams)var15_7);
                var16_6.itemView.setLayoutParams((ViewGroup.LayoutParams)var15_7);
            } else {
                var15_7 = (RecyclerView.LayoutParams)var15_7;
            }
            var15_7.mViewHolder = var16_6;
            var2_2 = var6_9 != 0 && var2_2 != false ? var9_5 : false;
            var15_7.mPendingInvalidate = var2_2;
            return var16_6;
        }
        var15_8 = new StringBuilder();
        var15_8.append("Invalid item position ");
        var15_8.append(var1_1);
        var15_8.append("(");
        var15_8.append(var1_1);
        var15_8.append("). Item count:");
        var15_8.append(RecyclerView.this.mState.getItemCount());
        var15_8.append(RecyclerView.this.exceptionLabel());
        throw new IndexOutOfBoundsException(var15_8.toString());
    }

    void unscrapView(RecyclerView.ViewHolder viewHolder) {
        if (viewHolder.mInChangeScrap) {
            this.mChangedScrap.remove(viewHolder);
        } else {
            this.mAttachedScrap.remove(viewHolder);
        }
        viewHolder.mScrapContainer = null;
        viewHolder.mInChangeScrap = false;
        viewHolder.clearReturnedFromScrapFlag();
    }

    void updateViewCacheSize() {
        int n = RecyclerView.this.mLayout != null ? RecyclerView.this.mLayout.mPrefetchMaxCountObserved : 0;
        this.mViewCacheMax = this.mRequestedCacheMax + n;
        for (n = this.mCachedViews.size() - 1; n >= 0 && this.mCachedViews.size() > this.mViewCacheMax; --n) {
            this.recycleCachedViewAt(n);
        }
    }

    boolean validateViewHolderForOffsetPosition(RecyclerView.ViewHolder viewHolder) {
        if (viewHolder.isRemoved()) {
            return RecyclerView.this.mState.isPreLayout();
        }
        if (viewHolder.mPosition >= 0 && viewHolder.mPosition < RecyclerView.this.mAdapter.getItemCount()) {
            boolean bl = RecyclerView.this.mState.isPreLayout();
            boolean bl2 = false;
            if (!bl && RecyclerView.this.mAdapter.getItemViewType(viewHolder.mPosition) != viewHolder.getItemViewType()) {
                return false;
            }
            if (RecyclerView.this.mAdapter.hasStableIds()) {
                if (viewHolder.getItemId() == RecyclerView.this.mAdapter.getItemId(viewHolder.mPosition)) {
                    bl2 = true;
                }
                return bl2;
            }
            return true;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Inconsistency detected. Invalid view holder adapter position");
        stringBuilder.append(viewHolder);
        stringBuilder.append(RecyclerView.this.exceptionLabel());
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    void viewRangeUpdate(int n, int n2) {
        for (int i = this.mCachedViews.size() - 1; i >= 0; --i) {
            int n3;
            RecyclerView.ViewHolder viewHolder = this.mCachedViews.get(i);
            if (viewHolder == null || (n3 = viewHolder.mPosition) < n || n3 >= n2 + n) continue;
            viewHolder.addFlags(2);
            this.recycleCachedViewAt(i);
        }
    }
}
