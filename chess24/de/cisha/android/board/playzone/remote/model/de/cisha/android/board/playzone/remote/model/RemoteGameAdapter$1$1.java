/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.remote.model;

import de.cisha.android.board.playzone.remote.model.RemoteGameAdapter;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.model.MovesObserver;
import de.cisha.chess.model.Piece;

class RemoteGameAdapter
implements MovesObserver {
    RemoteGameAdapter() {
    }

    @Override
    public void allMovesChanged(MoveContainer moveContainer) {
    }

    @Override
    public boolean canSkipMoves() {
        return false;
    }

    @Override
    public void newMove(Move move) {
        if (move.getPiece().isWhite() == 1.this.this$0._playerIsWhite) {
            1.this.this$0._lastSentMove = move;
        }
    }

    @Override
    public void selectedMoveChanged(Move move) {
    }
}
