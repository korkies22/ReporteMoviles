/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Rect
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.SparseIntArray
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 */
package android.support.v7.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import java.util.Arrays;
import java.util.List;

public class GridLayoutManager
extends LinearLayoutManager {
    private static final boolean DEBUG = false;
    public static final int DEFAULT_SPAN_COUNT = -1;
    private static final String TAG = "GridLayoutManager";
    int[] mCachedBorders;
    final Rect mDecorInsets = new Rect();
    boolean mPendingSpanCountChange = false;
    final SparseIntArray mPreLayoutSpanIndexCache = new SparseIntArray();
    final SparseIntArray mPreLayoutSpanSizeCache = new SparseIntArray();
    View[] mSet;
    int mSpanCount = -1;
    SpanSizeLookup mSpanSizeLookup = new DefaultSpanSizeLookup();

    public GridLayoutManager(Context context, int n) {
        super(context);
        this.setSpanCount(n);
    }

    public GridLayoutManager(Context context, int n, int n2, boolean bl) {
        super(context, n2, bl);
        this.setSpanCount(n);
    }

    public GridLayoutManager(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
        this.setSpanCount(GridLayoutManager.getProperties((Context)context, (AttributeSet)attributeSet, (int)n, (int)n2).spanCount);
    }

    private void assignSpans(RecyclerView.Recycler recycler, RecyclerView.State state, int n, int n2, boolean bl) {
        int n3;
        n2 = -1;
        int n4 = 0;
        if (bl) {
            n3 = 1;
            n2 = n;
            n = 0;
        } else {
            --n;
            n3 = -1;
        }
        while (n != n2) {
            View view = this.mSet[n];
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            layoutParams.mSpanSize = this.getSpanSize(recycler, state, this.getPosition(view));
            layoutParams.mSpanIndex = n4;
            n4 += layoutParams.mSpanSize;
            n += n3;
        }
    }

    private void cachePreLayoutSpanMapping() {
        int n = this.getChildCount();
        for (int i = 0; i < n; ++i) {
            LayoutParams layoutParams = (LayoutParams)this.getChildAt(i).getLayoutParams();
            int n2 = layoutParams.getViewLayoutPosition();
            this.mPreLayoutSpanSizeCache.put(n2, layoutParams.getSpanSize());
            this.mPreLayoutSpanIndexCache.put(n2, layoutParams.getSpanIndex());
        }
    }

    private void calculateItemBorders(int n) {
        this.mCachedBorders = GridLayoutManager.calculateItemBorders(this.mCachedBorders, this.mSpanCount, n);
    }

    static int[] calculateItemBorders(int[] arrn, int n, int n2) {
        int n3;
        int[] arrn2;
        block7 : {
            block6 : {
                n3 = 1;
                if (arrn == null || arrn.length != n + 1) break block6;
                arrn2 = arrn;
                if (arrn[arrn.length - 1] == n2) break block7;
            }
            arrn2 = new int[n + 1];
        }
        int n4 = 0;
        arrn2[0] = 0;
        int n5 = n2 / n;
        int n6 = n2 % n;
        int n7 = 0;
        n2 = n4;
        while (n3 <= n) {
            if ((n2 += n6) > 0 && n - n2 < n6) {
                n4 = n5 + 1;
                n2 -= n;
            } else {
                n4 = n5;
            }
            arrn2[n3] = n7 += n4;
            ++n3;
        }
        return arrn2;
    }

    private void clearPreLayoutSpanMappingCache() {
        this.mPreLayoutSpanSizeCache.clear();
        this.mPreLayoutSpanIndexCache.clear();
    }

    private void ensureAnchorIsInCorrectSpan(RecyclerView.Recycler recycler, RecyclerView.State state, LinearLayoutManager.AnchorInfo anchorInfo, int n) {
        n = n == 1 ? 1 : 0;
        int n2 = this.getSpanIndex(recycler, state, anchorInfo.mPosition);
        if (n != 0) {
            while (n2 > 0 && anchorInfo.mPosition > 0) {
                --anchorInfo.mPosition;
                n2 = this.getSpanIndex(recycler, state, anchorInfo.mPosition);
            }
        } else {
            int n3;
            int n4;
            int n5 = state.getItemCount();
            n = anchorInfo.mPosition;
            while (n < n5 - 1 && (n3 = this.getSpanIndex(recycler, state, n4 = n + 1)) > n2) {
                n = n4;
                n2 = n3;
            }
            anchorInfo.mPosition = n;
        }
    }

    private void ensureViewSet() {
        if (this.mSet == null || this.mSet.length != this.mSpanCount) {
            this.mSet = new View[this.mSpanCount];
        }
    }

    private int getSpanGroupIndex(RecyclerView.Recycler object, RecyclerView.State state, int n) {
        if (!state.isPreLayout()) {
            return this.mSpanSizeLookup.getSpanGroupIndex(n, this.mSpanCount);
        }
        int n2 = object.convertPreLayoutPositionToPostLayout(n);
        if (n2 == -1) {
            object = new StringBuilder();
            object.append("Cannot find span size for pre layout position. ");
            object.append(n);
            Log.w((String)TAG, (String)object.toString());
            return 0;
        }
        return this.mSpanSizeLookup.getSpanGroupIndex(n2, this.mSpanCount);
    }

    private int getSpanIndex(RecyclerView.Recycler object, RecyclerView.State state, int n) {
        if (!state.isPreLayout()) {
            return this.mSpanSizeLookup.getCachedSpanIndex(n, this.mSpanCount);
        }
        int n2 = this.mPreLayoutSpanIndexCache.get(n, -1);
        if (n2 != -1) {
            return n2;
        }
        n2 = object.convertPreLayoutPositionToPostLayout(n);
        if (n2 == -1) {
            object = new StringBuilder();
            object.append("Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:");
            object.append(n);
            Log.w((String)TAG, (String)object.toString());
            return 0;
        }
        return this.mSpanSizeLookup.getCachedSpanIndex(n2, this.mSpanCount);
    }

    private int getSpanSize(RecyclerView.Recycler object, RecyclerView.State state, int n) {
        if (!state.isPreLayout()) {
            return this.mSpanSizeLookup.getSpanSize(n);
        }
        int n2 = this.mPreLayoutSpanSizeCache.get(n, -1);
        if (n2 != -1) {
            return n2;
        }
        n2 = object.convertPreLayoutPositionToPostLayout(n);
        if (n2 == -1) {
            object = new StringBuilder();
            object.append("Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:");
            object.append(n);
            Log.w((String)TAG, (String)object.toString());
            return 1;
        }
        return this.mSpanSizeLookup.getSpanSize(n2);
    }

    private void guessMeasurement(float f, int n) {
        this.calculateItemBorders(Math.max(Math.round(f * (float)this.mSpanCount), n));
    }

    private void measureChild(View view, int n, boolean bl) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        Rect rect = layoutParams.mDecorInsets;
        int n2 = rect.top + rect.bottom + layoutParams.topMargin + layoutParams.bottomMargin;
        int n3 = rect.left + rect.right + layoutParams.leftMargin + layoutParams.rightMargin;
        int n4 = this.getSpaceForSpanRange(layoutParams.mSpanIndex, layoutParams.mSpanSize);
        if (this.mOrientation == 1) {
            n3 = GridLayoutManager.getChildMeasureSpec(n4, n, n3, layoutParams.width, false);
            n = GridLayoutManager.getChildMeasureSpec(this.mOrientationHelper.getTotalSpace(), this.getHeightMode(), n2, layoutParams.height, true);
        } else {
            n = GridLayoutManager.getChildMeasureSpec(n4, n, n2, layoutParams.height, false);
            n3 = GridLayoutManager.getChildMeasureSpec(this.mOrientationHelper.getTotalSpace(), this.getWidthMode(), n3, layoutParams.width, true);
        }
        this.measureChildWithDecorationsAndMargin(view, n3, n, bl);
    }

    private void measureChildWithDecorationsAndMargin(View view, int n, int n2, boolean bl) {
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)view.getLayoutParams();
        bl = bl ? this.shouldReMeasureChild(view, n, n2, layoutParams) : this.shouldMeasureChild(view, n, n2, layoutParams);
        if (bl) {
            view.measure(n, n2);
        }
    }

    private void updateMeasurements() {
        int n = this.getOrientation() == 1 ? this.getWidth() - this.getPaddingRight() - this.getPaddingLeft() : this.getHeight() - this.getPaddingBottom() - this.getPaddingTop();
        this.calculateItemBorders(n);
    }

    @Override
    public boolean checkLayoutParams(RecyclerView.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    @Override
    void collectPrefetchPositionsForLayoutState(RecyclerView.State state, LinearLayoutManager.LayoutState layoutState, RecyclerView.LayoutManager.LayoutPrefetchRegistry layoutPrefetchRegistry) {
        int n = this.mSpanCount;
        for (int i = 0; i < this.mSpanCount && layoutState.hasMore(state) && n > 0; ++i) {
            int n2 = layoutState.mCurrentPosition;
            layoutPrefetchRegistry.addPosition(n2, Math.max(0, layoutState.mScrollingOffset));
            n -= this.mSpanSizeLookup.getSpanSize(n2);
            layoutState.mCurrentPosition += layoutState.mItemDirection;
        }
    }

    @Override
    View findReferenceChild(RecyclerView.Recycler recycler, RecyclerView.State state, int n, int n2, int n3) {
        this.ensureLayoutState();
        int n4 = this.mOrientationHelper.getStartAfterPadding();
        int n5 = this.mOrientationHelper.getEndAfterPadding();
        int n6 = n2 > n ? 1 : -1;
        View view = null;
        View view2 = null;
        while (n != n2) {
            View view3 = this.getChildAt(n);
            int n7 = this.getPosition(view3);
            View view4 = view;
            View view5 = view2;
            if (n7 >= 0) {
                view4 = view;
                view5 = view2;
                if (n7 < n3) {
                    if (this.getSpanIndex(recycler, state, n7) != 0) {
                        view4 = view;
                        view5 = view2;
                    } else if (((RecyclerView.LayoutParams)view3.getLayoutParams()).isItemRemoved()) {
                        view4 = view;
                        view5 = view2;
                        if (view2 == null) {
                            view5 = view3;
                            view4 = view;
                        }
                    } else {
                        if (this.mOrientationHelper.getDecoratedStart(view3) < n5 && this.mOrientationHelper.getDecoratedEnd(view3) >= n4) {
                            return view3;
                        }
                        view4 = view;
                        view5 = view2;
                        if (view == null) {
                            view4 = view3;
                            view5 = view2;
                        }
                    }
                }
            }
            n += n6;
            view = view4;
            view2 = view5;
        }
        if (view != null) {
            return view;
        }
        return view2;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        if (this.mOrientation == 0) {
            return new LayoutParams(-2, -1);
        }
        return new LayoutParams(-1, -2);
    }

    @Override
    public RecyclerView.LayoutParams generateLayoutParams(Context context, AttributeSet attributeSet) {
        return new LayoutParams(context, attributeSet);
    }

    @Override
    public RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams)layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    @Override
    public int getColumnCountForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (this.mOrientation == 1) {
            return this.mSpanCount;
        }
        if (state.getItemCount() < 1) {
            return 0;
        }
        return this.getSpanGroupIndex(recycler, state, state.getItemCount() - 1) + 1;
    }

    @Override
    public int getRowCountForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (this.mOrientation == 0) {
            return this.mSpanCount;
        }
        if (state.getItemCount() < 1) {
            return 0;
        }
        return this.getSpanGroupIndex(recycler, state, state.getItemCount() - 1) + 1;
    }

    int getSpaceForSpanRange(int n, int n2) {
        if (this.mOrientation == 1 && this.isLayoutRTL()) {
            return this.mCachedBorders[this.mSpanCount - n] - this.mCachedBorders[this.mSpanCount - n - n2];
        }
        return this.mCachedBorders[n2 + n] - this.mCachedBorders[n];
    }

    public int getSpanCount() {
        return this.mSpanCount;
    }

    public SpanSizeLookup getSpanSizeLookup() {
        return this.mSpanSizeLookup;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    void layoutChunk(RecyclerView.Recycler object, RecyclerView.State object2, LinearLayoutManager.LayoutState layoutState, LinearLayoutManager.LayoutChunkResult layoutChunkResult) {
        int n;
        int n2;
        View view;
        int n3;
        int n4 = this.mOrientationHelper.getModeInOther();
        int n5 = n4 != 1073741824 ? 1 : 0;
        int n6 = this.getChildCount() > 0 ? this.mCachedBorders[this.mSpanCount] : 0;
        if (n5 != 0) {
            this.updateMeasurements();
        }
        boolean bl = layoutState.mItemDirection == 1;
        int n7 = this.mSpanCount;
        if (!bl) {
            n7 = this.getSpanIndex((RecyclerView.Recycler)object, (RecyclerView.State)object2, layoutState.mCurrentPosition) + this.getSpanSize((RecyclerView.Recycler)object, (RecyclerView.State)object2, layoutState.mCurrentPosition);
        }
        int n8 = 0;
        for (n3 = 0; n3 < this.mSpanCount && layoutState.hasMore((RecyclerView.State)object2) && n7 > 0; n8 += n2, ++n3) {
            n = layoutState.mCurrentPosition;
            n2 = this.getSpanSize((RecyclerView.Recycler)object, (RecyclerView.State)object2, n);
            if (n2 > this.mSpanCount) {
                object = new StringBuilder();
                object.append("Item at position ");
                object.append(n);
                object.append(" requires ");
                object.append(n2);
                object.append(" spans but GridLayoutManager has only ");
                object.append(this.mSpanCount);
                object.append(" spans.");
                throw new IllegalArgumentException(object.toString());
            }
            if ((n7 -= n2) < 0 || (view = layoutState.next((RecyclerView.Recycler)object)) == null) break;
            this.mSet[n3] = view;
        }
        if (n3 == 0) {
            layoutChunkResult.mFinished = true;
            return;
        }
        float f = 0.0f;
        this.assignSpans((RecyclerView.Recycler)object, (RecyclerView.State)object2, n3, n8, bl);
        n7 = 0;
        for (n8 = 0; n8 < n3; ++n8) {
            object = this.mSet[n8];
            if (layoutState.mScrapList == null) {
                if (bl) {
                    this.addView((View)object);
                } else {
                    this.addView((View)object, 0);
                }
            } else if (bl) {
                this.addDisappearingView((View)object);
            } else {
                this.addDisappearingView((View)object, 0);
            }
            this.calculateItemDecorationsForChild((View)object, this.mDecorInsets);
            this.measureChild((View)object, n4, false);
            n = this.mOrientationHelper.getDecoratedMeasurement((View)object);
            n2 = n7;
            if (n > n7) {
                n2 = n;
            }
            object2 = (LayoutParams)object.getLayoutParams();
            float f2 = 1.0f * (float)this.mOrientationHelper.getDecoratedMeasurementInOther((View)object) / (float)object2.mSpanSize;
            float f3 = f;
            if (f2 > f) {
                f3 = f2;
            }
            n7 = n2;
            f = f3;
        }
        n8 = n7;
        if (n5 != 0) {
            this.guessMeasurement(f, n6);
            n5 = 0;
            n7 = 0;
            do {
                n8 = n7;
                if (n5 >= n3) break;
                object = this.mSet[n5];
                this.measureChild((View)object, 1073741824, true);
                n6 = this.mOrientationHelper.getDecoratedMeasurement((View)object);
                n8 = n7;
                if (n6 > n7) {
                    n8 = n6;
                }
                ++n5;
                n7 = n8;
            } while (true);
        }
        for (n7 = 0; n7 < n3; ++n7) {
            object = this.mSet[n7];
            if (this.mOrientationHelper.getDecoratedMeasurement((View)object) == n8) continue;
            object2 = (LayoutParams)object.getLayoutParams();
            view = object2.mDecorInsets;
            n6 = view.top + view.bottom + object2.topMargin + object2.bottomMargin;
            n5 = view.left + view.right + object2.leftMargin + object2.rightMargin;
            n2 = this.getSpaceForSpanRange(object2.mSpanIndex, object2.mSpanSize);
            if (this.mOrientation == 1) {
                n5 = GridLayoutManager.getChildMeasureSpec(n2, 1073741824, n5, object2.width, false);
                n6 = View.MeasureSpec.makeMeasureSpec((int)(n8 - n6), (int)1073741824);
            } else {
                n5 = View.MeasureSpec.makeMeasureSpec((int)(n8 - n5), (int)1073741824);
                n6 = GridLayoutManager.getChildMeasureSpec(n2, 1073741824, n6, object2.height, false);
            }
            this.measureChildWithDecorationsAndMargin((View)object, n5, n6, true);
        }
        n = 0;
        layoutChunkResult.mConsumed = n8;
        if (this.mOrientation == 1) {
            if (layoutState.mLayoutDirection == -1) {
                n7 = n5 = layoutState.mOffset;
                n8 = n7;
                n7 = n5 -= n8;
            } else {
                n7 = n5 = layoutState.mOffset;
                n8 += n5;
            }
            n6 = n5 = 0;
            n2 = n;
        } else if (layoutState.mLayoutDirection == -1) {
            n5 = layoutState.mOffset;
            n2 = n7 = 0;
            n6 = n5;
            n5 -= n8;
            n8 = n2;
            n2 = n;
        } else {
            n5 = layoutState.mOffset;
            n6 = n8 + n5;
            n8 = n7 = 0;
            n2 = n;
        }
        do {
            block31 : {
                block32 : {
                    block29 : {
                        block30 : {
                            if (n2 >= n3) {
                                Arrays.fill((Object[])this.mSet, null);
                                return;
                            }
                            object = this.mSet[n2];
                            object2 = (LayoutParams)object.getLayoutParams();
                            if (this.mOrientation != 1) break block29;
                            if (!this.isLayoutRTL()) break block30;
                            n6 = this.getPaddingLeft() + this.mCachedBorders[this.mSpanCount - object2.mSpanIndex];
                            n = this.mOrientationHelper.getDecoratedMeasurementInOther((View)object);
                            n5 = n6;
                            n6 -= n;
                            break block31;
                        }
                        n5 = this.getPaddingLeft() + this.mCachedBorders[object2.mSpanIndex];
                        n6 = this.mOrientationHelper.getDecoratedMeasurementInOther((View)object) + n5;
                        break block32;
                    }
                    n7 = this.getPaddingTop() + this.mCachedBorders[object2.mSpanIndex];
                    n8 = this.mOrientationHelper.getDecoratedMeasurementInOther((View)object) + n7;
                }
                n = n5;
                n5 = n6;
                n6 = n;
            }
            this.layoutDecoratedWithMargins((View)object, n6, n7, n5, n8);
            if (object2.isItemRemoved() || object2.isItemChanged()) {
                layoutChunkResult.mIgnoreConsumed = true;
            }
            layoutChunkResult.mFocusable |= object.hasFocusable();
            n = n2 + 1;
            n2 = n5;
            n5 = n6;
            n6 = n2;
            n2 = n;
        } while (true);
    }

    @Override
    void onAnchorReady(RecyclerView.Recycler recycler, RecyclerView.State state, LinearLayoutManager.AnchorInfo anchorInfo, int n) {
        super.onAnchorReady(recycler, state, anchorInfo, n);
        this.updateMeasurements();
        if (state.getItemCount() > 0 && !state.isPreLayout()) {
            this.ensureAnchorIsInCorrectSpan(recycler, state, anchorInfo, n);
        }
        this.ensureViewSet();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public View onFocusSearchFailed(View var1_1, int var2_2, RecyclerView.Recycler var3_3, RecyclerView.State var4_4) {
        var22_10 = this.findContainingItemView(var1_1 /* !! */ );
        var21_11 = null;
        if (var22_10 == null) {
            return null;
        }
        var23_12 = (LayoutParams)var22_10.getLayoutParams();
        var14_13 = var23_12.mSpanIndex;
        var15_14 = var23_12.mSpanIndex + var23_12.mSpanSize;
        if (super.onFocusSearchFailed(var1_1 /* !! */ , var2_7, (RecyclerView.Recycler)var3_8, (RecyclerView.State)var4_9) == null) {
            return null;
        }
        var20_15 = this.convertFocusDirectionToLayoutDirection(var2_7) == 1;
        var2_7 = var20_15 != this.mShouldReverseLayout ? 1 : 0;
        if (var2_7 != 0) {
            var7_16 = this.getChildCount() - 1;
            var8_18 = var5_17 = -1;
        } else {
            var5_17 = this.getChildCount();
            var8_18 = 1;
            var7_16 = 0;
        }
        var9_19 = this.mOrientation == 1 && this.isLayoutRTL() != false ? 1 : 0;
        var16_20 = this.getSpanGroupIndex((RecyclerView.Recycler)var3_8, (RecyclerView.State)var4_9, var7_16);
        var6_22 = var12_21 = -1;
        var11_23 = 0;
        var2_7 = 0;
        var1_2 = null;
        var10_24 = var5_17;
        var5_17 = var12_21;
        while (var7_16 != var10_24) {
            block16 : {
                block17 : {
                    block15 : {
                        var12_21 = this.getSpanGroupIndex((RecyclerView.Recycler)var3_8, (RecyclerView.State)var4_9, var7_16);
                        var23_12 = this.getChildAt(var7_16);
                        if (var23_12 == var22_10) break;
                        if (!var23_12.hasFocusable() || var12_21 == var16_20) break block15;
                        if (var21_11 != null) {
                            break;
                        }
                        break block16;
                    }
                    var24_29 = (LayoutParams)var23_12.getLayoutParams();
                    var17_26 = var24_29.mSpanIndex;
                    var18_27 = var24_29.mSpanIndex + var24_29.mSpanSize;
                    if (var23_12.hasFocusable() && var17_26 == var14_13 && var18_27 == var15_14) {
                        return var23_12;
                    }
                    if (var23_12.hasFocusable() && var21_11 == null || !var23_12.hasFocusable() && var1_3 == null) ** GOTO lbl-1000
                    var12_21 = Math.max(var17_26, var14_13);
                    var19_28 = Math.min(var18_27, var15_14) - var12_21;
                    if (!var23_12.hasFocusable()) break block17;
                    if (var19_28 > var11_23) ** GOTO lbl-1000
                    if (var19_28 != var11_23) ** GOTO lbl-1000
                    var12_21 = var17_26 > var5_17 ? 1 : 0;
                    if (var9_19 != var12_21) ** GOTO lbl-1000
                    ** GOTO lbl-1000
                }
                if (var21_11 != null) ** GOTO lbl-1000
                var12_21 = 0;
                if (!this.isViewPartiallyVisible((View)var23_12, false, true)) ** GOTO lbl-1000
                var13_25 = var2_7;
                if (var19_28 <= var13_25) {
                    if (var19_28 == var13_25) {
                        if (var17_26 > var6_22) {
                            var12_21 = 1;
                        }
                        ** if (var9_19 != var12_21) goto lbl-1000
                    } else {
                        ** GOTO lbl61
                    }
                }
                ** GOTO lbl-1000
lbl61: // 2 sources:
                ** GOTO lbl-1000
lbl-1000: // 5 sources:
                {
                    var12_21 = 1;
                    ** GOTO lbl65
                }
lbl-1000: // 6 sources:
                {
                    var12_21 = 0;
                }
lbl65: // 2 sources:
                if (var12_21 != 0) {
                    if (var23_12.hasFocusable()) {
                        var5_17 = var24_29.mSpanIndex;
                        var11_23 = Math.min(var18_27, var15_14) - Math.max(var17_26, var14_13);
                        var21_11 = var23_12;
                    } else {
                        var6_22 = var24_29.mSpanIndex;
                        var2_7 = Math.min(var18_27, var15_14);
                        var12_21 = Math.max(var17_26, var14_13);
                        var1_4 = var23_12;
                        var2_7 -= var12_21;
                    }
                }
            }
            var7_16 += var8_18;
        }
        if (var21_11 == null) return var1_6;
        var1_5 = var21_11;
        return var1_6;
    }

    @Override
    public void onInitializeAccessibilityNodeInfoForItem(RecyclerView.Recycler recycler, RecyclerView.State state, View object, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        ViewGroup.LayoutParams layoutParams = object.getLayoutParams();
        if (!(layoutParams instanceof LayoutParams)) {
            super.onInitializeAccessibilityNodeInfoForItem((View)object, accessibilityNodeInfoCompat);
            return;
        }
        object = (LayoutParams)layoutParams;
        int n = this.getSpanGroupIndex(recycler, state, object.getViewLayoutPosition());
        if (this.mOrientation == 0) {
            int n2 = object.getSpanIndex();
            int n3 = object.getSpanSize();
            boolean bl = this.mSpanCount > 1 && object.getSpanSize() == this.mSpanCount;
            accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(n2, n3, n, 1, bl, false));
            return;
        }
        int n4 = object.getSpanIndex();
        int n5 = object.getSpanSize();
        boolean bl = this.mSpanCount > 1 && object.getSpanSize() == this.mSpanCount;
        accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(n, 1, n4, n5, bl, false));
    }

    @Override
    public void onItemsAdded(RecyclerView recyclerView, int n, int n2) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
    }

    @Override
    public void onItemsChanged(RecyclerView recyclerView) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
    }

    @Override
    public void onItemsMoved(RecyclerView recyclerView, int n, int n2, int n3) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
    }

    @Override
    public void onItemsRemoved(RecyclerView recyclerView, int n, int n2) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
    }

    @Override
    public void onItemsUpdated(RecyclerView recyclerView, int n, int n2, Object object) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (state.isPreLayout()) {
            this.cachePreLayoutSpanMapping();
        }
        super.onLayoutChildren(recycler, state);
        this.clearPreLayoutSpanMappingCache();
    }

    @Override
    public void onLayoutCompleted(RecyclerView.State state) {
        super.onLayoutCompleted(state);
        this.mPendingSpanCountChange = false;
    }

    @Override
    public int scrollHorizontallyBy(int n, RecyclerView.Recycler recycler, RecyclerView.State state) {
        this.updateMeasurements();
        this.ensureViewSet();
        return super.scrollHorizontallyBy(n, recycler, state);
    }

    @Override
    public int scrollVerticallyBy(int n, RecyclerView.Recycler recycler, RecyclerView.State state) {
        this.updateMeasurements();
        this.ensureViewSet();
        return super.scrollVerticallyBy(n, recycler, state);
    }

    @Override
    public void setMeasuredDimension(Rect rect, int n, int n2) {
        if (this.mCachedBorders == null) {
            super.setMeasuredDimension(rect, n, n2);
        }
        int n3 = this.getPaddingLeft() + this.getPaddingRight();
        int n4 = this.getPaddingTop() + this.getPaddingBottom();
        if (this.mOrientation == 1) {
            n2 = GridLayoutManager.chooseSize(n2, rect.height() + n4, this.getMinimumHeight());
            n3 = GridLayoutManager.chooseSize(n, this.mCachedBorders[this.mCachedBorders.length - 1] + n3, this.getMinimumWidth());
            n = n2;
            n2 = n3;
        } else {
            n = GridLayoutManager.chooseSize(n, rect.width() + n3, this.getMinimumWidth());
            n3 = GridLayoutManager.chooseSize(n2, this.mCachedBorders[this.mCachedBorders.length - 1] + n4, this.getMinimumHeight());
            n2 = n;
            n = n3;
        }
        this.setMeasuredDimension(n2, n);
    }

    public void setSpanCount(int n) {
        if (n == this.mSpanCount) {
            return;
        }
        this.mPendingSpanCountChange = true;
        if (n < 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Span count should be at least 1. Provided ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        this.mSpanCount = n;
        this.mSpanSizeLookup.invalidateSpanIndexCache();
        this.requestLayout();
    }

    public void setSpanSizeLookup(SpanSizeLookup spanSizeLookup) {
        this.mSpanSizeLookup = spanSizeLookup;
    }

    @Override
    public void setStackFromEnd(boolean bl) {
        if (bl) {
            throw new UnsupportedOperationException("GridLayoutManager does not support stack from end. Consider using reverse layout");
        }
        super.setStackFromEnd(false);
    }

    @Override
    public boolean supportsPredictiveItemAnimations() {
        if (this.mPendingSavedState == null && !this.mPendingSpanCountChange) {
            return true;
        }
        return false;
    }

    public static final class DefaultSpanSizeLookup
    extends SpanSizeLookup {
        @Override
        public int getSpanIndex(int n, int n2) {
            return n % n2;
        }

        @Override
        public int getSpanSize(int n) {
            return 1;
        }
    }

    public static class LayoutParams
    extends RecyclerView.LayoutParams {
        public static final int INVALID_SPAN_ID = -1;
        int mSpanIndex = -1;
        int mSpanSize = 0;

        public LayoutParams(int n, int n2) {
            super(n, n2);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(RecyclerView.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public int getSpanIndex() {
            return this.mSpanIndex;
        }

        public int getSpanSize() {
            return this.mSpanSize;
        }
    }

    public static abstract class SpanSizeLookup {
        private boolean mCacheSpanIndices = false;
        final SparseIntArray mSpanIndexCache = new SparseIntArray();

        int findReferenceIndexFromCache(int n) {
            int n2 = this.mSpanIndexCache.size() - 1;
            int n3 = 0;
            while (n3 <= n2) {
                int n4 = n3 + n2 >>> 1;
                if (this.mSpanIndexCache.keyAt(n4) < n) {
                    n3 = n4 + 1;
                    continue;
                }
                n2 = n4 - 1;
            }
            n = n3 - 1;
            if (n >= 0 && n < this.mSpanIndexCache.size()) {
                return this.mSpanIndexCache.keyAt(n);
            }
            return -1;
        }

        int getCachedSpanIndex(int n, int n2) {
            if (!this.mCacheSpanIndices) {
                return this.getSpanIndex(n, n2);
            }
            int n3 = this.mSpanIndexCache.get(n, -1);
            if (n3 != -1) {
                return n3;
            }
            n2 = this.getSpanIndex(n, n2);
            this.mSpanIndexCache.put(n, n2);
            return n2;
        }

        public int getSpanGroupIndex(int n, int n2) {
            int n3;
            int n4;
            int n5 = this.getSpanSize(n);
            int n6 = n4 = (n3 = 0);
            while (n3 < n) {
                int n7;
                int n8 = this.getSpanSize(n3);
                int n9 = n4 + n8;
                if (n9 == n2) {
                    n7 = n6 + 1;
                    n4 = 0;
                } else {
                    n4 = n9;
                    n7 = n6;
                    if (n9 > n2) {
                        n7 = n6 + 1;
                        n4 = n8;
                    }
                }
                ++n3;
                n6 = n7;
            }
            n = n6;
            if (n4 + n5 > n2) {
                n = n6 + 1;
            }
            return n;
        }

        public int getSpanIndex(int n, int n2) {
            int n3;
            int n4;
            int n5 = this.getSpanSize(n);
            if (n5 == n2) {
                return 0;
            }
            if (this.mCacheSpanIndices && this.mSpanIndexCache.size() > 0 && (n4 = this.findReferenceIndexFromCache(n)) >= 0) {
                n3 = this.mSpanIndexCache.get(n4) + this.getSpanSize(n4);
                ++n4;
            } else {
                n3 = n4 = 0;
            }
            while (n4 < n) {
                int n6 = this.getSpanSize(n4);
                int n7 = n3 + n6;
                if (n7 == n2) {
                    n3 = 0;
                } else {
                    n3 = n7;
                    if (n7 > n2) {
                        n3 = n6;
                    }
                }
                ++n4;
            }
            if (n5 + n3 <= n2) {
                return n3;
            }
            return 0;
        }

        public abstract int getSpanSize(int var1);

        public void invalidateSpanIndexCache() {
            this.mSpanIndexCache.clear();
        }

        public boolean isSpanIndexCacheEnabled() {
            return this.mCacheSpanIndices;
        }

        public void setSpanIndexCacheEnabled(boolean bl) {
            this.mCacheSpanIndices = bl;
        }
    }

}
