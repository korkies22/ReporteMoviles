/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model.pieces;

import de.cisha.chess.model.MoveDirection;
import de.cisha.chess.model.Piece;
import de.cisha.chess.model.Square;
import java.io.Serializable;

public class Bishop
extends Piece
implements Serializable {
    public static final Bishop BLACK;
    private static final MoveDirection[] MOVE_DIRECTIONS;
    public static final Bishop WHITE;
    private static final long serialVersionUID = -4160180296067189515L;

    static {
        MOVE_DIRECTIONS = new MoveDirection[]{MoveDirection.DOWN_LEFT, MoveDirection.DOWN_RIGHT, MoveDirection.UP_LEFT, MoveDirection.UP_RIGHT};
        WHITE = new Bishop(true);
        BLACK = new Bishop(false);
    }

    private Bishop(boolean bl) {
        super(bl);
    }

    public static Bishop instanceForColor(boolean bl) {
        if (bl) {
            return WHITE;
        }
        return BLACK;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        return false;
    }

    @Override
    public char getFENChar() {
        if (this.getColor()) {
            return 'B';
        }
        return 'b';
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
        return "B";
    }

    @Override
    public String getPieceUnicodeChar() {
        if (this.getColor()) {
            return "\u2657";
        }
        return "\u265d";
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
