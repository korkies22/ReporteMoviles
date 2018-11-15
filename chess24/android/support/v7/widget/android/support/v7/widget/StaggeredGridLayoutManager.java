/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.PointF
 *  android.graphics.Rect
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.accessibility.AccessibilityEvent
 */
package android.support.v7.widget;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v7.widget.LayoutState;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ScrollbarHelper;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

public class StaggeredGridLayoutManager
extends RecyclerView.LayoutManager
implements RecyclerView.SmoothScroller.ScrollVectorProvider {
    static final boolean DEBUG = false;
    @Deprecated
    public static final int GAP_HANDLING_LAZY = 1;
    public static final int GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS = 2;
    public static final int GAP_HANDLING_NONE = 0;
    public static final int HORIZONTAL = 0;
    static final int INVALID_OFFSET = Integer.MIN_VALUE;
    private static final float MAX_SCROLL_FACTOR = 0.33333334f;
    private static final String TAG = "StaggeredGridLManager";
    public static final int VERTICAL = 1;
    private final AnchorInfo mAnchorInfo = new AnchorInfo();
    private final Runnable mCheckForGapsRunnable = new Runnable(){

        @Override
        public void run() {
            StaggeredGridLayoutManager.this.checkForGaps();
        }
    };
    private int mFullSizeSpec;
    private int mGapStrategy = 2;
    private boolean mLaidOutInvalidFullSpan = false;
    private boolean mLastLayoutFromEnd;
    private boolean mLastLayoutRTL;
    @NonNull
    private final LayoutState mLayoutState;
    LazySpanLookup mLazySpanLookup = new LazySpanLookup();
    private int mOrientation;
    private SavedState mPendingSavedState;
    int mPendingScrollPosition = -1;
    int mPendingScrollPositionOffset = Integer.MIN_VALUE;
    private int[] mPrefetchDistances;
    @NonNull
    OrientationHelper mPrimaryOrientation;
    private BitSet mRemainingSpans;
    boolean mReverseLayout = false;
    @NonNull
    OrientationHelper mSecondaryOrientation;
    boolean mShouldReverseLayout = false;
    private int mSizePerSpan;
    private boolean mSmoothScrollbarEnabled = true;
    private int mSpanCount = -1;
    Span[] mSpans;
    private final Rect mTmpRect = new Rect();

    public StaggeredGridLayoutManager(int n, int n2) {
        this.mOrientation = n2;
        this.setSpanCount(n);
        this.mLayoutState = new LayoutState();
        this.createOrientationHelpers();
    }

    public StaggeredGridLayoutManager(Context object, AttributeSet attributeSet, int n, int n2) {
        object = StaggeredGridLayoutManager.getProperties(object, attributeSet, n, n2);
        this.setOrientation(object.orientation);
        this.setSpanCount(object.spanCount);
        this.setReverseLayout(object.reverseLayout);
        this.mLayoutState = new LayoutState();
        this.createOrientationHelpers();
    }

    private void appendViewToAllSpans(View view) {
        for (int i = this.mSpanCount - 1; i >= 0; --i) {
            this.mSpans[i].appendToSpan(view);
        }
    }

    private void applyPendingSavedState(AnchorInfo anchorInfo) {
        if (this.mPendingSavedState.mSpanOffsetsSize > 0) {
            if (this.mPendingSavedState.mSpanOffsetsSize == this.mSpanCount) {
                for (int i = 0; i < this.mSpanCount; ++i) {
                    int n;
                    this.mSpans[i].clear();
                    int n2 = n = this.mPendingSavedState.mSpanOffsets[i];
                    if (n != Integer.MIN_VALUE) {
                        n2 = this.mPendingSavedState.mAnchorLayoutFromEnd ? n + this.mPrimaryOrientation.getEndAfterPadding() : n + this.mPrimaryOrientation.getStartAfterPadding();
                    }
                    this.mSpans[i].setLine(n2);
                }
            } else {
                this.mPendingSavedState.invalidateSpanInfo();
                this.mPendingSavedState.mAnchorPosition = this.mPendingSavedState.mVisibleAnchorPosition;
            }
        }
        this.mLastLayoutRTL = this.mPendingSavedState.mLastLayoutRTL;
        this.setReverseLayout(this.mPendingSavedState.mReverseLayout);
        this.resolveShouldLayoutReverse();
        if (this.mPendingSavedState.mAnchorPosition != -1) {
            this.mPendingScrollPosition = this.mPendingSavedState.mAnchorPosition;
            anchorInfo.mLayoutFromEnd = this.mPendingSavedState.mAnchorLayoutFromEnd;
        } else {
            anchorInfo.mLayoutFromEnd = this.mShouldReverseLayout;
        }
        if (this.mPendingSavedState.mSpanLookupSize > 1) {
            this.mLazySpanLookup.mData = this.mPendingSavedState.mSpanLookup;
            this.mLazySpanLookup.mFullSpanItems = this.mPendingSavedState.mFullSpanItems;
        }
    }

    private void attachViewToSpans(View view, LayoutParams layoutParams, LayoutState layoutState) {
        if (layoutState.mLayoutDirection == 1) {
            if (layoutParams.mFullSpan) {
                this.appendViewToAllSpans(view);
                return;
            }
            layoutParams.mSpan.appendToSpan(view);
            return;
        }
        if (layoutParams.mFullSpan) {
            this.prependViewToAllSpans(view);
            return;
        }
        layoutParams.mSpan.prependToSpan(view);
    }

    private int calculateScrollDirectionForPosition(int n) {
        int n2 = this.getChildCount();
        int n3 = -1;
        if (n2 == 0) {
            n = n3;
            if (this.mShouldReverseLayout) {
                n = 1;
            }
            return n;
        }
        boolean bl = n < this.getFirstChildPosition();
        if (bl != this.mShouldReverseLayout) {
            return -1;
        }
        return 1;
    }

    private boolean checkSpanForGap(Span span) {
        if (this.mShouldReverseLayout) {
            if (span.getEndLine() < this.mPrimaryOrientation.getEndAfterPadding()) {
                return span.getLayoutParams((View)span.mViews.get((int)(span.mViews.size() - 1))).mFullSpan ^ true;
            }
        } else if (span.getStartLine() > this.mPrimaryOrientation.getStartAfterPadding()) {
            return span.getLayoutParams((View)span.mViews.get((int)0)).mFullSpan ^ true;
        }
        return false;
    }

    private int computeScrollExtent(RecyclerView.State state) {
        if (this.getChildCount() == 0) {
            return 0;
        }
        return ScrollbarHelper.computeScrollExtent(state, this.mPrimaryOrientation, this.findFirstVisibleItemClosestToStart(this.mSmoothScrollbarEnabled ^ true), this.findFirstVisibleItemClosestToEnd(this.mSmoothScrollbarEnabled ^ true), this, this.mSmoothScrollbarEnabled);
    }

    private int computeScrollOffset(RecyclerView.State state) {
        if (this.getChildCount() == 0) {
            return 0;
        }
        return ScrollbarHelper.computeScrollOffset(state, this.mPrimaryOrientation, this.findFirstVisibleItemClosestToStart(this.mSmoothScrollbarEnabled ^ true), this.findFirstVisibleItemClosestToEnd(this.mSmoothScrollbarEnabled ^ true), this, this.mSmoothScrollbarEnabled, this.mShouldReverseLayout);
    }

    private int computeScrollRange(RecyclerView.State state) {
        if (this.getChildCount() == 0) {
            return 0;
        }
        return ScrollbarHelper.computeScrollRange(state, this.mPrimaryOrientation, this.findFirstVisibleItemClosestToStart(this.mSmoothScrollbarEnabled ^ true), this.findFirstVisibleItemClosestToEnd(this.mSmoothScrollbarEnabled ^ true), this, this.mSmoothScrollbarEnabled);
    }

    private int convertFocusDirectionToLayoutDirection(int n) {
        int n2 = Integer.MIN_VALUE;
        if (n != 17) {
            if (n != 33) {
                if (n != 66) {
                    if (n != 130) {
                        switch (n) {
                            default: {
                                return Integer.MIN_VALUE;
                            }
                            case 2: {
                                if (this.mOrientation == 1) {
                                    return 1;
                                }
                                if (this.isLayoutRTL()) {
                                    return -1;
                                }
                                return 1;
                            }
                            case 1: 
                        }
                        if (this.mOrientation == 1) {
                            return -1;
                        }
                        if (this.isLayoutRTL()) {
                            return 1;
                        }
                        return -1;
                    }
                    if (this.mOrientation == 1) {
                        n2 = 1;
                    }
                    return n2;
                }
                if (this.mOrientation == 0) {
                    n2 = 1;
                }
                return n2;
            }
            if (this.mOrientation == 1) {
                return -1;
            }
            return Integer.MIN_VALUE;
        }
        if (this.mOrientation == 0) {
            return -1;
        }
        return Integer.MIN_VALUE;
    }

    private LazySpanLookup.FullSpanItem createFullSpanItemFromEnd(int n) {
        LazySpanLookup.FullSpanItem fullSpanItem = new LazySpanLookup.FullSpanItem();
        fullSpanItem.mGapPerSpan = new int[this.mSpanCount];
        for (int i = 0; i < this.mSpanCount; ++i) {
            fullSpanItem.mGapPerSpan[i] = n - this.mSpans[i].getEndLine(n);
        }
        return fullSpanItem;
    }

    private LazySpanLookup.FullSpanItem createFullSpanItemFromStart(int n) {
        LazySpanLookup.FullSpanItem fullSpanItem = new LazySpanLookup.FullSpanItem();
        fullSpanItem.mGapPerSpan = new int[this.mSpanCount];
        for (int i = 0; i < this.mSpanCount; ++i) {
            fullSpanItem.mGapPerSpan[i] = this.mSpans[i].getStartLine(n) - n;
        }
        return fullSpanItem;
    }

    private void createOrientationHelpers() {
        this.mPrimaryOrientation = OrientationHelper.createOrientationHelper(this, this.mOrientation);
        this.mSecondaryOrientation = OrientationHelper.createOrientationHelper(this, 1 - this.mOrientation);
    }

    private int fill(RecyclerView.Recycler recycler, LayoutState layoutState, RecyclerView.State state) {
        RuntimeException runtimeException;
        super("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.useAs(TypeTransformer.java:868)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:668)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:810)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:840)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
        throw runtimeException;
    }

    private int findFirstReferenceChildPosition(int n) {
        int n2 = this.getChildCount();
        for (int i = 0; i < n2; ++i) {
            int n3 = this.getPosition(this.getChildAt(i));
            if (n3 < 0 || n3 >= n) continue;
            return n3;
        }
        return 0;
    }

    private int findLastReferenceChildPosition(int n) {
        for (int i = this.getChildCount() - 1; i >= 0; --i) {
            int n2 = this.getPosition(this.getChildAt(i));
            if (n2 < 0 || n2 >= n) continue;
            return n2;
        }
        return 0;
    }

    private void fixEndGap(RecyclerView.Recycler recycler, RecyclerView.State state, boolean bl) {
        int n = this.getMaxEnd(Integer.MIN_VALUE);
        if (n == Integer.MIN_VALUE) {
            return;
        }
        n = this.mPrimaryOrientation.getEndAfterPadding() - n;
        if (n > 0) {
            n -= - this.scrollBy(- n, recycler, state);
            if (bl && n > 0) {
                this.mPrimaryOrientation.offsetChildren(n);
            }
            return;
        }
    }

    private void fixStartGap(RecyclerView.Recycler recycler, RecyclerView.State state, boolean bl) {
        int n = this.getMinStart(Integer.MAX_VALUE);
        if (n == Integer.MAX_VALUE) {
            return;
        }
        if ((n -= this.mPrimaryOrientation.getStartAfterPadding()) > 0) {
            n -= this.scrollBy(n, recycler, state);
            if (bl && n > 0) {
                this.mPrimaryOrientation.offsetChildren(- n);
            }
            return;
        }
    }

    private int getMaxEnd(int n) {
        int n2 = this.mSpans[0].getEndLine(n);
        for (int i = 1; i < this.mSpanCount; ++i) {
            int n3 = this.mSpans[i].getEndLine(n);
            int n4 = n2;
            if (n3 > n2) {
                n4 = n3;
            }
            n2 = n4;
        }
        return n2;
    }

    private int getMaxStart(int n) {
        int n2 = this.mSpans[0].getStartLine(n);
        for (int i = 1; i < this.mSpanCount; ++i) {
            int n3 = this.mSpans[i].getStartLine(n);
            int n4 = n2;
            if (n3 > n2) {
                n4 = n3;
            }
            n2 = n4;
        }
        return n2;
    }

    private int getMinEnd(int n) {
        int n2 = this.mSpans[0].getEndLine(n);
        for (int i = 1; i < this.mSpanCount; ++i) {
            int n3 = this.mSpans[i].getEndLine(n);
            int n4 = n2;
            if (n3 < n2) {
                n4 = n3;
            }
            n2 = n4;
        }
        return n2;
    }

    private int getMinStart(int n) {
        int n2 = this.mSpans[0].getStartLine(n);
        for (int i = 1; i < this.mSpanCount; ++i) {
            int n3 = this.mSpans[i].getStartLine(n);
            int n4 = n2;
            if (n3 < n2) {
                n4 = n3;
            }
            n2 = n4;
        }
        return n2;
    }

    private Span getNextSpan(LayoutState object) {
        int n;
        int n2;
        boolean bl = this.preferLastSpan(object.mLayoutDirection);
        int n3 = -1;
        if (bl) {
            n2 = this.mSpanCount - 1;
            n = -1;
        } else {
            n2 = 0;
            n3 = this.mSpanCount;
            n = 1;
        }
        int n4 = object.mLayoutDirection;
        Span span = null;
        object = null;
        if (n4 == 1) {
            n4 = Integer.MAX_VALUE;
            int n5 = this.mPrimaryOrientation.getStartAfterPadding();
            while (n2 != n3) {
                span = this.mSpans[n2];
                int n6 = span.getEndLine(n5);
                int n7 = n4;
                if (n6 < n4) {
                    object = span;
                    n7 = n6;
                }
                n2 += n;
                n4 = n7;
            }
            return object;
        }
        n4 = Integer.MIN_VALUE;
        int n8 = this.mPrimaryOrientation.getEndAfterPadding();
        object = span;
        while (n2 != n3) {
            span = this.mSpans[n2];
            int n9 = span.getStartLine(n8);
            int n10 = n4;
            if (n9 > n4) {
                object = span;
                n10 = n9;
            }
            n2 += n;
            n4 = n10;
        }
        return object;
    }

    private void handleUpdate(int n, int n2, int n3) {
        int n4;
        int n5;
        int n6;
        block11 : {
            block10 : {
                block8 : {
                    block9 : {
                        n6 = this.mShouldReverseLayout ? this.getLastChildPosition() : this.getFirstChildPosition();
                        if (n3 != 8) break block8;
                        if (n >= n2) break block9;
                        n5 = n2 + 1;
                        break block10;
                    }
                    n4 = n + 1;
                    n5 = n2;
                    break block11;
                }
                n5 = n + n2;
            }
            n4 = n5;
            n5 = n;
        }
        this.mLazySpanLookup.invalidateAfter(n5);
        if (n3 != 8) {
            switch (n3) {
                default: {
                    break;
                }
                case 2: {
                    this.mLazySpanLookup.offsetForRemoval(n, n2);
                    break;
                }
                case 1: {
                    this.mLazySpanLookup.offsetForAddition(n, n2);
                    break;
                }
            }
        } else {
            this.mLazySpanLookup.offsetForRemoval(n, 1);
            this.mLazySpanLookup.offsetForAddition(n2, 1);
        }
        if (n4 <= n6) {
            return;
        }
        n = this.mShouldReverseLayout ? this.getFirstChildPosition() : this.getLastChildPosition();
        if (n5 <= n) {
            this.requestLayout();
        }
    }

    private void measureChildWithDecorationsAndMargin(View view, int n, int n2, boolean bl) {
        this.calculateItemDecorationsForChild(view, this.mTmpRect);
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        n = this.updateSpecWithExtra(n, layoutParams.leftMargin + this.mTmpRect.left, layoutParams.rightMargin + this.mTmpRect.right);
        n2 = this.updateSpecWithExtra(n2, layoutParams.topMargin + this.mTmpRect.top, layoutParams.bottomMargin + this.mTmpRect.bottom);
        bl = bl ? this.shouldReMeasureChild(view, n, n2, layoutParams) : this.shouldMeasureChild(view, n, n2, layoutParams);
        if (bl) {
            view.measure(n, n2);
        }
    }

    private void measureChildWithDecorationsAndMargin(View view, LayoutParams layoutParams, boolean bl) {
        if (layoutParams.mFullSpan) {
            if (this.mOrientation == 1) {
                this.measureChildWithDecorationsAndMargin(view, this.mFullSizeSpec, StaggeredGridLayoutManager.getChildMeasureSpec(this.getHeight(), this.getHeightMode(), this.getPaddingTop() + this.getPaddingBottom(), layoutParams.height, true), bl);
                return;
            }
            this.measureChildWithDecorationsAndMargin(view, StaggeredGridLayoutManager.getChildMeasureSpec(this.getWidth(), this.getWidthMode(), this.getPaddingLeft() + this.getPaddingRight(), layoutParams.width, true), this.mFullSizeSpec, bl);
            return;
        }
        if (this.mOrientation == 1) {
            this.measureChildWithDecorationsAndMargin(view, StaggeredGridLayoutManager.getChildMeasureSpec(this.mSizePerSpan, this.getWidthMode(), 0, layoutParams.width, false), StaggeredGridLayoutManager.getChildMeasureSpec(this.getHeight(), this.getHeightMode(), this.getPaddingTop() + this.getPaddingBottom(), layoutParams.height, true), bl);
            return;
        }
        this.measureChildWithDecorationsAndMargin(view, StaggeredGridLayoutManager.getChildMeasureSpec(this.getWidth(), this.getWidthMode(), this.getPaddingLeft() + this.getPaddingRight(), layoutParams.width, true), StaggeredGridLayoutManager.getChildMeasureSpec(this.mSizePerSpan, this.getHeightMode(), 0, layoutParams.height, false), bl);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void onLayoutChildren(RecyclerView.Recycler var1_1, RecyclerView.State var2_2, boolean var3_3) {
        var7_4 = this.mAnchorInfo;
        if ((this.mPendingSavedState != null || this.mPendingScrollPosition != -1) && var2_2.getItemCount() == 0) {
            this.removeAndRecycleAllViews(var1_1);
            var7_4.reset();
            return;
        }
        var6_5 = var7_4.mValid;
        var5_6 = 1;
        var4_7 = var6_5 && this.mPendingScrollPosition == -1 && this.mPendingSavedState == null ? 0 : 1;
        if (var4_7 != 0) {
            var7_4.reset();
            if (this.mPendingSavedState != null) {
                this.applyPendingSavedState(var7_4);
            } else {
                this.resolveShouldLayoutReverse();
                var7_4.mLayoutFromEnd = this.mShouldReverseLayout;
            }
            this.updateAnchorInfoForLayout(var2_2, var7_4);
            var7_4.mValid = true;
        }
        if (this.mPendingSavedState == null && this.mPendingScrollPosition == -1 && (var7_4.mLayoutFromEnd != this.mLastLayoutFromEnd || this.isLayoutRTL() != this.mLastLayoutRTL)) {
            this.mLazySpanLookup.clear();
            var7_4.mInvalidateOffsets = true;
        }
        if (this.getChildCount() > 0 && (this.mPendingSavedState == null || this.mPendingSavedState.mSpanOffsetsSize < 1)) {
            if (var7_4.mInvalidateOffsets) {
                for (var4_7 = 0; var4_7 < this.mSpanCount; ++var4_7) {
                    this.mSpans[var4_7].clear();
                    if (var7_4.mOffset == Integer.MIN_VALUE) continue;
                    this.mSpans[var4_7].setLine(var7_4.mOffset);
                }
            } else if (var4_7 == 0 && this.mAnchorInfo.mSpanReferenceLines != null) {
                for (var4_7 = 0; var4_7 < this.mSpanCount; ++var4_7) {
                    var8_8 = this.mSpans[var4_7];
                    var8_8.clear();
                    var8_8.setLine(this.mAnchorInfo.mSpanReferenceLines[var4_7]);
                }
            } else {
                for (var4_7 = 0; var4_7 < this.mSpanCount; ++var4_7) {
                    this.mSpans[var4_7].cacheReferenceLineAndClear(this.mShouldReverseLayout, var7_4.mOffset);
                }
                this.mAnchorInfo.saveSpanReferenceLines(this.mSpans);
            }
        }
        this.detachAndScrapAttachedViews(var1_1);
        this.mLayoutState.mRecycle = false;
        this.mLaidOutInvalidFullSpan = false;
        this.updateMeasureSpecs(this.mSecondaryOrientation.getTotalSpace());
        this.updateLayoutState(var7_4.mPosition, var2_2);
        if (var7_4.mLayoutFromEnd) {
            this.setLayoutStateDirection(-1);
            this.fill(var1_1, this.mLayoutState, var2_2);
            this.setLayoutStateDirection(1);
            this.mLayoutState.mCurrentPosition = var7_4.mPosition + this.mLayoutState.mItemDirection;
            this.fill(var1_1, this.mLayoutState, var2_2);
        } else {
            this.setLayoutStateDirection(1);
            this.fill(var1_1, this.mLayoutState, var2_2);
            this.setLayoutStateDirection(-1);
            this.mLayoutState.mCurrentPosition = var7_4.mPosition + this.mLayoutState.mItemDirection;
            this.fill(var1_1, this.mLayoutState, var2_2);
        }
        this.repositionToWrapContentIfNecessary();
        if (this.getChildCount() > 0) {
            if (this.mShouldReverseLayout) {
                this.fixEndGap(var1_1, var2_2, true);
                this.fixStartGap(var1_1, var2_2, false);
            } else {
                this.fixStartGap(var1_1, var2_2, true);
                this.fixEndGap(var1_1, var2_2, false);
            }
        }
        if (!var3_3 || var2_2.isPreLayout()) ** GOTO lbl-1000
        var4_7 = this.mGapStrategy != 0 && this.getChildCount() > 0 && (this.mLaidOutInvalidFullSpan != false || this.hasGapsToFix() != null) ? 1 : 0;
        if (var4_7 == 0) ** GOTO lbl-1000
        this.removeCallbacks(this.mCheckForGapsRunnable);
        if (this.checkForGaps()) {
            var4_7 = var5_6;
        } else lbl-1000: // 3 sources:
        {
            var4_7 = 0;
        }
        if (var2_2.isPreLayout()) {
            this.mAnchorInfo.reset();
        }
        this.mLastLayoutFromEnd = var7_4.mLayoutFromEnd;
        this.mLastLayoutRTL = this.isLayoutRTL();
        if (var4_7 == 0) return;
        this.mAnchorInfo.reset();
        this.onLayoutChildren(var1_1, var2_2, false);
    }

    private boolean preferLastSpan(int n) {
        int n2 = this.mOrientation;
        boolean bl = false;
        boolean bl2 = false;
        if (n2 == 0) {
            boolean bl3 = n == -1;
            bl = bl2;
            if (bl3 != this.mShouldReverseLayout) {
                bl = true;
            }
            return bl;
        }
        boolean bl4 = n == -1;
        bl4 = bl4 == this.mShouldReverseLayout;
        if (bl4 == this.isLayoutRTL()) {
            bl = true;
        }
        return bl;
    }

    private void prependViewToAllSpans(View view) {
        for (int i = this.mSpanCount - 1; i >= 0; --i) {
            this.mSpans[i].prependToSpan(view);
        }
    }

    private void recycle(RecyclerView.Recycler recycler, LayoutState layoutState) {
        if (layoutState.mRecycle) {
            if (layoutState.mInfinite) {
                return;
            }
            if (layoutState.mAvailable == 0) {
                if (layoutState.mLayoutDirection == -1) {
                    this.recycleFromEnd(recycler, layoutState.mEndLine);
                    return;
                }
                this.recycleFromStart(recycler, layoutState.mStartLine);
                return;
            }
            if (layoutState.mLayoutDirection == -1) {
                int n = layoutState.mStartLine - this.getMaxStart(layoutState.mStartLine);
                n = n < 0 ? layoutState.mEndLine : layoutState.mEndLine - Math.min(n, layoutState.mAvailable);
                this.recycleFromEnd(recycler, n);
                return;
            }
            int n = this.getMinEnd(layoutState.mEndLine) - layoutState.mEndLine;
            if (n < 0) {
                n = layoutState.mStartLine;
            } else {
                int n2 = layoutState.mStartLine;
                n = Math.min(n, layoutState.mAvailable) + n2;
            }
            this.recycleFromStart(recycler, n);
            return;
        }
    }

    private void recycleFromEnd(RecyclerView.Recycler recycler, int n) {
        for (int i = this.getChildCount() - 1; i >= 0; --i) {
            View view = this.getChildAt(i);
            if (this.mPrimaryOrientation.getDecoratedStart(view) >= n && this.mPrimaryOrientation.getTransformedStartWithDecoration(view) >= n) {
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                if (layoutParams.mFullSpan) {
                    int n2 = 0;
                    int n3 = 0;
                    do {
                        if (n3 >= this.mSpanCount) break;
                        if (this.mSpans[n3].mViews.size() == 1) {
                            return;
                        }
                        ++n3;
                    } while (true);
                    for (int j = n2; j < this.mSpanCount; ++j) {
                        this.mSpans[j].popEnd();
                    }
                } else {
                    if (layoutParams.mSpan.mViews.size() == 1) {
                        return;
                    }
                    layoutParams.mSpan.popEnd();
                }
                this.removeAndRecycleView(view, recycler);
                continue;
            }
            return;
        }
    }

    private void recycleFromStart(RecyclerView.Recycler recycler, int n) {
        while (this.getChildCount() > 0) {
            int n2 = 0;
            View view = this.getChildAt(0);
            if (this.mPrimaryOrientation.getDecoratedEnd(view) <= n && this.mPrimaryOrientation.getTransformedEndWithDecoration(view) <= n) {
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                if (layoutParams.mFullSpan) {
                    int n3 = 0;
                    do {
                        if (n3 >= this.mSpanCount) break;
                        if (this.mSpans[n3].mViews.size() == 1) {
                            return;
                        }
                        ++n3;
                    } while (true);
                    for (int i = n2; i < this.mSpanCount; ++i) {
                        this.mSpans[i].popStart();
                    }
                } else {
                    if (layoutParams.mSpan.mViews.size() == 1) {
                        return;
                    }
                    layoutParams.mSpan.popStart();
                }
                this.removeAndRecycleView(view, recycler);
                continue;
            }
            return;
        }
    }

    private void repositionToWrapContentIfNecessary() {
        View view;
        int n;
        int n2;
        if (this.mSecondaryOrientation.getMode() == 1073741824) {
            return;
        }
        int n3 = this.getChildCount();
        int n4 = 0;
        float f = 0.0f;
        for (n = 0; n < n3; ++n) {
            view = this.getChildAt(n);
            float f2 = this.mSecondaryOrientation.getDecoratedMeasurement(view);
            if (f2 < f) continue;
            float f3 = f2;
            if (((LayoutParams)view.getLayoutParams()).isFullSpan()) {
                f3 = 1.0f * f2 / (float)this.mSpanCount;
            }
            f = Math.max(f, f3);
        }
        int n5 = this.mSizePerSpan;
        n = n2 = Math.round(f * (float)this.mSpanCount);
        if (this.mSecondaryOrientation.getMode() == Integer.MIN_VALUE) {
            n = Math.min(n2, this.mSecondaryOrientation.getTotalSpace());
        }
        this.updateMeasureSpecs(n);
        if (this.mSizePerSpan == n5) {
            return;
        }
        for (n = n4; n < n3; ++n) {
            view = this.getChildAt(n);
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            if (layoutParams.mFullSpan) continue;
            if (this.isLayoutRTL() && this.mOrientation == 1) {
                view.offsetLeftAndRight((- this.mSpanCount - 1 - layoutParams.mSpan.mIndex) * this.mSizePerSpan - (- this.mSpanCount - 1 - layoutParams.mSpan.mIndex) * n5);
                continue;
            }
            n4 = layoutParams.mSpan.mIndex * this.mSizePerSpan;
            n2 = layoutParams.mSpan.mIndex * n5;
            if (this.mOrientation == 1) {
                view.offsetLeftAndRight(n4 - n2);
                continue;
            }
            view.offsetTopAndBottom(n4 - n2);
        }
    }

    private void resolveShouldLayoutReverse() {
        if (this.mOrientation != 1 && this.isLayoutRTL()) {
            this.mShouldReverseLayout = this.mReverseLayout ^ true;
            return;
        }
        this.mShouldReverseLayout = this.mReverseLayout;
    }

    private void setLayoutStateDirection(int n) {
        this.mLayoutState.mLayoutDirection = n;
        LayoutState layoutState = this.mLayoutState;
        boolean bl = this.mShouldReverseLayout;
        int n2 = 1;
        boolean bl2 = n == -1;
        n = bl == bl2 ? n2 : -1;
        layoutState.mItemDirection = n;
    }

    private void updateAllRemainingSpans(int n, int n2) {
        for (int i = 0; i < this.mSpanCount; ++i) {
            if (this.mSpans[i].mViews.isEmpty()) continue;
            this.updateRemainingSpans(this.mSpans[i], n, n2);
        }
    }

    private boolean updateAnchorFromChildren(RecyclerView.State state, AnchorInfo anchorInfo) {
        int n = this.mLastLayoutFromEnd ? this.findLastReferenceChildPosition(state.getItemCount()) : this.findFirstReferenceChildPosition(state.getItemCount());
        anchorInfo.mPosition = n;
        anchorInfo.mOffset = Integer.MIN_VALUE;
        return true;
    }

    private void updateLayoutState(int n, RecyclerView.State object) {
        int n2;
        boolean bl;
        LayoutState layoutState = this.mLayoutState;
        boolean bl2 = false;
        layoutState.mAvailable = 0;
        this.mLayoutState.mCurrentPosition = n;
        if (this.isSmoothScrolling() && (n2 = object.getTargetScrollPosition()) != -1) {
            boolean bl3 = this.mShouldReverseLayout;
            bl = n2 < n;
            if (bl3 == bl) {
                n2 = this.mPrimaryOrientation.getTotalSpace();
                n = 0;
            } else {
                n = this.mPrimaryOrientation.getTotalSpace();
                n2 = 0;
            }
        } else {
            n2 = n = 0;
        }
        if (this.getClipToPadding()) {
            this.mLayoutState.mStartLine = this.mPrimaryOrientation.getStartAfterPadding() - n;
            this.mLayoutState.mEndLine = this.mPrimaryOrientation.getEndAfterPadding() + n2;
        } else {
            this.mLayoutState.mEndLine = this.mPrimaryOrientation.getEnd() + n2;
            this.mLayoutState.mStartLine = - n;
        }
        this.mLayoutState.mStopInFocusable = false;
        this.mLayoutState.mRecycle = true;
        object = this.mLayoutState;
        bl = bl2;
        if (this.mPrimaryOrientation.getMode() == 0) {
            bl = bl2;
            if (this.mPrimaryOrientation.getEnd() == 0) {
                bl = true;
            }
        }
        object.mInfinite = bl;
    }

    private void updateRemainingSpans(Span span, int n, int n2) {
        int n3 = span.getDeletedSize();
        if (n == -1) {
            if (span.getStartLine() + n3 <= n2) {
                this.mRemainingSpans.set(span.mIndex, false);
                return;
            }
        } else if (span.getEndLine() - n3 >= n2) {
            this.mRemainingSpans.set(span.mIndex, false);
        }
    }

    private int updateSpecWithExtra(int n, int n2, int n3) {
        if (n2 == 0 && n3 == 0) {
            return n;
        }
        int n4 = View.MeasureSpec.getMode((int)n);
        if (n4 != Integer.MIN_VALUE && n4 != 1073741824) {
            return n;
        }
        return View.MeasureSpec.makeMeasureSpec((int)Math.max(0, View.MeasureSpec.getSize((int)n) - n2 - n3), (int)n4);
    }

    boolean areAllEndsEqual() {
        int n = this.mSpans[0].getEndLine(Integer.MIN_VALUE);
        for (int i = 1; i < this.mSpanCount; ++i) {
            if (this.mSpans[i].getEndLine(Integer.MIN_VALUE) == n) continue;
            return false;
        }
        return true;
    }

    boolean areAllStartsEqual() {
        int n = this.mSpans[0].getStartLine(Integer.MIN_VALUE);
        for (int i = 1; i < this.mSpanCount; ++i) {
            if (this.mSpans[i].getStartLine(Integer.MIN_VALUE) == n) continue;
            return false;
        }
        return true;
    }

    @Override
    public void assertNotInLayoutOrScroll(String string) {
        if (this.mPendingSavedState == null) {
            super.assertNotInLayoutOrScroll(string);
        }
    }

    @Override
    public boolean canScrollHorizontally() {
        if (this.mOrientation == 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canScrollVertically() {
        if (this.mOrientation == 1) {
            return true;
        }
        return false;
    }

    boolean checkForGaps() {
        if (this.getChildCount() != 0 && this.mGapStrategy != 0) {
            int n;
            int n2;
            if (!this.isAttachedToWindow()) {
                return false;
            }
            if (this.mShouldReverseLayout) {
                n2 = this.getLastChildPosition();
                n = this.getFirstChildPosition();
            } else {
                n2 = this.getFirstChildPosition();
                n = this.getLastChildPosition();
            }
            if (n2 == 0 && this.hasGapsToFix() != null) {
                this.mLazySpanLookup.clear();
                this.requestSimpleAnimationsInNextLayout();
                this.requestLayout();
                return true;
            }
            if (!this.mLaidOutInvalidFullSpan) {
                return false;
            }
            int n3 = this.mShouldReverseLayout ? -1 : 1;
            Object object = this.mLazySpanLookup;
            if ((object = object.getFirstFullSpanItemInRange(n2, ++n, n3, true)) == null) {
                this.mLaidOutInvalidFullSpan = false;
                this.mLazySpanLookup.forceInvalidateAfter(n);
                return false;
            }
            LazySpanLookup.FullSpanItem fullSpanItem = this.mLazySpanLookup.getFirstFullSpanItemInRange(n2, object.mPosition, n3 * -1, true);
            if (fullSpanItem == null) {
                this.mLazySpanLookup.forceInvalidateAfter(object.mPosition);
            } else {
                this.mLazySpanLookup.forceInvalidateAfter(fullSpanItem.mPosition + 1);
            }
            this.requestSimpleAnimationsInNextLayout();
            this.requestLayout();
            return true;
        }
        return false;
    }

    @Override
    public boolean checkLayoutParams(RecyclerView.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY})
    @Override
    public void collectAdjacentPrefetchPositions(int n, int n2, RecyclerView.State state, RecyclerView.LayoutManager.LayoutPrefetchRegistry layoutPrefetchRegistry) {
        if (this.mOrientation != 0) {
            n = n2;
        }
        if (this.getChildCount() != 0) {
            if (n == 0) {
                return;
            }
            this.prepareLayoutStateForDelta(n, state);
            if (this.mPrefetchDistances == null || this.mPrefetchDistances.length < this.mSpanCount) {
                this.mPrefetchDistances = new int[this.mSpanCount];
            }
            int n3 = 0;
            n = n2 = 0;
            while (n2 < this.mSpanCount) {
                int n4 = this.mLayoutState.mItemDirection == -1 ? this.mLayoutState.mStartLine - this.mSpans[n2].getStartLine(this.mLayoutState.mStartLine) : this.mSpans[n2].getEndLine(this.mLayoutState.mEndLine) - this.mLayoutState.mEndLine;
                int n5 = n;
                if (n4 >= 0) {
                    this.mPrefetchDistances[n] = n4;
                    n5 = n + 1;
                }
                ++n2;
                n = n5;
            }
            Arrays.sort(this.mPrefetchDistances, 0, n);
            for (n2 = n3; n2 < n && this.mLayoutState.hasMore(state); ++n2) {
                layoutPrefetchRegistry.addPosition(this.mLayoutState.mCurrentPosition, this.mPrefetchDistances[n2]);
                LayoutState layoutState = this.mLayoutState;
                layoutState.mCurrentPosition += this.mLayoutState.mItemDirection;
            }
            return;
        }
    }

    @Override
    public int computeHorizontalScrollExtent(RecyclerView.State state) {
        return this.computeScrollExtent(state);
    }

    @Override
    public int computeHorizontalScrollOffset(RecyclerView.State state) {
        return this.computeScrollOffset(state);
    }

    @Override
    public int computeHorizontalScrollRange(RecyclerView.State state) {
        return this.computeScrollRange(state);
    }

    @Override
    public PointF computeScrollVectorForPosition(int n) {
        n = this.calculateScrollDirectionForPosition(n);
        PointF pointF = new PointF();
        if (n == 0) {
            return null;
        }
        if (this.mOrientation == 0) {
            pointF.x = n;
            pointF.y = 0.0f;
            return pointF;
        }
        pointF.x = 0.0f;
        pointF.y = n;
        return pointF;
    }

    @Override
    public int computeVerticalScrollExtent(RecyclerView.State state) {
        return this.computeScrollExtent(state);
    }

    @Override
    public int computeVerticalScrollOffset(RecyclerView.State state) {
        return this.computeScrollOffset(state);
    }

    @Override
    public int computeVerticalScrollRange(RecyclerView.State state) {
        return this.computeScrollRange(state);
    }

    public int[] findFirstCompletelyVisibleItemPositions(int[] arrn) {
        Object object;
        if (arrn == null) {
            object = new int[this.mSpanCount];
        } else {
            object = arrn;
            if (arrn.length < this.mSpanCount) {
                object = new StringBuilder();
                object.append("Provided int[]'s size must be more than or equal to span count. Expected:");
                object.append(this.mSpanCount);
                object.append(", array size:");
                object.append(arrn.length);
                throw new IllegalArgumentException(object.toString());
            }
        }
        for (int i = 0; i < this.mSpanCount; ++i) {
            object[i] = this.mSpans[i].findFirstCompletelyVisibleItemPosition();
        }
        return object;
    }

    View findFirstVisibleItemClosestToEnd(boolean bl) {
        int n = this.mPrimaryOrientation.getStartAfterPadding();
        int n2 = this.mPrimaryOrientation.getEndAfterPadding();
        View view = null;
        for (int i = this.getChildCount() - 1; i >= 0; --i) {
            View view2 = this.getChildAt(i);
            int n3 = this.mPrimaryOrientation.getDecoratedStart(view2);
            int n4 = this.mPrimaryOrientation.getDecoratedEnd(view2);
            View view3 = view;
            if (n4 > n) {
                if (n3 >= n2) {
                    view3 = view;
                } else if (n4 > n2) {
                    if (!bl) {
                        return view2;
                    }
                    view3 = view;
                    if (view == null) {
                        view3 = view2;
                    }
                } else {
                    return view2;
                }
            }
            view = view3;
        }
        return view;
    }

    View findFirstVisibleItemClosestToStart(boolean bl) {
        int n = this.mPrimaryOrientation.getStartAfterPadding();
        int n2 = this.mPrimaryOrientation.getEndAfterPadding();
        int n3 = this.getChildCount();
        View view = null;
        for (int i = 0; i < n3; ++i) {
            View view2 = this.getChildAt(i);
            int n4 = this.mPrimaryOrientation.getDecoratedStart(view2);
            View view3 = view;
            if (this.mPrimaryOrientation.getDecoratedEnd(view2) > n) {
                if (n4 >= n2) {
                    view3 = view;
                } else if (n4 < n) {
                    if (!bl) {
                        return view2;
                    }
                    view3 = view;
                    if (view == null) {
                        view3 = view2;
                    }
                } else {
                    return view2;
                }
            }
            view = view3;
        }
        return view;
    }

    int findFirstVisibleItemPositionInt() {
        View view = this.mShouldReverseLayout ? this.findFirstVisibleItemClosestToEnd(true) : this.findFirstVisibleItemClosestToStart(true);
        if (view == null) {
            return -1;
        }
        return this.getPosition(view);
    }

    public int[] findFirstVisibleItemPositions(int[] arrn) {
        Object object;
        if (arrn == null) {
            object = new int[this.mSpanCount];
        } else {
            object = arrn;
            if (arrn.length < this.mSpanCount) {
                object = new StringBuilder();
                object.append("Provided int[]'s size must be more than or equal to span count. Expected:");
                object.append(this.mSpanCount);
                object.append(", array size:");
                object.append(arrn.length);
                throw new IllegalArgumentException(object.toString());
            }
        }
        for (int i = 0; i < this.mSpanCount; ++i) {
            object[i] = this.mSpans[i].findFirstVisibleItemPosition();
        }
        return object;
    }

    public int[] findLastCompletelyVisibleItemPositions(int[] arrn) {
        Object object;
        if (arrn == null) {
            object = new int[this.mSpanCount];
        } else {
            object = arrn;
            if (arrn.length < this.mSpanCount) {
                object = new StringBuilder();
                object.append("Provided int[]'s size must be more than or equal to span count. Expected:");
                object.append(this.mSpanCount);
                object.append(", array size:");
                object.append(arrn.length);
                throw new IllegalArgumentException(object.toString());
            }
        }
        for (int i = 0; i < this.mSpanCount; ++i) {
            object[i] = this.mSpans[i].findLastCompletelyVisibleItemPosition();
        }
        return object;
    }

    public int[] findLastVisibleItemPositions(int[] arrn) {
        Object object;
        if (arrn == null) {
            object = new int[this.mSpanCount];
        } else {
            object = arrn;
            if (arrn.length < this.mSpanCount) {
                object = new StringBuilder();
                object.append("Provided int[]'s size must be more than or equal to span count. Expected:");
                object.append(this.mSpanCount);
                object.append(", array size:");
                object.append(arrn.length);
                throw new IllegalArgumentException(object.toString());
            }
        }
        for (int i = 0; i < this.mSpanCount; ++i) {
            object[i] = this.mSpans[i].findLastVisibleItemPosition();
        }
        return object;
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
        return super.getColumnCountForAccessibility(recycler, state);
    }

    int getFirstChildPosition() {
        if (this.getChildCount() == 0) {
            return 0;
        }
        return this.getPosition(this.getChildAt(0));
    }

    public int getGapStrategy() {
        return this.mGapStrategy;
    }

    int getLastChildPosition() {
        int n = this.getChildCount();
        if (n == 0) {
            return 0;
        }
        return this.getPosition(this.getChildAt(n - 1));
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public boolean getReverseLayout() {
        return this.mReverseLayout;
    }

    @Override
    public int getRowCountForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (this.mOrientation == 0) {
            return this.mSpanCount;
        }
        return super.getRowCountForAccessibility(recycler, state);
    }

    public int getSpanCount() {
        return this.mSpanCount;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    View hasGapsToFix() {
        var1_1 = this.getChildCount() - 1;
        var7_2 = new BitSet(this.mSpanCount);
        var7_2.set(0, this.mSpanCount, true);
        var2_3 = this.mOrientation;
        var5_4 = -1;
        var2_3 = var2_3 == 1 && this.isLayoutRTL() != false ? 1 : -1;
        if (this.mShouldReverseLayout) {
            var3_5 = -1;
        } else {
            var3_5 = var1_1 + 1;
            var1_1 = 0;
        }
        var4_6 = var1_1;
        if (var1_1 < var3_5) {
            var5_4 = 1;
            var4_6 = var1_1;
        }
        while (var4_6 != var3_5) {
            block12 : {
                block13 : {
                    var8_8 = this.getChildAt(var4_6);
                    var9_9 = (LayoutParams)var8_8.getLayoutParams();
                    if (var7_2.get(var9_9.mSpan.mIndex)) {
                        if (this.checkSpanForGap(var9_9.mSpan)) {
                            return var8_8;
                        }
                        var7_2.clear(var9_9.mSpan.mIndex);
                    }
                    if (var9_9.mFullSpan || (var1_1 = var4_6 + var5_4) == var3_5) break block12;
                    var10_11 = this.getChildAt(var1_1);
                    if (!this.mShouldReverseLayout) break block13;
                    var1_1 = this.mPrimaryOrientation.getDecoratedEnd(var8_8);
                    if (var1_1 < (var6_7 = this.mPrimaryOrientation.getDecoratedEnd(var10_11))) {
                        return var8_8;
                    }
                    if (var1_1 != var6_7) ** GOTO lbl-1000
                    ** GOTO lbl-1000
                }
                var1_1 = this.mPrimaryOrientation.getDecoratedStart(var8_8);
                if (var1_1 > (var6_7 = this.mPrimaryOrientation.getDecoratedStart(var10_11))) {
                    return var8_8;
                }
                if (var1_1 == var6_7) lbl-1000: // 2 sources:
                {
                    var1_1 = 1;
                } else lbl-1000: // 2 sources:
                {
                    var1_1 = 0;
                }
                if (var1_1 != 0) {
                    var10_12 = (LayoutParams)var10_11.getLayoutParams();
                    var1_1 = var9_9.mSpan.mIndex - var10_12.mSpan.mIndex < 0 ? 1 : 0;
                    var6_7 = var2_3 < 0 ? 1 : 0;
                    if (var1_1 != var6_7) {
                        return var8_8;
                    }
                }
            }
            var4_6 += var5_4;
        }
        return null;
    }

    public void invalidateSpanAssignments() {
        this.mLazySpanLookup.clear();
        this.requestLayout();
    }

    @Override
    public boolean isAutoMeasureEnabled() {
        if (this.mGapStrategy != 0) {
            return true;
        }
        return false;
    }

    boolean isLayoutRTL() {
        if (this.getLayoutDirection() == 1) {
            return true;
        }
        return false;
    }

    @Override
    public void offsetChildrenHorizontal(int n) {
        super.offsetChildrenHorizontal(n);
        for (int i = 0; i < this.mSpanCount; ++i) {
            this.mSpans[i].onOffset(n);
        }
    }

    @Override
    public void offsetChildrenVertical(int n) {
        super.offsetChildrenVertical(n);
        for (int i = 0; i < this.mSpanCount; ++i) {
            this.mSpans[i].onOffset(n);
        }
    }

    @Override
    public void onDetachedFromWindow(RecyclerView recyclerView, RecyclerView.Recycler recycler) {
        super.onDetachedFromWindow(recyclerView, recycler);
        this.removeCallbacks(this.mCheckForGapsRunnable);
        for (int i = 0; i < this.mSpanCount; ++i) {
            this.mSpans[i].clear();
        }
        recyclerView.requestLayout();
    }

    @Nullable
    @Override
    public View onFocusSearchFailed(View view, int n, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int n2;
        if (this.getChildCount() == 0) {
            return null;
        }
        if ((view = this.findContainingItemView(view)) == null) {
            return null;
        }
        this.resolveShouldLayoutReverse();
        int n3 = this.convertFocusDirectionToLayoutDirection(n);
        if (n3 == Integer.MIN_VALUE) {
            return null;
        }
        Object object = (LayoutParams)view.getLayoutParams();
        boolean bl = object.mFullSpan;
        object = object.mSpan;
        n = n3 == 1 ? this.getLastChildPosition() : this.getFirstChildPosition();
        this.updateLayoutState(n, state);
        this.setLayoutStateDirection(n3);
        this.mLayoutState.mCurrentPosition = this.mLayoutState.mItemDirection + n;
        this.mLayoutState.mAvailable = (int)(0.33333334f * (float)this.mPrimaryOrientation.getTotalSpace());
        this.mLayoutState.mStopInFocusable = true;
        LayoutState layoutState = this.mLayoutState;
        int n4 = 0;
        layoutState.mRecycle = false;
        this.fill(recycler, this.mLayoutState, state);
        this.mLastLayoutFromEnd = this.mShouldReverseLayout;
        if (!bl && (recycler = object.getFocusableViewAfter(n, n3)) != null && recycler != view) {
            return recycler;
        }
        if (this.preferLastSpan(n3)) {
            for (n2 = this.mSpanCount - 1; n2 >= 0; --n2) {
                recycler = this.mSpans[n2].getFocusableViewAfter(n, n3);
                if (recycler == null || recycler == view) continue;
                return recycler;
            }
        } else {
            for (n2 = 0; n2 < this.mSpanCount; ++n2) {
                recycler = this.mSpans[n2].getFocusableViewAfter(n, n3);
                if (recycler == null || recycler == view) continue;
                return recycler;
            }
        }
        boolean bl2 = this.mReverseLayout;
        n = n3 == -1 ? 1 : 0;
        n = (bl2 ^ true) == n ? 1 : 0;
        if (!bl) {
            n2 = n != 0 ? object.findFirstPartiallyVisibleItemPosition() : object.findLastPartiallyVisibleItemPosition();
            recycler = this.findViewByPosition(n2);
            if (recycler != null && recycler != view) {
                return recycler;
            }
        }
        if (this.preferLastSpan(n3)) {
            for (n2 = this.mSpanCount - 1; n2 >= 0; --n2) {
                if (n2 == object.mIndex) continue;
                n4 = n != 0 ? this.mSpans[n2].findFirstPartiallyVisibleItemPosition() : this.mSpans[n2].findLastPartiallyVisibleItemPosition();
                recycler = this.findViewByPosition(n4);
                if (recycler == null || recycler == view) continue;
                return recycler;
            }
        } else {
            for (n2 = n4; n2 < this.mSpanCount; ++n2) {
                n4 = n != 0 ? this.mSpans[n2].findFirstPartiallyVisibleItemPosition() : this.mSpans[n2].findLastPartiallyVisibleItemPosition();
                recycler = this.findViewByPosition(n4);
                if (recycler == null || recycler == view) continue;
                return recycler;
            }
        }
        return null;
    }

    @Override
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        if (this.getChildCount() > 0) {
            View view = this.findFirstVisibleItemClosestToStart(false);
            View view2 = this.findFirstVisibleItemClosestToEnd(false);
            if (view != null) {
                int n;
                if (view2 == null) {
                    return;
                }
                int n2 = this.getPosition(view);
                if (n2 < (n = this.getPosition(view2))) {
                    accessibilityEvent.setFromIndex(n2);
                    accessibilityEvent.setToIndex(n);
                    return;
                }
                accessibilityEvent.setFromIndex(n);
                accessibilityEvent.setToIndex(n2);
                return;
            }
            return;
        }
    }

    @Override
    public void onInitializeAccessibilityNodeInfoForItem(RecyclerView.Recycler object, RecyclerView.State state, View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        object = view.getLayoutParams();
        if (!(object instanceof LayoutParams)) {
            super.onInitializeAccessibilityNodeInfoForItem(view, accessibilityNodeInfoCompat);
            return;
        }
        object = (LayoutParams)((Object)object);
        int n = this.mOrientation;
        int n2 = 1;
        int n3 = 1;
        if (n == 0) {
            n2 = object.getSpanIndex();
            if (object.mFullSpan) {
                n3 = this.mSpanCount;
            }
            accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(n2, n3, -1, -1, object.mFullSpan, false));
            return;
        }
        n = object.getSpanIndex();
        n3 = n2;
        if (object.mFullSpan) {
            n3 = this.mSpanCount;
        }
        accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(-1, -1, n, n3, object.mFullSpan, false));
    }

    @Override
    public void onItemsAdded(RecyclerView recyclerView, int n, int n2) {
        this.handleUpdate(n, n2, 1);
    }

    @Override
    public void onItemsChanged(RecyclerView recyclerView) {
        this.mLazySpanLookup.clear();
        this.requestLayout();
    }

    @Override
    public void onItemsMoved(RecyclerView recyclerView, int n, int n2, int n3) {
        this.handleUpdate(n, n2, 8);
    }

    @Override
    public void onItemsRemoved(RecyclerView recyclerView, int n, int n2) {
        this.handleUpdate(n, n2, 2);
    }

    @Override
    public void onItemsUpdated(RecyclerView recyclerView, int n, int n2, Object object) {
        this.handleUpdate(n, n2, 4);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        this.onLayoutChildren(recycler, state, true);
    }

    @Override
    public void onLayoutCompleted(RecyclerView.State state) {
        super.onLayoutCompleted(state);
        this.mPendingScrollPosition = -1;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        this.mPendingSavedState = null;
        this.mAnchorInfo.reset();
    }

    @Override
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            this.mPendingSavedState = (SavedState)parcelable;
            this.requestLayout();
        }
    }

    @Override
    public Parcelable onSaveInstanceState() {
        if (this.mPendingSavedState != null) {
            return new SavedState(this.mPendingSavedState);
        }
        SavedState savedState = new SavedState();
        savedState.mReverseLayout = this.mReverseLayout;
        savedState.mAnchorLayoutFromEnd = this.mLastLayoutFromEnd;
        savedState.mLastLayoutRTL = this.mLastLayoutRTL;
        LazySpanLookup lazySpanLookup = this.mLazySpanLookup;
        int n = 0;
        if (lazySpanLookup != null && this.mLazySpanLookup.mData != null) {
            savedState.mSpanLookup = this.mLazySpanLookup.mData;
            savedState.mSpanLookupSize = savedState.mSpanLookup.length;
            savedState.mFullSpanItems = this.mLazySpanLookup.mFullSpanItems;
        } else {
            savedState.mSpanLookupSize = 0;
        }
        if (this.getChildCount() > 0) {
            int n2 = this.mLastLayoutFromEnd ? this.getLastChildPosition() : this.getFirstChildPosition();
            savedState.mAnchorPosition = n2;
            savedState.mVisibleAnchorPosition = this.findFirstVisibleItemPositionInt();
            savedState.mSpanOffsetsSize = this.mSpanCount;
            savedState.mSpanOffsets = new int[this.mSpanCount];
            while (n < this.mSpanCount) {
                int n3;
                if (this.mLastLayoutFromEnd) {
                    n2 = n3 = this.mSpans[n].getEndLine(Integer.MIN_VALUE);
                    if (n3 != Integer.MIN_VALUE) {
                        n2 = n3 - this.mPrimaryOrientation.getEndAfterPadding();
                    }
                } else {
                    n2 = n3 = this.mSpans[n].getStartLine(Integer.MIN_VALUE);
                    if (n3 != Integer.MIN_VALUE) {
                        n2 = n3 - this.mPrimaryOrientation.getStartAfterPadding();
                    }
                }
                savedState.mSpanOffsets[n] = n2;
                ++n;
            }
        } else {
            savedState.mAnchorPosition = -1;
            savedState.mVisibleAnchorPosition = -1;
            savedState.mSpanOffsetsSize = 0;
        }
        return savedState;
    }

    @Override
    public void onScrollStateChanged(int n) {
        if (n == 0) {
            this.checkForGaps();
        }
    }

    void prepareLayoutStateForDelta(int n, RecyclerView.State state) {
        int n2;
        int n3;
        if (n > 0) {
            n3 = this.getLastChildPosition();
            n2 = 1;
        } else {
            n3 = this.getFirstChildPosition();
            n2 = -1;
        }
        this.mLayoutState.mRecycle = true;
        this.updateLayoutState(n3, state);
        this.setLayoutStateDirection(n2);
        this.mLayoutState.mCurrentPosition = n3 + this.mLayoutState.mItemDirection;
        this.mLayoutState.mAvailable = Math.abs(n);
    }

    int scrollBy(int n, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (this.getChildCount() != 0) {
            if (n == 0) {
                return 0;
            }
            this.prepareLayoutStateForDelta(n, state);
            int n2 = this.fill(recycler, this.mLayoutState, state);
            if (this.mLayoutState.mAvailable >= n2) {
                n = n < 0 ? - n2 : n2;
            }
            this.mPrimaryOrientation.offsetChildren(- n);
            this.mLastLayoutFromEnd = this.mShouldReverseLayout;
            this.mLayoutState.mAvailable = 0;
            this.recycle(recycler, this.mLayoutState);
            return n;
        }
        return 0;
    }

    @Override
    public int scrollHorizontallyBy(int n, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return this.scrollBy(n, recycler, state);
    }

    @Override
    public void scrollToPosition(int n) {
        if (this.mPendingSavedState != null && this.mPendingSavedState.mAnchorPosition != n) {
            this.mPendingSavedState.invalidateAnchorPositionInfo();
        }
        this.mPendingScrollPosition = n;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        this.requestLayout();
    }

    public void scrollToPositionWithOffset(int n, int n2) {
        if (this.mPendingSavedState != null) {
            this.mPendingSavedState.invalidateAnchorPositionInfo();
        }
        this.mPendingScrollPosition = n;
        this.mPendingScrollPositionOffset = n2;
        this.requestLayout();
    }

    @Override
    public int scrollVerticallyBy(int n, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return this.scrollBy(n, recycler, state);
    }

    public void setGapStrategy(int n) {
        this.assertNotInLayoutOrScroll(null);
        if (n == this.mGapStrategy) {
            return;
        }
        if (n != 0 && n != 2) {
            throw new IllegalArgumentException("invalid gap strategy. Must be GAP_HANDLING_NONE or GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS");
        }
        this.mGapStrategy = n;
        this.requestLayout();
    }

    @Override
    public void setMeasuredDimension(Rect rect, int n, int n2) {
        int n3 = this.getPaddingLeft() + this.getPaddingRight();
        int n4 = this.getPaddingTop() + this.getPaddingBottom();
        if (this.mOrientation == 1) {
            n2 = StaggeredGridLayoutManager.chooseSize(n2, rect.height() + n4, this.getMinimumHeight());
            n3 = StaggeredGridLayoutManager.chooseSize(n, this.mSizePerSpan * this.mSpanCount + n3, this.getMinimumWidth());
            n = n2;
            n2 = n3;
        } else {
            n = StaggeredGridLayoutManager.chooseSize(n, rect.width() + n3, this.getMinimumWidth());
            n3 = StaggeredGridLayoutManager.chooseSize(n2, this.mSizePerSpan * this.mSpanCount + n4, this.getMinimumHeight());
            n2 = n;
            n = n3;
        }
        this.setMeasuredDimension(n2, n);
    }

    public void setOrientation(int n) {
        if (n != 0 && n != 1) {
            throw new IllegalArgumentException("invalid orientation.");
        }
        this.assertNotInLayoutOrScroll(null);
        if (n == this.mOrientation) {
            return;
        }
        this.mOrientation = n;
        OrientationHelper orientationHelper = this.mPrimaryOrientation;
        this.mPrimaryOrientation = this.mSecondaryOrientation;
        this.mSecondaryOrientation = orientationHelper;
        this.requestLayout();
    }

    public void setReverseLayout(boolean bl) {
        this.assertNotInLayoutOrScroll(null);
        if (this.mPendingSavedState != null && this.mPendingSavedState.mReverseLayout != bl) {
            this.mPendingSavedState.mReverseLayout = bl;
        }
        this.mReverseLayout = bl;
        this.requestLayout();
    }

    public void setSpanCount(int n) {
        this.assertNotInLayoutOrScroll(null);
        if (n != this.mSpanCount) {
            this.invalidateSpanAssignments();
            this.mSpanCount = n;
            this.mRemainingSpans = new BitSet(this.mSpanCount);
            this.mSpans = new Span[this.mSpanCount];
            for (n = 0; n < this.mSpanCount; ++n) {
                this.mSpans[n] = new Span(n);
            }
            this.requestLayout();
        }
    }

    @Override
    public void smoothScrollToPosition(RecyclerView object, RecyclerView.State state, int n) {
        object = new LinearSmoothScroller(object.getContext());
        object.setTargetPosition(n);
        this.startSmoothScroll((RecyclerView.SmoothScroller)object);
    }

    @Override
    public boolean supportsPredictiveItemAnimations() {
        if (this.mPendingSavedState == null) {
            return true;
        }
        return false;
    }

    boolean updateAnchorFromPendingData(RecyclerView.State state, AnchorInfo anchorInfo) {
        boolean bl = state.isPreLayout();
        boolean bl2 = false;
        if (!bl) {
            if (this.mPendingScrollPosition == -1) {
                return false;
            }
            if (this.mPendingScrollPosition >= 0 && this.mPendingScrollPosition < state.getItemCount()) {
                if (this.mPendingSavedState != null && this.mPendingSavedState.mAnchorPosition != -1 && this.mPendingSavedState.mSpanOffsetsSize >= 1) {
                    anchorInfo.mOffset = Integer.MIN_VALUE;
                    anchorInfo.mPosition = this.mPendingScrollPosition;
                    return true;
                }
                state = this.findViewByPosition(this.mPendingScrollPosition);
                if (state != null) {
                    int n = this.mShouldReverseLayout ? this.getLastChildPosition() : this.getFirstChildPosition();
                    anchorInfo.mPosition = n;
                    if (this.mPendingScrollPositionOffset != Integer.MIN_VALUE) {
                        if (anchorInfo.mLayoutFromEnd) {
                            anchorInfo.mOffset = this.mPrimaryOrientation.getEndAfterPadding() - this.mPendingScrollPositionOffset - this.mPrimaryOrientation.getDecoratedEnd((View)state);
                            return true;
                        }
                        anchorInfo.mOffset = this.mPrimaryOrientation.getStartAfterPadding() + this.mPendingScrollPositionOffset - this.mPrimaryOrientation.getDecoratedStart((View)state);
                        return true;
                    }
                    if (this.mPrimaryOrientation.getDecoratedMeasurement((View)state) > this.mPrimaryOrientation.getTotalSpace()) {
                        n = anchorInfo.mLayoutFromEnd ? this.mPrimaryOrientation.getEndAfterPadding() : this.mPrimaryOrientation.getStartAfterPadding();
                        anchorInfo.mOffset = n;
                        return true;
                    }
                    n = this.mPrimaryOrientation.getDecoratedStart((View)state) - this.mPrimaryOrientation.getStartAfterPadding();
                    if (n < 0) {
                        anchorInfo.mOffset = - n;
                        return true;
                    }
                    n = this.mPrimaryOrientation.getEndAfterPadding() - this.mPrimaryOrientation.getDecoratedEnd((View)state);
                    if (n < 0) {
                        anchorInfo.mOffset = n;
                        return true;
                    }
                    anchorInfo.mOffset = Integer.MIN_VALUE;
                    return true;
                }
                anchorInfo.mPosition = this.mPendingScrollPosition;
                if (this.mPendingScrollPositionOffset == Integer.MIN_VALUE) {
                    if (this.calculateScrollDirectionForPosition(anchorInfo.mPosition) == 1) {
                        bl2 = true;
                    }
                    anchorInfo.mLayoutFromEnd = bl2;
                    anchorInfo.assignCoordinateFromPadding();
                } else {
                    anchorInfo.assignCoordinateFromPadding(this.mPendingScrollPositionOffset);
                }
                anchorInfo.mInvalidateOffsets = true;
                return true;
            }
            this.mPendingScrollPosition = -1;
            this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
            return false;
        }
        return false;
    }

    void updateAnchorInfoForLayout(RecyclerView.State state, AnchorInfo anchorInfo) {
        if (this.updateAnchorFromPendingData(state, anchorInfo)) {
            return;
        }
        if (this.updateAnchorFromChildren(state, anchorInfo)) {
            return;
        }
        anchorInfo.assignCoordinateFromPadding();
        anchorInfo.mPosition = 0;
    }

    void updateMeasureSpecs(int n) {
        this.mSizePerSpan = n / this.mSpanCount;
        this.mFullSizeSpec = View.MeasureSpec.makeMeasureSpec((int)n, (int)this.mSecondaryOrientation.getMode());
    }

    class AnchorInfo {
        boolean mInvalidateOffsets;
        boolean mLayoutFromEnd;
        int mOffset;
        int mPosition;
        int[] mSpanReferenceLines;
        boolean mValid;

        AnchorInfo() {
            this.reset();
        }

        void assignCoordinateFromPadding() {
            int n = this.mLayoutFromEnd ? StaggeredGridLayoutManager.this.mPrimaryOrientation.getEndAfterPadding() : StaggeredGridLayoutManager.this.mPrimaryOrientation.getStartAfterPadding();
            this.mOffset = n;
        }

        void assignCoordinateFromPadding(int n) {
            if (this.mLayoutFromEnd) {
                this.mOffset = StaggeredGridLayoutManager.this.mPrimaryOrientation.getEndAfterPadding() - n;
                return;
            }
            this.mOffset = StaggeredGridLayoutManager.this.mPrimaryOrientation.getStartAfterPadding() + n;
        }

        void reset() {
            this.mPosition = -1;
            this.mOffset = Integer.MIN_VALUE;
            this.mLayoutFromEnd = false;
            this.mInvalidateOffsets = false;
            this.mValid = false;
            if (this.mSpanReferenceLines != null) {
                Arrays.fill(this.mSpanReferenceLines, -1);
            }
        }

        void saveSpanReferenceLines(Span[] arrspan) {
            int n = arrspan.length;
            if (this.mSpanReferenceLines == null || this.mSpanReferenceLines.length < n) {
                this.mSpanReferenceLines = new int[StaggeredGridLayoutManager.this.mSpans.length];
            }
            for (int i = 0; i < n; ++i) {
                this.mSpanReferenceLines[i] = arrspan[i].getStartLine(Integer.MIN_VALUE);
            }
        }
    }

    public static class LayoutParams
    extends RecyclerView.LayoutParams {
        public static final int INVALID_SPAN_ID = -1;
        boolean mFullSpan;
        Span mSpan;

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

        public final int getSpanIndex() {
            if (this.mSpan == null) {
                return -1;
            }
            return this.mSpan.mIndex;
        }

        public boolean isFullSpan() {
            return this.mFullSpan;
        }

        public void setFullSpan(boolean bl) {
            this.mFullSpan = bl;
        }
    }

    static class LazySpanLookup {
        private static final int MIN_SIZE = 10;
        int[] mData;
        List<FullSpanItem> mFullSpanItems;

        LazySpanLookup() {
        }

        private int invalidateFullSpansAfter(int n) {
            FullSpanItem fullSpanItem;
            int n2;
            block5 : {
                if (this.mFullSpanItems == null) {
                    return -1;
                }
                fullSpanItem = this.getFullSpanItem(n);
                if (fullSpanItem != null) {
                    this.mFullSpanItems.remove(fullSpanItem);
                }
                int n3 = this.mFullSpanItems.size();
                for (n2 = 0; n2 < n3; ++n2) {
                    if (this.mFullSpanItems.get((int)n2).mPosition < n) {
                        continue;
                    }
                    break block5;
                }
                n2 = -1;
            }
            if (n2 != -1) {
                fullSpanItem = this.mFullSpanItems.get(n2);
                this.mFullSpanItems.remove(n2);
                return fullSpanItem.mPosition;
            }
            return -1;
        }

        private void offsetFullSpansForAddition(int n, int n2) {
            if (this.mFullSpanItems == null) {
                return;
            }
            for (int i = this.mFullSpanItems.size() - 1; i >= 0; --i) {
                FullSpanItem fullSpanItem = this.mFullSpanItems.get(i);
                if (fullSpanItem.mPosition < n) continue;
                fullSpanItem.mPosition += n2;
            }
        }

        private void offsetFullSpansForRemoval(int n, int n2) {
            if (this.mFullSpanItems == null) {
                return;
            }
            for (int i = this.mFullSpanItems.size() - 1; i >= 0; --i) {
                FullSpanItem fullSpanItem = this.mFullSpanItems.get(i);
                if (fullSpanItem.mPosition < n) continue;
                if (fullSpanItem.mPosition < n + n2) {
                    this.mFullSpanItems.remove(i);
                    continue;
                }
                fullSpanItem.mPosition -= n2;
            }
        }

        public void addFullSpanItem(FullSpanItem fullSpanItem) {
            if (this.mFullSpanItems == null) {
                this.mFullSpanItems = new ArrayList<FullSpanItem>();
            }
            int n = this.mFullSpanItems.size();
            for (int i = 0; i < n; ++i) {
                FullSpanItem fullSpanItem2 = this.mFullSpanItems.get(i);
                if (fullSpanItem2.mPosition == fullSpanItem.mPosition) {
                    this.mFullSpanItems.remove(i);
                }
                if (fullSpanItem2.mPosition < fullSpanItem.mPosition) continue;
                this.mFullSpanItems.add(i, fullSpanItem);
                return;
            }
            this.mFullSpanItems.add(fullSpanItem);
        }

        void clear() {
            if (this.mData != null) {
                Arrays.fill(this.mData, -1);
            }
            this.mFullSpanItems = null;
        }

        void ensureSize(int n) {
            if (this.mData == null) {
                this.mData = new int[Math.max(n, 10) + 1];
                Arrays.fill(this.mData, -1);
                return;
            }
            if (n >= this.mData.length) {
                int[] arrn = this.mData;
                this.mData = new int[this.sizeForPosition(n)];
                System.arraycopy(arrn, 0, this.mData, 0, arrn.length);
                Arrays.fill(this.mData, arrn.length, this.mData.length, -1);
            }
        }

        int forceInvalidateAfter(int n) {
            if (this.mFullSpanItems != null) {
                for (int i = this.mFullSpanItems.size() - 1; i >= 0; --i) {
                    if (this.mFullSpanItems.get((int)i).mPosition < n) continue;
                    this.mFullSpanItems.remove(i);
                }
            }
            return this.invalidateAfter(n);
        }

        public FullSpanItem getFirstFullSpanItemInRange(int n, int n2, int n3, boolean bl) {
            if (this.mFullSpanItems == null) {
                return null;
            }
            int n4 = this.mFullSpanItems.size();
            for (int i = 0; i < n4; ++i) {
                FullSpanItem fullSpanItem = this.mFullSpanItems.get(i);
                if (fullSpanItem.mPosition >= n2) {
                    return null;
                }
                if (fullSpanItem.mPosition < n || n3 != 0 && fullSpanItem.mGapDir != n3 && (!bl || !fullSpanItem.mHasUnwantedGapAfter)) continue;
                return fullSpanItem;
            }
            return null;
        }

        public FullSpanItem getFullSpanItem(int n) {
            if (this.mFullSpanItems == null) {
                return null;
            }
            for (int i = this.mFullSpanItems.size() - 1; i >= 0; --i) {
                FullSpanItem fullSpanItem = this.mFullSpanItems.get(i);
                if (fullSpanItem.mPosition != n) continue;
                return fullSpanItem;
            }
            return null;
        }

        int getSpan(int n) {
            if (this.mData != null && n < this.mData.length) {
                return this.mData[n];
            }
            return -1;
        }

        int invalidateAfter(int n) {
            if (this.mData == null) {
                return -1;
            }
            if (n >= this.mData.length) {
                return -1;
            }
            int n2 = this.invalidateFullSpansAfter(n);
            if (n2 == -1) {
                Arrays.fill(this.mData, n, this.mData.length, -1);
                return this.mData.length;
            }
            int[] arrn = this.mData;
            Arrays.fill(arrn, n, ++n2, -1);
            return n2;
        }

        void offsetForAddition(int n, int n2) {
            if (this.mData != null) {
                if (n >= this.mData.length) {
                    return;
                }
                int n3 = n + n2;
                this.ensureSize(n3);
                System.arraycopy(this.mData, n, this.mData, n3, this.mData.length - n - n2);
                Arrays.fill(this.mData, n, n3, -1);
                this.offsetFullSpansForAddition(n, n2);
                return;
            }
        }

        void offsetForRemoval(int n, int n2) {
            if (this.mData != null) {
                if (n >= this.mData.length) {
                    return;
                }
                int n3 = n + n2;
                this.ensureSize(n3);
                System.arraycopy(this.mData, n3, this.mData, n, this.mData.length - n - n2);
                Arrays.fill(this.mData, this.mData.length - n2, this.mData.length, -1);
                this.offsetFullSpansForRemoval(n, n2);
                return;
            }
        }

        void setSpan(int n, Span span) {
            this.ensureSize(n);
            this.mData[n] = span.mIndex;
        }

        int sizeForPosition(int n) {
            int n2;
            for (n2 = this.mData.length; n2 <= n; n2 *= 2) {
            }
            return n2;
        }

        static class FullSpanItem
        implements Parcelable {
            public static final Parcelable.Creator<FullSpanItem> CREATOR = new Parcelable.Creator<FullSpanItem>(){

                public FullSpanItem createFromParcel(Parcel parcel) {
                    return new FullSpanItem(parcel);
                }

                public FullSpanItem[] newArray(int n) {
                    return new FullSpanItem[n];
                }
            };
            int mGapDir;
            int[] mGapPerSpan;
            boolean mHasUnwantedGapAfter;
            int mPosition;

            FullSpanItem() {
            }

            FullSpanItem(Parcel parcel) {
                this.mPosition = parcel.readInt();
                this.mGapDir = parcel.readInt();
                int n = parcel.readInt();
                boolean bl = true;
                if (n != 1) {
                    bl = false;
                }
                this.mHasUnwantedGapAfter = bl;
                n = parcel.readInt();
                if (n > 0) {
                    this.mGapPerSpan = new int[n];
                    parcel.readIntArray(this.mGapPerSpan);
                }
            }

            public int describeContents() {
                return 0;
            }

            int getGapForSpan(int n) {
                if (this.mGapPerSpan == null) {
                    return 0;
                }
                return this.mGapPerSpan[n];
            }

            public String toString() {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("FullSpanItem{mPosition=");
                stringBuilder.append(this.mPosition);
                stringBuilder.append(", mGapDir=");
                stringBuilder.append(this.mGapDir);
                stringBuilder.append(", mHasUnwantedGapAfter=");
                stringBuilder.append(this.mHasUnwantedGapAfter);
                stringBuilder.append(", mGapPerSpan=");
                stringBuilder.append(Arrays.toString(this.mGapPerSpan));
                stringBuilder.append('}');
                return stringBuilder.toString();
            }

            public void writeToParcel(Parcel parcel, int n) {
                RuntimeException runtimeException;
                super("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e1expr(TypeTransformer.java:496)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:713)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:810)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:840)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
                throw runtimeException;
            }

        }

    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public static class SavedState
    implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int n) {
                return new SavedState[n];
            }
        };
        boolean mAnchorLayoutFromEnd;
        int mAnchorPosition;
        List<LazySpanLookup.FullSpanItem> mFullSpanItems;
        boolean mLastLayoutRTL;
        boolean mReverseLayout;
        int[] mSpanLookup;
        int mSpanLookupSize;
        int[] mSpanOffsets;
        int mSpanOffsetsSize;
        int mVisibleAnchorPosition;

        public SavedState() {
        }

        SavedState(Parcel parcel) {
            this.mAnchorPosition = parcel.readInt();
            this.mVisibleAnchorPosition = parcel.readInt();
            this.mSpanOffsetsSize = parcel.readInt();
            if (this.mSpanOffsetsSize > 0) {
                this.mSpanOffsets = new int[this.mSpanOffsetsSize];
                parcel.readIntArray(this.mSpanOffsets);
            }
            this.mSpanLookupSize = parcel.readInt();
            if (this.mSpanLookupSize > 0) {
                this.mSpanLookup = new int[this.mSpanLookupSize];
                parcel.readIntArray(this.mSpanLookup);
            }
            int n = parcel.readInt();
            boolean bl = false;
            boolean bl2 = n == 1;
            this.mReverseLayout = bl2;
            bl2 = parcel.readInt() == 1;
            this.mAnchorLayoutFromEnd = bl2;
            bl2 = bl;
            if (parcel.readInt() == 1) {
                bl2 = true;
            }
            this.mLastLayoutRTL = bl2;
            this.mFullSpanItems = parcel.readArrayList(LazySpanLookup.FullSpanItem.class.getClassLoader());
        }

        public SavedState(SavedState savedState) {
            this.mSpanOffsetsSize = savedState.mSpanOffsetsSize;
            this.mAnchorPosition = savedState.mAnchorPosition;
            this.mVisibleAnchorPosition = savedState.mVisibleAnchorPosition;
            this.mSpanOffsets = savedState.mSpanOffsets;
            this.mSpanLookupSize = savedState.mSpanLookupSize;
            this.mSpanLookup = savedState.mSpanLookup;
            this.mReverseLayout = savedState.mReverseLayout;
            this.mAnchorLayoutFromEnd = savedState.mAnchorLayoutFromEnd;
            this.mLastLayoutRTL = savedState.mLastLayoutRTL;
            this.mFullSpanItems = savedState.mFullSpanItems;
        }

        public int describeContents() {
            return 0;
        }

        void invalidateAnchorPositionInfo() {
            this.mSpanOffsets = null;
            this.mSpanOffsetsSize = 0;
            this.mAnchorPosition = -1;
            this.mVisibleAnchorPosition = -1;
        }

        void invalidateSpanInfo() {
            this.mSpanOffsets = null;
            this.mSpanOffsetsSize = 0;
            this.mSpanLookupSize = 0;
            this.mSpanLookup = null;
            this.mFullSpanItems = null;
        }

        public void writeToParcel(Parcel parcel, int n) {
            RuntimeException runtimeException;
            super("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e1expr(TypeTransformer.java:496)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:713)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:810)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:840)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
            throw runtimeException;
        }

    }

    class Span {
        static final int INVALID_LINE = Integer.MIN_VALUE;
        int mCachedEnd = Integer.MIN_VALUE;
        int mCachedStart = Integer.MIN_VALUE;
        int mDeletedSize = 0;
        final int mIndex;
        ArrayList<View> mViews = new ArrayList();

        Span(int n) {
            this.mIndex = n;
        }

        void appendToSpan(View view) {
            LayoutParams layoutParams = this.getLayoutParams(view);
            layoutParams.mSpan = this;
            this.mViews.add(view);
            this.mCachedEnd = Integer.MIN_VALUE;
            if (this.mViews.size() == 1) {
                this.mCachedStart = Integer.MIN_VALUE;
            }
            if (layoutParams.isItemRemoved() || layoutParams.isItemChanged()) {
                this.mDeletedSize += StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedMeasurement(view);
            }
        }

        void cacheReferenceLineAndClear(boolean bl, int n) {
            int n2 = bl ? this.getEndLine(Integer.MIN_VALUE) : this.getStartLine(Integer.MIN_VALUE);
            this.clear();
            if (n2 == Integer.MIN_VALUE) {
                return;
            }
            if (bl && n2 < StaggeredGridLayoutManager.this.mPrimaryOrientation.getEndAfterPadding() || !bl && n2 > StaggeredGridLayoutManager.this.mPrimaryOrientation.getStartAfterPadding()) {
                return;
            }
            int n3 = n2;
            if (n != Integer.MIN_VALUE) {
                n3 = n2 + n;
            }
            this.mCachedEnd = n3;
            this.mCachedStart = n3;
        }

        void calculateCachedEnd() {
            Object object = this.mViews.get(this.mViews.size() - 1);
            LayoutParams layoutParams = this.getLayoutParams((View)object);
            this.mCachedEnd = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedEnd((View)object);
            if (layoutParams.mFullSpan && (object = StaggeredGridLayoutManager.this.mLazySpanLookup.getFullSpanItem(layoutParams.getViewLayoutPosition())) != null && object.mGapDir == 1) {
                this.mCachedEnd += object.getGapForSpan(this.mIndex);
            }
        }

        void calculateCachedStart() {
            Object object = this.mViews.get(0);
            LayoutParams layoutParams = this.getLayoutParams((View)object);
            this.mCachedStart = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedStart((View)object);
            if (layoutParams.mFullSpan && (object = StaggeredGridLayoutManager.this.mLazySpanLookup.getFullSpanItem(layoutParams.getViewLayoutPosition())) != null && object.mGapDir == -1) {
                this.mCachedStart -= object.getGapForSpan(this.mIndex);
            }
        }

        void clear() {
            this.mViews.clear();
            this.invalidateCache();
            this.mDeletedSize = 0;
        }

        public int findFirstCompletelyVisibleItemPosition() {
            if (StaggeredGridLayoutManager.this.mReverseLayout) {
                return this.findOneVisibleChild(this.mViews.size() - 1, -1, true);
            }
            return this.findOneVisibleChild(0, this.mViews.size(), true);
        }

        public int findFirstPartiallyVisibleItemPosition() {
            if (StaggeredGridLayoutManager.this.mReverseLayout) {
                return this.findOnePartiallyVisibleChild(this.mViews.size() - 1, -1, true);
            }
            return this.findOnePartiallyVisibleChild(0, this.mViews.size(), true);
        }

        public int findFirstVisibleItemPosition() {
            if (StaggeredGridLayoutManager.this.mReverseLayout) {
                return this.findOneVisibleChild(this.mViews.size() - 1, -1, false);
            }
            return this.findOneVisibleChild(0, this.mViews.size(), false);
        }

        public int findLastCompletelyVisibleItemPosition() {
            if (StaggeredGridLayoutManager.this.mReverseLayout) {
                return this.findOneVisibleChild(0, this.mViews.size(), true);
            }
            return this.findOneVisibleChild(this.mViews.size() - 1, -1, true);
        }

        public int findLastPartiallyVisibleItemPosition() {
            if (StaggeredGridLayoutManager.this.mReverseLayout) {
                return this.findOnePartiallyVisibleChild(0, this.mViews.size(), true);
            }
            return this.findOnePartiallyVisibleChild(this.mViews.size() - 1, -1, true);
        }

        public int findLastVisibleItemPosition() {
            if (StaggeredGridLayoutManager.this.mReverseLayout) {
                return this.findOneVisibleChild(0, this.mViews.size(), false);
            }
            return this.findOneVisibleChild(this.mViews.size() - 1, -1, false);
        }

        /*
         * Enabled aggressive block sorting
         */
        int findOnePartiallyOrCompletelyVisibleChild(int n, int n2, boolean bl, boolean bl2, boolean bl3) {
            int n3 = StaggeredGridLayoutManager.this.mPrimaryOrientation.getStartAfterPadding();
            int n4 = StaggeredGridLayoutManager.this.mPrimaryOrientation.getEndAfterPadding();
            int n5 = n2 > n ? 1 : -1;
            while (n != n2) {
                View view = this.mViews.get(n);
                int n6 = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedStart(view);
                int n7 = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedEnd(view);
                boolean bl4 = false;
                boolean bl5 = bl3 ? n6 <= n4 : n6 < n4;
                if (bl3 ? n7 >= n3 : n7 > n3) {
                    bl4 = true;
                }
                if (bl5 && bl4) {
                    if (bl && bl2) {
                        if (n6 >= n3 && n7 <= n4) {
                            return StaggeredGridLayoutManager.this.getPosition(view);
                        }
                    } else {
                        if (bl2) {
                            return StaggeredGridLayoutManager.this.getPosition(view);
                        }
                        if (n6 < n3 || n7 > n4) {
                            return StaggeredGridLayoutManager.this.getPosition(view);
                        }
                    }
                }
                n += n5;
            }
            return -1;
        }

        int findOnePartiallyVisibleChild(int n, int n2, boolean bl) {
            return this.findOnePartiallyOrCompletelyVisibleChild(n, n2, false, false, bl);
        }

        int findOneVisibleChild(int n, int n2, boolean bl) {
            return this.findOnePartiallyOrCompletelyVisibleChild(n, n2, bl, true, false);
        }

        public int getDeletedSize() {
            return this.mDeletedSize;
        }

        int getEndLine() {
            if (this.mCachedEnd != Integer.MIN_VALUE) {
                return this.mCachedEnd;
            }
            this.calculateCachedEnd();
            return this.mCachedEnd;
        }

        int getEndLine(int n) {
            if (this.mCachedEnd != Integer.MIN_VALUE) {
                return this.mCachedEnd;
            }
            if (this.mViews.size() == 0) {
                return n;
            }
            this.calculateCachedEnd();
            return this.mCachedEnd;
        }

        public View getFocusableViewAfter(int n, int n2) {
            View view;
            block9 : {
                view = null;
                View view2 = null;
                if (n2 == -1) {
                    int n3 = this.mViews.size();
                    n2 = 0;
                    do {
                        view = view2;
                        if (n2 >= n3) break block9;
                        View view3 = this.mViews.get(n2);
                        if (StaggeredGridLayoutManager.this.mReverseLayout) {
                            view = view2;
                            if (StaggeredGridLayoutManager.this.getPosition(view3) <= n) break block9;
                        }
                        if (!StaggeredGridLayoutManager.this.mReverseLayout && StaggeredGridLayoutManager.this.getPosition(view3) >= n) {
                            return view2;
                        }
                        view = view2;
                        if (view3.hasFocusable()) {
                            ++n2;
                            view2 = view3;
                            continue;
                        }
                        break block9;
                        break;
                    } while (true);
                }
                n2 = this.mViews.size() - 1;
                view2 = view;
                do {
                    view = view2;
                    if (n2 < 0) break;
                    View view4 = this.mViews.get(n2);
                    if (StaggeredGridLayoutManager.this.mReverseLayout) {
                        view = view2;
                        if (StaggeredGridLayoutManager.this.getPosition(view4) >= n) break;
                    }
                    if (!StaggeredGridLayoutManager.this.mReverseLayout && StaggeredGridLayoutManager.this.getPosition(view4) <= n) {
                        return view2;
                    }
                    view = view2;
                    if (!view4.hasFocusable()) break;
                    --n2;
                    view2 = view4;
                } while (true);
            }
            return view;
        }

        LayoutParams getLayoutParams(View view) {
            return (LayoutParams)view.getLayoutParams();
        }

        int getStartLine() {
            if (this.mCachedStart != Integer.MIN_VALUE) {
                return this.mCachedStart;
            }
            this.calculateCachedStart();
            return this.mCachedStart;
        }

        int getStartLine(int n) {
            if (this.mCachedStart != Integer.MIN_VALUE) {
                return this.mCachedStart;
            }
            if (this.mViews.size() == 0) {
                return n;
            }
            this.calculateCachedStart();
            return this.mCachedStart;
        }

        void invalidateCache() {
            this.mCachedStart = Integer.MIN_VALUE;
            this.mCachedEnd = Integer.MIN_VALUE;
        }

        void onOffset(int n) {
            if (this.mCachedStart != Integer.MIN_VALUE) {
                this.mCachedStart += n;
            }
            if (this.mCachedEnd != Integer.MIN_VALUE) {
                this.mCachedEnd += n;
            }
        }

        void popEnd() {
            int n = this.mViews.size();
            View view = this.mViews.remove(n - 1);
            LayoutParams layoutParams = this.getLayoutParams(view);
            layoutParams.mSpan = null;
            if (layoutParams.isItemRemoved() || layoutParams.isItemChanged()) {
                this.mDeletedSize -= StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedMeasurement(view);
            }
            if (n == 1) {
                this.mCachedStart = Integer.MIN_VALUE;
            }
            this.mCachedEnd = Integer.MIN_VALUE;
        }

        void popStart() {
            View view = this.mViews.remove(0);
            LayoutParams layoutParams = this.getLayoutParams(view);
            layoutParams.mSpan = null;
            if (this.mViews.size() == 0) {
                this.mCachedEnd = Integer.MIN_VALUE;
            }
            if (layoutParams.isItemRemoved() || layoutParams.isItemChanged()) {
                this.mDeletedSize -= StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedMeasurement(view);
            }
            this.mCachedStart = Integer.MIN_VALUE;
        }

        void prependToSpan(View view) {
            LayoutParams layoutParams = this.getLayoutParams(view);
            layoutParams.mSpan = this;
            this.mViews.add(0, view);
            this.mCachedStart = Integer.MIN_VALUE;
            if (this.mViews.size() == 1) {
                this.mCachedEnd = Integer.MIN_VALUE;
            }
            if (layoutParams.isItemRemoved() || layoutParams.isItemChanged()) {
                this.mDeletedSize += StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedMeasurement(view);
            }
        }

        void setLine(int n) {
            this.mCachedStart = n;
            this.mCachedEnd = n;
        }
    }

}
