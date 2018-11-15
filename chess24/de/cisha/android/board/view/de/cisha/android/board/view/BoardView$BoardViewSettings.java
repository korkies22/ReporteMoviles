/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.view;

import de.cisha.android.board.view.BoardView;
import de.cisha.chess.model.Piece;
import de.cisha.chess.model.Square;

public static interface BoardView.BoardViewSettings {
    public int getDrawableIdForPiece(Piece var1);

    public int getDrawableIdForSquare(Square var1);

    public int getMoveTimeInMills();

    public boolean isArrowOfLastMoveEnabled();

    public boolean isAutoQueenEnabled();

    public boolean isMarkMoveSquaresModeEnabled();

    public boolean isPlayingMoveSounds();

    public boolean isPremoveEnabled();

    public void setAutoQueenEnabled(boolean var1);

    public void setMoveTimeInMills(int var1);

    public void setPremoveEnabled(boolean var1);
}
