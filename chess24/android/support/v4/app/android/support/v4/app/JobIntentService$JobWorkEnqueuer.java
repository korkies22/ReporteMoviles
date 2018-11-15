/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.job.JobInfo
 *  android.app.job.JobInfo$Builder
 *  android.app.job.JobScheduler
 *  android.app.job.JobWorkItem
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 */
package android.support.v4.app;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.app.job.JobWorkItem;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.RequiresApi;
import android.support.v4.app.JobIntentService;

@RequiresApi(value=26)
static final class JobIntentService.JobWorkEnqueuer
extends JobIntentService.WorkEnqueuer {
    private final JobInfo mJobInfo;
    private final JobScheduler mJobScheduler;

    JobIntentService.JobWorkEnqueuer(Context context, ComponentName componentName, int n) {
        super(context, componentName);
        this.ensureJobId(n);
        this.mJobInfo = new JobInfo.Builder(n, this.mComponentName).setOverrideDeadline(0L).build();
        this.mJobScheduler = (JobScheduler)context.getApplicationContext().getSystemService("jobscheduler");
    }

    @Override
    void enqueueWork(Intent intent) {
        this.mJobScheduler.enqueue(this.mJobInfo, new JobWorkItem(intent));
    }
}
