/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Notification
 *  android.os.RemoteException
 */
package android.support.v4.app;

import android.app.Notification;
import android.os.RemoteException;
import android.support.v4.app.INotificationSideChannel;
import android.support.v4.app.NotificationCompatSideChannelService;

private class NotificationCompatSideChannelService.NotificationSideChannelStub
extends INotificationSideChannel.Stub {
    NotificationCompatSideChannelService.NotificationSideChannelStub() {
    }

    @Override
    public void cancel(String string, int n, String string2) throws RemoteException {
        NotificationCompatSideChannelService.this.checkPermission(NotificationCompatSideChannelService.NotificationSideChannelStub.getCallingUid(), string);
        long l = NotificationCompatSideChannelService.NotificationSideChannelStub.clearCallingIdentity();
        try {
            NotificationCompatSideChannelService.this.cancel(string, n, string2);
            return;
        }
        finally {
            NotificationCompatSideChannelService.NotificationSideChannelStub.restoreCallingIdentity((long)l);
        }
    }

    @Override
    public void cancelAll(String string) {
        NotificationCompatSideChannelService.this.checkPermission(NotificationCompatSideChannelService.NotificationSideChannelStub.getCallingUid(), string);
        long l = NotificationCompatSideChannelService.NotificationSideChannelStub.clearCallingIdentity();
        try {
            NotificationCompatSideChannelService.this.cancelAll(string);
            return;
        }
        finally {
            NotificationCompatSideChannelService.NotificationSideChannelStub.restoreCallingIdentity((long)l);
        }
    }

    @Override
    public void notify(String string, int n, String string2, Notification notification) throws RemoteException {
        NotificationCompatSideChannelService.this.checkPermission(NotificationCompatSideChannelService.NotificationSideChannelStub.getCallingUid(), string);
        long l = NotificationCompatSideChannelService.NotificationSideChannelStub.clearCallingIdentity();
        try {
            NotificationCompatSideChannelService.this.notify(string, n, string2, notification);
            return;
        }
        finally {
            NotificationCompatSideChannelService.NotificationSideChannelStub.restoreCallingIdentity((long)l);
        }
    }
}
