/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.LocaleList
 */
package android.support.v4.os;

import android.os.LocaleList;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.os.LocaleListCompat;
import android.support.v4.os.LocaleListInterface;
import java.util.Locale;

@RequiresApi(value=24)
static class LocaleListCompat.LocaleListCompatApi24Impl
implements LocaleListInterface {
    private LocaleList mLocaleList = new LocaleList(new Locale[0]);

    LocaleListCompat.LocaleListCompatApi24Impl() {
    }

    @Override
    public boolean equals(Object object) {
        return this.mLocaleList.equals(((LocaleListCompat)object).unwrap());
    }

    @Override
    public Locale get(int n) {
        return this.mLocaleList.get(n);
    }

    @Nullable
    @Override
    public Locale getFirstMatch(String[] arrstring) {
        if (this.mLocaleList != null) {
            return this.mLocaleList.getFirstMatch(arrstring);
        }
        return null;
    }

    @Override
    public Object getLocaleList() {
        return this.mLocaleList;
    }

    @Override
    public int hashCode() {
        return this.mLocaleList.hashCode();
    }

    @IntRange(from=-1L)
    @Override
    public int indexOf(Locale locale) {
        return this.mLocaleList.indexOf(locale);
    }

    @Override
    public boolean isEmpty() {
        return this.mLocaleList.isEmpty();
    }

    @Override
    public /* varargs */ void setLocaleList(@NonNull Locale ... arrlocale) {
        this.mLocaleList = new LocaleList(arrlocale);
    }

    @IntRange(from=0L)
    @Override
    public int size() {
        return this.mLocaleList.size();
    }

    @Override
    public String toLanguageTags() {
        return this.mLocaleList.toLanguageTags();
    }

    @Override
    public String toString() {
        return this.mLocaleList.toString();
    }
}
