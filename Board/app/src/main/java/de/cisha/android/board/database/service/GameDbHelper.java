// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.database.service;

import de.cisha.android.board.service.jsonmapping.JSONMappingGame;
import android.database.Cursor;
import de.cisha.chess.model.GameStatus;
import de.cisha.chess.model.GameEndReason;
import de.cisha.chess.model.GameResult;
import de.cisha.chess.model.Rating;
import de.cisha.chess.model.Opponent;
import java.util.Date;
import java.util.LinkedList;
import de.cisha.chess.model.GameInfo;
import java.util.List;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import org.json.JSONException;
import de.cisha.chess.util.Logger;
import org.json.JSONObject;
import de.cisha.android.board.service.jsonparser.JSONGameParser;
import android.database.sqlite.SQLiteDatabase;
import de.cisha.chess.model.GameID;
import de.cisha.chess.model.GameType;
import android.content.ContentValues;
import de.cisha.chess.model.Game;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

class GameDbHelper extends SQLiteOpenHelper
{
    private static final String COMMA_SEP = ",";
    private static final String DATABASE_NAME = "ChessGames.db";
    private static final int DATABASE_VERSION = 2;
    private static final String NUM_TYPE = " INTEGER";
    private static final String SQL_CREATE_TABLE_GAME = "CREATE TABLE game(game_id INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT,type INTEGER,creation_date INTEGER DEFAULT (CURRENT_TIMESTAMP),edit_date INTEGER DEFAULT (CURRENT_TIMESTAMP),event TEXT,site TEXT,date TEXT,origin_game_id INTEGER,origin_game_type INTEGER,result TEXT,player_black TEXT,player_black_elo INTEGER,player_white TEXT,player_white_elo INTEGER,jsongame TEXT)";
    private static final String SQL_CREATE_TRIGGER_EDIT_DATE = "CREATE TRIGGER IF NOT EXISTS update_edit_date AFTER UPDATE  ON game BEGIN  \tUPDATE game \tSET edit_date= (CURRENT_TIMESTAMP) \t\tWHERE game_id= old.game_id; END";
    private static final String SQL_DROP_TABLE_GAME = "DROP TABLE IF EXISTS game";
    private static final String TEXT_TYPE = " TEXT";
    
    public GameDbHelper(final Context context, final String s) {
        final StringBuilder sb = new StringBuilder();
        sb.append("ChessGames.db");
        sb.append(s);
        super(context, sb.toString(), (SQLiteDatabase.CursorFactory)null, 2);
    }
    
    private static ContentValues createContentValuesForGame(final Game game, final String s) {
        final ContentValues contentValues = new ContentValues();
        contentValues.put("date", game.getStartDate().getTime());
        contentValues.put("title", game.getTitle());
        final GameType type = game.getType();
        final int n = -1;
        int numericRepresentation;
        if (type != null) {
            numericRepresentation = type.getNumericRepresentation();
        }
        else {
            numericRepresentation = -1;
        }
        contentValues.put("type", numericRepresentation);
        contentValues.put("event", game.getEvent());
        final GameID originalGameId = game.getOriginalGameId();
        if (originalGameId != null) {
            contentValues.put("origin_game_id", originalGameId.getDBId());
        }
        final GameType originalGameType = game.getOriginalGameType();
        int numericRepresentation2 = n;
        if (originalGameType != null) {
            numericRepresentation2 = originalGameType.getNumericRepresentation();
        }
        contentValues.put("origin_game_type", numericRepresentation2);
        contentValues.put("player_black", game.getBlackPlayer().getName());
        contentValues.put("player_black_elo", game.getBlackPlayer().getRating().getRating());
        contentValues.put("player_white", game.getWhitePlayer().getName());
        contentValues.put("player_white_elo", game.getWhitePlayer().getRating().getRating());
        contentValues.put("result", game.getResult().getResult().getShortDescription());
        contentValues.put("site", game.getSite());
        contentValues.put("jsongame", s);
        return contentValues;
    }
    
    public static void deleteGame(final SQLiteDatabase sqLiteDatabase, final int n) {
        try {
            sqLiteDatabase.beginTransaction();
            final StringBuilder sb = new StringBuilder();
            sb.append("game_id=");
            sb.append(n);
            sqLiteDatabase.delete("game", sb.toString(), (String[])null);
            sqLiteDatabase.setTransactionSuccessful();
        }
        finally {
            sqLiteDatabase.endTransaction();
        }
    }
    
