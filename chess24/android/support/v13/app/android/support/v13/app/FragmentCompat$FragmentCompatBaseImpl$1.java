/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.content.pm.PackageManager
 */
package android.support.v13.app;

import android.app.Activity;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.support.v13.app.FragmentCompat;
import java.util.Arrays;

class FragmentCompat.FragmentCompatBaseImpl
implements Runnable {
    final /* synthetic */ Fragment val$fragment;
    final /* synthetic */ String[] val$permissions;
    final /* synthetic */ int val$requestCode;

    FragmentCompat.FragmentCompatBaseImpl(String[] arrstring, Fragment fragment, int n) {
        this.val$permissions = arrstring;
        this.val$fragment = fragment;
        this.val$requestCode = n;
    }

    @Override
    public void run() {
        int[] arrn = new int[this.val$permissions.length];
        Object object = this.val$fragment.getActivity();
        if (object != null) {
            PackageManager packageManager = object.getPackageManager();
            object = object.getPackageName();
            int n = this.val$permissions.length;
            for (int i = 0; i < n; ++i) {
                arrn[i] = packageManager.checkPermission(this.val$permissions[i], (String)object);
            }
        } else {
            Arrays.fill(arrn, -1);
        }
        ((FragmentCompat.OnRequestPermissionsResultCallback)this.val$fragment).onRequestPermissionsResult(this.val$requestCode, this.val$permissions, arrn);
    }
}
