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
import de.cisha.android.board.StatusCodeErrorHelper;
import de.cisha.android.board.service.jsonparser.APIStatusCode;

class MainActivity
implements Runnable {
    final /* synthetic */ IErrorPresenter.ICancelAction val$cancelAction;
    final /* synthetic */ APIStatusCode val$errorCode;
    final /* synthetic */ IErrorPresenter.IReloadAction val$reloadAction;

    MainActivity(IErrorPresenter.ICancelAction iCancelAction, APIStatusCode aPIStatusCode, IErrorPresenter.IReloadAction iReloadAction) {
        this.val$cancelAction = iCancelAction;
        this.val$errorCode = aPIStatusCode;
        this.val$reloadAction = iReloadAction;
    }

    @Override
    public void run() {
        View.OnClickListener onClickListener = this.val$cancelAction != null ? new View.OnClickListener(){

            public void onClick(View view) {
                5.this.val$cancelAction.cancelPressed();
            }
        } : null;
        StatusCodeErrorHelper.handleErrorCode(MainActivity.this, this.val$errorCode, this.val$reloadAction, onClickListener);
    }

}
