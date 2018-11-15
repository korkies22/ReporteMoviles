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
import de.cisha.android.board.tactics.model.TacticsExerciseSessionInfo;
import de.cisha.chess.model.Rating;
import de.cisha.chess.model.exercise.TacticsExerciseSession;
import de.cisha.chess.util.Logger;
import java.util.List;
import org.json.JSONObject;

class APITacticsExerciseService
extends LoadCommandCallbackWithTimeout<TacticsExerciseSessionInfo> {
    final /* synthetic */ LoadCommandCallback val$callback;

    APITacticsExerciseService(int n, LoadCommandCallback loadCommandCallback) {
        this.val$callback = loadCommandCallback;
        super(n);
    }

    @Override
    public void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        if (APITacticsExerciseService.this._session != null) {
            Logger.getInstance().error("APITacticsService", "failed to load session - using local one");
            this.val$callback.loadingSucceded(APITacticsExerciseService.this._session);
            return;
        }
        this.val$callback.loadingFailed(aPIStatusCode, string, list, null);
    }

    @Override
    public void succeded(TacticsExerciseSessionInfo tacticsExerciseSessionInfo) {
        APITacticsExerciseService.this._session.setRatingPerformance(tacticsExerciseSessionInfo.getUserPerformance());
        APITacticsExerciseService.this._session.setUserStartRating(tacticsExerciseSessionInfo.getUserStartRating());
        APITacticsExerciseService.this._session.setUserEndRating(tacticsExerciseSessionInfo.getUserCurrentRating());
        this.val$callback.loadingSucceded(APITacticsExerciseService.this._session);
    }
}
