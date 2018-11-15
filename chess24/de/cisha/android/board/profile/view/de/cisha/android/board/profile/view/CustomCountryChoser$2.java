/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemSelectedListener
 */
package de.cisha.android.board.profile.view;

import android.view.View;
import android.widget.AdapterView;
import com.neovisionaries.i18n.CountryCode;
import de.cisha.android.board.profile.view.CustomCountryChoser;
import de.cisha.chess.model.Country;

class CustomCountryChoser
implements AdapterView.OnItemSelectedListener {
    CustomCountryChoser() {
    }

    public void onItemSelected(AdapterView<?> object, View view, int n, long l) {
        object = CustomCountryChoser.this._countryAdapter.getItem(n);
        CustomCountryChoser.this._currentCountry = object;
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
        CustomCountryChoser.this._currentCountry = null;
    }
}
