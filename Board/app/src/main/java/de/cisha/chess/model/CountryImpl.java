// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model;

import java.util.Locale;
import android.content.res.Resources;
import java.util.Currency;

public class CountryImpl extends BaseObject implements Country
{
    private int _imageId;
    private String _iocCode;
    private String _isoAlpha2;
    private String _isoAlpha3;
    private int _isoNumeric;
    
    public CountryImpl(final String isoAlpha2, final String isoAlpha3, final String iocCode, final int isoNumeric, final int imageId) {
        this._imageId = imageId;
        this._isoNumeric = isoNumeric;
        this._iocCode = iocCode;
        this._isoAlpha3 = isoAlpha3;
        this._isoAlpha2 = isoAlpha2;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof CountryImpl && this.equals(((CountryImpl)o).getAlpha2(), this._isoAlpha2);
    }
    
    @Override
    public String getAlpha2() {
        return this._isoAlpha2;
    }
    
    @Override
    public String getAlpha3() {
        return this._isoAlpha3;
    }
    
    @Override
    public Currency getCurrency() {
        try {
            return Currency.getInstance(this.toLocale());
        }
        catch (IllegalArgumentException ex) {
            return null;
        }
    }
    
    @Override
    public String getDisplayName(final Resources resources) {
        final String displayCountry = this.toLocale().getDisplayCountry(Locale.getDefault());
        String alpha3 = null;
        Label_0034: {
            if (displayCountry != null) {
                alpha3 = displayCountry;
                if (!"".equals(displayCountry.trim())) {
                    break Label_0034;
                }
            }
            alpha3 = this.getAlpha3();
        }
        if (alpha3 != null) {
            final String alpha4 = alpha3;
            if (!"".equals(alpha3.trim())) {
                return alpha4;
            }
        }
        return this.getAlpha2();
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
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.toLocale().getDisplayCountry(Locale.getDefault()));
        sb.append(" (");
        sb.append(this.getAlpha2());
        sb.append(")");
        return sb.toString();
    }
}
