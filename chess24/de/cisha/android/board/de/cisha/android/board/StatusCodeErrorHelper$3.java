/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board;

import de.cisha.android.board.service.jsonparser.APIStatusCode;

static class StatusCodeErrorHelper {
    static final /* synthetic */ int[] $SwitchMap$de$cisha$android$board$service$jsonparser$APIStatusCode;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        $SwitchMap$de$cisha$android$board$service$jsonparser$APIStatusCode = new int[APIStatusCode.values().length];
        try {
            StatusCodeErrorHelper.$SwitchMap$de$cisha$android$board$service$jsonparser$APIStatusCode[APIStatusCode.API_ERROR_API_NOT_VALID_PLEASE_UPDATE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            StatusCodeErrorHelper.$SwitchMap$de$cisha$android$board$service$jsonparser$APIStatusCode[APIStatusCode.API_ERROR_FORBIDDEN.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            StatusCodeErrorHelper.$SwitchMap$de$cisha$android$board$service$jsonparser$APIStatusCode[APIStatusCode.INTERNAL_NOT_LOGGED_IN.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            StatusCodeErrorHelper.$SwitchMap$de$cisha$android$board$service$jsonparser$APIStatusCode[APIStatusCode.API_ERROR_INVALID_AUTHTOKEN.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            StatusCodeErrorHelper.$SwitchMap$de$cisha$android$board$service$jsonparser$APIStatusCode[APIStatusCode.INTERNAL_TIMEOUT.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            StatusCodeErrorHelper.$SwitchMap$de$cisha$android$board$service$jsonparser$APIStatusCode[APIStatusCode.API_ERROR_SERVICE_UNAVAILABLE.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            StatusCodeErrorHelper.$SwitchMap$de$cisha$android$board$service$jsonparser$APIStatusCode[APIStatusCode.API_ERROR_TOO_MANY_REQUESTS.ordinal()] = 7;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            StatusCodeErrorHelper.$SwitchMap$de$cisha$android$board$service$jsonparser$APIStatusCode[APIStatusCode.API_OK.ordinal()] = 8;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            StatusCodeErrorHelper.$SwitchMap$de$cisha$android$board$service$jsonparser$APIStatusCode[APIStatusCode.INTERNAL_TACTICTRAINER_DAILY_LIMIT_REACHED.ordinal()] = 9;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            StatusCodeErrorHelper.$SwitchMap$de$cisha$android$board$service$jsonparser$APIStatusCode[APIStatusCode.INTERNAL_TACTICTRAINER_GUEST_LIMIT_REACHED.ordinal()] = 10;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            StatusCodeErrorHelper.$SwitchMap$de$cisha$android$board$service$jsonparser$APIStatusCode[APIStatusCode.INTERNAL_TACTICTRAINER_NO_MORE_EXERCISES.ordinal()] = 11;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            StatusCodeErrorHelper.$SwitchMap$de$cisha$android$board$service$jsonparser$APIStatusCode[APIStatusCode.INTERNAL_PURCHASE_ERROR.ordinal()] = 12;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            StatusCodeErrorHelper.$SwitchMap$de$cisha$android$board$service$jsonparser$APIStatusCode[APIStatusCode.INTERNAL_PURCHASE_CONSUMPTION_ERROR.ordinal()] = 13;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            StatusCodeErrorHelper.$SwitchMap$de$cisha$android$board$service$jsonparser$APIStatusCode[APIStatusCode.API_ERROR_NOT_FOUND.ordinal()] = 14;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            StatusCodeErrorHelper.$SwitchMap$de$cisha$android$board$service$jsonparser$APIStatusCode[APIStatusCode.API_ERROR_NOT_SET.ordinal()] = 15;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            StatusCodeErrorHelper.$SwitchMap$de$cisha$android$board$service$jsonparser$APIStatusCode[APIStatusCode.API_ERROR_UNKNOWN.ordinal()] = 16;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            StatusCodeErrorHelper.$SwitchMap$de$cisha$android$board$service$jsonparser$APIStatusCode[APIStatusCode.API_ERROR_MISSING_ARGUMENT.ordinal()] = 17;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            StatusCodeErrorHelper.$SwitchMap$de$cisha$android$board$service$jsonparser$APIStatusCode[APIStatusCode.INTERNAL_INCORRECT_SERVER_JSON.ordinal()] = 18;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            StatusCodeErrorHelper.$SwitchMap$de$cisha$android$board$service$jsonparser$APIStatusCode[APIStatusCode.INTERNAL_INCORRECT_SERVER_JSON_NO_DATA_OBJECT.ordinal()] = 19;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            StatusCodeErrorHelper.$SwitchMap$de$cisha$android$board$service$jsonparser$APIStatusCode[APIStatusCode.INTERNAL_MALFORMED_API_URL.ordinal()] = 20;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            StatusCodeErrorHelper.$SwitchMap$de$cisha$android$board$service$jsonparser$APIStatusCode[APIStatusCode.INTERNAL_UNKNOWN.ordinal()] = 21;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            StatusCodeErrorHelper.$SwitchMap$de$cisha$android$board$service$jsonparser$APIStatusCode[APIStatusCode.API_ERROR_CODE_INVALID_DEVICE.ordinal()] = 22;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            StatusCodeErrorHelper.$SwitchMap$de$cisha$android$board$service$jsonparser$APIStatusCode[APIStatusCode.API_ERROR_INVALID_CREDENTIALS.ordinal()] = 23;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            StatusCodeErrorHelper.$SwitchMap$de$cisha$android$board$service$jsonparser$APIStatusCode[APIStatusCode.API_ERROR_UNCONFIRMED_ACCOUNT.ordinal()] = 24;
            return;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            return;
        }
    }
}
