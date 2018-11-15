/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 */
package android.support.v4.app;

import android.content.ComponentName;
import android.support.v4.app.INotificationSideChannel;
import android.support.v4.app.NotificationManagerCompat;
import java.util.ArrayDeque;

private static class NotificationManagerCompat.SideChannelManager.ListenerRecord {
    boolean bound = false;
    final ComponentName componentName;
    int retryCount = 0;
    INotificationSideChannel service;
    ArrayDeque<NotificationManagerCompat.Task> taskQueue = new ArrayDeque();

    NotificationManagerCompat.SideChannelManager.ListenerRecord(ComponentName componentName) {
        this.componentName = componentName;
    }
}
