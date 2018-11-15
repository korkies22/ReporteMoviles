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
import de.cisha.android.board.tactics.APITacticsExerciseService;
import de.cisha.android.board.tactics.model.TacticsExercisesBundle;
import de.cisha.chess.model.CishaUUID;
import de.cisha.chess.model.exercise.TacticsExercise;
import de.cisha.chess.model.exercise.TacticsExerciseSession;
import java.util.Collection;
import java.util.List;
import org.json.JSONObject;

class APITacticsExerciseService
extends LoadCommandCallbackWithTimeout<TacticsExercisesBundle> {
    APITacticsExerciseService(int n) {
        super(n);
    }

    @Override
    public void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        APITacticsExerciseService.this._isLoadingTactics = false;
        APITacticsExerciseService.this.loadingExercisesFinished(aPIStatusCode);
    }

    @Override
    public void succeded(TacticsExercisesBundle object) {
        APITacticsExerciseService.this._cachedExercises.addAll(object.getExercises());
        APITacticsExerciseService.this._session.setSessionId(object.getSessionId());
        APITacticsExerciseService.this._isLoadingTactics = false;
        if (object.hasExercises()) {
            APITacticsExerciseService.this.loadingExercisesFinished(APIStatusCode.API_OK);
            return;
        }
        TacticsExercisesBundle.NoMoreExercisesReason noMoreExercisesReason = object.getNoExercisesReason();
        object = null;
        switch (APITacticsExerciseService.$SwitchMap$de$cisha$android$board$tactics$model$TacticsExercisesBundle$NoMoreExercisesReason[noMoreExercisesReason.ordinal()]) {
            default: {
                break;
            }
            case 3: {
                object = APIStatusCode.INTERNAL_TACTICTRAINER_GUEST_LIMIT_REACHED;
                break;
            }
            case 2: {
                object = APIStatusCode.INTERNAL_TACTICTRAINER_DAILY_LIMIT_REACHED;
                break;
            }
            case 1: {
                object = APIStatusCode.INTERNAL_TACTICTRAINER_NO_MORE_EXERCISES;
            }
        }
        APITacticsExerciseService.this.loadingExercisesFinished((APIStatusCode)((Object)object));
    }
}
