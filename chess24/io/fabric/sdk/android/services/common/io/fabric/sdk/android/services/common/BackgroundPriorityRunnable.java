/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Process
 */
package io.fabric.sdk.android.services.common;

import android.os.Process;

public abstract class BackgroundPriorityRunnable
implements Runnable {
    protected abstract void onRun();

    @Override
    public final void run() {
        Process.setThreadPriority((int)10);
        this.onRun();
    }
}
