/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.AsyncTask
 */
package android.support.v4.app;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.JobIntentService;

final class JobIntentService.CommandProcessor
extends AsyncTask<Void, Void, Void> {
    JobIntentService.CommandProcessor() {
    }

    protected /* varargs */ Void doInBackground(Void ... object) {
        while ((object = JobIntentService.this.dequeueWork()) != null) {
            JobIntentService.this.onHandleWork(object.getIntent());
            object.complete();
        }
        return null;
    }

    protected void onCancelled(Void void_) {
        JobIntentService.this.processorFinished();
    }

    protected void onPostExecute(Void void_) {
        JobIntentService.this.processorFinished();
    }
}
