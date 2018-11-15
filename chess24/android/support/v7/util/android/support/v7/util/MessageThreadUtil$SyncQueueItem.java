/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.util;

import android.support.v7.util.MessageThreadUtil;

static class MessageThreadUtil.SyncQueueItem {
    private static MessageThreadUtil.SyncQueueItem sPool;
    private static final Object sPoolLock;
    public int arg1;
    public int arg2;
    public int arg3;
    public int arg4;
    public int arg5;
    public Object data;
    private MessageThreadUtil.SyncQueueItem next;
    public int what;

    static {
        sPoolLock = new Object();
    }

    MessageThreadUtil.SyncQueueItem() {
    }

    static /* synthetic */ MessageThreadUtil.SyncQueueItem access$000(MessageThreadUtil.SyncQueueItem syncQueueItem) {
        return syncQueueItem.next;
    }

    static /* synthetic */ MessageThreadUtil.SyncQueueItem access$002(MessageThreadUtil.SyncQueueItem syncQueueItem, MessageThreadUtil.SyncQueueItem syncQueueItem2) {
        syncQueueItem.next = syncQueueItem2;
        return syncQueueItem2;
    }

    static MessageThreadUtil.SyncQueueItem obtainMessage(int n, int n2, int n3) {
        return MessageThreadUtil.SyncQueueItem.obtainMessage(n, n2, n3, 0, 0, 0, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static MessageThreadUtil.SyncQueueItem obtainMessage(int n, int n2, int n3, int n4, int n5, int n6, Object object) {
        Object object2 = sPoolLock;
        synchronized (object2) {
            MessageThreadUtil.SyncQueueItem syncQueueItem;
            if (sPool == null) {
                syncQueueItem = new MessageThreadUtil.SyncQueueItem();
            } else {
                syncQueueItem = sPool;
                sPool = MessageThreadUtil.SyncQueueItem.sPool.next;
                syncQueueItem.next = null;
            }
            syncQueueItem.what = n;
            syncQueueItem.arg1 = n2;
            syncQueueItem.arg2 = n3;
            syncQueueItem.arg3 = n4;
            syncQueueItem.arg4 = n5;
            syncQueueItem.arg5 = n6;
            syncQueueItem.data = object;
            return syncQueueItem;
        }
    }

    static MessageThreadUtil.SyncQueueItem obtainMessage(int n, int n2, Object object) {
        return MessageThreadUtil.SyncQueueItem.obtainMessage(n, n2, 0, 0, 0, 0, object);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void recycle() {
        this.next = null;
        this.arg5 = 0;
        this.arg4 = 0;
        this.arg3 = 0;
        this.arg2 = 0;
        this.arg1 = 0;
        this.what = 0;
        this.data = null;
        Object object = sPoolLock;
        synchronized (object) {
            if (sPool != null) {
                this.next = sPool;
            }
            sPool = this;
            return;
        }
    }
}
