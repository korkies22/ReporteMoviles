/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.concurrency;

public interface Task {
    public Throwable getError();

    public boolean isFinished();

    public void setError(Throwable var1);

    public void setFinished(boolean var1);
}
