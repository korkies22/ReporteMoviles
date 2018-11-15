/*
 * Decompiled with CFR 0_134.
 */
package com.neovisionaries.i18n;

import com.neovisionaries.i18n.LanguageAlpha3Code;
import java.util.Locale;

static final class LanguageCode
extends com.neovisionaries.i18n.LanguageCode {
    LanguageCode(String string2, int n2) {
    }

    @Override
    public LanguageAlpha3Code getAlpha3() {
        return LanguageAlpha3Code.zho;
    }

    @Override
    public Locale toLocale() {
        return Locale.CHINESE;
    }
}
