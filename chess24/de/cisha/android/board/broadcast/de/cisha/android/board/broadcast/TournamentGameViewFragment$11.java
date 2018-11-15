/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast;

import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.service.jsonparser.APIStatusCode;

class TournamentGameViewFragment
implements Runnable {
    final /* synthetic */ APIStatusCode val$errorCode;

    TournamentGameViewFragment(APIStatusCode aPIStatusCode) {
        this.val$errorCode = aPIStatusCode;
    }

    @Override
    public void run() {
        TournamentGameViewFragment.this.hideDialogWaiting();
        TournamentGameViewFragment.this.showViewForErrorCode(this.val$errorCode, new IErrorPresenter.IReloadAction(){

            @Override
            public void performReload() {
                TournamentGameViewFragment.this._flagReloadGame = true;
                TournamentGameViewFragment.this.loadGame();
            }
        }, true);
    }

}
