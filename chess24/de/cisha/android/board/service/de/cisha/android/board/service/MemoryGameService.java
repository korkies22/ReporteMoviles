/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package de.cisha.android.board.service;

import android.content.Context;
import de.cisha.android.board.service.IGameService;
import de.cisha.chess.model.AbstractMoveContainer;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameID;
import de.cisha.chess.model.GameInfo;
import de.cisha.chess.model.GameType;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MemoryGameService
implements IGameService {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static IGameService _instance;
    private static int nextUUID = 324234;
    private Map<GameID, Game> _allGamesById = new HashMap<GameID, Game>();

    private MemoryGameService() {
    }

    private GameInfo getGameInfoFromGame(Game game) {
        return new GameInfo(game);
    }

    public static IGameService getInstance(Context object) {
        synchronized (MemoryGameService.class) {
            if (_instance == null) {
                _instance = new MemoryGameService();
            }
            object = _instance;
            return object;
        }
    }

    private static GameID getNewGameId() {
        int n = nextUUID;
        nextUUID = n + 1;
        return new GameID(n);
    }

    @Override
    public void deleteGameWithId(GameID gameID) {
        this._allGamesById.remove(gameID);
    }

    @Override
    public List<GameInfo> getAnalyzedGameInfos() {
        LinkedList<GameInfo> linkedList = new LinkedList<GameInfo>();
        for (Game game : this._allGamesById.values()) {
            if (game.getType() != GameType.ANALYZED_GAME) continue;
            linkedList.add(this.getGameInfoFromGame(game));
        }
        return linkedList;
    }

    @Override
    public Game getGame(GameID gameID) {
        return this._allGamesById.get(gameID);
    }

    @Override
    public List<GameInfo> getPlayzoneGameInfos() {
        LinkedList<GameInfo> linkedList = new LinkedList<GameInfo>();
        for (Game game : this._allGamesById.values()) {
            if (game.getType() == GameType.ANALYZED_GAME) continue;
            linkedList.add(this.getGameInfoFromGame(game));
        }
        return linkedList;
    }

    @Override
    public void saveAnalysis(Game game) {
        GameID gameID;
        AbstractMoveContainer abstractMoveContainer;
        GameID gameID2 = game.getGameId();
        if (game.getType() == GameType.ANALYZED_GAME) {
            gameID = gameID2;
            abstractMoveContainer = game;
            if (gameID2 == null) {
                gameID = MemoryGameService.getNewGameId();
                game.setGameId(gameID);
                abstractMoveContainer = game;
            }
        } else {
            gameID = game.getGameId();
            abstractMoveContainer = game.copy();
            abstractMoveContainer.setOriginalGameId(gameID);
            gameID = MemoryGameService.getNewGameId();
            abstractMoveContainer.setType(GameType.ANALYZED_GAME);
            abstractMoveContainer.setGameId(gameID);
        }
        this._allGamesById.put(gameID, (Game)abstractMoveContainer);
    }

    @Override
    public void savePlayzoneGame(Game game) {
        GameID gameID = MemoryGameService.getNewGameId();
        game.setGameId(gameID);
        this._allGamesById.put(gameID, game);
    }
}
