/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Notification
 *  android.app.Notification$Builder
 *  android.app.Notification$DecoratedCustomViewStyle
 *  android.app.Notification$Style
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.widget.RemoteViews
 */
package android.support.v4.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RestrictTo;
import android.support.compat.R;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;
import java.util.ArrayList;

public static class NotificationCompat.DecoratedCustomViewStyle
extends NotificationCompat.Style {
    private static final int MAX_ACTION_BUTTONS = 3;

    private RemoteViews createRemoteViews(RemoteViews remoteViews, boolean bl) {
        boolean bl2;
        int n;
        int n2 = R.layout.notification_template_custom_big;
        boolean bl3 = true;
        int n3 = 0;
        RemoteViews remoteViews2 = this.applyStandardTemplate(true, n2, false);
        remoteViews2.removeAllViews(R.id.actions);
        if (bl && this.mBuilder.mActions != null && (n = Math.min(this.mBuilder.mActions.size(), 3)) > 0) {
            n2 = 0;
            do {
                bl2 = bl3;
                if (n2 < n) {
                    RemoteViews remoteViews3 = this.generateActionButton(this.mBuilder.mActions.get(n2));
                    remoteViews2.addView(R.id.actions, remoteViews3);
                    ++n2;
                    continue;
                }
                break;
            } while (true);
        } else {
            bl2 = false;
        }
        n2 = bl2 ? n3 : 8;
        remoteViews2.setViewVisibility(R.id.actions, n2);
        remoteViews2.setViewVisibility(R.id.action_divider, n2);
        this.buildIntoRemoteViews(remoteViews2, remoteViews);
        return remoteViews2;
    }

    private RemoteViews generateActionButton(NotificationCompat.Action action) {
        boolean bl = action.actionIntent == null;
        String string = this.mBuilder.mContext.getPackageName();
        int n = bl ? R.layout.notification_action_tombstone : R.layout.notification_action;
        string = new RemoteViews(string, n);
        string.setImageViewBitmap(R.id.action_image, this.createColoredBitmap(action.getIcon(), this.mBuilder.mContext.getResources().getColor(R.color.notification_action_color_filter)));
        string.setTextViewText(R.id.action_text, action.title);
        if (!bl) {
            string.setOnClickPendingIntent(R.id.action_container, action.actionIntent);
        }
        if (Build.VERSION.SDK_INT >= 15) {
            string.setContentDescription(R.id.action_container, action.title);
        }
        return string;
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    @Override
    public void apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
        if (Build.VERSION.SDK_INT >= 24) {
            notificationBuilderWithBuilderAccessor.getBuilder().setStyle((Notification.Style)new Notification.DecoratedCustomViewStyle());
        }
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    @Override
    public RemoteViews makeBigContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
        if (Build.VERSION.SDK_INT >= 24) {
            return null;
        }
        notificationBuilderWithBuilderAccessor = this.mBuilder.getBigContentView();
        if (notificationBuilderWithBuilderAccessor == null) {
            notificationBuilderWithBuilderAccessor = this.mBuilder.getContentView();
        }
        if (notificationBuilderWithBuilderAccessor == null) {
            return null;
        }
        return this.createRemoteViews((RemoteViews)notificationBuilderWithBuilderAccessor, true);
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    @Override
    public RemoteViews makeContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
        if (Build.VERSION.SDK_INT >= 24) {
            return null;
        }
        if (this.mBuilder.getContentView() == null) {
            return null;
        }
        return this.createRemoteViews(this.mBuilder.getContentView(), false);
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    @Override
    public RemoteViews makeHeadsUpContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
        if (Build.VERSION.SDK_INT >= 24) {
            return null;
        }
        RemoteViews remoteViews = this.mBuilder.getHeadsUpContentView();
        notificationBuilderWithBuilderAccessor = remoteViews != null ? remoteViews : this.mBuilder.getContentView();
        if (remoteViews == null) {
            return null;
        }
        return this.createRemoteViews((RemoteViews)notificationBuilderWithBuilderAccessor, true);
    }
}
