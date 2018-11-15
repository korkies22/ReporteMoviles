/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.os.PowerManager
 *  android.os.PowerManager$WakeLock
 */
package android.support.v4.app;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.support.v4.app.JobIntentService;

static final class JobIntentService.CompatWorkEnqueuer
extends JobIntentService.WorkEnqueuer {
    private final Context mContext;
    private final PowerManager.WakeLock mLaunchWakeLock;
    boolean mLaunchingService;
    private final PowerManager.WakeLock mRunWakeLock;
    boolean mServiceProcessing;

    JobIntentService.CompatWorkEnqueuer(Context context, ComponentName componentName) {
        super(context, componentName);
        this.mContext = context.getApplicationContext();
        context = (PowerManager)context.getSystemService("power");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(componentName.getClassName());
        stringBuilder.append(":launch");
        this.mLaunchWakeLock = context.newWakeLock(1, stringBuilder.toString());
        this.mLaunchWakeLock.setReferenceCounted(false);
        stringBuilder = new StringBuilder();
        stringBuilder.append(componentName.getClassName());
        stringBuilder.append(":run");
        this.mRunWakeLock = context.newWakeLock(1, stringBuilder.toString());
        this.mRunWakeLock.setReferenceCounted(false);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    void enqueueWork(Intent intent) {
        intent = new Intent(intent);
        intent.setComponent(this.mComponentName);
        if (this.mContext.startService(intent) == null) {
            return;
        }
        synchronized (this) {
            if (!this.mLaunchingService) {
                this.mLaunchingService = true;
                if (!this.mServiceProcessing) {
                    this.mLaunchWakeLock.acquire(60000L);
                }
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void serviceProcessingFinished() {
        synchronized (this) {
            if (this.mServiceProcessing) {
                if (this.mLaunchingService) {
                    this.mLaunchWakeLock.acquire(60000L);
                }
                this.mServiceProcessing = false;
                this.mRunWakeLock.release();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void serviceProcessingStarted() {
        synchronized (this) {
            if (!this.mServiceProcessing) {
                this.mServiceProcessing = true;
                this.mRunWakeLock.acquire(600000L);
                this.mLaunchWakeLock.release();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void serviceStartReceived() {
        synchronized (this) {
            this.mLaunchingService = false;
            return;
        }
    }
}
