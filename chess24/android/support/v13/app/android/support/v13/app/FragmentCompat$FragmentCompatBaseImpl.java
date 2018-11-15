/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.content.pm.PackageManager
 *  android.os.Handler
 *  android.os.Looper
 */
package android.support.v13.app;

import android.app.Activity;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.support.v13.app.FragmentCompat;
import java.util.Arrays;

static class FragmentCompat.FragmentCompatBaseImpl
implements FragmentCompat.FragmentCompatImpl {
    FragmentCompat.FragmentCompatBaseImpl() {
    }

    @Override
    public void requestPermissions(final Fragment fragment, final String[] arrstring, final int n) {
        new Handler(Looper.getMainLooper()).post(new Runnable(){

            @Override
            public void run() {
                int[] arrn = new int[arrstring.length];
                Object object = fragment.getActivity();
                if (object != null) {
                    PackageManager packageManager = object.getPackageManager();
                    object = object.getPackageName();
                    int n2 = arrstring.length;
                    for (int i = 0; i < n2; ++i) {
                        arrn[i] = packageManager.checkPermission(arrstring[i], (String)object);
                    }
                } else {
                    Arrays.fill(arrn, -1);
                }
                ((FragmentCompat.OnRequestPermissionsResultCallback)fragment).onRequestPermissionsResult(n, arrstring, arrn);
            }
        });
    }

    @Override
    public void setUserVisibleHint(Fragment fragment, boolean bl) {
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(Fragment fragment, String string) {
        return false;
    }

}
