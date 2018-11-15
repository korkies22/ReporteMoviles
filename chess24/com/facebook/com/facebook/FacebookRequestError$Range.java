/*
 * Decompiled with CFR 0_134.
 */
package com.facebook;

import com.facebook.FacebookRequestError;

private static class FacebookRequestError.Range {
    private final int end;
    private final int start;

    private FacebookRequestError.Range(int n, int n2) {
        this.start = n;
        this.end = n2;
    }

    boolean contains(int n) {
        if (this.start <= n && n <= this.end) {
            return true;
        }
        return false;
    }
}
