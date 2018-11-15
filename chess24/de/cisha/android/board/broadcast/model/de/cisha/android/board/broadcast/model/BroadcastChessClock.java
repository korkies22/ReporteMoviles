/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model;

import de.cisha.android.board.playzone.model.PlayZoneChessClock;
import de.cisha.chess.model.ClockSetting;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.Piece;
import java.util.List;

public class BroadcastChessClock
extends PlayZoneChessClock {
    public BroadcastChessClock(ClockSetting clockSetting) {
        super(clockSetting);
    }

    @Override
    public void resetWithGame(Game object) {
        this.reset();
        if (object != null) {
            this.setUpdateObservers(false);
            for (Move move : object.getAllMainLineMoves()) {
                if (move.isUserMove()) continue;
                boolean bl = move.getPiece().getColor();
                this.addTimeUsage(move.getMoveTimeInMills(), bl);
            }
            this.setUpdateObservers(true);
        }
    }
}
