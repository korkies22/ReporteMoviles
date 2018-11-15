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

public static class NotificationCompat.Action {
    public PendingIntent actionIntent;
    public int icon;
    private boolean mAllowGeneratedReplies;
    private final RemoteInput[] mDataOnlyRemoteInputs;
    final Bundle mExtras;
    private final RemoteInput[] mRemoteInputs;
    public CharSequence title;

    public NotificationCompat.Action(int n, CharSequence charSequence, PendingIntent pendingIntent) {
        this(n, charSequence, pendingIntent, new Bundle(), null, null, true);
    }

    NotificationCompat.Action(int n, CharSequence charSequence, PendingIntent pendingIntent, Bundle bundle, RemoteInput[] arrremoteInput, RemoteInput[] arrremoteInput2, boolean bl) {
        this.icon = n;
        this.title = NotificationCompat.Builder.limitCharSequenceLength(charSequence);
        this.actionIntent = pendingIntent;
        if (bundle == null) {
            bundle = new Bundle();
        }
        this.mExtras = bundle;
        this.mRemoteInputs = arrremoteInput;
        this.mDataOnlyRemoteInputs = arrremoteInput2;
        this.mAllowGeneratedReplies = bl;
    }

    public PendingIntent getActionIntent() {
        return this.actionIntent;
    }

    public boolean getAllowGeneratedReplies() {
        return this.mAllowGeneratedReplies;
    }

