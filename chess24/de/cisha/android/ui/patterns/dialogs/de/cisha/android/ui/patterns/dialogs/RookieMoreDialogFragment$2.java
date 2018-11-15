/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.ui.patterns.dialogs;

import android.view.View;
import de.cisha.android.ui.patterns.dialogs.RookieMoreDialogFragment;

class RookieMoreDialogFragment
implements View.OnClickListener {
    final /* synthetic */ RookieMoreDialogFragment.ListOption val$option;

    RookieMoreDialogFragment(RookieMoreDialogFragment.ListOption listOption) {
        this.val$option = listOption;
    }

    public void onClick(View view) {
        if (!RookieMoreDialogFragment.this._flagDialogIsDismissed) {
            RookieMoreDialogFragment.this._flagDialogIsDismissed = true;
            RookieMoreDialogFragment.this.dismiss();
            this.val$option.executeAction();
        }
    }
}
