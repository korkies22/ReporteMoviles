// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.account.model;

import java.util.Date;

public class AfterPurchaseInformation
{
    private long _millisecondsExtended;
    private Date _validUntil;
    
    public AfterPurchaseInformation(final long millisecondsExtended, final Date validUntil) {
        this._millisecondsExtended = millisecondsExtended;
        this._validUntil = validUntil;
    }
    
    public long getMillisecondsExtended() {
        return this._millisecondsExtended;
    }
    
    public Date getValidUntil() {
        return this._validUntil;
    }
}
