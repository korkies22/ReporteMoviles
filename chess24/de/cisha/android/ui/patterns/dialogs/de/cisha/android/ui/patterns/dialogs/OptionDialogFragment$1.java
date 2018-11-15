/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.ui.patterns.dialogs;

import android.view.View;
import de.cisha.android.ui.patterns.dialogs.OptionDialogFragment;

class OptionDialogFragment
implements View.OnClickListener {
    final /* synthetic */ OptionDialogFragment.Option val$option;

    OptionDialogFragment(OptionDialogFragment.Option option) {
        this.val$option = option;
    }

    public void onClick(View view) {
        if (OptionDialogFragment.this._listener != null) {
            OptionDialogFragment.this._listener.onOptionSelected(this.val$option);
        }
        OptionDialogFragment.this.dismiss();
    }
}
