/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android;

import io.fabric.sdk.android.InitializationCallback;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

class Fabric
implements InitializationCallback {
    final CountDownLatch kitInitializedLatch;
    final /* synthetic */ int val$size;

    Fabric(int n) {
        this.val$size = n;
        this.kitInitializedLatch = new CountDownLatch(this.val$size);
    }

    @Override
    public void failure(Exception exception) {
        Fabric.this.initializationCallback.failure(exception);
    }

    public void success(Object object) {
        this.kitInitializedLatch.countDown();
        if (this.kitInitializedLatch.getCount() == 0L) {
            Fabric.this.initialized.set(true);
            Fabric.this.initializationCallback.success(Fabric.this);
        }
    }
}
