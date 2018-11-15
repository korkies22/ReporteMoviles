/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.engine.model;

import de.cisha.android.board.playzone.engine.model.AbstractSimpleEngineHumanization;
import de.cisha.android.board.playzone.model.ChessClock;
import de.cisha.android.board.playzone.model.TimeControl;
import de.cisha.chess.model.position.Position;

class EngineGameAdapter
extends AbstractSimpleEngineHumanization {
    EngineGameAdapter(int n, boolean bl) {
        super(n, bl);
    }

    @Override
    protected int getActionCounter() {
        return EngineGameAdapter.this.getPosition().getActionCounter();
    }

    @Override
    protected long getBaseTime() {
        if (!EngineGameAdapter.this._time.hasTimeControl()) {
            return 300000L;
        }
        return EngineGameAdapter.this._time.getMinutes() * 60000;
    }

    @Override
    protected long getMillisSinceLastMove() {
        return EngineGameAdapter.this.getClock().getTimeSinceLastMove();
    }

    @Override
    protected long getRemainingMillis() {
        long l = !EngineGameAdapter.this._time.hasTimeControl() ? 300000L : EngineGameAdapter.this.getClock().getTimeMillis(EngineGameAdapter.this._playerIsWhite ^ true);
        if (l > 0L) {
            return l;
        }
        return 0L;
    }
}
