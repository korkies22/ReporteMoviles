/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.registration;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import de.cisha.android.board.modalfragments.RookieDialogLoading;

class SingleScreenFragmentActivity
implements Runnable {
    final /* synthetic */ boolean val$cancelable;
    final /* synthetic */ RookieDialogLoading.OnCancelListener val$listener;

    SingleScreenFragmentActivity(boolean bl, RookieDialogLoading.OnCancelListener onCancelListener) {
        this.val$cancelable = bl;
        this.val$listener = onCancelListener;
    }

    @Override
    public void run() {
        RookieDialogLoading rookieDialogLoading = new RookieDialogLoading();
        rookieDialogLoading.setCancelable(this.val$cancelable);
        if (this.val$listener != null) {
            rookieDialogLoading.setOnCancelListener(this.val$listener);
        }
        if (!SingleScreenFragmentActivity.this.isFinishing() && !SingleScreenFragmentActivity.this._flagOnSaveInstanceStateCalled) {
            if (SingleScreenFragmentActivity.this._networkLoadingDialog != null) {
                SingleScreenFragmentActivity.this._networkLoadingDialog.dismissAllowingStateLoss();
            }
            rookieDialogLoading.show(SingleScreenFragmentActivity.this.getSupportFragmentManager(), "waitingdialog");
            SingleScreenFragmentActivity.this._networkLoadingDialog = rookieDialogLoading;
        }
    }
}
