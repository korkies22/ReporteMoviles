/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package android.support.v7.util;

import android.support.v7.util.MessageThreadUtil;
import android.support.v7.util.ThreadUtil;
import android.support.v7.util.TileList;
import android.util.Log;
import java.util.concurrent.atomic.AtomicBoolean;

class MessageThreadUtil
implements Runnable {
    MessageThreadUtil() {
    }

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
}
