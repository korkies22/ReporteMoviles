// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service.jsonparser;

import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;
import de.cisha.android.board.service.IWebSettingsService;
import java.util.Map;

public class JSONGetSettingsParser extends JSONAPIResultParser<Map<IWebSettingsService.PrivacySetting, IWebSettingsService.PrivacySetting.PrivacyValue>>
{
    @Override
    public Map<IWebSettingsService.PrivacySetting, IWebSettingsService.PrivacySetting.PrivacyValue> parseResult(final JSONObject jsonObject) throws InvalidJsonForObjectException {
        final HashMap<IWebSettingsService.PrivacySetting, IWebSettingsService.PrivacySetting.PrivacyValue> hashMap = new HashMap<IWebSettingsService.PrivacySetting, IWebSettingsService.PrivacySetting.PrivacyValue>();
        final IWebSettingsService.PrivacySetting[] values = IWebSettingsService.PrivacySetting.values();
        for (int i = 0; i < values.length; ++i) {
            final IWebSettingsService.PrivacySetting privacySetting = values[i];
            hashMap.put(privacySetting, IWebSettingsService.PrivacySetting.PrivacyValue.fromApiString(jsonObject.optString(privacySetting.getApiString(), "")));
        }
        return hashMap;
    }
}
