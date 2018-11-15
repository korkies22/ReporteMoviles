/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 *  android.os.Handler
 *  android.os.Message
 *  android.os.RemoteException
 */
package com.google.android.gms.iid;

import android.os.Binder;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import com.google.android.gms.iid.MessengerCompat;
import com.google.android.gms.iid.zzb;

private final class MessengerCompat.zza
extends zzb.zza {
    Handler handler;

    MessengerCompat.zza(MessengerCompat messengerCompat, Handler handler) {
        this.handler = handler;
    }

    @Override
    public void send(Message message) throws RemoteException {
        message.arg2 = Binder.getCallingUid();
        this.handler.dispatchMessage(message);
    }
}
