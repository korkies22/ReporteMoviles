// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.model;

public enum TournamentState
{
    FINISHED(2131690389), 
    ONGOING(2131690390), 
    PAUSED(2131690391), 
    UPCOMING(2131690392);
    
    private int _ressourceId;
    
    private TournamentState(final int ressourceId) {
        this._ressourceId = ressourceId;
    }
    
    public int getNameResourceId() {
        return this._ressourceId;
    }
}
