/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.view;

import de.cisha.android.board.view.BoardView;
import de.cisha.chess.model.Piece;
import de.cisha.chess.model.Square;

class BoardView
implements BoardView.BoardViewSettings {
    private boolean _autoQueen = false;
    private int _moveTime = 300;
    private boolean _premoveEnabled = false;

    BoardView() {
    }

    @Override
    public int getDrawableIdForPiece(Piece piece) {
        return 2131231785;
    }

    @Override
    public int getDrawableIdForSquare(Square square) {
        return 2131231936;
    }

    @Override
    public int getMoveTimeInMills() {
        return this._moveTime;
    }

    @Override
    public boolean isArrowOfLastMoveEnabled() {
        return true;
    }

    @Override
    public boolean isAutoQueenEnabled() {
        return this._autoQueen;
    }

    @Override
    public boolean isMarkMoveSquaresModeEnabled() {
        return true;
    }

    @Override
    public boolean isPlayingMoveSounds() {
        return false;
    }

    @Override
    public boolean isPremoveEnabled() {
        return this._premoveEnabled;
    }

    @Override
    public void setAutoQueenEnabled(boolean bl) {
        this._autoQueen = bl;
    }

    @Override
    public void setMoveTimeInMills(int n) {
        this._moveTime = n;
    }

    @Override
    public void setPremoveEnabled(boolean bl) {
        this._premoveEnabled = bl;
    }
}
