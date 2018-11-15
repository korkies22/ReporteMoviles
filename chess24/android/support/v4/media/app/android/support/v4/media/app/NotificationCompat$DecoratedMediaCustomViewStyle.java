/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Notification
 *  android.app.Notification$Builder
 *  android.app.Notification$DecoratedMediaCustomViewStyle
 *  android.app.Notification$MediaStyle
 *  android.app.Notification$Style
 *  android.content.Context
 *  android.content.res.Resources
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.widget.RemoteViews
 */
package android.support.v4.media.app;

import android.app.Notification;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.RestrictTo;
import android.support.mediacompat.R;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.v4.app.NotificationCompat;
import android.support.v4.media.app.NotificationCompat;
import android.widget.RemoteViews;

public static class NotificationCompat.DecoratedMediaCustomViewStyle
extends NotificationCompat.MediaStyle {
    private void setBackgroundColor(RemoteViews remoteViews) {
        int n = this.mBuilder.getColor() != 0 ? this.mBuilder.getColor() : this.mBuilder.mContext.getResources().getColor(R.color.notification_material_background_media_default_color);
        remoteViews.setInt(R.id.status_bar_latest_event_content, "setBackgroundColor", n);
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    @Override
    public void apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
        if (Build.VERSION.SDK_INT >= 24) {
            notificationBuilderWithBuilderAccessor.getBuilder().setStyle((Notification.Style)this.fillInMediaStyle((Notification.MediaStyle)new Notification.DecoratedMediaCustomViewStyle()));
            return;
        }
        super.apply(notificationBuilderWithBuilderAccessor);
    }

    @Override
    int getBigContentViewLayoutResource(int n) {
        if (n <= 3) {
            return R.layout.notification_template_big_media_narrow_custom;
        }
        return R.layout.notification_template_big_media_custom;
    }

    @Override
    int getContentViewLayoutResource() {
        if (this.mBuilder.getContentView() != null) {
            return R.layout.notification_template_media_custom;
        }
        return super.getContentViewLayoutResource();
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    @Override
    public RemoteViews makeBigContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
        if (Build.VERSION.SDK_INT >= 24) {
            return null;
        }
        notificationBuilderWithBuilderAccessor = this.mBuilder.getBigContentView() != null ? this.mBuilder.getBigContentView() : this.mBuilder.getContentView();
        if (notificationBuilderWithBuilderAccessor == null) {
            return null;
        }
        RemoteViews remoteViews = this.generateBigContentView();
        this.buildIntoRemoteViews(remoteViews, (RemoteViews)notificationBuilderWithBuilderAccessor);
        if (Build.VERSION.SDK_INT >= 21) {
            this.setBackgroundColor(remoteViews);
        }
        return remoteViews;
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    @Override
    public RemoteViews makeContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
        if (Build.VERSION.SDK_INT >= 24) {
            return null;
        }
        notificationBuilderWithBuilderAccessor = this.mBuilder.getContentView();
        boolean bl = false;
        boolean bl2 = notificationBuilderWithBuilderAccessor != null;
        if (Build.VERSION.SDK_INT >= 21) {
            if (bl2 || this.mBuilder.getBigContentView() != null) {
                bl = true;
            }
            if (bl) {
                notificationBuilderWithBuilderAccessor = this.generateContentView();
                if (bl2) {
                    this.buildIntoRemoteViews((RemoteViews)notificationBuilderWithBuilderAccessor, this.mBuilder.getContentView());
                }
                this.setBackgroundColor((RemoteViews)notificationBuilderWithBuilderAccessor);
                return notificationBuilderWithBuilderAccessor;
            }
        } else {
            notificationBuilderWithBuilderAccessor = this.generateContentView();
            if (bl2) {
                this.buildIntoRemoteViews((RemoteViews)notificationBuilderWithBuilderAccessor, this.mBuilder.getContentView());
                return notificationBuilderWithBuilderAccessor;
            }
        }
        return null;
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    @Override
    public RemoteViews makeHeadsUpContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
        if (Build.VERSION.SDK_INT >= 24) {
            return null;
        }
        notificationBuilderWithBuilderAccessor = this.mBuilder.getHeadsUpContentView() != null ? this.mBuilder.getHeadsUpContentView() : this.mBuilder.getContentView();
        if (notificationBuilderWithBuilderAccessor == null) {
            return null;
        }
        RemoteViews remoteViews = this.generateBigContentView();
        this.buildIntoRemoteViews(remoteViews, (RemoteViews)notificationBuilderWithBuilderAccessor);
        if (Build.VERSION.SDK_INT >= 21) {
            this.setBackgroundColor(remoteViews);
        }
        return remoteViews;
    }
}
