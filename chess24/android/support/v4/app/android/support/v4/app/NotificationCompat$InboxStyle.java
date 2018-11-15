/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Notification
 *  android.app.Notification$Builder
 *  android.app.Notification$InboxStyle
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package android.support.v4.app;

import android.app.Notification;
import android.os.Build;
import android.support.annotation.RestrictTo;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.v4.app.NotificationCompat;
import java.util.ArrayList;
import java.util.Iterator;

public static class NotificationCompat.InboxStyle
extends NotificationCompat.Style {
    private ArrayList<CharSequence> mTexts = new ArrayList();

    public NotificationCompat.InboxStyle() {
    }

    public NotificationCompat.InboxStyle(NotificationCompat.Builder builder) {
        this.setBuilder(builder);
    }

    public NotificationCompat.InboxStyle addLine(CharSequence charSequence) {
        this.mTexts.add(NotificationCompat.Builder.limitCharSequenceLength(charSequence));
        return this;
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    @Override
    public void apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
        if (Build.VERSION.SDK_INT >= 16) {
            notificationBuilderWithBuilderAccessor = new Notification.InboxStyle(notificationBuilderWithBuilderAccessor.getBuilder()).setBigContentTitle(this.mBigContentTitle);
            if (this.mSummaryTextSet) {
                notificationBuilderWithBuilderAccessor.setSummaryText(this.mSummaryText);
            }
            Iterator<CharSequence> iterator = this.mTexts.iterator();
            while (iterator.hasNext()) {
                notificationBuilderWithBuilderAccessor.addLine(iterator.next());
            }
        }
    }

    public NotificationCompat.InboxStyle setBigContentTitle(CharSequence charSequence) {
        this.mBigContentTitle = NotificationCompat.Builder.limitCharSequenceLength(charSequence);
        return this;
    }

    public NotificationCompat.InboxStyle setSummaryText(CharSequence charSequence) {
        this.mSummaryText = NotificationCompat.Builder.limitCharSequenceLength(charSequence);
        this.mSummaryTextSet = true;
        return this;
    }
}
