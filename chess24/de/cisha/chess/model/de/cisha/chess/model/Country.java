/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 */
package de.cisha.chess.model;

import android.content.res.Resources;
import java.util.Currency;
import java.util.Locale;

public interface Country {
    public String getAlpha2();

    public String getAlpha3();

    public Currency getCurrency();

    public String getDisplayName(Resources var1);

    public String getIOC();

    public int getImageId();

    public int getNumeric();

    public Locale toLocale();
}
