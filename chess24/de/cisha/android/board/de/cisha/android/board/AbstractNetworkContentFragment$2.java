/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.AlertDialog
 */
package de.cisha.android.board;

import android.app.AlertDialog;
import de.cisha.android.board.modalfragments.RookieDialogFragmentNoInternet;

class AbstractNetworkContentFragment
implements Runnable {
    AbstractNetworkContentFragment() {
    }

    @Override
    public void run() {
        if (AbstractNetworkContentFragment.this._networkLostInfo != null) {
            AbstractNetworkContentFragment.this._networkLostInfo.cancel();
            AbstractNetworkContentFragment.this._networkLostInfo = null;
        }
        if (AbstractNetworkContentFragment.this._netWorkDialog != null) {
            AbstractNetworkContentFragment.this._netWorkDialog.dismiss();
            AbstractNetworkContentFragment.this._netWorkDialog = null;
        }
    }
}
