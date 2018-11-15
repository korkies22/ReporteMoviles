/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.common;

import io.fabric.sdk.android.services.common.BackgroundPriorityRunnable;
import io.fabric.sdk.android.services.common.ExecutorUtils;

class ExecutorUtils
extends BackgroundPriorityRunnable {
    final /* synthetic */ Runnable val$runnable;

    ExecutorUtils(Runnable runnable) {
        this.val$runnable = runnable;
    }

    @Override
    public void onRun() {
        this.val$runnable.run();
    }
}
