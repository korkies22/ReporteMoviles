// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.tactics.model;

import org.json.JSONArray;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import org.json.JSONException;
import de.cisha.chess.util.Logger;
import java.util.List;
import de.cisha.chess.model.CishaUUID;
import de.cisha.chess.model.exercise.TacticsExercise;
import java.util.LinkedList;
import org.json.JSONObject;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;

public class TacticsExercisesRequestParser extends JSONAPIResultParser<TacticsExercisesBundle>
{
    public static final String ALL_PUZZLES = "puzzles";
    private static final String EMPTY_REASON = "empty";
    
    @Override
    public TacticsExercisesBundle parseResult(final JSONObject jsonObject) throws InvalidJsonForObjectException {
        final LinkedList<TacticsExercise> list = new LinkedList<TacticsExercise>();
        try {
            if (jsonObject.has("puzzles") && !jsonObject.isNull("puzzles")) {
                final JSONArray jsonArray = jsonObject.getJSONArray("puzzles");
                final TacticsExerciseParser tacticsExerciseParser = new TacticsExerciseParser();
                for (int i = 0; i < jsonArray.length(); ++i) {
                    list.add(tacticsExerciseParser.parseResult(jsonArray.getJSONObject(i)));
                }
            }
            final String optString = jsonObject.optString("session", "");
            final CishaUUID cishaUUID = new CishaUUID(optString, optString.isEmpty() ^ true);
            if (list.size() == 0) {
                return new TacticsExercisesBundle(TacticsExercisesBundle.NoMoreExercisesReason.reasonForString(jsonObject.optString("empty")), cishaUUID);
            }
            return new TacticsExercisesBundle(list, cishaUUID);
        }
        catch (JSONException ex) {
            Logger.getInstance().debug(TacticsExerciseParser.class.getName(), JSONException.class.getName(), (Throwable)ex);
            throw new InvalidJsonForObjectException("Error parsing TacticPuzzles JSON: ", (Throwable)ex);
        }
    }
}
