/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.concurrency;

import io.fabric.sdk.android.services.concurrency.AsyncTask;
import java.util.concurrent.Callable;

private static abstract class AsyncTask.WorkerRunnable<Params, Result>
implements Callable<Result> {
    Params[] params;

    private AsyncTask.WorkerRunnable() {
    }
}
