/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.common;

public class ResponseParser {
    public static final int ResponseActionDiscard = 0;
    public static final int ResponseActionRetry = 1;

    public static int parse(int n) {
        if (n >= 200 && n <= 299) {
            return 0;
        }
        if (n >= 300 && n <= 399) {
            return 1;
        }
        if (n >= 400 && n <= 499) {
            return 0;
        }
        if (n >= 500) {
            return 1;
        }
        return 1;
    }
}
