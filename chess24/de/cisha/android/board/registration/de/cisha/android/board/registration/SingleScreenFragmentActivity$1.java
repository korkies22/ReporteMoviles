/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.registration;

import android.support.v4.app.DialogFragment;

class SingleScreenFragmentActivity
implements Runnable {
    SingleScreenFragmentActivity() {
    }

    @Override
    public void run() {
        if (SingleScreenFragmentActivity.this._networkLoadingDialog != null) {
            SingleScreenFragmentActivity.this._networkLoadingDialog.dismissAllowingStateLoss();
            SingleScreenFragmentActivity.this._networkLoadingDialog = null;
        }
    }
}
