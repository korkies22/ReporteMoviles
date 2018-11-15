/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 */
package android.support.v4.widget;

import android.graphics.Rect;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.widget.FocusStrategy;

static final class ExploreByTouchHelper
implements FocusStrategy.BoundsAdapter<AccessibilityNodeInfoCompat> {
    ExploreByTouchHelper() {
    }

    @Override
    public void obtainBounds(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat, Rect rect) {
        accessibilityNodeInfoCompat.getBoundsInParent(rect);
    }
}
