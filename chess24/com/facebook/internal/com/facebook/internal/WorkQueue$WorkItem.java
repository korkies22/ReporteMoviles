/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.internal;

import com.facebook.internal.WorkQueue;

public static interface WorkQueue.WorkItem {
    public boolean cancel();

    public boolean isRunning();

    public void moveToFront();
}
