/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.settings;

public class SessionSettingsData {
    public final int identifierMask;
    public final int logBufferSize;
    public final int maxChainedExceptionDepth;
    public final int maxCompleteSessionsCount;
    public final int maxCustomExceptionEvents;
    public final int maxCustomKeyValuePairs;
    public final boolean sendSessionWithoutCrash;

    public SessionSettingsData(int n, int n2, int n3, int n4, int n5, boolean bl, int n6) {
        this.logBufferSize = n;
        this.maxChainedExceptionDepth = n2;
        this.maxCustomExceptionEvents = n3;
        this.maxCustomKeyValuePairs = n4;
        this.identifierMask = n5;
        this.sendSessionWithoutCrash = bl;
        this.maxCompleteSessionsCount = n6;
    }
}
