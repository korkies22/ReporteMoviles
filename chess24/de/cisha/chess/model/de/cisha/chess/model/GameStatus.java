/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model;

import de.cisha.chess.model.GameEndReason;
import de.cisha.chess.model.GameResult;

public class GameStatus {
    public static final GameStatus GAME_RUNNING = new GameStatus(GameResult.NO_RESULT, GameEndReason.NO_REASON);
    private final GameEndReason _reason;
    private final GameResult _result;

    public GameStatus(GameResult gameResult, GameEndReason gameEndReason) {
        this._result = gameResult;
        this._reason = gameEndReason;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof GameStatus;
        boolean bl2 = false;
        if (bl) {
            object = (GameStatus)object;
            bl = bl2;
            if (this._reason == object.getReason()) {
                bl = bl2;
                if (this._result == object.getResult()) {
                    bl = true;
                }
            }
            return bl;
        }
        return false;
    }

    public GameEndReason getReason() {
        return this._reason;
    }

    public GameResult getResult() {
        return this._result;
    }

    public int hashCode() {
        return this._reason.ordinal() + this._result.ordinal();
    }

    public boolean isFinished() {
        if (this.getResult() != null && this.getResult() != GameResult.NO_RESULT) {
            return true;
        }
        return false;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this._reason.getDescription());
        stringBuilder.append("\n");
        stringBuilder.append(this._result.getDescription());
        return stringBuilder.toString();
    }
}