    public static Game getGameForId(SQLiteDatabase query, final int n) {
        final StringBuilder sb = new StringBuilder();
        sb.append("game_id=");
        sb.append(n);
        String s = sb.toString();
        query = (SQLiteDatabase)query.query("game", new String[] { "jsongame", "type", "origin_game_id" }, s, (String[])null, (String)null, (String)null, (String)null);
        ((Cursor)query).moveToFirst();
    Label_0106_Outer:
        while (true) {
            if (((Cursor)query).getCount() > 0) {
                s = ((Cursor)query).getString(((Cursor)query).getColumnIndex("jsongame"));
                while (true) {
                    try {
                        int int1 = ((Cursor)query).getInt(((Cursor)query).getColumnIndex("origin_game_id"));
                        while (true) {
                            try {
                                final Game result = new JSONGameParser().parseResult(new JSONObject(s));
                                result.setGameId(new GameID(n));
                                if (int1 > -1) {
                                    result.setOriginalGameId(new GameID(int1));
                                }
                                return result;
                            }
                            catch (JSONException s) {
                                Logger.getInstance().error(GameDbHelper.class.getName(), JSONException.class.getName(), (Throwable)s);
                            }
                            catch (InvalidJsonForObjectException s) {
                                Logger.getInstance().error(GameDbHelper.class.getName(), InvalidJsonForObjectException.class.getName(), (Throwable)s);
                            }
                            ((Cursor)query).close();
                            return null;
                            int1 = -1;
                            continue Label_0106_Outer;
                        }
                    }
                    catch (Exception ex) {}
                    continue;
                }
            }
            continue;
        }
    }
    
    public static List<GameInfo> getGameInfosForType(final SQLiteDatabase sqLiteDatabase, final GameType gameType, final GameType gameType2) {
        final LinkedList<GameInfo> list = new LinkedList<GameInfo>();
        int i = 0;
        String string = "";
        if (gameType != null) {
            final StringBuilder sb = new StringBuilder();
            sb.append("type = ");
            sb.append(gameType.getNumericRepresentation());
            string = sb.toString();
        }
        String string2 = string;
        if (gameType2 != null) {
            String string3 = string2;
            if (string2.length() > 0) {
                final StringBuilder sb2 = new StringBuilder();
                sb2.append(string2);
                sb2.append(" AND ");
                string3 = sb2.toString();
            }
            final StringBuilder sb3 = new StringBuilder();
            sb3.append(string3);
            sb3.append("type<>");
            sb3.append(gameType2.getNumericRepresentation());
            string2 = sb3.toString();
        }
        final Cursor query = sqLiteDatabase.query("game", new String[] { "game_id", "origin_game_id", "origin_game_type", "event", "date", "player_black", "player_black_elo", "player_white", "player_white_elo", "result", "site", "title", "type" }, string2, (String[])null, (String)null, (String)null, "creation_date DESC", (String)null);
        query.moveToFirst();
        while (i < query.getCount()) {
            query.moveToPosition(i);
            final int int1 = query.getInt(query.getColumnIndex("game_id"));
            final int int2 = query.getInt(query.getColumnIndex("origin_game_id"));
            final int int3 = query.getInt(query.getColumnIndex("origin_game_type"));
            final String string4 = query.getString(query.getColumnIndex("event"));
            final long long1 = query.getLong(query.getColumnIndex("date"));
            final String string5 = query.getString(query.getColumnIndex("player_black"));
            final int int4 = query.getInt(query.getColumnIndex("player_black_elo"));
            final String string6 = query.getString(query.getColumnIndex("player_white"));
            final int int5 = query.getInt(query.getColumnIndex("player_white_elo"));
            final String string7 = query.getString(query.getColumnIndex("result"));
            final String string8 = query.getString(query.getColumnIndex("site"));
            final String string9 = query.getString(query.getColumnIndex("title"));
            final int int6 = query.getInt(query.getColumnIndex("type"));
            final Date date = new Date(long1);
            final GameInfo gameInfo = new GameInfo(new GameID(int1));
            gameInfo.setOriginalGameID(new GameID(int2));
            gameInfo.setOriginalGameType(GameType.forNumericRepresentation(int3));
            gameInfo.setTitle(string9);
            gameInfo.setDate(date);
            gameInfo.setSite(string8);
            gameInfo.setEvent(string4);
            gameInfo.setType(GameType.forNumericRepresentation(int6));
            final Opponent blackplayer = new Opponent();
            blackplayer.setName(string5);
            blackplayer.setRating(new Rating(int4));
            gameInfo.setBlackplayer(blackplayer);
            final Opponent whitePlayer = new Opponent();
            whitePlayer.setName(string6);
            whitePlayer.setRating(new Rating(int5));
            gameInfo.setWhitePlayer(whitePlayer);
            gameInfo.setResultStatus(new GameStatus(GameResult.fromString(string7), GameEndReason.NO_REASON));
            list.add(gameInfo);
            ++i;
        }
        query.close();
        return list;
    }
    
