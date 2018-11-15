/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.AsyncTask
 */
package com.crashlytics.android.core;

import android.os.AsyncTask;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;

public class CrashTest {
    private void privateMethodThatThrowsException(String string) {
        throw new RuntimeException(string);
    }

    public void crashAsyncTask(final long l) {
        new AsyncTask<Void, Void, Void>(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            protected /* varargs */ Void doInBackground(Void ... arrvoid) {
                try {
                    Thread.sleep(l);
                }
                catch (InterruptedException interruptedException) {}
                CrashTest.this.throwRuntimeException("Background thread crash");
                return null;
            }
        }.execute((Object[])new Void[]{null});
    }

    public void indexOutOfBounds() {
        int n = new int[2][10];
        Logger logger = Fabric.getLogger();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Out of bounds value: ");
        stringBuilder.append(n);
        logger.d("CrashlyticsCore", stringBuilder.toString());
    }

    public int stackOverflow() {
        return this.stackOverflow() + (int)Math.random();
    }

    public void throwFiveChainedExceptions() {
        try {
            this.privateMethodThatThrowsException("1");
            return;
        }
        catch (Exception exception) {
            try {
                throw new RuntimeException("2", exception);
            }
            catch (Exception exception2) {
                try {
                    throw new RuntimeException("3", exception2);
                }
                catch (Exception exception3) {
                    try {
                        throw new RuntimeException("4", exception3);
                    }
                    catch (Exception exception4) {
                        throw new RuntimeException("5", exception4);
                    }
                }
            }
        }
    }

    public void throwRuntimeException(String string) {
        throw new RuntimeException(string);
    }

}
