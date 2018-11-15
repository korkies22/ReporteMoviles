/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.Map;

class zzaad
implements OnCompleteListener<TResult> {
    final /* synthetic */ TaskCompletionSource zzazG;

    zzaad(TaskCompletionSource taskCompletionSource) {
        this.zzazG = taskCompletionSource;
    }

    @Override
    public void onComplete(@NonNull Task<TResult> task) {
        zzaad.this.zzazD.remove(this.zzazG);
    }
}
