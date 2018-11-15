/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.tactics;

import de.cisha.android.board.view.notation.NotationListView;
import de.cisha.chess.model.Move;

class TacticsStopFragment
implements Runnable {
    final /* synthetic */ Move val$newMove;

    TacticsStopFragment(Move move) {
        this.val$newMove = move;
    }

    @Override
    public void run() {
        if (TacticsStopFragment.this._notationList != null) {
            TacticsStopFragment.this._notationList.selectedMoveChanged(this.val$newMove);
            TacticsStopFragment.this._notationList.markMoveWithColor(this.val$newMove, -65536);
        }
    }
}
