/*
 * Decompiled with CFR 0_134.
 */
package com.example.android.trivialdrivesample.util;

import com.example.android.trivialdrivesample.util.IabResult;

public class IabException
extends Exception {
    IabResult mResult;

    public IabException(int n, String string) {
        this(new IabResult(n, string));
    }

    public IabException(int n, String string, Exception exception) {
        this(new IabResult(n, string), exception);
    }

    public IabException(IabResult iabResult) {
        this(iabResult, null);
    }

    public IabException(IabResult iabResult, Exception exception) {
        super(iabResult.getMessage(), exception);
        this.mResult = iabResult;
    }

    public IabResult getResult() {
        return this.mResult;
    }
}
