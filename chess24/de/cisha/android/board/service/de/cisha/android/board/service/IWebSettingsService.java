/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import java.util.Map;

public interface IWebSettingsService {
    public void getPrivacySettings(LoadCommandCallback<Map<PrivacySetting, PrivacySetting.PrivacyValue>> var1);

    public void setPrivacySetting(PrivacySetting var1, PrivacySetting.PrivacyValue var2, LoadCommandCallback<Void> var3);

    public static enum PrivacySetting {
        REALNAME(2131690191, "privacy-realname", PrivacySetting$PrivacyVisibiltyArea.WEB_AND_MOBILE),
        EMAIL(2131690188, "privacy-email", PrivacySetting$PrivacyVisibiltyArea.WEB),
        COUNTRY(2131690187, "privacy-federation", PrivacySetting$PrivacyVisibiltyArea.WEB_AND_MOBILE),
        BIRTHDATE(2131690186, "privacy-birthdate", PrivacySetting$PrivacyVisibiltyArea.WEB),
        ONLINE(2131690190, "privacy-online", PrivacySetting$PrivacyVisibiltyArea.WEB),
        FRIENDSLIST(2131690189, "privacy-friendslist", PrivacySetting$PrivacyVisibiltyArea.WEB);
        
        private PrivacySetting$PrivacyVisibiltyArea _affectedAreas;
        private String _apiString;
        private int _resIdName;

        private PrivacySetting(int n2, String string2, PrivacySetting$PrivacyVisibiltyArea privacySetting$PrivacyVisibiltyArea) {
            this._resIdName = n2;
            this._apiString = string2;
            this._affectedAreas = privacySetting$PrivacyVisibiltyArea;
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

    }

    public static enum PrivacySetting$PrivacyVisibiltyArea {
        WEB(2131690193),
        MOBILE(2131690192),
        WEB_AND_MOBILE(2131690194);
        
        private int _stringResourceId;

        private PrivacySetting$PrivacyVisibiltyArea(int n2) {
            this._stringResourceId = n2;
        }

        public int getStringId() {
            return this._stringResourceId;
        }
    }

}
