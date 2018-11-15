/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 */
package com.crashlytics.android.core;

import android.content.DialogInterface;
import com.crashlytics.android.core.CrashPromptDialog;

static final class CrashPromptDialog
implements DialogInterface.OnClickListener {
    final /* synthetic */ CrashPromptDialog.OptInLatch val$latch;

    CrashPromptDialog(CrashPromptDialog.OptInLatch optInLatch) {
        this.val$latch = optInLatch;
    }

    public void onClick(DialogInterface dialogInterface, int n) {
        this.val$latch.setOptIn(true);
        dialogInterface.dismiss();
    }
}
