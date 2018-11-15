/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model.pieces;

import de.cisha.chess.model.MoveDirection;
import de.cisha.chess.model.Piece;
import de.cisha.chess.model.Square;
import java.io.Serializable;

public class Rook
extends Piece
implements Serializable {
    public static final Rook BLACK;
    private static final MoveDirection[] MOVE_DIRECTIONS;
    public static final Rook WHITE;
    private static final long serialVersionUID = -5216263788075767043L;

    static {
        MOVE_DIRECTIONS = new MoveDirection[]{MoveDirection.DOWN, MoveDirection.RIGHT, MoveDirection.UP, MoveDirection.LEFT};
        WHITE = new Rook(true);
        BLACK = new Rook(false);
    }

    private Rook(boolean bl) {
        super(bl);
    }

    public static Rook instanceForColor(boolean bl) {
        if (bl) {
            return WHITE;
        }
        return BLACK;
    }

    @Override
    public char getFENChar() {
        if (this.getColor()) {
            return 'R';
        }
        return 'r';
    }

    @Override
    public MoveDirection[] getMoveDirections(Square square) {
        return MOVE_DIRECTIONS;
    }

    @Override
    public int getMoveNumberOfSquares(Square square) {
        return 7;
    }

    @Override
    public String getPieceFigurineNotation() {
        return this.getPieceUnicodeChar();
    }

    @Override
    public String getPieceNotation() {
        return "R";
    }

    @Override
    public String getPieceUnicodeChar() {
        if (this.getColor()) {
            return "\u2656";
        }
        return "\u265c";
    }

    @Override
    public MoveDirection[] getTakeDirections(Square square) {
        return MOVE_DIRECTIONS;
    }

    @Override
    public int getTakeNumberOfSquares(Square square) {
        return 7;
    }
}
