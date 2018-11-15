/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.ApplicationInfo
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Process
 *  android.util.Log
 *  android.util.TypedValue
 */
package android.support.v4.content;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.TypedValue;
import java.io.File;

public class ContextCompat {
    private static final String TAG = "ContextCompat";
    private static final Object sLock = new Object();
    private static TypedValue sTempValue;

    protected ContextCompat() {
    }

    private static /* varargs */ File buildPath(File file, String ... arrstring) {
        int n = arrstring.length;
        File file2 = file;
        for (int i = 0; i < n; ++i) {
            String string = arrstring[i];
            if (file2 == null) {
                file = new File(string);
            } else {
                file = file2;
                if (string != null) {
                    file = new File(file2, string);
                }
            }
            file2 = file;
        }
        return file2;
    }

    public static int checkSelfPermission(@NonNull Context context, @NonNull String string) {
        if (string == null) {
            throw new IllegalArgumentException("permission is null");
        }
        return context.checkPermission(string, Process.myPid(), Process.myUid());
    }

    @Nullable
    public static Context createDeviceProtectedStorageContext(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= 24) {
            return context.createDeviceProtectedStorageContext();
        }
        return null;
    }

    private static File createFilesDir(File file) {
        synchronized (ContextCompat.class) {
            block5 : {
                block6 : {
                    if (file.exists() || file.mkdirs()) break block5;
                    boolean bl = file.exists();
                    if (!bl) break block6;
                    return file;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unable to create files subdir ");
                stringBuilder.append(file.getPath());
                Log.w((String)TAG, (String)stringBuilder.toString());
                return null;
            }
            return file;
            finally {
            }
        }
    }

    public static File getCodeCacheDir(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= 21) {
            return context.getCodeCacheDir();
        }
        return ContextCompat.createFilesDir(new File(context.getApplicationInfo().dataDir, "code_cache"));
    }

    @ColorInt
    public static int getColor(@NonNull Context context, @ColorRes int n) {
        if (Build.VERSION.SDK_INT >= 23) {
            return context.getColor(n);
        }
        return context.getResources().getColor(n);
    }

    @Nullable
    public static ColorStateList getColorStateList(@NonNull Context context, @ColorRes int n) {
        if (Build.VERSION.SDK_INT >= 23) {
            return context.getColorStateList(n);
        }
        return context.getResources().getColorStateList(n);
    }

    @Nullable
    public static File getDataDir(@NonNull Context object) {
        if (Build.VERSION.SDK_INT >= 24) {
            return object.getDataDir();
        }
        object = object.getApplicationInfo().dataDir;
        if (object != null) {
            return new File((String)object);
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Nullable
    public static Drawable getDrawable(@NonNull Context context, @DrawableRes int n) {
        if (Build.VERSION.SDK_INT >= 21) {
            return context.getDrawable(n);
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return context.getResources().getDrawable(n);
        }
        Object object = sLock;
        synchronized (object) {
            if (sTempValue == null) {
                sTempValue = new TypedValue();
            }
            context.getResources().getValue(n, sTempValue, true);
            n = ContextCompat.sTempValue.resourceId;
            return context.getResources().getDrawable(n);
        }
    }

    @NonNull
    public static File[] getExternalCacheDirs(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= 19) {
            return context.getExternalCacheDirs();
        }
        return new File[]{context.getExternalCacheDir()};
    }

    @NonNull
    public static File[] getExternalFilesDirs(@NonNull Context context, @Nullable String string) {
        if (Build.VERSION.SDK_INT >= 19) {
            return context.getExternalFilesDirs(string);
        }
        return new File[]{context.getExternalFilesDir(string)};
    }

    @Nullable
    public static File getNoBackupFilesDir(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= 21) {
            return context.getNoBackupFilesDir();
        }
        return ContextCompat.createFilesDir(new File(context.getApplicationInfo().dataDir, "no_backup"));
    }

    @NonNull
    public static File[] getObbDirs(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= 19) {
            return context.getObbDirs();
        }
        return new File[]{context.getObbDir()};
    }

    public static boolean isDeviceProtectedStorage(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= 24) {
            return context.isDeviceProtectedStorage();
        }
        return false;
    }

    public static boolean startActivities(@NonNull Context context, @NonNull Intent[] arrintent) {
        return ContextCompat.startActivities(context, arrintent, null);
    }

    public static boolean startActivities(@NonNull Context context, @NonNull Intent[] arrintent, @Nullable Bundle bundle) {
        if (Build.VERSION.SDK_INT >= 16) {
            context.startActivities(arrintent, bundle);
        } else {
            context.startActivities(arrintent);
        }
        return true;
    }

    public static void startActivity(@NonNull Context context, @NonNull Intent intent, @Nullable Bundle bundle) {
        if (Build.VERSION.SDK_INT >= 16) {
            context.startActivity(intent, bundle);
            return;
        }
        context.startActivity(intent);
    }

    public static void startForegroundService(@NonNull Context context, @NonNull Intent intent) {
        if (Build.VERSION.SDK_INT >= 26) {
            context.startForegroundService(intent);
            return;
        }
        context.startService(intent);
    }
}
