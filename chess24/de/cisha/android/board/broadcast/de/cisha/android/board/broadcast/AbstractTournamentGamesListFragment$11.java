/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast;

import de.cisha.android.board.TwoSidedHashMap;
import de.cisha.android.board.broadcast.model.TournamentGameID;
import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import de.cisha.android.board.broadcast.view.TournamentGameInfoView;

class AbstractTournamentGamesListFragment
implements Runnable {
    final /* synthetic */ TournamentGameInfo val$gameInfo;

    AbstractTournamentGamesListFragment(TournamentGameInfo tournamentGameInfo) {
        this.val$gameInfo = tournamentGameInfo;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void run() {
        TwoSidedHashMap twoSidedHashMap = AbstractTournamentGamesListFragment.this._mapIdView;
        synchronized (twoSidedHashMap) {
            if (AbstractTournamentGamesListFragment.this._mapIdView.containsKey(this.val$gameInfo.getGameID().getUuid())) {
                ((TournamentGameInfoView)((Object)AbstractTournamentGamesListFragment.this._mapIdView.get(this.val$gameInfo.getGameID().getUuid()))).setGameInfo(this.val$gameInfo);
            }
            return;
        }
    }
}
