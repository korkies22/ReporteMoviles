/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.internal;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

class LockOnGetVariable
implements Callable<Void> {
    final /* synthetic */ Callable val$callable;

    LockOnGetVariable(Callable callable) {
        this.val$callable = callable;
    }

    @Override
    public Void call() throws Exception {
        try {
            LockOnGetVariable.this.value = this.val$callable.call();
            return null;
        }
        finally {
            LockOnGetVariable.this.initLatch.countDown();
        }
    }
}
