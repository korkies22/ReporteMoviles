/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Handler$Callback
 *  android.os.Message
 */
package android.support.design.widget;

import android.os.Handler;
import android.os.Message;
import android.support.design.widget.SnackbarManager;

class SnackbarManager
implements Handler.Callback {
    SnackbarManager() {
    }

    public boolean handleMessage(Message message) {
        if (message.what != 0) {
            return false;
        }
        SnackbarManager.this.handleTimeout((SnackbarManager.SnackbarRecord)message.obj);
        return true;
    }
}
