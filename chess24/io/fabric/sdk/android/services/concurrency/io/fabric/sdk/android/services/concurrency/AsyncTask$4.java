/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.concurrency;

import io.fabric.sdk.android.services.concurrency.AsyncTask;

static class AsyncTask {
    static final /* synthetic */ int[] $SwitchMap$io$fabric$sdk$android$services$concurrency$AsyncTask$Status;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        $SwitchMap$io$fabric$sdk$android$services$concurrency$AsyncTask$Status = new int[AsyncTask.Status.values().length];
        try {
            AsyncTask.$SwitchMap$io$fabric$sdk$android$services$concurrency$AsyncTask$Status[AsyncTask.Status.RUNNING.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            AsyncTask.$SwitchMap$io$fabric$sdk$android$services$concurrency$AsyncTask$Status[AsyncTask.Status.FINISHED.ordinal()] = 2;
            return;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            return;
        }
    }
}
