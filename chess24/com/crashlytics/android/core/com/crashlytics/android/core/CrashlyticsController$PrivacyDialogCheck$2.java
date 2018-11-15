/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.CrashPromptDialog;
import com.crashlytics.android.core.CrashlyticsController;

class CrashlyticsController.PrivacyDialogCheck
implements Runnable {
    final /* synthetic */ CrashPromptDialog val$dialog;

    CrashlyticsController.PrivacyDialogCheck(CrashPromptDialog crashPromptDialog) {
        this.val$dialog = crashPromptDialog;
    }

    @Override
    public void run() {
        this.val$dialog.show();
    }
}
