/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.v7.app;

import android.support.v4.widget.NestedScrollView;
import android.view.View;

class AlertController
implements Runnable {
    final /* synthetic */ View val$bottom;
    final /* synthetic */ View val$top;

    AlertController(View view, View view2) {
        this.val$top = view;
        this.val$bottom = view2;
    }

    @Override
    public void run() {
        android.support.v7.app.AlertController.manageScrollIndicators((View)AlertController.this.mScrollView, this.val$top, this.val$bottom);
    }
}
