/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 */
package android.support.v4.app;

import android.os.IBinder;
import android.support.v4.app.JobIntentService;

static interface JobIntentService.CompatJobEngine {
    public IBinder compatGetBinder();

    public JobIntentService.GenericWorkItem dequeueWork();
}
