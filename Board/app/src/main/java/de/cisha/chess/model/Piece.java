// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model;

import de.cisha.chess.model.position.MutablePosition;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;
import de.cisha.chess.model.position.AbstractPosition;
import java.io.Serializable;

public abstract class Piece implements Cloneable, Serializable
{
    public static final boolean COLOR_BLACK = false;
    public static final boolean COLOR_WHITE = true;
    private static final long serialVersionUID = -2848326736741788757L;
    private boolean _color;
    
    protected Piece(final boolean color) {
        this._color = color;
    }
    
    public boolean canMove(final Square square, final Square square2, final AbstractPosition abstractPosition) {
        final Iterator<Square> iterator = this.getAllMoves(abstractPosition, square).iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(square2)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean canTakeInDirectionInSteps(final Square square, final MoveDirection moveDirection, int i) {
        if (i <= this.getTakeNumberOfSquares(square)) {
            MoveDirection[] takeDirections;
            for (takeDirections = this.getTakeDirections(square), i = 0; i < takeDirections.length; ++i) {
                if (takeDirections[i].equals(moveDirection)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o == this;
    }
    
    public boolean equalsIgnoreColor(final Piece piece) {
        return this.getClass().equals(piece.getClass());
    }
    
    public List<Square> getAllMoves(final AbstractPosition abstractPosition, final Square square) {
        final LinkedList<Square> list = new LinkedList<Square>(this.getAllSpecialMoves(abstractPosition, square));
        final MoveDirection[] moveDirections = this.getMoveDirections(square);
        final int n = 0;
        int n2 = 0;
        while (true) {
            int i = 1;
            if (n2 >= moveDirections.length) {
                break;
            }
            final MoveDirection moveDirection = moveDirections[n2];
            while (i <= this.getMoveNumberOfSquares(square)) {
                final Square instanceForRowAndColumn = Square.instanceForRowAndColumn(square.getRow() + moveDirection.getRowDifference() * i, square.getColumn() + moveDirection.getColDifference() * i);
                if (!instanceForRowAndColumn.isValid() || abstractPosition.getPieceFor(instanceForRowAndColumn) != null) {
                    break;
                }
                final MutablePosition mutablePosition = new MutablePosition(abstractPosition);
                mutablePosition.doMove(new SEP(square, instanceForRowAndColumn, 3));
                if (!mutablePosition.isCheck(this.getColor())) {
                    list.add(instanceForRowAndColumn);
                }
                ++i;
            }
            ++n2;
        }
        final MoveDirection[] takeDirections = this.getTakeDirections(square);
        for (int j = n; j < takeDirections.length; ++j) {
            final MoveDirection moveDirection2 = takeDirections[j];
            int k = 1;
            while (k <= this.getTakeNumberOfSquares(square)) {
                final Square instanceForRowAndColumn2 = Square.instanceForRowAndColumn(square.getRow() + moveDirection2.getRowDifference() * k, square.getColumn() + moveDirection2.getColDifference() * k);
                if (!instanceForRowAndColumn2.isValid()) {
                    break;
                }
                final Piece piece = abstractPosition.getPieceFor(instanceForRowAndColumn2);
                if (piece != null) {
                    if (piece.getColor() == this.getColor()) {
                        break;
                    }
                    final MutablePosition mutablePosition2 = new MutablePosition(abstractPosition);
                    mutablePosition2.doMove(new SEP(square, instanceForRowAndColumn2, 3));
                    if (!mutablePosition2.isCheck(this.getColor())) {
                        list.add(instanceForRowAndColumn2);
                        break;
                    }
                    break;
                }
                else {
                    ++k;
                }
            }
        }
        return list;
    }
    
    protected List<Square> getAllSpecialMoves(final AbstractPosition abstractPosition, final Square square) {
        return new LinkedList<Square>();
    }
    
    public boolean getColor() {
        return this._color;
    }
    
    public abstract char getFENChar();
    
    public abstract MoveDirection[] getMoveDirections(final Square p0);
    
    public abstract int getMoveNumberOfSquares(final Square p0);
    
    public abstract String getPieceFigurineNotation();
    
    public abstract String getPieceNotation();
    
    public abstract String getPieceUnicodeChar();
    
    public abstract MoveDirection[] getTakeDirections(final Square p0);
    
    public abstract int getTakeNumberOfSquares(final Square p0);
    
    public boolean hasLegalMove(final AbstractPosition abstractPosition, final Square square) {
        final MoveDirection[] moveDirections = this.getMoveDirections(square);
        final int n = 0;
        for (int i = 0; i < moveDirections.length; ++i) {
            final MoveDirection moveDirection = moveDirections[i];
            for (int j = 1; j <= this.getMoveNumberOfSquares(square); ++j) {
                final Square instanceForRowAndColumn = Square.instanceForRowAndColumn(square.getRow() + moveDirection.getRowDifference() * j, square.getColumn() + moveDirection.getColDifference() * j);
                if (!instanceForRowAndColumn.isValid() || abstractPosition.getPieceFor(instanceForRowAndColumn) != null) {
                    break;
                }
                final MutablePosition mutablePosition = new MutablePosition(abstractPosition);
                mutablePosition.doMove(new SEP(square, instanceForRowAndColumn, 3));
                if (!mutablePosition.isCheck(this.getColor())) {
                    return true;
                }
            }
        }
        final MoveDirection[] takeDirections = this.getTakeDirections(square);
        for (int k = n; k < takeDirections.length; ++k) {
            final MoveDirection moveDirection2 = takeDirections[k];
            int l = 1;
            while (l <= this.getTakeNumberOfSquares(square)) {
                final Square instanceForRowAndColumn2 = Square.instanceForRowAndColumn(square.getRow() + moveDirection2.getRowDifference() * l, square.getColumn() + moveDirection2.getColDifference() * l);
                if (!instanceForRowAndColumn2.isValid()) {
                    break;
                }
                final Piece piece = abstractPosition.getPieceFor(instanceForRowAndColumn2);
                if (piece != null) {
                    if (piece.getColor() == this.getColor()) {
                        break;
                    }
                    final MutablePosition mutablePosition2 = new MutablePosition(abstractPosition);
                    mutablePosition2.doMove(new SEP(square, instanceForRowAndColumn2, 3));
                    if (!mutablePosition2.isCheck(this.getColor())) {
                        return true;
                    }
                    break;
                }
                else {
                    ++l;
                }
            }
        }
        return this.hasLegalSpecialMove(abstractPosition, square);
    }
    
    protected boolean hasLegalSpecialMove(final AbstractPosition abstractPosition, final Square square) {
        return false;
    }
    
    @Override
    public int hashCode() {
        final int hashCode = this.getClass().hashCode();
        int n;
        if (this._color) {
            n = 0;
        }
        else {
            n = 64;
        }
        return hashCode + n;
    }
    
    public boolean isBlack() {
        return this.isWhite() ^ true;
    }
    
    public boolean isWhite() {
        return this._color;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(this.getFENChar());
        return sb.toString();
    }
}