    public RemoteInput[] getDataOnlyRemoteInputs() {
        return this.mDataOnlyRemoteInputs;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public int getIcon() {
        return this.icon;
    }

    public RemoteInput[] getRemoteInputs() {
        return this.mRemoteInputs;
    }

    public CharSequence getTitle() {
        return this.title;
    }

    public static final class Builder {
        private boolean mAllowGeneratedReplies = true;
        private final Bundle mExtras;
        private final int mIcon;
        private final PendingIntent mIntent;
        private ArrayList<RemoteInput> mRemoteInputs;
        private final CharSequence mTitle;

        public Builder(int n, CharSequence charSequence, PendingIntent pendingIntent) {
            this(n, charSequence, pendingIntent, new Bundle(), null, true);
        }

        private Builder(int n, CharSequence object, PendingIntent pendingIntent, Bundle bundle, RemoteInput[] arrremoteInput, boolean bl) {
            this.mIcon = n;
            this.mTitle = NotificationCompat.Builder.limitCharSequenceLength((CharSequence)object);
            this.mIntent = pendingIntent;
            this.mExtras = bundle;
            object = arrremoteInput == null ? null : new ArrayList<RemoteInput>(Arrays.asList(arrremoteInput));
            this.mRemoteInputs = object;
            this.mAllowGeneratedReplies = bl;
        }

        public Builder(NotificationCompat.Action action) {
            this(action.icon, action.title, action.actionIntent, new Bundle(action.mExtras), action.getRemoteInputs(), action.getAllowGeneratedReplies());
        }

        public Builder addExtras(Bundle bundle) {
            if (bundle != null) {
                this.mExtras.putAll(bundle);
            }
            return this;
        }

        public Builder addRemoteInput(RemoteInput remoteInput) {
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

        public Builder extend(Extender extender) {
            extender.extend(this);
            return this;
        }

        public Bundle getExtras() {
            return this.mExtras;
        }

        public Builder setAllowGeneratedReplies(boolean bl) {
            this.mAllowGeneratedReplies = bl;
            return this;
        }
    }

    public static interface Extender {
        public Builder extend(Builder var1);
    }

    public static final class WearableExtender
    implements Extender {
        private static final int DEFAULT_FLAGS = 1;
        private static final String EXTRA_WEARABLE_EXTENSIONS = "android.wearable.EXTENSIONS";
        private static final int FLAG_AVAILABLE_OFFLINE = 1;
        private static final int FLAG_HINT_DISPLAY_INLINE = 4;
        private static final int FLAG_HINT_LAUNCHES_ACTIVITY = 2;
        private static final String KEY_CANCEL_LABEL = "cancelLabel";
        private static final String KEY_CONFIRM_LABEL = "confirmLabel";
        private static final String KEY_FLAGS = "flags";
        private static final String KEY_IN_PROGRESS_LABEL = "inProgressLabel";
        private CharSequence mCancelLabel;
        private CharSequence mConfirmLabel;
        private int mFlags = 1;
        private CharSequence mInProgressLabel;

        public WearableExtender() {
        }

        public WearableExtender(NotificationCompat.Action action) {
            action = action.getExtras().getBundle(EXTRA_WEARABLE_EXTENSIONS);
            if (action != null) {
                this.mFlags = action.getInt(KEY_FLAGS, 1);
                this.mInProgressLabel = action.getCharSequence(KEY_IN_PROGRESS_LABEL);
                this.mConfirmLabel = action.getCharSequence(KEY_CONFIRM_LABEL);
                this.mCancelLabel = action.getCharSequence(KEY_CANCEL_LABEL);
            }
        }

        private void setFlag(int n, boolean bl) {
            if (bl) {
                this.mFlags = n | this.mFlags;
                return;
            }
            this.mFlags = ~ n & this.mFlags;
        }

        public WearableExtender clone() {
            WearableExtender wearableExtender = new WearableExtender();
            wearableExtender.mFlags = this.mFlags;
            wearableExtender.mInProgressLabel = this.mInProgressLabel;
            wearableExtender.mConfirmLabel = this.mConfirmLabel;
            wearableExtender.mCancelLabel = this.mCancelLabel;
            return wearableExtender;
        }

        @Override
        public Builder extend(Builder builder) {
            Bundle bundle = new Bundle();
            if (this.mFlags != 1) {
                bundle.putInt(KEY_FLAGS, this.mFlags);
            }
            if (this.mInProgressLabel != null) {
                bundle.putCharSequence(KEY_IN_PROGRESS_LABEL, this.mInProgressLabel);
            }
            if (this.mConfirmLabel != null) {
                bundle.putCharSequence(KEY_CONFIRM_LABEL, this.mConfirmLabel);
            }
            if (this.mCancelLabel != null) {
                bundle.putCharSequence(KEY_CANCEL_LABEL, this.mCancelLabel);
            }
            builder.getExtras().putBundle(EXTRA_WEARABLE_EXTENSIONS, bundle);
            return builder;
        }

        public CharSequence getCancelLabel() {
            return this.mCancelLabel;
        }

        public CharSequence getConfirmLabel() {
            return this.mConfirmLabel;
        }

        public boolean getHintDisplayActionInline() {
            if ((this.mFlags & 4) != 0) {
                return true;
            }
            return false;
        }

        public boolean getHintLaunchesActivity() {
            if ((this.mFlags & 2) != 0) {
                return true;
            }
            return false;
        }

        public CharSequence getInProgressLabel() {
            return this.mInProgressLabel;
        }

        public boolean isAvailableOffline() {
            if ((this.mFlags & 1) != 0) {
                return true;
            }
            return false;
        }

        public WearableExtender setAvailableOffline(boolean bl) {
            this.setFlag(1, bl);
            return this;
        }

        public WearableExtender setCancelLabel(CharSequence charSequence) {
            this.mCancelLabel = charSequence;
            return this;
        }

        public WearableExtender setConfirmLabel(CharSequence charSequence) {
            this.mConfirmLabel = charSequence;
            return this;
        }

        public WearableExtender setHintDisplayActionInline(boolean bl) {
            this.setFlag(4, bl);
            return this;
        }

        public WearableExtender setHintLaunchesActivity(boolean bl) {
            this.setFlag(2, bl);
            return this;
        }

        public WearableExtender setInProgressLabel(CharSequence charSequence) {
            this.mInProgressLabel = charSequence;
            return this;
        }
    }

}
