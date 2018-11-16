// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model.pieces;

import de.cisha.chess.model.position.Position;
import de.cisha.chess.model.position.MutablePosition;
import de.cisha.chess.model.MoveDirection;
import de.cisha.chess.model.SEP;
import java.util.LinkedList;
import java.util.List;
import de.cisha.chess.model.Square;
import de.cisha.chess.model.position.AbstractPosition;
import java.io.Serializable;
import de.cisha.chess.model.Piece;

public class Pawn extends Piece implements Serializable
{
    public static final Pawn BLACK;
    public static final Pawn WHITE;
    private static final long serialVersionUID = 7848180467848912095L;
    
    static {
        WHITE = new Pawn(true);
        BLACK = new Pawn(false);
    }
    
    private Pawn(final boolean b) {
        super(b);
    }
    
    public static Pawn instanceForColor(final boolean b) {
        if (b) {
            return Pawn.WHITE;
        }
        return Pawn.BLACK;
    }
    
    @Override
    protected List<Square> getAllSpecialMoves(final AbstractPosition abstractPosition, final Square square) {
        final LinkedList<Square> list = new LinkedList<Square>();
        if (abstractPosition.isEnPassantPossible()) {
            final MoveDirection[] takeDirections = this.getTakeDirections(square);
            for (int i = 0; i < takeDirections.length; ++i) {
                final MoveDirection moveDirection = takeDirections[i];
                final Square instanceForRowAndColumn = Square.instanceForRowAndColumn(square.getRow() + moveDirection.getRowDifference(), square.getColumn() + moveDirection.getColDifference());
                if (instanceForRowAndColumn.equals(abstractPosition.getEnPassantSquare())) {
                    final MutablePosition mutablePosition = abstractPosition.getMutablePosition();
                    mutablePosition.doMove(new SEP(square, instanceForRowAndColumn, 0));
                    if (!mutablePosition.isCheck(this.getColor())) {
                        list.add(instanceForRowAndColumn);
                    }
                }
            }
        }
        return list;
    }
    
    @Override
    public char getFENChar() {
        if (this.getColor()) {
            return 'P';
        }
        return 'p';
    }
    
    @Override
    public MoveDirection[] getMoveDirections(final Square square) {
        MoveDirection moveDirection;
        if (this.getColor()) {
            moveDirection = MoveDirection.UP;
        }
        else {
            moveDirection = MoveDirection.DOWN;
        }
        return new MoveDirection[] { moveDirection };
    }
    
    @Override
    public int getMoveNumberOfSquares(final Square square) {
        if (square != null) {
            final int rank = square.getRank();
            int n;
            if (this.getColor()) {
                n = 2;
            }
            else {
                n = 7;
            }
            if (rank == n) {
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
    public MoveDirection[] getTakeDirections(final Square square) {
        final MoveDirection[] array = new MoveDirection[2];
        if (this.getColor()) {
            array[0] = MoveDirection.UP_LEFT;
            array[1] = MoveDirection.UP_RIGHT;
            return array;
        }
        array[0] = MoveDirection.DOWN_LEFT;
        array[1] = MoveDirection.DOWN_RIGHT;
        return array;
    }
    
    @Override
    public int getTakeNumberOfSquares(final Square square) {
        return 1;
    }
    
    protected boolean hasLegalSpecialMove(final Position position, final Square square) {
        if (position.isEnPassantPossible()) {
            final MoveDirection[] takeDirections = this.getTakeDirections(square);
            for (int i = 0; i < takeDirections.length; ++i) {
                final MoveDirection moveDirection = takeDirections[i];
                final Square instanceForRowAndColumn = Square.instanceForRowAndColumn(square.getRow() + moveDirection.getRowDifference(), square.getColumn() + moveDirection.getColDifference());
                if (instanceForRowAndColumn.equals(position.getEnPassantSquare())) {
                    final MutablePosition mutablePosition = position.getMutablePosition();
                    mutablePosition.doMove(new SEP(square, instanceForRowAndColumn, 0));
                    if (!mutablePosition.isCheck(this.getColor())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
