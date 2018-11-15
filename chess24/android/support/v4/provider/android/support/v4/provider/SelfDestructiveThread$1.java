/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Handler$Callback
 *  android.os.Message
 */
package android.support.v4.provider;

import android.os.Handler;
import android.os.Message;

class SelfDestructiveThread
implements Handler.Callback {
    SelfDestructiveThread() {
    }

    public boolean handleMessage(Message message) {
        switch (message.what) {
            default: {
                return true;
            }
            case 1: {
                SelfDestructiveThread.this.onInvokeRunnable((Runnable)message.obj);
                return true;
            }
            case 0: 
        }
        SelfDestructiveThread.this.onDestruction();
        return true;
    }
}
