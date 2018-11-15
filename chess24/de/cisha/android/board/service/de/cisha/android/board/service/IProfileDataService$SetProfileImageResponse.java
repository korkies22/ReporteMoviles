/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import de.cisha.android.board.service.IProfileDataService;
import de.cisha.chess.model.CishaUUID;
import java.net.URL;

public static class IProfileDataService.SetProfileImageResponse {
    private CishaUUID _couchId;
    private String _revision;
    private URL _url;

    public IProfileDataService.SetProfileImageResponse(CishaUUID cishaUUID, URL uRL, String string) {
        this._couchId = cishaUUID;
        this._url = uRL;
        this._revision = string;
    }

    public CishaUUID getCouchId() {
        return this._couchId;
    }

    public String getRevision() {
        return this._revision;
    }

    public URL getUrl() {
        return this._url;
    }
}
