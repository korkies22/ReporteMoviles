/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone;

import de.cisha.android.ui.patterns.dialogs.RookieInfoDialogFragment;

class AbstractPlayzoneFragment
implements Runnable {
    AbstractPlayzoneFragment() {
    }

    @Override
    public void run() {
        if (AbstractPlayzoneFragment.this._rookieDialog != null) {
            AbstractPlayzoneFragment.this._rookieDialog.dismiss();
            AbstractPlayzoneFragment.this._rookieDialog = null;
        }
    }
}
