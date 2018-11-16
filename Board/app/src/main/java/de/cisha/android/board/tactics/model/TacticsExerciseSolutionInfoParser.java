// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.tactics.model;

import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import org.json.JSONException;
import de.cisha.chess.util.Logger;
import de.cisha.chess.model.Rating;
import org.json.JSONObject;
import de.cisha.chess.model.exercise.TacticsExerciseSolutionInfo;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;

public class TacticsExerciseSolutionInfoParser extends JSONAPIResultParser<TacticsExerciseSolutionInfo>
{
    @Override
    public TacticsExerciseSolutionInfo parseResult(final JSONObject jsonObject) throws InvalidJsonForObjectException {
        try {
            return new TacticsExerciseSolutionInfo(jsonObject.getInt("id"), new Rating(jsonObject.getInt("eloFailure")), new Rating(jsonObject.getInt("eloSuccess")), new Rating(jsonObject.getInt("eloUser")));
        }
        catch (JSONException ex) {
            Logger.getInstance().debug(TacticsExerciseSolutionInfoParser.class.getName(), JSONException.class.getName(), (Throwable)ex);
            throw new InvalidJsonForObjectException("error parsing user promise", (Throwable)ex);
        }
    }
}
