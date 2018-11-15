/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.engine;

import de.cisha.chess.engine.UCIEngine;
import java.util.TimerTask;

class StockfishEngine
extends TimerTask {
    StockfishEngine() {
    }

    @Override
    public void run() {
        StockfishEngine.this._stockfish.stop();
    }
}
