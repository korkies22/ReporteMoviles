// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.profile.view;

import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.SpinnerAdapter;
import java.util.Collection;
import java.util.Collections;
import android.content.res.Resources;
import java.util.Comparator;
import java.util.ArrayList;
import android.util.AttributeSet;
import android.content.Context;
import de.cisha.chess.model.Country;
import com.neovisionaries.i18n.CountryCode;
import java.util.List;
import de.cisha.android.ui.patterns.input.CustomSpinner;

public class CustomCountryChoser extends CustomSpinner
{
    private List<CountryCode> _allCountries;
    private int _baseCountryCount;
    private CountryAdapter _countryAdapter;
    private Country _currentCountry;
    
    public CustomCountryChoser(final Context context) {
        super(context);
        this.init();
    }
    
    public CustomCountryChoser(final Context context, final AttributeSet set) {
        super(context, set);
        this.init();
    }
    
    private void init() {
        (this._allCountries = new ArrayList<CountryCode>()).add(CountryCode.RU);
        this._allCountries.add(CountryCode.UA);
        this._allCountries.add(CountryCode.CN);
        this._allCountries.add(CountryCode.IN);
        this._allCountries.add(CountryCode.US);
        this._allCountries.add(CountryCode.ES);
        this._allCountries.add(CountryCode.GB);
        this._allCountries.add(CountryCode.DE);
        this._allCountries.add(CountryCode.FR);
        this._baseCountryCount = this._allCountries.size();
        final ArrayList<CountryCode> list = new ArrayList<CountryCode>();
        final CountryCode[] values = CountryCode.values();
        for (int i = 0; i < values.length; ++i) {
            final CountryCode countryCode = values[i];
            if (countryCode.isSelectableByUser()) {
                list.add(countryCode);
            }
        }
        Collections.sort((List<Object>)list, (Comparator<? super Object>)new Comparator<CountryCode>() {
            final /* synthetic */ Resources val.res = CustomCountryChoser.this.getResources();
            
            @Override
            public int compare(final CountryCode countryCode, final CountryCode countryCode2) {
                final String displayName = countryCode.getDisplayName(this.val.res);
                if (displayName == null) {
                    return -1;
                }
                return displayName.compareTo(countryCode2.getDisplayName(this.val.res));
            }
        });
        this._allCountries.addAll(list);
        this._countryAdapter = new CountryAdapter();
        this.getSpinner().setAdapter((SpinnerAdapter)this._countryAdapter);
        this.getSpinner().setOnItemSelectedListener((AdapterView.OnItemSelectedListener)new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(final AdapterView<?> adapterView, final View view, final int n, final long n2) {
                CustomCountryChoser.this._currentCountry = CustomCountryChoser.this._countryAdapter.getItem(n);
            }
            
            public void onNothingSelected(final AdapterView<?> adapterView) {
                CustomCountryChoser.this._currentCountry = null;
            }
        });
    }
    
    public Country getCountry() {
        return this._currentCountry;
    }
    
    public void setCountry(final Country currentCountry) {
        int selection;
        if (currentCountry != null) {
            selection = this._allCountries.indexOf(currentCountry) + 1;
        }
        else {
            selection = 0;
        }
        if (selection >= 0) {
            this.getSpinner().setSelection(selection);
        }
        this._currentCountry = currentCountry;
    }
    
    private class CountryAdapter extends BaseAdapter
    {
        public int getCount() {
            return CustomCountryChoser.this._allCountries.size() + 1;
        }
        
        public View getDropDownView(final int n, final View view, final ViewGroup viewGroup) {
            final CountryRowView countryRowView = (CountryRowView)this.getView(n, view, viewGroup);
            final int access.300 = CustomCountryChoser.this._baseCountryCount;
            boolean b = true;
            if (n != access.300 + 1) {
                b = false;
            }
            countryRowView.showTopDivider(b);
            return (View)countryRowView;
        }
        
        public CountryCode getItem(final int n) {
            if (n == 0) {
                return null;
            }
            return CustomCountryChoser.this._allCountries.get(n - 1);
        }
        
        public long getItemId(final int n) {
            return 0L;
        }
        
        public View getView(final int n, final View view, final ViewGroup viewGroup) {
            CountryRowView countryRowView;
            if (view == null) {
                countryRowView = new CountryRowView(CustomCountryChoser.this.getContext());
            }
            else {
                countryRowView = (CountryRowView)view;
            }
            countryRowView.setCountry(this.getItem(n));
            countryRowView.showTopDivider(false);
            return (View)countryRowView;
        }
    }
}
