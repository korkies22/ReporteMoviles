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
import de.cisha.android.board.tactics.model.TacticsExerciseParser;
import de.cisha.android.board.tactics.model.TacticsExercisesBundle;
import de.cisha.chess.model.CishaUUID;
import de.cisha.chess.model.exercise.TacticsExercise;
import de.cisha.chess.util.Logger;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TacticsExercisesRequestParser
extends JSONAPIResultParser<TacticsExercisesBundle> {
    public static final String ALL_PUZZLES = "puzzles";
    private static final String EMPTY_REASON = "empty";

    @Override
    public TacticsExercisesBundle parseResult(JSONObject object) throws InvalidJsonForObjectException {
        Object object2;
        LinkedList<TacticsExercise> linkedList;
        block6 : {
            linkedList = new LinkedList<TacticsExercise>();
            if (!object.has(ALL_PUZZLES) || object.isNull(ALL_PUZZLES)) break block6;
            object2 = object.getJSONArray(ALL_PUZZLES);
            TacticsExerciseParser tacticsExerciseParser = new TacticsExerciseParser();
            int n = 0;
            do {
                if (n >= object2.length()) break;
                linkedList.add(tacticsExerciseParser.parseResult(object2.getJSONObject(n)));
                ++n;
                continue;
                break;
            } while (true);
        }
        try {
            object2 = object.optString("session", "");
            object2 = new CishaUUID((String)object2, object2.isEmpty() ^ true);
            if (linkedList.size() == 0) {
                return new TacticsExercisesBundle(TacticsExercisesBundle.NoMoreExercisesReason.reasonForString(object.optString(EMPTY_REASON)), (CishaUUID)object2);
            }
            object = new TacticsExercisesBundle(linkedList, (CishaUUID)object2);
            return object;
        }
        catch (JSONException jSONException) {
            Logger.getInstance().debug(TacticsExerciseParser.class.getName(), JSONException.class.getName(), (Throwable)jSONException);
            throw new InvalidJsonForObjectException("Error parsing TacticPuzzles JSON: ", (Throwable)jSONException);
        }
    }
}
