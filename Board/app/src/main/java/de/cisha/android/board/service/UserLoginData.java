// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import de.cisha.chess.model.CishaUUID;

public class UserLoginData
{
    private CishaUUID _authToken;
    private CishaUUID _uuid;
    
    public UserLoginData(final CishaUUID authToken, final CishaUUID uuid) {
        this._authToken = authToken;
        this._uuid = uuid;
    }
    
    public CishaUUID getAuthToken() {
        return this._authToken;
    }
    
    public CishaUUID getUserId() {
        return this._uuid;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("UserData - authToken: ");
        sb.append(this._authToken);
        sb.append(" uuid: ");
        sb.append(this._uuid);
        return sb.toString();
    }
}
