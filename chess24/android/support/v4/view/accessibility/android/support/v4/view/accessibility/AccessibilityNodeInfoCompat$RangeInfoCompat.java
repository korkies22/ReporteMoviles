/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.view.accessibility.AccessibilityNodeInfo$RangeInfo
 */
package android.support.v4.view.accessibility;

import android.os.Build;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.view.accessibility.AccessibilityNodeInfo;

public static class AccessibilityNodeInfoCompat.RangeInfoCompat {
    public static final int RANGE_TYPE_FLOAT = 1;
    public static final int RANGE_TYPE_INT = 0;
    public static final int RANGE_TYPE_PERCENT = 2;
    final Object mInfo;

    AccessibilityNodeInfoCompat.RangeInfoCompat(Object object) {
        this.mInfo = object;
    }

    public static AccessibilityNodeInfoCompat.RangeInfoCompat obtain(int n, float f, float f2, float f3) {
        if (Build.VERSION.SDK_INT >= 19) {
            return new AccessibilityNodeInfoCompat.RangeInfoCompat((Object)AccessibilityNodeInfo.RangeInfo.obtain((int)n, (float)f, (float)f2, (float)f3));
        }
        return new AccessibilityNodeInfoCompat.RangeInfoCompat(null);
    }

    public float getCurrent() {
        if (Build.VERSION.SDK_INT >= 19) {
            return ((AccessibilityNodeInfo.RangeInfo)this.mInfo).getCurrent();
        }
        return 0.0f;
    }

    public float getMax() {
        if (Build.VERSION.SDK_INT >= 19) {
            return ((AccessibilityNodeInfo.RangeInfo)this.mInfo).getMax();
        }
        return 0.0f;
    }

    public float getMin() {
        if (Build.VERSION.SDK_INT >= 19) {
            return ((AccessibilityNodeInfo.RangeInfo)this.mInfo).getMin();
        }
        return 0.0f;
    }

    public int getType() {
        if (Build.VERSION.SDK_INT >= 19) {
            return ((AccessibilityNodeInfo.RangeInfo)this.mInfo).getType();
        }
        return 0;
    }
}
