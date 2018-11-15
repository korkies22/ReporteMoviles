/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board;

import android.support.v4.app.FragmentManager;
import de.cisha.android.board.modalfragments.RookieDialogLoading;

class AbstractContentFragment
implements Runnable {
    AbstractContentFragment() {
    }

    @Override
    public void run() {
        FragmentManager fragmentManager = AbstractContentFragment.this.getChildFragmentManager();
        if (AbstractContentFragment.this._networkLoadingDialog != null && fragmentManager != null) {
            if (AbstractContentFragment.this.isAdded() && !AbstractContentFragment.this.isRemoving() && AbstractContentFragment.this._networkLoadingDialog.isAdded() && !AbstractContentFragment.this._networkLoadingDialog.isRemoving()) {
                AbstractContentFragment.this._networkLoadingDialog.dismissAllowingStateLoss();
                AbstractContentFragment.this._networkLoadingDialog = null;
            } else {
                AbstractContentFragment.this._flagRemoveLoadingDialogOnResume = true;
            }
        }
        AbstractContentFragment.this._showLoadingViewAction = null;
    }
}
