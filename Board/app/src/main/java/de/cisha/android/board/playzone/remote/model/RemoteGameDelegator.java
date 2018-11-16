// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.remote.model;

import de.cisha.android.board.service.NodeServerAddress;
import de.cisha.chess.model.ClockSetting;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameStatus;
import de.cisha.android.board.playzone.model.GameEndInformation;
import de.cisha.android.board.playzone.remote.PlayzoneConnectionListener;
import de.cisha.android.board.playzone.model.IGameDelegate;

public interface RemoteGameDelegator extends IGameDelegate, PlayzoneConnectionListener
{
    void onGameEnd(final GameEndInformation p0);
    
    void onGameSessionEnded(final GameStatus p0);
    
    void onGameSessionOver(final Game p0, final GameEndInformation p1);
    
    void onJoinGameFailed();
    
    void onMove(final String p0, final int p1);
    
    void onMoveACK(final int p0, final int p1);
    
    void onPairingFailed();
    
    void onPairingFailedNoOpponentFound();
    
    void onPairingFailedNotAllowed();
    
    void onPairingSucceeded(final boolean p0, final ClockSetting p1, final String p2, final NodeServerAddress p3);
    
    void onRematchOffer();
    
    void opponentDeclinedRematch();
}
