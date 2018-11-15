/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

final class TaskExecutors
implements Executor {
    TaskExecutors() {
    }

    @Override
    public void execute(@NonNull Runnable runnable) {
        runnable.run();
    }
}
