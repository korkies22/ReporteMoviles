/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.accessibility.AccessibilityManager
 *  android.view.accessibility.AccessibilityManager$AccessibilityStateChangeListener
 */
package android.support.v4.view.accessibility;

import android.support.annotation.NonNull;
import android.support.v4.view.accessibility.AccessibilityManagerCompat;
import android.view.accessibility.AccessibilityManager;

private static class AccessibilityManagerCompat.AccessibilityStateChangeListenerWrapper
implements AccessibilityManager.AccessibilityStateChangeListener {
    AccessibilityManagerCompat.AccessibilityStateChangeListener mListener;

    AccessibilityManagerCompat.AccessibilityStateChangeListenerWrapper(@NonNull AccessibilityManagerCompat.AccessibilityStateChangeListener accessibilityStateChangeListener) {
        this.mListener = accessibilityStateChangeListener;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (AccessibilityManagerCompat.AccessibilityStateChangeListenerWrapper)object;
            return this.mListener.equals(object.mListener);
        }
        return false;
    }

    public int hashCode() {
        return this.mListener.hashCode();
    }

    public void onAccessibilityStateChanged(boolean bl) {
        this.mListener.onAccessibilityStateChanged(bl);
    }
}
