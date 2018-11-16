// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service.jsonparser;

import org.json.JSONException;
import org.json.JSONObject;
import de.cisha.android.board.tactics.ITacticsExerciseService;

public class TacticsTrainerInfoParser extends JSONAPIResultParser<ITacticsExerciseService.TacticsTrainerInfo>
{
    @Override
    public ITacticsExerciseService.TacticsTrainerInfo parseResult(final JSONObject jsonObject) throws InvalidJsonForObjectException, JSONException {
        final int optInt = jsonObject.optInt("puzzles", -1);
        boolean b = false;
        final int optInt2 = jsonObject.optInt("wait", 0);
        if (optInt >= 0) {
            b = true;
        }
        return new ITacticsExerciseService.TacticsTrainerInfo(b, optInt, optInt2 * 1000);
    }
}
