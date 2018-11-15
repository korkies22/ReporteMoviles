/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.view.accessibility.AccessibilityNodeInfo$CollectionItemInfo
 */
package android.support.v4.view.accessibility;

import android.os.Build;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.view.accessibility.AccessibilityNodeInfo;

public static class AccessibilityNodeInfoCompat.CollectionItemInfoCompat {
    final Object mInfo;

    AccessibilityNodeInfoCompat.CollectionItemInfoCompat(Object object) {
        this.mInfo = object;
    }

    public static AccessibilityNodeInfoCompat.CollectionItemInfoCompat obtain(int n, int n2, int n3, int n4, boolean bl) {
        if (Build.VERSION.SDK_INT >= 19) {
            return new AccessibilityNodeInfoCompat.CollectionItemInfoCompat((Object)AccessibilityNodeInfo.CollectionItemInfo.obtain((int)n, (int)n2, (int)n3, (int)n4, (boolean)bl));
        }
        return new AccessibilityNodeInfoCompat.CollectionItemInfoCompat(null);
    }

    public static AccessibilityNodeInfoCompat.CollectionItemInfoCompat obtain(int n, int n2, int n3, int n4, boolean bl, boolean bl2) {
        if (Build.VERSION.SDK_INT >= 21) {
            return new AccessibilityNodeInfoCompat.CollectionItemInfoCompat((Object)AccessibilityNodeInfo.CollectionItemInfo.obtain((int)n, (int)n2, (int)n3, (int)n4, (boolean)bl, (boolean)bl2));
        }
        if (Build.VERSION.SDK_INT >= 19) {
            return new AccessibilityNodeInfoCompat.CollectionItemInfoCompat((Object)AccessibilityNodeInfo.CollectionItemInfo.obtain((int)n, (int)n2, (int)n3, (int)n4, (boolean)bl));
        }
        return new AccessibilityNodeInfoCompat.CollectionItemInfoCompat(null);
    }

    public int getColumnIndex() {
        if (Build.VERSION.SDK_INT >= 19) {
            return ((AccessibilityNodeInfo.CollectionItemInfo)this.mInfo).getColumnIndex();
        }
        return 0;
    }

    public int getColumnSpan() {
        if (Build.VERSION.SDK_INT >= 19) {
            return ((AccessibilityNodeInfo.CollectionItemInfo)this.mInfo).getColumnSpan();
        }
        return 0;
    }

    public int getRowIndex() {
        if (Build.VERSION.SDK_INT >= 19) {
            return ((AccessibilityNodeInfo.CollectionItemInfo)this.mInfo).getRowIndex();
        }
        return 0;
    }

    public int getRowSpan() {
        if (Build.VERSION.SDK_INT >= 19) {
            return ((AccessibilityNodeInfo.CollectionItemInfo)this.mInfo).getRowSpan();
        }
        return 0;
    }

    public boolean isHeading() {
        if (Build.VERSION.SDK_INT >= 19) {
            return ((AccessibilityNodeInfo.CollectionItemInfo)this.mInfo).isHeading();
        }
        return false;
    }

    public boolean isSelected() {
        if (Build.VERSION.SDK_INT >= 21) {
            return ((AccessibilityNodeInfo.CollectionItemInfo)this.mInfo).isSelected();
        }
        return false;
    }
}
