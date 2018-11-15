/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import de.cisha.android.board.service.IWebSettingsService;

public static enum IWebSettingsService.PrivacySetting.PrivacyVisibiltyArea {
    WEB(2131690193),
    MOBILE(2131690192),
    WEB_AND_MOBILE(2131690194);
    
    private int _stringResourceId;

    private IWebSettingsService.PrivacySetting.PrivacyVisibiltyArea(int n2) {
        this._stringResourceId = n2;
    }

    public int getStringId() {
        return this._stringResourceId;
    }
}
