/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model.position;

import de.cisha.chess.model.FEN;
import de.cisha.chess.model.PieceInformation;
import de.cisha.chess.model.position.AbstractPosition;
import de.cisha.chess.model.position.MutablePosition;
import java.util.List;

public final class Position
extends AbstractPosition {
    public Position() {
    }

    public Position(FEN fEN) {
        super(fEN);
    }

    public Position(AbstractPosition abstractPosition) {
        super(abstractPosition);
    }

    public Position(List<PieceInformation> list) {
        super(list);
    }

    @Override
    public MutablePosition getMutablePosition() {
        return new MutablePosition(this);
    }

    @Override
    public Position getSafePosition() {
        return this;
    }
}
