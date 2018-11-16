// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.model;

import java.util.Iterator;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.ClockSetting;

public class PlayZoneChessClock extends ChessClock
{
    private int _timeMillisBlack;
    private int _timeMillisWhite;
    private boolean _updateing;
    
    public PlayZoneChessClock(final ClockSetting clockSetting) {
        super(clockSetting);
        this._updateing = true;
    }
    
    public boolean addTimeUsage(int n, final boolean b) {
        int incrementPerMove;
        if (!this._countUpwards) {
            incrementPerMove = this.getClockSettings().getIncrementPerMove(b);
        }
        else {
            n = -n;
            incrementPerMove = 0;
        }
        if (b) {
            this._timeMillisWhite += n - incrementPerMove;
            this.setRemainingTime(b, this.getStartTimeMillis(b) - this._timeMillisWhite);
        }
        else {
            this._timeMillisBlack += n - incrementPerMove;
            this.setRemainingTime(b, this.getStartTimeMillis(b) - this._timeMillisBlack);
        }
        if (this._updateing) {
            this.fireUpdate();
        }
        return true;
    }
    
    @Override
    public void reset() {
        super.reset();
        this._timeMillisBlack = 0;
        this._timeMillisWhite = 0;
    }
    
    public void resetWithGame(final Game game) {
        this.reset();
        if (game != null) {
            this.setUpdateObservers(false);
            for (final Move move : game.getAllMainLineMoves()) {
                this.addTimeUsage(move.getMoveTimeInMills(), move.getPiece().getColor());
            }
            this.setUpdateObservers(true);
        }
    }
    
    protected void setUpdateObservers(final boolean updateing) {
        final boolean b = updateing && !this._updateing;
        this._updateing = updateing;
        if (b) {
            this.fireUpdate();
        }
    }
}
