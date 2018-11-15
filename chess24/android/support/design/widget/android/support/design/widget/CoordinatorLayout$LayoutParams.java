/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.Rect
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewParent
 */
package android.support.design.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.coreui.R;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

public static class CoordinatorLayout.LayoutParams
extends ViewGroup.MarginLayoutParams {
    public int anchorGravity = 0;
    public int dodgeInsetEdges = 0;
    public int gravity = 0;
    public int insetEdge = 0;
    public int keyline = -1;
    View mAnchorDirectChild;
    int mAnchorId = -1;
    View mAnchorView;
    CoordinatorLayout.Behavior mBehavior;
    boolean mBehaviorResolved = false;
    Object mBehaviorTag;
    private boolean mDidAcceptNestedScrollNonTouch;
    private boolean mDidAcceptNestedScrollTouch;
    private boolean mDidBlockInteraction;
    private boolean mDidChangeAfterNestedScroll;
    int mInsetOffsetX;
    int mInsetOffsetY;
    final Rect mLastChildRect = new Rect();

    public CoordinatorLayout.LayoutParams(int n, int n2) {
        super(n, n2);
    }

    CoordinatorLayout.LayoutParams(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CoordinatorLayout_Layout);
        this.gravity = typedArray.getInteger(R.styleable.CoordinatorLayout_Layout_android_layout_gravity, 0);
        this.mAnchorId = typedArray.getResourceId(R.styleable.CoordinatorLayout_Layout_layout_anchor, -1);
        this.anchorGravity = typedArray.getInteger(R.styleable.CoordinatorLayout_Layout_layout_anchorGravity, 0);
        this.keyline = typedArray.getInteger(R.styleable.CoordinatorLayout_Layout_layout_keyline, -1);
        this.insetEdge = typedArray.getInt(R.styleable.CoordinatorLayout_Layout_layout_insetEdge, 0);
        this.dodgeInsetEdges = typedArray.getInt(R.styleable.CoordinatorLayout_Layout_layout_dodgeInsetEdges, 0);
        this.mBehaviorResolved = typedArray.hasValue(R.styleable.CoordinatorLayout_Layout_layout_behavior);
        if (this.mBehaviorResolved) {
            this.mBehavior = CoordinatorLayout.parseBehavior(context, attributeSet, typedArray.getString(R.styleable.CoordinatorLayout_Layout_layout_behavior));
        }
        typedArray.recycle();
        if (this.mBehavior != null) {
            this.mBehavior.onAttachedToLayoutParams(this);
        }
    }

    public CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams layoutParams) {
        super((ViewGroup.MarginLayoutParams)layoutParams);
    }

    public CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams layoutParams) {
        super(layoutParams);
    }

    public CoordinatorLayout.LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
        super(marginLayoutParams);
    }

    private void resolveAnchorView(View view, CoordinatorLayout coordinatorLayout) {
        this.mAnchorView = coordinatorLayout.findViewById(this.mAnchorId);
        if (this.mAnchorView != null) {
            if (this.mAnchorView == coordinatorLayout) {
                if (coordinatorLayout.isInEditMode()) {
                    this.mAnchorDirectChild = null;
                    this.mAnchorView = null;
                    return;
                }
                throw new IllegalStateException("View can not be anchored to the the parent CoordinatorLayout");
            }
            View view2 = this.mAnchorView;
            for (ViewParent viewParent = this.mAnchorView.getParent(); viewParent != coordinatorLayout && viewParent != null; viewParent = viewParent.getParent()) {
                if (viewParent == view) {
                    if (coordinatorLayout.isInEditMode()) {
                        this.mAnchorDirectChild = null;
                        this.mAnchorView = null;
                        return;
                    }
                    throw new IllegalStateException("Anchor must not be a descendant of the anchored view");
                }
                if (!(viewParent instanceof View)) continue;
                view2 = (View)viewParent;
            }
            this.mAnchorDirectChild = view2;
            return;
        }
        if (coordinatorLayout.isInEditMode()) {
            this.mAnchorDirectChild = null;
            this.mAnchorView = null;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Could not find CoordinatorLayout descendant view with id ");
        stringBuilder.append(coordinatorLayout.getResources().getResourceName(this.mAnchorId));
        stringBuilder.append(" to anchor view ");
        stringBuilder.append((Object)view);
        throw new IllegalStateException(stringBuilder.toString());
    }

    private boolean shouldDodge(View view, int n) {
        int n2 = GravityCompat.getAbsoluteGravity(((CoordinatorLayout.LayoutParams)view.getLayoutParams()).insetEdge, n);
        if (n2 != 0 && (GravityCompat.getAbsoluteGravity(this.dodgeInsetEdges, n) & n2) == n2) {
            return true;
        }
        return false;
    }

    private boolean verifyAnchorView(View view, CoordinatorLayout coordinatorLayout) {
        if (this.mAnchorView.getId() != this.mAnchorId) {
            return false;
        }
        View view2 = this.mAnchorView;
        for (ViewParent viewParent = this.mAnchorView.getParent(); viewParent != coordinatorLayout; viewParent = viewParent.getParent()) {
            if (viewParent != null && viewParent != view) {
                if (!(viewParent instanceof View)) continue;
                view2 = (View)viewParent;
                continue;
            }
            this.mAnchorDirectChild = null;
            this.mAnchorView = null;
            return false;
        }
        this.mAnchorDirectChild = view2;
        return true;
    }

    boolean checkAnchorChanged() {
        if (this.mAnchorView == null && this.mAnchorId != -1) {
            return true;
        }
        return false;
    }

    boolean dependsOn(CoordinatorLayout coordinatorLayout, View view, View view2) {
        if (!(view2 == this.mAnchorDirectChild || this.shouldDodge(view2, ViewCompat.getLayoutDirection((View)coordinatorLayout)) || this.mBehavior != null && this.mBehavior.layoutDependsOn(coordinatorLayout, view, view2))) {
            return false;
        }
        return true;
    }

    boolean didBlockInteraction() {
        if (this.mBehavior == null) {
            this.mDidBlockInteraction = false;
        }
        return this.mDidBlockInteraction;
    }

    View findAnchorView(CoordinatorLayout coordinatorLayout, View view) {
        if (this.mAnchorId == -1) {
            this.mAnchorDirectChild = null;
            this.mAnchorView = null;
            return null;
        }
        if (this.mAnchorView == null || !this.verifyAnchorView(view, coordinatorLayout)) {
            this.resolveAnchorView(view, coordinatorLayout);
        }
        return this.mAnchorView;
    }

    @IdRes
    public int getAnchorId() {
        return this.mAnchorId;
    }

    @Nullable
    public CoordinatorLayout.Behavior getBehavior() {
        return this.mBehavior;
    }

    boolean getChangedAfterNestedScroll() {
        return this.mDidChangeAfterNestedScroll;
    }

    Rect getLastChildRect() {
        return this.mLastChildRect;
    }

    void invalidateAnchor() {
        this.mAnchorDirectChild = null;
        this.mAnchorView = null;
    }

    boolean isBlockingInteractionBelow(CoordinatorLayout coordinatorLayout, View view) {
        if (this.mDidBlockInteraction) {
            return true;
        }
        boolean bl = this.mDidBlockInteraction;
        boolean bl2 = this.mBehavior != null ? this.mBehavior.blocksInteractionBelow(coordinatorLayout, view) : false;
        this.mDidBlockInteraction = bl2 |= bl;
        return bl2;
    }

    boolean isNestedScrollAccepted(int n) {
        switch (n) {
            default: {
                return false;
            }
            case 1: {
                return this.mDidAcceptNestedScrollNonTouch;
            }
            case 0: 
        }
        return this.mDidAcceptNestedScrollTouch;
    }

    void resetChangedAfterNestedScroll() {
        this.mDidChangeAfterNestedScroll = false;
    }

    void resetNestedScroll(int n) {
        this.setNestedScrollAccepted(n, false);
    }

    void resetTouchBehaviorTracking() {
        this.mDidBlockInteraction = false;
    }

    public void setAnchorId(@IdRes int n) {
        this.invalidateAnchor();
        this.mAnchorId = n;
    }

    public void setBehavior(@Nullable CoordinatorLayout.Behavior behavior) {
        if (this.mBehavior != behavior) {
            if (this.mBehavior != null) {
                this.mBehavior.onDetachedFromLayoutParams();
            }
            this.mBehavior = behavior;
            this.mBehaviorTag = null;
            this.mBehaviorResolved = true;
            if (behavior != null) {
                behavior.onAttachedToLayoutParams(this);
            }
        }
    }

    void setChangedAfterNestedScroll(boolean bl) {
        this.mDidChangeAfterNestedScroll = bl;
    }

    void setLastChildRect(Rect rect) {
        this.mLastChildRect.set(rect);
    }

    void setNestedScrollAccepted(int n, boolean bl) {
        switch (n) {
            default: {
                return;
            }
            case 1: {
                this.mDidAcceptNestedScrollNonTouch = bl;
                return;
            }
            case 0: 
        }
        this.mDidAcceptNestedScrollTouch = bl;
    }
}
