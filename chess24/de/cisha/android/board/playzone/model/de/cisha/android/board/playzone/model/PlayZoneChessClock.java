/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.model;

import de.cisha.android.board.playzone.model.ChessClock;
import de.cisha.chess.model.ClockSetting;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.Piece;
import java.util.List;

public class PlayZoneChessClock
extends ChessClock {
    private int _timeMillisBlack;
    private int _timeMillisWhite;
    private boolean _updateing = true;

    public PlayZoneChessClock(ClockSetting clockSetting) {
        super(clockSetting);
    }

    public boolean addTimeUsage(int n, boolean bl) {
        int n2;
        if (!this._countUpwards) {
            n2 = this.getClockSettings().getIncrementPerMove(bl);
        } else {
            n = - n;
            n2 = 0;
        }
        if (bl) {
            this._timeMillisWhite += n - n2;
            this.setRemainingTime(bl, this.getStartTimeMillis(bl) - (long)this._timeMillisWhite);
        } else {
            this._timeMillisBlack += n - n2;
            this.setRemainingTime(bl, this.getStartTimeMillis(bl) - (long)this._timeMillisBlack);
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

    public void resetWithGame(Game object) {
        this.reset();
        if (object != null) {
            this.setUpdateObservers(false);
            for (Move move : object.getAllMainLineMoves()) {
                boolean bl = move.getPiece().getColor();
                this.addTimeUsage(move.getMoveTimeInMills(), bl);
            }
            this.setUpdateObservers(true);
        }
    }

    protected void setUpdateObservers(boolean bl) {
        boolean bl2 = bl && !this._updateing;
        this._updateing = bl;
        if (bl2) {
            this.fireUpdate();
        }
    }
}
