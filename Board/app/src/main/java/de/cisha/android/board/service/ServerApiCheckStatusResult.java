// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import java.util.Date;

public class ServerApiCheckStatusResult implements ServerAPIStatus
{
    private boolean _deprecated;
    private Date _runsUntil;
    private String _serverBaseURL;
    private boolean _valid;
    
    public ServerApiCheckStatusResult(final boolean valid, final boolean deprecated, final Date runsUntil, final String serverBaseURL) {
        this._valid = valid;
        this._deprecated = deprecated;
        this._runsUntil = runsUntil;
        this._serverBaseURL = serverBaseURL;
    }
    
    @Override
    public Date getDeprecationDate() {
        return this._runsUntil;
    }
    
    public String getServerBaseURL() {
        return this._serverBaseURL;
    }
    
    @Override
    public boolean isDeprecated() {
        return this._deprecated;
    }
    
    @Override
    public boolean isValid() {
        return this._valid;
    }
}
