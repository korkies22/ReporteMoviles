/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package de.cisha.android.board.setup.view;

import android.view.View;
import de.cisha.android.board.setup.view.DragBoardView;
import de.cisha.chess.model.Piece;
import de.cisha.chess.model.Square;

public static interface DragBoardView.DragDelegate {
    public void onDragStart();

    public void onPieceDown(Piece var1, Square var2, View var3);

    public void onSquareUp(Square var1, boolean var2);
}
