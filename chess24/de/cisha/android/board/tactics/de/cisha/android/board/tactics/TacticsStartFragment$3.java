/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.tactics;

import de.cisha.android.board.LoadingHelperWithAPIStatusCode;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.tactics.ITacticsExerciseService;
import java.util.List;
import org.json.JSONObject;

class TacticsStartFragment
extends LoadCommandCallbackWithTimeout<ITacticsExerciseService.TacticsTrainerInfo> {
    TacticsStartFragment() {
    }

    @Override
    protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        TacticsStartFragment.this._loadingHelper.loadingFailed(this, aPIStatusCode);
    }

    @Override
    protected void succeded(ITacticsExerciseService.TacticsTrainerInfo tacticsTrainerInfo) {
        TacticsStartFragment.this._dailyPuzzlesInfo = tacticsTrainerInfo;
        TacticsStartFragment.this._onStartTimeMillis = System.currentTimeMillis();
        TacticsStartFragment.this._loadingHelper.loadingCompleted(this);
    }
}
