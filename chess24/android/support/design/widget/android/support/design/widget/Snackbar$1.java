/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package android.support.design.widget;

import android.view.View;

class Snackbar
implements View.OnClickListener {
    final /* synthetic */ View.OnClickListener val$listener;

    Snackbar(View.OnClickListener onClickListener) {
        this.val$listener = onClickListener;
    }

    public void onClick(View view) {
        this.val$listener.onClick(view);
        Snackbar.this.dispatchDismiss(1);
    }
}
