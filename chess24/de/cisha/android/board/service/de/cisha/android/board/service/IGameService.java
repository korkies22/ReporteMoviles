/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameID;
import de.cisha.chess.model.GameInfo;
import java.util.List;

public interface IGameService {
    public void deleteGameWithId(GameID var1);

    public List<GameInfo> getAnalyzedGameInfos();

    public Game getGame(GameID var1);

    public List<GameInfo> getPlayzoneGameInfos();

    public void saveAnalysis(Game var1);

    public void savePlayzoneGame(Game var1);
}
