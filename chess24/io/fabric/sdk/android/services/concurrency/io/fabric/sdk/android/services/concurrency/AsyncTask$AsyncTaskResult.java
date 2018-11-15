/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.concurrency;

import io.fabric.sdk.android.services.concurrency.AsyncTask;

private static class AsyncTask.AsyncTaskResult<Data> {
    final Data[] data;
    final AsyncTask task;

    /* varargs */ AsyncTask.AsyncTaskResult(AsyncTask asyncTask, Data ... arrData) {
        this.task = asyncTask;
        this.data = arrData;
    }
}
