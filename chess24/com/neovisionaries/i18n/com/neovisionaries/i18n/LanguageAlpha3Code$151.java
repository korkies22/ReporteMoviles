/*
 * Decompiled with CFR 0_134.
 */
package com.neovisionaries.i18n;

import com.neovisionaries.i18n.LanguageAlpha3Code;
import com.neovisionaries.i18n.LanguageCode;

static final class LanguageAlpha3Code
extends com.neovisionaries.i18n.LanguageAlpha3Code {
    LanguageAlpha3Code(String string2, int n2, String string3) {
    }

    @Override
    public LanguageCode getAlpha2() {
        return LanguageCode.ro;
    }

    @Override
    public com.neovisionaries.i18n.LanguageAlpha3Code getSynonym() {
        return ron;
    }

    @Override
    public LanguageAlpha3Code.Usage getUsage() {
        return LanguageAlpha3Code.Usage.BIBLIOGRAPHY;
    }
}
