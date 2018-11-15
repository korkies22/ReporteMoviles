/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board;

import java.net.URL;

public class CouchUrl {
    private String _rev;
    private URL _url;

    public CouchUrl(URL uRL, String string) {
        this._url = uRL;
        this._rev = string;
    }

    public String getRevision() {
        return this._rev;
    }

    public URL getUrl() {
        return this._url;
    }
}
