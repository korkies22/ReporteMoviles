/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.profile;

import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.user.User;
import java.util.List;
import org.json.JSONObject;

class ProfileEditorFragment
extends LoadCommandCallbackWithTimeout<User> {
    ProfileEditorFragment() {
    }

    @Override
    protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        ProfileEditorFragment.this.hideDialogWaiting();
        ProfileEditorFragment.this.showViewForErrorCode(aPIStatusCode, new IErrorPresenter.IReloadAction(){

            @Override
            public void performReload() {
                ProfileEditorFragment.this.loadProfileData();
            }
        }, true);
    }

    @Override
    protected void succeded(User user) {
        ProfileEditorFragment.this.hideDialogWaiting();
        ProfileEditorFragment.this._user = user;
        ProfileEditorFragment.this.updateEditFields();
        ProfileEditorFragment.this.updatePremium();
    }

}
