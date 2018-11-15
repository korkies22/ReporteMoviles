/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import de.cisha.android.board.service.ServerAPIStatus;
import java.util.Date;

public class ServerApiCheckStatusResult
implements ServerAPIStatus {
    private boolean _deprecated;
    private Date _runsUntil;
    private String _serverBaseURL;
    private boolean _valid;

    public ServerApiCheckStatusResult(boolean bl, boolean bl2, Date date, String string) {
        this._valid = bl;
        this._deprecated = bl2;
        this._runsUntil = date;
        this._serverBaseURL = string;
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
