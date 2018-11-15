/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Notification
 *  android.app.Notification$Action
 *  android.app.Notification$Action$Builder
 *  android.app.Notification$BigPictureStyle
 *  android.app.Notification$BigTextStyle
 *  android.app.Notification$Builder
 *  android.app.Notification$DecoratedCustomViewStyle
 *  android.app.Notification$InboxStyle
 *  android.app.Notification$MessagingStyle
 *  android.app.Notification$MessagingStyle$Message
 *  android.app.Notification$Style
 *  android.app.PendingIntent
 *  android.app.RemoteInput
 *  android.app.RemoteInput$Builder
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.PorterDuffColorFilter
 *  android.graphics.drawable.Drawable
 *  android.media.AudioAttributes
 *  android.media.AudioAttributes$Builder
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.os.SystemClock
 *  android.text.SpannableStringBuilder
 *  android.text.TextUtils
 *  android.text.style.TextAppearanceSpan
 *  android.util.SparseArray
 *  android.widget.RemoteViews
 */
package android.support.v4.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.compat.R;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.v4.app.NotificationCompatBuilder;
import android.support.v4.app.NotificationCompatJellybean;
import android.support.v4.app.RemoteInput;
import android.support.v4.text.BidiFormatter;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.util.SparseArray;
import android.widget.RemoteViews;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class NotificationCompat {
    public static final int BADGE_ICON_LARGE = 2;
    public static final int BADGE_ICON_NONE = 0;
    public static final int BADGE_ICON_SMALL = 1;
    public static final String CATEGORY_ALARM = "alarm";
    public static final String CATEGORY_CALL = "call";
    public static final String CATEGORY_EMAIL = "email";
    public static final String CATEGORY_ERROR = "err";
    public static final String CATEGORY_EVENT = "event";
    public static final String CATEGORY_MESSAGE = "msg";
    public static final String CATEGORY_PROGRESS = "progress";
    public static final String CATEGORY_PROMO = "promo";
    public static final String CATEGORY_RECOMMENDATION = "recommendation";
    public static final String CATEGORY_REMINDER = "reminder";
    public static final String CATEGORY_SERVICE = "service";
    public static final String CATEGORY_SOCIAL = "social";
    public static final String CATEGORY_STATUS = "status";
    public static final String CATEGORY_SYSTEM = "sys";
    public static final String CATEGORY_TRANSPORT = "transport";
    @ColorInt
    public static final int COLOR_DEFAULT = 0;
    public static final int DEFAULT_ALL = -1;
    public static final int DEFAULT_LIGHTS = 4;
    public static final int DEFAULT_SOUND = 1;
    public static final int DEFAULT_VIBRATE = 2;
    public static final String EXTRA_AUDIO_CONTENTS_URI = "android.audioContents";
    public static final String EXTRA_BACKGROUND_IMAGE_URI = "android.backgroundImageUri";
    public static final String EXTRA_BIG_TEXT = "android.bigText";
    public static final String EXTRA_COMPACT_ACTIONS = "android.compactActions";
    public static final String EXTRA_CONVERSATION_TITLE = "android.conversationTitle";
    public static final String EXTRA_INFO_TEXT = "android.infoText";
    public static final String EXTRA_LARGE_ICON = "android.largeIcon";
    public static final String EXTRA_LARGE_ICON_BIG = "android.largeIcon.big";
    public static final String EXTRA_MEDIA_SESSION = "android.mediaSession";
    public static final String EXTRA_MESSAGES = "android.messages";
    public static final String EXTRA_PEOPLE = "android.people";
    public static final String EXTRA_PICTURE = "android.picture";
    public static final String EXTRA_PROGRESS = "android.progress";
    public static final String EXTRA_PROGRESS_INDETERMINATE = "android.progressIndeterminate";
    public static final String EXTRA_PROGRESS_MAX = "android.progressMax";
    public static final String EXTRA_REMOTE_INPUT_HISTORY = "android.remoteInputHistory";
    public static final String EXTRA_SELF_DISPLAY_NAME = "android.selfDisplayName";
    public static final String EXTRA_SHOW_CHRONOMETER = "android.showChronometer";
    public static final String EXTRA_SHOW_WHEN = "android.showWhen";
    public static final String EXTRA_SMALL_ICON = "android.icon";
    public static final String EXTRA_SUB_TEXT = "android.subText";
    public static final String EXTRA_SUMMARY_TEXT = "android.summaryText";
    public static final String EXTRA_TEMPLATE = "android.template";
    public static final String EXTRA_TEXT = "android.text";
    public static final String EXTRA_TEXT_LINES = "android.textLines";
    public static final String EXTRA_TITLE = "android.title";
    public static final String EXTRA_TITLE_BIG = "android.title.big";
    public static final int FLAG_AUTO_CANCEL = 16;
    public static final int FLAG_FOREGROUND_SERVICE = 64;
    public static final int FLAG_GROUP_SUMMARY = 512;
    @Deprecated
    public static final int FLAG_HIGH_PRIORITY = 128;
    public static final int FLAG_INSISTENT = 4;
    public static final int FLAG_LOCAL_ONLY = 256;
    public static final int FLAG_NO_CLEAR = 32;
    public static final int FLAG_ONGOING_EVENT = 2;
    public static final int FLAG_ONLY_ALERT_ONCE = 8;
    public static final int FLAG_SHOW_LIGHTS = 1;
    public static final int GROUP_ALERT_ALL = 0;
    public static final int GROUP_ALERT_CHILDREN = 2;
    public static final int GROUP_ALERT_SUMMARY = 1;
    public static final int PRIORITY_DEFAULT = 0;
    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_LOW = -1;
    public static final int PRIORITY_MAX = 2;
    public static final int PRIORITY_MIN = -2;
    public static final int STREAM_DEFAULT = -1;
    public static final int VISIBILITY_PRIVATE = 0;
    public static final int VISIBILITY_PUBLIC = 1;
    public static final int VISIBILITY_SECRET = -1;

    public static Action getAction(Notification notification, int n) {
        if (Build.VERSION.SDK_INT >= 20) {
            return NotificationCompat.getActionCompatFromAction(notification.actions[n]);
        }
        int n2 = Build.VERSION.SDK_INT;
        Object var3_3 = null;
        if (n2 >= 19) {
            Notification.Action action = notification.actions[n];
            SparseArray sparseArray = notification.extras.getSparseParcelableArray("android.support.actionExtras");
            notification = var3_3;
            if (sparseArray != null) {
                notification = (Bundle)sparseArray.get(n);
            }
            return NotificationCompatJellybean.readAction(action.icon, action.title, action.actionIntent, (Bundle)notification);
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return NotificationCompatJellybean.getAction(notification, n);
        }
        return null;
    }

    @RequiresApi(value=20)
    static Action getActionCompatFromAction(Notification.Action action) {
        RemoteInput[] arrremoteInput;
        android.app.RemoteInput[] arrremoteInput2 = action.getRemoteInputs();
        boolean bl = false;
        if (arrremoteInput2 == null) {
            arrremoteInput = null;
        } else {
            arrremoteInput = new RemoteInput[arrremoteInput2.length];
            for (int i = 0; i < arrremoteInput2.length; ++i) {
                android.app.RemoteInput remoteInput = arrremoteInput2[i];
                arrremoteInput[i] = new RemoteInput(remoteInput.getResultKey(), remoteInput.getLabel(), remoteInput.getChoices(), remoteInput.getAllowFreeFormInput(), remoteInput.getExtras(), null);
            }
        }
        if (Build.VERSION.SDK_INT >= 24) {
            if (action.getExtras().getBoolean("android.support.allowGeneratedReplies") || action.getAllowGeneratedReplies()) {
                bl = true;
            }
        } else {
            bl = action.getExtras().getBoolean("android.support.allowGeneratedReplies");
        }
        return new Action(action.icon, action.title, action.actionIntent, action.getExtras(), arrremoteInput, null, bl);
    }

    public static int getActionCount(Notification notification) {
        int n = Build.VERSION.SDK_INT;
        int n2 = 0;
        if (n >= 19) {
            if (notification.actions != null) {
                n2 = notification.actions.length;
            }
            return n2;
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return NotificationCompatJellybean.getActionCount(notification);
        }
        return 0;
    }

    public static int getBadgeIconType(Notification notification) {
        if (Build.VERSION.SDK_INT >= 26) {
            return notification.getBadgeIconType();
        }
        return 0;
    }

    public static String getCategory(Notification notification) {
        if (Build.VERSION.SDK_INT >= 21) {
            return notification.category;
        }
        return null;
    }

    public static String getChannelId(Notification notification) {
        if (Build.VERSION.SDK_INT >= 26) {
            return notification.getChannelId();
        }
        return null;
    }

    public static Bundle getExtras(Notification notification) {
        if (Build.VERSION.SDK_INT >= 19) {
            return notification.extras;
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return NotificationCompatJellybean.getExtras(notification);
        }
        return null;
    }

    public static String getGroup(Notification notification) {
        if (Build.VERSION.SDK_INT >= 20) {
            return notification.getGroup();
        }
        if (Build.VERSION.SDK_INT >= 19) {
            return notification.extras.getString("android.support.groupKey");
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return NotificationCompatJellybean.getExtras(notification).getString("android.support.groupKey");
        }
        return null;
    }

    public static int getGroupAlertBehavior(Notification notification) {
        if (Build.VERSION.SDK_INT >= 26) {
            return notification.getGroupAlertBehavior();
        }
        return 0;
    }

    public static boolean getLocalOnly(Notification notification) {
        int n = Build.VERSION.SDK_INT;
        boolean bl = false;
        if (n >= 20) {
            if ((notification.flags & 256) != 0) {
                bl = true;
            }
            return bl;
        }
        if (Build.VERSION.SDK_INT >= 19) {
            return notification.extras.getBoolean("android.support.localOnly");
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return NotificationCompatJellybean.getExtras(notification).getBoolean("android.support.localOnly");
        }
        return false;
    }

    static Notification[] getNotificationArrayFromBundle(Bundle bundle, String string2) {
        Parcelable[] arrparcelable = bundle.getParcelableArray(string2);
        if (!(arrparcelable instanceof Notification[]) && arrparcelable != null) {
            Notification[] arrnotification = new Notification[arrparcelable.length];
            for (int i = 0; i < arrparcelable.length; ++i) {
                arrnotification[i] = (Notification)arrparcelable[i];
            }
            bundle.putParcelableArray(string2, (Parcelable[])arrnotification);
            return arrnotification;
        }
        return (Notification[])arrparcelable;
    }

    public static String getShortcutId(Notification notification) {
        if (Build.VERSION.SDK_INT >= 26) {
            return notification.getShortcutId();
        }
        return null;
    }

    public static String getSortKey(Notification notification) {
        if (Build.VERSION.SDK_INT >= 20) {
            return notification.getSortKey();
        }
        if (Build.VERSION.SDK_INT >= 19) {
            return notification.extras.getString("android.support.sortKey");
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return NotificationCompatJellybean.getExtras(notification).getString("android.support.sortKey");
        }
        return null;
    }

    public static long getTimeoutAfter(Notification notification) {
        if (Build.VERSION.SDK_INT >= 26) {
            return notification.getTimeoutAfter();
        }
        return 0L;
    }

    public static boolean isGroupSummary(Notification notification) {
        int n = Build.VERSION.SDK_INT;
        boolean bl = false;
        if (n >= 20) {
            if ((notification.flags & 512) != 0) {
                bl = true;
            }
            return bl;
        }
        if (Build.VERSION.SDK_INT >= 19) {
            return notification.extras.getBoolean("android.support.isGroupSummary");
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return NotificationCompatJellybean.getExtras(notification).getBoolean("android.support.isGroupSummary");
        }
        return false;
    }

    public static class Action {
        public PendingIntent actionIntent;
        public int icon;
        private boolean mAllowGeneratedReplies;
        private final RemoteInput[] mDataOnlyRemoteInputs;
        final Bundle mExtras;
        private final RemoteInput[] mRemoteInputs;
        public CharSequence title;

        public Action(int n, CharSequence charSequence, PendingIntent pendingIntent) {
            this(n, charSequence, pendingIntent, new Bundle(), null, null, true);
        }

        Action(int n, CharSequence charSequence, PendingIntent pendingIntent, Bundle bundle, RemoteInput[] arrremoteInput, RemoteInput[] arrremoteInput2, boolean bl) {
            this.icon = n;
            this.title = Builder.limitCharSequenceLength(charSequence);
            this.actionIntent = pendingIntent;
            if (bundle == null) {
                bundle = new Bundle();
            }
            this.mExtras = bundle;
            this.mRemoteInputs = arrremoteInput;
            this.mDataOnlyRemoteInputs = arrremoteInput2;
            this.mAllowGeneratedReplies = bl;
        }

        public PendingIntent getActionIntent() {
            return this.actionIntent;
        }

        public boolean getAllowGeneratedReplies() {
            return this.mAllowGeneratedReplies;
        }

        public RemoteInput[] getDataOnlyRemoteInputs() {
            return this.mDataOnlyRemoteInputs;
        }

        public Bundle getExtras() {
            return this.mExtras;
        }

        public int getIcon() {
            return this.icon;
        }

        public RemoteInput[] getRemoteInputs() {
            return this.mRemoteInputs;
        }

        public CharSequence getTitle() {
            return this.title;
        }
    }

    public static final class Action$Builder {
        private boolean mAllowGeneratedReplies = true;
        private final Bundle mExtras;
        private final int mIcon;
        private final PendingIntent mIntent;
        private ArrayList<RemoteInput> mRemoteInputs;
        private final CharSequence mTitle;

        public Action$Builder(int n, CharSequence charSequence, PendingIntent pendingIntent) {
            this(n, charSequence, pendingIntent, new Bundle(), null, true);
        }

        private Action$Builder(int n, CharSequence object, PendingIntent pendingIntent, Bundle bundle, RemoteInput[] arrremoteInput, boolean bl) {
            this.mIcon = n;
            this.mTitle = Builder.limitCharSequenceLength((CharSequence)object);
            this.mIntent = pendingIntent;
            this.mExtras = bundle;
            object = arrremoteInput == null ? null : new ArrayList<RemoteInput>(Arrays.asList(arrremoteInput));
            this.mRemoteInputs = object;
            this.mAllowGeneratedReplies = bl;
        }

        public Action$Builder(Action action) {
            this(action.icon, action.title, action.actionIntent, new Bundle(action.mExtras), action.getRemoteInputs(), action.getAllowGeneratedReplies());
        }

        public Action$Builder addExtras(Bundle bundle) {
            if (bundle != null) {
                this.mExtras.putAll(bundle);
            }
            return this;
        }

        public Action$Builder addRemoteInput(RemoteInput remoteInput) {
            if (this.mRemoteInputs == null) {
                this.mRemoteInputs = new ArrayList();
            }
            this.mRemoteInputs.add(remoteInput);
            return this;
        }

        public Action build() {
            RemoteInput[] arrremoteInput;
            Object object;
            block3 : {
                object = new RemoteInput[]();
                ArrayList<RemoteInput> arrayList = new ArrayList<RemoteInput>();
                if (this.mRemoteInputs != null) {
                    for (RemoteInput remoteInput : this.mRemoteInputs) {
                        if (remoteInput.isDataOnly()) {
                            object.add(remoteInput);
                            continue;
                        }
                        arrayList.add(remoteInput);
                    }
                }
                boolean bl = object.isEmpty();
                arrremoteInput = null;
                object = bl ? null : object.toArray(new RemoteInput[object.size()]);
                if (arrayList.isEmpty()) break block3;
                arrremoteInput = arrayList.toArray(new RemoteInput[arrayList.size()]);
            }
            return new Action(this.mIcon, this.mTitle, this.mIntent, this.mExtras, arrremoteInput, (RemoteInput[])object, this.mAllowGeneratedReplies);
        }

        public Action$Builder extend(Action$Extender action$Extender) {
            action$Extender.extend(this);
            return this;
        }

        public Bundle getExtras() {
            return this.mExtras;
        }

        public Action$Builder setAllowGeneratedReplies(boolean bl) {
            this.mAllowGeneratedReplies = bl;
            return this;
        }
    }

    public static interface Action$Extender {
        public Action$Builder extend(Action$Builder var1);
    }

    public static final class Action$WearableExtender
    implements Action$Extender {
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

        public Action$WearableExtender() {
        }

        public Action$WearableExtender(Action action) {
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

        public Action$WearableExtender clone() {
            Action$WearableExtender action$WearableExtender = new Action$WearableExtender();
            action$WearableExtender.mFlags = this.mFlags;
            action$WearableExtender.mInProgressLabel = this.mInProgressLabel;
            action$WearableExtender.mConfirmLabel = this.mConfirmLabel;
            action$WearableExtender.mCancelLabel = this.mCancelLabel;
            return action$WearableExtender;
        }

        @Override
        public Action$Builder extend(Action$Builder action$Builder) {
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
            action$Builder.getExtras().putBundle(EXTRA_WEARABLE_EXTENSIONS, bundle);
            return action$Builder;
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

        public Action$WearableExtender setAvailableOffline(boolean bl) {
            this.setFlag(1, bl);
            return this;
        }

        public Action$WearableExtender setCancelLabel(CharSequence charSequence) {
            this.mCancelLabel = charSequence;
            return this;
        }

        public Action$WearableExtender setConfirmLabel(CharSequence charSequence) {
            this.mConfirmLabel = charSequence;
            return this;
        }

        public Action$WearableExtender setHintDisplayActionInline(boolean bl) {
            this.setFlag(4, bl);
            return this;
        }

        public Action$WearableExtender setHintLaunchesActivity(boolean bl) {
            this.setFlag(2, bl);
            return this;
        }

        public Action$WearableExtender setInProgressLabel(CharSequence charSequence) {
            this.mInProgressLabel = charSequence;
            return this;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public static @interface BadgeIconType {
    }

    public static class BigPictureStyle
    extends Style {
        private Bitmap mBigLargeIcon;
        private boolean mBigLargeIconSet;
        private Bitmap mPicture;

        public BigPictureStyle() {
        }

        public BigPictureStyle(Builder builder) {
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

        public BigPictureStyle bigLargeIcon(Bitmap bitmap) {
            this.mBigLargeIcon = bitmap;
            this.mBigLargeIconSet = true;
            return this;
        }

        public BigPictureStyle bigPicture(Bitmap bitmap) {
            this.mPicture = bitmap;
            return this;
        }

        public BigPictureStyle setBigContentTitle(CharSequence charSequence) {
            this.mBigContentTitle = Builder.limitCharSequenceLength(charSequence);
            return this;
        }

        public BigPictureStyle setSummaryText(CharSequence charSequence) {
            this.mSummaryText = Builder.limitCharSequenceLength(charSequence);
            this.mSummaryTextSet = true;
            return this;
        }
    }

    public static class BigTextStyle
    extends Style {
        private CharSequence mBigText;

        public BigTextStyle() {
        }

        public BigTextStyle(Builder builder) {
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

        public BigTextStyle bigText(CharSequence charSequence) {
            this.mBigText = Builder.limitCharSequenceLength(charSequence);
            return this;
        }

        public BigTextStyle setBigContentTitle(CharSequence charSequence) {
            this.mBigContentTitle = Builder.limitCharSequenceLength(charSequence);
            return this;
        }

        public BigTextStyle setSummaryText(CharSequence charSequence) {
            this.mSummaryText = Builder.limitCharSequenceLength(charSequence);
            this.mSummaryTextSet = true;
            return this;
        }
    }

    public static class Builder {
        private static final int MAX_CHARSEQUENCE_LENGTH = 5120;
        @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
        public ArrayList<Action> mActions = new ArrayList();
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
        Style mStyle;
        CharSequence mSubText;
        RemoteViews mTickerView;
        long mTimeout;
        boolean mUseChronometer;
        int mVisibility = 0;

        @Deprecated
        public Builder(Context context) {
            this(context, null);
        }

        public Builder(@NonNull Context context, @NonNull String string) {
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

        public Builder addAction(int n, CharSequence charSequence, PendingIntent pendingIntent) {
            this.mActions.add(new Action(n, charSequence, pendingIntent));
            return this;
        }

        public Builder addAction(Action action) {
            this.mActions.add(action);
            return this;
        }

        public Builder addExtras(Bundle bundle) {
            if (bundle != null) {
                if (this.mExtras == null) {
                    this.mExtras = new Bundle(bundle);
                    return this;
                }
                this.mExtras.putAll(bundle);
            }
            return this;
        }

        public Builder addPerson(String string) {
            this.mPeople.add(string);
            return this;
        }

        public Notification build() {
            return new NotificationCompatBuilder(this).build();
        }

        public Builder extend(Extender extender) {
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

        public Builder setAutoCancel(boolean bl) {
            this.setFlag(16, bl);
            return this;
        }

        public Builder setBadgeIconType(int n) {
            this.mBadgeIcon = n;
            return this;
        }

        public Builder setCategory(String string) {
            this.mCategory = string;
            return this;
        }

        public Builder setChannelId(@NonNull String string) {
            this.mChannelId = string;
            return this;
        }

        public Builder setColor(@ColorInt int n) {
            this.mColor = n;
            return this;
        }

        public Builder setColorized(boolean bl) {
            this.mColorized = bl;
            this.mColorizedSet = true;
            return this;
        }

        public Builder setContent(RemoteViews remoteViews) {
            this.mNotification.contentView = remoteViews;
            return this;
        }

        public Builder setContentInfo(CharSequence charSequence) {
            this.mContentInfo = Builder.limitCharSequenceLength(charSequence);
            return this;
        }

        public Builder setContentIntent(PendingIntent pendingIntent) {
            this.mContentIntent = pendingIntent;
            return this;
        }

        public Builder setContentText(CharSequence charSequence) {
            this.mContentText = Builder.limitCharSequenceLength(charSequence);
            return this;
        }

        public Builder setContentTitle(CharSequence charSequence) {
            this.mContentTitle = Builder.limitCharSequenceLength(charSequence);
            return this;
        }

        public Builder setCustomBigContentView(RemoteViews remoteViews) {
            this.mBigContentView = remoteViews;
            return this;
        }

        public Builder setCustomContentView(RemoteViews remoteViews) {
            this.mContentView = remoteViews;
            return this;
        }

        public Builder setCustomHeadsUpContentView(RemoteViews remoteViews) {
            this.mHeadsUpContentView = remoteViews;
            return this;
        }

        public Builder setDefaults(int n) {
            this.mNotification.defaults = n;
            if ((n & 4) != 0) {
                Notification notification = this.mNotification;
                notification.flags |= 1;
            }
            return this;
        }

        public Builder setDeleteIntent(PendingIntent pendingIntent) {
            this.mNotification.deleteIntent = pendingIntent;
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public Builder setFullScreenIntent(PendingIntent pendingIntent, boolean bl) {
            this.mFullScreenIntent = pendingIntent;
            this.setFlag(128, bl);
            return this;
        }

        public Builder setGroup(String string) {
            this.mGroupKey = string;
            return this;
        }

        public Builder setGroupAlertBehavior(int n) {
            this.mGroupAlertBehavior = n;
            return this;
        }

        public Builder setGroupSummary(boolean bl) {
            this.mGroupSummary = bl;
            return this;
        }

        public Builder setLargeIcon(Bitmap bitmap) {
            this.mLargeIcon = bitmap;
            return this;
        }

        public Builder setLights(@ColorInt int n, int n2, int n3) {
            this.mNotification.ledARGB = n;
            this.mNotification.ledOnMS = n2;
            this.mNotification.ledOffMS = n3;
            n = this.mNotification.ledOnMS != 0 && this.mNotification.ledOffMS != 0 ? 1 : 0;
            this.mNotification.flags = n | this.mNotification.flags & -2;
            return this;
        }

        public Builder setLocalOnly(boolean bl) {
            this.mLocalOnly = bl;
            return this;
        }

        public Builder setNumber(int n) {
            this.mNumber = n;
            return this;
        }

        public Builder setOngoing(boolean bl) {
            this.setFlag(2, bl);
            return this;
        }

        public Builder setOnlyAlertOnce(boolean bl) {
            this.setFlag(8, bl);
            return this;
        }

        public Builder setPriority(int n) {
            this.mPriority = n;
            return this;
        }

        public Builder setProgress(int n, int n2, boolean bl) {
            this.mProgressMax = n;
            this.mProgress = n2;
            this.mProgressIndeterminate = bl;
            return this;
        }

        public Builder setPublicVersion(Notification notification) {
            this.mPublicVersion = notification;
            return this;
        }

        public Builder setRemoteInputHistory(CharSequence[] arrcharSequence) {
            this.mRemoteInputHistory = arrcharSequence;
            return this;
        }

        public Builder setShortcutId(String string) {
            this.mShortcutId = string;
            return this;
        }

        public Builder setShowWhen(boolean bl) {
            this.mShowWhen = bl;
            return this;
        }

        public Builder setSmallIcon(int n) {
            this.mNotification.icon = n;
            return this;
        }

        public Builder setSmallIcon(int n, int n2) {
            this.mNotification.icon = n;
            this.mNotification.iconLevel = n2;
            return this;
        }

        public Builder setSortKey(String string) {
            this.mSortKey = string;
            return this;
        }

        public Builder setSound(Uri uri) {
            this.mNotification.sound = uri;
            this.mNotification.audioStreamType = -1;
            if (Build.VERSION.SDK_INT >= 21) {
                this.mNotification.audioAttributes = new AudioAttributes.Builder().setContentType(4).setUsage(5).build();
            }
            return this;
        }

        public Builder setSound(Uri uri, int n) {
            this.mNotification.sound = uri;
            this.mNotification.audioStreamType = n;
            if (Build.VERSION.SDK_INT >= 21) {
                this.mNotification.audioAttributes = new AudioAttributes.Builder().setContentType(4).setLegacyStreamType(n).build();
            }
            return this;
        }

        public Builder setStyle(Style style) {
            if (this.mStyle != style) {
                this.mStyle = style;
                if (this.mStyle != null) {
                    this.mStyle.setBuilder(this);
                }
            }
            return this;
        }

        public Builder setSubText(CharSequence charSequence) {
            this.mSubText = Builder.limitCharSequenceLength(charSequence);
            return this;
        }

        public Builder setTicker(CharSequence charSequence) {
            this.mNotification.tickerText = Builder.limitCharSequenceLength(charSequence);
            return this;
        }

        public Builder setTicker(CharSequence charSequence, RemoteViews remoteViews) {
            this.mNotification.tickerText = Builder.limitCharSequenceLength(charSequence);
            this.mTickerView = remoteViews;
            return this;
        }

        public Builder setTimeoutAfter(long l) {
            this.mTimeout = l;
            return this;
        }

        public Builder setUsesChronometer(boolean bl) {
            this.mUseChronometer = bl;
            return this;
        }

        public Builder setVibrate(long[] arrl) {
            this.mNotification.vibrate = arrl;
            return this;
        }

        public Builder setVisibility(int n) {
            this.mVisibility = n;
            return this;
        }

        public Builder setWhen(long l) {
            this.mNotification.when = l;
            return this;
        }
    }

    public static final class CarExtender
    implements Extender {
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
        private CarExtender$UnreadConversation mUnreadConversation;

        public CarExtender() {
        }

        public CarExtender(Notification object) {
            if (Build.VERSION.SDK_INT < 21) {
                return;
            }
            object = NotificationCompat.getExtras(object) == null ? null : NotificationCompat.getExtras(object).getBundle(EXTRA_CAR_EXTENDER);
            if (object != null) {
                this.mLargeIcon = (Bitmap)object.getParcelable(EXTRA_LARGE_ICON);
                this.mColor = object.getInt(EXTRA_COLOR, 0);
                this.mUnreadConversation = CarExtender.getUnreadConversationFromBundle(object.getBundle(EXTRA_CONVERSATION));
            }
        }

        @RequiresApi(value=21)
        private static Bundle getBundleForUnreadConversation(@NonNull CarExtender$UnreadConversation carExtender$UnreadConversation) {
            Bundle bundle = new Bundle();
            Object object = carExtender$UnreadConversation.getParticipants();
            object = object != null && carExtender$UnreadConversation.getParticipants().length > 1 ? carExtender$UnreadConversation.getParticipants()[0] : null;
            Parcelable[] arrparcelable = new Parcelable[carExtender$UnreadConversation.getMessages().length];
            for (int i = 0; i < arrparcelable.length; ++i) {
                Bundle bundle2 = new Bundle();
                bundle2.putString(KEY_TEXT, carExtender$UnreadConversation.getMessages()[i]);
                bundle2.putString(KEY_AUTHOR, (String)object);
                arrparcelable[i] = bundle2;
            }
            bundle.putParcelableArray(KEY_MESSAGES, arrparcelable);
            object = carExtender$UnreadConversation.getRemoteInput();
            if (object != null) {
                bundle.putParcelable(KEY_REMOTE_INPUT, (Parcelable)new RemoteInput.Builder(object.getResultKey()).setLabel(object.getLabel()).setChoices(object.getChoices()).setAllowFreeFormInput(object.getAllowFreeFormInput()).addExtras(object.getExtras()).build());
            }
            bundle.putParcelable(KEY_ON_REPLY, (Parcelable)carExtender$UnreadConversation.getReplyPendingIntent());
            bundle.putParcelable(KEY_ON_READ, (Parcelable)carExtender$UnreadConversation.getReadPendingIntent());
            bundle.putStringArray(KEY_PARTICIPANTS, carExtender$UnreadConversation.getParticipants());
            bundle.putLong(KEY_TIMESTAMP, carExtender$UnreadConversation.getLatestTimestamp());
            return bundle;
        }

        @RequiresApi(value=21)
        private static CarExtender$UnreadConversation getUnreadConversationFromBundle(@Nullable Bundle bundle) {
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
                return new CarExtender$UnreadConversation(arrstring, remoteInput, pendingIntent2, pendingIntent, arrstring2, bundle.getLong(KEY_TIMESTAMP));
            }
            return null;
        }

        @Override
        public Builder extend(Builder builder) {
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
                bundle.putBundle(EXTRA_CONVERSATION, CarExtender.getBundleForUnreadConversation(this.mUnreadConversation));
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

        public CarExtender$UnreadConversation getUnreadConversation() {
            return this.mUnreadConversation;
        }

        public CarExtender setColor(@ColorInt int n) {
            this.mColor = n;
            return this;
        }

        public CarExtender setLargeIcon(Bitmap bitmap) {
            this.mLargeIcon = bitmap;
            return this;
        }

        public CarExtender setUnreadConversation(CarExtender$UnreadConversation carExtender$UnreadConversation) {
            this.mUnreadConversation = carExtender$UnreadConversation;
            return this;
        }
    }

    public static class CarExtender$UnreadConversation {
        private final long mLatestTimestamp;
        private final String[] mMessages;
        private final String[] mParticipants;
        private final PendingIntent mReadPendingIntent;
        private final RemoteInput mRemoteInput;
        private final PendingIntent mReplyPendingIntent;

        CarExtender$UnreadConversation(String[] arrstring, RemoteInput remoteInput, PendingIntent pendingIntent, PendingIntent pendingIntent2, String[] arrstring2, long l) {
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

    public static class CarExtender$UnreadConversation$Builder {
        private long mLatestTimestamp;
        private final List<String> mMessages = new ArrayList<String>();
        private final String mParticipant;
        private PendingIntent mReadPendingIntent;
        private RemoteInput mRemoteInput;
        private PendingIntent mReplyPendingIntent;

        public CarExtender$UnreadConversation$Builder(String string) {
            this.mParticipant = string;
        }

        public CarExtender$UnreadConversation$Builder addMessage(String string) {
            this.mMessages.add(string);
            return this;
        }

        public CarExtender$UnreadConversation build() {
            String[] arrstring = this.mMessages.toArray(new String[this.mMessages.size()]);
            String string = this.mParticipant;
            RemoteInput remoteInput = this.mRemoteInput;
            PendingIntent pendingIntent = this.mReplyPendingIntent;
            PendingIntent pendingIntent2 = this.mReadPendingIntent;
            long l = this.mLatestTimestamp;
            return new CarExtender$UnreadConversation(arrstring, remoteInput, pendingIntent, pendingIntent2, new String[]{string}, l);
        }

        public CarExtender$UnreadConversation$Builder setLatestTimestamp(long l) {
            this.mLatestTimestamp = l;
            return this;
        }

        public CarExtender$UnreadConversation$Builder setReadPendingIntent(PendingIntent pendingIntent) {
            this.mReadPendingIntent = pendingIntent;
            return this;
        }

        public CarExtender$UnreadConversation$Builder setReplyAction(PendingIntent pendingIntent, RemoteInput remoteInput) {
            this.mRemoteInput = remoteInput;
            this.mReplyPendingIntent = pendingIntent;
            return this;
        }
    }

    public static class DecoratedCustomViewStyle
    extends Style {
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

        private RemoteViews generateActionButton(Action action) {
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

    public static interface Extender {
        public Builder extend(Builder var1);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public static @interface GroupAlertBehavior {
    }

    public static class InboxStyle
    extends Style {
        private ArrayList<CharSequence> mTexts = new ArrayList();

        public InboxStyle() {
        }

        public InboxStyle(Builder builder) {
            this.setBuilder(builder);
        }

        public InboxStyle addLine(CharSequence charSequence) {
            this.mTexts.add(Builder.limitCharSequenceLength(charSequence));
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

        public InboxStyle setBigContentTitle(CharSequence charSequence) {
            this.mBigContentTitle = Builder.limitCharSequenceLength(charSequence);
            return this;
        }

        public InboxStyle setSummaryText(CharSequence charSequence) {
            this.mSummaryText = Builder.limitCharSequenceLength(charSequence);
            this.mSummaryTextSet = true;
            return this;
        }
    }

    public static class MessagingStyle
    extends Style {
        public static final int MAXIMUM_RETAINED_MESSAGES = 25;
        CharSequence mConversationTitle;
        List<MessagingStyle$Message> mMessages = new ArrayList<MessagingStyle$Message>();
        CharSequence mUserDisplayName;

        MessagingStyle() {
        }

        public MessagingStyle(@NonNull CharSequence charSequence) {
            this.mUserDisplayName = charSequence;
        }

        public static MessagingStyle extractMessagingStyleFromNotification(Notification notification) {
            if ((notification = NotificationCompat.getExtras(notification)) != null && !notification.containsKey(NotificationCompat.EXTRA_SELF_DISPLAY_NAME)) {
                return null;
            }
            try {
                MessagingStyle messagingStyle = new MessagingStyle();
                messagingStyle.restoreFromCompatExtras((Bundle)notification);
                return messagingStyle;
            }
            catch (ClassCastException classCastException) {
                return null;
            }
        }

        @Nullable
        private MessagingStyle$Message findLatestIncomingMessage() {
            for (int i = this.mMessages.size() - 1; i >= 0; --i) {
                MessagingStyle$Message messagingStyle$Message = this.mMessages.get(i);
                if (TextUtils.isEmpty((CharSequence)messagingStyle$Message.getSender())) continue;
                return messagingStyle$Message;
            }
            if (!this.mMessages.isEmpty()) {
                return this.mMessages.get(this.mMessages.size() - 1);
            }
            return null;
        }

        private boolean hasMessagesWithoutSender() {
            for (int i = this.mMessages.size() - 1; i >= 0; --i) {
                if (this.mMessages.get(i).getSender() != null) continue;
                return true;
            }
            return false;
        }

        @NonNull
        private TextAppearanceSpan makeFontColorSpan(int n) {
            return new TextAppearanceSpan(null, 0, 0, ColorStateList.valueOf((int)n), null);
        }

        private CharSequence makeMessageLine(MessagingStyle$Message object) {
            CharSequence charSequence;
            BidiFormatter bidiFormatter = BidiFormatter.getInstance();
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            boolean bl = Build.VERSION.SDK_INT >= 21;
            int n = bl ? -16777216 : -1;
            CharSequence charSequence2 = object.getSender();
            int n2 = n;
            if (TextUtils.isEmpty((CharSequence)object.getSender())) {
                charSequence = this.mUserDisplayName == null ? "" : this.mUserDisplayName;
                n2 = n;
                charSequence2 = charSequence;
                if (bl) {
                    n2 = n;
                    charSequence2 = charSequence;
                    if (this.mBuilder.getColor() != 0) {
                        n2 = this.mBuilder.getColor();
                        charSequence2 = charSequence;
                    }
                }
            }
            charSequence = bidiFormatter.unicodeWrap(charSequence2);
            spannableStringBuilder.append(charSequence);
            spannableStringBuilder.setSpan((Object)this.makeFontColorSpan(n2), spannableStringBuilder.length() - charSequence.length(), spannableStringBuilder.length(), 33);
            object = object.getText() == null ? "" : object.getText();
            spannableStringBuilder.append((CharSequence)"  ").append(bidiFormatter.unicodeWrap((CharSequence)object));
            return spannableStringBuilder;
        }

        @Override
        public void addCompatExtras(Bundle bundle) {
            super.addCompatExtras(bundle);
            if (this.mUserDisplayName != null) {
                bundle.putCharSequence(NotificationCompat.EXTRA_SELF_DISPLAY_NAME, this.mUserDisplayName);
            }
            if (this.mConversationTitle != null) {
                bundle.putCharSequence(NotificationCompat.EXTRA_CONVERSATION_TITLE, this.mConversationTitle);
            }
            if (!this.mMessages.isEmpty()) {
                bundle.putParcelableArray(NotificationCompat.EXTRA_MESSAGES, (Parcelable[])MessagingStyle$Message.getBundleArrayForMessages(this.mMessages));
            }
        }

        public MessagingStyle addMessage(MessagingStyle$Message messagingStyle$Message) {
            this.mMessages.add(messagingStyle$Message);
            if (this.mMessages.size() > 25) {
                this.mMessages.remove(0);
            }
            return this;
        }

        public MessagingStyle addMessage(CharSequence charSequence, long l, CharSequence charSequence2) {
            this.mMessages.add(new MessagingStyle$Message(charSequence, l, charSequence2));
            if (this.mMessages.size() > 25) {
                this.mMessages.remove(0);
            }
            return this;
        }

        @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
        @Override
        public void apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            Notification.Builder builder;
            if (Build.VERSION.SDK_INT >= 24) {
                Notification.MessagingStyle messagingStyle = new Notification.MessagingStyle(this.mUserDisplayName).setConversationTitle(this.mConversationTitle);
                for (MessagingStyle$Message messagingStyle$Message : this.mMessages) {
                    Notification.MessagingStyle.Message message = new Notification.MessagingStyle.Message(messagingStyle$Message.getText(), messagingStyle$Message.getTimestamp(), messagingStyle$Message.getSender());
                    if (messagingStyle$Message.getDataMimeType() != null) {
                        message.setData(messagingStyle$Message.getDataMimeType(), messagingStyle$Message.getDataUri());
                    }
                    messagingStyle.addMessage(message);
                }
                messagingStyle.setBuilder(notificationBuilderWithBuilderAccessor.getBuilder());
                return;
            }
            Object object = this.findLatestIncomingMessage();
            if (this.mConversationTitle != null) {
                notificationBuilderWithBuilderAccessor.getBuilder().setContentTitle(this.mConversationTitle);
            } else if (object != null) {
                notificationBuilderWithBuilderAccessor.getBuilder().setContentTitle(object.getSender());
            }
            if (object != null) {
                builder = notificationBuilderWithBuilderAccessor.getBuilder();
                object = this.mConversationTitle != null ? this.makeMessageLine((MessagingStyle$Message)object) : object.getText();
                builder.setContentText((CharSequence)object);
            }
            if (Build.VERSION.SDK_INT >= 16) {
                builder = new SpannableStringBuilder();
                boolean bl = this.mConversationTitle != null || this.hasMessagesWithoutSender();
                for (int i = this.mMessages.size() - 1; i >= 0; --i) {
                    object = this.mMessages.get(i);
                    object = bl ? this.makeMessageLine((MessagingStyle$Message)object) : object.getText();
                    if (i != this.mMessages.size() - 1) {
                        builder.insert(0, (CharSequence)"\n");
                    }
                    builder.insert(0, (CharSequence)object);
                }
                new Notification.BigTextStyle(notificationBuilderWithBuilderAccessor.getBuilder()).setBigContentTitle(null).bigText((CharSequence)builder);
            }
        }

        public CharSequence getConversationTitle() {
            return this.mConversationTitle;
        }

        public List<MessagingStyle$Message> getMessages() {
            return this.mMessages;
        }

        public CharSequence getUserDisplayName() {
            return this.mUserDisplayName;
        }

        @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
        @Override
        protected void restoreFromCompatExtras(Bundle arrparcelable) {
            this.mMessages.clear();
            this.mUserDisplayName = arrparcelable.getString(NotificationCompat.EXTRA_SELF_DISPLAY_NAME);
            this.mConversationTitle = arrparcelable.getString(NotificationCompat.EXTRA_CONVERSATION_TITLE);
            arrparcelable = arrparcelable.getParcelableArray(NotificationCompat.EXTRA_MESSAGES);
            if (arrparcelable != null) {
                this.mMessages = MessagingStyle$Message.getMessagesFromBundleArray(arrparcelable);
            }
        }

        public MessagingStyle setConversationTitle(CharSequence charSequence) {
            this.mConversationTitle = charSequence;
            return this;
        }
    }

    public static final class MessagingStyle$Message {
        static final String KEY_DATA_MIME_TYPE = "type";
        static final String KEY_DATA_URI = "uri";
        static final String KEY_EXTRAS_BUNDLE = "extras";
        static final String KEY_SENDER = "sender";
        static final String KEY_TEXT = "text";
        static final String KEY_TIMESTAMP = "time";
        private String mDataMimeType;
        private Uri mDataUri;
        private Bundle mExtras = new Bundle();
        private final CharSequence mSender;
        private final CharSequence mText;
        private final long mTimestamp;

        public MessagingStyle$Message(CharSequence charSequence, long l, CharSequence charSequence2) {
            this.mText = charSequence;
            this.mTimestamp = l;
            this.mSender = charSequence2;
        }

        static Bundle[] getBundleArrayForMessages(List<MessagingStyle$Message> list) {
            Bundle[] arrbundle = new Bundle[list.size()];
            int n = list.size();
            for (int i = 0; i < n; ++i) {
                arrbundle[i] = list.get(i).toBundle();
            }
            return arrbundle;
        }

        static MessagingStyle$Message getMessageFromBundle(Bundle bundle) {
            block5 : {
                block6 : {
                    try {
                        if (!bundle.containsKey(KEY_TEXT)) break block5;
                        if (bundle.containsKey(KEY_TIMESTAMP)) break block6;
                        return null;
                    }
                    catch (ClassCastException classCastException) {
                        return null;
                    }
                }
                MessagingStyle$Message messagingStyle$Message = new MessagingStyle$Message(bundle.getCharSequence(KEY_TEXT), bundle.getLong(KEY_TIMESTAMP), bundle.getCharSequence(KEY_SENDER));
                if (bundle.containsKey(KEY_DATA_MIME_TYPE) && bundle.containsKey(KEY_DATA_URI)) {
                    messagingStyle$Message.setData(bundle.getString(KEY_DATA_MIME_TYPE), (Uri)bundle.getParcelable(KEY_DATA_URI));
                }
                if (bundle.containsKey(KEY_EXTRAS_BUNDLE)) {
                    messagingStyle$Message.getExtras().putAll(bundle.getBundle(KEY_EXTRAS_BUNDLE));
                }
                return messagingStyle$Message;
            }
            return null;
        }

        static List<MessagingStyle$Message> getMessagesFromBundleArray(Parcelable[] arrparcelable) {
            ArrayList<MessagingStyle$Message> arrayList = new ArrayList<MessagingStyle$Message>(arrparcelable.length);
            for (int i = 0; i < arrparcelable.length; ++i) {
                MessagingStyle$Message messagingStyle$Message;
                if (!(arrparcelable[i] instanceof Bundle) || (messagingStyle$Message = MessagingStyle$Message.getMessageFromBundle((Bundle)arrparcelable[i])) == null) continue;
                arrayList.add(messagingStyle$Message);
            }
            return arrayList;
        }

        private Bundle toBundle() {
            Bundle bundle = new Bundle();
            if (this.mText != null) {
                bundle.putCharSequence(KEY_TEXT, this.mText);
            }
            bundle.putLong(KEY_TIMESTAMP, this.mTimestamp);
            if (this.mSender != null) {
                bundle.putCharSequence(KEY_SENDER, this.mSender);
            }
            if (this.mDataMimeType != null) {
                bundle.putString(KEY_DATA_MIME_TYPE, this.mDataMimeType);
            }
            if (this.mDataUri != null) {
                bundle.putParcelable(KEY_DATA_URI, (Parcelable)this.mDataUri);
            }
            if (this.mExtras != null) {
                bundle.putBundle(KEY_EXTRAS_BUNDLE, this.mExtras);
            }
            return bundle;
        }

        public String getDataMimeType() {
            return this.mDataMimeType;
        }

        public Uri getDataUri() {
            return this.mDataUri;
        }

        public Bundle getExtras() {
            return this.mExtras;
        }

        public CharSequence getSender() {
            return this.mSender;
        }

        public CharSequence getText() {
            return this.mText;
        }

        public long getTimestamp() {
            return this.mTimestamp;
        }

        public MessagingStyle$Message setData(String string, Uri uri) {
            this.mDataMimeType = string;
            this.mDataUri = uri;
            return this;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public static @interface NotificationVisibility {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public static @interface StreamType {
    }

    public static abstract class Style {
        CharSequence mBigContentTitle;
        @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
        protected Builder mBuilder;
        CharSequence mSummaryText;
        boolean mSummaryTextSet = false;

        private int calculateTopPadding() {
            Resources resources = this.mBuilder.mContext.getResources();
            int n = resources.getDimensionPixelSize(R.dimen.notification_top_pad);
            int n2 = resources.getDimensionPixelSize(R.dimen.notification_top_pad_large_text);
            float f = (Style.constrain(resources.getConfiguration().fontScale, 1.0f, 1.3f) - 1.0f) / 0.29999995f;
            return Math.round((1.0f - f) * (float)n + f * (float)n2);
        }

        private static float constrain(float f, float f2, float f3) {
            if (f < f2) {
                return f2;
            }
            f2 = f;
            if (f > f3) {
                f2 = f3;
            }
            return f2;
        }

        private Bitmap createColoredBitmap(int n, int n2, int n3) {
            Drawable drawable = this.mBuilder.mContext.getResources().getDrawable(n);
            n = n3 == 0 ? drawable.getIntrinsicWidth() : n3;
            int n4 = n3;
            if (n3 == 0) {
                n4 = drawable.getIntrinsicHeight();
            }
            Bitmap bitmap = Bitmap.createBitmap((int)n, (int)n4, (Bitmap.Config)Bitmap.Config.ARGB_8888);
            drawable.setBounds(0, 0, n, n4);
            if (n2 != 0) {
                drawable.mutate().setColorFilter((ColorFilter)new PorterDuffColorFilter(n2, PorterDuff.Mode.SRC_IN));
            }
            drawable.draw(new Canvas(bitmap));
            return bitmap;
        }

        private Bitmap createIconWithBackground(int n, int n2, int n3, int n4) {
            int n5 = R.drawable.notification_icon_background;
            int n6 = n4;
            if (n4 == 0) {
                n6 = 0;
            }
            Bitmap bitmap = this.createColoredBitmap(n5, n6, n2);
            Canvas canvas = new Canvas(bitmap);
            Drawable drawable2 = this.mBuilder.mContext.getResources().getDrawable(n).mutate();
            drawable2.setFilterBitmap(true);
            n = (n2 - n3) / 2;
            n2 = n3 + n;
            drawable2.setBounds(n, n, n2, n2);
            drawable2.setColorFilter((ColorFilter)new PorterDuffColorFilter(-1, PorterDuff.Mode.SRC_ATOP));
            drawable2.draw(canvas);
            return bitmap;
        }

        private void hideNormalContent(RemoteViews remoteViews) {
            remoteViews.setViewVisibility(R.id.title, 8);
            remoteViews.setViewVisibility(R.id.text2, 8);
            remoteViews.setViewVisibility(R.id.text, 8);
        }

        @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
        public void addCompatExtras(Bundle bundle) {
        }

        @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
        public void apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
        public RemoteViews applyStandardTemplate(boolean var1_1, int var2_2, boolean var3_3) {
            block27 : {
                var9_4 = this.mBuilder.mContext.getResources();
                var10_5 = new RemoteViews(this.mBuilder.mContext.getPackageName(), var2_2);
                var2_2 = this.mBuilder.getPriority();
                var8_6 = 1;
                var7_7 = 0;
                var2_2 = var2_2 < -1 ? 1 : 0;
                if (Build.VERSION.SDK_INT >= 16 && Build.VERSION.SDK_INT < 21) {
                    if (var2_2 != 0) {
                        var10_5.setInt(R.id.notification_background, "setBackgroundResource", R.drawable.notification_bg_low);
                        var10_5.setInt(R.id.icon, "setBackgroundResource", R.drawable.notification_template_icon_low_bg);
                    } else {
                        var10_5.setInt(R.id.notification_background, "setBackgroundResource", R.drawable.notification_bg);
                        var10_5.setInt(R.id.icon, "setBackgroundResource", R.drawable.notification_template_icon_bg);
                    }
                }
                if (this.mBuilder.mLargeIcon != null) {
                    if (Build.VERSION.SDK_INT >= 16) {
                        var10_5.setViewVisibility(R.id.icon, 0);
                        var10_5.setImageViewBitmap(R.id.icon, this.mBuilder.mLargeIcon);
                    } else {
                        var10_5.setViewVisibility(R.id.icon, 8);
                    }
                    if (var1_1 && this.mBuilder.mNotification.icon != 0) {
                        var2_2 = var9_4.getDimensionPixelSize(R.dimen.notification_right_icon_size);
                        var5_8 = var9_4.getDimensionPixelSize(R.dimen.notification_small_icon_background_padding);
                        if (Build.VERSION.SDK_INT >= 21) {
                            var11_9 = this.createIconWithBackground(this.mBuilder.mNotification.icon, var2_2, var2_2 - var5_8 * 2, this.mBuilder.getColor());
                            var10_5.setImageViewBitmap(R.id.right_icon, (Bitmap)var11_9);
                        } else {
                            var10_5.setImageViewBitmap(R.id.right_icon, this.createColoredBitmap(this.mBuilder.mNotification.icon, -1));
                        }
                        var10_5.setViewVisibility(R.id.right_icon, 0);
                    }
                } else if (var1_1 && this.mBuilder.mNotification.icon != 0) {
                    var10_5.setViewVisibility(R.id.icon, 0);
                    if (Build.VERSION.SDK_INT >= 21) {
                        var2_2 = var9_4.getDimensionPixelSize(R.dimen.notification_large_icon_width);
                        var5_8 = var9_4.getDimensionPixelSize(R.dimen.notification_big_circle_margin);
                        var6_10 = var9_4.getDimensionPixelSize(R.dimen.notification_small_icon_size_as_large);
                        var11_9 = this.createIconWithBackground(this.mBuilder.mNotification.icon, var2_2 - var5_8, var6_10, this.mBuilder.getColor());
                        var10_5.setImageViewBitmap(R.id.icon, (Bitmap)var11_9);
                    } else {
                        var10_5.setImageViewBitmap(R.id.icon, this.createColoredBitmap(this.mBuilder.mNotification.icon, -1));
                    }
                }
                if (this.mBuilder.mContentTitle != null) {
                    var10_5.setTextViewText(R.id.title, this.mBuilder.mContentTitle);
                }
                if (this.mBuilder.mContentText != null) {
                    var10_5.setTextViewText(R.id.text, this.mBuilder.mContentText);
                    var2_2 = 1;
                } else {
                    var2_2 = 0;
                }
                var5_8 = Build.VERSION.SDK_INT < 21 && this.mBuilder.mLargeIcon != null ? 1 : 0;
                if (this.mBuilder.mContentInfo == null) break block27;
                var10_5.setTextViewText(R.id.info, this.mBuilder.mContentInfo);
                var10_5.setViewVisibility(R.id.info, 0);
                ** GOTO lbl61
            }
            if (this.mBuilder.mNumber > 0) {
                var2_2 = var9_4.getInteger(R.integer.status_bar_notification_info_maxnum);
                if (this.mBuilder.mNumber > var2_2) {
                    var10_5.setTextViewText(R.id.info, (CharSequence)var9_4.getString(R.string.status_bar_notification_info_overflow));
                } else {
                    var11_9 = NumberFormat.getIntegerInstance();
                    var10_5.setTextViewText(R.id.info, (CharSequence)var11_9.format(this.mBuilder.mNumber));
                }
                var10_5.setViewVisibility(R.id.info, 0);
lbl61: // 2 sources:
                var5_8 = var2_2 = 1;
            } else {
                var10_5.setViewVisibility(R.id.info, 8);
                var6_10 = var5_8;
                var5_8 = var2_2;
                var2_2 = var6_10;
            }
            if (this.mBuilder.mSubText == null || Build.VERSION.SDK_INT < 16) ** GOTO lbl75
            var10_5.setTextViewText(R.id.text, this.mBuilder.mSubText);
            if (this.mBuilder.mContentText != null) {
                var10_5.setTextViewText(R.id.text2, this.mBuilder.mContentText);
                var10_5.setViewVisibility(R.id.text2, 0);
                var6_10 = 1;
            } else {
                var10_5.setViewVisibility(R.id.text2, 8);
lbl75: // 2 sources:
                var6_10 = 0;
            }
            if (var6_10 != 0 && Build.VERSION.SDK_INT >= 16) {
                if (var3_3) {
                    var4_11 = var9_4.getDimensionPixelSize(R.dimen.notification_subtext_size);
                    var10_5.setTextViewTextSize(R.id.text, 0, var4_11);
                }
                var10_5.setViewPadding(R.id.line1, 0, 0, 0, 0);
            }
            if (this.mBuilder.getWhenIfShowing() != 0L) {
                if (this.mBuilder.mUseChronometer && Build.VERSION.SDK_INT >= 16) {
                    var10_5.setViewVisibility(R.id.chronometer, 0);
                    var10_5.setLong(R.id.chronometer, "setBase", this.mBuilder.getWhenIfShowing() + (SystemClock.elapsedRealtime() - System.currentTimeMillis()));
                    var10_5.setBoolean(R.id.chronometer, "setStarted", true);
                    var2_2 = var8_6;
                } else {
                    var10_5.setViewVisibility(R.id.time, 0);
                    var10_5.setLong(R.id.time, "setTime", this.mBuilder.getWhenIfShowing());
                    var2_2 = var8_6;
                }
            }
            var6_10 = R.id.right_side;
            var2_2 = var2_2 != 0 ? 0 : 8;
            var10_5.setViewVisibility(var6_10, var2_2);
            var6_10 = R.id.line3;
            var2_2 = var5_8 != 0 ? var7_7 : 8;
            var10_5.setViewVisibility(var6_10, var2_2);
            return var10_5;
        }

        public Notification build() {
            if (this.mBuilder != null) {
                return this.mBuilder.build();
            }
            return null;
        }

        @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
        public void buildIntoRemoteViews(RemoteViews remoteViews, RemoteViews remoteViews2) {
            this.hideNormalContent(remoteViews);
            remoteViews.removeAllViews(R.id.notification_main_column);
            remoteViews.addView(R.id.notification_main_column, remoteViews2.clone());
            remoteViews.setViewVisibility(R.id.notification_main_column, 0);
            if (Build.VERSION.SDK_INT >= 21) {
                remoteViews.setViewPadding(R.id.notification_main_column_container, 0, this.calculateTopPadding(), 0, 0);
            }
        }

        @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
        public Bitmap createColoredBitmap(int n, int n2) {
            return this.createColoredBitmap(n, n2, 0);
        }

        @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
        public RemoteViews makeBigContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            return null;
        }

        @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
        public RemoteViews makeContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            return null;
        }

        @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
        public RemoteViews makeHeadsUpContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            return null;
        }

        @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
        protected void restoreFromCompatExtras(Bundle bundle) {
        }

        public void setBuilder(Builder builder) {
            if (this.mBuilder != builder) {
                this.mBuilder = builder;
                if (this.mBuilder != null) {
                    this.mBuilder.setStyle(this);
                }
            }
        }
    }

    public static final class WearableExtender
    implements Extender {
        private static final int DEFAULT_CONTENT_ICON_GRAVITY = 8388613;
        private static final int DEFAULT_FLAGS = 1;
        private static final int DEFAULT_GRAVITY = 80;
        private static final String EXTRA_WEARABLE_EXTENSIONS = "android.wearable.EXTENSIONS";
        private static final int FLAG_BIG_PICTURE_AMBIENT = 32;
        private static final int FLAG_CONTENT_INTENT_AVAILABLE_OFFLINE = 1;
        private static final int FLAG_HINT_AVOID_BACKGROUND_CLIPPING = 16;
        private static final int FLAG_HINT_CONTENT_INTENT_LAUNCHES_ACTIVITY = 64;
        private static final int FLAG_HINT_HIDE_ICON = 2;
        private static final int FLAG_HINT_SHOW_BACKGROUND_ONLY = 4;
        private static final int FLAG_START_SCROLL_BOTTOM = 8;
        private static final String KEY_ACTIONS = "actions";
        private static final String KEY_BACKGROUND = "background";
        private static final String KEY_BRIDGE_TAG = "bridgeTag";
        private static final String KEY_CONTENT_ACTION_INDEX = "contentActionIndex";
        private static final String KEY_CONTENT_ICON = "contentIcon";
        private static final String KEY_CONTENT_ICON_GRAVITY = "contentIconGravity";
        private static final String KEY_CUSTOM_CONTENT_HEIGHT = "customContentHeight";
        private static final String KEY_CUSTOM_SIZE_PRESET = "customSizePreset";
        private static final String KEY_DISMISSAL_ID = "dismissalId";
        private static final String KEY_DISPLAY_INTENT = "displayIntent";
        private static final String KEY_FLAGS = "flags";
        private static final String KEY_GRAVITY = "gravity";
        private static final String KEY_HINT_SCREEN_TIMEOUT = "hintScreenTimeout";
        private static final String KEY_PAGES = "pages";
        public static final int SCREEN_TIMEOUT_LONG = -1;
        public static final int SCREEN_TIMEOUT_SHORT = 0;
        public static final int SIZE_DEFAULT = 0;
        public static final int SIZE_FULL_SCREEN = 5;
        public static final int SIZE_LARGE = 4;
        public static final int SIZE_MEDIUM = 3;
        public static final int SIZE_SMALL = 2;
        public static final int SIZE_XSMALL = 1;
        public static final int UNSET_ACTION_INDEX = -1;
        private ArrayList<Action> mActions = new ArrayList();
        private Bitmap mBackground;
        private String mBridgeTag;
        private int mContentActionIndex = -1;
        private int mContentIcon;
        private int mContentIconGravity = 8388613;
        private int mCustomContentHeight;
        private int mCustomSizePreset = 0;
        private String mDismissalId;
        private PendingIntent mDisplayIntent;
        private int mFlags = 1;
        private int mGravity = 80;
        private int mHintScreenTimeout;
        private ArrayList<Notification> mPages = new ArrayList();

        public WearableExtender() {
        }

        public WearableExtender(Notification object) {
            object = NotificationCompat.getExtras(object);
            object = object != null ? object.getBundle(EXTRA_WEARABLE_EXTENSIONS) : null;
            if (object != null) {
                Notification[] arrnotification = object.getParcelableArrayList(KEY_ACTIONS);
                if (Build.VERSION.SDK_INT >= 16 && arrnotification != null) {
                    Action[] arraction = new Action[arrnotification.size()];
                    for (int i = 0; i < arraction.length; ++i) {
                        if (Build.VERSION.SDK_INT >= 20) {
                            arraction[i] = NotificationCompat.getActionCompatFromAction((Notification.Action)arrnotification.get(i));
                            continue;
                        }
                        if (Build.VERSION.SDK_INT < 16) continue;
                        arraction[i] = NotificationCompatJellybean.getActionFromBundle((Bundle)arrnotification.get(i));
                    }
                    Collections.addAll(this.mActions, arraction);
                }
                this.mFlags = object.getInt(KEY_FLAGS, 1);
                this.mDisplayIntent = (PendingIntent)object.getParcelable(KEY_DISPLAY_INTENT);
                arrnotification = NotificationCompat.getNotificationArrayFromBundle((Bundle)object, KEY_PAGES);
                if (arrnotification != null) {
                    Collections.addAll(this.mPages, arrnotification);
                }
                this.mBackground = (Bitmap)object.getParcelable(KEY_BACKGROUND);
                this.mContentIcon = object.getInt(KEY_CONTENT_ICON);
                this.mContentIconGravity = object.getInt(KEY_CONTENT_ICON_GRAVITY, 8388613);
                this.mContentActionIndex = object.getInt(KEY_CONTENT_ACTION_INDEX, -1);
                this.mCustomSizePreset = object.getInt(KEY_CUSTOM_SIZE_PRESET, 0);
                this.mCustomContentHeight = object.getInt(KEY_CUSTOM_CONTENT_HEIGHT);
                this.mGravity = object.getInt(KEY_GRAVITY, 80);
                this.mHintScreenTimeout = object.getInt(KEY_HINT_SCREEN_TIMEOUT);
                this.mDismissalId = object.getString(KEY_DISMISSAL_ID);
                this.mBridgeTag = object.getString(KEY_BRIDGE_TAG);
            }
        }

        @RequiresApi(value=20)
        private static Notification.Action getActionFromActionCompat(Action arrremoteInput) {
            Notification.Action.Builder builder = new Notification.Action.Builder(arrremoteInput.getIcon(), arrremoteInput.getTitle(), arrremoteInput.getActionIntent());
            Bundle bundle = arrremoteInput.getExtras() != null ? new Bundle(arrremoteInput.getExtras()) : new Bundle();
            bundle.putBoolean("android.support.allowGeneratedReplies", arrremoteInput.getAllowGeneratedReplies());
            if (Build.VERSION.SDK_INT >= 24) {
                builder.setAllowGeneratedReplies(arrremoteInput.getAllowGeneratedReplies());
            }
            builder.addExtras(bundle);
            arrremoteInput = arrremoteInput.getRemoteInputs();
            if (arrremoteInput != null) {
                arrremoteInput = RemoteInput.fromCompat(arrremoteInput);
                int n = arrremoteInput.length;
                for (int i = 0; i < n; ++i) {
                    builder.addRemoteInput((android.app.RemoteInput)arrremoteInput[i]);
                }
            }
            return builder.build();
        }

        private void setFlag(int n, boolean bl) {
            if (bl) {
                this.mFlags = n | this.mFlags;
                return;
            }
            this.mFlags = ~ n & this.mFlags;
        }

        public WearableExtender addAction(Action action) {
            this.mActions.add(action);
            return this;
        }

        public WearableExtender addActions(List<Action> list) {
            this.mActions.addAll(list);
            return this;
        }

        public WearableExtender addPage(Notification notification) {
            this.mPages.add(notification);
            return this;
        }

        public WearableExtender addPages(List<Notification> list) {
            this.mPages.addAll(list);
            return this;
        }

        public WearableExtender clearActions() {
            this.mActions.clear();
            return this;
        }

        public WearableExtender clearPages() {
            this.mPages.clear();
            return this;
        }

        public WearableExtender clone() {
            WearableExtender wearableExtender = new WearableExtender();
            wearableExtender.mActions = new ArrayList<Action>(this.mActions);
            wearableExtender.mFlags = this.mFlags;
            wearableExtender.mDisplayIntent = this.mDisplayIntent;
            wearableExtender.mPages = new ArrayList<Notification>(this.mPages);
            wearableExtender.mBackground = this.mBackground;
            wearableExtender.mContentIcon = this.mContentIcon;
            wearableExtender.mContentIconGravity = this.mContentIconGravity;
            wearableExtender.mContentActionIndex = this.mContentActionIndex;
            wearableExtender.mCustomSizePreset = this.mCustomSizePreset;
            wearableExtender.mCustomContentHeight = this.mCustomContentHeight;
            wearableExtender.mGravity = this.mGravity;
            wearableExtender.mHintScreenTimeout = this.mHintScreenTimeout;
            wearableExtender.mDismissalId = this.mDismissalId;
            wearableExtender.mBridgeTag = this.mBridgeTag;
            return wearableExtender;
        }

        @Override
        public Builder extend(Builder builder) {
            Bundle bundle = new Bundle();
            if (!this.mActions.isEmpty()) {
                if (Build.VERSION.SDK_INT >= 16) {
                    ArrayList<Object> arrayList = new ArrayList<Object>(this.mActions.size());
                    for (Action action : this.mActions) {
                        if (Build.VERSION.SDK_INT >= 20) {
                            arrayList.add((Object)WearableExtender.getActionFromActionCompat(action));
                            continue;
                        }
                        if (Build.VERSION.SDK_INT < 16) continue;
                        arrayList.add((Object)NotificationCompatJellybean.getBundleForAction(action));
                    }
                    bundle.putParcelableArrayList(KEY_ACTIONS, arrayList);
                } else {
                    bundle.putParcelableArrayList(KEY_ACTIONS, null);
                }
            }
            if (this.mFlags != 1) {
                bundle.putInt(KEY_FLAGS, this.mFlags);
            }
            if (this.mDisplayIntent != null) {
                bundle.putParcelable(KEY_DISPLAY_INTENT, (Parcelable)this.mDisplayIntent);
            }
            if (!this.mPages.isEmpty()) {
                bundle.putParcelableArray(KEY_PAGES, (Parcelable[])this.mPages.toArray((T[])new Notification[this.mPages.size()]));
            }
            if (this.mBackground != null) {
                bundle.putParcelable(KEY_BACKGROUND, (Parcelable)this.mBackground);
            }
            if (this.mContentIcon != 0) {
                bundle.putInt(KEY_CONTENT_ICON, this.mContentIcon);
            }
            if (this.mContentIconGravity != 8388613) {
                bundle.putInt(KEY_CONTENT_ICON_GRAVITY, this.mContentIconGravity);
            }
            if (this.mContentActionIndex != -1) {
                bundle.putInt(KEY_CONTENT_ACTION_INDEX, this.mContentActionIndex);
            }
            if (this.mCustomSizePreset != 0) {
                bundle.putInt(KEY_CUSTOM_SIZE_PRESET, this.mCustomSizePreset);
            }
            if (this.mCustomContentHeight != 0) {
                bundle.putInt(KEY_CUSTOM_CONTENT_HEIGHT, this.mCustomContentHeight);
            }
            if (this.mGravity != 80) {
                bundle.putInt(KEY_GRAVITY, this.mGravity);
            }
            if (this.mHintScreenTimeout != 0) {
                bundle.putInt(KEY_HINT_SCREEN_TIMEOUT, this.mHintScreenTimeout);
            }
            if (this.mDismissalId != null) {
                bundle.putString(KEY_DISMISSAL_ID, this.mDismissalId);
            }
            if (this.mBridgeTag != null) {
                bundle.putString(KEY_BRIDGE_TAG, this.mBridgeTag);
            }
            builder.getExtras().putBundle(EXTRA_WEARABLE_EXTENSIONS, bundle);
            return builder;
        }

        public List<Action> getActions() {
            return this.mActions;
        }

        public Bitmap getBackground() {
            return this.mBackground;
        }

        public String getBridgeTag() {
            return this.mBridgeTag;
        }

        public int getContentAction() {
            return this.mContentActionIndex;
        }

        public int getContentIcon() {
            return this.mContentIcon;
        }

        public int getContentIconGravity() {
            return this.mContentIconGravity;
        }

        public boolean getContentIntentAvailableOffline() {
            if ((this.mFlags & 1) != 0) {
                return true;
            }
            return false;
        }

        public int getCustomContentHeight() {
            return this.mCustomContentHeight;
        }

        public int getCustomSizePreset() {
            return this.mCustomSizePreset;
        }

        public String getDismissalId() {
            return this.mDismissalId;
        }

        public PendingIntent getDisplayIntent() {
            return this.mDisplayIntent;
        }

        public int getGravity() {
            return this.mGravity;
        }

        public boolean getHintAmbientBigPicture() {
            if ((this.mFlags & 32) != 0) {
                return true;
            }
            return false;
        }

        public boolean getHintAvoidBackgroundClipping() {
            if ((this.mFlags & 16) != 0) {
                return true;
            }
            return false;
        }

        public boolean getHintContentIntentLaunchesActivity() {
            if ((this.mFlags & 64) != 0) {
                return true;
            }
            return false;
        }

        public boolean getHintHideIcon() {
            if ((this.mFlags & 2) != 0) {
                return true;
            }
            return false;
        }

        public int getHintScreenTimeout() {
            return this.mHintScreenTimeout;
        }

        public boolean getHintShowBackgroundOnly() {
            if ((this.mFlags & 4) != 0) {
                return true;
            }
            return false;
        }

        public List<Notification> getPages() {
            return this.mPages;
        }

        public boolean getStartScrollBottom() {
            if ((this.mFlags & 8) != 0) {
                return true;
            }
            return false;
        }

        public WearableExtender setBackground(Bitmap bitmap) {
            this.mBackground = bitmap;
            return this;
        }

        public WearableExtender setBridgeTag(String string2) {
            this.mBridgeTag = string2;
            return this;
        }

        public WearableExtender setContentAction(int n) {
            this.mContentActionIndex = n;
            return this;
        }

        public WearableExtender setContentIcon(int n) {
            this.mContentIcon = n;
            return this;
        }

        public WearableExtender setContentIconGravity(int n) {
            this.mContentIconGravity = n;
            return this;
        }

        public WearableExtender setContentIntentAvailableOffline(boolean bl) {
            this.setFlag(1, bl);
            return this;
        }

        public WearableExtender setCustomContentHeight(int n) {
            this.mCustomContentHeight = n;
            return this;
        }

        public WearableExtender setCustomSizePreset(int n) {
            this.mCustomSizePreset = n;
            return this;
        }

        public WearableExtender setDismissalId(String string2) {
            this.mDismissalId = string2;
            return this;
        }

        public WearableExtender setDisplayIntent(PendingIntent pendingIntent) {
            this.mDisplayIntent = pendingIntent;
            return this;
        }

        public WearableExtender setGravity(int n) {
            this.mGravity = n;
            return this;
        }

        public WearableExtender setHintAmbientBigPicture(boolean bl) {
            this.setFlag(32, bl);
            return this;
        }

        public WearableExtender setHintAvoidBackgroundClipping(boolean bl) {
            this.setFlag(16, bl);
            return this;
        }

        public WearableExtender setHintContentIntentLaunchesActivity(boolean bl) {
            this.setFlag(64, bl);
            return this;
        }

        public WearableExtender setHintHideIcon(boolean bl) {
            this.setFlag(2, bl);
            return this;
        }

        public WearableExtender setHintScreenTimeout(int n) {
            this.mHintScreenTimeout = n;
            return this;
        }

        public WearableExtender setHintShowBackgroundOnly(boolean bl) {
            this.setFlag(4, bl);
            return this;
        }

        public WearableExtender setStartScrollBottom(boolean bl) {
            this.setFlag(8, bl);
            return this;
        }
    }

}
