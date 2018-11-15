/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewParent
 */
package android.support.v4.view;

import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ViewParentCompat;
import android.view.View;
import android.view.ViewParent;

static class ViewParentCompat.ViewParentCompatBaseImpl {
    ViewParentCompat.ViewParentCompatBaseImpl() {
    }

    public void notifySubtreeAccessibilityStateChanged(ViewParent viewParent, View view, View view2, int n) {
    }

    public boolean onNestedFling(ViewParent viewParent, View view, float f, float f2, boolean bl) {
        if (viewParent instanceof NestedScrollingParent) {
            return ((NestedScrollingParent)viewParent).onNestedFling(view, f, f2, bl);
        }
        return false;
    }

    public boolean onNestedPreFling(ViewParent viewParent, View view, float f, float f2) {
        if (viewParent instanceof NestedScrollingParent) {
            return ((NestedScrollingParent)viewParent).onNestedPreFling(view, f, f2);
        }
        return false;
    }

    public void onNestedPreScroll(ViewParent viewParent, View view, int n, int n2, int[] arrn) {
        if (viewParent instanceof NestedScrollingParent) {
            ((NestedScrollingParent)viewParent).onNestedPreScroll(view, n, n2, arrn);
        }
    }

    public void onNestedScroll(ViewParent viewParent, View view, int n, int n2, int n3, int n4) {
        if (viewParent instanceof NestedScrollingParent) {
            ((NestedScrollingParent)viewParent).onNestedScroll(view, n, n2, n3, n4);
        }
    }

    public void onNestedScrollAccepted(ViewParent viewParent, View view, View view2, int n) {
        if (viewParent instanceof NestedScrollingParent) {
            ((NestedScrollingParent)viewParent).onNestedScrollAccepted(view, view2, n);
        }
    }

    public boolean onStartNestedScroll(ViewParent viewParent, View view, View view2, int n) {
        if (viewParent instanceof NestedScrollingParent) {
            return ((NestedScrollingParent)viewParent).onStartNestedScroll(view, view2, n);
        }
        return false;
    }

    public void onStopNestedScroll(ViewParent viewParent, View view) {
        if (viewParent instanceof NestedScrollingParent) {
            ((NestedScrollingParent)viewParent).onStopNestedScroll(view);
        }
    }
}
