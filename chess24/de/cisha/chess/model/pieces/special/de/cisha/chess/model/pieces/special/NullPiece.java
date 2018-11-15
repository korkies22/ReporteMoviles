/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model.pieces.special;

import de.cisha.chess.model.MoveDirection;
import de.cisha.chess.model.Piece;
import de.cisha.chess.model.Square;

public class NullPiece
extends Piece {
    public static final NullPiece BLACK;
    public static final NullPiece WHITE;

    static {
        WHITE = new NullPiece(true);
        BLACK = new NullPiece(false);
    }

    protected NullPiece(boolean bl) {
        super(bl);
    }

    public static NullPiece instanceForColor(boolean bl) {
        if (bl) {
            return WHITE;
        }
        return BLACK;
    }

    @Override
    public char getFENChar() {
        return 'z';
    }

    @Override
    public MoveDirection[] getMoveDirections(Square square) {
        return new MoveDirection[0];
    }

    @Override
    public int getMoveNumberOfSquares(Square square) {
        return 0;
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
        return "";
    }

    @Override
    public MoveDirection[] getTakeDirections(Square square) {
        return new MoveDirection[0];
    }

    @Override
    public int getTakeNumberOfSquares(Square square) {
        return 0;
    }
}
