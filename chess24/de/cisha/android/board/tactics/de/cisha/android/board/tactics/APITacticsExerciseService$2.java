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
import de.cisha.chess.model.exercise.TacticsExerciseSolutionInfo;
import java.util.List;
import org.json.JSONObject;

class APITacticsExerciseService
extends LoadCommandCallbackWithTimeout<TacticsExerciseSolutionInfo> {
    APITacticsExerciseService(int n) {
        super(n);
    }

    @Override
    public void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        APITacticsExerciseService.this.loadingUserSolutionInfoFinished(aPIStatusCode);
        APITacticsExerciseService.this._isLoadingUserSolutionInfo = false;
    }

    @Override
    public void succeded(TacticsExerciseSolutionInfo tacticsExerciseSolutionInfo) {
        APITacticsExerciseService.this._nextInfo = tacticsExerciseSolutionInfo;
        APITacticsExerciseService.this.loadingUserSolutionInfoFinished(APIStatusCode.API_OK);
        APITacticsExerciseService.this._isLoadingUserSolutionInfo = false;
    }
}
