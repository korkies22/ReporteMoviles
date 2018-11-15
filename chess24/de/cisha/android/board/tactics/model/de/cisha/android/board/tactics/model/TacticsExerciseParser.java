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
import de.cisha.android.board.service.jsonparser.JSONGameParser;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameType;
import de.cisha.chess.model.Rating;
import de.cisha.chess.model.exercise.TacticsExercise;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.util.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public class TacticsExerciseParser
extends JSONAPIResultParser<TacticsExercise> {
    private static final String LASTMOVE = "lastmove";
    private static final String PUZZLE = "puzzle";

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public TacticsExercise parseResult(JSONObject jSONObject) throws InvalidJsonForObjectException {
        boolean bl;
        Object object;
        try {
            object = jSONObject.getJSONObject(PUZZLE);
            object = new JSONGameParser().parseResult((JSONObject)object);
            bl = jSONObject.getBoolean(LASTMOVE);
            if (object.getStartingPosition() == null) {
                throw new InvalidJsonForObjectException("Incorrect game in Puzzle");
            }
            if (bl) {
                bl = !object.getStartingPosition().getActiveColor();
            }
            bl = object.getStartingPosition().getActiveColor();
        }
        catch (JSONException jSONException) {
            Logger.getInstance().debug(TacticsExerciseParser.class.getName(), JSONException.class.getName(), (Throwable)jSONException);
            throw new InvalidJsonForObjectException("Error parsing json TecticsExercise: ", (Throwable)jSONException);
        }
        double d = jSONObject.optDouble("avg", 0.0);
        double d2 = jSONObject.optDouble("elo", 0.0);
        object.setType(GameType.TACTICS);
        object = new TacticsExercise((Game)object, bl, new Rating((int)d2), (int)(d * 1000.0));
        object.setExerciseId(jSONObject.getInt("id"));
        return object;
    }
}
