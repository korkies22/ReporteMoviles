/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.registration;

import de.cisha.android.board.service.jsonparser.APIStatusCode;

static class LoginActivity {
    static final /* synthetic */ int[] $SwitchMap$de$cisha$android$board$service$jsonparser$APIStatusCode;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        $SwitchMap$de$cisha$android$board$service$jsonparser$APIStatusCode = new int[APIStatusCode.values().length];
        try {
            LoginActivity.$SwitchMap$de$cisha$android$board$service$jsonparser$APIStatusCode[APIStatusCode.API_ERROR_CODE_INVALID_DEVICE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            LoginActivity.$SwitchMap$de$cisha$android$board$service$jsonparser$APIStatusCode[APIStatusCode.API_ERROR_INVALID_CREDENTIALS.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            LoginActivity.$SwitchMap$de$cisha$android$board$service$jsonparser$APIStatusCode[APIStatusCode.API_ERROR_UNCONFIRMED_ACCOUNT.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            LoginActivity.$SwitchMap$de$cisha$android$board$service$jsonparser$APIStatusCode[APIStatusCode.API_ERROR_TOO_MANY_REQUESTS.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            LoginActivity.$SwitchMap$de$cisha$android$board$service$jsonparser$APIStatusCode[APIStatusCode.INTERNAL_TIMEOUT.ordinal()] = 5;
            return;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            return;
        }
    }
}
