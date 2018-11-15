/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board;

import android.view.View;
import de.cisha.android.board.BasicFragmentActivity;
import de.cisha.android.ui.patterns.dialogs.RookieInfoDialogFragment;

class BasicFragmentActivity
implements View.OnClickListener {
    final /* synthetic */ RookieInfoDialogFragment val$infoDialog;

    BasicFragmentActivity(RookieInfoDialogFragment rookieInfoDialogFragment) {
        this.val$infoDialog = rookieInfoDialogFragment;
    }

    public void onClick(View view) {
        this.val$infoDialog.dismiss();
    }
}
