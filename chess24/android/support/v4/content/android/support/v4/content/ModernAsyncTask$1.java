/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.content;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

static final class ModernAsyncTask
implements ThreadFactory {
    private final AtomicInteger mCount = new AtomicInteger(1);

    ModernAsyncTask() {
    }

    @Override
    public Thread newThread(Runnable runnable) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ModernAsyncTask #");
        stringBuilder.append(this.mCount.getAndIncrement());
        return new Thread(runnable, stringBuilder.toString());
    }
}
