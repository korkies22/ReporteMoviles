/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Notification
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.media.AudioAttributes
 *  android.media.AudioAttributes$Builder
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.widget.RemoteViews
 */
package android.support.v4.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompatBuilder;
import android.widget.RemoteViews;
import java.util.ArrayList;

public static class NotificationCompat.Builder {
    private static final int MAX_CHARSEQUENCE_LENGTH = 5120;
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public ArrayList<NotificationCompat.Action> mActions = new ArrayList();
    int mBadgeIcon = 0;
    RemoteViews mBigContentView;
    String mCategory;
    String mChannelId;
    int mColor = 0;
    boolean mColorized;
    boolean mColorizedSet;
    CharSequence mContentInfo;
    PendingIntent mContentIntent;
    CharSequence mContentText;
    CharSequence mContentTitle;
    RemoteViews mContentView;
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public Context mContext;
    Bundle mExtras;
    PendingIntent mFullScreenIntent;
    int mGroupAlertBehavior = 0;
    String mGroupKey;
    boolean mGroupSummary;
    RemoteViews mHeadsUpContentView;
    Bitmap mLargeIcon;
    boolean mLocalOnly = false;
    Notification mNotification = new Notification();
    int mNumber;
    @Deprecated
    public ArrayList<String> mPeople;
    int mPriority;
    int mProgress;
    boolean mProgressIndeterminate;
    int mProgressMax;
    Notification mPublicVersion;
    CharSequence[] mRemoteInputHistory;
    String mShortcutId;
    boolean mShowWhen = true;
    String mSortKey;
    NotificationCompat.Style mStyle;
    CharSequence mSubText;
    RemoteViews mTickerView;
    long mTimeout;
    boolean mUseChronometer;
    int mVisibility = 0;

    @Deprecated
    public NotificationCompat.Builder(Context context) {
        this(context, null);
    }

    public NotificationCompat.Builder(@NonNull Context context, @NonNull String string) {
        this.mContext = context;
        this.mChannelId = string;
        this.mNotification.when = System.currentTimeMillis();
        this.mNotification.audioStreamType = -1;
        this.mPriority = 0;
        this.mPeople = new ArrayList();
    }

    protected static CharSequence limitCharSequenceLength(CharSequence charSequence) {
        if (charSequence == null) {
            return charSequence;
        }
        CharSequence charSequence2 = charSequence;
        if (charSequence.length() > 5120) {
            charSequence2 = charSequence.subSequence(0, 5120);
        }
        return charSequence2;
    }

    private void setFlag(int n, boolean bl) {
        if (bl) {
            Notification notification = this.mNotification;
            notification.flags = n | notification.flags;
            return;
        }
        Notification notification = this.mNotification;
        notification.flags = ~ n & notification.flags;
    }

    public NotificationCompat.Builder addAction(int n, CharSequence charSequence, PendingIntent pendingIntent) {
        this.mActions.add(new NotificationCompat.Action(n, charSequence, pendingIntent));
        return this;
    }

    public NotificationCompat.Builder addAction(NotificationCompat.Action action) {
        this.mActions.add(action);
        return this;
    }

    public NotificationCompat.Builder addExtras(Bundle bundle) {
        if (bundle != null) {
            if (this.mExtras == null) {
                this.mExtras = new Bundle(bundle);
                return this;
            }
            this.mExtras.putAll(bundle);
        }
        return this;
    }

    public NotificationCompat.Builder addPerson(String string) {
        this.mPeople.add(string);
        return this;
    }

    public Notification build() {
        return new NotificationCompatBuilder(this).build();
    }

