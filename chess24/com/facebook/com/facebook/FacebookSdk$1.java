/*
 * Decompiled with CFR 0_134.
 */
package com.facebook;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

static final class FacebookSdk
implements ThreadFactory {
    private final AtomicInteger counter = new AtomicInteger(0);

    FacebookSdk() {
    }

    @Override
    public Thread newThread(Runnable runnable) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("FacebookSdk #");
        stringBuilder.append(this.counter.incrementAndGet());
        return new Thread(runnable, stringBuilder.toString());
    }
}
