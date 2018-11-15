/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.view.View
 */
package android.support.design.widget;

import android.os.Bundle;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.view.View;

class BottomSheetDialog
extends AccessibilityDelegateCompat {
    BottomSheetDialog() {
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
        if (BottomSheetDialog.this.mCancelable) {
            accessibilityNodeInfoCompat.addAction(1048576);
            accessibilityNodeInfoCompat.setDismissable(true);
            return;
        }
        accessibilityNodeInfoCompat.setDismissable(false);
    }

    @Override
    public boolean performAccessibilityAction(View view, int n, Bundle bundle) {
        if (n == 1048576 && BottomSheetDialog.this.mCancelable) {
            BottomSheetDialog.this.cancel();
            return true;
        }
        return super.performAccessibilityAction(view, n, bundle);
    }
}
