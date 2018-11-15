/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.ActivityInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.util.Log
 */
package android.support.v4.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public final class NavUtils {
    public static final String PARENT_ACTIVITY = "android.support.PARENT_ACTIVITY";
    private static final String TAG = "NavUtils";

    private NavUtils() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Nullable
    public static Intent getParentActivityIntent(@NonNull Activity activity) {
        Intent intent;
        if (Build.VERSION.SDK_INT >= 16 && (intent = activity.getParentActivityIntent()) != null) {
            return intent;
        }
        String string = NavUtils.getParentActivityName(activity);
        if (string == null) {
            return null;
        }
        ComponentName componentName = new ComponentName((Context)activity, string);
        try {
            if (NavUtils.getParentActivityName((Context)activity, componentName) != null) return new Intent().setComponent(componentName);
            return Intent.makeMainActivity((ComponentName)componentName);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {}
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("getParentActivityIntent: bad parentActivityName '");
        stringBuilder.append(string);
        stringBuilder.append("' in manifest");
        Log.e((String)TAG, (String)stringBuilder.toString());
        return null;
    }

    @Nullable
    public static Intent getParentActivityIntent(@NonNull Context context, @NonNull ComponentName componentName) throws PackageManager.NameNotFoundException {
        String string = NavUtils.getParentActivityName(context, componentName);
        if (string == null) {
            return null;
        }
        if (NavUtils.getParentActivityName(context, componentName = new ComponentName(componentName.getPackageName(), string)) == null) {
            return Intent.makeMainActivity((ComponentName)componentName);
        }
        return new Intent().setComponent(componentName);
    }

    @Nullable
    public static Intent getParentActivityIntent(@NonNull Context context, @NonNull Class<?> object) throws PackageManager.NameNotFoundException {
        if ((object = NavUtils.getParentActivityName(context, new ComponentName(context, object))) == null) {
            return null;
        }
        if (NavUtils.getParentActivityName(context, (ComponentName)(object = new ComponentName(context, (String)object))) == null) {
            return Intent.makeMainActivity((ComponentName)object);
        }
        return new Intent().setComponent((ComponentName)object);
    }

    @Nullable
    public static String getParentActivityName(@NonNull Activity object) {
        try {
            object = NavUtils.getParentActivityName((Context)object, object.getComponentName());
            return object;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            throw new IllegalArgumentException((Throwable)nameNotFoundException);
        }
    }

    @Nullable
    public static String getParentActivityName(@NonNull Context context, @NonNull ComponentName object) throws PackageManager.NameNotFoundException {
        String string;
        object = context.getPackageManager().getActivityInfo((ComponentName)object, 128);
        if (Build.VERSION.SDK_INT >= 16 && (string = object.parentActivityName) != null) {
            return string;
        }
        if (object.metaData == null) {
            return null;
        }
        string = object.metaData.getString(PARENT_ACTIVITY);
        if (string == null) {
            return null;
        }
        object = string;
        if (string.charAt(0) == '.') {
            object = new StringBuilder();
            object.append(context.getPackageName());
            object.append(string);
            object = object.toString();
        }
        return object;
    }

    public static void navigateUpFromSameTask(@NonNull Activity activity) {
        Object object = NavUtils.getParentActivityIntent(activity);
        if (object == null) {
            object = new StringBuilder();
            object.append("Activity ");
            object.append(activity.getClass().getSimpleName());
            object.append(" does not have a parent activity name specified.");
            object.append(" (Did you forget to add the android.support.PARENT_ACTIVITY <meta-data> ");
            object.append(" element in your manifest?)");
            throw new IllegalArgumentException(object.toString());
        }
        NavUtils.navigateUpTo(activity, (Intent)object);
    }

    public static void navigateUpTo(@NonNull Activity activity, @NonNull Intent intent) {
        if (Build.VERSION.SDK_INT >= 16) {
            activity.navigateUpTo(intent);
            return;
        }
        intent.addFlags(67108864);
        activity.startActivity(intent);
        activity.finish();
    }

    public static boolean shouldUpRecreateTask(@NonNull Activity object, @NonNull Intent intent) {
        if (Build.VERSION.SDK_INT >= 16) {
            return object.shouldUpRecreateTask(intent);
        }
        if ((object = object.getIntent().getAction()) != null && !object.equals("android.intent.action.MAIN")) {
            return true;
        }
        return false;
    }
}
