/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model;

import de.cisha.android.board.broadcast.model.Tournament;

public static class Tournament.TournamentStructureKey {
    private String _game;
    private String _match;
    private String _round;

    public Tournament.TournamentStructureKey(String string, String string2, String string3) {
        this._round = string;
        this._match = string2;
        this._game = string3;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Tournament.TournamentStructureKey)) {
            return false;
        }
        object = (Tournament.TournamentStructureKey)object;
        if (this._round.equals(object._round) && this._match.equals(object._match) && this._game.equals(object._game)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this._round.hashCode() + this._match.hashCode() + this._game.hashCode();
    }
}
