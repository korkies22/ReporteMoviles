/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast;

import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.broadcast.TournamentDetailsFragment;

class TournamentDetailsFragment
implements IErrorPresenter.IReloadAction {
    TournamentDetailsFragment() {
    }

    @Override
    public void performReload() {
        3.this.this$0.loadTournament();
    }
}
