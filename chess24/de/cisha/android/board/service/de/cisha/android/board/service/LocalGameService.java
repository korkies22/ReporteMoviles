/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.service;

import android.content.Context;
import android.content.SharedPreferences;
import de.cisha.android.board.analyze.AnalyzeFragment;
import de.cisha.android.board.service.ILocalGameService;
import de.cisha.android.board.service.StoredGameInformation;
import de.cisha.android.board.service.jsonmapping.JSONMappingGame;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONGameParser;
import de.cisha.chess.model.ClockSetting;
import de.cisha.chess.model.Game;
import de.cisha.chess.util.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public class LocalGameService
implements ILocalGameService {
    private static final String PREF_GAME = "jsonGame";
    private static final String PREF_PLAYER_WHITE = "playerWhite";
    private static final String PREF_TIME_GONE = "timeGone";
    private static LocalGameService _instance;
    private Context _context;
    private SharedPreferences _prefs;

    private LocalGameService(Context context) {
        this._context = context;
        this._prefs = this._context.getSharedPreferences("playzone_engine", 0);
    }

    public static ILocalGameService getInstance(Context context) {
        if (_instance == null) {
            _instance = new LocalGameService(context);
        }
        return _instance;
    }

    @Override
    public void clearGameStorage() {
        SharedPreferences.Editor editor = this._prefs.edit();
        editor.remove(PREF_GAME);
        editor.remove(PREF_PLAYER_WHITE);
        editor.commit();
    }

    @Override
    public StoredGameInformation loadGame() {
        Object object = this._prefs.getString(PREF_GAME, null);
        boolean bl = this._prefs.getBoolean(PREF_PLAYER_WHITE, true);
        long l = this._prefs.getLong(PREF_TIME_GONE, 0L);
        if (object != null) {
            try {
                object = new JSONGameParser().parseResult(new JSONObject((String)object));
                object.getClockSetting().setTimeGoneSinceLastMove((int)l);
                object = new StoredGameInformation((Game)object, bl);
                return object;
            }
            catch (JSONException jSONException) {
                Logger.getInstance().debug(AnalyzeFragment.class.getName(), JSONException.class.getName(), (Throwable)jSONException);
                return null;
            }
            catch (InvalidJsonForObjectException invalidJsonForObjectException) {
                Logger.getInstance().debug(AnalyzeFragment.class.getName(), InvalidJsonForObjectException.class.getName(), invalidJsonForObjectException);
            }
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void storeGameLocally(Game game, boolean bl, long l) {
        String string = new JSONMappingGame().mapToJSON(game).toString();
        SharedPreferences.Editor editor = this._prefs.edit();
        editor.putString(PREF_GAME, string);
        editor.putBoolean(PREF_PLAYER_WHITE, bl);
        editor.putLong(PREF_TIME_GONE, l);
        editor.commit();
        // MONITOREXIT : game
    }
}
