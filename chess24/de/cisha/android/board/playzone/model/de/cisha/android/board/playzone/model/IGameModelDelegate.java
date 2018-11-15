/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.model;

import de.cisha.android.board.playzone.model.ClockListener;
import de.cisha.android.board.playzone.model.GameEndInformation;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameStatus;
import de.cisha.chess.model.Move;

public interface IGameModelDelegate
extends ClockListener {
    public void onGameEnd(GameEndInformation var1);

    public void onGameSessionEnded(GameStatus var1);

    public void onGameSessionOver(Game var1, GameEndInformation var2);

    public void onGameStart();

    public void onMove(Move var1);

    public void onOpponentDeclinedRematch();

    public void onReceiveDrawOffer();

    public void onReceivedRematchOffer();

    public void onStartGameFailed(int var1);
}
