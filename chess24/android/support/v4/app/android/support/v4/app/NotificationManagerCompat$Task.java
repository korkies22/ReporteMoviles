/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package android.support.v4.app;

import android.os.RemoteException;
import android.support.v4.app.INotificationSideChannel;
import android.support.v4.app.NotificationManagerCompat;

private static interface NotificationManagerCompat.Task {
    public void send(INotificationSideChannel var1) throws RemoteException;
}
