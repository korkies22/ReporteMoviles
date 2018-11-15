/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.AlertDialog
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 */
package de.cisha.android.board;

import android.app.AlertDialog;
import android.content.DialogInterface;
import de.cisha.android.board.AbstractNetworkContentFragment;

class AbstractNetworkContentFragment
implements DialogInterface.OnClickListener {
    AbstractNetworkContentFragment() {
    }

    public void onClick(DialogInterface dialogInterface, int n) {
        dialogInterface.cancel();
        1.this.this$0._networkLostInfo = null;
    }
}
