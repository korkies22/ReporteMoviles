/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Handler$Callback
 *  android.os.Message
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 */
package android.support.v4.view;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.AsyncLayoutInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

class AsyncLayoutInflater
implements Handler.Callback {
    AsyncLayoutInflater() {
    }

    public boolean handleMessage(Message object) {
        object = (AsyncLayoutInflater.InflateRequest)object.obj;
        if (object.view == null) {
            object.view = AsyncLayoutInflater.this.mInflater.inflate(object.resid, object.parent, false);
        }
        object.callback.onInflateFinished(object.view, object.resid, object.parent);
        AsyncLayoutInflater.this.mInflateThread.releaseRequest((AsyncLayoutInflater.InflateRequest)object);
        return true;
    }
}
