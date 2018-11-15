/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.design.widget;

import android.view.View;

class BottomSheetBehavior
implements Runnable {
    final /* synthetic */ View val$child;
    final /* synthetic */ int val$state;

    BottomSheetBehavior(View view, int n) {
        this.val$child = view;
        this.val$state = n;
    }

    @Override
    public void run() {
        BottomSheetBehavior.this.startSettlingAnimation(this.val$child, this.val$state);
    }
}
