/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.design.widget;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.view.View;

class BottomSheetDialog
extends BottomSheetBehavior.BottomSheetCallback {
    BottomSheetDialog() {
    }

    @Override
    public void onSlide(@NonNull View view, float f) {
    }

    @Override
    public void onStateChanged(@NonNull View view, int n) {
        if (n == 5) {
            BottomSheetDialog.this.cancel();
        }
    }
}
