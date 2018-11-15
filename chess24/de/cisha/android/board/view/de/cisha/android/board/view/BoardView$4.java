/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.view;

import de.cisha.android.board.model.BoardMarks;
import de.cisha.chess.model.PieceSetup;
import de.cisha.chess.model.position.Position;

class BoardView
implements Runnable {
    final /* synthetic */ Position val$pos;

    BoardView(Position position) {
        this.val$pos = position;
    }

    @Override
    public void run() {
        BoardView.this.stopMovement(this.val$pos);
        BoardView.this.setBoardPosition(this.val$pos);
        BoardView.this.resetActivePiece();
        BoardView.this._localMarkings.clear();
        BoardView.this.invalidate();
    }
}
