/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model;

import de.cisha.chess.model.FEN;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameEndReason;
import de.cisha.chess.model.GameResult;
import de.cisha.chess.model.GameStatus;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.model.Piece;
import de.cisha.chess.model.PieceInformation;
import de.cisha.chess.model.pieces.Pawn;
import de.cisha.chess.model.pieces.Queen;
import de.cisha.chess.model.pieces.Rook;
import de.cisha.chess.model.position.Position;
import java.util.Iterator;
import java.util.List;

public class GameEndChecker {
    public GameEndChecker(Game game) {
    }

    private boolean didRepeatMoves(Move object) {
        Position position = object.getResultingPosition();
        String string = this.getFenStringWithoutMoves(position.getFEN());
        int n = 0;
        while (object != null) {
            Object object2 = position.getFEN();
            if (!position.isEnPassantReallyPossible()) {
                object2.removeEnPassantString();
            }
            int n2 = n;
            if (this.getFenStringWithoutMoves((FEN)object2).equals(string)) {
                n2 = n + 1;
            }
            if (n2 >= 3) {
                return true;
            }
            if (position.getActionCounter() == 0) {
                return false;
            }
            object2 = object.getParent();
            n = n2;
            object = object2;
            if (object2 == null) continue;
            position = object2.getResultingPosition();
            n = n2;
            object = object2;
        }
        return false;
    }

    private String getFenStringWithoutMoves(FEN fEN) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(fEN.getPiecePlacmentString());
        stringBuilder.append(fEN.getCastlingString());
        stringBuilder.append(fEN.getActiveColorChar());
        stringBuilder.append(fEN.getEnPassantString());
        return stringBuilder.toString();
    }

    private boolean hasInsufficientMaterial(Position object) {
        int n;
        Iterator<PieceInformation> iterator = object.getAllPieces().iterator();
        int n2 = n = 0;
        while (iterator.hasNext()) {
            Piece piece = iterator.next().getPiece();
            if (!piece.equalsIgnoreColor(Queen.WHITE) && !piece.equalsIgnoreColor(Rook.WHITE)) {
                if (piece.equalsIgnoreColor(Pawn.WHITE)) {
                    return false;
                }
                if (piece.isWhite()) {
                    ++n;
                    continue;
                }
                ++n2;
                continue;
            }
            return false;
        }
        if (n < 3) {
            if (n2 >= 3) {
                return false;
            }
            object = object.getAllMoves().iterator();
            while (object.hasNext()) {
                if (!((Move)object.next()).isCheckMate()) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    public GameStatus checkForAutomaticGameEnd(Move object) {
        GameStatus gameStatus;
        GameStatus gameStatus2 = gameStatus = GameStatus.GAME_RUNNING;
        if (object != null) {
            Position position = object.getResultingPosition();
            gameStatus2 = gameStatus;
            if (position != null) {
                if (position.isCheckMate()) {
                    object = GameResult.WHITE_WINS;
                    if (position.getActiveColor()) {
                        object = GameResult.BLACK_WINS;
                    }
                    return new GameStatus((GameResult)((Object)object), GameEndReason.MATE);
                }
                if (position.isStaleMate()) {
                    return new GameStatus(GameResult.DRAW, GameEndReason.DRAW_BY_STALEMATE);
                }
                if (this.hasInsufficientMaterial(position)) {
                    return new GameStatus(GameResult.DRAW, GameEndReason.DRAW_BY_INSUFFICENT_MATERIAL);
                }
                if (this.didRepeatMoves((Move)object)) {
                    return new GameStatus(GameResult.DRAW, GameEndReason.DRAW_BY_THREE_REPETITIONS);
                }
                gameStatus2 = gameStatus;
                if (position.getActionCounter() >= 100) {
                    gameStatus2 = new GameStatus(GameResult.DRAW, GameEndReason.DRAW_BY_FIFTY_MOVE_RULE);
                }
            }
        }
        return gameStatus2;
    }
}
