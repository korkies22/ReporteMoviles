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
import de.cisha.chess.model.Rating;
import de.cisha.chess.model.exercise.TacticsExerciseSolutionInfo;
import de.cisha.chess.util.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public class TacticsExerciseSolutionInfoParser
extends JSONAPIResultParser<TacticsExerciseSolutionInfo> {
    @Override
    public TacticsExerciseSolutionInfo parseResult(JSONObject object) throws InvalidJsonForObjectException {
        try {
            int n = object.getInt("id");
            int n2 = object.getInt("eloUser");
            int n3 = object.getInt("eloSuccess");
            object = new TacticsExerciseSolutionInfo(n, new Rating(object.getInt("eloFailure")), new Rating(n3), new Rating(n2));
            return object;
        }
        catch (JSONException jSONException) {
            Logger.getInstance().debug(TacticsExerciseSolutionInfoParser.class.getName(), JSONException.class.getName(), (Throwable)jSONException);
            throw new InvalidJsonForObjectException("error parsing user promise", (Throwable)jSONException);
        }
    }
}
