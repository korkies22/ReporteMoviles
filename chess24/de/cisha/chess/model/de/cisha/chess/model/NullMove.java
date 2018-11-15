/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model;

import de.cisha.chess.model.AbstractMoveContainer;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.Piece;
import de.cisha.chess.model.Square;
import de.cisha.chess.model.pieces.special.NullPiece;
import de.cisha.chess.model.position.AbstractPosition;
import de.cisha.chess.model.position.MutablePosition;
import de.cisha.chess.model.position.Position;

public class NullMove
extends Move {
    public static final String EAN = "a1a1";
    public static final String SAN = "--";
    private static final long serialVersionUID = -7250395501875819452L;

    protected NullMove(NullMove nullMove) {
        super(nullMove);
    }

    public NullMove(AbstractPosition abstractPosition) {
        super(Square.SQUARE_A1, Square.SQUARE_A1, false, false, false, false, false, null, NullPiece.instanceForColor(abstractPosition.getActiveColor()), null, "", null, false);
        abstractPosition = new MutablePosition(abstractPosition);
        abstractPosition.doNullMove();
        this.setResultingPosition(((MutablePosition)abstractPosition).getSafePosition());
    }

    @Override
    public NullMove copy() {
        return new NullMove(this);
    }

    @Override
    public String getEAN() {
        return EAN;
    }

    @Override
    public String getFAN() {
        return SAN;
    }

    @Override
    public String getLAN() {
        return SAN;
    }

    @Override
    public String getSAN() {
        return SAN;
    }

    @Override
    public boolean isNullMove() {
        return true;
    }
}
