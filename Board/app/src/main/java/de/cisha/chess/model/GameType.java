// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model;

public enum GameType
{
    ANALYZED_GAME(true, 3), 
    BROADCAST(false, 5), 
    GAME_VS_ENGINE(false, 2), 
    ONLINEPLAYZONE(false, 1), 
    PGN_GAME(false, 4), 
    TACTICS(false, 6), 
    UNDEFINED(true, 0);
    
    private boolean _editable;
    private int _finalNumRepresentation;
    
    private GameType(final boolean editable, final int finalNumRepresentation) {
        this._editable = editable;
        this._finalNumRepresentation = finalNumRepresentation;
    }
    
    public static GameType forNumericRepresentation(final int n) {
        final GameType[] values = values();
        for (int i = 0; i < values.length; ++i) {
            final GameType gameType = values[i];
            if (gameType._finalNumRepresentation == n) {
                return gameType;
            }
        }
        return null;
    }
    
    public int getNumericRepresentation() {
        return this._finalNumRepresentation;
    }
    
    public boolean isEditable() {
        return this._editable;
    }
}
