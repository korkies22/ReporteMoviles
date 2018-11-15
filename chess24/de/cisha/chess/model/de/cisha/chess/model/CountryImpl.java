/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 */
package de.cisha.chess.model;

import android.content.res.Resources;
import de.cisha.chess.model.BaseObject;
import de.cisha.chess.model.Country;
import java.util.Currency;
import java.util.Locale;

public class CountryImpl
extends BaseObject
implements Country {
    private int _imageId;
    private String _iocCode;
    private String _isoAlpha2;
    private String _isoAlpha3;
    private int _isoNumeric;

    public CountryImpl(String string, String string2, String string3, int n, int n2) {
        this._imageId = n2;
        this._isoNumeric = n;
        this._iocCode = string3;
        this._isoAlpha3 = string2;
        this._isoAlpha2 = string;
    }

    public boolean equals(Object object) {
        if (!(object instanceof CountryImpl)) {
            return false;
        }
        return this.equals(((CountryImpl)object).getAlpha2(), this._isoAlpha2);
    }

    @Override
    public String getAlpha2() {
        return this._isoAlpha2;
    }

    @Override
    public String getAlpha3() {
        return this._isoAlpha3;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public Currency getCurrency() {
        try {
            return Currency.getInstance(this.toLocale());
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return null;
        }
    }

    @Override
    public String getDisplayName(Resources object) {
        String string;
        block7 : {
            block6 : {
                block5 : {
                    block4 : {
                        string = this.toLocale().getDisplayCountry(Locale.getDefault());
                        if (string == null) break block4;
                        object = string;
                        if (!"".equals(string.trim())) break block5;
                    }
                    object = this.getAlpha3();
                }
                if (object == null) break block6;
                string = object;
                if (!"".equals(object.trim())) break block7;
            }
            string = this.getAlpha2();
        }
        return string;
    }

    @Override
    public String getIOC() {
        return this._iocCode;
    }

    @Override
    public int getImageId() {
        return this._imageId;
    }

    @Override
    public int getNumeric() {
        return this._isoNumeric;
    }

    @Override
    public Locale toLocale() {
        return new Locale(this.getAlpha2(), this.getAlpha2());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.toLocale().getDisplayCountry(Locale.getDefault()));
        stringBuilder.append(" (");
        stringBuilder.append(this.getAlpha2());
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
