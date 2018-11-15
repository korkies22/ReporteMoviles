/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 */
package de.cisha.android.board.tactics;

import android.os.Handler;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.tactics.TacticsStopFragment;

class TacticsStopFragment
implements Runnable {
    final /* synthetic */ APIStatusCode val$errorCode;

    TacticsStopFragment(APIStatusCode aPIStatusCode) {
        this.val$errorCode = aPIStatusCode;
    }

    @Override
    public void run() {
        2.this.this$0.hideDialogWaiting();
        final IContentPresenter iContentPresenter = 2.this.this$0.getContentPresenter();
        IErrorPresenter iErrorPresenter = 2.this.this$0.getErrorHandler();
        if (this.val$errorCode == APIStatusCode.API_ERROR_NOT_SET && iContentPresenter != null) {
            new Handler().post(new Runnable(){

                @Override
                public void run() {
                    iContentPresenter.popCurrentFragment();
                }
            });
            return;
        }
        if (iErrorPresenter != null) {
            iErrorPresenter.showViewForErrorCode(this.val$errorCode, new IErrorPresenter.IReloadAction(){

                @Override
                public void performReload() {
                    2.this.this$0.loadSession();
                }
            }, true);
        }
    }

}
