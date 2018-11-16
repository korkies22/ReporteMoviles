// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.standings.model;

import de.cisha.chess.util.Logger;

public class GamePairing<Opponent>
{
    private Opponent _opponentLeft;
    private Opponent _opponentRight;
    
    public GamePairing(final Opponent opponentLeft, final Opponent opponentRight) {
        this._opponentLeft = opponentLeft;
        this._opponentRight = opponentRight;
        if (this._opponentLeft.equals(this._opponentRight)) {
            final Logger instance = Logger.getInstance();
            final StringBuilder sb = new StringBuilder();
            sb.append("Pairing has the same opponent twice: ");
            sb.append(this._opponentLeft);
            instance.error("GamePairing", sb.toString());
        }
    }
    
    @Override
    public boolean equals(final Object o) {
        boolean b = true;
        if (o == this) {
            return true;
        }
        if (!(o instanceof GamePairing)) {
            return false;
        }
        final GamePairing gamePairing = (GamePairing)o;
        if (!gamePairing._opponentLeft.equals(this._opponentLeft) || !gamePairing._opponentRight.equals(this._opponentRight)) {
            if (gamePairing._opponentRight.equals(this._opponentLeft) && gamePairing._opponentLeft.equals(this._opponentRight)) {
                return true;
            }
            b = false;
        }
        return b;
    }
    
    public Opponent getOpponentLeft() {
        return this._opponentLeft;
    }
    
    public Opponent getOpponentRight() {
        return this._opponentRight;
    }
    
    @Override
    public int hashCode() {
        return this._opponentLeft.hashCode() + this._opponentRight.hashCode();
    }
}
