/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model;

import de.cisha.android.board.broadcast.model.DummyTournamentService;
import de.cisha.android.board.broadcast.model.TournamentRoundInfo;

class DummyTournamentService
extends TournamentRoundInfo {
    DummyTournamentService() {
    }

    @Override
    public int compareTo(TournamentRoundInfo tournamentRoundInfo) {
        return 0;
    }

    @Override
    public TournamentRoundInfo getMainRound() {
        return this;
    }

    @Override
    public String getRoundString() {
        return "R-1";
    }
}
