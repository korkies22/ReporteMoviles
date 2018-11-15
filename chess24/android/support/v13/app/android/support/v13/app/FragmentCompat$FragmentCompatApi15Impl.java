/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Fragment
 */
package android.support.v13.app;

import android.app.Fragment;
import android.support.annotation.RequiresApi;
import android.support.v13.app.FragmentCompat;

@RequiresApi(value=15)
static class FragmentCompat.FragmentCompatApi15Impl
extends FragmentCompat.FragmentCompatBaseImpl {
    FragmentCompat.FragmentCompatApi15Impl() {
    }

    @Override
    public void setUserVisibleHint(Fragment fragment, boolean bl) {
        fragment.setUserVisibleHint(bl);
    }
}
