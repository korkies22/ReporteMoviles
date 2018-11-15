/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.internal;

import com.facebook.internal.WorkQueue;

class WorkQueue
implements Runnable {
    final /* synthetic */ WorkQueue.WorkNode val$node;

    WorkQueue(WorkQueue.WorkNode workNode) {
        this.val$node = workNode;
    }

    @Override
    public void run() {
        try {
            this.val$node.getCallback().run();
            return;
        }
        finally {
            WorkQueue.this.finishItemAndStartNew(this.val$node);
        }
    }
}
