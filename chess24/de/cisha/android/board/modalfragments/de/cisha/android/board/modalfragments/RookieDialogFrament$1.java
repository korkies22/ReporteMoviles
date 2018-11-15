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
        if (RookieDialogFrament.this._reloadClickListener != null) {
            RookieDialogFrament.this._reloadClickListener.onClick(view);
            RookieDialogFrament.this._reloadClickListener = null;
        }
    }
}
