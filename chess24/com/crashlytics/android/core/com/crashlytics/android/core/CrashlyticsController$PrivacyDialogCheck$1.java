/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.CrashPromptDialog;
import com.crashlytics.android.core.CrashlyticsController;
import com.crashlytics.android.core.PreferenceManager;

class CrashlyticsController.PrivacyDialogCheck
implements CrashPromptDialog.AlwaysSendCallback {
    CrashlyticsController.PrivacyDialogCheck() {
    }

    @Override
    public void sendUserReportsWithoutPrompting(boolean bl) {
        PrivacyDialogCheck.this.preferenceManager.setShouldAlwaysSendReports(bl);
    }
}
