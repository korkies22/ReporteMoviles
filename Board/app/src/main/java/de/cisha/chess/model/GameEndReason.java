// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model;

public enum GameEndReason
{
    ABORTED("ABORTED", "aborted"), 
    CLOCK_FLAG("CLOCKFLAG", "clock flag down"), 
    DISCONNECT_TIMEOUT("DISCONNECT_TIMEOUT", "disconnect timeout"), 
    DRAW_BY_BOTH_FLAGS_DOWN("BOTH_FLAGS_DOWN", "both flags down"), 
    DRAW_BY_CLOCKFLAG_VS_INSUFFICIENT_MATERIAL("CLOCKFLAG_VS_INSUFFICENT_MATERIAL", "clock flag vs. insufficent material"), 
    DRAW_BY_DISCONNECT_VS_INSUFFICENT_MATERIAL("DISCONNECT_VS_INSUFFICENT_MATERIAL", "disconnected, but insufficent material"), 
    DRAW_BY_FIFTY_MOVE_RULE("NO_ACTION", "50 moves rule"), 
    DRAW_BY_INSUFFICENT_MATERIAL("INSUFFICENT_MATERIAL", "insufficent material"), 
    DRAW_BY_MUTUAL_AGREEMENT("DRAW_AGREED", "mutual agreement"), 
    DRAW_BY_STALEMATE("STALEMATE", "stalemate"), 
    DRAW_BY_THREE_REPETITIONS("REPETITION", "three repetitions"), 
    FORFEITURE("TOURNAMENT_WITHDRAW", "forfeiture"), 
    MATE("CHECKMATE", "checkmate"), 
    NO_REASON("NO_REASON", "running"), 
    RESIGN("RESIGN", "resigned");
    
    private String _description;
    private String _reasonKey;
    
    private GameEndReason(final String reasonKey, final String description) {
        this._reasonKey = reasonKey;
        this._description = description;
    }
    
    public static GameEndReason getReasonForKey(final String s) {
        if (s == null) {
            return GameEndReason.NO_REASON;
        }
        final GameEndReason[] values = values();
        for (int length = values.length, i = 0; i < length; ++i) {
            final GameEndReason gameEndReason = values[i];
            if (s.equalsIgnoreCase(gameEndReason._reasonKey)) {
                return gameEndReason;
            }
        }
        return GameEndReason.NO_REASON;
    }
    
    public String getDescription() {
        return this._description;
    }
}
