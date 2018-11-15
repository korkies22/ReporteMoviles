/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 */
package com.crashlytics.android.core;

import android.app.Activity;
import com.crashlytics.android.core.CrashPromptDialog;
import com.crashlytics.android.core.CrashlyticsController;
import com.crashlytics.android.core.PreferenceManager;
import com.crashlytics.android.core.ReportUploader;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.settings.PromptSettingsData;

private static final class CrashlyticsController.PrivacyDialogCheck
implements ReportUploader.SendCheck {
    private final Kit kit;
    private final PreferenceManager preferenceManager;
    private final PromptSettingsData promptData;

    public CrashlyticsController.PrivacyDialogCheck(Kit kit, PreferenceManager preferenceManager, PromptSettingsData promptSettingsData) {
        this.kit = kit;
        this.preferenceManager = preferenceManager;
        this.promptData = promptSettingsData;
    }

    @Override
    public boolean canSendReports() {
        Activity activity = this.kit.getFabric().getCurrentActivity();
        if (activity != null && !activity.isFinishing()) {
            Object object = new CrashPromptDialog.AlwaysSendCallback(){

                @Override
                public void sendUserReportsWithoutPrompting(boolean bl) {
                    PrivacyDialogCheck.this.preferenceManager.setShouldAlwaysSendReports(bl);
                }
            };
            object = CrashPromptDialog.create(activity, this.promptData, (CrashPromptDialog.AlwaysSendCallback)object);
            activity.runOnUiThread(new Runnable((CrashPromptDialog)object){
                final /* synthetic */ CrashPromptDialog val$dialog;
                {
                    this.val$dialog = crashPromptDialog;
                }

                @Override
                public void run() {
                    this.val$dialog.show();
                }
            });
            Fabric.getLogger().d("CrashlyticsCore", "Waiting for user opt-in.");
            object.await();
            return object.getOptIn();
        }
        return true;
    }

}
