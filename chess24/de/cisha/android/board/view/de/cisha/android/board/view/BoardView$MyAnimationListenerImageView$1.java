/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.view;

import de.cisha.android.board.view.BoardView;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.PieceSetup;

class BoardView.MyAnimationListenerImageView
implements Runnable {
    BoardView.MyAnimationListenerImageView() {
    }

    @Override
    public void run() {
        MyAnimationListenerImageView.this.this$0.setBoardPosition(MyAnimationListenerImageView.this._move.getResultingPieceSetup());
    }
}
