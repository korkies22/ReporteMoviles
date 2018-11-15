/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$OnHierarchyChangeListener
 */
package android.support.design.widget;

import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.view.ViewGroup;

private class CoordinatorLayout.HierarchyChangeListener
implements ViewGroup.OnHierarchyChangeListener {
    CoordinatorLayout.HierarchyChangeListener() {
    }

    public void onChildViewAdded(View view, View view2) {
        if (CoordinatorLayout.this.mOnHierarchyChangeListener != null) {
            CoordinatorLayout.this.mOnHierarchyChangeListener.onChildViewAdded(view, view2);
        }
    }

    public void onChildViewRemoved(View view, View view2) {
        CoordinatorLayout.this.onChildViewsChanged(2);
        if (CoordinatorLayout.this.mOnHierarchyChangeListener != null) {
            CoordinatorLayout.this.mOnHierarchyChangeListener.onChildViewRemoved(view, view2);
        }
    }
}
