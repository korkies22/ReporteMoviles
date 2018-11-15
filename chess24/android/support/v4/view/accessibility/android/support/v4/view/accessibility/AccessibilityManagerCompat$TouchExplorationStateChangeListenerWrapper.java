/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.accessibility.AccessibilityManager
 *  android.view.accessibility.AccessibilityManager$TouchExplorationStateChangeListener
 */
package android.support.v4.view.accessibility;

import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.view.accessibility.AccessibilityManagerCompat;
import android.view.accessibility.AccessibilityManager;

@RequiresApi(value=19)
private static class AccessibilityManagerCompat.TouchExplorationStateChangeListenerWrapper
implements AccessibilityManager.TouchExplorationStateChangeListener {
    final AccessibilityManagerCompat.TouchExplorationStateChangeListener mListener;

    AccessibilityManagerCompat.TouchExplorationStateChangeListenerWrapper(@NonNull AccessibilityManagerCompat.TouchExplorationStateChangeListener touchExplorationStateChangeListener) {
        this.mListener = touchExplorationStateChangeListener;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (AccessibilityManagerCompat.TouchExplorationStateChangeListenerWrapper)object;
            return this.mListener.equals(object.mListener);
        }
        return false;
    }

    public int hashCode() {
        return this.mListener.hashCode();
    }

    public void onTouchExplorationStateChanged(boolean bl) {
        this.mListener.onTouchExplorationStateChanged(bl);
    }
}
