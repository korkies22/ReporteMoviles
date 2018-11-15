/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast;

import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.service.jsonparser.APIStatusCode;

class TournamentDetailsFragment
implements Runnable {
    final /* synthetic */ APIStatusCode val$errorCode;

    TournamentDetailsFragment(APIStatusCode aPIStatusCode) {
        this.val$errorCode = aPIStatusCode;
    }

    @Override
    public void run() {
        TournamentDetailsFragment.this.showViewForErrorCode(this.val$errorCode, new IErrorPresenter.IReloadAction(){

            @Override
            public void performReload() {
                TournamentDetailsFragment.this.loadTournament();
            }
        }, true);
    }

}
