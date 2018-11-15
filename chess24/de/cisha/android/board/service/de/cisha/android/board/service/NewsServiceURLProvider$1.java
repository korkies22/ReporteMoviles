/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import de.cisha.android.board.Language;

static class NewsServiceURLProvider {
    static final /* synthetic */ int[] $SwitchMap$de$cisha$android$board$Language;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        $SwitchMap$de$cisha$android$board$Language = new int[Language.values().length];
        try {
            NewsServiceURLProvider.$SwitchMap$de$cisha$android$board$Language[Language.DE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            NewsServiceURLProvider.$SwitchMap$de$cisha$android$board$Language[Language.ES.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            NewsServiceURLProvider.$SwitchMap$de$cisha$android$board$Language[Language.EN.ordinal()] = 3;
            return;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            return;
        }
    }
}
