/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import com.google.android.gms.tasks.Task;

public interface Continuation<TResult, TContinuationResult> {
    public TContinuationResult then(@NonNull Task<TResult> var1) throws Exception;
}
