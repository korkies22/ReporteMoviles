/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.tactics;

import de.cisha.android.board.view.BoardView;
import de.cisha.chess.model.Square;

class TacticsExerciseFragment
implements Runnable {
    final /* synthetic */ Square val$hint;

    TacticsExerciseFragment(Square square) {
        this.val$hint = square;
    }

    @Override
    public void run() {
        TacticsExerciseFragment.this._board.markSquare(this.val$hint, -16711936);
    }
}
