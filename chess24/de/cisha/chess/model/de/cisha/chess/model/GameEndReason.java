/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model;

public enum GameEndReason {
    NO_REASON("NO_REASON", "running"),
    ABORTED("ABORTED", "aborted"),
    DISCONNECT_TIMEOUT("DISCONNECT_TIMEOUT", "disconnect timeout"),
    DRAW_BY_MUTUAL_AGREEMENT("DRAW_AGREED", "mutual agreement"),
    DRAW_BY_STALEMATE("STALEMATE", "stalemate"),
    DRAW_BY_THREE_REPETITIONS("REPETITION", "three repetitions"),
    DRAW_BY_FIFTY_MOVE_RULE("NO_ACTION", "50 moves rule"),
    DRAW_BY_INSUFFICENT_MATERIAL("INSUFFICENT_MATERIAL", "insufficent material"),
    DRAW_BY_CLOCKFLAG_VS_INSUFFICIENT_MATERIAL("CLOCKFLAG_VS_INSUFFICENT_MATERIAL", "clock flag vs. insufficent material"),
    DRAW_BY_DISCONNECT_VS_INSUFFICENT_MATERIAL("DISCONNECT_VS_INSUFFICENT_MATERIAL", "disconnected, but insufficent material"),
    DRAW_BY_BOTH_FLAGS_DOWN("BOTH_FLAGS_DOWN", "both flags down"),
    CLOCK_FLAG("CLOCKFLAG", "clock flag down"),
    RESIGN("RESIGN", "resigned"),
    MATE("CHECKMATE", "checkmate"),
    FORFEITURE("TOURNAMENT_WITHDRAW", "forfeiture");
    
    private String _description;
    private String _reasonKey;

    private GameEndReason(String string2, String string3) {
        this._reasonKey = string2;
        this._description = string3;
    }

    public static GameEndReason getReasonForKey(String string) {
        if (string == null) {
            return NO_REASON;
        }
        for (GameEndReason gameEndReason : GameEndReason.values()) {
            if (!string.equalsIgnoreCase(gameEndReason._reasonKey)) continue;
            return gameEndReason;
        }
        return NO_REASON;
    }

    public String getDescription() {
        return this._description;
    }
}
