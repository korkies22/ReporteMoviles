/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model;

import de.cisha.chess.util.PGNReader;

class PGNGame
implements PGNReader.PGNReaderFinishDelegate {
    PGNGame() {
    }

    @Override
    public void finishedReadingMoves() {
        PGNGame.this._isReadingMoves = false;
    }
}
