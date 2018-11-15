/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.tactics.model;

import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.android.board.tactics.model.TacticsExerciseInfo;
import de.cisha.chess.model.Rating;
import de.cisha.chess.util.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public class TacticsExerciseInfoParser
extends JSONAPIResultParser<TacticsExerciseInfo> {
    @Override
    public TacticsExerciseInfo parseResult(JSONObject object) throws InvalidJsonForObjectException {
        try {
            int n = object.getInt("id");
            boolean bl = object.optString("status", "").equals("success");
            int n2 = object.optInt("eloChange");
            int n3 = object.optInt("eloPuzzle");
            int n4 = object.optInt("eloUser");
            int n5 = (int)(object.optDouble("time") * 1000.0);
            int n6 = (int)(object.optDouble("timeAvg") * 1000.0);
            int n7 = object.optInt("likes");
            int n8 = object.optInt("unlikes");
            object = new TacticsExerciseInfo(n, bl, n2, new Rating(n3), new Rating(n4), n5, n6, n7, n8);
            return object;
        }
        catch (JSONException jSONException) {
            Logger.getInstance().debug(TacticsExerciseInfoParser.class.getName(), JSONException.class.getName(), (Throwable)jSONException);
            throw new InvalidJsonForObjectException("error Parsing tactics puzzle info", (Throwable)jSONException);
        }
    }
}
