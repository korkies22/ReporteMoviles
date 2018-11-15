/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import de.cisha.chess.model.CishaUUID;

public class UserLoginData {
    private CishaUUID _authToken;
    private CishaUUID _uuid;

    public UserLoginData(CishaUUID cishaUUID, CishaUUID cishaUUID2) {
        this._authToken = cishaUUID;
        this._uuid = cishaUUID2;
    }

    public CishaUUID getAuthToken() {
        return this._authToken;
    }

    public CishaUUID getUserId() {
        return this._uuid;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UserData - authToken: ");
        stringBuilder.append(this._authToken);
        stringBuilder.append(" uuid: ");
        stringBuilder.append(this._uuid);
        return stringBuilder.toString();
    }
}
