/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model.pieces;

import de.cisha.chess.model.MoveDirection;
import de.cisha.chess.model.Piece;
import de.cisha.chess.model.Square;
import java.io.Serializable;

public class Knight
extends Piece
implements Serializable {
    public static final Knight BLACK;
    private static final MoveDirection[] MOVE_DIRECTIONS;
    public static final Knight WHITE;
    private static final long serialVersionUID = -2118662142567753854L;

    static {
        MOVE_DIRECTIONS = new MoveDirection[]{MoveDirection.DOWN_KNIGHT_LEFT, MoveDirection.DOWN_KNIGHT_RIGHT, MoveDirection.RIGHT_KNIGHT_DOWN, MoveDirection.RIGHT_KNIGHT_UP, MoveDirection.UP_KNIGHT_LEFT, MoveDirection.UP_KNIGHT_RIGHT, MoveDirection.LEFT_KNIGHT_DOWN, MoveDirection.LEFT_KNIGHT_UP};
        WHITE = new Knight(true);
        BLACK = new Knight(false);
    }

    private Knight(boolean bl) {
        super(bl);
    }

    public static Knight instanceForColor(boolean bl) {
        if (bl) {
            return WHITE;
        }
        return BLACK;
    }

    @Override
    public char getFENChar() {
        if (this.getColor()) {
            return 'N';
        }
        return 'n';
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
        return "N";
    }

    @Override
    public String getPieceUnicodeChar() {
        if (this.getColor()) {
            return "\u2658";
        }
        return "\u265e";
    }

    @Override
    public MoveDirection[] getTakeDirections(Square square) {
        return MOVE_DIRECTIONS;
    }

    @Override
    public int getTakeNumberOfSquares(Square square) {
        return 1;
    }
}
