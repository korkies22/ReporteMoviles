// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model;

import java.util.Iterator;
import de.cisha.chess.model.pieces.Pawn;
import de.cisha.chess.model.pieces.Rook;
import de.cisha.chess.model.pieces.Queen;
import de.cisha.chess.model.position.Position;

public class GameEndChecker
{
    public GameEndChecker(final Game game) {
    }
    
    private boolean didRepeatMoves(Move move) {
        Position position = move.getResultingPosition();
        final String fenStringWithoutMoves = this.getFenStringWithoutMoves(position.getFEN());
        int n = 0;
        while (move != null) {
            final FEN fen = position.getFEN();
            if (!position.isEnPassantReallyPossible()) {
                fen.removeEnPassantString();
            }
            int n2 = n;
            if (this.getFenStringWithoutMoves(fen).equals(fenStringWithoutMoves)) {
                n2 = n + 1;
            }
            if (n2 >= 3) {
                return true;
            }
            if (position.getActionCounter() == 0) {
                return false;
            }
            final MoveContainer parent = move.getParent();
            n = n2;
            if ((move = (Move)parent) == null) {
                continue;
            }
            position = parent.getResultingPosition();
            n = n2;
            move = (Move)parent;
        }
        return false;
    }
    
    private String getFenStringWithoutMoves(final FEN fen) {
        final StringBuilder sb = new StringBuilder();
        sb.append(fen.getPiecePlacmentString());
        sb.append(fen.getCastlingString());
        sb.append(fen.getActiveColorChar());
        sb.append(fen.getEnPassantString());
        return sb.toString();
    }
    
    private boolean hasInsufficientMaterial(final Position position) {
        final Iterator<PieceInformation> iterator = position.getAllPieces().iterator();
        int n2;
        int n = n2 = 0;
        while (iterator.hasNext()) {
            final Piece piece = iterator.next().getPiece();
            if (piece.equalsIgnoreColor(Queen.WHITE) || piece.equalsIgnoreColor(Rook.WHITE)) {
                return false;
            }
            if (piece.equalsIgnoreColor(Pawn.WHITE)) {
                return false;
            }
            if (piece.isWhite()) {
                ++n;
            }
            else {
                ++n2;
            }
        }
        if (n >= 3) {
            return false;
        }
        if (n2 >= 3) {
            return false;
        }
        final Iterator<Move> iterator2 = position.getAllMoves().iterator();
        while (iterator2.hasNext()) {
            if (iterator2.next().isCheckMate()) {
                return false;
            }
        }
        return true;
    }
    
    public GameStatus checkForAutomaticGameEnd(final Move move) {
        GameStatus game_RUNNING;
        final GameStatus gameStatus = game_RUNNING = GameStatus.GAME_RUNNING;
        if (move != null) {
            final Position resultingPosition = move.getResultingPosition();
            game_RUNNING = gameStatus;
            if (resultingPosition != null) {
                if (resultingPosition.isCheckMate()) {
                    GameResult gameResult = GameResult.WHITE_WINS;
                    if (resultingPosition.getActiveColor()) {
                        gameResult = GameResult.BLACK_WINS;
                    }
                    return new GameStatus(gameResult, GameEndReason.MATE);
                }
                if (resultingPosition.isStaleMate()) {
                    return new GameStatus(GameResult.DRAW, GameEndReason.DRAW_BY_STALEMATE);
                }
                if (this.hasInsufficientMaterial(resultingPosition)) {
                    return new GameStatus(GameResult.DRAW, GameEndReason.DRAW_BY_INSUFFICENT_MATERIAL);
                }
                if (this.didRepeatMoves(move)) {
                    return new GameStatus(GameResult.DRAW, GameEndReason.DRAW_BY_THREE_REPETITIONS);
                }
                game_RUNNING = gameStatus;
                if (resultingPosition.getActionCounter() >= 100) {
                    game_RUNNING = new GameStatus(GameResult.DRAW, GameEndReason.DRAW_BY_FIFTY_MOVE_RULE);
                }
            }
        }
        return game_RUNNING;
    }
}
