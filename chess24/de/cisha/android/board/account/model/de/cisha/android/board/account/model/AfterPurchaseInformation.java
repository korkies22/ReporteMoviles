/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.account.model;

import java.util.Date;

public class AfterPurchaseInformation {
    private long _millisecondsExtended;
    private Date _validUntil;

    public AfterPurchaseInformation(long l, Date date) {
        this._millisecondsExtended = l;
        this._validUntil = date;
    }

    public long getMillisecondsExtended() {
        return this._millisecondsExtended;
    }

    public Date getValidUntil() {
        return this._validUntil;
    }
}
