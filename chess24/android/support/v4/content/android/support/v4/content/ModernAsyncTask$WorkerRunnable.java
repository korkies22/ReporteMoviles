/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.content;

import android.support.v4.content.ModernAsyncTask;
import java.util.concurrent.Callable;

private static abstract class ModernAsyncTask.WorkerRunnable<Params, Result>
implements Callable<Result> {
    Params[] mParams;

    ModernAsyncTask.WorkerRunnable() {
    }
}
