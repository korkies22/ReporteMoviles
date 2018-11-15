/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Fragment
 */
package android.support.v13.app;

import android.app.Fragment;
import android.support.v13.app.FragmentCompat;

static interface FragmentCompat.FragmentCompatImpl {
    public void requestPermissions(Fragment var1, String[] var2, int var3);

    public void setUserVisibleHint(Fragment var1, boolean var2);

    public boolean shouldShowRequestPermissionRationale(Fragment var1, String var2);
}
