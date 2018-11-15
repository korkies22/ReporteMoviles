/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model;

public enum GameType {
    UNDEFINED(true, 0),
    ONLINEPLAYZONE(false, 1),
    GAME_VS_ENGINE(false, 2),
    ANALYZED_GAME(true, 3),
    PGN_GAME(false, 4),
    BROADCAST(false, 5),
    TACTICS(false, 6);
    
    private boolean _editable;
    private int _finalNumRepresentation;

    private GameType(boolean bl, int n2) {
        this._editable = bl;
        this._finalNumRepresentation = n2;
    }

    public static GameType forNumericRepresentation(int n) {
        for (GameType gameType : GameType.values()) {
            if (gameType._finalNumRepresentation != n) continue;
            return gameType;
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
