/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model;

import de.cisha.chess.model.ClassicChess;
import de.cisha.chess.model.MoveDirection;
import de.cisha.chess.model.Piece;
import de.cisha.chess.model.Square;

public abstract class ChessVariant {
    public static final ChessVariant CLASSIC_CHESS = new ClassicChess();

    public abstract MoveDirection[] getAllMoveDirections();

    public abstract Piece[] getAllPieceTypes();

    public abstract Square[] getAllSquares();

    public abstract MoveDirection[] getAllTakeDirections();

    public abstract int getMaxMovesForMoveDirection(MoveDirection var1);

    public abstract int getMaxMovesForTakeDirection(MoveDirection var1);

    public abstract int getNumberForPieceType(Piece var1);
}
