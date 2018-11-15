/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import de.cisha.android.board.service.IWebSettingsService;
import de.cisha.android.board.service.jsonparser.GeneralJSONAPICommandLoader;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.android.board.service.jsonparser.JSONGetSettingsParser;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import java.util.Map;
import java.util.TreeMap;

public class WebSettingsService
implements IWebSettingsService {
    private static WebSettingsService _instance;

    private WebSettingsService() {
    }

    public static WebSettingsService getInstance() {
        if (_instance == null) {
            _instance = new WebSettingsService();
        }
        return _instance;
    }

    @Override
    public void getPrivacySettings(LoadCommandCallback<Map<IWebSettingsService.PrivacySetting, IWebSettingsService.PrivacySetting.PrivacyValue>> loadCommandCallback) {
        new GeneralJSONAPICommandLoader<Map<IWebSettingsService.PrivacySetting, IWebSettingsService.PrivacySetting.PrivacyValue>>().loadApiCommandGet(loadCommandCallback, "mobileAPI/GetSettings", null, new JSONGetSettingsParser(), true);
    }

    @Override
    public void setPrivacySetting(IWebSettingsService.PrivacySetting privacySetting, IWebSettingsService.PrivacySetting.PrivacyValue privacyValue, LoadCommandCallback<Void> loadCommandCallback) {
        GeneralJSONAPICommandLoader<Void> generalJSONAPICommandLoader = new GeneralJSONAPICommandLoader<Void>();
        TreeMap<String, String> treeMap = new TreeMap<String, String>();
        treeMap.put(privacySetting.getApiString(), privacyValue.getApiString());
        generalJSONAPICommandLoader.loadApiCommandPost(loadCommandCallback, "mobileAPI/SetSettings", treeMap, null, true);
    }
}
