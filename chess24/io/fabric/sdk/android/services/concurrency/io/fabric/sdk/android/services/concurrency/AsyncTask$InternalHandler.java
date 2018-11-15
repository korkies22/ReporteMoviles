/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 */
package io.fabric.sdk.android.services.concurrency;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import io.fabric.sdk.android.services.concurrency.AsyncTask;

private static class AsyncTask.InternalHandler
extends Handler {
    public AsyncTask.InternalHandler() {
        super(Looper.getMainLooper());
    }

    public void handleMessage(Message message) {
        AsyncTask.AsyncTaskResult asyncTaskResult = (AsyncTask.AsyncTaskResult)message.obj;
        switch (message.what) {
            default: {
                return;
            }
            case 2: {
                asyncTaskResult.task.onProgressUpdate(asyncTaskResult.data);
                return;
            }
            case 1: 
        }
        asyncTaskResult.task.finish(asyncTaskResult.data[0]);
    }
}
