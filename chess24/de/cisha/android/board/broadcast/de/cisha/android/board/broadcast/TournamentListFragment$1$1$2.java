/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast;

import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.broadcast.TournamentListFragment;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.ui.list.UpdatingList;

class TournamentListFragment
implements Runnable {
    final /* synthetic */ APIStatusCode val$errorCode;

    TournamentListFragment(APIStatusCode aPIStatusCode) {
        this.val$errorCode = aPIStatusCode;
    }

    @Override
    public void run() {
        1.this.this$1.this$0.hideDialogWaiting();
        1.this.this$1.this$0._tournamentList.updateFinished();
        IErrorPresenter iErrorPresenter = 1.this.this$1.this$0.getErrorHandler();
        if (iErrorPresenter != null) {
            iErrorPresenter.showViewForErrorCode(this.val$errorCode, new IErrorPresenter.IReloadAction(){

                @Override
                public void performReload() {
                    1.this.this$1.this$0.loadTournaments();
                }
            }, true);
        }
    }

}
