/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package android.support.v4.widget;

import android.os.Bundle;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeProviderCompat;
import android.support.v4.widget.ExploreByTouchHelper;

private class ExploreByTouchHelper.MyNodeProvider
extends AccessibilityNodeProviderCompat {
    ExploreByTouchHelper.MyNodeProvider() {
    }

    @Override
    public AccessibilityNodeInfoCompat createAccessibilityNodeInfo(int n) {
        return AccessibilityNodeInfoCompat.obtain(ExploreByTouchHelper.this.obtainAccessibilityNodeInfo(n));
    }

    @Override
    public AccessibilityNodeInfoCompat findFocus(int n) {
        n = n == 2 ? ExploreByTouchHelper.this.mAccessibilityFocusedVirtualViewId : ExploreByTouchHelper.this.mKeyboardFocusedVirtualViewId;
        if (n == Integer.MIN_VALUE) {
            return null;
        }
        return this.createAccessibilityNodeInfo(n);
    }

    @Override
    public boolean performAction(int n, int n2, Bundle bundle) {
        return ExploreByTouchHelper.this.performAction(n, n2, bundle);
    }
}
