// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import java.util.Iterator;
import de.cisha.chess.model.GameType;
import java.util.LinkedList;
import java.util.List;
import android.content.Context;
import de.cisha.chess.model.GameInfo;
import java.util.HashMap;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameID;
import java.util.Map;

public class MemoryGameService implements IGameService
{
    private static IGameService _instance;
    private static int nextUUID = 324234;
    private Map<GameID, Game> _allGamesById;
    
    private MemoryGameService() {
        this._allGamesById = new HashMap<GameID, Game>();
    }
    
    private GameInfo getGameInfoFromGame(final Game game) {
        return new GameInfo(game);
    }
    
    public static IGameService getInstance(final Context context) {
        synchronized (MemoryGameService.class) {
            if (MemoryGameService._instance == null) {
                MemoryGameService._instance = new MemoryGameService();
            }
            return MemoryGameService._instance;
        }
    }
    
    private static GameID getNewGameId() {
        final int nextUUID = MemoryGameService.nextUUID;
        MemoryGameService.nextUUID = nextUUID + 1;
        return new GameID(nextUUID);
    }
    
    @Override
    public void deleteGameWithId(final GameID gameID) {
        this._allGamesById.remove(gameID);
    }
    
    @Override
    public List<GameInfo> getAnalyzedGameInfos() {
        final LinkedList<GameInfo> list = new LinkedList<GameInfo>();
        for (final Game game : this._allGamesById.values()) {
            if (game.getType() == GameType.ANALYZED_GAME) {
                list.add(this.getGameInfoFromGame(game));
            }
        }
        return list;
    }
    
    @Override
    public Game getGame(final GameID gameID) {
        return this._allGamesById.get(gameID);
    }
    
    @Override
    public List<GameInfo> getPlayzoneGameInfos() {
        final LinkedList<GameInfo> list = new LinkedList<GameInfo>();
        for (final Game game : this._allGamesById.values()) {
            if (game.getType() != GameType.ANALYZED_GAME) {
                list.add(this.getGameInfoFromGame(game));
            }
        }
        return list;
    }
    
    @Override
    public void saveAnalysis(final Game game) {
        final GameID gameId = game.getGameId();
        GameID gameID;
        Game copy;
        if (game.getType() == GameType.ANALYZED_GAME) {
            gameID = gameId;
            copy = game;
            if (gameId == null) {
                gameID = getNewGameId();
                game.setGameId(gameID);
                copy = game;
            }
        }
        else {
            final GameID gameId2 = game.getGameId();
            copy = game.copy();
            copy.setOriginalGameId(gameId2);
            gameID = getNewGameId();
            copy.setType(GameType.ANALYZED_GAME);
            copy.setGameId(gameID);
        }
        this._allGamesById.put(gameID, copy);
    }
    
    @Override
    public void savePlayzoneGame(final Game game) {
        final GameID newGameId = getNewGameId();
        game.setGameId(newGameId);
        this._allGamesById.put(newGameId, game);
    }
}
