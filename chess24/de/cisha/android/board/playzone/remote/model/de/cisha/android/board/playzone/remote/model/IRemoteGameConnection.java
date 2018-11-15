/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.remote.model;

import android.support.annotation.Nullable;
import de.cisha.android.board.playzone.model.IChessGameConnection;
import de.cisha.android.board.playzone.remote.model.EloRange;
import de.cisha.android.board.playzone.remote.model.RemoteGameDelegator;
import de.cisha.chess.model.ClockSetting;

public interface IRemoteGameConnection
extends IChessGameConnection {
    public void initGameSession(String var1, boolean var2, RemoteGameDelegator var3);

    public void requestForChallengeEngine(String var1, ClockSetting var2, boolean var3, RemoteGameDelegator var4);

    public void requestForPairing(ClockSetting var1, @Nullable EloRange var2, RemoteGameDelegator var3);

    public void requestForRematch(String var1, RemoteGameDelegator var2);
}
