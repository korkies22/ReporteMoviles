/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast;

import de.cisha.android.board.TwoSidedHashMap;
import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import de.cisha.android.board.broadcast.model.TournamentTeamPairing;
import de.cisha.android.board.broadcast.view.TournamentTeamMatchView;
import java.util.Map;

class AbstractTeamPairingsFragment
implements Runnable {
    final /* synthetic */ TournamentGameInfo val$gameInfo;

    AbstractTeamPairingsFragment(TournamentGameInfo tournamentGameInfo) {
        this.val$gameInfo = tournamentGameInfo;
    }

    @Override
    public void run() {
        TournamentTeamMatchView tournamentTeamMatchView;
        TournamentTeamPairing tournamentTeamPairing = (TournamentTeamPairing)AbstractTeamPairingsFragment.this._mapGameTeam.get(this.val$gameInfo);
        if (tournamentTeamPairing != null && (tournamentTeamMatchView = (TournamentTeamMatchView)((Object)AbstractTeamPairingsFragment.this._mapViewPairing.getKeyForValue(tournamentTeamPairing))) != null) {
            tournamentTeamMatchView.setMatchIsOngoing(tournamentTeamPairing.isOngoing());
            tournamentTeamMatchView.setPointsText(tournamentTeamPairing.getPointsTeam1(), tournamentTeamPairing.getPointsTeam2());
        }
    }
}
