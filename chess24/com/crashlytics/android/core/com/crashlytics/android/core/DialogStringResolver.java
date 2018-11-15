/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.crashlytics.android.core;

import android.content.Context;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.settings.PromptSettingsData;

class DialogStringResolver {
    private static final String PROMPT_MESSAGE_RES_NAME = "com.crashlytics.CrashSubmissionPromptMessage";
    private static final String PROMPT_TITLE_RES_NAME = "com.crashlytics.CrashSubmissionPromptTitle";
    private static final String SUBMISSION_ALWAYS_SEND_RES_NAME = "com.crashlytics.CrashSubmissionAlwaysSendTitle";
    private static final String SUBMISSION_CANCEL_RES_NAME = "com.crashlytics.CrashSubmissionCancelTitle";
    private static final String SUBMISSION_SEND_RES_NAME = "com.crashlytics.CrashSubmissionSendTitle";
    private final Context context;
    private final PromptSettingsData promptData;

    public DialogStringResolver(Context context, PromptSettingsData promptSettingsData) {
        this.context = context;
        this.promptData = promptSettingsData;
    }

    private boolean isNullOrEmpty(String string) {
        if (string != null && string.length() != 0) {
            return false;
        }
        return true;
    }

    private String resourceOrFallbackValue(String string, String string2) {
        return this.stringOrFallback(CommonUtils.getStringsFileValue(this.context, string), string2);
    }

    private String stringOrFallback(String string, String string2) {
        String string3 = string;
        if (this.isNullOrEmpty(string)) {
            string3 = string2;
        }
        return string3;
    }

    public String getAlwaysSendButtonTitle() {
        return this.resourceOrFallbackValue(SUBMISSION_ALWAYS_SEND_RES_NAME, this.promptData.alwaysSendButtonTitle);
    }

    public String getCancelButtonTitle() {
        return this.resourceOrFallbackValue(SUBMISSION_CANCEL_RES_NAME, this.promptData.cancelButtonTitle);
    }

    public String getMessage() {
        return this.resourceOrFallbackValue(PROMPT_MESSAGE_RES_NAME, this.promptData.message);
    }

    public String getSendButtonTitle() {
        return this.resourceOrFallbackValue(SUBMISSION_SEND_RES_NAME, this.promptData.sendButtonTitle);
    }

    public String getTitle() {
        return this.resourceOrFallbackValue(PROMPT_TITLE_RES_NAME, this.promptData.title);
    }
}
