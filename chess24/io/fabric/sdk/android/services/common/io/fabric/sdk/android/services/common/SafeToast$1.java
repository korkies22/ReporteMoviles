/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.common;

import io.fabric.sdk.android.services.concurrency.PriorityRunnable;

class SafeToast
extends PriorityRunnable {
    SafeToast() {
    }

    @Override
    public void run() {
        io.fabric.sdk.android.services.common.SafeToast.super.show();
    }
}
