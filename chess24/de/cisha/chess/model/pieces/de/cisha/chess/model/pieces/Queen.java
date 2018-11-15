/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model.pieces;

import de.cisha.chess.model.MoveDirection;
import de.cisha.chess.model.Piece;
import de.cisha.chess.model.Square;
import java.io.Serializable;

public class Queen
extends Piece
implements Serializable {
    public static final Queen BLACK;
    private static final MoveDirection[] MOVE_DIRECTIONS;
    public static final Queen WHITE;
    private static final long serialVersionUID = 3250496508108306143L;

    static {
        MOVE_DIRECTIONS = new MoveDirection[]{MoveDirection.DOWN, MoveDirection.RIGHT, MoveDirection.UP, MoveDirection.LEFT, MoveDirection.DOWN_LEFT, MoveDirection.DOWN_RIGHT, MoveDirection.UP_LEFT, MoveDirection.UP_RIGHT};
        WHITE = new Queen(true);
        BLACK = new Queen(false);
    }

    private Queen(boolean bl) {
        super(bl);
    }

    public static Queen instanceForColor(boolean bl) {
        if (bl) {
            return WHITE;
        }
        return BLACK;
    }

    @Override
    public char getFENChar() {
        if (this.getColor()) {
            return 'Q';
        }
        return 'q';
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
        return "Q";
    }

    @Override
    public String getPieceUnicodeChar() {
        if (this.getColor()) {
            return "\u2655";
        }
        return "\u265b";
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
