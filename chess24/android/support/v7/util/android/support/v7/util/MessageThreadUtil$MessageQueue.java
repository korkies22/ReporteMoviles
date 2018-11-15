/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.util;

import android.support.v7.util.MessageThreadUtil;

static class MessageThreadUtil.MessageQueue {
    private MessageThreadUtil.SyncQueueItem mRoot;

    MessageThreadUtil.MessageQueue() {
    }

    MessageThreadUtil.SyncQueueItem next() {
        synchronized (this) {
            MessageThreadUtil.SyncQueueItem syncQueueItem;
            block4 : {
                syncQueueItem = this.mRoot;
                if (syncQueueItem != null) break block4;
                return null;
            }
            syncQueueItem = this.mRoot;
            this.mRoot = this.mRoot.next;
            return syncQueueItem;
        }
    }

    void removeMessages(int n) {
        synchronized (this) {
            block8 : {
                MessageThreadUtil.SyncQueueItem syncQueueItem;
                while (this.mRoot != null && this.mRoot.what == n) {
                    syncQueueItem = this.mRoot;
                    this.mRoot = this.mRoot.next;
                    syncQueueItem.recycle();
                }
                if (this.mRoot == null) break block8;
                MessageThreadUtil.SyncQueueItem syncQueueItem2 = this.mRoot;
                syncQueueItem = syncQueueItem2.next;
                while (syncQueueItem != null) {
                    MessageThreadUtil.SyncQueueItem syncQueueItem3 = syncQueueItem.next;
                    if (syncQueueItem.what == n) {
                        syncQueueItem2.next = syncQueueItem3;
                        syncQueueItem.recycle();
                    } else {
                        syncQueueItem2 = syncQueueItem;
                    }
                    syncQueueItem = syncQueueItem3;
                }
            }
            return;
        }
    }

    void sendMessage(MessageThreadUtil.SyncQueueItem syncQueueItem) {
        synchronized (this) {
            if (this.mRoot == null) {
                this.mRoot = syncQueueItem;
                return;
            }
            MessageThreadUtil.SyncQueueItem syncQueueItem2 = this.mRoot;
            while (syncQueueItem2.next != null) {
                syncQueueItem2 = syncQueueItem2.next;
            }
            syncQueueItem2.next = syncQueueItem;
            return;
        }
    }

    void sendMessageAtFrontOfQueue(MessageThreadUtil.SyncQueueItem syncQueueItem) {
        synchronized (this) {
            syncQueueItem.next = this.mRoot;
            this.mRoot = syncQueueItem;
            return;
        }
    }
}
