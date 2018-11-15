/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.os.AsyncTask
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package de.cisha.android.board.util;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import de.cisha.chess.util.Logger;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

public class AsyncTaskCompatHelper {
    private AsyncTaskCompatHelper() {
    }

    @SuppressLint(value={"NewApi"})
    public static /* varargs */ <A, B, C> void executeOnExecutorPool(AsyncTask<A, B, C> asyncTask, A ... arrobject) {
        try {
            if (Build.VERSION.SDK_INT >= 11) {
                asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, arrobject);
                return;
            }
            asyncTask.execute(arrobject);
            return;
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            arrobject = Logger.getInstance();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Loading images too fast for the ThreadPoolExecutor to handle in the queue");
            stringBuilder.append(rejectedExecutionException.getMessage());
            arrobject.debug("CouchImageService", stringBuilder.toString());
            return;
        }
    }
}
