/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Notification
 *  android.app.Notification$Builder
 *  android.app.PendingIntent
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.util.Log
 *  android.util.SparseArray
 */
package android.support.v4.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;
import android.util.Log;
import android.util.SparseArray;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@RequiresApi(value=16)
class NotificationCompatJellybean {
    static final String EXTRA_ALLOW_GENERATED_REPLIES = "android.support.allowGeneratedReplies";
    static final String EXTRA_DATA_ONLY_REMOTE_INPUTS = "android.support.dataRemoteInputs";
    private static final String KEY_ACTION_INTENT = "actionIntent";
    private static final String KEY_ALLOWED_DATA_TYPES = "allowedDataTypes";
    private static final String KEY_ALLOW_FREE_FORM_INPUT = "allowFreeFormInput";
    private static final String KEY_CHOICES = "choices";
    private static final String KEY_DATA_ONLY_REMOTE_INPUTS = "dataOnlyRemoteInputs";
    private static final String KEY_EXTRAS = "extras";
    private static final String KEY_ICON = "icon";
    private static final String KEY_LABEL = "label";
    private static final String KEY_REMOTE_INPUTS = "remoteInputs";
    private static final String KEY_RESULT_KEY = "resultKey";
    private static final String KEY_TITLE = "title";
    public static final String TAG = "NotificationCompat";
    private static Class<?> sActionClass;
    private static Field sActionIconField;
    private static Field sActionIntentField;
    private static Field sActionTitleField;
    private static boolean sActionsAccessFailed;
    private static Field sActionsField;
    private static final Object sActionsLock;
    private static Field sExtrasField;
    private static boolean sExtrasFieldAccessFailed;
    private static final Object sExtrasLock;

    static {
        sExtrasLock = new Object();
        sActionsLock = new Object();
    }

    NotificationCompatJellybean() {
    }

    public static SparseArray<Bundle> buildActionExtrasMap(List<Bundle> list) {
        int n = list.size();
        SparseArray sparseArray = null;
        for (int i = 0; i < n; ++i) {
            Bundle bundle = list.get(i);
            SparseArray sparseArray2 = sparseArray;
            if (bundle != null) {
                sparseArray2 = sparseArray;
                if (sparseArray == null) {
                    sparseArray2 = new SparseArray();
                }
                sparseArray2.put(i, (Object)bundle);
            }
            sparseArray = sparseArray2;
        }
        return sparseArray;
    }

    private static boolean ensureActionReflectionReadyLocked() {
        if (sActionsAccessFailed) {
            return false;
        }
        try {
            if (sActionsField == null) {
                sActionClass = Class.forName("android.app.Notification$Action");
                sActionIconField = sActionClass.getDeclaredField(KEY_ICON);
                sActionTitleField = sActionClass.getDeclaredField(KEY_TITLE);
                sActionIntentField = sActionClass.getDeclaredField(KEY_ACTION_INTENT);
                sActionsField = Notification.class.getDeclaredField("actions");
                sActionsField.setAccessible(true);
            }
        }
        catch (NoSuchFieldException noSuchFieldException) {
            Log.e((String)TAG, (String)"Unable to access notification actions", (Throwable)noSuchFieldException);
            sActionsAccessFailed = true;
        }
        catch (ClassNotFoundException classNotFoundException) {
            Log.e((String)TAG, (String)"Unable to access notification actions", (Throwable)classNotFoundException);
            sActionsAccessFailed = true;
        }
        return true ^ sActionsAccessFailed;
    }

    private static RemoteInput fromBundle(Bundle bundle) {
        Object object = bundle.getStringArrayList(KEY_ALLOWED_DATA_TYPES);
        HashSet<String> hashSet = new HashSet<String>();
        if (object != null) {
            object = object.iterator();
            while (object.hasNext()) {
                hashSet.add((String)object.next());
            }
        }
        return new RemoteInput(bundle.getString(KEY_RESULT_KEY), bundle.getCharSequence(KEY_LABEL), bundle.getCharSequenceArray(KEY_CHOICES), bundle.getBoolean(KEY_ALLOW_FREE_FORM_INPUT), bundle.getBundle(KEY_EXTRAS), hashSet);
    }

