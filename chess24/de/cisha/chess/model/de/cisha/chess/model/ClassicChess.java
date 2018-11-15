/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model;

import de.cisha.chess.model.ChessVariant;
import de.cisha.chess.model.MoveDirection;
import de.cisha.chess.model.Piece;
import de.cisha.chess.model.Square;
import de.cisha.chess.model.pieces.Bishop;
import de.cisha.chess.model.pieces.King;
import de.cisha.chess.model.pieces.Knight;
import de.cisha.chess.model.pieces.Pawn;
import de.cisha.chess.model.pieces.Queen;
import de.cisha.chess.model.pieces.Rook;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

class ClassicChess
extends ChessVariant {
    private static final MoveDirection[] _allMoveDirections = new MoveDirection[]{MoveDirection.DOWN, MoveDirection.RIGHT, MoveDirection.UP, MoveDirection.LEFT, MoveDirection.DOWN_LEFT, MoveDirection.DOWN_RIGHT, MoveDirection.UP_LEFT, MoveDirection.UP_RIGHT, MoveDirection.DOWN_KNIGHT_LEFT, MoveDirection.DOWN_KNIGHT_RIGHT, MoveDirection.RIGHT_KNIGHT_DOWN, MoveDirection.RIGHT_KNIGHT_UP, MoveDirection.UP_KNIGHT_LEFT, MoveDirection.UP_KNIGHT_RIGHT, MoveDirection.LEFT_KNIGHT_DOWN, MoveDirection.LEFT_KNIGHT_UP};
    private static final Piece[] _allPieces = new Piece[]{Pawn.WHITE, Bishop.WHITE, Knight.WHITE, Rook.WHITE, Queen.WHITE, King.WHITE, Pawn.BLACK, Bishop.BLACK, Knight.BLACK, Rook.BLACK, Queen.BLACK, King.BLACK};
    private static Map<Piece, Integer> _mapPieceNumber = new HashMap<Piece, Integer>(12);

    static {
        _mapPieceNumber.put(Pawn.WHITE, 8);
        _mapPieceNumber.put(Pawn.BLACK, 8);
        _mapPieceNumber.put(Bishop.WHITE, 2);
        _mapPieceNumber.put(Bishop.BLACK, 2);
        _mapPieceNumber.put(Rook.WHITE, 2);
        _mapPieceNumber.put(Rook.BLACK, 2);
        _mapPieceNumber.put(Knight.WHITE, 2);
        _mapPieceNumber.put(Knight.BLACK, 2);
        _mapPieceNumber.put(Queen.WHITE, 1);
        _mapPieceNumber.put(Queen.BLACK, 1);
        _mapPieceNumber.put(King.WHITE, 1);
        _mapPieceNumber.put(King.BLACK, 1);
    }

    ClassicChess() {
    }

    @Override
    public MoveDirection[] getAllMoveDirections() {
        return _allMoveDirections;
    }

    @Override
    public Piece[] getAllPieceTypes() {
        return _allPieces;
    }

    @Override
    public Square[] getAllSquares() {
        return Square.getAllSquares64();
    }

    @Override
    public MoveDirection[] getAllTakeDirections() {
        return _allMoveDirections;
    }

    @Override
    public int getMaxMovesForMoveDirection(MoveDirection moveDirection) {
        if (Math.abs(moveDirection.getRowDifference()) + Math.abs(moveDirection.getColDifference()) == 3) {
            return 1;
        }
        return 7;
    }

    @Override
    public int getMaxMovesForTakeDirection(MoveDirection moveDirection) {
        if (Math.abs(moveDirection.getRowDifference()) + Math.abs(moveDirection.getColDifference()) == 3) {
            return 1;
        }
        return 7;
    }

    @Override
    public int getNumberForPieceType(Piece serializable) {
        if ((serializable = _mapPieceNumber.get(serializable)) != null) {
            return serializable.intValue();
        }
        return 0;
    }
}
