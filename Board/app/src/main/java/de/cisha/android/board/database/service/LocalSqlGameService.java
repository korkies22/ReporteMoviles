// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.database.service;

import de.cisha.chess.model.GameEndReason;
import android.database.sqlite.SQLiteDatabase;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameType;
import de.cisha.chess.model.GameInfo;
import java.util.List;
import de.cisha.chess.model.GameID;
import de.cisha.android.board.service.ServiceProvider;
import android.content.Context;
import de.cisha.android.board.service.ILoginService;
import de.cisha.android.board.service.IGameService;

public class LocalSqlGameService implements IGameService, LoginObserver
{
    private static LocalSqlGameService _instance;
    private Context _context;
    private String _userPrefix;
    
    private LocalSqlGameService(final Context context) {
        this._context = context;
        this._userPrefix = ServiceProvider.getInstance().getLoginService().getUserPrefix();
        ServiceProvider.getInstance().getLoginService().addLoginListener((ILoginService.LoginObserver)this);
    }
    
    public static LocalSqlGameService getInstance(final Context context) {
        if (LocalSqlGameService._instance == null) {
            LocalSqlGameService._instance = new LocalSqlGameService(context);
        }
        return LocalSqlGameService._instance;
    }
    
    @Override
    public void deleteGameWithId(final GameID gameID) {
        final GameDbHelper gameDbHelper = new GameDbHelper(this._context, this._userPrefix);
        GameDbHelper.deleteGame(gameDbHelper.getWritableDatabase(), gameID.getDBId());
        gameDbHelper.close();
    }
    
    @Override
    public List<GameInfo> getAnalyzedGameInfos() {
        final GameDbHelper gameDbHelper = new GameDbHelper(this._context, this._userPrefix);
        final List<GameInfo> gameInfosForType = GameDbHelper.getGameInfosForType(gameDbHelper.getReadableDatabase(), GameType.ANALYZED_GAME, null);
        gameDbHelper.close();
        return gameInfosForType;
    }
    
    @Override
    public Game getGame(final GameID gameID) {
        if (gameID.getDBId() >= 0) {
            final GameDbHelper gameDbHelper = new GameDbHelper(this._context, this._userPrefix);
            final Game gameForId = GameDbHelper.getGameForId(gameDbHelper.getReadableDatabase(), gameID.getDBId());
            gameDbHelper.close();
            return gameForId;
        }
        return null;
    }
    
    @Override
    public List<GameInfo> getPlayzoneGameInfos() {
        final GameDbHelper gameDbHelper = new GameDbHelper(this._context, this._userPrefix);
        final List<GameInfo> gameInfosForType = GameDbHelper.getGameInfosForType(gameDbHelper.getReadableDatabase(), null, GameType.ANALYZED_GAME);
        gameDbHelper.close();
        return gameInfosForType;
    }
    
    @Override
    public void loginStateChanged(final boolean b) {
        this._userPrefix = ServiceProvider.getInstance().getLoginService().getUserPrefix();
    }
    
    @Override
    public void saveAnalysis(final Game game) {
        final GameDbHelper gameDbHelper = new GameDbHelper(this._context, this._userPrefix);
        final SQLiteDatabase writableDatabase = gameDbHelper.getWritableDatabase();
        if (game.getType() != GameType.ANALYZED_GAME) {
            game.setType(GameType.ANALYZED_GAME);
            game.setOriginalGameId(game.getGameId());
            game.setGameId(null);
        }
        if (game.getGameId() != null && GameDbHelper.getGameForId(writableDatabase, game.getGameId().getDBId()) != null) {
            GameDbHelper.updateGame(writableDatabase, game);
        }
        else {
            GameDbHelper.insertNewGame(writableDatabase, game);
        }
        gameDbHelper.close();
    }
    
    @Override
    public void savePlayzoneGame(final Game game) {
        if (game.getResult().getReason() != GameEndReason.ABORTED) {
            final GameDbHelper gameDbHelper = new GameDbHelper(this._context, this._userPrefix);
            GameDbHelper.insertNewGame(gameDbHelper.getWritableDatabase(), game);
            gameDbHelper.close();
        }
    }
}
