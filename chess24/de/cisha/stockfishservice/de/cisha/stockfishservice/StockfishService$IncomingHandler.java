/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Message
 *  android.os.Messenger
 *  android.util.Log
 */
package de.cisha.stockfishservice;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import de.cisha.stockfishservice.StockfishService;

class StockfishService.IncomingHandler
extends Handler {
    StockfishService.IncomingHandler() {
    }

    public void handleMessage(Message message) {
        StockfishService.this.mClient = message.replyTo;
        if (StockfishService.this.mClient == null) {
            Log.e((String)StockfishService.TAG, (String)"msg.replyTo is null");
            super.handleMessage(message);
            return;
        }
        Object object = message.peekData();
        if (object == null) {
            super.handleMessage(message);
            return;
        }
        if ((object = object.getString(StockfishService.MSG_KEY)) == null) {
            super.handleMessage(message);
            return;
        }
        StockfishService.this.clientToEngine((String)object);
    }
}
