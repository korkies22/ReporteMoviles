/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.SharedElementCallback
 *  android.app.SharedElementCallback$OnSharedElementsReadyListener
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentSender
 *  android.content.IntentSender$SendIntentException
 *  android.content.pm.PackageManager
 *  android.graphics.Matrix
 *  android.graphics.RectF
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Parcelable
 *  android.view.DragEvent
 *  android.view.View
 */
package android.support.v4.app;

import android.app.Activity;
import android.app.SharedElementCallback;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.v13.view.DragAndDropPermissionsCompat;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.content.ContextCompat;
import android.view.DragEvent;
import android.view.View;
import java.util.List;
import java.util.Map;

public class ActivityCompat
extends ContextCompat {
    private static PermissionCompatDelegate sDelegate;

    protected ActivityCompat() {
    }

    public static void finishAffinity(@NonNull Activity activity) {
        if (Build.VERSION.SDK_INT >= 16) {
            activity.finishAffinity();
            return;
        }
        activity.finish();
    }

    public static void finishAfterTransition(@NonNull Activity activity) {
        if (Build.VERSION.SDK_INT >= 21) {
            activity.finishAfterTransition();
            return;
        }
        activity.finish();
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public static PermissionCompatDelegate getPermissionCompatDelegate() {
        return sDelegate;
    }

    @Nullable
    public static Uri getReferrer(@NonNull Activity object) {
        if (Build.VERSION.SDK_INT >= 22) {
            return object.getReferrer();
        }
        Uri uri = (Uri)(object = object.getIntent()).getParcelableExtra("android.intent.extra.REFERRER");
        if (uri != null) {
            return uri;
        }
        if ((object = object.getStringExtra("android.intent.extra.REFERRER_NAME")) != null) {
            return Uri.parse((String)object);
        }
        return null;
    }

    @Deprecated
    public static boolean invalidateOptionsMenu(Activity activity) {
        activity.invalidateOptionsMenu();
        return true;
    }

    public static void postponeEnterTransition(@NonNull Activity activity) {
        if (Build.VERSION.SDK_INT >= 21) {
            activity.postponeEnterTransition();
        }
    }

    @Nullable
    public static DragAndDropPermissionsCompat requestDragAndDropPermissions(Activity activity, DragEvent dragEvent) {
        return DragAndDropPermissionsCompat.request(activity, dragEvent);
    }

    public static void requestPermissions(final @NonNull Activity activity, final @NonNull String[] arrstring, final @IntRange(from=0L) int n) {
        if (sDelegate != null && sDelegate.requestPermissions(activity, arrstring, n)) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            if (activity instanceof RequestPermissionsRequestCodeValidator) {
                ((RequestPermissionsRequestCodeValidator)activity).validateRequestPermissionsRequestCode(n);
            }
            activity.requestPermissions(arrstring, n);
            return;
        }
        if (activity instanceof OnRequestPermissionsResultCallback) {
            new Handler(Looper.getMainLooper()).post(new Runnable(){

                @Override
                public void run() {
                    String[] arrstring2 = arrstring;
                    arrstring2 = new int[arrstring2.length];
                    PackageManager packageManager = activity.getPackageManager();
                    String string = activity.getPackageName();
                    int n2 = arrstring.length;
                    for (int i = 0; i < n2; ++i) {
                        arrstring2[i] = (String)packageManager.checkPermission(arrstring[i], string);
                    }
                    ((OnRequestPermissionsResultCallback)activity).onRequestPermissionsResult(n, arrstring, (int[])arrstring2);
                }
            });
        }
    }

    @NonNull
    public static <T extends View> T requireViewById(@NonNull Activity activity, @IdRes int n) {
        if ((activity = activity.findViewById(n)) == null) {
            throw new IllegalArgumentException("ID does not reference a View inside this Activity");
        }
        return (T)activity;
    }

    public static void setEnterSharedElementCallback(@NonNull Activity activity, @Nullable SharedElementCallback sharedElementCallback) {
        int n = Build.VERSION.SDK_INT;
        SharedElementCallback23Impl sharedElementCallback23Impl = null;
        SharedElementCallback21Impl sharedElementCallback21Impl = null;
        if (n >= 23) {
            if (sharedElementCallback != null) {
                sharedElementCallback21Impl = new SharedElementCallback23Impl(sharedElementCallback);
            }
            activity.setEnterSharedElementCallback(sharedElementCallback21Impl);
            return;
        }
        if (Build.VERSION.SDK_INT >= 21) {
            sharedElementCallback21Impl = sharedElementCallback23Impl;
            if (sharedElementCallback != null) {
                sharedElementCallback21Impl = new SharedElementCallback21Impl(sharedElementCallback);
            }
            activity.setEnterSharedElementCallback((android.app.SharedElementCallback)sharedElementCallback21Impl);
        }
    }

    public static void setExitSharedElementCallback(@NonNull Activity activity, @Nullable SharedElementCallback sharedElementCallback) {
        int n = Build.VERSION.SDK_INT;
        SharedElementCallback23Impl sharedElementCallback23Impl = null;
        SharedElementCallback21Impl sharedElementCallback21Impl = null;
        if (n >= 23) {
            if (sharedElementCallback != null) {
                sharedElementCallback21Impl = new SharedElementCallback23Impl(sharedElementCallback);
            }
            activity.setExitSharedElementCallback(sharedElementCallback21Impl);
            return;
        }
        if (Build.VERSION.SDK_INT >= 21) {
            sharedElementCallback21Impl = sharedElementCallback23Impl;
            if (sharedElementCallback != null) {
                sharedElementCallback21Impl = new SharedElementCallback21Impl(sharedElementCallback);
            }
            activity.setExitSharedElementCallback((android.app.SharedElementCallback)sharedElementCallback21Impl);
        }
    }

    public static void setPermissionCompatDelegate(@Nullable PermissionCompatDelegate permissionCompatDelegate) {
        sDelegate = permissionCompatDelegate;
    }

    public static boolean shouldShowRequestPermissionRationale(@NonNull Activity activity, @NonNull String string) {
        if (Build.VERSION.SDK_INT >= 23) {
            return activity.shouldShowRequestPermissionRationale(string);
        }
        return false;
    }

    public static void startActivityForResult(@NonNull Activity activity, @NonNull Intent intent, int n, @Nullable Bundle bundle) {
        if (Build.VERSION.SDK_INT >= 16) {
            activity.startActivityForResult(intent, n, bundle);
            return;
        }
        activity.startActivityForResult(intent, n);
    }

    public static void startIntentSenderForResult(@NonNull Activity activity, @NonNull IntentSender intentSender, int n, @Nullable Intent intent, int n2, int n3, int n4, @Nullable Bundle bundle) throws IntentSender.SendIntentException {
        if (Build.VERSION.SDK_INT >= 16) {
            activity.startIntentSenderForResult(intentSender, n, intent, n2, n3, n4, bundle);
            return;
        }
        activity.startIntentSenderForResult(intentSender, n, intent, n2, n3, n4);
    }

    public static void startPostponedEnterTransition(@NonNull Activity activity) {
        if (Build.VERSION.SDK_INT >= 21) {
            activity.startPostponedEnterTransition();
        }
    }

    public static interface OnRequestPermissionsResultCallback {
        public void onRequestPermissionsResult(int var1, @NonNull String[] var2, @NonNull int[] var3);
    }

    public static interface PermissionCompatDelegate {
        public boolean onActivityResult(@NonNull Activity var1, @IntRange(from=0L) int var2, int var3, @Nullable Intent var4);

        public boolean requestPermissions(@NonNull Activity var1, @NonNull String[] var2, @IntRange(from=0L) int var3);
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public static interface RequestPermissionsRequestCodeValidator {
        public void validateRequestPermissionsRequestCode(int var1);
    }

    @RequiresApi(value=21)
    private static class SharedElementCallback21Impl
    extends android.app.SharedElementCallback {
        protected SharedElementCallback mCallback;

        SharedElementCallback21Impl(SharedElementCallback sharedElementCallback) {
            this.mCallback = sharedElementCallback;
        }

        public Parcelable onCaptureSharedElementSnapshot(View view, Matrix matrix, RectF rectF) {
            return this.mCallback.onCaptureSharedElementSnapshot(view, matrix, rectF);
        }

        public View onCreateSnapshotView(Context context, Parcelable parcelable) {
            return this.mCallback.onCreateSnapshotView(context, parcelable);
        }

        public void onMapSharedElements(List<String> list, Map<String, View> map) {
            this.mCallback.onMapSharedElements(list, map);
        }

        public void onRejectSharedElements(List<View> list) {
            this.mCallback.onRejectSharedElements(list);
        }

        public void onSharedElementEnd(List<String> list, List<View> list2, List<View> list3) {
            this.mCallback.onSharedElementEnd(list, list2, list3);
        }

        public void onSharedElementStart(List<String> list, List<View> list2, List<View> list3) {
            this.mCallback.onSharedElementStart(list, list2, list3);
        }
    }

    @RequiresApi(value=23)
    private static class SharedElementCallback23Impl
    extends SharedElementCallback21Impl {
        SharedElementCallback23Impl(SharedElementCallback sharedElementCallback) {
            super(sharedElementCallback);
        }

        public void onSharedElementsArrived(List<String> list, List<View> list2, final SharedElementCallback.OnSharedElementsReadyListener onSharedElementsReadyListener) {
            this.mCallback.onSharedElementsArrived(list, list2, new SharedElementCallback.OnSharedElementsReadyListener(){

                @Override
                public void onSharedElementsReady() {
                    onSharedElementsReadyListener.onSharedElementsReady();
                }
            });
        }

    }

}
