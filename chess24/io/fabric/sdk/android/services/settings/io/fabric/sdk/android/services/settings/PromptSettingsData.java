/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.settings;

public class PromptSettingsData {
    public final String alwaysSendButtonTitle;
    public final String cancelButtonTitle;
    public final String message;
    public final String sendButtonTitle;
    public final boolean showAlwaysSendButton;
    public final boolean showCancelButton;
    public final String title;

    public PromptSettingsData(String string, String string2, String string3, boolean bl, String string4, boolean bl2, String string5) {
        this.title = string;
        this.message = string2;
        this.sendButtonTitle = string3;
        this.showCancelButton = bl;
        this.cancelButtonTitle = string4;
        this.showAlwaysSendButton = bl2;
        this.alwaysSendButtonTitle = string5;
    }
}
