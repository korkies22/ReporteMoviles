// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.registration;

import de.cisha.android.board.modalfragments.RookieDialogLoading;
import android.support.v4.app.DialogFragment;
import de.cisha.android.board.BasicFragmentActivity;

public class SingleScreenFragmentActivity extends BasicFragmentActivity
{
    public void hideDialogWaiting() {
        this.runOnUiThread((Runnable)new Runnable() {
            @Override
            public void run() {
                if (SingleScreenFragmentActivity.this._networkLoadingDialog != null) {
                    SingleScreenFragmentActivity.this._networkLoadingDialog.dismissAllowingStateLoss();
                    SingleScreenFragmentActivity.this._networkLoadingDialog = null;
                }
            }
        });
    }
    
    public void showDialogWaiting(final boolean b) {
        this.showDialogWaiting(b, null);
    }
    
    public void showDialogWaiting(final boolean b, final RookieDialogLoading.OnCancelListener onCancelListener) {
        this.runOnUiThread((Runnable)new Runnable() {
            @Override
            public void run() {
                final RookieDialogLoading rookieDialogLoading = new RookieDialogLoading();
                rookieDialogLoading.setCancelable(b);
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
