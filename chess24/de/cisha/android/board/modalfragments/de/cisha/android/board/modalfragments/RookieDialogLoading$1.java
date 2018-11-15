/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnKeyListener
 *  android.view.KeyEvent
 */
package de.cisha.android.board.modalfragments;

import android.content.DialogInterface;
import android.view.KeyEvent;

class RookieDialogLoading
implements DialogInterface.OnKeyListener {
    RookieDialogLoading() {
    }

    public boolean onKey(DialogInterface dialogInterface, int n, KeyEvent keyEvent) {
        if (n == 4) {
            RookieDialogLoading.this.onLoadingDialogCancelled();
            return false;
        }
        return false;
    }
}
