/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.tactics;

import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.chess.model.Rating;
import java.util.List;
import org.json.JSONObject;

class TacticsExerciseFragment
extends LoadCommandCallbackWithTimeout<Rating> {
    TacticsExerciseFragment() {
    }

    @Override
    public void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
    }

    @Override
    public void succeded(Rating rating) {
        TacticsExerciseFragment.this._rating = rating;
    }
}
