/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.model;

import de.cisha.android.board.playzone.model.GameEndInformation;
import de.cisha.android.board.playzone.model.IGameModelDelegate;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameStatus;
import de.cisha.chess.model.Move;

class AbstractGameModel
implements IGameModelDelegate {
    AbstractGameModel() {
    }

    @Override
    public void onClockChanged(long l, boolean bl) {
    }

    @Override
    public void onClockFlag(boolean bl) {
    }

    @Override
    public void onClockStarted() {
    }

    @Override
    public void onClockStopped() {
    }

    @Override
    public void onGameEnd(GameEndInformation gameEndInformation) {
    }

    @Override
    public void onGameSessionEnded(GameStatus gameStatus) {
    }

    @Override
    public void onGameSessionOver(Game game, GameEndInformation gameEndInformation) {
    }

    @Override
    public void onGameStart() {
    }

    @Override
    public void onMove(Move move) {
    }

    @Override
    public void onOpponentDeclinedRematch() {
    }

    @Override
    public void onReceiveDrawOffer() {
    }

    @Override
    public void onReceivedRematchOffer() {
    }

    @Override
    public void onStartGameFailed(int n) {
    }
}
