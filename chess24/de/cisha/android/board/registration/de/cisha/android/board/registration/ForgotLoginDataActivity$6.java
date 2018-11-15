/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.registration;

import de.cisha.android.board.service.jsonparser.APIStatusCode;

static class ForgotLoginDataActivity {
    static final /* synthetic */ int[] $SwitchMap$de$cisha$android$board$service$jsonparser$APIStatusCode;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        $SwitchMap$de$cisha$android$board$service$jsonparser$APIStatusCode = new int[APIStatusCode.values().length];
        try {
            ForgotLoginDataActivity.$SwitchMap$de$cisha$android$board$service$jsonparser$APIStatusCode[APIStatusCode.API_ERROR_CODE_INVALID_DEVICE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            ForgotLoginDataActivity.$SwitchMap$de$cisha$android$board$service$jsonparser$APIStatusCode[APIStatusCode.INTERNAL_TIMEOUT.ordinal()] = 2;
            return;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            return;
        }
    }
}
