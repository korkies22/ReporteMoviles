// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.util;

import android.annotation.SuppressLint;
import java.util.concurrent.RejectedExecutionException;
import de.cisha.chess.util.Logger;
import android.os.Build.VERSION;
import android.os.AsyncTask;

public class AsyncTaskCompatHelper
{
    @SuppressLint({ "NewApi" })
    public static <A, B, C> void executeOnExecutorPool(final AsyncTask<A, B, C> asyncTask, final A... array) {
        try {
            if (Build.VERSION.SDK_INT >= 11) {
                asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Object[])array);
                return;
            }
            asyncTask.execute((Object[])array);
        }
        catch (RejectedExecutionException ex) {
            final Logger instance = Logger.getInstance();
            final StringBuilder sb = new StringBuilder();
            sb.append("Loading images too fast for the ThreadPoolExecutor to handle in the queue");
            sb.append(ex.getMessage());
            instance.debug("CouchImageService", sb.toString());
        }
    }
}
