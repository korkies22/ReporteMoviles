/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.AsyncTask
 */
package com.crashlytics.android.core;

import android.os.AsyncTask;

class CrashTest
extends AsyncTask<Void, Void, Void> {
    final /* synthetic */ long val$delayMs;

    CrashTest(long l) {
        this.val$delayMs = l;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected /* varargs */ Void doInBackground(Void ... arrvoid) {
        try {
            Thread.sleep(this.val$delayMs);
        }
        catch (InterruptedException interruptedException) {}
        CrashTest.this.throwRuntimeException("Background thread crash");
        return null;
    }
}
