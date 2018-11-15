/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 */
package android.support.v4.content;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.ModernAsyncTask;

private static class ModernAsyncTask.InternalHandler
extends Handler {
    ModernAsyncTask.InternalHandler() {
        super(Looper.getMainLooper());
    }

    public void handleMessage(Message message) {
        ModernAsyncTask.AsyncTaskResult asyncTaskResult = (ModernAsyncTask.AsyncTaskResult)message.obj;
        switch (message.what) {
            default: {
                return;
            }
            case 2: {
                asyncTaskResult.mTask.onProgressUpdate(asyncTaskResult.mData);
                return;
            }
            case 1: 
        }
        asyncTaskResult.mTask.finish(asyncTaskResult.mData[0]);
    }
}