    public NotificationCompat.Builder extend(NotificationCompat.Extender extender) {
        extender.extend(this);
        return this;
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public RemoteViews getBigContentView() {
        return this.mBigContentView;
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public int getColor() {
        return this.mColor;
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public RemoteViews getContentView() {
        return this.mContentView;
    }

    public Bundle getExtras() {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        return this.mExtras;
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public RemoteViews getHeadsUpContentView() {
        return this.mHeadsUpContentView;
    }

    @Deprecated
    public Notification getNotification() {
        return this.build();
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public int getPriority() {
        return this.mPriority;
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public long getWhenIfShowing() {
        if (this.mShowWhen) {
            return this.mNotification.when;
        }
        return 0L;
    }

    public NotificationCompat.Builder setAutoCancel(boolean bl) {
        this.setFlag(16, bl);
        return this;
    }

    public NotificationCompat.Builder setBadgeIconType(int n) {
        this.mBadgeIcon = n;
        return this;
    }

    public NotificationCompat.Builder setCategory(String string) {
        this.mCategory = string;
        return this;
    }

    public NotificationCompat.Builder setChannelId(@NonNull String string) {
        this.mChannelId = string;
        return this;
    }

    public NotificationCompat.Builder setColor(@ColorInt int n) {
        this.mColor = n;
        return this;
    }

    public NotificationCompat.Builder setColorized(boolean bl) {
        this.mColorized = bl;
        this.mColorizedSet = true;
        return this;
    }

    public NotificationCompat.Builder setContent(RemoteViews remoteViews) {
        this.mNotification.contentView = remoteViews;
        return this;
    }

    public NotificationCompat.Builder setContentInfo(CharSequence charSequence) {
        this.mContentInfo = NotificationCompat.Builder.limitCharSequenceLength(charSequence);
        return this;
    }

    public NotificationCompat.Builder setContentIntent(PendingIntent pendingIntent) {
        this.mContentIntent = pendingIntent;
        return this;
    }

    public NotificationCompat.Builder setContentText(CharSequence charSequence) {
        this.mContentText = NotificationCompat.Builder.limitCharSequenceLength(charSequence);
        return this;
    }

    public NotificationCompat.Builder setContentTitle(CharSequence charSequence) {
        this.mContentTitle = NotificationCompat.Builder.limitCharSequenceLength(charSequence);
        return this;
    }

    public NotificationCompat.Builder setCustomBigContentView(RemoteViews remoteViews) {
        this.mBigContentView = remoteViews;
        return this;
    }

    public NotificationCompat.Builder setCustomContentView(RemoteViews remoteViews) {
        this.mContentView = remoteViews;
        return this;
    }

    public NotificationCompat.Builder setCustomHeadsUpContentView(RemoteViews remoteViews) {
        this.mHeadsUpContentView = remoteViews;
        return this;
    }

    public NotificationCompat.Builder setDefaults(int n) {
        this.mNotification.defaults = n;
        if ((n & 4) != 0) {
            Notification notification = this.mNotification;
            notification.flags |= 1;
        }
        return this;
    }

    public NotificationCompat.Builder setDeleteIntent(PendingIntent pendingIntent) {
        this.mNotification.deleteIntent = pendingIntent;
        return this;
    }

    public NotificationCompat.Builder setExtras(Bundle bundle) {
        this.mExtras = bundle;
        return this;
    }

    public NotificationCompat.Builder setFullScreenIntent(PendingIntent pendingIntent, boolean bl) {
        this.mFullScreenIntent = pendingIntent;
        this.setFlag(128, bl);
        return this;
    }

    public NotificationCompat.Builder setGroup(String string) {
        this.mGroupKey = string;
        return this;
    }

    public NotificationCompat.Builder setGroupAlertBehavior(int n) {
        this.mGroupAlertBehavior = n;
        return this;
    }

    public NotificationCompat.Builder setGroupSummary(boolean bl) {
        this.mGroupSummary = bl;
        return this;
    }

    public NotificationCompat.Builder setLargeIcon(Bitmap bitmap) {
        this.mLargeIcon = bitmap;
        return this;
    }

    public NotificationCompat.Builder setLights(@ColorInt int n, int n2, int n3) {
        this.mNotification.ledARGB = n;
        this.mNotification.ledOnMS = n2;
        this.mNotification.ledOffMS = n3;
        n = this.mNotification.ledOnMS != 0 && this.mNotification.ledOffMS != 0 ? 1 : 0;
        this.mNotification.flags = n | this.mNotification.flags & -2;
        return this;
    }

    public NotificationCompat.Builder setLocalOnly(boolean bl) {
        this.mLocalOnly = bl;
        return this;
    }

    public NotificationCompat.Builder setNumber(int n) {
        this.mNumber = n;
        return this;
    }

    public NotificationCompat.Builder setOngoing(boolean bl) {
        this.setFlag(2, bl);
        return this;
    }

    public NotificationCompat.Builder setOnlyAlertOnce(boolean bl) {
        this.setFlag(8, bl);
        return this;
    }

    public NotificationCompat.Builder setPriority(int n) {
        this.mPriority = n;
        return this;
    }

    public NotificationCompat.Builder setProgress(int n, int n2, boolean bl) {
        this.mProgressMax = n;
        this.mProgress = n2;
        this.mProgressIndeterminate = bl;
        return this;
    }

    public NotificationCompat.Builder setPublicVersion(Notification notification) {
        this.mPublicVersion = notification;
        return this;
    }

    public NotificationCompat.Builder setRemoteInputHistory(CharSequence[] arrcharSequence) {
        this.mRemoteInputHistory = arrcharSequence;
        return this;
    }

    public NotificationCompat.Builder setShortcutId(String string) {
        this.mShortcutId = string;
        return this;
    }

    public NotificationCompat.Builder setShowWhen(boolean bl) {
        this.mShowWhen = bl;
        return this;
    }

    public NotificationCompat.Builder setSmallIcon(int n) {
        this.mNotification.icon = n;
        return this;
    }

    public NotificationCompat.Builder setSmallIcon(int n, int n2) {
        this.mNotification.icon = n;
        this.mNotification.iconLevel = n2;
        return this;
    }

    public NotificationCompat.Builder setSortKey(String string) {
        this.mSortKey = string;
        return this;
    }

    public NotificationCompat.Builder setSound(Uri uri) {
        this.mNotification.sound = uri;
        this.mNotification.audioStreamType = -1;
        if (Build.VERSION.SDK_INT >= 21) {
            this.mNotification.audioAttributes = new AudioAttributes.Builder().setContentType(4).setUsage(5).build();
        }
        return this;
    }

    public NotificationCompat.Builder setSound(Uri uri, int n) {
        this.mNotification.sound = uri;
        this.mNotification.audioStreamType = n;
        if (Build.VERSION.SDK_INT >= 21) {
            this.mNotification.audioAttributes = new AudioAttributes.Builder().setContentType(4).setLegacyStreamType(n).build();
        }
        return this;
    }

    public NotificationCompat.Builder setStyle(NotificationCompat.Style style) {
        if (this.mStyle != style) {
            this.mStyle = style;
            if (this.mStyle != null) {
                this.mStyle.setBuilder(this);
            }
        }
        return this;
    }

    public NotificationCompat.Builder setSubText(CharSequence charSequence) {
        this.mSubText = NotificationCompat.Builder.limitCharSequenceLength(charSequence);
        return this;
    }

    public NotificationCompat.Builder setTicker(CharSequence charSequence) {
        this.mNotification.tickerText = NotificationCompat.Builder.limitCharSequenceLength(charSequence);
        return this;
    }

    public NotificationCompat.Builder setTicker(CharSequence charSequence, RemoteViews remoteViews) {
        this.mNotification.tickerText = NotificationCompat.Builder.limitCharSequenceLength(charSequence);
        this.mTickerView = remoteViews;
        return this;
    }

    public NotificationCompat.Builder setTimeoutAfter(long l) {
        this.mTimeout = l;
        return this;
    }

    public NotificationCompat.Builder setUsesChronometer(boolean bl) {
        this.mUseChronometer = bl;
        return this;
    }

    public NotificationCompat.Builder setVibrate(long[] arrl) {
        this.mNotification.vibrate = arrl;
        return this;
    }

    public NotificationCompat.Builder setVisibility(int n) {
        this.mVisibility = n;
        return this;
    }

    public NotificationCompat.Builder setWhen(long l) {
        this.mNotification.when = l;
        return this;
    }
}
