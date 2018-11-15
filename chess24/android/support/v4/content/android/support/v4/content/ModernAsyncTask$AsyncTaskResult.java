/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.content;

import android.support.v4.content.ModernAsyncTask;

private static class ModernAsyncTask.AsyncTaskResult<Data> {
    final Data[] mData;
    final ModernAsyncTask mTask;

    /* varargs */ ModernAsyncTask.AsyncTaskResult(ModernAsyncTask modernAsyncTask, Data ... arrData) {
        this.mTask = modernAsyncTask;
        this.mData = arrData;
    }
}
