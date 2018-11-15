/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model;

import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.model.MovesObserver;

public interface MovesObservable {
    public void addMovesObserver(MovesObserver var1);

    public Move getCurrentMove();

    public MoveContainer getRootMoveContainer();

    public void removeMovesObserver(MovesObserver var1);
}
