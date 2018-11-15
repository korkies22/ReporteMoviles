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

@RequiresApi(value=23)
static class FragmentCompat.FragmentCompatApi23Impl
extends FragmentCompat.FragmentCompatApi15Impl {
    FragmentCompat.FragmentCompatApi23Impl() {
    }

    @Override
    public void requestPermissions(Fragment fragment, String[] arrstring, int n) {
        fragment.requestPermissions(arrstring, n);
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(Fragment fragment, String string) {
        return fragment.shouldShowRequestPermissionRationale(string);
    }
}
