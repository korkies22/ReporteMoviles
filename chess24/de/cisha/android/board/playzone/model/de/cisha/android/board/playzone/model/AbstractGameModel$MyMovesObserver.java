/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.model;

import de.cisha.android.board.playzone.model.AbstractGameModel;
import de.cisha.android.board.playzone.model.ChessClock;
import de.cisha.android.board.playzone.model.GameEndInformation;
import de.cisha.android.board.playzone.model.IChessGameConnection;
import de.cisha.android.board.playzone.model.IGameModelDelegate;
import de.cisha.chess.model.GameEndChecker;
import de.cisha.chess.model.GameStatus;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.model.MovesObserver;
import de.cisha.chess.model.Piece;
import de.cisha.chess.model.position.Position;

private final class AbstractGameModel.MyMovesObserver
implements MovesObserver {
    private AbstractGameModel.MyMovesObserver() {
    }

    @Override
    public void allMovesChanged(MoveContainer moveContainer) {
    }

    @Override
    public boolean canSkipMoves() {
        return false;
    }

    @Override
    public void newMove(Move move) {
        long l = AbstractGameModel.this.getClock().getTimeSinceLastMove();
        boolean bl = move.getPiece().isWhite();
        move.setMoveTimeInMills((int)l);
        Object object = AbstractGameModel.this._gameEndChecker.checkForAutomaticGameEnd(move);
        if (!GameStatus.GAME_RUNNING.equals(object)) {
            object = new GameEndInformation((GameStatus)object, AbstractGameModel.this.getPosition().getActiveColor());
            AbstractGameModel.this.onGameEndDetected((GameEndInformation)object);
        }
        if (AbstractGameModel.this.playerIsWhite() == bl) {
            AbstractGameModel.this.getConnection().doMove(move);
        }
        AbstractGameModel.this._delegate.onMove(move);
    }

    @Override
    public void selectedMoveChanged(Move move) {
    }
}
