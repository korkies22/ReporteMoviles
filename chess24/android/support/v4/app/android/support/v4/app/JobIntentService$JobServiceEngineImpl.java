/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Service
 *  android.app.job.JobParameters
 *  android.app.job.JobServiceEngine
 *  android.app.job.JobWorkItem
 *  android.content.Intent
 *  android.os.IBinder
 */
package android.support.v4.app;

import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobServiceEngine;
import android.app.job.JobWorkItem;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.app.JobIntentService;

@RequiresApi(value=26)
static final class JobIntentService.JobServiceEngineImpl
extends JobServiceEngine
implements JobIntentService.CompatJobEngine {
    static final boolean DEBUG = false;
    static final String TAG = "JobServiceEngineImpl";
    final Object mLock = new Object();
    JobParameters mParams;
    final JobIntentService mService;

    JobIntentService.JobServiceEngineImpl(JobIntentService jobIntentService) {
        super((Service)jobIntentService);
        this.mService = jobIntentService;
    }

    @Override
    public IBinder compatGetBinder() {
        return this.getBinder();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public JobIntentService.GenericWorkItem dequeueWork() {
        Object object = this.mLock;
        // MONITORENTER : object
        if (this.mParams == null) {
            // MONITOREXIT : object
            return null;
        }
        JobWorkItem jobWorkItem = this.mParams.dequeueWork();
        // MONITOREXIT : object
        if (jobWorkItem == null) return null;
        jobWorkItem.getIntent().setExtrasClassLoader(this.mService.getClassLoader());
        return new WrapperWorkItem(jobWorkItem);
    }

    public boolean onStartJob(JobParameters jobParameters) {
        this.mParams = jobParameters;
        this.mService.ensureProcessorRunningLocked(false);
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean onStopJob(JobParameters object) {
        boolean bl = this.mService.doStopCurrentWork();
        object = this.mLock;
        synchronized (object) {
            this.mParams = null;
            return bl;
        }
    }

    final class WrapperWorkItem
    implements JobIntentService.GenericWorkItem {
        final JobWorkItem mJobWork;

        WrapperWorkItem(JobWorkItem jobWorkItem) {
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

}
