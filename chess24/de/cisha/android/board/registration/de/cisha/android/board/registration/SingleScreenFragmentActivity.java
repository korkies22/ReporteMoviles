/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.registration;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import de.cisha.android.board.BasicFragmentActivity;
import de.cisha.android.board.modalfragments.RookieDialogLoading;

public class SingleScreenFragmentActivity
extends BasicFragmentActivity {
    public void hideDialogWaiting() {
        this.runOnUiThread(new Runnable(){

            @Override
            public void run() {
                if (SingleScreenFragmentActivity.this._networkLoadingDialog != null) {
                    SingleScreenFragmentActivity.this._networkLoadingDialog.dismissAllowingStateLoss();
                    SingleScreenFragmentActivity.this._networkLoadingDialog = null;
                }
            }
        });
    }

    public void showDialogWaiting(boolean bl) {
        this.showDialogWaiting(bl, null);
    }

    public void showDialogWaiting(final boolean bl, final RookieDialogLoading.OnCancelListener onCancelListener) {
        this.runOnUiThread(new Runnable(){

            @Override
            public void run() {
                RookieDialogLoading rookieDialogLoading = new RookieDialogLoading();
                rookieDialogLoading.setCancelable(bl);
                if (onCancelListener != null) {
                    rookieDialogLoading.setOnCancelListener(onCancelListener);
                }
                if (!SingleScreenFragmentActivity.this.isFinishing() && !SingleScreenFragmentActivity.this._flagOnSaveInstanceStateCalled) {
                    if (SingleScreenFragmentActivity.this._networkLoadingDialog != null) {
                        SingleScreenFragmentActivity.this._networkLoadingDialog.dismissAllowingStateLoss();
                    }
                    rookieDialogLoading.show(SingleScreenFragmentActivity.this.getSupportFragmentManager(), "waitingdialog");
                    SingleScreenFragmentActivity.this._networkLoadingDialog = rookieDialogLoading;
                }
            }
        });
    }

}
