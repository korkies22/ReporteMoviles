/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.pm.PackageManager
 */
package android.support.v4.app;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

static final class ActivityCompat
implements Runnable {
    final /* synthetic */ Activity val$activity;
    final /* synthetic */ String[] val$permissions;
    final /* synthetic */ int val$requestCode;

    ActivityCompat(String[] arrstring, Activity activity, int n) {
        this.val$permissions = arrstring;
        this.val$activity = activity;
        this.val$requestCode = n;
    }

    @Override
    public void run() {
        String[] arrstring = this.val$permissions;
        arrstring = new int[arrstring.length];
        PackageManager packageManager = this.val$activity.getPackageManager();
        String string = this.val$activity.getPackageName();
        int n = this.val$permissions.length;
        for (int i = 0; i < n; ++i) {
            arrstring[i] = (String)packageManager.checkPermission(this.val$permissions[i], string);
        }
        ((ActivityCompat.OnRequestPermissionsResultCallback)this.val$activity).onRequestPermissionsResult(this.val$requestCode, this.val$permissions, (int[])arrstring);
    }
}
