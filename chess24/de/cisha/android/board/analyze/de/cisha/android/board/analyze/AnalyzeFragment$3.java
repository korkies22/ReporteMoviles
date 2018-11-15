/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.analyze;

import de.cisha.chess.model.GameHolder;
import de.cisha.chess.util.PGNReader;

class AnalyzeFragment
implements PGNReader.PGNReaderFinishDelegate {
    AnalyzeFragment() {
    }

    @Override
    public void finishedReadingMoves() {
        AnalyzeFragment.this.hideDialogWaiting();
        AnalyzeFragment.this.getGameHolder().setUpdateObservers(true);
    }
}
