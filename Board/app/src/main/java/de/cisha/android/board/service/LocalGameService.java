// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import de.cisha.android.board.service.jsonmapping.JSONMappingGame;
import de.cisha.chess.model.Game;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import org.json.JSONException;
import de.cisha.android.board.analyze.AnalyzeFragment;
import de.cisha.chess.util.Logger;
import org.json.JSONObject;
import de.cisha.android.board.service.jsonparser.JSONGameParser;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences;
import android.content.Context;

public class LocalGameService implements ILocalGameService
{
    private static final String PREF_GAME = "jsonGame";
    private static final String PREF_PLAYER_WHITE = "playerWhite";
    private static final String PREF_TIME_GONE = "timeGone";
    private static LocalGameService _instance;
    private Context _context;
    private SharedPreferences _prefs;
    
    private LocalGameService(final Context context) {
        this._context = context;
        this._prefs = this._context.getSharedPreferences("playzone_engine", 0);
    }
    
    public static ILocalGameService getInstance(final Context context) {
        if (LocalGameService._instance == null) {
            LocalGameService._instance = new LocalGameService(context);
        }
        return LocalGameService._instance;
    }
    
    @Override
    public void clearGameStorage() {
        final SharedPreferences.Editor edit = this._prefs.edit();
        edit.remove("jsonGame");
        edit.remove("playerWhite");
        edit.commit();
    }
    
    @Override
    public StoredGameInformation loadGame() {
        final String string = this._prefs.getString("jsonGame", (String)null);
        final boolean boolean1 = this._prefs.getBoolean("playerWhite", true);
        final long long1 = this._prefs.getLong("timeGone", 0L);
        if (string != null) {
            try {
                final Game result = new JSONGameParser().parseResult(new JSONObject(string));
                result.getClockSetting().setTimeGoneSinceLastMove((int)long1);
                return new StoredGameInformation(result, boolean1);
            }
            catch (JSONException ex) {
                Logger.getInstance().debug(AnalyzeFragment.class.getName(), JSONException.class.getName(), (Throwable)ex);
                return null;
            }
            catch (InvalidJsonForObjectException ex2) {
                Logger.getInstance().debug(AnalyzeFragment.class.getName(), InvalidJsonForObjectException.class.getName(), ex2);
            }
        }
        return null;
    }
    
    @Override
    public void storeGameLocally(final Game game, final boolean b, final long n) {
        try {
            synchronized (game) {
                final String string = new JSONMappingGame().mapToJSON(game).toString();
                final SharedPreferences.Editor edit = this._prefs.edit();
                edit.putString("jsonGame", string);
                edit.putBoolean("playerWhite", b);
                edit.putLong("timeGone", n);
                edit.commit();
            }
        }
        catch (JSONException ex) {
            Logger.getInstance().debug(this.getClass().getName(), JSONException.class.getName(), (Throwable)ex);
        }
    }
}
