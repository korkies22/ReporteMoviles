/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.tactics;

import de.cisha.android.board.tactics.view.ScrollViewWithSizeListener;
import de.cisha.android.board.view.notation.NotationListView;
import de.cisha.chess.model.Move;

class TacticsStopFragment
implements Runnable {
    final /* synthetic */ Move val$selectedMove;

    TacticsStopFragment(Move move) {
        this.val$selectedMove = move;
    }

    @Override
    public void run() {
        if (TacticsStopFragment.this._notationListScrollView != null) {
            TacticsStopFragment.this._notationListScrollView.scrollTo(0, TacticsStopFragment.this._notationList.getTopPositionForMove(this.val$selectedMove));
        }
    }
}
