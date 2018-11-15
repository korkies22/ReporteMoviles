/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Notification
 *  android.app.Notification$Action
 *  android.app.Notification$Action$Builder
 *  android.app.PendingIntent
 *  android.app.RemoteInput
 *  android.graphics.Bitmap
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Parcelable
 */
package android.support.v4.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompatJellybean;
import android.support.v4.app.RemoteInput;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public static final class NotificationCompat.WearableExtender
implements NotificationCompat.Extender {
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
    private ArrayList<NotificationCompat.Action> mActions = new ArrayList();
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

    public NotificationCompat.WearableExtender() {
    }

    public NotificationCompat.WearableExtender(Notification object) {
        object = NotificationCompat.getExtras(object);
        object = object != null ? object.getBundle(EXTRA_WEARABLE_EXTENSIONS) : null;
        if (object != null) {
            Notification[] arrnotification = object.getParcelableArrayList(KEY_ACTIONS);
            if (Build.VERSION.SDK_INT >= 16 && arrnotification != null) {
                NotificationCompat.Action[] arraction = new NotificationCompat.Action[arrnotification.size()];
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
    private static Notification.Action getActionFromActionCompat(NotificationCompat.Action arrremoteInput) {
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

    public NotificationCompat.WearableExtender addAction(NotificationCompat.Action action) {
        this.mActions.add(action);
        return this;
    }

    public NotificationCompat.WearableExtender addActions(List<NotificationCompat.Action> list) {
        this.mActions.addAll(list);
        return this;
    }

    public NotificationCompat.WearableExtender addPage(Notification notification) {
        this.mPages.add(notification);
        return this;
    }

    public NotificationCompat.WearableExtender addPages(List<Notification> list) {
        this.mPages.addAll(list);
        return this;
    }

    public NotificationCompat.WearableExtender clearActions() {
        this.mActions.clear();
        return this;
    }

    public NotificationCompat.WearableExtender clearPages() {
        this.mPages.clear();
        return this;
    }

    public NotificationCompat.WearableExtender clone() {
        NotificationCompat.WearableExtender wearableExtender = new NotificationCompat.WearableExtender();
        wearableExtender.mActions = new ArrayList<NotificationCompat.Action>(this.mActions);
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
    public NotificationCompat.Builder extend(NotificationCompat.Builder builder) {
        Bundle bundle = new Bundle();
        if (!this.mActions.isEmpty()) {
            if (Build.VERSION.SDK_INT >= 16) {
                ArrayList<Object> arrayList = new ArrayList<Object>(this.mActions.size());
                for (NotificationCompat.Action action : this.mActions) {
                    if (Build.VERSION.SDK_INT >= 20) {
                        arrayList.add((Object)NotificationCompat.WearableExtender.getActionFromActionCompat(action));
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

    public List<NotificationCompat.Action> getActions() {
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

    public NotificationCompat.WearableExtender setBackground(Bitmap bitmap) {
        this.mBackground = bitmap;
        return this;
    }

    public NotificationCompat.WearableExtender setBridgeTag(String string) {
        this.mBridgeTag = string;
        return this;
    }

    public NotificationCompat.WearableExtender setContentAction(int n) {
        this.mContentActionIndex = n;
        return this;
    }

    public NotificationCompat.WearableExtender setContentIcon(int n) {
        this.mContentIcon = n;
        return this;
    }

    public NotificationCompat.WearableExtender setContentIconGravity(int n) {
        this.mContentIconGravity = n;
        return this;
    }

    public NotificationCompat.WearableExtender setContentIntentAvailableOffline(boolean bl) {
        this.setFlag(1, bl);
        return this;
    }

    public NotificationCompat.WearableExtender setCustomContentHeight(int n) {
        this.mCustomContentHeight = n;
        return this;
    }

    public NotificationCompat.WearableExtender setCustomSizePreset(int n) {
        this.mCustomSizePreset = n;
        return this;
    }

    public NotificationCompat.WearableExtender setDismissalId(String string) {
        this.mDismissalId = string;
        return this;
    }

    public NotificationCompat.WearableExtender setDisplayIntent(PendingIntent pendingIntent) {
        this.mDisplayIntent = pendingIntent;
        return this;
    }

    public NotificationCompat.WearableExtender setGravity(int n) {
        this.mGravity = n;
        return this;
    }

    public NotificationCompat.WearableExtender setHintAmbientBigPicture(boolean bl) {
        this.setFlag(32, bl);
        return this;
    }

    public NotificationCompat.WearableExtender setHintAvoidBackgroundClipping(boolean bl) {
        this.setFlag(16, bl);
        return this;
    }

    public NotificationCompat.WearableExtender setHintContentIntentLaunchesActivity(boolean bl) {
        this.setFlag(64, bl);
        return this;
    }

    public NotificationCompat.WearableExtender setHintHideIcon(boolean bl) {
        this.setFlag(2, bl);
        return this;
    }

    public NotificationCompat.WearableExtender setHintScreenTimeout(int n) {
        this.mHintScreenTimeout = n;
        return this;
    }

    public NotificationCompat.WearableExtender setHintShowBackgroundOnly(boolean bl) {
        this.setFlag(4, bl);
        return this;
    }

    public NotificationCompat.WearableExtender setStartScrollBottom(boolean bl) {
        this.setFlag(8, bl);
        return this;
    }
}
