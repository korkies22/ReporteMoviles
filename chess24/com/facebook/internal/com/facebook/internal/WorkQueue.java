/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.internal;

import com.facebook.FacebookSdk;
import java.util.concurrent.Executor;

public class WorkQueue {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int DEFAULT_MAX_CONCURRENT = 8;
    private final Executor executor;
    private final int maxConcurrent;
    private WorkNode pendingJobs;
    private int runningCount = 0;
    private WorkNode runningJobs = null;
    private final Object workLock = new Object();

    public WorkQueue() {
        this(8);
    }

    public WorkQueue(int n) {
        this(n, FacebookSdk.getExecutor());
    }

    public WorkQueue(int n, Executor executor) {
        this.maxConcurrent = n;
        this.executor = executor;
    }

    private void execute(final WorkNode workNode) {
        this.executor.execute(new Runnable(){

            @Override
            public void run() {
                try {
                    workNode.getCallback().run();
                    return;
                }
                finally {
                    WorkQueue.this.finishItemAndStartNew(workNode);
                }
            }
        });
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void finishItemAndStartNew(WorkNode var1_1) {
        block6 : {
            block5 : {
                var3_3 = this.workLock;
                // MONITORENTER : var3_3
                if (var1_1 == null) ** GOTO lbl7
                this.runningJobs = var1_1.removeFromList(this.runningJobs);
                --this.runningCount;
lbl7: // 2 sources:
                if (this.runningCount >= this.maxConcurrent) break block5;
                var1_1 = var2_4 = this.pendingJobs;
                if (var2_4 != null) {
                    this.pendingJobs = var2_4.removeFromList(this.pendingJobs);
                    this.runningJobs = var2_4.addToList(this.runningJobs, false);
                    ++this.runningCount;
                    var2_4.setIsRunning(true);
                    var1_1 = var2_4;
                }
                // MONITOREXIT : var3_3
                break block6;
            }
            var1_1 = null;
        }
        if (var1_1 == null) return;
        this.execute(var1_1);
    }

    private void startItem() {
        this.finishItemAndStartNew(null);
    }

    public WorkItem addActiveWorkItem(Runnable runnable) {
        return this.addActiveWorkItem(runnable, true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public WorkItem addActiveWorkItem(Runnable object, boolean bl) {
        WorkNode workNode = new WorkNode((Runnable)object);
        object = this.workLock;
        synchronized (object) {
            this.pendingJobs = workNode.addToList(this.pendingJobs, bl);
        }
        this.startItem();
        return workNode;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void validate() {
        Object object = this.workLock;
        synchronized (object) {
            if (this.runningJobs != null) {
                WorkNode workNode;
                WorkNode workNode2 = this.runningJobs;
                do {
                    workNode2.verify(true);
                    workNode2 = workNode = workNode2.getNext();
                } while (workNode != this.runningJobs);
            }
            return;
        }
    }

    public static interface WorkItem {
        public boolean cancel();

        public boolean isRunning();

        public void moveToFront();
    }

    private class WorkNode
    implements WorkItem {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final Runnable callback;
        private boolean isRunning;
        private WorkNode next;
        private WorkNode prev;

        WorkNode(Runnable runnable) {
            this.callback = runnable;
        }

        WorkNode addToList(WorkNode workNode, boolean bl) {
            if (workNode == null) {
                this.prev = this;
                this.next = this;
                workNode = this;
            } else {
                this.next = workNode;
                this.prev = workNode.prev;
                WorkNode workNode2 = this.next;
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

        WorkNode getNext() {
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

        WorkNode removeFromList(WorkNode workNode) {
            WorkNode workNode2 = workNode;
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

}
