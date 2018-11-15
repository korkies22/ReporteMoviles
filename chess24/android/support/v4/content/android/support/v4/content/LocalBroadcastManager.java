/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.net.Uri
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.util.Log
 */
package android.support.v4.content;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public final class LocalBroadcastManager {
    private static final boolean DEBUG = false;
    static final int MSG_EXEC_PENDING_BROADCASTS = 1;
    private static final String TAG = "LocalBroadcastManager";
    private static LocalBroadcastManager mInstance;
    private static final Object mLock;
    private final HashMap<String, ArrayList<ReceiverRecord>> mActions = new HashMap();
    private final Context mAppContext;
    private final Handler mHandler;
    private final ArrayList<BroadcastRecord> mPendingBroadcasts = new ArrayList();
    private final HashMap<BroadcastReceiver, ArrayList<ReceiverRecord>> mReceivers = new HashMap();

    static {
        mLock = new Object();
    }

    private LocalBroadcastManager(Context context) {
        this.mAppContext = context;
        this.mHandler = new Handler(context.getMainLooper()){

            public void handleMessage(Message message) {
                if (message.what != 1) {
                    super.handleMessage(message);
                    return;
                }
                LocalBroadcastManager.this.executePendingBroadcasts();
            }
        };
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void executePendingBroadcasts() {
        block3 : do {
            BroadcastRecord[] arrbroadcastRecord;
            int n;
            Object object = this.mReceivers;
            synchronized (object) {
                n = this.mPendingBroadcasts.size();
                if (n <= 0) {
                    return;
                }
                arrbroadcastRecord = new BroadcastRecord[n];
                this.mPendingBroadcasts.toArray(arrbroadcastRecord);
                this.mPendingBroadcasts.clear();
            }
            n = 0;
            do {
                if (n >= arrbroadcastRecord.length) continue block3;
                object = arrbroadcastRecord[n];
                int n2 = object.receivers.size();
                for (int i = 0; i < n2; ++i) {
                    ReceiverRecord receiverRecord = object.receivers.get(i);
                    if (receiverRecord.dead) continue;
                    receiverRecord.receiver.onReceive(this.mAppContext, object.intent);
                }
                ++n;
            } while (true);
            break;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @NonNull
    public static LocalBroadcastManager getInstance(@NonNull Context object) {
        Object object2 = mLock;
        synchronized (object2) {
            if (mInstance != null) return mInstance;
            mInstance = new LocalBroadcastManager(object.getApplicationContext());
            return mInstance;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void registerReceiver(@NonNull BroadcastReceiver object, @NonNull IntentFilter intentFilter) {
        HashMap<BroadcastReceiver, ArrayList<ReceiverRecord>> hashMap = this.mReceivers;
        synchronized (hashMap) {
            ReceiverRecord receiverRecord = new ReceiverRecord(intentFilter, (BroadcastReceiver)object);
            Object object2 = this.mReceivers.get(object);
            ArrayList<ReceiverRecord> arrayList = object2;
            if (object2 == null) {
                arrayList = new ArrayList(1);
                this.mReceivers.put((BroadcastReceiver)object, arrayList);
            }
            arrayList.add(receiverRecord);
            int n = 0;
            while (n < intentFilter.countActions()) {
                object2 = intentFilter.getAction(n);
                arrayList = this.mActions.get(object2);
                object = arrayList;
                if (arrayList == null) {
                    object = new ArrayList(1);
                    this.mActions.put((String)object2, (ArrayList<ReceiverRecord>)object);
                }
                object.add(receiverRecord);
                ++n;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean sendBroadcast(@NonNull Intent intent) {
        HashMap<BroadcastReceiver, ArrayList<ReceiverRecord>> hashMap = this.mReceivers;
        synchronized (hashMap) {
            Object object;
            int n;
            block22 : {
                block21 : {
                    Object object2;
                    ArrayList<ReceiverRecord> arrayList;
                    String string = intent.getAction();
                    String string2 = intent.resolveTypeIfNeeded(this.mAppContext.getContentResolver());
                    Uri uri = intent.getData();
                    String string3 = intent.getScheme();
                    Set set = intent.getCategories();
                    n = (intent.getFlags() & 8) != 0 ? 1 : 0;
                    if (n != 0) {
                        object2 = new StringBuilder();
                        object2.append("Resolving type ");
                        object2.append(string2);
                        object2.append(" scheme ");
                        object2.append(string3);
                        object2.append(" of intent ");
                        object2.append((Object)intent);
                        Log.v((String)TAG, (String)object2.toString());
                    }
                    if ((arrayList = this.mActions.get(intent.getAction())) == null) break block21;
                    if (n != 0) {
                        object2 = new StringBuilder();
                        object2.append("Action list: ");
                        object2.append(arrayList);
                        Log.v((String)TAG, (String)object2.toString());
                    }
                    object = null;
                    for (int i = 0; i < arrayList.size(); ++i) {
                        Object object3 = arrayList.get(i);
                        if (n != 0) {
                            object2 = new StringBuilder();
                            object2.append("Matching against filter ");
                            object2.append((Object)object3.filter);
                            Log.v((String)TAG, (String)object2.toString());
                        }
                        if (object3.broadcasting) {
                            if (n == 0) continue;
                            Log.v((String)TAG, (String)"  Filter's target already added");
                            continue;
                        }
                        IntentFilter intentFilter = object3.filter;
                        object2 = object;
                        int n2 = intentFilter.match(string, string2, string3, uri, set, TAG);
                        if (n2 >= 0) {
                            if (n != 0) {
                                object = new StringBuilder();
                                object.append("  Filter matched!  match=0x");
                                object.append(Integer.toHexString(n2));
                                Log.v((String)TAG, (String)object.toString());
                            }
                            object = object2 == null ? new ArrayList<ReceiverRecord>() : object2;
                            object.add(object3);
                            object3.broadcasting = true;
                            continue;
                        }
                        if (n == 0) continue;
                        switch (n2) {
                            default: {
                                object2 = "unknown reason";
                                break;
                            }
                            case -1: {
                                object2 = "type";
                                break;
                            }
                            case -2: {
                                object2 = "data";
                                break;
                            }
                            case -3: {
                                object2 = "action";
                                break;
                            }
                            case -4: {
                                object2 = "category";
                            }
                        }
                        object3 = new StringBuilder();
                        object3.append("  Filter did not match: ");
                        object3.append((String)object2);
                        Log.v((String)TAG, (String)object3.toString());
                    }
                    if (object != null) break block22;
                }
                return false;
            }
            for (n = 0; n < object.size(); ++n) {
                ((ReceiverRecord)object.get((int)n)).broadcasting = false;
            }
            this.mPendingBroadcasts.add(new BroadcastRecord(intent, (ArrayList<ReceiverRecord>)object));
            if (!this.mHandler.hasMessages(1)) {
                this.mHandler.sendEmptyMessage(1);
            }
            return true;
        }
    }

    public void sendBroadcastSync(@NonNull Intent intent) {
        if (this.sendBroadcast(intent)) {
            this.executePendingBroadcasts();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unregisterReceiver(@NonNull BroadcastReceiver broadcastReceiver) {
        HashMap<BroadcastReceiver, ArrayList<ReceiverRecord>> hashMap = this.mReceivers;
        synchronized (hashMap) {
            ArrayList<ReceiverRecord> arrayList = this.mReceivers.remove((Object)broadcastReceiver);
            if (arrayList == null) {
                return;
            }
            int n = arrayList.size() - 1;
            block2 : while (n >= 0) {
                ReceiverRecord receiverRecord = arrayList.get(n);
                receiverRecord.dead = true;
                int n2 = 0;
                do {
                    block11 : {
                        String string;
                        ArrayList<ReceiverRecord> arrayList2;
                        block12 : {
                            block10 : {
                                if (n2 >= receiverRecord.filter.countActions()) break block10;
                                string = receiverRecord.filter.getAction(n2);
                                arrayList2 = this.mActions.get(string);
                                if (arrayList2 == null) break block11;
                                break block12;
                            }
                            --n;
                            continue block2;
                        }
                        int n3 = arrayList2.size() - 1;
                        do {
                            if (n3 >= 0) {
                                ReceiverRecord receiverRecord2 = arrayList2.get(n3);
                                if (receiverRecord2.receiver == broadcastReceiver) {
                                    receiverRecord2.dead = true;
                                    arrayList2.remove(n3);
                                }
                            } else {
                                if (arrayList2.size() > 0) break;
                                this.mActions.remove(string);
                                break;
                            }
                            --n3;
                        } while (true);
                    }
                    ++n2;
                } while (true);
                break;
            }
            return;
        }
    }

    private static final class BroadcastRecord {
        final Intent intent;
        final ArrayList<ReceiverRecord> receivers;

        BroadcastRecord(Intent intent, ArrayList<ReceiverRecord> arrayList) {
            this.intent = intent;
            this.receivers = arrayList;
        }
    }

    private static final class ReceiverRecord {
        boolean broadcasting;
        boolean dead;
        final IntentFilter filter;
        final BroadcastReceiver receiver;

        ReceiverRecord(IntentFilter intentFilter, BroadcastReceiver broadcastReceiver) {
            this.filter = intentFilter;
            this.receiver = broadcastReceiver;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(128);
            stringBuilder.append("Receiver{");
            stringBuilder.append((Object)this.receiver);
            stringBuilder.append(" filter=");
            stringBuilder.append((Object)this.filter);
            if (this.dead) {
                stringBuilder.append(" DEAD");
            }
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
    }

}
