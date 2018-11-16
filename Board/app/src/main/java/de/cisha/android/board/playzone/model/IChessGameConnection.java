// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.model;

import de.cisha.chess.model.Move;

public interface IChessGameConnection
{
    void acceptDrawOffer();
    
    void disconnect();
    
    void doMove(final Move p0);
    
    void offerDraw();
    
    void requestAbort();
    
    void resign();
}
