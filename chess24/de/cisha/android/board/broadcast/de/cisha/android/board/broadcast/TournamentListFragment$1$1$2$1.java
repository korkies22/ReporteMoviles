/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast;

import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.broadcast.TournamentListFragment;

class TournamentListFragment
implements IErrorPresenter.IReloadAction {
    TournamentListFragment() {
    }

    @Override
    public void performReload() {
        2.this.this$2.this$1.this$0.loadTournaments();
    }
}
