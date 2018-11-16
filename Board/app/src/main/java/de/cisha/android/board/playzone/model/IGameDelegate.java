// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.model;

import de.cisha.chess.model.Move;
import de.cisha.chess.model.Game;

public interface IGameDelegate
{
    void onGameEndDetected(final GameEndInformation p0);
    
    void onGameStart(final Game p0, final int p1, final int p2);
    
    void onMove(final Move p0);
    
    void onReceiveDrawOffer();
}
