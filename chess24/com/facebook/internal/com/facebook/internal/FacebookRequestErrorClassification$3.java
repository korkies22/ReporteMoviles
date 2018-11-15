/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.internal;

import com.facebook.FacebookRequestError;

static class FacebookRequestErrorClassification {
    static final /* synthetic */ int[] $SwitchMap$com$facebook$FacebookRequestError$Category;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        $SwitchMap$com$facebook$FacebookRequestError$Category = new int[FacebookRequestError.Category.values().length];
        try {
            FacebookRequestErrorClassification.$SwitchMap$com$facebook$FacebookRequestError$Category[FacebookRequestError.Category.OTHER.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            FacebookRequestErrorClassification.$SwitchMap$com$facebook$FacebookRequestError$Category[FacebookRequestError.Category.LOGIN_RECOVERABLE.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            FacebookRequestErrorClassification.$SwitchMap$com$facebook$FacebookRequestError$Category[FacebookRequestError.Category.TRANSIENT.ordinal()] = 3;
            return;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            return;
        }
    }
}
