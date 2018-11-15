/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.service.jsonparser;

import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.android.board.tactics.ITacticsExerciseService;
import org.json.JSONException;
import org.json.JSONObject;

public class TacticsTrainerInfoParser
extends JSONAPIResultParser<ITacticsExerciseService.TacticsTrainerInfo> {
    @Override
    public ITacticsExerciseService.TacticsTrainerInfo parseResult(JSONObject jSONObject) throws InvalidJsonForObjectException, JSONException {
        int n = jSONObject.optInt("puzzles", -1);
        boolean bl = false;
        int n2 = jSONObject.optInt("wait", 0);
        if (n >= 0) {
            bl = true;
        }
        return new ITacticsExerciseService.TacticsTrainerInfo(bl, n, n2 * 1000);
    }
}
