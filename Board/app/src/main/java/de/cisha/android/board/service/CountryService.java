// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import de.cisha.chess.model.CountryImpl;
import java.util.ArrayList;
import com.neovisionaries.i18n.CountryCode;
import de.cisha.chess.model.Country;
import java.util.List;

public class CountryService implements ICountryService
{
    private static CountryService _instance;
    
    public static ICountryService getInstance() {
        synchronized (CountryService.class) {
            if (CountryService._instance == null) {
                CountryService._instance = new CountryService();
            }
            return CountryService._instance;
        }
    }
    
    @Override
    public List<Country> getAllCountries() {
        final CountryCode[] values = CountryCode.values();
        int i = 0;
        final ArrayList list = new ArrayList<CountryCode>(values.length);
        for (CountryCode[] values2 = CountryCode.values(); i < values2.length; ++i) {
            list.add(values2[i]);
        }
        return (List<Country>)list;
    }
    
    @Override
    public Country getCountryForString(final String s) {
        Country byCode;
        final CountryCode countryCode = (CountryCode)(byCode = CountryCode.getByCode(s));
        if (countryCode == null) {
            byCode = countryCode;
            if (s != null) {
                byCode = new CountryImpl(s, s, s, 0, 2131231366);
            }
        }
        return byCode;
    }
}
