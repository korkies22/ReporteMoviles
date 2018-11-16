// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.tactics.model;

import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import org.json.JSONException;
import de.cisha.chess.util.Logger;
import de.cisha.chess.model.Rating;
import org.json.JSONObject;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;

public class TacticsExerciseInfoParser extends JSONAPIResultParser<TacticsExerciseInfo>
{
    @Override
    public TacticsExerciseInfo parseResult(final JSONObject jsonObject) throws InvalidJsonForObjectException {
        try {
            return new TacticsExerciseInfo(jsonObject.getInt("id"), jsonObject.optString("status", "").equals("success"), jsonObject.optInt("eloChange"), new Rating(jsonObject.optInt("eloPuzzle")), new Rating(jsonObject.optInt("eloUser")), (int)(jsonObject.optDouble("time") * 1000.0), (int)(jsonObject.optDouble("timeAvg") * 1000.0), jsonObject.optInt("likes"), jsonObject.optInt("unlikes"));
        }
        catch (JSONException ex) {
            Logger.getInstance().debug(TacticsExerciseInfoParser.class.getName(), JSONException.class.getName(), (Throwable)ex);
            throw new InvalidJsonForObjectException("error Parsing tactics puzzle info", (Throwable)ex);
        }
    }
}
