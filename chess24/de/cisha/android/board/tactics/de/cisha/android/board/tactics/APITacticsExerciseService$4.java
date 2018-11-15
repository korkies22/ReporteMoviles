/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.tactics;

import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.chess.model.Rating;
import de.cisha.chess.model.exercise.TacticsExerciseSolutionInfo;
import java.util.List;
import org.json.JSONObject;

class APITacticsExerciseService
extends LoadCommandCallbackWithTimeout<TacticsExerciseSolutionInfo> {
    final /* synthetic */ boolean val$loadNextExercise;
    final /* synthetic */ LoadCommandCallback val$newRatingCallback;

    APITacticsExerciseService(int n, LoadCommandCallback loadCommandCallback, boolean bl) {
        this.val$newRatingCallback = loadCommandCallback;
        this.val$loadNextExercise = bl;
        super(n);
    }

    @Override
    public void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        APITacticsExerciseService.this._isLoadingUserSolutionInfo = false;
        if (aPIStatusCode == APIStatusCode.INTERNAL_INCORRECT_SERVER_JSON_NO_DATA_OBJECT) {
            APITacticsExerciseService.this.loadingUserSolutionInfoFinished(APIStatusCode.API_OK);
            APITacticsExerciseService.this.getUserExerciseRating(this.val$newRatingCallback);
            return;
        }
        APITacticsExerciseService.this.loadingUserSolutionInfoFinished(aPIStatusCode);
        if (!this.val$loadNextExercise) {
            APITacticsExerciseService.this.getUserExerciseRating(this.val$newRatingCallback);
            return;
        }
        this.val$newRatingCallback.loadingFailed(aPIStatusCode, string, null, null);
    }

    @Override
    public void succeded(TacticsExerciseSolutionInfo tacticsExerciseSolutionInfo) {
        APITacticsExerciseService.this._nextInfo = tacticsExerciseSolutionInfo;
        APITacticsExerciseService.this.loadingUserSolutionInfoFinished(APIStatusCode.API_OK);
        APITacticsExerciseService.this._isLoadingUserSolutionInfo = false;
        this.val$newRatingCallback.loadingSucceded(tacticsExerciseSolutionInfo.getUserRatingNow());
    }
}
