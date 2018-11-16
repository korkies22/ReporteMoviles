// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model.pieces;

import de.cisha.chess.model.position.Position;
import de.cisha.chess.model.position.MutablePosition;
import de.cisha.chess.model.SEP;
import java.util.LinkedList;
import java.util.List;
import de.cisha.chess.model.Square;
import de.cisha.chess.model.position.AbstractPosition;
import de.cisha.chess.model.MoveDirection;
import java.io.Serializable;
import de.cisha.chess.model.Piece;

public class King extends Piece implements Serializable
{
    public static final King BLACK;
    private static final MoveDirection[] MOVE_DIRECTIONS;
    public static final King WHITE;
    private static final long serialVersionUID = 1152520135307328175L;
    
    static {
        MOVE_DIRECTIONS = new MoveDirection[] { MoveDirection.DOWN, MoveDirection.RIGHT, MoveDirection.UP, MoveDirection.LEFT, MoveDirection.DOWN_LEFT, MoveDirection.DOWN_RIGHT, MoveDirection.UP_LEFT, MoveDirection.UP_RIGHT };
        WHITE = new King(true);
        BLACK = new King(false);
    }
    
    private King(final boolean b) {
        super(b);
    }
    
    public static King instanceForColor(final boolean b) {
        if (b) {
            return King.WHITE;
        }
        return King.BLACK;
    }
    
    @Override
    protected List<Square> getAllSpecialMoves(final AbstractPosition abstractPosition, final Square square) {
        final LinkedList<Square> list = new LinkedList<Square>();
        final boolean castlingPossible = abstractPosition.isCastlingPossible(this.getColor(), true);
        final int n = 8;
        if (castlingPossible) {
            int n2;
            if (this.getColor()) {
                n2 = 1;
            }
            else {
                n2 = 8;
            }
            final Square instanceForFileAndRank = Square.instanceForFileAndRank('e', n2);
            if (square.equals(instanceForFileAndRank)) {
                final Square instanceForFileAndRank2 = Square.instanceForFileAndRank('f', n2);
                final Square instanceForFileAndRank3 = Square.instanceForFileAndRank('g', n2);
                if (abstractPosition.isEmpty(instanceForFileAndRank2) && abstractPosition.isEmpty(instanceForFileAndRank3) && !abstractPosition.isAttacked(instanceForFileAndRank, this.getColor() ^ true) && !abstractPosition.isAttacked(instanceForFileAndRank2, this.getColor() ^ true) && !abstractPosition.isAttacked(instanceForFileAndRank3, this.getColor() ^ true)) {
                    final MutablePosition mutablePosition = abstractPosition.getMutablePosition();
                    mutablePosition.doMove(new SEP(square, instanceForFileAndRank3, 0));
                    if (!mutablePosition.isCheck(this.getColor())) {
                        list.add(instanceForFileAndRank3);
                    }
                }
            }
        }
        if (abstractPosition.isCastlingPossible(this.getColor(), false)) {
            int n3 = n;
            if (this.getColor()) {
                n3 = 1;
            }
            final Square instanceForFileAndRank4 = Square.instanceForFileAndRank('e', n3);
            if (square.equals(instanceForFileAndRank4)) {
                final Square instanceForFileAndRank5 = Square.instanceForFileAndRank('d', n3);
                final Square instanceForFileAndRank6 = Square.instanceForFileAndRank('c', n3);
                final Square instanceForFileAndRank7 = Square.instanceForFileAndRank('b', n3);
                if (abstractPosition.isEmpty(instanceForFileAndRank5) && abstractPosition.isEmpty(instanceForFileAndRank6) && abstractPosition.isEmpty(instanceForFileAndRank7) && !abstractPosition.isAttacked(instanceForFileAndRank4, this.getColor() ^ true) && !abstractPosition.isAttacked(instanceForFileAndRank5, this.getColor() ^ true) && !abstractPosition.isAttacked(instanceForFileAndRank6, this.getColor() ^ true)) {
                    final MutablePosition mutablePosition2 = abstractPosition.getMutablePosition();
                    mutablePosition2.doMove(new SEP(square, instanceForFileAndRank6, 0));
                    if (!mutablePosition2.isCheck(this.getColor())) {
                        list.add(instanceForFileAndRank6);
                    }
                }
            }
        }
        return list;
    }
    
    @Override
    public char getFENChar() {
        if (this.getColor()) {
            return 'K';
        }
        return 'k';
    }
    
    @Override
    public MoveDirection[] getMoveDirections(final Square square) {
        return King.MOVE_DIRECTIONS;
    }
    
    @Override
    public int getMoveNumberOfSquares(final Square square) {
        return 1;
    }
    
    @Override
    public String getPieceFigurineNotation() {
        return this.getPieceUnicodeChar();
    }
    
    @Override
    public String getPieceNotation() {
        return "K";
    }
    
    @Override
    public String getPieceUnicodeChar() {
        if (this.getColor()) {
            return "\u2654";
        }
        return "\u265a";
    }
    
    @Override
    public MoveDirection[] getTakeDirections(final Square square) {
        return King.MOVE_DIRECTIONS;
    }
    
    @Override
    public int getTakeNumberOfSquares(final Square square) {
        return 1;
    }
    
    protected boolean hasLegalSpecialMove(final Position position, final Square square) {
        return false;
    }
}
