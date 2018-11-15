/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.graphics.Bitmap
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.Parcelable
 *  android.widget.RemoteViews
 */
package android.support.customtabs;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.annotation.AnimRes;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsSession;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.BundleCompat;
import android.widget.RemoteViews;
import java.util.ArrayList;

public static final class CustomTabsIntent.Builder {
    private ArrayList<Bundle> mActionButtons;
    private boolean mInstantAppsEnabled;
    private final Intent mIntent = new Intent("android.intent.action.VIEW");
    private ArrayList<Bundle> mMenuItems;
    private Bundle mStartAnimationBundle;

    public CustomTabsIntent.Builder() {
        this(null);
    }

    public CustomTabsIntent.Builder(@Nullable CustomTabsSession customTabsSession) {
        Object var2_2 = null;
        this.mMenuItems = null;
        this.mStartAnimationBundle = null;
        this.mActionButtons = null;
        this.mInstantAppsEnabled = true;
        if (customTabsSession != null) {
            this.mIntent.setPackage(customTabsSession.getComponentName().getPackageName());
        }
        Bundle bundle = new Bundle();
        customTabsSession = customTabsSession == null ? var2_2 : customTabsSession.getBinder();
        BundleCompat.putBinder(bundle, CustomTabsIntent.EXTRA_SESSION, (IBinder)customTabsSession);
        this.mIntent.putExtras(bundle);
    }

    public CustomTabsIntent.Builder addDefaultShareMenuItem() {
        this.mIntent.putExtra(CustomTabsIntent.EXTRA_DEFAULT_SHARE_MENU_ITEM, true);
        return this;
    }

    public CustomTabsIntent.Builder addMenuItem(@NonNull String string, @NonNull PendingIntent pendingIntent) {
        if (this.mMenuItems == null) {
            this.mMenuItems = new ArrayList();
        }
        Bundle bundle = new Bundle();
        bundle.putString(CustomTabsIntent.KEY_MENU_ITEM_TITLE, string);
        bundle.putParcelable(CustomTabsIntent.KEY_PENDING_INTENT, (Parcelable)pendingIntent);
        this.mMenuItems.add(bundle);
        return this;
    }

    @Deprecated
    public CustomTabsIntent.Builder addToolbarItem(int n, @NonNull Bitmap bitmap, @NonNull String string, PendingIntent pendingIntent) throws IllegalStateException {
        if (this.mActionButtons == null) {
            this.mActionButtons = new ArrayList();
        }
        if (this.mActionButtons.size() >= 5) {
            throw new IllegalStateException("Exceeded maximum toolbar item count of 5");
        }
        Bundle bundle = new Bundle();
        bundle.putInt(CustomTabsIntent.KEY_ID, n);
        bundle.putParcelable(CustomTabsIntent.KEY_ICON, (Parcelable)bitmap);
        bundle.putString(CustomTabsIntent.KEY_DESCRIPTION, string);
        bundle.putParcelable(CustomTabsIntent.KEY_PENDING_INTENT, (Parcelable)pendingIntent);
        this.mActionButtons.add(bundle);
        return this;
    }

    public CustomTabsIntent build() {
        if (this.mMenuItems != null) {
            this.mIntent.putParcelableArrayListExtra(CustomTabsIntent.EXTRA_MENU_ITEMS, this.mMenuItems);
        }
        if (this.mActionButtons != null) {
            this.mIntent.putParcelableArrayListExtra(CustomTabsIntent.EXTRA_TOOLBAR_ITEMS, this.mActionButtons);
        }
        this.mIntent.putExtra(CustomTabsIntent.EXTRA_ENABLE_INSTANT_APPS, this.mInstantAppsEnabled);
        return new CustomTabsIntent(this.mIntent, this.mStartAnimationBundle, null);
    }

    public CustomTabsIntent.Builder enableUrlBarHiding() {
        this.mIntent.putExtra(CustomTabsIntent.EXTRA_ENABLE_URLBAR_HIDING, true);
        return this;
    }

    public CustomTabsIntent.Builder setActionButton(@NonNull Bitmap bitmap, @NonNull String string, @NonNull PendingIntent pendingIntent) {
        return this.setActionButton(bitmap, string, pendingIntent, false);
    }

    public CustomTabsIntent.Builder setActionButton(@NonNull Bitmap bitmap, @NonNull String string, @NonNull PendingIntent pendingIntent, boolean bl) {
        Bundle bundle = new Bundle();
        bundle.putInt(CustomTabsIntent.KEY_ID, 0);
        bundle.putParcelable(CustomTabsIntent.KEY_ICON, (Parcelable)bitmap);
        bundle.putString(CustomTabsIntent.KEY_DESCRIPTION, string);
        bundle.putParcelable(CustomTabsIntent.KEY_PENDING_INTENT, (Parcelable)pendingIntent);
        this.mIntent.putExtra(CustomTabsIntent.EXTRA_ACTION_BUTTON_BUNDLE, bundle);
        this.mIntent.putExtra(CustomTabsIntent.EXTRA_TINT_ACTION_BUTTON, bl);
        return this;
    }

    public CustomTabsIntent.Builder setCloseButtonIcon(@NonNull Bitmap bitmap) {
        this.mIntent.putExtra(CustomTabsIntent.EXTRA_CLOSE_BUTTON_ICON, (Parcelable)bitmap);
        return this;
    }

    public CustomTabsIntent.Builder setExitAnimations(@NonNull Context context, @AnimRes int n, @AnimRes int n2) {
        context = ActivityOptionsCompat.makeCustomAnimation(context, n, n2).toBundle();
        this.mIntent.putExtra(CustomTabsIntent.EXTRA_EXIT_ANIMATION_BUNDLE, (Bundle)context);
        return this;
    }

    public CustomTabsIntent.Builder setInstantAppsEnabled(boolean bl) {
        this.mInstantAppsEnabled = bl;
        return this;
    }

    public CustomTabsIntent.Builder setSecondaryToolbarColor(@ColorInt int n) {
        this.mIntent.putExtra(CustomTabsIntent.EXTRA_SECONDARY_TOOLBAR_COLOR, n);
        return this;
    }

    public CustomTabsIntent.Builder setSecondaryToolbarViews(@NonNull RemoteViews remoteViews, @Nullable int[] arrn, @Nullable PendingIntent pendingIntent) {
        this.mIntent.putExtra(CustomTabsIntent.EXTRA_REMOTEVIEWS, (Parcelable)remoteViews);
        this.mIntent.putExtra(CustomTabsIntent.EXTRA_REMOTEVIEWS_VIEW_IDS, arrn);
        this.mIntent.putExtra(CustomTabsIntent.EXTRA_REMOTEVIEWS_PENDINGINTENT, (Parcelable)pendingIntent);
        return this;
    }

    public CustomTabsIntent.Builder setShowTitle(boolean bl) {
        RuntimeException runtimeException;
        super("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
        throw runtimeException;
    }

    public CustomTabsIntent.Builder setStartAnimations(@NonNull Context context, @AnimRes int n, @AnimRes int n2) {
        this.mStartAnimationBundle = ActivityOptionsCompat.makeCustomAnimation(context, n, n2).toBundle();
        return this;
    }

    public CustomTabsIntent.Builder setToolbarColor(@ColorInt int n) {
        this.mIntent.putExtra(CustomTabsIntent.EXTRA_TOOLBAR_COLOR, n);
        return this;
    }
}
