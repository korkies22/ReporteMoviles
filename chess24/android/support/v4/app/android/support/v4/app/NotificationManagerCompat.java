/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.AppOpsManager
 *  android.app.Notification
 *  android.app.NotificationManager
 *  android.content.ComponentName
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.ResolveInfo
 *  android.content.pm.ServiceInfo
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.DeadObjectException
 *  android.os.Handler
 *  android.os.Handler$Callback
 *  android.os.HandlerThread
 *  android.os.IBinder
 *  android.os.Looper
 *  android.os.Message
 *  android.os.RemoteException
 *  android.provider.Settings
 *  android.provider.Settings$Secure
 *  android.util.Log
 */
package android.support.v4.app;

import android.app.AppOpsManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.provider.Settings;
import android.support.annotation.GuardedBy;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.INotificationSideChannel;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class NotificationManagerCompat {
    public static final String ACTION_BIND_SIDE_CHANNEL = "android.support.BIND_NOTIFICATION_SIDE_CHANNEL";
    private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
    public static final String EXTRA_USE_SIDE_CHANNEL = "android.support.useSideChannel";
    public static final int IMPORTANCE_DEFAULT = 3;
    public static final int IMPORTANCE_HIGH = 4;
    public static final int IMPORTANCE_LOW = 2;
    public static final int IMPORTANCE_MAX = 5;
    public static final int IMPORTANCE_MIN = 1;
    public static final int IMPORTANCE_NONE = 0;
    public static final int IMPORTANCE_UNSPECIFIED = -1000;
    static final int MAX_SIDE_CHANNEL_SDK_VERSION = 19;
    private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";
    private static final String SETTING_ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
    private static final int SIDE_CHANNEL_RETRY_BASE_INTERVAL_MS = 1000;
    private static final int SIDE_CHANNEL_RETRY_MAX_COUNT = 6;
    private static final String TAG = "NotifManCompat";
    @GuardedBy(value="sEnabledNotificationListenersLock")
    private static Set<String> sEnabledNotificationListenerPackages;
    @GuardedBy(value="sEnabledNotificationListenersLock")
    private static String sEnabledNotificationListeners;
    private static final Object sEnabledNotificationListenersLock;
    private static final Object sLock;
    @GuardedBy(value="sLock")
    private static SideChannelManager sSideChannelManager;
    private final Context mContext;
    private final NotificationManager mNotificationManager;

    static {
        sEnabledNotificationListenersLock = new Object();
        sEnabledNotificationListenerPackages = new HashSet<String>();
        sLock = new Object();
    }

    private NotificationManagerCompat(Context context) {
        this.mContext = context;
        this.mNotificationManager = (NotificationManager)this.mContext.getSystemService("notification");
    }

    @NonNull
    public static NotificationManagerCompat from(@NonNull Context context) {
        return new NotificationManagerCompat(context);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @NonNull
    public static Set<String> getEnabledListenerPackages(@NonNull Context object) {
        String string = Settings.Secure.getString((ContentResolver)object.getContentResolver(), (String)SETTING_ENABLED_NOTIFICATION_LISTENERS);
        object = sEnabledNotificationListenersLock;
        synchronized (object) {
            HashSet<String> hashSet;
            int n;
            String[] arrstring;
            int n2;
            block7 : {
                if (string == null) return sEnabledNotificationListenerPackages;
                try {
                    if (string.equals(sEnabledNotificationListeners)) return sEnabledNotificationListenerPackages;
                    arrstring = string.split(":");
                    hashSet = new HashSet<String>(arrstring.length);
                    n = arrstring.length;
                    n2 = 0;
                    break block7;
                }
                catch (Throwable throwable) {}
                throw throwable;
            }
            do {
                if (n2 < n) {
                    ComponentName componentName = ComponentName.unflattenFromString((String)arrstring[n2]);
                    if (componentName != null) {
                        hashSet.add(componentName.getPackageName());
                    }
                } else {
                    sEnabledNotificationListenerPackages = hashSet;
                    sEnabledNotificationListeners = string;
                    return sEnabledNotificationListenerPackages;
                }
                ++n2;
            } while (true);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void pushSideChannelQueue(Task task) {
        Object object = sLock;
        synchronized (object) {
            if (sSideChannelManager == null) {
                sSideChannelManager = new SideChannelManager(this.mContext.getApplicationContext());
            }
            sSideChannelManager.queueTask(task);
            return;
        }
    }

    private static boolean useSideChannelForNotification(Notification notification) {
        if ((notification = NotificationCompat.getExtras(notification)) != null && notification.getBoolean(EXTRA_USE_SIDE_CHANNEL)) {
            return true;
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean areNotificationsEnabled() {
        if (Build.VERSION.SDK_INT >= 24) {
            return this.mNotificationManager.areNotificationsEnabled();
        }
        if (Build.VERSION.SDK_INT < 19) return true;
        AppOpsManager appOpsManager = (AppOpsManager)this.mContext.getSystemService("appops");
        Object object = this.mContext.getApplicationInfo();
        String string = this.mContext.getApplicationContext().getPackageName();
        int n = object.uid;
        try {
            object = Class.forName(AppOpsManager.class.getName());
            n = (Integer)object.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE, String.class).invoke((Object)appOpsManager, (int)((Integer)object.getDeclaredField(OP_POST_NOTIFICATION).get(Integer.class)), n, string);
            if (n != 0) return false;
            return true;
        }
        catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException | NoSuchMethodException | RuntimeException | InvocationTargetException exception) {
            return true;
        }
    }

    public void cancel(int n) {
        this.cancel(null, n);
    }

    public void cancel(@Nullable String string, int n) {
        this.mNotificationManager.cancel(string, n);
        if (Build.VERSION.SDK_INT <= 19) {
            this.pushSideChannelQueue(new CancelTask(this.mContext.getPackageName(), n, string));
        }
    }

    public void cancelAll() {
        this.mNotificationManager.cancelAll();
        if (Build.VERSION.SDK_INT <= 19) {
            this.pushSideChannelQueue(new CancelTask(this.mContext.getPackageName()));
        }
    }

    public int getImportance() {
        if (Build.VERSION.SDK_INT >= 24) {
            return this.mNotificationManager.getImportance();
        }
        return -1000;
    }

    public void notify(int n, @NonNull Notification notification) {
        this.notify(null, n, notification);
    }

    public void notify(@Nullable String string, int n, @NonNull Notification notification) {
        if (NotificationManagerCompat.useSideChannelForNotification(notification)) {
            this.pushSideChannelQueue(new NotifyTask(this.mContext.getPackageName(), n, string, notification));
            this.mNotificationManager.cancel(string, n);
            return;
        }
        this.mNotificationManager.notify(string, n, notification);
    }

    private static class CancelTask
    implements Task {
        final boolean all;
        final int id;
        final String packageName;
        final String tag;

        CancelTask(String string) {
            this.packageName = string;
            this.id = 0;
            this.tag = null;
            this.all = true;
        }

        CancelTask(String string, int n, String string2) {
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

    private static class NotifyTask
    implements Task {
        final int id;
        final Notification notif;
        final String packageName;
        final String tag;

        NotifyTask(String string, int n, String string2, Notification notification) {
            this.packageName = string;
            this.id = n;
            this.tag = string2;
            this.notif = notification;
        }

        @Override
        public void send(INotificationSideChannel iNotificationSideChannel) throws RemoteException {
            iNotificationSideChannel.notify(this.packageName, this.id, this.tag, this.notif);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("NotifyTask[");
            stringBuilder.append("packageName:");
            stringBuilder.append(this.packageName);
            stringBuilder.append(", id:");
            stringBuilder.append(this.id);
            stringBuilder.append(", tag:");
            stringBuilder.append(this.tag);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }
    }

    private static class ServiceConnectedEvent {
        final ComponentName componentName;
        final IBinder iBinder;

        ServiceConnectedEvent(ComponentName componentName, IBinder iBinder) {
            this.componentName = componentName;
            this.iBinder = iBinder;
        }
    }

    private static class SideChannelManager
    implements Handler.Callback,
    ServiceConnection {
        private static final int MSG_QUEUE_TASK = 0;
        private static final int MSG_RETRY_LISTENER_QUEUE = 3;
        private static final int MSG_SERVICE_CONNECTED = 1;
        private static final int MSG_SERVICE_DISCONNECTED = 2;
        private Set<String> mCachedEnabledPackages = new HashSet<String>();
        private final Context mContext;
        private final Handler mHandler;
        private final HandlerThread mHandlerThread;
        private final Map<ComponentName, SideChannelManager$ListenerRecord> mRecordMap = new HashMap<ComponentName, SideChannelManager$ListenerRecord>();

        SideChannelManager(Context context) {
            this.mContext = context;
            this.mHandlerThread = new HandlerThread("NotificationManagerCompat");
            this.mHandlerThread.start();
            this.mHandler = new Handler(this.mHandlerThread.getLooper(), (Handler.Callback)this);
        }

        private boolean ensureServiceBound(SideChannelManager$ListenerRecord sideChannelManager$ListenerRecord) {
            if (sideChannelManager$ListenerRecord.bound) {
                return true;
            }
            Object object = new Intent(NotificationManagerCompat.ACTION_BIND_SIDE_CHANNEL).setComponent(sideChannelManager$ListenerRecord.componentName);
            sideChannelManager$ListenerRecord.bound = this.mContext.bindService((Intent)object, (ServiceConnection)this, 33);
            if (sideChannelManager$ListenerRecord.bound) {
                sideChannelManager$ListenerRecord.retryCount = 0;
            } else {
                object = new StringBuilder();
                object.append("Unable to bind to listener ");
                object.append((Object)sideChannelManager$ListenerRecord.componentName);
                Log.w((String)NotificationManagerCompat.TAG, (String)object.toString());
                this.mContext.unbindService((ServiceConnection)this);
            }
            return sideChannelManager$ListenerRecord.bound;
        }

        private void ensureServiceUnbound(SideChannelManager$ListenerRecord sideChannelManager$ListenerRecord) {
            if (sideChannelManager$ListenerRecord.bound) {
                this.mContext.unbindService((ServiceConnection)this);
                sideChannelManager$ListenerRecord.bound = false;
            }
            sideChannelManager$ListenerRecord.service = null;
        }

        private void handleQueueTask(Task task) {
            this.updateListenerMap();
            for (SideChannelManager$ListenerRecord sideChannelManager$ListenerRecord : this.mRecordMap.values()) {
                sideChannelManager$ListenerRecord.taskQueue.add(task);
                this.processListenerQueue(sideChannelManager$ListenerRecord);
            }
        }

        private void handleRetryListenerQueue(ComponentName object) {
            if ((object = this.mRecordMap.get(object)) != null) {
                this.processListenerQueue((SideChannelManager$ListenerRecord)object);
            }
        }

        private void handleServiceConnected(ComponentName object, IBinder iBinder) {
            if ((object = this.mRecordMap.get(object)) != null) {
                object.service = INotificationSideChannel.Stub.asInterface(iBinder);
                object.retryCount = 0;
                this.processListenerQueue((SideChannelManager$ListenerRecord)object);
            }
        }

        private void handleServiceDisconnected(ComponentName object) {
            if ((object = this.mRecordMap.get(object)) != null) {
                this.ensureServiceUnbound((SideChannelManager$ListenerRecord)object);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void processListenerQueue(SideChannelManager$ListenerRecord sideChannelManager$ListenerRecord) {
            Object object;
            if (Log.isLoggable((String)NotificationManagerCompat.TAG, (int)3)) {
                object = new StringBuilder();
                object.append("Processing component ");
                object.append((Object)sideChannelManager$ListenerRecord.componentName);
                object.append(", ");
                object.append(sideChannelManager$ListenerRecord.taskQueue.size());
                object.append(" queued tasks");
                Log.d((String)NotificationManagerCompat.TAG, (String)object.toString());
            }
            if (sideChannelManager$ListenerRecord.taskQueue.isEmpty()) {
                return;
            }
            if (!this.ensureServiceBound(sideChannelManager$ListenerRecord) || sideChannelManager$ListenerRecord.service == null) {
                this.scheduleListenerRetry(sideChannelManager$ListenerRecord);
                return;
            }
            while ((object = sideChannelManager$ListenerRecord.taskQueue.peek()) != null) {
                StringBuilder stringBuilder;
                try {
                    if (Log.isLoggable((String)NotificationManagerCompat.TAG, (int)3)) {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("Sending task ");
                        stringBuilder.append(object);
                        Log.d((String)NotificationManagerCompat.TAG, (String)stringBuilder.toString());
                    }
                    object.send(sideChannelManager$ListenerRecord.service);
                    sideChannelManager$ListenerRecord.taskQueue.remove();
                    continue;
                }
                catch (RemoteException remoteException) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("RemoteException communicating with ");
                    stringBuilder.append((Object)sideChannelManager$ListenerRecord.componentName);
                    Log.w((String)NotificationManagerCompat.TAG, (String)stringBuilder.toString(), (Throwable)remoteException);
                    break;
                }
                catch (DeadObjectException deadObjectException) {}
                if (!Log.isLoggable((String)NotificationManagerCompat.TAG, (int)3)) break;
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Remote service has died: ");
                stringBuilder2.append((Object)sideChannelManager$ListenerRecord.componentName);
                Log.d((String)NotificationManagerCompat.TAG, (String)stringBuilder2.toString());
                break;
            }
            if (!sideChannelManager$ListenerRecord.taskQueue.isEmpty()) {
                this.scheduleListenerRetry(sideChannelManager$ListenerRecord);
            }
        }

        private void scheduleListenerRetry(SideChannelManager$ListenerRecord sideChannelManager$ListenerRecord) {
            if (this.mHandler.hasMessages(3, (Object)sideChannelManager$ListenerRecord.componentName)) {
                return;
            }
            ++sideChannelManager$ListenerRecord.retryCount;
            if (sideChannelManager$ListenerRecord.retryCount > 6) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Giving up on delivering ");
                stringBuilder.append(sideChannelManager$ListenerRecord.taskQueue.size());
                stringBuilder.append(" tasks to ");
                stringBuilder.append((Object)sideChannelManager$ListenerRecord.componentName);
                stringBuilder.append(" after ");
                stringBuilder.append(sideChannelManager$ListenerRecord.retryCount);
                stringBuilder.append(" retries");
                Log.w((String)NotificationManagerCompat.TAG, (String)stringBuilder.toString());
                sideChannelManager$ListenerRecord.taskQueue.clear();
                return;
            }
            int n = 1000 * (1 << sideChannelManager$ListenerRecord.retryCount - 1);
            if (Log.isLoggable((String)NotificationManagerCompat.TAG, (int)3)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Scheduling retry for ");
                stringBuilder.append(n);
                stringBuilder.append(" ms");
                Log.d((String)NotificationManagerCompat.TAG, (String)stringBuilder.toString());
            }
            sideChannelManager$ListenerRecord = this.mHandler.obtainMessage(3, (Object)sideChannelManager$ListenerRecord.componentName);
            this.mHandler.sendMessageDelayed((Message)sideChannelManager$ListenerRecord, (long)n);
        }

        private void updateListenerMap() {
            Object object;
            Object object2 = NotificationManagerCompat.getEnabledListenerPackages(this.mContext);
            if (object2.equals(this.mCachedEnabledPackages)) {
                return;
            }
            this.mCachedEnabledPackages = object2;
            List componentName2 = this.mContext.getPackageManager().queryIntentServices(new Intent().setAction(NotificationManagerCompat.ACTION_BIND_SIDE_CHANNEL), 0);
            HashSet<Object> hashSet = new HashSet<Object>();
            for (Object object3 : componentName2) {
                if (!object2.contains(object3.serviceInfo.packageName)) continue;
                object = new ComponentName(object3.serviceInfo.packageName, object3.serviceInfo.name);
                if (object3.serviceInfo.permission != null) {
                    object3 = new StringBuilder();
                    object3.append("Permission present on component ");
                    object3.append(object);
                    object3.append(", not adding listener record.");
                    Log.w((String)NotificationManagerCompat.TAG, (String)object3.toString());
                    continue;
                }
                hashSet.add(object);
            }
            for (ComponentName componentName : hashSet) {
                if (this.mRecordMap.containsKey((Object)componentName)) continue;
                if (Log.isLoggable((String)NotificationManagerCompat.TAG, (int)3)) {
                    object = new StringBuilder();
                    object.append("Adding listener record for ");
                    object.append((Object)componentName);
                    Log.d((String)NotificationManagerCompat.TAG, (String)object.toString());
                }
                this.mRecordMap.put(componentName, new SideChannelManager$ListenerRecord(componentName));
            }
            object2 = this.mRecordMap.entrySet().iterator();
            while (object2.hasNext()) {
                Map.Entry<ComponentName, SideChannelManager$ListenerRecord> entry = object2.next();
                if (hashSet.contains((Object)entry.getKey())) continue;
                if (Log.isLoggable((String)NotificationManagerCompat.TAG, (int)3)) {
                    object = new StringBuilder();
                    object.append("Removing listener record for ");
                    object.append((Object)entry.getKey());
                    Log.d((String)NotificationManagerCompat.TAG, (String)object.toString());
                }
                this.ensureServiceUnbound(entry.getValue());
                object2.remove();
            }
        }

        public boolean handleMessage(Message object) {
            switch (object.what) {
                default: {
                    return false;
                }
                case 3: {
                    this.handleRetryListenerQueue((ComponentName)object.obj);
                    return true;
                }
                case 2: {
                    this.handleServiceDisconnected((ComponentName)object.obj);
                    return true;
                }
                case 1: {
                    object = (ServiceConnectedEvent)object.obj;
                    this.handleServiceConnected(object.componentName, object.iBinder);
                    return true;
                }
                case 0: 
            }
            this.handleQueueTask((Task)object.obj);
            return true;
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            if (Log.isLoggable((String)NotificationManagerCompat.TAG, (int)3)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Connected to service ");
                stringBuilder.append((Object)componentName);
                Log.d((String)NotificationManagerCompat.TAG, (String)stringBuilder.toString());
            }
            this.mHandler.obtainMessage(1, (Object)new ServiceConnectedEvent(componentName, iBinder)).sendToTarget();
        }

        public void onServiceDisconnected(ComponentName componentName) {
            if (Log.isLoggable((String)NotificationManagerCompat.TAG, (int)3)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Disconnected from service ");
                stringBuilder.append((Object)componentName);
                Log.d((String)NotificationManagerCompat.TAG, (String)stringBuilder.toString());
            }
            this.mHandler.obtainMessage(2, (Object)componentName).sendToTarget();
        }

        public void queueTask(Task task) {
            this.mHandler.obtainMessage(0, (Object)task).sendToTarget();
        }
    }

    private static class SideChannelManager$ListenerRecord {
        boolean bound = false;
        final ComponentName componentName;
        int retryCount = 0;
        INotificationSideChannel service;
        ArrayDeque<Task> taskQueue = new ArrayDeque();

        SideChannelManager$ListenerRecord(ComponentName componentName) {
            this.componentName = componentName;
        }
    }

    private static interface Task {
        public void send(INotificationSideChannel var1) throws RemoteException;
    }

}
