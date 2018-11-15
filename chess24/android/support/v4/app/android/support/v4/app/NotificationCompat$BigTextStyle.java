/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Notification
 *  android.app.Notification$BigTextStyle
 *  android.app.Notification$Builder
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package android.support.v4.app;

import android.app.Notification;
import android.os.Build;
import android.support.annotation.RestrictTo;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.v4.app.NotificationCompat;

public static class NotificationCompat.BigTextStyle
extends NotificationCompat.Style {
    private CharSequence mBigText;

    public NotificationCompat.BigTextStyle() {
    }

    public NotificationCompat.BigTextStyle(NotificationCompat.Builder builder) {
        this.setBuilder(builder);
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    @Override
    public void apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
        if (Build.VERSION.SDK_INT >= 16) {
            notificationBuilderWithBuilderAccessor = new Notification.BigTextStyle(notificationBuilderWithBuilderAccessor.getBuilder()).setBigContentTitle(this.mBigContentTitle).bigText(this.mBigText);
            if (this.mSummaryTextSet) {
                notificationBuilderWithBuilderAccessor.setSummaryText(this.mSummaryText);
            }
        }
    }

    public NotificationCompat.BigTextStyle bigText(CharSequence charSequence) {
        this.mBigText = NotificationCompat.Builder.limitCharSequenceLength(charSequence);
        return this;
    }

    public NotificationCompat.BigTextStyle setBigContentTitle(CharSequence charSequence) {
        this.mBigContentTitle = NotificationCompat.Builder.limitCharSequenceLength(charSequence);
        return this;
    }

    public NotificationCompat.BigTextStyle setSummaryText(CharSequence charSequence) {
        this.mSummaryText = NotificationCompat.Builder.limitCharSequenceLength(charSequence);
        this.mSummaryTextSet = true;
        return this;
    }
}
