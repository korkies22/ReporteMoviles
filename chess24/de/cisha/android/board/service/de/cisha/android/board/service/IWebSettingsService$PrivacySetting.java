/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import de.cisha.android.board.service.IWebSettingsService;

public static enum IWebSettingsService.PrivacySetting {
    REALNAME(2131690191, "privacy-realname", PrivacyVisibiltyArea.WEB_AND_MOBILE),
    EMAIL(2131690188, "privacy-email", PrivacyVisibiltyArea.WEB),
    COUNTRY(2131690187, "privacy-federation", PrivacyVisibiltyArea.WEB_AND_MOBILE),
    BIRTHDATE(2131690186, "privacy-birthdate", PrivacyVisibiltyArea.WEB),
    ONLINE(2131690190, "privacy-online", PrivacyVisibiltyArea.WEB),
    FRIENDSLIST(2131690189, "privacy-friendslist", PrivacyVisibiltyArea.WEB);
    
    private PrivacyVisibiltyArea _affectedAreas;
    private String _apiString;
    private int _resIdName;

    private IWebSettingsService.PrivacySetting(int n2, String string2, PrivacyVisibiltyArea privacyVisibiltyArea) {
        this._resIdName = n2;
        this._apiString = string2;
        this._affectedAreas = privacyVisibiltyArea;
    }

    public String getApiString() {
        return this._apiString;
    }

    public int getNameResId() {
        return this._resIdName;
    }

    public int getShortDescriptionResId() {
        return this._affectedAreas.getStringId();
    }

    public static enum PrivacyValue {
        HIDDEN("hidden"),
        FRIENDS("friends"),
        EVERYONE("everyone");
        
        private String _apiString;

        private PrivacyValue(String string2) {
            this._apiString = string2;
        }

        public static PrivacyValue fromApiString(String string) {
            for (PrivacyValue privacyValue : PrivacyValue.values()) {
                if (!privacyValue.getApiString().equals(string)) continue;
                return privacyValue;
            }
            return HIDDEN;
        }

        public String getApiString() {
            return this._apiString;
        }
    }

    public static enum PrivacyVisibiltyArea {
        WEB(2131690193),
        MOBILE(2131690192),
        WEB_AND_MOBILE(2131690194);
        
        private int _stringResourceId;

        private PrivacyVisibiltyArea(int n2) {
            this._stringResourceId = n2;
        }

        public int getStringId() {
            return this._stringResourceId;
        }
    }

}
