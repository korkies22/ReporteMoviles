// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.tactics.model;

import de.cisha.chess.model.Game;
import org.json.JSONException;
import de.cisha.chess.util.Logger;
import de.cisha.chess.model.Rating;
import de.cisha.chess.model.GameType;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONGameParser;
import org.json.JSONObject;
import de.cisha.chess.model.exercise.TacticsExercise;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;

public class TacticsExerciseParser extends JSONAPIResultParser<TacticsExercise>
{
    private static final String LASTMOVE = "lastmove";
    private static final String PUZZLE = "puzzle";
    
    @Override
    public TacticsExercise parseResult(final JSONObject jsonObject) throws InvalidJsonForObjectException {
        while (true) {
            while (true) {
                Label_0177: {
                    try {
                        final Game result = new JSONGameParser().parseResult(jsonObject.getJSONObject("puzzle"));
                        final boolean boolean1 = jsonObject.getBoolean("lastmove");
                        if (result.getStartingPosition() == null) {
                            throw new InvalidJsonForObjectException("Incorrect game in Puzzle");
                        }
                        boolean activeColor;
                        if (boolean1) {
                            if (result.getStartingPosition().getActiveColor()) {
                                break Label_0177;
                            }
                            activeColor = true;
                        }
                        else {
                            activeColor = result.getStartingPosition().getActiveColor();
                        }
                        final double optDouble = jsonObject.optDouble("avg", 0.0);
                        final double optDouble2 = jsonObject.optDouble("elo", 0.0);
                        result.setType(GameType.TACTICS);
                        final TacticsExercise tacticsExercise = new TacticsExercise(result, activeColor, new Rating((int)optDouble2), (int)(optDouble * 1000.0));
                        tacticsExercise.setExerciseId(jsonObject.getInt("id"));
                        return tacticsExercise;
                    }
                    catch (JSONException ex) {
                        Logger.getInstance().debug(TacticsExerciseParser.class.getName(), JSONException.class.getName(), (Throwable)ex);
                        throw new InvalidJsonForObjectException("Error parsing json TecticsExercise: ", (Throwable)ex);
                    }
                }
                boolean activeColor = false;
                continue;
            }
        }
    }
}
