/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package android.support.design.widget;

import android.view.View;

class BottomSheetDialog
implements View.OnClickListener {
    BottomSheetDialog() {
    }

    public void onClick(View view) {
        if (BottomSheetDialog.this.mCancelable && BottomSheetDialog.this.isShowing() && BottomSheetDialog.this.shouldWindowCloseOnTouchOutside()) {
            BottomSheetDialog.this.cancel();
        }
    }
}
