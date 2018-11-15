/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.AsyncTask
 *  android.util.Log
 */
package android.support.v7.util;

import android.os.AsyncTask;
import android.support.v7.util.MessageThreadUtil;
import android.support.v7.util.ThreadUtil;
import android.support.v7.util.TileList;
import android.util.Log;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

class MessageThreadUtil
implements ThreadUtil.BackgroundCallback<T> {
    static final int LOAD_TILE = 3;
    static final int RECYCLE_TILE = 4;
    static final int REFRESH = 1;
    static final int UPDATE_RANGE = 2;
    private Runnable mBackgroundRunnable = new Runnable(){

        @Override
        public void run() {
            block6 : do {
                MessageThreadUtil.SyncQueueItem syncQueueItem;
                if ((syncQueueItem = 2.this.mQueue.next()) == null) {
                    2.this.mBackgroundRunning.set(false);
                    return;
                }
                switch (syncQueueItem.what) {
                    default: {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Unsupported message, what=");
                        stringBuilder.append(syncQueueItem.what);
                        Log.e((String)"ThreadUtil", (String)stringBuilder.toString());
                        continue block6;
                    }
                    case 4: {
                        2.this.val$callback.recycleTile((TileList.Tile)syncQueueItem.data);
                        continue block6;
                    }
                    case 3: {
                        2.this.val$callback.loadTile(syncQueueItem.arg1, syncQueueItem.arg2);
                        continue block6;
                    }
                    case 2: {
                        2.this.mQueue.removeMessages(2);
                        2.this.mQueue.removeMessages(3);
                        2.this.val$callback.updateRange(syncQueueItem.arg1, syncQueueItem.arg2, syncQueueItem.arg3, syncQueueItem.arg4, syncQueueItem.arg5);
                        continue block6;
                    }
                    case 1: 
                }
                2.this.mQueue.removeMessages(1);
                2.this.val$callback.refresh(syncQueueItem.arg1);
            } while (true);
        }
    };
    AtomicBoolean mBackgroundRunning = new AtomicBoolean(false);
    private final Executor mExecutor = AsyncTask.THREAD_POOL_EXECUTOR;
    final MessageThreadUtil.MessageQueue mQueue = new MessageThreadUtil.MessageQueue();
    final /* synthetic */ ThreadUtil.BackgroundCallback val$callback;

    MessageThreadUtil(ThreadUtil.BackgroundCallback backgroundCallback) {
        this.val$callback = backgroundCallback;
    }

    private void maybeExecuteBackgroundRunnable() {
        if (this.mBackgroundRunning.compareAndSet(false, true)) {
            this.mExecutor.execute(this.mBackgroundRunnable);
        }
    }

    private void sendMessage(MessageThreadUtil.SyncQueueItem syncQueueItem) {
        this.mQueue.sendMessage(syncQueueItem);
        this.maybeExecuteBackgroundRunnable();
    }

    private void sendMessageAtFrontOfQueue(MessageThreadUtil.SyncQueueItem syncQueueItem) {
        this.mQueue.sendMessageAtFrontOfQueue(syncQueueItem);
        this.maybeExecuteBackgroundRunnable();
    }

    @Override
    public void loadTile(int n, int n2) {
        this.sendMessage(MessageThreadUtil.SyncQueueItem.obtainMessage(3, n, n2));
    }

    @Override
    public void recycleTile(TileList.Tile<T> tile) {
        this.sendMessage(MessageThreadUtil.SyncQueueItem.obtainMessage(4, 0, tile));
    }

    @Override
    public void refresh(int n) {
        this.sendMessageAtFrontOfQueue(MessageThreadUtil.SyncQueueItem.obtainMessage(1, n, null));
    }

    @Override
    public void updateRange(int n, int n2, int n3, int n4, int n5) {
        this.sendMessageAtFrontOfQueue(MessageThreadUtil.SyncQueueItem.obtainMessage(2, n, n2, n3, n4, n5, null));
    }

}
