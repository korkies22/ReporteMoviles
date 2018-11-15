/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model;

import de.cisha.android.board.broadcast.model.Tournament;
import de.cisha.android.board.broadcast.model.TournamentPlayer;
import de.cisha.android.board.broadcast.model.jsonparsers.BroadcastTournamentType;
import java.util.List;

public class SinglePlayerTournament
extends Tournament {
    private List<TournamentPlayer> _standings;

    public SinglePlayerTournament(String string, String string2) {
        super(string, string2, BroadcastTournamentType.SINGLEPLAYER_OPEN);
    }

    @Override
    public List<TournamentPlayer> getPlayerStandings() {
        return this._standings;
    }

    @Override
    public boolean hasTeams() {
        return false;
    }

    @Override
    public boolean isStandingsEnabled() {
        return true;
    }

    public void setPlayerStandings(List<TournamentPlayer> list) {
        this._standings = list;
    }
}
