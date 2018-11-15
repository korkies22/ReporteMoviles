/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  org.json.JSONObject
 */
package de.cisha.android.board.profile;

import android.view.View;
import de.cisha.android.board.profile.EditPasswordDialogFragment;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

class ProfileEditorFragment
extends LoadCommandCallbackWithTimeout<Map<String, String>> {
    ProfileEditorFragment() {
    }

    @Override
    protected void failed(APIStatusCode aPIStatusCode, String string, final List<LoadFieldError> list, JSONObject jSONObject) {
        ProfileEditorFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                ProfileEditorFragment.this.getView().post(new Runnable(){

                    @Override
                    public void run() {
                        if (ProfileEditorFragment.this._dialogEditPassword != null && list != null) {
                            ProfileEditorFragment.this._dialogEditPassword.setErrors(list);
                        }
                        ProfileEditorFragment.this.hideDialogWaiting();
                    }
                });
            }

        });
    }

    @Override
    protected void succeded(Map<String, String> map) {
        ProfileEditorFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                ProfileEditorFragment.this.hideDialogWaiting();
                if (ProfileEditorFragment.this._dialogEditPassword != null && ProfileEditorFragment.this._dialogEditPassword.isResumed()) {
                    ProfileEditorFragment.this._dialogEditPassword.dismiss();
                }
            }
        });
    }

}
