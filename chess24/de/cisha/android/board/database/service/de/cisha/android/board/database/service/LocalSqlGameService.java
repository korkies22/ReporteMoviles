/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.database.sqlite.SQLiteDatabase
 */
package de.cisha.android.board.database.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import de.cisha.android.board.database.service.GameDbHelper;
import de.cisha.android.board.service.IGameService;
import de.cisha.android.board.service.ILoginService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameEndReason;
import de.cisha.chess.model.GameID;
import de.cisha.chess.model.GameInfo;
import de.cisha.chess.model.GameStatus;
import de.cisha.chess.model.GameType;
import java.io.Serializable;
import java.util.List;

public class LocalSqlGameService
implements IGameService,
ILoginService.LoginObserver {
    private static LocalSqlGameService _instance;
    private Context _context;
    private String _userPrefix;

    private LocalSqlGameService(Context context) {
        this._context = context;
        this._userPrefix = ServiceProvider.getInstance().getLoginService().getUserPrefix();
        ServiceProvider.getInstance().getLoginService().addLoginListener(this);
    }

    public static LocalSqlGameService getInstance(Context context) {
        if (_instance == null) {
            _instance = new LocalSqlGameService(context);
        }
        return _instance;
    }

    @Override
    public void deleteGameWithId(GameID gameID) {
        GameDbHelper gameDbHelper = new GameDbHelper(this._context, this._userPrefix);
        GameDbHelper.deleteGame(gameDbHelper.getWritableDatabase(), gameID.getDBId());
        gameDbHelper.close();
    }

    @Override
    public List<GameInfo> getAnalyzedGameInfos() {
        GameDbHelper gameDbHelper = new GameDbHelper(this._context, this._userPrefix);
        List<GameInfo> list = GameDbHelper.getGameInfosForType(gameDbHelper.getReadableDatabase(), GameType.ANALYZED_GAME, null);
        gameDbHelper.close();
        return list;
    }

    @Override
    public Game getGame(GameID serializable) {
        if (serializable.getDBId() >= 0) {
            GameDbHelper gameDbHelper = new GameDbHelper(this._context, this._userPrefix);
            serializable = GameDbHelper.getGameForId(gameDbHelper.getReadableDatabase(), serializable.getDBId());
            gameDbHelper.close();
            return serializable;
        }
        return null;
    }

    @Override
    public List<GameInfo> getPlayzoneGameInfos() {
        GameDbHelper gameDbHelper = new GameDbHelper(this._context, this._userPrefix);
        List<GameInfo> list = GameDbHelper.getGameInfosForType(gameDbHelper.getReadableDatabase(), null, GameType.ANALYZED_GAME);
        gameDbHelper.close();
        return list;
    }

    @Override
    public void loginStateChanged(boolean bl) {
        this._userPrefix = ServiceProvider.getInstance().getLoginService().getUserPrefix();
    }

    @Override
    public void saveAnalysis(Game game) {
        GameDbHelper gameDbHelper = new GameDbHelper(this._context, this._userPrefix);
        SQLiteDatabase sQLiteDatabase = gameDbHelper.getWritableDatabase();
        if (game.getType() != GameType.ANALYZED_GAME) {
            game.setType(GameType.ANALYZED_GAME);
            game.setOriginalGameId(game.getGameId());
            game.setGameId(null);
        }
        if (game.getGameId() != null && GameDbHelper.getGameForId(sQLiteDatabase, game.getGameId().getDBId()) != null) {
            GameDbHelper.updateGame(sQLiteDatabase, game);
        } else {
            GameDbHelper.insertNewGame(sQLiteDatabase, game);
        }
        gameDbHelper.close();
    }

    @Override
    public void savePlayzoneGame(Game game) {
        if (game.getResult().getReason() != GameEndReason.ABORTED) {
            GameDbHelper gameDbHelper = new GameDbHelper(this._context, this._userPrefix);
            GameDbHelper.insertNewGame(gameDbHelper.getWritableDatabase(), game);
            gameDbHelper.close();
        }
    }
}
