/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast;

import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.broadcast.TournamentGameViewFragment;

class TournamentGameViewFragment
implements IErrorPresenter.IReloadAction {
    TournamentGameViewFragment() {
    }

    @Override
    public void performReload() {
        11.this.this$0._flagReloadGame = true;
        11.this.this$0.loadGame();
    }
}
