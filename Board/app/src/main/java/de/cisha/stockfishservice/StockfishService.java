// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.stockfishservice;

import android.os.IBinder;
import android.content.Intent;
import android.os.RemoteException;
import android.util.Log;
import android.os.Message;
import android.os.Bundle;
import android.os.Handler;
import android.os.Messenger;
import android.app.Service;

public class StockfishService extends Service
{
    public static final String MSG_KEY = "MSG_KEY";
    private static final String TAG = "StockfishService";
    private Messenger mClient;
    private final Messenger mMessenger;
    
    static {
        System.loadLibrary("stockfish-lib");
    }
    
    public StockfishService() {
        this.mMessenger = new Messenger((Handler)new IncomingHandler());
    }
    
    public native void clientToEngine(final String p0);
    
    public void engineToClient(final String s) {
        if (this.mClient != null) {
            final Bundle data = new Bundle();
            data.setClassLoader(Thread.currentThread().getContextClassLoader());
            data.putString("MSG_KEY", s);
            final Message obtain = Message.obtain();
            obtain.setData(data);
            while (true) {
                try {
                    this.mClient.send(obtain);
                    return;
                    Log.d("StockfishService", "Client is not reachable.");
                    this.mClient = null;
                    return;
                }
                catch (RemoteException ex) {}
                continue;
            }
        }
    }
    
    public IBinder onBind(final Intent intent) {
        return this.mMessenger.getBinder();
    }
    
    class IncomingHandler extends Handler
    {
        public void handleMessage(final Message message) {
            StockfishService.this.mClient = message.replyTo;
            if (StockfishService.this.mClient == null) {
                Log.e("StockfishService", "msg.replyTo is null");
                super.handleMessage(message);
                return;
            }
            final Bundle peekData = message.peekData();
            if (peekData == null) {
                super.handleMessage(message);
                return;
            }
            final String string = peekData.getString("MSG_KEY");
            if (string == null) {
                super.handleMessage(message);
                return;
            }
            StockfishService.this.clientToEngine(string);
        }
    }
}
