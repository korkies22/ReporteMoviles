// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model;

import java.util.Locale;
import android.content.res.Resources;
import java.util.Currency;

public interface Country
{
    String getAlpha2();
    
    String getAlpha3();
    
    Currency getCurrency();
    
    String getDisplayName(final Resources p0);
    
    String getIOC();
    
    int getImageId();
    
    int getNumeric();
    
    Locale toLocale();
}
