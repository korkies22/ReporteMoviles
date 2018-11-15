/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model;

import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveContainer;

public interface MovesObserver {
    public void allMovesChanged(MoveContainer var1);

    public boolean canSkipMoves();

    public void newMove(Move var1);

    public void selectedMoveChanged(Move var1);
}
