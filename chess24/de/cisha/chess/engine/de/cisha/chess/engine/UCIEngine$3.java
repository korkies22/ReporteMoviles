/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.engine;

import de.cisha.chess.model.FEN;
import de.cisha.chess.model.position.Position;

class UCIEngine
implements Runnable {
    final /* synthetic */ FEN val$fen;

    UCIEngine(FEN fEN) {
        this.val$fen = fEN;
    }

    @Override
    public void run() {
        UCIEngine.this._currentPosition = new Position(this.val$fen);
    }
}
