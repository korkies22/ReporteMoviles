/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.text.TextUtils
 */
package android.support.v4.content.pm;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.pm.ShortcutInfoCompat;
import android.support.v4.graphics.drawable.IconCompat;
import android.text.TextUtils;

public static class ShortcutInfoCompat.Builder {
    private final ShortcutInfoCompat mInfo = new ShortcutInfoCompat(null);

    public ShortcutInfoCompat.Builder(@NonNull Context context, @NonNull String string) {
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
    public ShortcutInfoCompat.Builder setActivity(@NonNull ComponentName componentName) {
        this.mInfo.mActivity = componentName;
        return this;
    }

    public ShortcutInfoCompat.Builder setAlwaysBadged() {
        this.mInfo.mIsAlwaysBadged = true;
        return this;
    }

    @NonNull
    public ShortcutInfoCompat.Builder setDisabledMessage(@NonNull CharSequence charSequence) {
        this.mInfo.mDisabledMessage = charSequence;
        return this;
    }

    @NonNull
    public ShortcutInfoCompat.Builder setIcon(IconCompat iconCompat) {
        this.mInfo.mIcon = iconCompat;
        return this;
    }

    @NonNull
    public ShortcutInfoCompat.Builder setIntent(@NonNull Intent intent) {
        return this.setIntents(new Intent[]{intent});
    }

    @NonNull
    public ShortcutInfoCompat.Builder setIntents(@NonNull Intent[] arrintent) {
        this.mInfo.mIntents = arrintent;
        return this;
    }

    @NonNull
    public ShortcutInfoCompat.Builder setLongLabel(@NonNull CharSequence charSequence) {
        this.mInfo.mLongLabel = charSequence;
        return this;
    }

    @NonNull
    public ShortcutInfoCompat.Builder setShortLabel(@NonNull CharSequence charSequence) {
        this.mInfo.mLabel = charSequence;
        return this;
    }
}
