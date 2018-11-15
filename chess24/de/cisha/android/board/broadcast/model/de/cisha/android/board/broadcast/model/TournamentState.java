/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model;

public enum TournamentState {
    ONGOING(2131690390),
    FINISHED(2131690389),
    UPCOMING(2131690392),
    PAUSED(2131690391);
    
    private int _ressourceId;

    private TournamentState(int n2) {
        this._ressourceId = n2;
    }

    public int getNameResourceId() {
        return this._ressourceId;
    }
}
