/*
 * Decompiled with CFR 0_134.
 */
package com.facebook;

import com.facebook.FacebookException;
import com.facebook.FacebookRequestError;

public class FacebookServiceException
extends FacebookException {
    private static final long serialVersionUID = 1L;
    private final FacebookRequestError error;

    public FacebookServiceException(FacebookRequestError facebookRequestError, String string) {
        super(string);
        this.error = facebookRequestError;
    }

    public final FacebookRequestError getRequestError() {
        return this.error;
    }

    @Override
    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{FacebookServiceException: ");
        stringBuilder.append("httpResponseCode: ");
        stringBuilder.append(this.error.getRequestStatusCode());
        stringBuilder.append(", facebookErrorCode: ");
        stringBuilder.append(this.error.getErrorCode());
        stringBuilder.append(", facebookErrorType: ");
        stringBuilder.append(this.error.getErrorType());
        stringBuilder.append(", message: ");
        stringBuilder.append(this.error.getErrorMessage());
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
