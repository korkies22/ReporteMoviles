/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.view;

import de.cisha.chess.model.Square;

public interface BoardMarkingDisplay {
    public void addArrowSquare(Square var1, Square var2, int var3);

    public void markSquare(Square var1, int var2);

    public void removeAllArrows();

    public void removeArrow(Square var1, Square var2);

    public void removeArrowSquare(Square var1, Square var2, int var3);

    public void reset();

    public void setUpdateArrows(boolean var1);

    public void unmarkField();

    public void unmarkSquare(Square var1);
}
