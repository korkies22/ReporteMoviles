// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.remote.model;

import android.support.annotation.Nullable;
import de.cisha.chess.model.ClockSetting;
import de.cisha.android.board.playzone.model.IChessGameConnection;

public interface IRemoteGameConnection extends IChessGameConnection
{
    void initGameSession(final String p0, final boolean p1, final RemoteGameDelegator p2);
    
    void requestForChallengeEngine(final String p0, final ClockSetting p1, final boolean p2, final RemoteGameDelegator p3);
    
    void requestForPairing(final ClockSetting p0, @Nullable final EloRange p1, final RemoteGameDelegator p2);
    
    void requestForRematch(final String p0, final RemoteGameDelegator p1);
}
