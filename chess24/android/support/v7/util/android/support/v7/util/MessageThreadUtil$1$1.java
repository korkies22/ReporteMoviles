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

class MessageThreadUtil
implements Runnable {
    MessageThreadUtil() {
    }

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
}
