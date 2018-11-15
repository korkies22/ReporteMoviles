/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.view.View
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.accessibility.AccessibilityRecord
 *  android.widget.ScrollView
 */
package android.support.v4.widget;

import android.os.Bundle;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityRecordCompat;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityRecord;
import android.widget.ScrollView;

static class NestedScrollView.AccessibilityDelegate
extends AccessibilityDelegateCompat {
    NestedScrollView.AccessibilityDelegate() {
    }

    @Override
    public void onInitializeAccessibilityEvent(View object, AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent((View)object, accessibilityEvent);
        object = (NestedScrollView)object;
        accessibilityEvent.setClassName((CharSequence)ScrollView.class.getName());
        boolean bl = object.getScrollRange() > 0;
        accessibilityEvent.setScrollable(bl);
        accessibilityEvent.setScrollX(object.getScrollX());
        accessibilityEvent.setScrollY(object.getScrollY());
        AccessibilityRecordCompat.setMaxScrollX((AccessibilityRecord)accessibilityEvent, object.getScrollX());
        AccessibilityRecordCompat.setMaxScrollY((AccessibilityRecord)accessibilityEvent, object.getScrollRange());
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(View object, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        int n;
        super.onInitializeAccessibilityNodeInfo((View)object, accessibilityNodeInfoCompat);
        object = (NestedScrollView)object;
        accessibilityNodeInfoCompat.setClassName(ScrollView.class.getName());
        if (object.isEnabled() && (n = object.getScrollRange()) > 0) {
            accessibilityNodeInfoCompat.setScrollable(true);
            if (object.getScrollY() > 0) {
                accessibilityNodeInfoCompat.addAction(8192);
            }
            if (object.getScrollY() < n) {
                accessibilityNodeInfoCompat.addAction(4096);
            }
        }
    }

    @Override
    public boolean performAccessibilityAction(View object, int n, Bundle bundle) {
        if (super.performAccessibilityAction((View)object, n, bundle)) {
            return true;
        }
        if (!(object = (NestedScrollView)object).isEnabled()) {
            return false;
        }
        if (n != 4096) {
            if (n != 8192) {
                return false;
            }
            n = object.getHeight();
            int n2 = object.getPaddingBottom();
            int n3 = object.getPaddingTop();
            n = Math.max(object.getScrollY() - (n - n2 - n3), 0);
            if (n != object.getScrollY()) {
                object.smoothScrollTo(0, n);
                return true;
            }
            return false;
        }
        n = object.getHeight();
        int n4 = object.getPaddingBottom();
        int n5 = object.getPaddingTop();
        n = Math.min(object.getScrollY() + (n - n4 - n5), object.getScrollRange());
        if (n != object.getScrollY()) {
            object.smoothScrollTo(0, n);
            return true;
        }
        return false;
    }
}
