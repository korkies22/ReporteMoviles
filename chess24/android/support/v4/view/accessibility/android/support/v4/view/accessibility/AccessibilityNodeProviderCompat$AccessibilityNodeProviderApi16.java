/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.view.accessibility.AccessibilityNodeProvider
 */
package android.support.v4.view.accessibility;

import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeProviderCompat;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(value=16)
static class AccessibilityNodeProviderCompat.AccessibilityNodeProviderApi16
extends AccessibilityNodeProvider {
    final AccessibilityNodeProviderCompat mCompat;

    AccessibilityNodeProviderCompat.AccessibilityNodeProviderApi16(AccessibilityNodeProviderCompat accessibilityNodeProviderCompat) {
        this.mCompat = accessibilityNodeProviderCompat;
    }

    public AccessibilityNodeInfo createAccessibilityNodeInfo(int n) {
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompat = this.mCompat.createAccessibilityNodeInfo(n);
        if (accessibilityNodeInfoCompat == null) {
            return null;
        }
        return accessibilityNodeInfoCompat.unwrap();
    }

    public List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText(String object, int n) {
        if ((object = this.mCompat.findAccessibilityNodeInfosByText((String)object, n)) == null) {
            return null;
        }
        ArrayList<AccessibilityNodeInfo> arrayList = new ArrayList<AccessibilityNodeInfo>();
        int n2 = object.size();
        for (n = 0; n < n2; ++n) {
            arrayList.add(((AccessibilityNodeInfoCompat)object.get(n)).unwrap());
        }
        return arrayList;
    }

    public boolean performAction(int n, int n2, Bundle bundle) {
        return this.mCompat.performAction(n, n2, bundle);
    }
}
