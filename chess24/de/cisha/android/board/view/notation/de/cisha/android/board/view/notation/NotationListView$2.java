/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.view.notation;

import de.cisha.android.board.view.notation.Line;
import de.cisha.android.board.view.notation.MoveView;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveContainer;
import java.util.Map;

class NotationListView
implements Runnable {
    final /* synthetic */ Move val$move;

    NotationListView(Move move) {
        this.val$move = move;
    }

    @Override
    public void run() {
        if (NotationListView.this._mapMoveToView.isEmpty()) {
            NotationListView.this._rootMoveContainer = this.val$move.getGame();
            MoveView moveView = NotationListView.this.createMoveView(this.val$move, 0);
            NotationListView.this.createLineWith(0, 0, null, moveView);
        }
        NotationListView.this.fulfillWithMoveViews(this.val$move);
    }
}
