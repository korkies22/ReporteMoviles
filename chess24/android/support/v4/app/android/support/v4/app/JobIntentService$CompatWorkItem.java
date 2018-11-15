/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 */
package android.support.v4.app;

import android.content.Intent;
import android.support.v4.app.JobIntentService;

final class JobIntentService.CompatWorkItem
implements JobIntentService.GenericWorkItem {
    final Intent mIntent;
    final int mStartId;

    JobIntentService.CompatWorkItem(Intent intent, int n) {
        this.mIntent = intent;
        this.mStartId = n;
    }

    @Override
    public void complete() {
        JobIntentService.this.stopSelf(this.mStartId);
    }

    @Override
    public Intent getIntent() {
        return this.mIntent;
    }
}
