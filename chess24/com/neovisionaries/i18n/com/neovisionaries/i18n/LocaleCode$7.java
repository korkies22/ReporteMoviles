/*
 * Decompiled with CFR 0_134.
 */
package com.neovisionaries.i18n;

import com.neovisionaries.i18n.CountryCode;
import com.neovisionaries.i18n.LanguageCode;
import java.util.Locale;

static final class LocaleCode
extends com.neovisionaries.i18n.LocaleCode {
    LocaleCode(String string2, int n2, LanguageCode languageCode, CountryCode countryCode) {
    }

    @Override
    public Locale toLocale() {
        return Locale.KOREAN;
    }
}
