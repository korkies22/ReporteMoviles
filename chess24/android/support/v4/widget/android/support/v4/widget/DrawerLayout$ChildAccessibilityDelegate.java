/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.v4.widget;

import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

static final class DrawerLayout.ChildAccessibilityDelegate
extends AccessibilityDelegateCompat {
    DrawerLayout.ChildAccessibilityDelegate() {
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
        if (!DrawerLayout.includeChildForAccessibility(view)) {
            accessibilityNodeInfoCompat.setParent(null);
        }
    }
}
