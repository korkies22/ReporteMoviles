// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model;

public class GameStatus
{
    public static final GameStatus GAME_RUNNING;
    private final GameEndReason _reason;
    private final GameResult _result;
    
    static {
        GAME_RUNNING = new GameStatus(GameResult.NO_RESULT, GameEndReason.NO_REASON);
    }
    
    public GameStatus(final GameResult result, final GameEndReason reason) {
        this._result = result;
        this._reason = reason;
    }
    
    @Override
    public boolean equals(final Object o) {
        final boolean b = o instanceof GameStatus;
        final boolean b2 = false;
        if (b) {
            final GameStatus gameStatus = (GameStatus)o;
            boolean b3 = b2;
            if (this._reason == gameStatus.getReason()) {
                b3 = b2;
                if (this._result == gameStatus.getResult()) {
                    b3 = true;
                }
            }
            return b3;
        }
        return false;
    }
    
    public GameEndReason getReason() {
        return this._reason;
    }
    
    public GameResult getResult() {
        return this._result;
    }
    
    @Override
    public int hashCode() {
        return this._reason.ordinal() + this._result.ordinal();
    }
    
    public boolean isFinished() {
        return this.getResult() != null && this.getResult() != GameResult.NO_RESULT;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this._reason.getDescription());
        sb.append("\n");
        sb.append(this._result.getDescription());
        return sb.toString();
    }
}
