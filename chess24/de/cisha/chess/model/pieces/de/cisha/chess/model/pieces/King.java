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

public class King
extends Piece
implements Serializable {
    public static final King BLACK;
    private static final MoveDirection[] MOVE_DIRECTIONS;
    public static final King WHITE;
    private static final long serialVersionUID = 1152520135307328175L;

    static {
        MOVE_DIRECTIONS = new MoveDirection[]{MoveDirection.DOWN, MoveDirection.RIGHT, MoveDirection.UP, MoveDirection.LEFT, MoveDirection.DOWN_LEFT, MoveDirection.DOWN_RIGHT, MoveDirection.UP_LEFT, MoveDirection.UP_RIGHT};
        WHITE = new King(true);
        BLACK = new King(false);
    }

    private King(boolean bl) {
        super(bl);
    }

    public static King instanceForColor(boolean bl) {
        if (bl) {
            return WHITE;
        }
        return BLACK;
    }

    @Override
    protected List<Square> getAllSpecialMoves(AbstractPosition abstractPosition, Square square) {
        Square square2;
        Square square3;
        int n;
        Serializable serializable;
        LinkedList<Square> linkedList = new LinkedList<Square>();
        boolean bl = abstractPosition.isCastlingPossible(this.getColor(), true);
        int n2 = 8;
        if (bl) {
            n = this.getColor() ? 1 : 8;
            serializable = Square.instanceForFileAndRank('e', n);
            if (square.equals(serializable)) {
                square2 = Square.instanceForFileAndRank('f', n);
                square3 = Square.instanceForFileAndRank('g', n);
                if (abstractPosition.isEmpty(square2) && abstractPosition.isEmpty(square3) && !abstractPosition.isAttacked((Square)serializable, this.getColor() ^ true) && !abstractPosition.isAttacked(square2, this.getColor() ^ true) && !abstractPosition.isAttacked(square3, this.getColor() ^ true)) {
                    serializable = abstractPosition.getMutablePosition();
                    serializable.doMove(new SEP(square, square3, 0));
                    if (!serializable.isCheck(this.getColor())) {
                        linkedList.add(square3);
                    }
                }
            }
        }
        if (abstractPosition.isCastlingPossible(this.getColor(), false)) {
            n = n2;
            if (this.getColor()) {
                n = 1;
            }
            if (square.equals(serializable = Square.instanceForFileAndRank('e', n))) {
                square2 = Square.instanceForFileAndRank('d', n);
                square3 = Square.instanceForFileAndRank('c', n);
                Square square4 = Square.instanceForFileAndRank('b', n);
                if (abstractPosition.isEmpty(square2) && abstractPosition.isEmpty(square3) && abstractPosition.isEmpty(square4) && !abstractPosition.isAttacked((Square)serializable, this.getColor() ^ true) && !abstractPosition.isAttacked(square2, this.getColor() ^ true) && !abstractPosition.isAttacked(square3, this.getColor() ^ true)) {
                    abstractPosition = abstractPosition.getMutablePosition();
                    abstractPosition.doMove(new SEP(square, square3, 0));
                    if (!abstractPosition.isCheck(this.getColor())) {
                        linkedList.add(square3);
                    }
                }
            }
        }
        return linkedList;
    }

    @Override
    public char getFENChar() {
        if (this.getColor()) {
            return 'K';
        }
        return 'k';
    }

    @Override
    public MoveDirection[] getMoveDirections(Square square) {
        return MOVE_DIRECTIONS;
    }

    @Override
    public int getMoveNumberOfSquares(Square square) {
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
    public MoveDirection[] getTakeDirections(Square square) {
        return MOVE_DIRECTIONS;
    }

    @Override
    public int getTakeNumberOfSquares(Square square) {
        return 1;
    }

    protected boolean hasLegalSpecialMove(Position position, Square square) {
        return false;
    }
}
