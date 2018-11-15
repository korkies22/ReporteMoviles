/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package android.support.v4.app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;

static final class FragmentTabHost.TabInfo {
    @Nullable
    final Bundle args;
    @NonNull
    final Class<?> clss;
    Fragment fragment;
    @NonNull
    final String tag;

    FragmentTabHost.TabInfo(@NonNull String string, @NonNull Class<?> class_, @Nullable Bundle bundle) {
        this.tag = string;
        this.clss = class_;
        this.args = bundle;
    }
}
