/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.ui.patterns.dialogs;

import android.support.v4.app.FragmentActivity;
import de.cisha.android.ui.patterns.R;
import de.cisha.android.ui.patterns.dialogs.RookieMoreDialogFragment;

class RookieMoreDialogFragment
implements RookieMoreDialogFragment.ListOption {
    RookieMoreDialogFragment() {
    }

    @Override
    public void executeAction() {
    }

    @Override
    public String getString() {
        return RookieMoreDialogFragment.this.getActivity().getString(R.string.rookie_more_dialog_option_cancel);
    }
}
