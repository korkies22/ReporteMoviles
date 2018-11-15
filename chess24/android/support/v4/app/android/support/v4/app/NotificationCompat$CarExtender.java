/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Notification
 *  android.app.PendingIntent
 *  android.app.RemoteInput
 *  android.app.RemoteInput$Builder
 *  android.graphics.Bitmap
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Parcelable
 */
package android.support.v4.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public static final class NotificationCompat.CarExtender
implements NotificationCompat.Extender {
    private static final String EXTRA_CAR_EXTENDER = "android.car.EXTENSIONS";
    private static final String EXTRA_COLOR = "app_color";
    private static final String EXTRA_CONVERSATION = "car_conversation";
    private static final String EXTRA_LARGE_ICON = "large_icon";
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_MESSAGES = "messages";
    private static final String KEY_ON_READ = "on_read";
    private static final String KEY_ON_REPLY = "on_reply";
    private static final String KEY_PARTICIPANTS = "participants";
    private static final String KEY_REMOTE_INPUT = "remote_input";
    private static final String KEY_TEXT = "text";
    private static final String KEY_TIMESTAMP = "timestamp";
    private int mColor = 0;
    private Bitmap mLargeIcon;
    private UnreadConversation mUnreadConversation;

    public NotificationCompat.CarExtender() {
    }

    public NotificationCompat.CarExtender(Notification object) {
        if (Build.VERSION.SDK_INT < 21) {
            return;
        }
        object = NotificationCompat.getExtras(object) == null ? null : NotificationCompat.getExtras(object).getBundle(EXTRA_CAR_EXTENDER);
        if (object != null) {
            this.mLargeIcon = (Bitmap)object.getParcelable(EXTRA_LARGE_ICON);
            this.mColor = object.getInt(EXTRA_COLOR, 0);
            this.mUnreadConversation = NotificationCompat.CarExtender.getUnreadConversationFromBundle(object.getBundle(EXTRA_CONVERSATION));
        }
    }

    @RequiresApi(value=21)
    private static Bundle getBundleForUnreadConversation(@NonNull UnreadConversation unreadConversation) {
        Bundle bundle = new Bundle();
        Object object = unreadConversation.getParticipants();
        object = object != null && unreadConversation.getParticipants().length > 1 ? unreadConversation.getParticipants()[0] : null;
        Parcelable[] arrparcelable = new Parcelable[unreadConversation.getMessages().length];
        for (int i = 0; i < arrparcelable.length; ++i) {
            Bundle bundle2 = new Bundle();
            bundle2.putString(KEY_TEXT, unreadConversation.getMessages()[i]);
            bundle2.putString(KEY_AUTHOR, (String)object);
            arrparcelable[i] = bundle2;
        }
        bundle.putParcelableArray(KEY_MESSAGES, arrparcelable);
        object = unreadConversation.getRemoteInput();
        if (object != null) {
            bundle.putParcelable(KEY_REMOTE_INPUT, (Parcelable)new RemoteInput.Builder(object.getResultKey()).setLabel(object.getLabel()).setChoices(object.getChoices()).setAllowFreeFormInput(object.getAllowFreeFormInput()).addExtras(object.getExtras()).build());
        }
        bundle.putParcelable(KEY_ON_REPLY, (Parcelable)unreadConversation.getReplyPendingIntent());
        bundle.putParcelable(KEY_ON_READ, (Parcelable)unreadConversation.getReadPendingIntent());
        bundle.putStringArray(KEY_PARTICIPANTS, unreadConversation.getParticipants());
        bundle.putLong(KEY_TIMESTAMP, unreadConversation.getLatestTimestamp());
        return bundle;
    }

    @RequiresApi(value=21)
    private static UnreadConversation getUnreadConversationFromBundle(@Nullable Bundle bundle) {
        String[] arrstring;
        RemoteInput remoteInput = null;
        if (bundle == null) {
            return null;
        }
        PendingIntent pendingIntent = bundle.getParcelableArray(KEY_MESSAGES);
        if (pendingIntent != null) {
            int n;
            block10 : {
                int n2 = 0;
                arrstring = new String[((Parcelable[])pendingIntent).length];
                for (n = 0; n < arrstring.length; ++n) {
                    if (!(pendingIntent[n] instanceof Bundle)) {
                        n = n2;
                    } else {
                        arrstring[n] = ((Bundle)pendingIntent[n]).getString(KEY_TEXT);
                        if (arrstring[n] != null) continue;
                        n = n2;
                    }
                    break block10;
                }
                n = 1;
            }
            if (n == 0) {
                return null;
            }
        } else {
            arrstring = null;
        }
        pendingIntent = (PendingIntent)bundle.getParcelable(KEY_ON_READ);
        PendingIntent pendingIntent2 = (PendingIntent)bundle.getParcelable(KEY_ON_REPLY);
        android.app.RemoteInput remoteInput2 = (android.app.RemoteInput)bundle.getParcelable(KEY_REMOTE_INPUT);
        String[] arrstring2 = bundle.getStringArray(KEY_PARTICIPANTS);
        if (arrstring2 != null) {
            if (arrstring2.length != 1) {
                return null;
            }
            if (remoteInput2 != null) {
                remoteInput = new RemoteInput(remoteInput2.getResultKey(), remoteInput2.getLabel(), remoteInput2.getChoices(), remoteInput2.getAllowFreeFormInput(), remoteInput2.getExtras(), null);
            }
            return new UnreadConversation(arrstring, remoteInput, pendingIntent2, pendingIntent, arrstring2, bundle.getLong(KEY_TIMESTAMP));
        }
        return null;
    }

    @Override
    public NotificationCompat.Builder extend(NotificationCompat.Builder builder) {
        if (Build.VERSION.SDK_INT < 21) {
            return builder;
        }
        Bundle bundle = new Bundle();
        if (this.mLargeIcon != null) {
            bundle.putParcelable(EXTRA_LARGE_ICON, (Parcelable)this.mLargeIcon);
        }
        if (this.mColor != 0) {
            bundle.putInt(EXTRA_COLOR, this.mColor);
        }
        if (this.mUnreadConversation != null) {
            bundle.putBundle(EXTRA_CONVERSATION, NotificationCompat.CarExtender.getBundleForUnreadConversation(this.mUnreadConversation));
        }
        builder.getExtras().putBundle(EXTRA_CAR_EXTENDER, bundle);
        return builder;
    }

    @ColorInt
    public int getColor() {
        return this.mColor;
    }

    public Bitmap getLargeIcon() {
        return this.mLargeIcon;
    }

    public UnreadConversation getUnreadConversation() {
        return this.mUnreadConversation;
    }

    public NotificationCompat.CarExtender setColor(@ColorInt int n) {
        this.mColor = n;
        return this;
    }

    public NotificationCompat.CarExtender setLargeIcon(Bitmap bitmap) {
        this.mLargeIcon = bitmap;
        return this;
    }

    public NotificationCompat.CarExtender setUnreadConversation(UnreadConversation unreadConversation) {
        this.mUnreadConversation = unreadConversation;
        return this;
    }

    public static class UnreadConversation {
        private final long mLatestTimestamp;
        private final String[] mMessages;
        private final String[] mParticipants;
        private final PendingIntent mReadPendingIntent;
        private final RemoteInput mRemoteInput;
        private final PendingIntent mReplyPendingIntent;

        UnreadConversation(String[] arrstring, RemoteInput remoteInput, PendingIntent pendingIntent, PendingIntent pendingIntent2, String[] arrstring2, long l) {
            this.mMessages = arrstring;
            this.mRemoteInput = remoteInput;
            this.mReadPendingIntent = pendingIntent2;
            this.mReplyPendingIntent = pendingIntent;
            this.mParticipants = arrstring2;
            this.mLatestTimestamp = l;
        }

        public long getLatestTimestamp() {
            return this.mLatestTimestamp;
        }

        public String[] getMessages() {
            return this.mMessages;
        }

        public String getParticipant() {
            if (this.mParticipants.length > 0) {
                return this.mParticipants[0];
            }
            return null;
        }

        public String[] getParticipants() {
            return this.mParticipants;
        }

        public PendingIntent getReadPendingIntent() {
            return this.mReadPendingIntent;
        }

        public RemoteInput getRemoteInput() {
            return this.mRemoteInput;
        }

        public PendingIntent getReplyPendingIntent() {
            return this.mReplyPendingIntent;
        }
    }

    public static class UnreadConversation$Builder {
        private long mLatestTimestamp;
        private final List<String> mMessages = new ArrayList<String>();
        private final String mParticipant;
        private PendingIntent mReadPendingIntent;
        private RemoteInput mRemoteInput;
        private PendingIntent mReplyPendingIntent;

        public UnreadConversation$Builder(String string) {
            this.mParticipant = string;
        }

        public UnreadConversation$Builder addMessage(String string) {
            this.mMessages.add(string);
            return this;
        }

        public UnreadConversation build() {
            String[] arrstring = this.mMessages.toArray(new String[this.mMessages.size()]);
            String string = this.mParticipant;
            RemoteInput remoteInput = this.mRemoteInput;
            PendingIntent pendingIntent = this.mReplyPendingIntent;
            PendingIntent pendingIntent2 = this.mReadPendingIntent;
            long l = this.mLatestTimestamp;
            return new UnreadConversation(arrstring, remoteInput, pendingIntent, pendingIntent2, new String[]{string}, l);
        }

        public UnreadConversation$Builder setLatestTimestamp(long l) {
            this.mLatestTimestamp = l;
            return this;
        }

        public UnreadConversation$Builder setReadPendingIntent(PendingIntent pendingIntent) {
            this.mReadPendingIntent = pendingIntent;
            return this;
        }

        public UnreadConversation$Builder setReplyAction(PendingIntent pendingIntent, RemoteInput remoteInput) {
            this.mRemoteInput = remoteInput;
            this.mReplyPendingIntent = pendingIntent;
            return this;
        }
    }

}
