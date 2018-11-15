/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.view.accessibility.AccessibilityNodeInfo$CollectionInfo
 */
package android.support.v4.view.accessibility;

import android.os.Build;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.view.accessibility.AccessibilityNodeInfo;

public static class AccessibilityNodeInfoCompat.CollectionInfoCompat {
    public static final int SELECTION_MODE_MULTIPLE = 2;
    public static final int SELECTION_MODE_NONE = 0;
    public static final int SELECTION_MODE_SINGLE = 1;
    final Object mInfo;

    AccessibilityNodeInfoCompat.CollectionInfoCompat(Object object) {
        this.mInfo = object;
    }

    public static AccessibilityNodeInfoCompat.CollectionInfoCompat obtain(int n, int n2, boolean bl) {
        if (Build.VERSION.SDK_INT >= 19) {
            return new AccessibilityNodeInfoCompat.CollectionInfoCompat((Object)AccessibilityNodeInfo.CollectionInfo.obtain((int)n, (int)n2, (boolean)bl));
        }
        return new AccessibilityNodeInfoCompat.CollectionInfoCompat(null);
    }

    public static AccessibilityNodeInfoCompat.CollectionInfoCompat obtain(int n, int n2, boolean bl, int n3) {
        if (Build.VERSION.SDK_INT >= 21) {
            return new AccessibilityNodeInfoCompat.CollectionInfoCompat((Object)AccessibilityNodeInfo.CollectionInfo.obtain((int)n, (int)n2, (boolean)bl, (int)n3));
        }
        if (Build.VERSION.SDK_INT >= 19) {
            return new AccessibilityNodeInfoCompat.CollectionInfoCompat((Object)AccessibilityNodeInfo.CollectionInfo.obtain((int)n, (int)n2, (boolean)bl));
        }
        return new AccessibilityNodeInfoCompat.CollectionInfoCompat(null);
    }

    public int getColumnCount() {
        if (Build.VERSION.SDK_INT >= 19) {
            return ((AccessibilityNodeInfo.CollectionInfo)this.mInfo).getColumnCount();
        }
        return 0;
    }

    public int getRowCount() {
        if (Build.VERSION.SDK_INT >= 19) {
            return ((AccessibilityNodeInfo.CollectionInfo)this.mInfo).getRowCount();
        }
        return 0;
    }

    public int getSelectionMode() {
        if (Build.VERSION.SDK_INT >= 21) {
            return ((AccessibilityNodeInfo.CollectionInfo)this.mInfo).getSelectionMode();
        }
        return 0;
    }

    public boolean isHierarchical() {
        if (Build.VERSION.SDK_INT >= 19) {
            return ((AccessibilityNodeInfo.CollectionInfo)this.mInfo).isHierarchical();
        }
        return false;
    }
}
