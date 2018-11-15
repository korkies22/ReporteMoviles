/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.pm.ShortcutInfo
 *  android.content.pm.ShortcutInfo$Builder
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Icon
 *  android.os.Parcelable
 *  android.text.TextUtils
 */
package android.support.v4.content.pm;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.VisibleForTesting;
import android.support.v4.graphics.drawable.IconCompat;
import android.text.TextUtils;
import java.util.Arrays;

public class ShortcutInfoCompat {
    private ComponentName mActivity;
    private Context mContext;
    private CharSequence mDisabledMessage;
    private IconCompat mIcon;
    private String mId;
    private Intent[] mIntents;
    private boolean mIsAlwaysBadged;
    private CharSequence mLabel;
    private CharSequence mLongLabel;

    private ShortcutInfoCompat() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @VisibleForTesting
    Intent addToIntent(Intent intent) {
        intent.putExtra("android.intent.extra.shortcut.INTENT", (Parcelable)this.mIntents[this.mIntents.length - 1]).putExtra("android.intent.extra.shortcut.NAME", this.mLabel.toString());
        if (this.mIcon != null) {
            Drawable drawable = null;
            Drawable drawable2 = null;
            if (this.mIsAlwaysBadged) {
                PackageManager packageManager = this.mContext.getPackageManager();
                Drawable drawable3 = drawable2;
                if (this.mActivity != null) {
                    try {
                        drawable3 = packageManager.getActivityIcon(this.mActivity);
                    }
                    catch (PackageManager.NameNotFoundException nameNotFoundException) {
                        drawable3 = drawable2;
                    }
                }
                drawable = drawable3;
                if (drawable3 == null) {
                    drawable = this.mContext.getApplicationInfo().loadIcon(packageManager);
                }
            }
            this.mIcon.addToShortcutIntent(intent, drawable);
        }
        return intent;
    }

    @Nullable
    public ComponentName getActivity() {
        return this.mActivity;
    }

    @Nullable
    public CharSequence getDisabledMessage() {
        return this.mDisabledMessage;
    }

    @NonNull
    public String getId() {
        return this.mId;
    }

    @NonNull
    public Intent getIntent() {
        return this.mIntents[this.mIntents.length - 1];
    }

    @NonNull
    public Intent[] getIntents() {
        return Arrays.copyOf(this.mIntents, this.mIntents.length);
    }

    @Nullable
    public CharSequence getLongLabel() {
        return this.mLongLabel;
    }

    @NonNull
    public CharSequence getShortLabel() {
        return this.mLabel;
    }

    @RequiresApi(value=25)
    public ShortcutInfo toShortcutInfo() {
        ShortcutInfo.Builder builder = new ShortcutInfo.Builder(this.mContext, this.mId).setShortLabel(this.mLabel).setIntents(this.mIntents);
        if (this.mIcon != null) {
            builder.setIcon(this.mIcon.toIcon());
        }
        if (!TextUtils.isEmpty((CharSequence)this.mLongLabel)) {
            builder.setLongLabel(this.mLongLabel);
        }
        if (!TextUtils.isEmpty((CharSequence)this.mDisabledMessage)) {
            builder.setDisabledMessage(this.mDisabledMessage);
        }
        if (this.mActivity != null) {
            builder.setActivity(this.mActivity);
        }
        return builder.build();
    }

    public static class Builder {
        private final ShortcutInfoCompat mInfo = new ShortcutInfoCompat();

        public Builder(@NonNull Context context, @NonNull String string) {
            this.mInfo.mContext = context;
            this.mInfo.mId = string;
        }

        @NonNull
        public ShortcutInfoCompat build() {
            if (TextUtils.isEmpty((CharSequence)this.mInfo.mLabel)) {
                throw new IllegalArgumentException("Shortcut much have a non-empty label");
            }
            if (this.mInfo.mIntents != null && this.mInfo.mIntents.length != 0) {
                return this.mInfo;
            }
            throw new IllegalArgumentException("Shortcut much have an intent");
        }

        @NonNull
        public Builder setActivity(@NonNull ComponentName componentName) {
            this.mInfo.mActivity = componentName;
            return this;
        }

        public Builder setAlwaysBadged() {
            this.mInfo.mIsAlwaysBadged = true;
            return this;
        }

        @NonNull
        public Builder setDisabledMessage(@NonNull CharSequence charSequence) {
            this.mInfo.mDisabledMessage = charSequence;
            return this;
        }

        @NonNull
        public Builder setIcon(IconCompat iconCompat) {
            this.mInfo.mIcon = iconCompat;
            return this;
        }

        @NonNull
        public Builder setIntent(@NonNull Intent intent) {
            return this.setIntents(new Intent[]{intent});
        }

        @NonNull
        public Builder setIntents(@NonNull Intent[] arrintent) {
            this.mInfo.mIntents = arrintent;
            return this;
        }

        @NonNull
        public Builder setLongLabel(@NonNull CharSequence charSequence) {
            this.mInfo.mLongLabel = charSequence;
            return this;
        }

        @NonNull
        public Builder setShortLabel(@NonNull CharSequence charSequence) {
            this.mInfo.mLabel = charSequence;
            return this;
        }
    }

}
