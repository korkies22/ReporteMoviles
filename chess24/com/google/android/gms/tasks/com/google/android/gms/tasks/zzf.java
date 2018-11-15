/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import com.google.android.gms.tasks.Task;

interface zzf<TResult> {
    public void cancel();

    public void onComplete(@NonNull Task<TResult> var1);
}
