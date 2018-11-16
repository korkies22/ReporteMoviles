// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import de.cisha.chess.model.Country;
import java.util.List;

public interface ICountryService
{
    List<Country> getAllCountries();
    
    Country getCountryForString(final String p0);
}
