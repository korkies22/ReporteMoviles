/*
 * Decompiled with CFR 0_134.
 */
package com.facebook;

import com.facebook.FacebookException;
import com.facebook.FacebookRequestError;
import com.facebook.GraphResponse;

public class FacebookGraphResponseException
extends FacebookException {
    private final GraphResponse graphResponse;

    public FacebookGraphResponseException(GraphResponse graphResponse, String string) {
        super(string);
        this.graphResponse = graphResponse;
    }

    public final GraphResponse getGraphResponse() {
        return this.graphResponse;
    }

    @Override
    public final String toString() {
        FacebookRequestError facebookRequestError = this.graphResponse != null ? this.graphResponse.getError() : null;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{FacebookGraphResponseException: ");
        String string = this.getMessage();
        if (string != null) {
            stringBuilder.append(string);
            stringBuilder.append(" ");
        }
        if (facebookRequestError != null) {
            stringBuilder.append("httpResponseCode: ");
            stringBuilder.append(facebookRequestError.getRequestStatusCode());
            stringBuilder.append(", facebookErrorCode: ");
            stringBuilder.append(facebookRequestError.getErrorCode());
            stringBuilder.append(", facebookErrorType: ");
            stringBuilder.append(facebookRequestError.getErrorType());
            stringBuilder.append(", message: ");
            stringBuilder.append(facebookRequestError.getErrorMessage());
            stringBuilder.append("}");
        }
        return stringBuilder.toString();
    }
}
