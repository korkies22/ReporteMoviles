/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.settings;

public class AnalyticsSettingsData {
    public static final int DEFAULT_SAMPLING_RATE = 1;
    public final String analyticsURL;
    public final int flushIntervalSeconds;
    public final boolean flushOnBackground;
    public final boolean forwardToFirebaseAnalytics;
    public final boolean includePurchaseEventsInForwardedEvents;
    public final int maxByteSizePerFile;
    public final int maxFileCountPerSend;
    public final int maxPendingSendFileCount;
    public final int samplingRate;
    public final boolean trackCustomEvents;
    public final boolean trackPredefinedEvents;

    @Deprecated
    public AnalyticsSettingsData(String string, int n, int n2, int n3, int n4, boolean bl) {
        this(string, n, n2, n3, n4, false, false, bl, true, 1, true);
    }

    @Deprecated
    public AnalyticsSettingsData(String string, int n, int n2, int n3, int n4, boolean bl, int n5) {
        this(string, n, n2, n3, n4, false, false, bl, true, n5, true);
    }

    public AnalyticsSettingsData(String string, int n, int n2, int n3, int n4, boolean bl, boolean bl2, boolean bl3, boolean bl4, int n5, boolean bl5) {
        this.analyticsURL = string;
        this.flushIntervalSeconds = n;
        this.maxByteSizePerFile = n2;
        this.maxFileCountPerSend = n3;
        this.maxPendingSendFileCount = n4;
        this.forwardToFirebaseAnalytics = bl;
        this.includePurchaseEventsInForwardedEvents = bl2;
        this.trackCustomEvents = bl3;
        this.trackPredefinedEvents = bl4;
        this.samplingRate = n5;
        this.flushOnBackground = bl5;
    }
}
