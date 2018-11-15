/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.text.TextUtils
 */
package android.support.v4.media.session;

import android.os.Bundle;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;

public static final class PlaybackStateCompat.CustomAction.Builder {
    private final String mAction;
    private Bundle mExtras;
    private final int mIcon;
    private final CharSequence mName;

    public PlaybackStateCompat.CustomAction.Builder(String string, CharSequence charSequence, int n) {
        if (TextUtils.isEmpty((CharSequence)string)) {
            throw new IllegalArgumentException("You must specify an action to build a CustomAction.");
        }
        if (TextUtils.isEmpty((CharSequence)charSequence)) {
            throw new IllegalArgumentException("You must specify a name to build a CustomAction.");
        }
        if (n == 0) {
            throw new IllegalArgumentException("You must specify an icon resource id to build a CustomAction.");
        }
        this.mAction = string;
        this.mName = charSequence;
        this.mIcon = n;
    }

    public PlaybackStateCompat.CustomAction build() {
        return new PlaybackStateCompat.CustomAction(this.mAction, this.mName, this.mIcon, this.mExtras);
    }

    public PlaybackStateCompat.CustomAction.Builder setExtras(Bundle bundle) {
        this.mExtras = bundle;
        return this;
    }
}
