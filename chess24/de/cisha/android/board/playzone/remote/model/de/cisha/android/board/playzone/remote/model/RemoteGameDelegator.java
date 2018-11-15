/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.remote.model;

import de.cisha.android.board.playzone.model.GameEndInformation;
import de.cisha.android.board.playzone.model.IGameDelegate;
import de.cisha.android.board.playzone.remote.PlayzoneConnectionListener;
import de.cisha.android.board.service.NodeServerAddress;
import de.cisha.chess.model.ClockSetting;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameStatus;

public interface RemoteGameDelegator
extends IGameDelegate,
PlayzoneConnectionListener {
    public void onGameEnd(GameEndInformation var1);

    public void onGameSessionEnded(GameStatus var1);

    public void onGameSessionOver(Game var1, GameEndInformation var2);

    public void onJoinGameFailed();

    public void onMove(String var1, int var2);

    public void onMoveACK(int var1, int var2);

    public void onPairingFailed();

    public void onPairingFailedNoOpponentFound();

    public void onPairingFailedNotAllowed();

    public void onPairingSucceeded(boolean var1, ClockSetting var2, String var3, NodeServerAddress var4);

    public void onRematchOffer();

    public void opponentDeclinedRematch();
}
