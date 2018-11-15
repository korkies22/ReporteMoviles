/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.modalfragments;

import android.view.View;

class RookieDialogFrament
implements View.OnClickListener {
    RookieDialogFrament() {
    }

    public void onClick(View view) {
        RookieDialogFrament.this.dismiss();
        if (RookieDialogFrament.this._cancelClickListener != null) {
            RookieDialogFrament.this._cancelClickListener.onClick(view);
            RookieDialogFrament.this._cancelClickListener = null;
        }
    }
}
