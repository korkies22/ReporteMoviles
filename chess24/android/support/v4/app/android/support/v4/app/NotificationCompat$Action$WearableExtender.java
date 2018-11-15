/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package android.support.v4.app;

import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

public static final class NotificationCompat.Action.WearableExtender
implements NotificationCompat.Action.Extender {
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

    public NotificationCompat.Action.WearableExtender() {
    }

    public NotificationCompat.Action.WearableExtender(NotificationCompat.Action action) {
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

    public NotificationCompat.Action.WearableExtender clone() {
        NotificationCompat.Action.WearableExtender wearableExtender = new NotificationCompat.Action.WearableExtender();
        wearableExtender.mFlags = this.mFlags;
        wearableExtender.mInProgressLabel = this.mInProgressLabel;
        wearableExtender.mConfirmLabel = this.mConfirmLabel;
        wearableExtender.mCancelLabel = this.mCancelLabel;
        return wearableExtender;
    }

    @Override
    public NotificationCompat.Action.Builder extend(NotificationCompat.Action.Builder builder) {
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

    public NotificationCompat.Action.WearableExtender setAvailableOffline(boolean bl) {
        this.setFlag(1, bl);
        return this;
    }

    public NotificationCompat.Action.WearableExtender setCancelLabel(CharSequence charSequence) {
        this.mCancelLabel = charSequence;
        return this;
    }

    public NotificationCompat.Action.WearableExtender setConfirmLabel(CharSequence charSequence) {
        this.mConfirmLabel = charSequence;
        return this;
    }

    public NotificationCompat.Action.WearableExtender setHintDisplayActionInline(boolean bl) {
        this.setFlag(4, bl);
        return this;
    }

    public NotificationCompat.Action.WearableExtender setHintLaunchesActivity(boolean bl) {
        this.setFlag(2, bl);
        return this;
    }

    public NotificationCompat.Action.WearableExtender setInProgressLabel(CharSequence charSequence) {
        this.mInProgressLabel = charSequence;
        return this;
    }
}
