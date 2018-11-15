/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 */
package android.support.v4.app;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.JobIntentService;

static abstract class JobIntentService.WorkEnqueuer {
    final ComponentName mComponentName;
    boolean mHasJobId;
    int mJobId;

    JobIntentService.WorkEnqueuer(Context context, ComponentName componentName) {
        this.mComponentName = componentName;
    }

    abstract void enqueueWork(Intent var1);

    void ensureJobId(int n) {
        if (!this.mHasJobId) {
            this.mHasJobId = true;
            this.mJobId = n;
            return;
        }
        if (this.mJobId != n) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Given job ID ");
            stringBuilder.append(n);
            stringBuilder.append(" is different than previous ");
            stringBuilder.append(this.mJobId);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    public void serviceProcessingFinished() {
    }

    public void serviceProcessingStarted() {
    }

    public void serviceStartReceived() {
    }
}
