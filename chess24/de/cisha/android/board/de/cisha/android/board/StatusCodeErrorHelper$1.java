/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board;

import android.view.View;
import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.modalfragments.RookieDialogFragmentNoInternet;

static final class StatusCodeErrorHelper
implements View.OnClickListener {
    final /* synthetic */ RookieDialogFragmentNoInternet val$fraggi;
    final /* synthetic */ IErrorPresenter.IReloadAction val$reloadAction;

    StatusCodeErrorHelper(IErrorPresenter.IReloadAction iReloadAction, RookieDialogFragmentNoInternet rookieDialogFragmentNoInternet) {
        this.val$reloadAction = iReloadAction;
        this.val$fraggi = rookieDialogFragmentNoInternet;
    }

    public void onClick(View view) {
        if (this.val$reloadAction != null) {
            this.val$reloadAction.performReload();
            this.val$fraggi.dismiss();
        }
    }
}
