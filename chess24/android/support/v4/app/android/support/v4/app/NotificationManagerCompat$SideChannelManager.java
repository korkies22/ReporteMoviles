/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.content.pm.PackageManager
 *  android.content.pm.ResolveInfo
 *  android.content.pm.ServiceInfo
 *  android.os.DeadObjectException
 *  android.os.Handler
 *  android.os.Handler$Callback
 *  android.os.HandlerThread
 *  android.os.IBinder
 *  android.os.Looper
 *  android.os.Message
 *  android.os.RemoteException
 *  android.util.Log
 */
package android.support.v4.app;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.support.v4.app.INotificationSideChannel;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

private static class NotificationManagerCompat.SideChannelManager
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
    private final Map<ComponentName, ListenerRecord> mRecordMap = new HashMap<ComponentName, ListenerRecord>();

    NotificationManagerCompat.SideChannelManager(Context context) {
        this.mContext = context;
        this.mHandlerThread = new HandlerThread("NotificationManagerCompat");
        this.mHandlerThread.start();
        this.mHandler = new Handler(this.mHandlerThread.getLooper(), (Handler.Callback)this);
    }

    private boolean ensureServiceBound(ListenerRecord listenerRecord) {
        if (listenerRecord.bound) {
            return true;
        }
        Object object = new Intent(NotificationManagerCompat.ACTION_BIND_SIDE_CHANNEL).setComponent(listenerRecord.componentName);
        listenerRecord.bound = this.mContext.bindService((Intent)object, (ServiceConnection)this, 33);
        if (listenerRecord.bound) {
            listenerRecord.retryCount = 0;
        } else {
            object = new StringBuilder();
            object.append("Unable to bind to listener ");
            object.append((Object)listenerRecord.componentName);
            Log.w((String)NotificationManagerCompat.TAG, (String)object.toString());
            this.mContext.unbindService((ServiceConnection)this);
        }
        return listenerRecord.bound;
    }

    private void ensureServiceUnbound(ListenerRecord listenerRecord) {
        if (listenerRecord.bound) {
            this.mContext.unbindService((ServiceConnection)this);
            listenerRecord.bound = false;
        }
        listenerRecord.service = null;
    }

    private void handleQueueTask(NotificationManagerCompat.Task task) {
        this.updateListenerMap();
        for (ListenerRecord listenerRecord : this.mRecordMap.values()) {
            listenerRecord.taskQueue.add(task);
            this.processListenerQueue(listenerRecord);
        }
    }

    private void handleRetryListenerQueue(ComponentName object) {
        if ((object = this.mRecordMap.get(object)) != null) {
            this.processListenerQueue((ListenerRecord)object);
        }
    }

    private void handleServiceConnected(ComponentName object, IBinder iBinder) {
        if ((object = this.mRecordMap.get(object)) != null) {
            object.service = INotificationSideChannel.Stub.asInterface(iBinder);
            object.retryCount = 0;
            this.processListenerQueue((ListenerRecord)object);
        }
    }

    private void handleServiceDisconnected(ComponentName object) {
        if ((object = this.mRecordMap.get(object)) != null) {
            this.ensureServiceUnbound((ListenerRecord)object);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void processListenerQueue(ListenerRecord listenerRecord) {
        Object object;
        if (Log.isLoggable((String)NotificationManagerCompat.TAG, (int)3)) {
            object = new StringBuilder();
            object.append("Processing component ");
            object.append((Object)listenerRecord.componentName);
            object.append(", ");
            object.append(listenerRecord.taskQueue.size());
            object.append(" queued tasks");
            Log.d((String)NotificationManagerCompat.TAG, (String)object.toString());
        }
        if (listenerRecord.taskQueue.isEmpty()) {
            return;
        }
        if (!this.ensureServiceBound(listenerRecord) || listenerRecord.service == null) {
            this.scheduleListenerRetry(listenerRecord);
            return;
        }
        while ((object = listenerRecord.taskQueue.peek()) != null) {
            StringBuilder stringBuilder;
            try {
                if (Log.isLoggable((String)NotificationManagerCompat.TAG, (int)3)) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Sending task ");
                    stringBuilder.append(object);
                    Log.d((String)NotificationManagerCompat.TAG, (String)stringBuilder.toString());
                }
                object.send(listenerRecord.service);
                listenerRecord.taskQueue.remove();
                continue;
            }
            catch (RemoteException remoteException) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("RemoteException communicating with ");
                stringBuilder.append((Object)listenerRecord.componentName);
                Log.w((String)NotificationManagerCompat.TAG, (String)stringBuilder.toString(), (Throwable)remoteException);
                break;
            }
            catch (DeadObjectException deadObjectException) {}
            if (!Log.isLoggable((String)NotificationManagerCompat.TAG, (int)3)) break;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Remote service has died: ");
            stringBuilder2.append((Object)listenerRecord.componentName);
            Log.d((String)NotificationManagerCompat.TAG, (String)stringBuilder2.toString());
            break;
        }
        if (!listenerRecord.taskQueue.isEmpty()) {
            this.scheduleListenerRetry(listenerRecord);
        }
    }

    private void scheduleListenerRetry(ListenerRecord listenerRecord) {
        if (this.mHandler.hasMessages(3, (Object)listenerRecord.componentName)) {
            return;
        }
        ++listenerRecord.retryCount;
        if (listenerRecord.retryCount > 6) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Giving up on delivering ");
            stringBuilder.append(listenerRecord.taskQueue.size());
            stringBuilder.append(" tasks to ");
            stringBuilder.append((Object)listenerRecord.componentName);
            stringBuilder.append(" after ");
            stringBuilder.append(listenerRecord.retryCount);
            stringBuilder.append(" retries");
            Log.w((String)NotificationManagerCompat.TAG, (String)stringBuilder.toString());
            listenerRecord.taskQueue.clear();
            return;
        }
        int n = 1000 * (1 << listenerRecord.retryCount - 1);
        if (Log.isLoggable((String)NotificationManagerCompat.TAG, (int)3)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Scheduling retry for ");
            stringBuilder.append(n);
            stringBuilder.append(" ms");
            Log.d((String)NotificationManagerCompat.TAG, (String)stringBuilder.toString());
        }
        listenerRecord = this.mHandler.obtainMessage(3, (Object)listenerRecord.componentName);
        this.mHandler.sendMessageDelayed((Message)listenerRecord, (long)n);
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
            this.mRecordMap.put(componentName, new ListenerRecord(componentName));
        }
        object2 = this.mRecordMap.entrySet().iterator();
        while (object2.hasNext()) {
            Map.Entry<ComponentName, ListenerRecord> entry = object2.next();
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
                object = (NotificationManagerCompat.ServiceConnectedEvent)object.obj;
                this.handleServiceConnected(object.componentName, object.iBinder);
                return true;
            }
            case 0: 
        }
        this.handleQueueTask((NotificationManagerCompat.Task)object.obj);
        return true;
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        if (Log.isLoggable((String)NotificationManagerCompat.TAG, (int)3)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Connected to service ");
            stringBuilder.append((Object)componentName);
            Log.d((String)NotificationManagerCompat.TAG, (String)stringBuilder.toString());
        }
        this.mHandler.obtainMessage(1, (Object)new NotificationManagerCompat.ServiceConnectedEvent(componentName, iBinder)).sendToTarget();
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

    public void queueTask(NotificationManagerCompat.Task task) {
        this.mHandler.obtainMessage(0, (Object)task).sendToTarget();
    }

    private static class ListenerRecord {
        boolean bound = false;
        final ComponentName componentName;
        int retryCount = 0;
        INotificationSideChannel service;
        ArrayDeque<NotificationManagerCompat.Task> taskQueue = new ArrayDeque();

        ListenerRecord(ComponentName componentName) {
            this.componentName = componentName;
        }
    }

}
