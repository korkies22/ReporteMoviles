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

public static class NotificationCompat.CarExtender.UnreadConversation.Builder {
    private long mLatestTimestamp;
    private final List<String> mMessages = new ArrayList<String>();
    private final String mParticipant;
    private PendingIntent mReadPendingIntent;
    private RemoteInput mRemoteInput;
    private PendingIntent mReplyPendingIntent;

    public NotificationCompat.CarExtender.UnreadConversation.Builder(String string) {
        this.mParticipant = string;
    }

    public NotificationCompat.CarExtender.UnreadConversation.Builder addMessage(String string) {
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

    public NotificationCompat.CarExtender.UnreadConversation.Builder setLatestTimestamp(long l) {
        this.mLatestTimestamp = l;
        return this;
    }

    public NotificationCompat.CarExtender.UnreadConversation.Builder setReadPendingIntent(PendingIntent pendingIntent) {
        this.mReadPendingIntent = pendingIntent;
        return this;
    }

    public NotificationCompat.CarExtender.UnreadConversation.Builder setReplyAction(PendingIntent pendingIntent, RemoteInput remoteInput) {
        this.mRemoteInput = remoteInput;
        this.mReplyPendingIntent = pendingIntent;
        return this;
    }
}
