/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 */
package android.support.v4.app;

import android.app.PendingIntent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;
import java.util.ArrayList;
import java.util.List;

public static class NotificationCompat.CarExtender.UnreadConversation {
    private final long mLatestTimestamp;
    private final String[] mMessages;
    private final String[] mParticipants;
    private final PendingIntent mReadPendingIntent;
    private final RemoteInput mRemoteInput;
    private final PendingIntent mReplyPendingIntent;

    NotificationCompat.CarExtender.UnreadConversation(String[] arrstring, RemoteInput remoteInput, PendingIntent pendingIntent, PendingIntent pendingIntent2, String[] arrstring2, long l) {
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

    public static class Builder {
        private long mLatestTimestamp;
        private final List<String> mMessages = new ArrayList<String>();
        private final String mParticipant;
        private PendingIntent mReadPendingIntent;
        private RemoteInput mRemoteInput;
        private PendingIntent mReplyPendingIntent;

        public Builder(String string) {
            this.mParticipant = string;
        }

        public Builder addMessage(String string) {
            this.mMessages.add(string);
            return this;
        }

        public NotificationCompat.CarExtender.UnreadConversation build() {
            String[] arrstring = this.mMessages.toArray(new String[this.mMessages.size()]);
            String string = this.mParticipant;
            RemoteInput remoteInput = this.mRemoteInput;
            PendingIntent pendingIntent = this.mReplyPendingIntent;
            PendingIntent pendingIntent2 = this.mReadPendingIntent;
            long l = this.mLatestTimestamp;
            return new NotificationCompat.CarExtender.UnreadConversation(arrstring, remoteInput, pendingIntent, pendingIntent2, new String[]{string}, l);
        }

        public Builder setLatestTimestamp(long l) {
            this.mLatestTimestamp = l;
            return this;
        }

        public Builder setReadPendingIntent(PendingIntent pendingIntent) {
            this.mReadPendingIntent = pendingIntent;
            return this;
        }

        public Builder setReplyAction(PendingIntent pendingIntent, RemoteInput remoteInput) {
            this.mRemoteInput = remoteInput;
            this.mReplyPendingIntent = pendingIntent;
            return this;
        }
    }

}
