/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.tactics.model;

import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.android.board.tactics.model.TacticsExerciseInfo;
import de.cisha.android.board.tactics.model.TacticsExerciseInfoParser;
import de.cisha.android.board.tactics.model.TacticsExerciseSessionInfo;
import de.cisha.chess.model.Rating;
import de.cisha.chess.util.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TacticsExerciseSessionInfoParser
extends JSONAPIResultParser<TacticsExerciseSessionInfo> {
    @Override
    public TacticsExerciseSessionInfo parseResult(JSONObject jSONObject) throws InvalidJsonForObjectException {
        TacticsExerciseSessionInfo tacticsExerciseSessionInfo = new TacticsExerciseSessionInfo();
        try {
            jSONObject = jSONObject.getJSONObject("summary");
        }
        catch (JSONException jSONException) {
            Logger.getInstance().debug(TacticsExerciseSessionInfoParser.class.getName(), JSONException.class.getName(), (Throwable)jSONException);
            throw new InvalidJsonForObjectException("No field summary at session object", (Throwable)jSONException);
        }
        tacticsExerciseSessionInfo.setPuzzleCount(jSONObject.optInt("countTotal"));
        tacticsExerciseSessionInfo.setCorrectPuzzleCount(jSONObject.optInt("countSuccess"));
        int n = jSONObject.optInt("elo");
        tacticsExerciseSessionInfo.setUserCurrentRating(new Rating(n));
        tacticsExerciseSessionInfo.setUserStartRating(new Rating(n - jSONObject.optInt("eloChange", 0)));
        tacticsExerciseSessionInfo.setUserPerformance(new Rating(jSONObject.optInt("performance")));
        tacticsExerciseSessionInfo.setAverageTimePerPuzzle((int)(jSONObject.optDouble("tempo") * 1000.0));
        jSONObject = jSONObject.optJSONArray("puzzles");
        if (jSONObject != null) {
            TacticsExerciseInfoParser tacticsExerciseInfoParser = new TacticsExerciseInfoParser();
            for (int i = 0; i < jSONObject.length(); ++i) {
                try {
                    tacticsExerciseSessionInfo.addPuzzleInfo(tacticsExerciseInfoParser.parseResult(jSONObject.getJSONObject(i)));
                    continue;
                }
                catch (JSONException jSONException) {
                    Logger.getInstance().debug(TacticsExerciseSessionInfoParser.class.getName(), JSONException.class.getName(), (Throwable)jSONException);
                }
            }
        }
        return tacticsExerciseSessionInfo;
    }
}
