/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.service.jsonparser;

import de.cisha.android.board.service.IWebSettingsService;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONGetSettingsParser
extends JSONAPIResultParser<Map<IWebSettingsService.PrivacySetting, IWebSettingsService.PrivacySetting.PrivacyValue>> {
    @Override
    public Map<IWebSettingsService.PrivacySetting, IWebSettingsService.PrivacySetting.PrivacyValue> parseResult(JSONObject jSONObject) throws InvalidJsonForObjectException {
        HashMap<IWebSettingsService.PrivacySetting, IWebSettingsService.PrivacySetting.PrivacyValue> hashMap = new HashMap<IWebSettingsService.PrivacySetting, IWebSettingsService.PrivacySetting.PrivacyValue>();
        for (IWebSettingsService.PrivacySetting privacySetting : IWebSettingsService.PrivacySetting.values()) {
            hashMap.put(privacySetting, IWebSettingsService.PrivacySetting.PrivacyValue.fromApiString(jSONObject.optString(privacySetting.getApiString(), "")));
        }
        return hashMap;
    }
}
