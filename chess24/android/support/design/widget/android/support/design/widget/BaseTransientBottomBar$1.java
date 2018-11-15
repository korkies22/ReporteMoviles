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

static final class BaseTransientBottomBar
implements Handler.Callback {
    BaseTransientBottomBar() {
    }

    public boolean handleMessage(Message message) {
        switch (message.what) {
            default: {
                return false;
            }
            case 1: {
                ((android.support.design.widget.BaseTransientBottomBar)message.obj).hideView(message.arg1);
                return true;
            }
            case 0: 
        }
        ((android.support.design.widget.BaseTransientBottomBar)message.obj).showView();
        return true;
    }
}
