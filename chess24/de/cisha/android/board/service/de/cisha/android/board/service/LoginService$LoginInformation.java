/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import de.cisha.android.board.service.LoginService;
import de.cisha.chess.model.CishaUUID;

private static class LoginService.LoginInformation {
    private CishaUUID _authToken;
    private CishaUUID _lastGuestAuthToken;
    private CishaUUID _lastGuestUuid;
    private CishaUUID _uuid;

    public LoginService.LoginInformation() {
        this.resetUser();
    }

    static /* synthetic */ CishaUUID access$100(LoginService.LoginInformation loginInformation) {
        return loginInformation._authToken;
    }

    static /* synthetic */ CishaUUID access$102(LoginService.LoginInformation loginInformation, CishaUUID cishaUUID) {
        loginInformation._authToken = cishaUUID;
        return cishaUUID;
    }

    static /* synthetic */ CishaUUID access$300(LoginService.LoginInformation loginInformation) {
        return loginInformation._uuid;
    }

    static /* synthetic */ CishaUUID access$302(LoginService.LoginInformation loginInformation, CishaUUID cishaUUID) {
        loginInformation._uuid = cishaUUID;
        return cishaUUID;
    }

    static /* synthetic */ CishaUUID access$400(LoginService.LoginInformation loginInformation) {
        return loginInformation._lastGuestAuthToken;
    }

    static /* synthetic */ CishaUUID access$402(LoginService.LoginInformation loginInformation, CishaUUID cishaUUID) {
        loginInformation._lastGuestAuthToken = cishaUUID;
        return cishaUUID;
    }

    static /* synthetic */ CishaUUID access$500(LoginService.LoginInformation loginInformation) {
        return loginInformation._lastGuestUuid;
    }

    static /* synthetic */ CishaUUID access$502(LoginService.LoginInformation loginInformation, CishaUUID cishaUUID) {
        loginInformation._lastGuestUuid = cishaUUID;
        return cishaUUID;
    }

    public void resetGuest() {
        this._lastGuestAuthToken = null;
        this._lastGuestUuid = null;
    }

    public void resetUser() {
        this._uuid = null;
        this._authToken = null;
    }
}
