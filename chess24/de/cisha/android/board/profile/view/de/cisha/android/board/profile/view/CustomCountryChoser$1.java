/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 */
package de.cisha.android.board.profile.view;

import android.content.res.Resources;
import com.neovisionaries.i18n.CountryCode;
import java.util.Comparator;

class CustomCountryChoser
implements Comparator<CountryCode> {
    final /* synthetic */ Resources val$res;

    CustomCountryChoser(Resources resources) {
        this.val$res = resources;
    }

    @Override
    public int compare(CountryCode object, CountryCode countryCode) {
        if ((object = object.getDisplayName(this.val$res)) == null) {
            return -1;
        }
        return object.compareTo(countryCode.getDisplayName(this.val$res));
    }
}
