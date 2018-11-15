/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model;

import de.cisha.android.board.broadcast.model.BroadcastGameStatus;
import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import de.cisha.android.board.broadcast.model.TournamentPlayer;
import de.cisha.android.board.broadcast.model.TournamentTeam;
import de.cisha.chess.model.GameResult;
import java.util.Iterator;
import java.util.List;

public class TournamentTeamPairing {
    private List<TournamentGameInfo> _games;
    private TournamentTeam _team1;
    private TournamentTeam _team2;

    public TournamentTeamPairing(TournamentTeam tournamentTeam, TournamentTeam tournamentTeam2, List<TournamentGameInfo> list) {
        this._team1 = tournamentTeam;
        this._team2 = tournamentTeam2;
        this._games = list;
    }

    private float getPointsTeam(boolean bl) {
        Iterator<TournamentGameInfo> iterator = this._games.iterator();
        float f = 0.0f;
        while (iterator.hasNext()) {
            TournamentGameInfo tournamentGameInfo = iterator.next();
            GameResult gameResult = tournamentGameInfo.getStatus().getResult();
            TournamentPlayer tournamentPlayer = bl ? tournamentGameInfo.getPlayerLeft() : tournamentGameInfo.getPlayerRight();
            boolean bl2 = tournamentPlayer == tournamentGameInfo.getPlayerWhite();
            float f2 = bl2 ? gameResult.getScoreWhite() : gameResult.getScoreBlack();
            f += f2;
        }
        return f;
    }

    public List<TournamentGameInfo> getGames() {
        return this._games;
    }

    public float getPointsTeam1() {
        return this.getPointsTeam(true);
    }

    public float getPointsTeam2() {
        return this.getPointsTeam(false);
    }

    public TournamentTeam getTeam1() {
        return this._team1;
    }

    public TournamentTeam getTeam2() {
        return this._team2;
    }

    public boolean isOngoing() {
        Iterator<TournamentGameInfo> iterator = this._games.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().isOngoing()) continue;
            return true;
        }
        return false;
    }
}
