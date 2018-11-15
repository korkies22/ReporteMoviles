/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Notification
 *  android.app.Notification$BigPictureStyle
 *  android.app.Notification$Builder
 *  android.graphics.Bitmap
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package android.support.v4.app;

import android.app.Notification;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RestrictTo;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.v4.app.NotificationCompat;

public static class NotificationCompat.BigPictureStyle
extends NotificationCompat.Style {
    private Bitmap mBigLargeIcon;
    private boolean mBigLargeIconSet;
    private Bitmap mPicture;

    public NotificationCompat.BigPictureStyle() {
    }

    public NotificationCompat.BigPictureStyle(NotificationCompat.Builder builder) {
        this.setBuilder(builder);
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    @Override
    public void apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
        if (Build.VERSION.SDK_INT >= 16) {
            notificationBuilderWithBuilderAccessor = new Notification.BigPictureStyle(notificationBuilderWithBuilderAccessor.getBuilder()).setBigContentTitle(this.mBigContentTitle).bigPicture(this.mPicture);
            if (this.mBigLargeIconSet) {
                notificationBuilderWithBuilderAccessor.bigLargeIcon(this.mBigLargeIcon);
            }
            if (this.mSummaryTextSet) {
                notificationBuilderWithBuilderAccessor.setSummaryText(this.mSummaryText);
            }
        }
    }

    public NotificationCompat.BigPictureStyle bigLargeIcon(Bitmap bitmap) {
        this.mBigLargeIcon = bitmap;
        this.mBigLargeIconSet = true;
        return this;
    }

    public NotificationCompat.BigPictureStyle bigPicture(Bitmap bitmap) {
        this.mPicture = bitmap;
        return this;
    }

    public NotificationCompat.BigPictureStyle setBigContentTitle(CharSequence charSequence) {
        this.mBigContentTitle = NotificationCompat.Builder.limitCharSequenceLength(charSequence);
        return this;
    }

    public NotificationCompat.BigPictureStyle setSummaryText(CharSequence charSequence) {
        this.mSummaryText = NotificationCompat.Builder.limitCharSequenceLength(charSequence);
        this.mSummaryTextSet = true;
        return this;
    }
}
