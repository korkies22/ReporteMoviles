/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ContentValues
 *  android.content.Context
 *  android.database.Cursor
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteDatabase$CursorFactory
 *  android.database.sqlite.SQLiteException
 *  android.database.sqlite.SQLiteOpenHelper
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.database.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import de.cisha.android.board.database.service.LocalSqlGameService;
import de.cisha.android.board.service.jsonmapping.JSONMappingGame;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONGameParser;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameEndReason;
import de.cisha.chess.model.GameID;
import de.cisha.chess.model.GameInfo;
import de.cisha.chess.model.GameResult;
import de.cisha.chess.model.GameStatus;
import de.cisha.chess.model.GameType;
import de.cisha.chess.model.Opponent;
import de.cisha.chess.model.Rating;
import de.cisha.chess.util.Logger;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

class GameDbHelper
extends SQLiteOpenHelper {
    private static final String COMMA_SEP = ",";
    private static final String DATABASE_NAME = "ChessGames.db";
    private static final int DATABASE_VERSION = 2;
    private static final String NUM_TYPE = " INTEGER";
    private static final String SQL_CREATE_TABLE_GAME = "CREATE TABLE game(game_id INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT,type INTEGER,creation_date INTEGER DEFAULT (CURRENT_TIMESTAMP),edit_date INTEGER DEFAULT (CURRENT_TIMESTAMP),event TEXT,site TEXT,date TEXT,origin_game_id INTEGER,origin_game_type INTEGER,result TEXT,player_black TEXT,player_black_elo INTEGER,player_white TEXT,player_white_elo INTEGER,jsongame TEXT)";
    private static final String SQL_CREATE_TRIGGER_EDIT_DATE = "CREATE TRIGGER IF NOT EXISTS update_edit_date AFTER UPDATE  ON game BEGIN  \tUPDATE game \tSET edit_date= (CURRENT_TIMESTAMP) \t\tWHERE game_id= old.game_id; END";
    private static final String SQL_DROP_TABLE_GAME = "DROP TABLE IF EXISTS game";
    private static final String TEXT_TYPE = " TEXT";

    public GameDbHelper(Context context, String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(DATABASE_NAME);
        stringBuilder.append(string);
        super(context, stringBuilder.toString(), null, 2);
    }

    private static ContentValues createContentValuesForGame(Game game, String string) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("date", Long.valueOf(game.getStartDate().getTime()));
        contentValues.put("title", game.getTitle());
        Object object = game.getType();
        int n = -1;
        int n2 = object != null ? object.getNumericRepresentation() : -1;
        contentValues.put("type", Integer.valueOf(n2));
        contentValues.put("event", game.getEvent());
        object = game.getOriginalGameId();
        if (object != null) {
            contentValues.put("origin_game_id", Integer.valueOf(object.getDBId()));
        }
        object = game.getOriginalGameType();
        n2 = n;
        if (object != null) {
            n2 = object.getNumericRepresentation();
        }
        contentValues.put("origin_game_type", Integer.valueOf(n2));
        contentValues.put("player_black", game.getBlackPlayer().getName());
        contentValues.put("player_black_elo", Integer.valueOf(game.getBlackPlayer().getRating().getRating()));
        contentValues.put("player_white", game.getWhitePlayer().getName());
        contentValues.put("player_white_elo", Integer.valueOf(game.getWhitePlayer().getRating().getRating()));
        contentValues.put("result", game.getResult().getResult().getShortDescription());
        contentValues.put("site", game.getSite());
        contentValues.put("jsongame", string);
        return contentValues;
    }

    public static void deleteGame(SQLiteDatabase sQLiteDatabase, int n) {
        try {
            sQLiteDatabase.beginTransaction();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("game_id=");
            stringBuilder.append(n);
            sQLiteDatabase.delete("game", stringBuilder.toString(), null);
            sQLiteDatabase.setTransactionSuccessful();
            return;
        }
        finally {
            sQLiteDatabase.endTransaction();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Game getGameForId(SQLiteDatabase sQLiteDatabase, int n) {
        Object object = new StringBuilder();
        object.append("game_id=");
        object.append(n);
        object = object.toString();
        sQLiteDatabase = sQLiteDatabase.query("game", new String[]{"jsongame", "type", "origin_game_id"}, (String)object, null, null, null, null);
        sQLiteDatabase.moveToFirst();
        if (sQLiteDatabase.getCount() > 0) {
            int n2;
            block7 : {
                object = sQLiteDatabase.getString(sQLiteDatabase.getColumnIndex("jsongame"));
                try {
                    n2 = sQLiteDatabase.getInt(sQLiteDatabase.getColumnIndex("origin_game_id"));
                    break block7;
                }
                catch (Exception exception) {}
                n2 = -1;
            }
            try {
                object = new JSONGameParser().parseResult(new JSONObject((String)object));
                object.setGameId(new GameID(n));
                if (n2 > -1) {
                    object.setOriginalGameId(new GameID(n2));
                }
                return object;
            }
            catch (JSONException jSONException) {
                Logger.getInstance().error(GameDbHelper.class.getName(), JSONException.class.getName(), (Throwable)jSONException);
            }
            catch (InvalidJsonForObjectException invalidJsonForObjectException) {
                Logger.getInstance().error(GameDbHelper.class.getName(), InvalidJsonForObjectException.class.getName(), invalidJsonForObjectException);
            }
        }
        sQLiteDatabase.close();
        return null;
    }

    public static List<GameInfo> getGameInfosForType(SQLiteDatabase sQLiteDatabase, GameType object, GameType object2) {
        LinkedList<GameInfo> linkedList = new LinkedList<GameInfo>();
        int n = 0;
        Object object3 = "";
        if (object != null) {
            object3 = new StringBuilder();
            object3.append("type = ");
            object3.append(object.getNumericRepresentation());
            object3 = object3.toString();
        }
        object = object3;
        if (object2 != null) {
            object3 = object;
            if (object.length() > 0) {
                object3 = new StringBuilder();
                object3.append((String)object);
                object3.append(" AND ");
                object3 = object3.toString();
            }
            object = new StringBuilder();
            object.append((String)object3);
            object.append("type<>");
            object.append(object2.getNumericRepresentation());
            object = object.toString();
        }
        sQLiteDatabase = sQLiteDatabase.query("game", new String[]{"game_id", "origin_game_id", "origin_game_type", "event", "date", "player_black", "player_black_elo", "player_white", "player_white_elo", "result", "site", "title", "type"}, (String)object, null, null, null, "creation_date DESC", null);
        sQLiteDatabase.moveToFirst();
        while (n < sQLiteDatabase.getCount()) {
            sQLiteDatabase.moveToPosition(n);
            int n2 = sQLiteDatabase.getInt(sQLiteDatabase.getColumnIndex("game_id"));
            int n3 = sQLiteDatabase.getInt(sQLiteDatabase.getColumnIndex("origin_game_id"));
            int n4 = sQLiteDatabase.getInt(sQLiteDatabase.getColumnIndex("origin_game_type"));
            Object object4 = sQLiteDatabase.getString(sQLiteDatabase.getColumnIndex("event"));
            long l = sQLiteDatabase.getLong(sQLiteDatabase.getColumnIndex("date"));
            Object object5 = sQLiteDatabase.getString(sQLiteDatabase.getColumnIndex("player_black"));
            int n5 = sQLiteDatabase.getInt(sQLiteDatabase.getColumnIndex("player_black_elo"));
            object = sQLiteDatabase.getString(sQLiteDatabase.getColumnIndex("player_white"));
            int n6 = sQLiteDatabase.getInt(sQLiteDatabase.getColumnIndex("player_white_elo"));
            object2 = sQLiteDatabase.getString(sQLiteDatabase.getColumnIndex("result"));
            String string = sQLiteDatabase.getString(sQLiteDatabase.getColumnIndex("site"));
            String string2 = sQLiteDatabase.getString(sQLiteDatabase.getColumnIndex("title"));
            int n7 = sQLiteDatabase.getInt(sQLiteDatabase.getColumnIndex("type"));
            Date date = new Date(l);
            object3 = new GameInfo(new GameID(n2));
            object3.setOriginalGameID(new GameID(n3));
            object3.setOriginalGameType(GameType.forNumericRepresentation(n4));
            object3.setTitle(string2);
            object3.setDate(date);
            object3.setSite(string);
            object3.setEvent((String)object4);
            object3.setType(GameType.forNumericRepresentation(n7));
            object4 = new Opponent();
            object4.setName((String)object5);
            object4.setRating(new Rating(n5));
            object3.setBlackplayer((Opponent)object4);
            object5 = new Opponent();
            object5.setName((String)object);
            object5.setRating(new Rating(n6));
            object3.setWhitePlayer((Opponent)object5);
            object3.setResultStatus(new GameStatus(GameResult.fromString((String)object2), GameEndReason.NO_REASON));
            linkedList.add((GameInfo)object3);
            ++n;
        }
        sQLiteDatabase.close();
        return linkedList;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static void insertNewGame(SQLiteDatabase sQLiteDatabase, Game game) {
        JSONMappingGame jSONMappingGame;
        try {
            jSONMappingGame = new JSONMappingGame();
            jSONMappingGame.setMapFens(false);
            jSONMappingGame = GameDbHelper.createContentValuesForGame(game, jSONMappingGame.mapToJSON(game).toString());
        }
        catch (JSONException jSONException) {
            Logger.getInstance().error(LocalSqlGameService.class.getName(), JSONException.class.getName(), (Throwable)jSONException);
            return;
        }
        sQLiteDatabase.beginTransaction();
        long l = sQLiteDatabase.insert("game", null, (ContentValues)jSONMappingGame);
        sQLiteDatabase.setTransactionSuccessful();
        game.setGameId(new GameID((int)l));
        {
            catch (Throwable throwable) {
                sQLiteDatabase.endTransaction();
                throw throwable;
            }
        }
        sQLiteDatabase.endTransaction();
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static boolean updateGame(SQLiteDatabase sQLiteDatabase, Game game) {
        void var1_11;
        int n;
        block18 : {
            boolean bl;
            block16 : {
                void var0_4;
                block17 : {
                    int n2;
                    block19 : {
                        block15 : {
                            bl = false;
                            Object object = new JSONMappingGame();
                            object.setMapFens(false);
                            object = object.mapToJSON(game).toString();
                            sQLiteDatabase.beginTransaction();
                            if (game.getGameId() != null) {
                                object = GameDbHelper.createContentValuesForGame(game, (String)object);
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("game_id=");
                                stringBuilder.append(game.getGameId().getUuid());
                                n = sQLiteDatabase.update("game", (ContentValues)object, stringBuilder.toString(), null);
                                break block15;
                            }
                            n = 0;
                        }
                        sQLiteDatabase.setTransactionSuccessful();
                        try {
                            sQLiteDatabase.endTransaction();
                            break block16;
                        }
                        catch (JSONException jSONException) {
                            break block17;
                        }
                        catch (Throwable throwable) {
                            break block18;
                        }
                        catch (SQLiteException sQLiteException) {
                            break block19;
                        }
                        catch (Throwable throwable) {
                            n = 0;
                            break block18;
                        }
                        catch (SQLiteException sQLiteException) {
                            n = 0;
                        }
                    }
                    try {
                        void var1_10;
                        Logger.getInstance().error(GameDbHelper.class.getName(), SQLiteException.class.getName(), (Throwable)var1_10);
                        n2 = n;
                    }
                    catch (Throwable throwable) {}
                    try {
                        sQLiteDatabase.endTransaction();
                        break block16;
                    }
                    catch (JSONException jSONException) {
                        n = n2;
                        break block17;
                    }
                    catch (JSONException jSONException) {
                        n = 0;
                    }
                }
                Logger.getInstance().error(GameDbHelper.class.getName(), JSONException.class.getName(), (Throwable)var0_4);
            }
            if (n <= 0) return bl;
            return true;
        }
        int n2 = n;
        sQLiteDatabase.endTransaction();
        n2 = n;
        throw var1_11;
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.beginTransaction();
        try {
            sQLiteDatabase.execSQL(SQL_CREATE_TABLE_GAME);
            sQLiteDatabase.execSQL(SQL_CREATE_TRIGGER_EDIT_DATE);
            sQLiteDatabase.setTransactionSuccessful();
            return;
        }
        finally {
            sQLiteDatabase.endTransaction();
        }
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int n, int n2) {
        sQLiteDatabase.beginTransaction();
        try {
            sQLiteDatabase.execSQL(SQL_DROP_TABLE_GAME);
            sQLiteDatabase.execSQL(SQL_CREATE_TABLE_GAME);
            sQLiteDatabase.execSQL(SQL_CREATE_TRIGGER_EDIT_DATE);
            sQLiteDatabase.setTransactionSuccessful();
            return;
        }
        finally {
            sQLiteDatabase.endTransaction();
        }
    }
}
