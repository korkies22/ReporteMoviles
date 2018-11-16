// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board;

import java.net.URL;

public class CouchUrl
{
    private String _rev;
    private URL _url;
    
    public CouchUrl(final URL url, final String rev) {
        this._url = url;
        this._rev = rev;
    }
    
    public String getRevision() {
        return this._rev;
    }
    
    public URL getUrl() {
        return this._url;
    }
}
