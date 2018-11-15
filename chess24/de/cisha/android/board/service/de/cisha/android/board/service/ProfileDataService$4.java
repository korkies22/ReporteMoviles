/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Bitmap
 *  org.json.JSONObject
 */
package de.cisha.android.board.service;

import android.content.Context;
import android.graphics.Bitmap;
import de.cisha.android.board.service.CouchImageCache;
import de.cisha.android.board.service.IProfileDataService;
import de.cisha.android.board.service.ITrackingService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWrapper;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.user.User;
import de.cisha.chess.model.CishaUUID;
import java.util.List;
import org.json.JSONObject;

class ProfileDataService
extends LoadCommandCallbackWrapper<IProfileDataService.SetProfileImageResponse> {
    final /* synthetic */ Bitmap val$image;

    ProfileDataService(LoadCommandCallback loadCommandCallback, Bitmap bitmap) {
        this.val$image = bitmap;
        super(loadCommandCallback);
    }

    @Override
    public void loadingFailed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        synchronized (this) {
            super.loadingFailed(aPIStatusCode, string, list, jSONObject);
            ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.PROFILE, de.cisha.android.board.service.ProfileDataService.UPLOAD_PROFILE_IMAGE_TRACKING_ACTION, de.cisha.android.board.service.ProfileDataService.ERROR);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void loadingSucceded(IProfileDataService.SetProfileImageResponse setProfileImageResponse) {
        synchronized (this) {
            super.loadingSucceded(setProfileImageResponse);
            if (ProfileDataService.this._user != null) {
                ProfileDataService.this._user.setProfileImageCouchId(setProfileImageResponse.getCouchId());
                String string = setProfileImageResponse.getRevision();
                if (string != null && string.length() > 0) {
                    CouchImageCache.getInstance(_context).makeCouchImageCacheEntry(setProfileImageResponse.getCouchId(), string, 300, this.val$image);
                }
                ProfileDataService.this.storeUserDataInPreferences();
                ProfileDataService.this.notifyUserDataChanged();
            }
            ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.PROFILE, de.cisha.android.board.service.ProfileDataService.UPLOAD_PROFILE_IMAGE_TRACKING_ACTION, de.cisha.android.board.service.ProfileDataService.SUCCESS);
            return;
        }
    }
}
