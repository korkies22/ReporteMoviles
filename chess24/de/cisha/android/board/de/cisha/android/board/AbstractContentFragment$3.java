/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board;

import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.modalfragments.RookieDialogLoading;

class AbstractContentFragment
implements RookieDialogLoading.OnCancelListener {
    final /* synthetic */ boolean val$cancelable;
    final /* synthetic */ RookieDialogLoading.OnCancelListener val$listener;
    final /* synthetic */ boolean val$popFragmentOnCancel;

    AbstractContentFragment(boolean bl, boolean bl2, RookieDialogLoading.OnCancelListener onCancelListener) {
        this.val$cancelable = bl;
        this.val$popFragmentOnCancel = bl2;
        this.val$listener = onCancelListener;
    }

    @Override
    public void onCancel() {
        if (this.val$cancelable) {
            IContentPresenter iContentPresenter;
            AbstractContentFragment.this.hideDialogWaiting();
            if (this.val$popFragmentOnCancel && (iContentPresenter = AbstractContentFragment.this.getContentPresenter()) != null) {
                iContentPresenter.popCurrentFragment();
            }
            AbstractContentFragment.this.onLoadingDialogCancelled();
            if (this.val$listener != null) {
                this.val$listener.onCancel();
            }
        }
    }
}
