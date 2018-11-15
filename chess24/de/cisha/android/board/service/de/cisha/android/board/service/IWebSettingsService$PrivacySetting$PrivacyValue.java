/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import de.cisha.android.board.service.IWebSettingsService;

public static enum IWebSettingsService.PrivacySetting.PrivacyValue {
    HIDDEN("hidden"),
    FRIENDS("friends"),
    EVERYONE("everyone");
    
    private String _apiString;

    private IWebSettingsService.PrivacySetting.PrivacyValue(String string2) {
        this._apiString = string2;
    }

    public static IWebSettingsService.PrivacySetting.PrivacyValue fromApiString(String string) {
        for (IWebSettingsService.PrivacySetting.PrivacyValue privacyValue : IWebSettingsService.PrivacySetting.PrivacyValue.values()) {
            if (!privacyValue.getApiString().equals(string)) continue;
            return privacyValue;
        }
        return HIDDEN;
    }

    public String getApiString() {
        return this._apiString;
    }
}
