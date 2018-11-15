/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Service
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.Message
 *  android.os.Messenger
 *  android.os.RemoteException
 *  android.util.Log
 */
package de.cisha.stockfishservice;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class StockfishService
extends Service {
    public static final String MSG_KEY = "MSG_KEY";
    private static final String TAG = "StockfishService";
    private Messenger mClient;
    private final Messenger mMessenger = new Messenger((Handler)new IncomingHandler());

    static {
        System.loadLibrary("stockfish-lib");
    }

    public native void clientToEngine(String var1);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void engineToClient(String string) {
        if (this.mClient != null) {
            Bundle bundle = new Bundle();
            bundle.setClassLoader(Thread.currentThread().getContextClassLoader());
            bundle.putString("MSG_KEY", string);
            string = Message.obtain();
            string.setData(bundle);
            try {
                this.mClient.send((Message)string);
                return;
            }
            catch (RemoteException remoteException) {}
            Log.d((String)"StockfishService", (String)"Client is not reachable.");
            this.mClient = null;
        }
    }

    public IBinder onBind(Intent intent) {
        return this.mMessenger.getBinder();
    }

    class IncomingHandler
    extends Handler {
        IncomingHandler() {
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

}
