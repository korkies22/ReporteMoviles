// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.engine.model;

import de.cisha.chess.model.Game;
import de.cisha.android.board.playzone.model.IChessGameConnection;

public interface IEngineGameConnection extends IChessGameConnection
{
    void setGameOnStart(final Game p0);
    
    void startUp();
}