    private static RemoteInput[] fromBundleArray(Bundle[] arrbundle) {
        if (arrbundle == null) {
            return null;
        }
        RemoteInput[] arrremoteInput = new RemoteInput[arrbundle.length];
        for (int i = 0; i < arrbundle.length; ++i) {
            arrremoteInput[i] = NotificationCompatJellybean.fromBundle(arrbundle[i]);
        }
        return arrremoteInput;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static NotificationCompat.Action getAction(Notification object, int n) {
        Object object2 = sActionsLock;
        synchronized (object2) {
            Object object3 = NotificationCompatJellybean.getActionObjectsLocked(object);
            if (object3 == null) return null;
            object3 = object3[n];
            try {
                try {
                    object = NotificationCompatJellybean.getExtras(object);
                    object = object != null && (object = object.getSparseParcelableArray("android.support.actionExtras")) != null ? (Bundle)object.get(n) : null;
                }
                catch (IllegalAccessException illegalAccessException) {
                    Log.e((String)TAG, (String)"Unable to access notification actions", (Throwable)illegalAccessException);
                    sActionsAccessFailed = true;
                }
                return null;
                return NotificationCompatJellybean.readAction(sActionIconField.getInt(object3), (CharSequence)sActionTitleField.get(object3), (PendingIntent)sActionIntentField.get(object3), (Bundle)object);
            }
            catch (Throwable throwable) {}
            throw throwable;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static int getActionCount(Notification arrobject) {
        Object object = sActionsLock;
        synchronized (object) {
            arrobject = NotificationCompatJellybean.getActionObjectsLocked((Notification)arrobject);
            if (arrobject == null) return 0;
            return arrobject.length;
        }
    }

    static NotificationCompat.Action getActionFromBundle(Bundle bundle) {
        Bundle bundle2 = bundle.getBundle(KEY_EXTRAS);
        boolean bl = false;
        if (bundle2 != null) {
            bl = bundle2.getBoolean(EXTRA_ALLOW_GENERATED_REPLIES, false);
        }
        return new NotificationCompat.Action(bundle.getInt(KEY_ICON), bundle.getCharSequence(KEY_TITLE), (PendingIntent)bundle.getParcelable(KEY_ACTION_INTENT), bundle.getBundle(KEY_EXTRAS), NotificationCompatJellybean.fromBundleArray(NotificationCompatJellybean.getBundleArrayFromBundle(bundle, KEY_REMOTE_INPUTS)), NotificationCompatJellybean.fromBundleArray(NotificationCompatJellybean.getBundleArrayFromBundle(bundle, KEY_DATA_ONLY_REMOTE_INPUTS)), bl);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static Object[] getActionObjectsLocked(Notification arrobject) {
        Object object = sActionsLock;
        synchronized (object) {
            if (!NotificationCompatJellybean.ensureActionReflectionReadyLocked()) {
                return null;
            }
            try {
                return (Object[])sActionsField.get(arrobject);
            }
            catch (IllegalAccessException illegalAccessException) {
                Log.e((String)TAG, (String)"Unable to access notification actions", (Throwable)illegalAccessException);
                sActionsAccessFailed = true;
                return null;
            }
        }
    }

    private static Bundle[] getBundleArrayFromBundle(Bundle bundle, String string) {
        Parcelable[] arrparcelable = bundle.getParcelableArray(string);
        if (!(arrparcelable instanceof Bundle[]) && arrparcelable != null) {
            arrparcelable = (Bundle[])Arrays.copyOf(arrparcelable, arrparcelable.length, Bundle[].class);
            bundle.putParcelableArray(string, arrparcelable);
            return arrparcelable;
        }
        return (Bundle[])arrparcelable;
    }

    static Bundle getBundleForAction(NotificationCompat.Action action) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_ICON, action.getIcon());
        bundle.putCharSequence(KEY_TITLE, action.getTitle());
        bundle.putParcelable(KEY_ACTION_INTENT, (Parcelable)action.getActionIntent());
        Bundle bundle2 = action.getExtras() != null ? new Bundle(action.getExtras()) : new Bundle();
        bundle2.putBoolean(EXTRA_ALLOW_GENERATED_REPLIES, action.getAllowGeneratedReplies());
        bundle.putBundle(KEY_EXTRAS, bundle2);
        bundle.putParcelableArray(KEY_REMOTE_INPUTS, (Parcelable[])NotificationCompatJellybean.toBundleArray(action.getRemoteInputs()));
        return bundle;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Bundle getExtras(Notification notification) {
        Object object = sExtrasLock;
        synchronized (object) {
            if (sExtrasFieldAccessFailed) {
                return null;
            }
            try {
                Field field;
                if (sExtrasField == null) {
                    field = Notification.class.getDeclaredField(KEY_EXTRAS);
                    if (!Bundle.class.isAssignableFrom(field.getType())) {
                        Log.e((String)TAG, (String)"Notification.extras field is not of type Bundle");
                        sExtrasFieldAccessFailed = true;
                        return null;
                    }
                    field.setAccessible(true);
                    sExtrasField = field;
                }
                Bundle bundle = (Bundle)sExtrasField.get((Object)notification);
                field = bundle;
                if (bundle == null) {
                    field = new Bundle();
                    sExtrasField.set((Object)notification, field);
                }
                return field;
            }
            catch (NoSuchFieldException noSuchFieldException) {
                Log.e((String)TAG, (String)"Unable to access notification extras", (Throwable)noSuchFieldException);
            }
            catch (IllegalAccessException illegalAccessException) {
                Log.e((String)TAG, (String)"Unable to access notification extras", (Throwable)illegalAccessException);
            }
            sExtrasFieldAccessFailed = true;
            return null;
        }
    }

    public static NotificationCompat.Action readAction(int n, CharSequence charSequence, PendingIntent pendingIntent, Bundle bundle) {
        RemoteInput[] arrremoteInput;
        boolean bl;
        RemoteInput[] arrremoteInput2;
        if (bundle != null) {
            arrremoteInput2 = NotificationCompatJellybean.fromBundleArray(NotificationCompatJellybean.getBundleArrayFromBundle(bundle, "android.support.remoteInputs"));
            arrremoteInput = NotificationCompatJellybean.fromBundleArray(NotificationCompatJellybean.getBundleArrayFromBundle(bundle, EXTRA_DATA_ONLY_REMOTE_INPUTS));
            bl = bundle.getBoolean(EXTRA_ALLOW_GENERATED_REPLIES);
        } else {
            bl = false;
            arrremoteInput = arrremoteInput2 = null;
        }
        return new NotificationCompat.Action(n, charSequence, pendingIntent, bundle, arrremoteInput2, arrremoteInput, bl);
    }

    private static Bundle toBundle(RemoteInput object) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_RESULT_KEY, object.getResultKey());
        bundle.putCharSequence(KEY_LABEL, object.getLabel());
        bundle.putCharSequenceArray(KEY_CHOICES, object.getChoices());
        bundle.putBoolean(KEY_ALLOW_FREE_FORM_INPUT, object.getAllowFreeFormInput());
        bundle.putBundle(KEY_EXTRAS, object.getExtras());
        Object object2 = object.getAllowedDataTypes();
        if (object2 != null && !object2.isEmpty()) {
            object = new ArrayList(object2.size());
            object2 = object2.iterator();
            while (object2.hasNext()) {
                object.add((String)object2.next());
            }
            bundle.putStringArrayList(KEY_ALLOWED_DATA_TYPES, (ArrayList)object);
        }
        return bundle;
    }

    private static Bundle[] toBundleArray(RemoteInput[] arrremoteInput) {
        if (arrremoteInput == null) {
            return null;
        }
        Bundle[] arrbundle = new Bundle[arrremoteInput.length];
        for (int i = 0; i < arrremoteInput.length; ++i) {
            arrbundle[i] = NotificationCompatJellybean.toBundle(arrremoteInput[i]);
        }
        return arrbundle;
    }

    public static Bundle writeActionAndGetExtras(Notification.Builder builder, NotificationCompat.Action action) {
        builder.addAction(action.getIcon(), action.getTitle(), action.getActionIntent());
        builder = new Bundle(action.getExtras());
        if (action.getRemoteInputs() != null) {
            builder.putParcelableArray("android.support.remoteInputs", (Parcelable[])NotificationCompatJellybean.toBundleArray(action.getRemoteInputs()));
        }
        if (action.getDataOnlyRemoteInputs() != null) {
            builder.putParcelableArray(EXTRA_DATA_ONLY_REMOTE_INPUTS, (Parcelable[])NotificationCompatJellybean.toBundleArray(action.getDataOnlyRemoteInputs()));
        }
        builder.putBoolean(EXTRA_ALLOW_GENERATED_REPLIES, action.getAllowGeneratedReplies());
        return builder;
    }
}
