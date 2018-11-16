// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.model;

import de.cisha.chess.model.Move;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameStatus;

public interface IGameModelDelegate extends ClockListener
{
    void onGameEnd(final GameEndInformation p0);
    
    void onGameSessionEnded(final GameStatus p0);
    
    void onGameSessionOver(final Game p0, final GameEndInformation p1);
    
    void onGameStart();
    
    void onMove(final Move p0);
    
    void onOpponentDeclinedRematch();
    
    void onReceiveDrawOffer();
    
    void onReceivedRematchOffer();
    
    void onStartGameFailed(final int p0);
}
