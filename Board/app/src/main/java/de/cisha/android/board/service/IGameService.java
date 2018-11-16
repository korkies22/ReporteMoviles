// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameInfo;
import java.util.List;
import de.cisha.chess.model.GameID;

public interface IGameService
{
    void deleteGameWithId(final GameID p0);
    
    List<GameInfo> getAnalyzedGameInfos();
    
    Game getGame(final GameID p0);
    
    List<GameInfo> getPlayzoneGameInfos();
    
    void saveAnalysis(final Game p0);
    
    void savePlayzoneGame(final Game p0);
}
