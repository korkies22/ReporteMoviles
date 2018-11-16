// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import java.util.TreeMap;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.android.board.service.jsonparser.JSONGetSettingsParser;
import de.cisha.android.board.service.jsonparser.GeneralJSONAPICommandLoader;
import java.util.Map;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;

public class WebSettingsService implements IWebSettingsService
{
    private static WebSettingsService _instance;
    
    public static WebSettingsService getInstance() {
        if (WebSettingsService._instance == null) {
            WebSettingsService._instance = new WebSettingsService();
        }
        return WebSettingsService._instance;
    }
    
    @Override
    public void getPrivacySettings(final LoadCommandCallback<Map<PrivacySetting, PrivacyValue>> loadCommandCallback) {
        new GeneralJSONAPICommandLoader<Map<PrivacySetting, PrivacyValue>>().loadApiCommandGet(loadCommandCallback, "mobileAPI/GetSettings", null, new JSONGetSettingsParser(), true);
    }
    
    @Override
    public void setPrivacySetting(final PrivacySetting privacySetting, final PrivacyValue privacyValue, final LoadCommandCallback<Void> loadCommandCallback) {
        final GeneralJSONAPICommandLoader<Void> generalJSONAPICommandLoader = new GeneralJSONAPICommandLoader<Void>();
        final TreeMap<String, String> treeMap = new TreeMap<String, String>();
        treeMap.put(privacySetting.getApiString(), privacyValue.getApiString());
        generalJSONAPICommandLoader.loadApiCommandPost(loadCommandCallback, "mobileAPI/SetSettings", treeMap, null, true);
    }
}
