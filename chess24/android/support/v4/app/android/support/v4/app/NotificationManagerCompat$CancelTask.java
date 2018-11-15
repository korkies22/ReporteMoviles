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

private static class NotificationManagerCompat.CancelTask
implements NotificationManagerCompat.Task {
    final boolean all;
    final int id;
    final String packageName;
    final String tag;

    NotificationManagerCompat.CancelTask(String string) {
        this.packageName = string;
        this.id = 0;
        this.tag = null;
        this.all = true;
    }

    NotificationManagerCompat.CancelTask(String string, int n, String string2) {
        this.packageName = string;
        this.id = n;
        this.tag = string2;
        this.all = false;
    }

    @Override
    public void send(INotificationSideChannel iNotificationSideChannel) throws RemoteException {
        if (this.all) {
            iNotificationSideChannel.cancelAll(this.packageName);
            return;
        }
        iNotificationSideChannel.cancel(this.packageName, this.id, this.tag);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("CancelTask[");
        stringBuilder.append("packageName:");
        stringBuilder.append(this.packageName);
        stringBuilder.append(", id:");
        stringBuilder.append(this.id);
        stringBuilder.append(", tag:");
        stringBuilder.append(this.tag);
        stringBuilder.append(", all:");
        stringBuilder.append(this.all);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
