/*
 * Decompiled with CFR 0_134.
 */
package com.example.android.trivialdrivesample.util;

import com.example.android.trivialdrivesample.util.IabHelper;

public class IabResult {
    String mMessage;
    int mResponse;

    public IabResult(int n, String string) {
        this.mResponse = n;
        if (string != null && string.trim().length() != 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append(" (response: ");
            stringBuilder.append(IabHelper.getResponseDesc(n));
            stringBuilder.append(")");
            this.mMessage = stringBuilder.toString();
            return;
        }
        this.mMessage = IabHelper.getResponseDesc(n);
    }

    public String getMessage() {
        return this.mMessage;
    }

    public int getResponse() {
        return this.mResponse;
    }

    public boolean isFailure() {
        return this.isSuccess() ^ true;
    }

    public boolean isSuccess() {
        if (this.mResponse == 0) {
            return true;
        }
        return false;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("IabResult: ");
        stringBuilder.append(this.getMessage());
        return stringBuilder.toString();
    }
}
