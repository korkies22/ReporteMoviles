/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.account.view;

import android.view.View;
import de.cisha.android.board.account.view.PurchaseDialog;

class PurchaseDialog
implements View.OnClickListener {
    PurchaseDialog() {
    }

    public void onClick(View view) {
        if (PurchaseDialog.this._dialogListener != null) {
            PurchaseDialog.this._dialogListener.onRestorePressed();
            PurchaseDialog.this.dismiss();
        }
    }
}
