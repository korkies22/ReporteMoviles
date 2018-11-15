/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import com.neovisionaries.i18n.CountryCode;
import de.cisha.android.board.service.ICountryService;
import de.cisha.chess.model.Country;
import de.cisha.chess.model.CountryImpl;
import java.util.ArrayList;
import java.util.List;

public class CountryService
implements ICountryService {
    private static CountryService _instance;

    private CountryService() {
    }

    public static ICountryService getInstance() {
        synchronized (CountryService.class) {
            if (_instance == null) {
                _instance = new CountryService();
            }
            CountryService countryService = _instance;
            return countryService;
        }
    }

    @Override
    public List<Country> getAllCountries() {
        Object object = CountryCode.values();
        object = new ArrayList(((CountryCode[])object).length);
        CountryCode[] arrcountryCode = CountryCode.values();
        int n = arrcountryCode.length;
        for (int i = 0; i < n; ++i) {
            object.add(arrcountryCode[i]);
        }
        return object;
    }

    @Override
    public Country getCountryForString(String string) {
        CountryCode countryCode;
        Country country = countryCode = CountryCode.getByCode(string);
        if (countryCode == null) {
            country = countryCode;
            if (string != null) {
                country = new CountryImpl(string, string, string, 0, 2131231366);
            }
        }
        return country;
    }
}
