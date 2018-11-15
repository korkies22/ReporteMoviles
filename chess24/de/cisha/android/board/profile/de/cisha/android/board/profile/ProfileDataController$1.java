/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.profile;

import de.cisha.android.board.LoadingHelper;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.user.User;
import java.util.List;
import org.json.JSONObject;

class ProfileDataController
implements LoadCommandCallback<User> {
    ProfileDataController() {
    }

    private void notifyLoadingCompleted() {
        ProfileDataController.this._loadingHelper.loadingCompleted(ProfileDataController.this);
    }

    @Override
    public void loadingCancelled() {
    }

    @Override
    public void loadingFailed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        this.notifyLoadingCompleted();
    }

    @Override
    public void loadingSucceded(User user) {
        this.notifyLoadingCompleted();
    }
}
