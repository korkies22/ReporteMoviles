/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model.pieces;

import de.cisha.chess.model.MoveDirection;
import de.cisha.chess.model.Piece;
import de.cisha.chess.model.SEP;
import de.cisha.chess.model.Square;
import de.cisha.chess.model.position.AbstractPosition;
import de.cisha.chess.model.position.MutablePosition;
import de.cisha.chess.model.position.Position;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Pawn
extends Piece
implements Serializable {
    public static final Pawn BLACK;
    public static final Pawn WHITE;
    private static final long serialVersionUID = 7848180467848912095L;

    static {
        WHITE = new Pawn(true);
        BLACK = new Pawn(false);
    }

    private Pawn(boolean bl) {
        super(bl);
    }

    public static Pawn instanceForColor(boolean bl) {
        if (bl) {
            return WHITE;
        }
        return BLACK;
    }

    @Override
    protected List<Square> getAllSpecialMoves(AbstractPosition abstractPosition, Square square) {
        LinkedList<Square> linkedList = new LinkedList<Square>();
        if (abstractPosition.isEnPassantPossible()) {
            MoveDirection[] arrmoveDirection = this.getTakeDirections(square);
            for (int i = 0; i < arrmoveDirection.length; ++i) {
                Serializable serializable = arrmoveDirection[i];
                serializable = Square.instanceForRowAndColumn(square.getRow() + serializable.getRowDifference(), square.getColumn() + serializable.getColDifference());
                if (!serializable.equals(abstractPosition.getEnPassantSquare())) continue;
                MutablePosition mutablePosition = abstractPosition.getMutablePosition();
                mutablePosition.doMove(new SEP(square, (Square)serializable, 0));
                if (mutablePosition.isCheck(this.getColor())) continue;
                linkedList.add((Square)serializable);
            }
        }
        return linkedList;
    }

    @Override
    public char getFENChar() {
        if (this.getColor()) {
            return 'P';
        }
        return 'p';
    }

    @Override
    public MoveDirection[] getMoveDirections(Square serializable) {
        serializable = this.getColor() ? MoveDirection.UP : MoveDirection.DOWN;
        return new MoveDirection[]{serializable};
    }

    @Override
    public int getMoveNumberOfSquares(Square square) {
        if (square != null) {
            int n = square.getRank();
            int n2 = this.getColor() ? 2 : 7;
            if (n == n2) {
                return 2;
            }
        }
        return 1;
    }

    @Override
    public String getPieceFigurineNotation() {
        return "";
    }

    @Override
    public String getPieceNotation() {
        return "";
    }

    @Override
    public String getPieceUnicodeChar() {
        if (this.getColor()) {
            return "\u2659";
        }
        return "\u265f";
    }

    @Override
    public MoveDirection[] getTakeDirections(Square arrmoveDirection) {
        arrmoveDirection = new MoveDirection[2];
        if (this.getColor()) {
            arrmoveDirection[0] = MoveDirection.UP_LEFT;
            arrmoveDirection[1] = MoveDirection.UP_RIGHT;
            return arrmoveDirection;
        }
        arrmoveDirection[0] = MoveDirection.DOWN_LEFT;
        arrmoveDirection[1] = MoveDirection.DOWN_RIGHT;
        return arrmoveDirection;
    }

    @Override
    public int getTakeNumberOfSquares(Square square) {
        return 1;
    }

    protected boolean hasLegalSpecialMove(Position position, Square square) {
        if (position.isEnPassantPossible()) {
            MoveDirection[] arrmoveDirection = this.getTakeDirections(square);
            for (int i = 0; i < arrmoveDirection.length; ++i) {
                Serializable serializable = arrmoveDirection[i];
                serializable = Square.instanceForRowAndColumn(square.getRow() + serializable.getRowDifference(), square.getColumn() + serializable.getColDifference());
                if (!serializable.equals(position.getEnPassantSquare())) continue;
                MutablePosition mutablePosition = position.getMutablePosition();
                mutablePosition.doMove(new SEP(square, (Square)serializable, 0));
                if (mutablePosition.isCheck(this.getColor())) continue;
                return true;
            }
        }
        return false;
    }
}
