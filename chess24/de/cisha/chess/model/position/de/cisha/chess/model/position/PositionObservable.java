/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model.position;

import de.cisha.chess.model.FEN;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.model.position.PositionObserver;

public interface PositionObservable {
    public void addPositionObserver(PositionObserver var1);

    public FEN getFEN();

    public Position getPosition();

    public void removePositionObserver(PositionObserver var1);
}
