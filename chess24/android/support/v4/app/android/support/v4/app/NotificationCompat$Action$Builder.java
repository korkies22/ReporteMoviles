/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.os.Bundle
 */
package android.support.v4.app;

import android.app.PendingIntent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public static final class NotificationCompat.Action.Builder {
    private boolean mAllowGeneratedReplies = true;
    private final Bundle mExtras;
    private final int mIcon;
    private final PendingIntent mIntent;
    private ArrayList<RemoteInput> mRemoteInputs;
    private final CharSequence mTitle;

    public NotificationCompat.Action.Builder(int n, CharSequence charSequence, PendingIntent pendingIntent) {
        this(n, charSequence, pendingIntent, new Bundle(), null, true);
    }

    private NotificationCompat.Action.Builder(int n, CharSequence object, PendingIntent pendingIntent, Bundle bundle, RemoteInput[] arrremoteInput, boolean bl) {
        this.mIcon = n;
        this.mTitle = NotificationCompat.Builder.limitCharSequenceLength((CharSequence)object);
        this.mIntent = pendingIntent;
        this.mExtras = bundle;
        object = arrremoteInput == null ? null : new ArrayList<RemoteInput>(Arrays.asList(arrremoteInput));
        this.mRemoteInputs = object;
        this.mAllowGeneratedReplies = bl;
    }

    public NotificationCompat.Action.Builder(NotificationCompat.Action action) {
        this(action.icon, action.title, action.actionIntent, new Bundle(action.mExtras), action.getRemoteInputs(), action.getAllowGeneratedReplies());
    }

    public NotificationCompat.Action.Builder addExtras(Bundle bundle) {
        if (bundle != null) {
            this.mExtras.putAll(bundle);
        }
        return this;
    }

    public NotificationCompat.Action.Builder addRemoteInput(RemoteInput remoteInput) {
        if (this.mRemoteInputs == null) {
            this.mRemoteInputs = new ArrayList();
        }
        this.mRemoteInputs.add(remoteInput);
        return this;
    }

    public NotificationCompat.Action build() {
        RemoteInput[] arrremoteInput;
        Object object;
        block3 : {
            object = new RemoteInput[]();
            ArrayList<RemoteInput> arrayList = new ArrayList<RemoteInput>();
            if (this.mRemoteInputs != null) {
                for (RemoteInput remoteInput : this.mRemoteInputs) {
                    if (remoteInput.isDataOnly()) {
                        object.add(remoteInput);
                        continue;
                    }
                    arrayList.add(remoteInput);
                }
            }
            boolean bl = object.isEmpty();
            arrremoteInput = null;
            object = bl ? null : object.toArray(new RemoteInput[object.size()]);
            if (arrayList.isEmpty()) break block3;
            arrremoteInput = arrayList.toArray(new RemoteInput[arrayList.size()]);
        }
        return new NotificationCompat.Action(this.mIcon, this.mTitle, this.mIntent, this.mExtras, arrremoteInput, (RemoteInput[])object, this.mAllowGeneratedReplies);
    }

    public NotificationCompat.Action.Builder extend(NotificationCompat.Action.Extender extender) {
        extender.extend(this);
        return this;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public NotificationCompat.Action.Builder setAllowGeneratedReplies(boolean bl) {
        this.mAllowGeneratedReplies = bl;
        return this;
    }
}
