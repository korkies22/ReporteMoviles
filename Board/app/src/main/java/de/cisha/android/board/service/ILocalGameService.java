// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import de.cisha.chess.model.Game;

public interface ILocalGameService
{
    void clearGameStorage();
    
    StoredGameInformation loadGame();
    
    void storeGameLocally(final Game p0, final boolean p1, final long p2);
}