    public static void insertNewGame(final SQLiteDatabase sqLiteDatabase, final Game game) {
        try {
            final JSONMappingGame jsonMappingGame = new JSONMappingGame();
            jsonMappingGame.setMapFens(false);
            final ContentValues contentValuesForGame = createContentValuesForGame(game, jsonMappingGame.mapToJSON(game).toString());
            try {
                sqLiteDatabase.beginTransaction();
                final long insert = sqLiteDatabase.insert("game", (String)null, contentValuesForGame);
                sqLiteDatabase.setTransactionSuccessful();
                game.setGameId(new GameID((int)insert));
            }
            finally {
                sqLiteDatabase.endTransaction();
            }
        }
        catch (JSONException ex) {
            Logger.getInstance().error(LocalSqlGameService.class.getName(), JSONException.class.getName(), (Throwable)ex);
        }
    }
    
    public static boolean updateGame(final SQLiteDatabase p0, final Game p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     0: iconst_0       
        //     1: istore          4
        //     3: new             Lde/cisha/android/board/service/jsonmapping/JSONMappingGame;
        //     6: dup            
        //     7: invokespecial   de/cisha/android/board/service/jsonmapping/JSONMappingGame.<init>:()V
        //    10: astore          5
        //    12: aload           5
        //    14: iconst_0       
        //    15: invokevirtual   de/cisha/android/board/service/jsonmapping/JSONMappingGame.setMapFens:(Z)V
        //    18: aload           5
        //    20: aload_1        
        //    21: invokevirtual   de/cisha/android/board/service/jsonmapping/JSONMappingGame.mapToJSON:(Lde/cisha/chess/model/Game;)Lorg/json/JSONObject;
        //    24: invokevirtual   org/json/JSONObject.toString:()Ljava/lang/String;
        //    27: astore          5
        //    29: aload_0        
        //    30: invokevirtual   android/database/sqlite/SQLiteDatabase.beginTransaction:()V
        //    33: aload_1        
        //    34: invokevirtual   de/cisha/chess/model/Game.getGameId:()Lde/cisha/chess/model/GameID;
        //    37: ifnull          96
        //    40: aload_1        
        //    41: aload           5
        //    43: invokestatic    de/cisha/android/board/database/service/GameDbHelper.createContentValuesForGame:(Lde/cisha/chess/model/Game;Ljava/lang/String;)Landroid/content/ContentValues;
        //    46: astore          5
        //    48: new             Ljava/lang/StringBuilder;
        //    51: dup            
        //    52: invokespecial   java/lang/StringBuilder.<init>:()V
        //    55: astore          6
        //    57: aload           6
        //    59: ldc             "game_id="
        //    61: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    64: pop            
        //    65: aload           6
        //    67: aload_1        
        //    68: invokevirtual   de/cisha/chess/model/Game.getGameId:()Lde/cisha/chess/model/GameID;
        //    71: invokevirtual   de/cisha/chess/model/GameID.getUuid:()Ljava/lang/String;
        //    74: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    77: pop            
        //    78: aload_0        
        //    79: ldc             "game"
        //    81: aload           5
        //    83: aload           6
        //    85: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    88: aconst_null    
        //    89: invokevirtual   android/database/sqlite/SQLiteDatabase.update:(Ljava/lang/String;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I
        //    92: istore_2       
        //    93: goto            98
        //    96: iconst_0       
        //    97: istore_2       
        //    98: aload_0        
        //    99: invokevirtual   android/database/sqlite/SQLiteDatabase.setTransactionSuccessful:()V
        //   102: aload_0        
        //   103: invokevirtual   android/database/sqlite/SQLiteDatabase.endTransaction:()V
        //   106: goto            193
        //   109: astore_0       
        //   110: goto            176
        //   113: astore_1       
        //   114: goto            157
        //   117: astore_1       
        //   118: goto            130
        //   121: astore_1       
        //   122: iconst_0       
        //   123: istore_2       
        //   124: goto            157
        //   127: astore_1       
        //   128: iconst_0       
        //   129: istore_2       
        //   130: invokestatic    de/cisha/chess/util/Logger.getInstance:()Lde/cisha/chess/util/Logger;
        //   133: ldc             Lde/cisha/android/board/database/service/GameDbHelper;.class
        //   135: invokevirtual   java/lang/Class.getName:()Ljava/lang/String;
        //   138: ldc_w           Landroid/database/sqlite/SQLiteException;.class
        //   141: invokevirtual   java/lang/Class.getName:()Ljava/lang/String;
        //   144: aload_1        
        //   145: invokevirtual   de/cisha/chess/util/Logger.error:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
        //   148: iload_2        
        //   149: istore_3       
        //   150: aload_0        
        //   151: invokevirtual   android/database/sqlite/SQLiteDatabase.endTransaction:()V
        //   154: goto            193
        //   157: iload_2        
        //   158: istore_3       
        //   159: aload_0        
        //   160: invokevirtual   android/database/sqlite/SQLiteDatabase.endTransaction:()V
        //   163: iload_2        
        //   164: istore_3       
        //   165: aload_1        
        //   166: athrow         
        //   167: astore_0       
        //   168: iload_3        
        //   169: istore_2       
        //   170: goto            176
        //   173: astore_0       
        //   174: iconst_0       
        //   175: istore_2       
        //   176: invokestatic    de/cisha/chess/util/Logger.getInstance:()Lde/cisha/chess/util/Logger;
        //   179: ldc             Lde/cisha/android/board/database/service/GameDbHelper;.class
        //   181: invokevirtual   java/lang/Class.getName:()Ljava/lang/String;
        //   184: ldc             Lorg/json/JSONException;.class
        //   186: invokevirtual   java/lang/Class.getName:()Ljava/lang/String;
        //   189: aload_0        
        //   190: invokevirtual   de/cisha/chess/util/Logger.error:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
        //   193: iload_2        
        //   194: ifle            200
        //   197: iconst_1       
        //   198: istore          4
        //   200: iload           4
        //   202: ireturn        
        //   203: astore_1       
        //   204: goto            157
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                                     
        //  -----  -----  -----  -----  -----------------------------------------
        //  3      29     173    176    Lorg/json/JSONException;
        //  29     93     127    130    Landroid/database/sqlite/SQLiteException;
        //  29     93     121    127    Any
        //  98     102    117    121    Landroid/database/sqlite/SQLiteException;
        //  98     102    113    117    Any
        //  102    106    109    113    Lorg/json/JSONException;
        //  130    148    203    207    Any
        //  150    154    167    173    Lorg/json/JSONException;
        //  159    163    167    173    Lorg/json/JSONException;
        //  165    167    167    173    Lorg/json/JSONException;
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Expression is linked from several locations: Label_0130:
        //     at com.strobel.decompiler.ast.Error.expressionLinkedFromMultipleLocations(Error.java:27)
        //     at com.strobel.decompiler.ast.AstOptimizer.mergeDisparateObjectInitializations(AstOptimizer.java:2592)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:235)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:317)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:238)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:123)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void onCreate(final SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.beginTransaction();
        try {
            sqLiteDatabase.execSQL("CREATE TABLE game(game_id INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT,type INTEGER,creation_date INTEGER DEFAULT (CURRENT_TIMESTAMP),edit_date INTEGER DEFAULT (CURRENT_TIMESTAMP),event TEXT,site TEXT,date TEXT,origin_game_id INTEGER,origin_game_type INTEGER,result TEXT,player_black TEXT,player_black_elo INTEGER,player_white TEXT,player_white_elo INTEGER,jsongame TEXT)");
            sqLiteDatabase.execSQL("CREATE TRIGGER IF NOT EXISTS update_edit_date AFTER UPDATE  ON game BEGIN  \tUPDATE game \tSET edit_date= (CURRENT_TIMESTAMP) \t\tWHERE game_id= old.game_id; END");
            sqLiteDatabase.setTransactionSuccessful();
        }
        finally {
            sqLiteDatabase.endTransaction();
        }
    }
    
    public void onUpgrade(final SQLiteDatabase sqLiteDatabase, final int n, final int n2) {
        sqLiteDatabase.beginTransaction();
        try {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS game");
            sqLiteDatabase.execSQL("CREATE TABLE game(game_id INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT,type INTEGER,creation_date INTEGER DEFAULT (CURRENT_TIMESTAMP),edit_date INTEGER DEFAULT (CURRENT_TIMESTAMP),event TEXT,site TEXT,date TEXT,origin_game_id INTEGER,origin_game_type INTEGER,result TEXT,player_black TEXT,player_black_elo INTEGER,player_white TEXT,player_white_elo INTEGER,jsongame TEXT)");
            sqLiteDatabase.execSQL("CREATE TRIGGER IF NOT EXISTS update_edit_date AFTER UPDATE  ON game BEGIN  \tUPDATE game \tSET edit_date= (CURRENT_TIMESTAMP) \t\tWHERE game_id= old.game_id; END");
            sqLiteDatabase.setTransactionSuccessful();
        }
        finally {
            sqLiteDatabase.endTransaction();
        }
    }
}
