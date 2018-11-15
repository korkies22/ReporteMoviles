/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.PackageManager
 *  android.os.Binder
 *  android.os.Process
 */
package android.support.v4.content;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v4.app.AppOpsManagerCompat;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class PermissionChecker {
    public static final int PERMISSION_DENIED = -1;
    public static final int PERMISSION_DENIED_APP_OP = -2;
    public static final int PERMISSION_GRANTED = 0;

    private PermissionChecker() {
    }

    public static int checkCallingOrSelfPermission(@NonNull Context context, @NonNull String string) {
        String string2 = Binder.getCallingPid() == Process.myPid() ? context.getPackageName() : null;
        return PermissionChecker.checkPermission(context, string, Binder.getCallingPid(), Binder.getCallingUid(), string2);
    }

    public static int checkCallingPermission(@NonNull Context context, @NonNull String string, @Nullable String string2) {
        if (Binder.getCallingPid() == Process.myPid()) {
            return -1;
        }
        return PermissionChecker.checkPermission(context, string, Binder.getCallingPid(), Binder.getCallingUid(), string2);
    }

    public static int checkPermission(@NonNull Context context, @NonNull String object, int n, int n2, @Nullable String string) {
        if (context.checkPermission((String)object, n, n2) == -1) {
            return -1;
        }
        String string2 = AppOpsManagerCompat.permissionToOp((String)object);
        if (string2 == null) {
            return 0;
        }
        object = string;
        if (string == null) {
            object = context.getPackageManager().getPackagesForUid(n2);
            if (object != null) {
                if (((String[])object).length <= 0) {
                    return -1;
                }
                object = object[0];
            } else {
                return -1;
            }
        }
        if (AppOpsManagerCompat.noteProxyOpNoThrow(context, string2, (String)object) != 0) {
            return -2;
        }
        return 0;
    }

    public static int checkSelfPermission(@NonNull Context context, @NonNull String string) {
        return PermissionChecker.checkPermission(context, string, Process.myPid(), Process.myUid(), context.getPackageName());
    }

    @Retention(value=RetentionPolicy.SOURCE)
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public static @interface PermissionResult {
    }

}
