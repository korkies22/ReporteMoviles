/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  org.json.JSONObject
 */
package de.cisha.android.board.profile;

import android.view.View;
import de.cisha.android.board.profile.EditEmailDialogFragment;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.ui.patterns.input.CustomEditText;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

class ProfileEditorFragment
extends LoadCommandCallbackWithTimeout<Map<String, String>> {
    final /* synthetic */ String val$email;

    ProfileEditorFragment(String string) {
        this.val$email = string;
    }

    @Override
    protected void failed(APIStatusCode aPIStatusCode, String string, final List<LoadFieldError> list, JSONObject jSONObject) {
        ProfileEditorFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                ProfileEditorFragment.this.getView().post(new Runnable(){

                    @Override
                    public void run() {
                        ProfileEditorFragment.this.hideDialogWaiting();
                        if (ProfileEditorFragment.this._dialogEditEmail != null) {
                            ProfileEditorFragment.this._dialogEditEmail.setErrors(list);
                        }
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
                if (ProfileEditorFragment.this._dialogEditEmail != null && ProfileEditorFragment.this._dialogEditEmail.isResumed()) {
                    ProfileEditorFragment.this._dialogEditEmail.dismiss();
                }
                ProfileEditorFragment.this.hideDialogWaiting();
                ProfileEditorFragment.this._emailField.setText(14.this.val$email);
            }
        });
    }

}
