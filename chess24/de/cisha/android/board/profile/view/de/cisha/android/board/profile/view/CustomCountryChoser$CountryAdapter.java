/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.BaseAdapter
 */
package de.cisha.android.board.profile.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.neovisionaries.i18n.CountryCode;
import de.cisha.android.board.profile.view.CountryRowView;
import de.cisha.android.board.profile.view.CustomCountryChoser;
import java.util.List;

private class CustomCountryChoser.CountryAdapter
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
