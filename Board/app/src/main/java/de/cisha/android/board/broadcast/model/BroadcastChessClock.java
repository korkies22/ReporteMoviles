// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.model;

import java.util.Iterator;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.ClockSetting;
import de.cisha.android.board.playzone.model.PlayZoneChessClock;

public class BroadcastChessClock extends PlayZoneChessClock
{
    public BroadcastChessClock(final ClockSetting clockSetting) {
        super(clockSetting);
    }
    
    @Override
    public void resetWithGame(final Game game) {
        this.reset();
        if (game != null) {
            this.setUpdateObservers(false);
            for (final Move move : game.getAllMainLineMoves()) {
                if (!move.isUserMove()) {
                    this.addTimeUsage(move.getMoveTimeInMills(), move.getPiece().getColor());
                }
            }
            this.setUpdateObservers(true);
        }
    }
}
