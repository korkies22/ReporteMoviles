/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.job.JobParameters
 *  android.app.job.JobWorkItem
 *  android.content.Intent
 */
package android.support.v4.app;

import android.app.job.JobParameters;
import android.app.job.JobWorkItem;
import android.content.Intent;
import android.support.v4.app.JobIntentService;

final class JobIntentService.JobServiceEngineImpl.WrapperWorkItem
implements JobIntentService.GenericWorkItem {
    final JobWorkItem mJobWork;

    JobIntentService.JobServiceEngineImpl.WrapperWorkItem(JobWorkItem jobWorkItem) {
        this.mJobWork = jobWorkItem;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void complete() {
        Object object = JobServiceEngineImpl.this.mLock;
        synchronized (object) {
            if (JobServiceEngineImpl.this.mParams != null) {
                JobServiceEngineImpl.this.mParams.completeWork(this.mJobWork);
            }
            return;
        }
    }

    @Override
    public Intent getIntent() {
        return this.mJobWork.getIntent();
    }
}
