/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Color
 *  android.view.animation.Animation
 *  android.view.animation.Animation$AnimationListener
 */
package de.cisha.android.board.view;

import android.graphics.Color;
import android.view.animation.Animation;
import de.cisha.android.board.model.Arrow;
import de.cisha.android.board.model.ArrowCategory;
import de.cisha.android.board.model.ArrowContainer;
import de.cisha.android.board.model.BoardMarks;
import de.cisha.android.board.view.BoardView;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.Piece;
import de.cisha.chess.model.SEP;
import de.cisha.chess.model.Square;

class BoardView
implements Runnable {
    final /* synthetic */ boolean val$animateMove;
    final /* synthetic */ Move val$move;
    final /* synthetic */ boolean val$sound;

    BoardView(Move move, boolean bl, boolean bl2) {
        this.val$move = move;
        this.val$animateMove = bl;
        this.val$sound = bl2;
    }

    @Override
    public void run() {
        Object object;
        BoardView.this._localMarkings.clear();
        if (this.val$move != null && BoardView.this._boardViewSettings.isArrowOfLastMoveEnabled()) {
            object = new Arrow(this.val$move.getSEP(), ArrowCategory.LAST_MOVE, Color.argb((int)110, (int)255, (int)255, (int)255));
            BoardView.this._localMarkings.getArrowContainer().addArrow((Arrow)object);
            BoardView.this._arrowsLayer.invalidate();
        }
        object = BoardView.this._activePieceSquare;
        BoardView.this.resetActivePiece();
        if (object != null && this.val$move != null && (BoardView.this._allowWhitePremoves && this.val$move.getPiece().isBlack() || BoardView.this._allowBlackPremoves && this.val$move.getPiece().isWhite())) {
            BoardView.this.setActivePieceSquare((Square)object);
            BoardView.this.setMoveSquare(BoardView.this._probableSquare);
        }
        if (this.val$animateMove) {
            BoardView.this.animateMove(this.val$move, BoardView.this._boardViewSettings.getMoveTimeInMills(), null);
        }
        if (this.val$move != null && this.val$sound) {
            BoardView.this.playMoveSound(this.val$move);
        }
    }
}
