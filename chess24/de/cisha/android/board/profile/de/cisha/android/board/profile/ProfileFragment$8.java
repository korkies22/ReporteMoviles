/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.profile;

import de.cisha.android.board.LoadingHelper;
import de.cisha.android.board.profile.YourGamesViewController;
import de.cisha.android.board.profile.YourTacticsStatsController;
import de.cisha.android.board.profile.model.DashboardData;
import de.cisha.android.board.profile.model.PlayzoneStatisticData;
import de.cisha.android.board.profile.model.TacticStatisticData;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import org.json.JSONObject;

class ProfileFragment
extends LoadCommandCallbackWithTimeout<DashboardData> {
    ProfileFragment() {
    }

    private void notifyLoadingCompleted() {
        ProfileFragment.this._loadingHelperProfileData.loadingCompleted(ProfileFragment.this);
    }

    @Override
    protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        this.notifyLoadingCompleted();
    }

    @Override
    protected void succeded(DashboardData dashboardData) {
        ProfileFragment.this._yourGamesViewController.setPlayzoneData(dashboardData.getPlayzoneData());
        ProfileFragment.this._yourTacticsStatsController.setTacticsData(dashboardData.getTacticsData());
        this.notifyLoadingCompleted();
    }
}
