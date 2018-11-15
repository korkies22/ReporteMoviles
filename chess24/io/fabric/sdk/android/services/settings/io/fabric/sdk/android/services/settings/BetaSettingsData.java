/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.settings;

public class BetaSettingsData {
    public final int updateSuspendDurationSeconds;
    public final String updateUrl;

    public BetaSettingsData(String string, int n) {
        this.updateUrl = string;
        this.updateSuspendDurationSeconds = n;
    }
}
