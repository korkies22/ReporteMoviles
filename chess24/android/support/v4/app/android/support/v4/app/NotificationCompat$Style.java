/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Notification
 *  android.content.Context
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
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.SystemClock
 *  android.widget.RemoteViews
 */
package android.support.v4.app;

import android.app.Notification;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.RestrictTo;
import android.support.compat.R;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;
import java.text.NumberFormat;

public static abstract class NotificationCompat.Style {
    CharSequence mBigContentTitle;
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    protected NotificationCompat.Builder mBuilder;
    CharSequence mSummaryText;
    boolean mSummaryTextSet = false;

    private int calculateTopPadding() {
        Resources resources = this.mBuilder.mContext.getResources();
        int n = resources.getDimensionPixelSize(R.dimen.notification_top_pad);
        int n2 = resources.getDimensionPixelSize(R.dimen.notification_top_pad_large_text);
        float f = (NotificationCompat.Style.constrain(resources.getConfiguration().fontScale, 1.0f, 1.3f) - 1.0f) / 0.29999995f;
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

    public void setBuilder(NotificationCompat.Builder builder) {
        if (this.mBuilder != builder) {
            this.mBuilder = builder;
            if (this.mBuilder != null) {
                this.mBuilder.setStyle(this);
            }
        }
    }
}
