/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.standings.model;

import de.cisha.chess.util.Logger;

public class GamePairing<Opponent> {
    private Opponent _opponentLeft;
    private Opponent _opponentRight;

    public GamePairing(Opponent object, Opponent object2) {
        this._opponentLeft = object;
        this._opponentRight = object2;
        if (this._opponentLeft.equals(this._opponentRight)) {
            object = Logger.getInstance();
            object2 = new StringBuilder();
            object2.append("Pairing has the same opponent twice: ");
            object2.append(this._opponentLeft);
            object.error("GamePairing", object2.toString());
        }
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof GamePairing)) {
            return false;
        }
        object = (GamePairing)object;
        if (!object._opponentLeft.equals(this._opponentLeft) || !object._opponentRight.equals(this._opponentRight)) {
            if (object._opponentRight.equals(this._opponentLeft) && object._opponentLeft.equals(this._opponentRight)) {
                return true;
            }
            bl = false;
        }
        return bl;
    }

    public Opponent getOpponentLeft() {
        return this._opponentLeft;
    }

    public Opponent getOpponentRight() {
        return this._opponentRight;
    }

    public int hashCode() {
        return this._opponentLeft.hashCode() + this._opponentRight.hashCode();
    }
}
