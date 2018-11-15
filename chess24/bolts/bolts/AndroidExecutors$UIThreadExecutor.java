/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 */
package bolts;

import android.os.Handler;
import android.os.Looper;
import bolts.AndroidExecutors;
import java.util.concurrent.Executor;

private static class AndroidExecutors.UIThreadExecutor
implements Executor {
    private AndroidExecutors.UIThreadExecutor() {
    }

    @Override
    public void execute(Runnable runnable) {
        new Handler(Looper.getMainLooper()).post(runnable);
    }
}
