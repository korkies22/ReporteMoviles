/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.internal;

import com.facebook.internal.WorkQueue;

private class WorkQueue.WorkNode
implements WorkQueue.WorkItem {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final Runnable callback;
    private boolean isRunning;
    private WorkQueue.WorkNode next;
    private WorkQueue.WorkNode prev;

    WorkQueue.WorkNode(Runnable runnable) {
        this.callback = runnable;
    }

    WorkQueue.WorkNode addToList(WorkQueue.WorkNode workNode, boolean bl) {
        if (workNode == null) {
            this.prev = this;
            this.next = this;
            workNode = this;
        } else {
            this.next = workNode;
            this.prev = workNode.prev;
            WorkQueue.WorkNode workNode2 = this.next;
            this.prev.next = this;
            workNode2.prev = this;
        }
        if (bl) {
            workNode = this;
        }
        return workNode;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean cancel() {
        Object object = WorkQueue.this.workLock;
        synchronized (object) {
            if (!this.isRunning()) {
                WorkQueue.this.pendingJobs = this.removeFromList(WorkQueue.this.pendingJobs);
                return true;
            }
            return false;
        }
    }

    Runnable getCallback() {
        return this.callback;
    }

    WorkQueue.WorkNode getNext() {
        return this.next;
    }

    @Override
    public boolean isRunning() {
        return this.isRunning;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void moveToFront() {
        Object object = WorkQueue.this.workLock;
        synchronized (object) {
            if (!this.isRunning()) {
                WorkQueue.this.pendingJobs = this.removeFromList(WorkQueue.this.pendingJobs);
                WorkQueue.this.pendingJobs = this.addToList(WorkQueue.this.pendingJobs, true);
            }
            return;
        }
    }

    WorkQueue.WorkNode removeFromList(WorkQueue.WorkNode workNode) {
        WorkQueue.WorkNode workNode2 = workNode;
        if (workNode == this) {
            workNode2 = this.next == this ? null : this.next;
        }
        this.next.prev = this.prev;
        this.prev.next = this.next;
        this.prev = null;
        this.next = null;
        return workNode2;
    }

    void setIsRunning(boolean bl) {
        this.isRunning = bl;
    }

    void verify(boolean bl) {
    }
}
