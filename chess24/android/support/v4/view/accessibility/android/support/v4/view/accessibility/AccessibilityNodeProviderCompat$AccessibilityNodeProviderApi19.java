/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.accessibility.AccessibilityNodeInfo
 */
package android.support.v4.view.accessibility;

import android.support.annotation.RequiresApi;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeProviderCompat;
import android.view.accessibility.AccessibilityNodeInfo;

@RequiresApi(value=19)
static class AccessibilityNodeProviderCompat.AccessibilityNodeProviderApi19
extends AccessibilityNodeProviderCompat.AccessibilityNodeProviderApi16 {
    AccessibilityNodeProviderCompat.AccessibilityNodeProviderApi19(AccessibilityNodeProviderCompat accessibilityNodeProviderCompat) {
        super(accessibilityNodeProviderCompat);
    }

    public AccessibilityNodeInfo findFocus(int n) {
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompat = this.mCompat.findFocus(n);
        if (accessibilityNodeInfoCompat == null) {
            return null;
        }
        return accessibilityNodeInfoCompat.unwrap();
    }
}
