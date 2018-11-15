/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Fragment
 *  android.os.Bundle
 */
package android.support.v13.app;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v13.app.FragmentTabHost;

static final class FragmentTabHost.TabInfo {
    final Bundle args;
    final Class<?> clss;
    Fragment fragment;
    final String tag;

    FragmentTabHost.TabInfo(String string, Class<?> class_, Bundle bundle) {
        this.tag = string;
        this.clss = class_;
        this.args = bundle;
    }
}
