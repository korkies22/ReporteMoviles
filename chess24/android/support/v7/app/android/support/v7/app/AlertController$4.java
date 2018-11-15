/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.widget.AbsListView
 *  android.widget.AbsListView$OnScrollListener
 */
package android.support.v7.app;

import android.view.View;
import android.widget.AbsListView;

class AlertController
implements AbsListView.OnScrollListener {
    final /* synthetic */ View val$bottom;
    final /* synthetic */ View val$top;

    AlertController(View view, View view2) {
        this.val$top = view;
        this.val$bottom = view2;
    }

    public void onScroll(AbsListView absListView, int n, int n2, int n3) {
        android.support.v7.app.AlertController.manageScrollIndicators((View)absListView, this.val$top, this.val$bottom);
    }

    public void onScrollStateChanged(AbsListView absListView, int n) {
    }
}
