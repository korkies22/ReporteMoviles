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
implements NestedScrollView.OnScrollChangeListener {
    final /* synthetic */ View val$bottom;
    final /* synthetic */ View val$top;

    AlertController(View view, View view2) {
        this.val$top = view;
        this.val$bottom = view2;
    }

    @Override
    public void onScrollChange(NestedScrollView nestedScrollView, int n, int n2, int n3, int n4) {
        android.support.v7.app.AlertController.manageScrollIndicators((View)nestedScrollView, this.val$top, this.val$bottom);
    }
}
