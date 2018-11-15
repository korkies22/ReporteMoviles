/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.os.IBinder
 */
package android.support.v4.app;

import android.content.ComponentName;
import android.os.IBinder;
import android.support.v4.app.NotificationManagerCompat;

private static class NotificationManagerCompat.ServiceConnectedEvent {
    final ComponentName componentName;
    final IBinder iBinder;

    NotificationManagerCompat.ServiceConnectedEvent(ComponentName componentName, IBinder iBinder) {
        this.componentName = componentName;
        this.iBinder = iBinder;
    }
}
