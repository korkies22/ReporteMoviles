/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 *  android.util.Log
 */
package android.support.v7.util;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.util.MessageThreadUtil;
import android.support.v7.util.ThreadUtil;
import android.support.v7.util.TileList;
import android.util.Log;

class MessageThreadUtil
implements ThreadUtil.MainThreadCallback<T> {
    static final int ADD_TILE = 2;
    static final int REMOVE_TILE = 3;
    static final int UPDATE_ITEM_COUNT = 1;
    private final Handler mMainThreadHandler = new Handler(Looper.getMainLooper());
    private Runnable mMainThreadRunnable = new Runnable(){

        @Override
        public void run() {
            MessageThreadUtil.SyncQueueItem syncQueueItem = 1.this.mQueue.next();
            while (syncQueueItem != null) {
                switch (syncQueueItem.what) {
                    default: {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Unsupported message, what=");
                        stringBuilder.append(syncQueueItem.what);
                        Log.e((String)"ThreadUtil", (String)stringBuilder.toString());
                        break;
                    }
                    case 3: {
                        1.this.val$callback.removeTile(syncQueueItem.arg1, syncQueueItem.arg2);
                        break;
                    }
                    case 2: {
                        1.this.val$callback.addTile(syncQueueItem.arg1, (TileList.Tile)syncQueueItem.data);
                        break;
                    }
                    case 1: {
                        1.this.val$callback.updateItemCount(syncQueueItem.arg1, syncQueueItem.arg2);
                    }
                }
                syncQueueItem = 1.this.mQueue.next();
            }
        }
    };
    final MessageThreadUtil.MessageQueue mQueue = new MessageThreadUtil.MessageQueue();
    final /* synthetic */ ThreadUtil.MainThreadCallback val$callback;

    MessageThreadUtil(ThreadUtil.MainThreadCallback mainThreadCallback) {
        this.val$callback = mainThreadCallback;
    }

    private void sendMessage(MessageThreadUtil.SyncQueueItem syncQueueItem) {
        this.mQueue.sendMessage(syncQueueItem);
        this.mMainThreadHandler.post(this.mMainThreadRunnable);
    }

    @Override
    public void addTile(int n, TileList.Tile<T> tile) {
        this.sendMessage(MessageThreadUtil.SyncQueueItem.obtainMessage(2, n, tile));
    }

    @Override
    public void removeTile(int n, int n2) {
        this.sendMessage(MessageThreadUtil.SyncQueueItem.obtainMessage(3, n, n2));
    }

    @Override
    public void updateItemCount(int n, int n2) {
        this.sendMessage(MessageThreadUtil.SyncQueueItem.obtainMessage(1, n, n2));
    }

}
