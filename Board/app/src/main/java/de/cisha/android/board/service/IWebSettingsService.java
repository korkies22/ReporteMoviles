// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import java.util.Map;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;

public interface IWebSettingsService
{
    void getPrivacySettings(final LoadCommandCallback<Map<PrivacySetting, PrivacyValue>> p0);
    
    void setPrivacySetting(final PrivacySetting p0, final PrivacyValue p1, final LoadCommandCallback<Void> p2);
    
    public enum PrivacySetting
    {
        BIRTHDATE(2131690186, "privacy-birthdate", PrivacyVisibiltyArea.WEB), 
        COUNTRY(2131690187, "privacy-federation", PrivacyVisibiltyArea.WEB_AND_MOBILE), 
        EMAIL(2131690188, "privacy-email", PrivacyVisibiltyArea.WEB), 
        FRIENDSLIST(2131690189, "privacy-friendslist", PrivacyVisibiltyArea.WEB), 
        ONLINE(2131690190, "privacy-online", PrivacyVisibiltyArea.WEB), 
        REALNAME(2131690191, "privacy-realname", PrivacyVisibiltyArea.WEB_AND_MOBILE);
        
        private PrivacyVisibiltyArea _affectedAreas;
        private String _apiString;
        private int _resIdName;
        
        private PrivacySetting(final int resIdName, final String apiString, final PrivacyVisibiltyArea affectedAreas) {
            this._resIdName = resIdName;
            this._apiString = apiString;
            this._affectedAreas = affectedAreas;
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
        
        public enum PrivacyValue
        {
            EVERYONE("everyone"), 
            FRIENDS("friends"), 
            HIDDEN("hidden");
            
            private String _apiString;
            
            private PrivacyValue(final String apiString) {
                this._apiString = apiString;
            }
            
            public static PrivacyValue fromApiString(final String s) {
                final PrivacyValue[] values = values();
                for (int i = 0; i < values.length; ++i) {
                    final PrivacyValue privacyValue = values[i];
                    if (privacyValue.getApiString().equals(s)) {
                        return privacyValue;
                    }
                }
                return PrivacyValue.HIDDEN;
            }
            
            public String getApiString() {
                return this._apiString;
            }
        }
        
        public enum PrivacyVisibiltyArea
        {
            MOBILE(2131690192), 
            WEB(2131690193), 
            WEB_AND_MOBILE(2131690194);
            
            private int _stringResourceId;
            
            private PrivacyVisibiltyArea(final int stringResourceId) {
                this._stringResourceId = stringResourceId;
            }
            
            public int getStringId() {
                return this._stringResourceId;
            }
        }
    }
}
