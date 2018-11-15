/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.view.notation;

import de.cisha.android.board.view.notation.MoveView;
import de.cisha.chess.model.Move;
import java.util.Map;

class NotationListView
implements Runnable {
    final /* synthetic */ Move val$selectedMove;

    NotationListView(Move move) {
        this.val$selectedMove = move;
    }

    @Override
    public void run() {
        if (!(this.val$selectedMove == null || NotationListView.this._selectedMoveView != null && NotationListView.this._selectedMoveView.getMove().equals(this.val$selectedMove))) {
            NotationListView.this.fulfillWithMoveViews(this.val$selectedMove);
            NotationListView.this.setSelectedMoveView((MoveView)((Object)NotationListView.this._mapMoveToView.get(this.val$selectedMove)), this.val$selectedMove);
            return;
        }
        if (this.val$selectedMove == null) {
            NotationListView.this.setSelectedMoveView(null, this.val$selectedMove);
        }
    }
}
