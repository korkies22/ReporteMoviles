// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.tactics.model;

import org.json.JSONArray;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import org.json.JSONException;
import de.cisha.chess.util.Logger;
import de.cisha.chess.model.Rating;
import org.json.JSONObject;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;

public class TacticsExerciseSessionInfoParser extends JSONAPIResultParser<TacticsExerciseSessionInfo>
{
    @Override
    public TacticsExerciseSessionInfo parseResult(JSONObject o) throws InvalidJsonForObjectException {
        final TacticsExerciseSessionInfo tacticsExerciseSessionInfo = new TacticsExerciseSessionInfo();
        try {
            o = ((JSONObject)o).getJSONObject("summary");
            tacticsExerciseSessionInfo.setPuzzleCount(((JSONObject)o).optInt("countTotal"));
            tacticsExerciseSessionInfo.setCorrectPuzzleCount(((JSONObject)o).optInt("countSuccess"));
            final int optInt = ((JSONObject)o).optInt("elo");
            tacticsExerciseSessionInfo.setUserCurrentRating(new Rating(optInt));
            int i = 0;
            tacticsExerciseSessionInfo.setUserStartRating(new Rating(optInt - ((JSONObject)o).optInt("eloChange", 0)));
            tacticsExerciseSessionInfo.setUserPerformance(new Rating(((JSONObject)o).optInt("performance")));
            tacticsExerciseSessionInfo.setAverageTimePerPuzzle((int)(((JSONObject)o).optDouble("tempo") * 1000.0));
            o = ((JSONObject)o).optJSONArray("puzzles");
            if (o != null) {
                final TacticsExerciseInfoParser tacticsExerciseInfoParser = new TacticsExerciseInfoParser();
                while (i < ((JSONArray)o).length()) {
                    try {
                        tacticsExerciseSessionInfo.addPuzzleInfo(tacticsExerciseInfoParser.parseResult(((JSONArray)o).getJSONObject(i)));
                    }
                    catch (JSONException ex) {
                        Logger.getInstance().debug(TacticsExerciseSessionInfoParser.class.getName(), JSONException.class.getName(), (Throwable)ex);
                    }
                    ++i;
                }
            }
            return tacticsExerciseSessionInfo;
        }
        catch (JSONException ex2) {
            Logger.getInstance().debug(TacticsExerciseSessionInfoParser.class.getName(), JSONException.class.getName(), (Throwable)ex2);
            throw new InvalidJsonForObjectException("No field summary at session object", (Throwable)ex2);
        }
    }
}
