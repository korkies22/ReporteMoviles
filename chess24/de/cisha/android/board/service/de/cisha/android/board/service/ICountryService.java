/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import de.cisha.chess.model.Country;
import java.util.List;

public interface ICountryService {
    public List<Country> getAllCountries();

    public Country getCountryForString(String var1);
}
