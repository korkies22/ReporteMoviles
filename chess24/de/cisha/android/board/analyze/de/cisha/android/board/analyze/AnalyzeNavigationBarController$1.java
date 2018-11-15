/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.analyze;

import de.cisha.chess.model.Move;
import de.cisha.chess.model.MovesObservable;

class AnalyzeNavigationBarController
implements Runnable {
    AnalyzeNavigationBarController() {
    }

    @Override
    public void run() {
        AnalyzeNavigationBarController.this.updatePositionNavigationButtons(AnalyzeNavigationBarController.this._movesObservable.getCurrentMove());
    }
}
