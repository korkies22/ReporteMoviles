/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.os;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.os.LocaleListCompat;
import android.support.v4.os.LocaleListHelper;
import android.support.v4.os.LocaleListInterface;
import java.util.Locale;

static class LocaleListCompat.LocaleListCompatBaseImpl
implements LocaleListInterface {
    private LocaleListHelper mLocaleList = new LocaleListHelper(new Locale[0]);

    LocaleListCompat.LocaleListCompatBaseImpl() {
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
        this.mLocaleList = new LocaleListHelper(arrlocale);
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
