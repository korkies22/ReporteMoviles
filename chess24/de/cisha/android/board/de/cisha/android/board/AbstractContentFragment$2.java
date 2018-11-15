/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board;

import de.cisha.android.board.modalfragments.RookieDialogLoading;

class AbstractContentFragment
implements Runnable {
    final /* synthetic */ boolean val$cancelable;
    final /* synthetic */ RookieDialogLoading.OnCancelListener val$listener;
    final /* synthetic */ String val$message;
    final /* synthetic */ boolean val$popFragmentOnCancel;

    AbstractContentFragment(boolean bl, String string, RookieDialogLoading.OnCancelListener onCancelListener, boolean bl2) {
        this.val$cancelable = bl;
        this.val$message = string;
        this.val$listener = onCancelListener;
        this.val$popFragmentOnCancel = bl2;
    }

    @Override
    public void run() {
        if (!AbstractContentFragment.this._flagOnSaveInstanceStateCalled) {
            AbstractContentFragment.this._networkLoadingDialog = AbstractContentFragment.this.showDialog(this.val$cancelable, "", this.val$message, this.val$listener, this.val$popFragmentOnCancel);
        }
        AbstractContentFragment.this._showLoadingViewAction = this;
    }
}
