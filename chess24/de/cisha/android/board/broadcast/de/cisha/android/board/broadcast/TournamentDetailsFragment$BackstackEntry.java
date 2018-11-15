/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast;

import de.cisha.android.board.broadcast.TournamentDetailsFragment;
import de.cisha.android.board.broadcast.model.TournamentPlayer;
import de.cisha.android.board.broadcast.model.TournamentTeam;

private class TournamentDetailsFragment.BackstackEntry {
    private TournamentPlayer _player;
    private TournamentTeam _team;

    public TournamentDetailsFragment.BackstackEntry(TournamentPlayer tournamentPlayer) {
        this._player = tournamentPlayer;
    }

    public TournamentDetailsFragment.BackstackEntry(TournamentTeam tournamentTeam) {
        this._team = tournamentTeam;
    }

    static /* synthetic */ boolean access$000(TournamentDetailsFragment.BackstackEntry backstackEntry) {
        return backstackEntry.hasPlayer();
    }

    static /* synthetic */ boolean access$100(TournamentDetailsFragment.BackstackEntry backstackEntry) {
        return backstackEntry.hasTeam();
    }

    private boolean hasPlayer() {
        if (this._player != null) {
            return true;
        }
        return false;
    }

    private boolean hasTeam() {
        if (this._team != null) {
            return true;
        }
        return false;
    }

    public TournamentPlayer getPlayer() {
        return this._player;
    }

    public TournamentTeam getTeam() {
        return this._team;
    }
}
