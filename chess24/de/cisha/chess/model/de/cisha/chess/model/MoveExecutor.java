/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model;

import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveSelector;
import de.cisha.chess.model.SEP;
import de.cisha.chess.model.Square;

public interface MoveExecutor
extends MoveSelector {
    public boolean advanceOneMoveInCurrentLine();

    public Move doMoveInCurrentPosition(SEP var1);

    public boolean goBackOneMove();

    public void gotoEndingPosition();

    public boolean gotoStartingPosition();

    public void registerPremove(Square var1, Square var2);
}
