/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model.position;

import de.cisha.chess.model.Move;
import de.cisha.chess.model.position.Position;

public interface PositionObserver {
    public void positionChanged(Position var1, Move var2);
}
