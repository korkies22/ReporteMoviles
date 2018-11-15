/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.widget.Toast
 *  org.json.JSONObject
 */
package de.cisha.android.board.profile;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;
import de.cisha.android.board.profile.PrivacySettingsController;
import de.cisha.android.board.service.IWebSettingsService;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import org.json.JSONObject;

class ProfileEditorFragment
extends LoadCommandCallbackWithTimeout<Void> {
    final /* synthetic */ IWebSettingsService.PrivacySetting.PrivacyValue val$newValue;
    final /* synthetic */ IWebSettingsService.PrivacySetting val$setting;

    ProfileEditorFragment(IWebSettingsService.PrivacySetting privacySetting, IWebSettingsService.PrivacySetting.PrivacyValue privacyValue) {
        this.val$setting = privacySetting;
        this.val$newValue = privacyValue;
    }

    @Override
    protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        ProfileEditorFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                if (ProfileEditorFragment.this.isResumed()) {
                    Toast.makeText((Context)ProfileEditorFragment.this.getActivity(), (CharSequence)"set setting failed", (int)0).show();
                }
                ProfileEditorFragment.this.hideDialogWaiting();
                ProfileEditorFragment.this._privacySettingsController.resetPrivacySetting(15.this.val$setting);
            }
        });
    }

    @Override
    protected void succeded(Void void_) {
        ProfileEditorFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                ProfileEditorFragment.this._privacySettingsController.updatePrivacySetting(15.this.val$setting, 15.this.val$newValue);
                ProfileEditorFragment.this.hideDialogWaiting();
            }
        });
    }

}
