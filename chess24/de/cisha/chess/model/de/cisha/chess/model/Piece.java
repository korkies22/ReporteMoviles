/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model;

import de.cisha.chess.model.MoveDirection;
import de.cisha.chess.model.SEP;
import de.cisha.chess.model.Square;
import de.cisha.chess.model.position.AbstractPosition;
import de.cisha.chess.model.position.MutablePosition;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public abstract class Piece
implements Cloneable,
Serializable {
    public static final boolean COLOR_BLACK = false;
    public static final boolean COLOR_WHITE = true;
    private static final long serialVersionUID = -2848326736741788757L;
    private boolean _color;

    protected Piece(boolean bl) {
        this._color = bl;
    }

    public boolean canMove(Square object, Square square, AbstractPosition abstractPosition) {
        object = this.getAllMoves(abstractPosition, (Square)object).iterator();
        while (object.hasNext()) {
            if (!((Square)object.next()).equals(square)) continue;
            return true;
        }
        return false;
    }

    public boolean canTakeInDirectionInSteps(Square arrmoveDirection, MoveDirection moveDirection, int n) {
        if (n <= this.getTakeNumberOfSquares((Square)arrmoveDirection)) {
            arrmoveDirection = this.getTakeDirections((Square)arrmoveDirection);
            for (n = 0; n < arrmoveDirection.length; ++n) {
                if (!arrmoveDirection[n].equals(moveDirection)) continue;
                return true;
            }
        }
        return false;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        return false;
    }

    public boolean equalsIgnoreColor(Piece piece) {
        if (this.getClass().equals(piece.getClass())) {
            return true;
        }
        return false;
    }

    public List<Square> getAllMoves(AbstractPosition abstractPosition, Square square) {
        Serializable serializable;
        Serializable serializable2;
        int n;
        Serializable serializable3;
        LinkedList<Square> linkedList = new LinkedList<Square>(this.getAllSpecialMoves(abstractPosition, square));
        MoveDirection[] arrmoveDirection = this.getMoveDirections(square);
        int n2 = 0;
        int n3 = 0;
        do {
            if (n3 >= arrmoveDirection.length) break;
            serializable3 = arrmoveDirection[n3];
            for (n = 1; n <= this.getMoveNumberOfSquares(square) && (serializable2 = Square.instanceForRowAndColumn(square.getRow() + serializable3.getRowDifference() * n, square.getColumn() + serializable3.getColDifference() * n)).isValid() && abstractPosition.getPieceFor((Square)serializable2) == null; ++n) {
                serializable = new MutablePosition(abstractPosition);
                serializable.doMove(new SEP(square, (Square)serializable2, 3));
                if (serializable.isCheck(this.getColor())) continue;
                linkedList.add((Square)serializable2);
            }
            ++n3;
        } while (true);
        arrmoveDirection = this.getTakeDirections(square);
        block2 : for (n3 = n2; n3 < arrmoveDirection.length; ++n3) {
            serializable2 = arrmoveDirection[n3];
            for (n = 1; n <= this.getTakeNumberOfSquares(square) && (serializable3 = Square.instanceForRowAndColumn(square.getRow() + serializable2.getRowDifference() * n, square.getColumn() + serializable2.getColDifference() * n)).isValid(); ++n) {
                serializable = abstractPosition.getPieceFor((Square)serializable3);
                if (serializable == null) continue;
                if (serializable.getColor() == this.getColor()) continue block2;
                serializable2 = new MutablePosition(abstractPosition);
                serializable2.doMove(new SEP(square, (Square)serializable3, 3));
                if (serializable2.isCheck(this.getColor())) continue block2;
                linkedList.add((Square)serializable3);
                continue block2;
            }
        }
        return linkedList;
    }

    protected List<Square> getAllSpecialMoves(AbstractPosition abstractPosition, Square square) {
        return new LinkedList<Square>();
    }

    public boolean getColor() {
        return this._color;
    }

    public abstract char getFENChar();

    public abstract MoveDirection[] getMoveDirections(Square var1);

    public abstract int getMoveNumberOfSquares(Square var1);

    public abstract String getPieceFigurineNotation();

    public abstract String getPieceNotation();

    public abstract String getPieceUnicodeChar();

    public abstract MoveDirection[] getTakeDirections(Square var1);

    public abstract int getTakeNumberOfSquares(Square var1);

    public boolean hasLegalMove(AbstractPosition abstractPosition, Square square) {
        Serializable serializable;
        int n;
        Serializable serializable2;
        Serializable serializable3;
        int n2;
        MoveDirection[] arrmoveDirection = this.getMoveDirections(square);
        int n3 = 0;
        for (n2 = 0; n2 < arrmoveDirection.length; ++n2) {
            serializable = arrmoveDirection[n2];
            for (n = 1; n <= this.getMoveNumberOfSquares(square) && (serializable3 = Square.instanceForRowAndColumn(square.getRow() + serializable.getRowDifference() * n, square.getColumn() + serializable.getColDifference() * n)).isValid() && abstractPosition.getPieceFor((Square)serializable3) == null; ++n) {
                serializable2 = new MutablePosition(abstractPosition);
                serializable2.doMove(new SEP(square, (Square)serializable3, 3));
                if (serializable2.isCheck(this.getColor())) continue;
                return true;
            }
        }
        arrmoveDirection = this.getTakeDirections(square);
        block2 : for (n2 = n3; n2 < arrmoveDirection.length; ++n2) {
            serializable3 = arrmoveDirection[n2];
            for (n = 1; n <= this.getTakeNumberOfSquares(square) && (serializable = Square.instanceForRowAndColumn(square.getRow() + serializable3.getRowDifference() * n, square.getColumn() + serializable3.getColDifference() * n)).isValid(); ++n) {
                serializable2 = abstractPosition.getPieceFor((Square)serializable);
                if (serializable2 == null) continue;
                if (serializable2.getColor() == this.getColor()) continue block2;
                serializable3 = new MutablePosition(abstractPosition);
                serializable3.doMove(new SEP(square, (Square)serializable, 3));
                if (serializable3.isCheck(this.getColor())) continue block2;
                return true;
            }
        }
        return this.hasLegalSpecialMove(abstractPosition, square);
    }

    protected boolean hasLegalSpecialMove(AbstractPosition abstractPosition, Square square) {
        return false;
    }

    public int hashCode() {
        int n = this.getClass().hashCode();
        int n2 = this._color ? 0 : 64;
        return n + n2;
    }

    public boolean isBlack() {
        return this.isWhite() ^ true;
    }

    public boolean isWhite() {
        return this._color;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(this.getFENChar());
        return stringBuilder.toString();
    }
}
