/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemSelectedListener
 *  android.widget.BaseAdapter
 *  android.widget.Spinner
 *  android.widget.SpinnerAdapter
 */
package de.cisha.android.board.profile.view;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import com.neovisionaries.i18n.CountryCode;
import de.cisha.android.board.profile.view.CountryRowView;
import de.cisha.android.ui.patterns.input.CustomSpinner;
import de.cisha.chess.model.Country;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CustomCountryChoser
extends CustomSpinner {
    private List<CountryCode> _allCountries;
    private int _baseCountryCount;
    private CountryAdapter _countryAdapter;
    private Country _currentCountry;

    public CustomCountryChoser(Context context) {
        super(context);
        this.init();
    }

    public CustomCountryChoser(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    private void init() {
        this._allCountries = new ArrayList<CountryCode>();
        this._allCountries.add(CountryCode.RU);
        this._allCountries.add(CountryCode.UA);
        this._allCountries.add(CountryCode.CN);
        this._allCountries.add(CountryCode.IN);
        this._allCountries.add(CountryCode.US);
        this._allCountries.add(CountryCode.ES);
        this._allCountries.add(CountryCode.GB);
        this._allCountries.add(CountryCode.DE);
        this._allCountries.add(CountryCode.FR);
        this._baseCountryCount = this._allCountries.size();
        ArrayList<CountryCode> arrayList = new ArrayList<CountryCode>();
        for (CountryCode countryCode : CountryCode.values()) {
            if (!countryCode.isSelectableByUser()) continue;
            arrayList.add(countryCode);
        }
        Collections.sort(arrayList, new Comparator<CountryCode>(this.getResources()){
            final /* synthetic */ Resources val$res;
            {
                this.val$res = resources;
            }

            @Override
            public int compare(CountryCode object, CountryCode countryCode) {
                if ((object = object.getDisplayName(this.val$res)) == null) {
                    return -1;
                }
                return object.compareTo(countryCode.getDisplayName(this.val$res));
            }
        });
        this._allCountries.addAll(arrayList);
        this._countryAdapter = new CountryAdapter();
        this.getSpinner().setAdapter((SpinnerAdapter)this._countryAdapter);
        this.getSpinner().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            public void onItemSelected(AdapterView<?> object, View view, int n, long l) {
                object = CustomCountryChoser.this._countryAdapter.getItem(n);
                CustomCountryChoser.this._currentCountry = object;
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                CustomCountryChoser.this._currentCountry = null;
            }
        });
    }

    public Country getCountry() {
        return this._currentCountry;
    }

    public void setCountry(Country country) {
        int n = country != null ? this._allCountries.indexOf(country) + 1 : 0;
        if (n >= 0) {
            this.getSpinner().setSelection(n);
        }
        this._currentCountry = country;
    }

    private class CountryAdapter
    extends BaseAdapter {
        public int getCount() {
            return CustomCountryChoser.this._allCountries.size() + 1;
        }

        public View getDropDownView(int n, View object, ViewGroup viewGroup) {
            object = (CountryRowView)this.getView(n, (View)object, viewGroup);
            int n2 = CustomCountryChoser.this._baseCountryCount;
            boolean bl = true;
            if (n != n2 + 1) {
                bl = false;
            }
            object.showTopDivider(bl);
            return object;
        }

        public CountryCode getItem(int n) {
            if (n == 0) {
                return null;
            }
            return (CountryCode)CustomCountryChoser.this._allCountries.get(n - 1);
        }

        public long getItemId(int n) {
            return 0L;
        }

        public View getView(int n, View object, ViewGroup viewGroup) {
            object = object == null ? new CountryRowView(CustomCountryChoser.this.getContext()) : (CountryRowView)((Object)object);
            object.setCountry(this.getItem(n));
            object.showTopDivider(false);
            return object;
        }
    }

}
