// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model;

import java.util.HashMap;
import de.cisha.chess.model.pieces.King;
import de.cisha.chess.model.pieces.Queen;
import de.cisha.chess.model.pieces.Rook;
import de.cisha.chess.model.pieces.Knight;
import de.cisha.chess.model.pieces.Bishop;
import de.cisha.chess.model.pieces.Pawn;
import java.util.Map;

class ClassicChess extends ChessVariant
{
    private static final MoveDirection[] _allMoveDirections;
    private static final Piece[] _allPieces;
    private static Map<Piece, Integer> _mapPieceNumber;
    
    static {
        _allMoveDirections = new MoveDirection[] { MoveDirection.DOWN, MoveDirection.RIGHT, MoveDirection.UP, MoveDirection.LEFT, MoveDirection.DOWN_LEFT, MoveDirection.DOWN_RIGHT, MoveDirection.UP_LEFT, MoveDirection.UP_RIGHT, MoveDirection.DOWN_KNIGHT_LEFT, MoveDirection.DOWN_KNIGHT_RIGHT, MoveDirection.RIGHT_KNIGHT_DOWN, MoveDirection.RIGHT_KNIGHT_UP, MoveDirection.UP_KNIGHT_LEFT, MoveDirection.UP_KNIGHT_RIGHT, MoveDirection.LEFT_KNIGHT_DOWN, MoveDirection.LEFT_KNIGHT_UP };
        _allPieces = new Piece[] { Pawn.WHITE, Bishop.WHITE, Knight.WHITE, Rook.WHITE, Queen.WHITE, King.WHITE, Pawn.BLACK, Bishop.BLACK, Knight.BLACK, Rook.BLACK, Queen.BLACK, King.BLACK };
        (ClassicChess._mapPieceNumber = new HashMap<Piece, Integer>(12)).put(Pawn.WHITE, 8);
        ClassicChess._mapPieceNumber.put(Pawn.BLACK, 8);
        ClassicChess._mapPieceNumber.put(Bishop.WHITE, 2);
        ClassicChess._mapPieceNumber.put(Bishop.BLACK, 2);
        ClassicChess._mapPieceNumber.put(Rook.WHITE, 2);
        ClassicChess._mapPieceNumber.put(Rook.BLACK, 2);
        ClassicChess._mapPieceNumber.put(Knight.WHITE, 2);
        ClassicChess._mapPieceNumber.put(Knight.BLACK, 2);
        ClassicChess._mapPieceNumber.put(Queen.WHITE, 1);
        ClassicChess._mapPieceNumber.put(Queen.BLACK, 1);
        ClassicChess._mapPieceNumber.put(King.WHITE, 1);
        ClassicChess._mapPieceNumber.put(King.BLACK, 1);
    }
    
    @Override
    public MoveDirection[] getAllMoveDirections() {
        return ClassicChess._allMoveDirections;
    }
    
    @Override
    public Piece[] getAllPieceTypes() {
        return ClassicChess._allPieces;
    }
    
    @Override
    public Square[] getAllSquares() {
        return Square.getAllSquares64();
    }
    
    @Override
    public MoveDirection[] getAllTakeDirections() {
        return ClassicChess._allMoveDirections;
    }
    
    @Override
    public int getMaxMovesForMoveDirection(final MoveDirection moveDirection) {
        if (Math.abs(moveDirection.getRowDifference()) + Math.abs(moveDirection.getColDifference()) == 3) {
            return 1;
        }
        return 7;
    }
    
    @Override
    public int getMaxMovesForTakeDirection(final MoveDirection moveDirection) {
        if (Math.abs(moveDirection.getRowDifference()) + Math.abs(moveDirection.getColDifference()) == 3) {
            return 1;
        }
        return 7;
    }
    
    @Override
    public int getNumberForPieceType(final Piece piece) {
        final Integer n = ClassicChess._mapPieceNumber.get(piece);
        if (n != null) {
            return n;
        }
        return 0;
    }
}
