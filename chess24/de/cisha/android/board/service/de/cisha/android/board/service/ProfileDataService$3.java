/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.service;

import de.cisha.android.board.service.ITrackingService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWrapper;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.user.User;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

class ProfileDataService
extends LoadCommandCallbackWrapper<Map<String, String>> {
    ProfileDataService(LoadCommandCallback loadCommandCallback) {
        super(loadCommandCallback);
    }

    @Override
    protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        super.failed(aPIStatusCode, string, list, jSONObject);
        ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.PROFILE, de.cisha.android.board.service.ProfileDataService.EDIT_PROFILE_IMAGE_TRACKING_ACTION, de.cisha.android.board.service.ProfileDataService.ERROR);
    }

    @Override
    protected void succeded(Map<String, String> map) {
        ProfileDataService.this.getUserData(null);
        super.succeded(map);
        ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.PROFILE, de.cisha.android.board.service.ProfileDataService.EDIT_PROFILE_IMAGE_TRACKING_ACTION, de.cisha.android.board.service.ProfileDataService.SUCCESS);
    }
}
