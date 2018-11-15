/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.profile;

import de.cisha.android.board.service.IWebSettingsService;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

class ProfileEditorFragment
extends LoadCommandCallbackWithTimeout<Map<IWebSettingsService.PrivacySetting, IWebSettingsService.PrivacySetting.PrivacyValue>> {
    ProfileEditorFragment() {
    }

    @Override
    protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
    }

    @Override
    protected void succeded(Map<IWebSettingsService.PrivacySetting, IWebSettingsService.PrivacySetting.PrivacyValue> map) {
        ProfileEditorFragment.this._privacyMap = map;
        ProfileEditorFragment.this.setPrivacyValues();
    }
}
