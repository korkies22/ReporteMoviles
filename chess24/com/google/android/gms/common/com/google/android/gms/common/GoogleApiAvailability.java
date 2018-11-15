/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.annotation.TargetApi
 *  android.app.Activity
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.app.Dialog
 *  android.app.FragmentManager
 *  android.app.Notification
 *  android.app.Notification$BigTextStyle
 *  android.app.Notification$Builder
 *  android.app.Notification$Style
 *  android.app.NotificationManager
 *  android.app.PendingIntent
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.pm.ApplicationInfo
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.TypedValue
 *  android.view.View
 *  android.widget.ProgressBar
 */
package com.google.android.gms.common;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ProgressBar;
import com.google.android.gms.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.ErrorDialogFragment;
import com.google.android.gms.common.SupportErrorDialogFragment;
import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.internal.zzh;
import com.google.android.gms.common.internal.zzi;
import com.google.android.gms.common.util.zzs;
import com.google.android.gms.common.zzc;
import com.google.android.gms.common.zze;
import com.google.android.gms.internal.zzaar;
import com.google.android.gms.internal.zzaax;
import com.google.android.gms.internal.zzabb;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import java.util.concurrent.atomic.AtomicBoolean;

public class GoogleApiAvailability
extends zzc {
    public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";
    public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE;
    private static final GoogleApiAvailability zzaxa;

    static {
        zzaxa = new GoogleApiAvailability();
        GOOGLE_PLAY_SERVICES_VERSION_CODE = zzc.GOOGLE_PLAY_SERVICES_VERSION_CODE;
    }

    GoogleApiAvailability() {
    }

    public static GoogleApiAvailability getInstance() {
        return zzaxa;
    }

    public Dialog getErrorDialog(Activity activity, int n, int n2) {
        return this.getErrorDialog(activity, n, n2, null);
    }

    public Dialog getErrorDialog(Activity activity, int n, int n2, DialogInterface.OnCancelListener onCancelListener) {
        return this.zza((Context)activity, n, zzi.zza(activity, this.zzb((Context)activity, n, "d"), n2), onCancelListener);
    }

    @Nullable
    @Override
    public PendingIntent getErrorResolutionPendingIntent(Context context, int n, int n2) {
        return super.getErrorResolutionPendingIntent(context, n, n2);
    }

    @Nullable
    public PendingIntent getErrorResolutionPendingIntent(Context context, ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            return connectionResult.getResolution();
        }
        return this.getErrorResolutionPendingIntent(context, connectionResult.getErrorCode(), 0);
    }

    @Override
    public final String getErrorString(int n) {
        return super.getErrorString(n);
    }

    @Nullable
    @Override
    public String getOpenSourceSoftwareLicenseInfo(Context context) {
        return super.getOpenSourceSoftwareLicenseInfo(context);
    }

    @Override
    public int isGooglePlayServicesAvailable(Context context) {
        return super.isGooglePlayServicesAvailable(context);
    }

    @Override
    public final boolean isUserResolvableError(int n) {
        return super.isUserResolvableError(n);
    }

    @MainThread
    public Task<Void> makeGooglePlayServicesAvailable(Activity object) {
        zzac.zzdn("makeGooglePlayServicesAvailable must be called from the main thread");
        int n = this.isGooglePlayServicesAvailable((Context)object);
        if (n == 0) {
            return Tasks.forResult(null);
        }
        object = zzabb.zzu((Activity)object);
        object.zzk(new ConnectionResult(n, null));
        return object.getTask();
    }

    public boolean showErrorDialogFragment(Activity activity, int n, int n2) {
        return this.showErrorDialogFragment(activity, n, n2, null);
    }

    public boolean showErrorDialogFragment(Activity activity, int n, int n2, DialogInterface.OnCancelListener onCancelListener) {
        Dialog dialog = this.getErrorDialog(activity, n, n2, onCancelListener);
        if (dialog == null) {
            return false;
        }
        this.zza(activity, dialog, "GooglePlayServicesErrorDialog", onCancelListener);
        return true;
    }

    public void showErrorNotification(Context context, int n) {
        this.zza(context, n, null);
    }

    public void showErrorNotification(Context context, ConnectionResult connectionResult) {
        PendingIntent pendingIntent = this.getErrorResolutionPendingIntent(context, connectionResult);
        this.zza(context, connectionResult.getErrorCode(), null, pendingIntent);
    }

    public Dialog zza(Activity activity, DialogInterface.OnCancelListener onCancelListener) {
        ProgressBar progressBar = new ProgressBar((Context)activity, null, 16842874);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(0);
        AlertDialog.Builder builder = new AlertDialog.Builder((Context)activity);
        builder.setView((View)progressBar);
        builder.setMessage((CharSequence)zzh.zzi((Context)activity, 18));
        builder.setPositiveButton((CharSequence)"", null);
        progressBar = builder.create();
        this.zza(activity, (Dialog)progressBar, "GooglePlayServicesUpdatingDialog", onCancelListener);
        return progressBar;
    }

    @TargetApi(value=14)
    Dialog zza(Context object, int n, zzi zzi2, DialogInterface.OnCancelListener object2) {
        AlertDialog.Builder builder = null;
        if (n == 0) {
            return null;
        }
        AlertDialog.Builder builder2 = builder;
        if (zzs.zzyA()) {
            TypedValue typedValue = new TypedValue();
            object.getTheme().resolveAttribute(16843529, typedValue, true);
            builder2 = builder;
            if ("Theme.Dialog.Alert".equals(object.getResources().getResourceEntryName(typedValue.resourceId))) {
                builder2 = new AlertDialog.Builder(object, 5);
            }
        }
        builder = builder2;
        if (builder2 == null) {
            builder = new AlertDialog.Builder(object);
        }
        builder.setMessage((CharSequence)zzh.zzi(object, n));
        if (object2 != null) {
            builder.setOnCancelListener(object2);
        }
        if ((object2 = zzh.zzk(object, n)) != null) {
            builder.setPositiveButton((CharSequence)object2, (DialogInterface.OnClickListener)zzi2);
        }
        if ((object = zzh.zzg(object, n)) != null) {
            builder.setTitle((CharSequence)object);
        }
        return builder.create();
    }

    @Nullable
    @Override
    public PendingIntent zza(Context context, int n, int n2, @Nullable String string) {
        return super.zza(context, n, n2, string);
    }

    @Nullable
    public zzaar zza(Context context, zzaar.zza zza2) {
        IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_ADDED");
        intentFilter.addDataScheme("package");
        zzaar zzaar2 = new zzaar(zza2);
        context.registerReceiver((BroadcastReceiver)zzaar2, intentFilter);
        zzaar2.setContext(context);
        if (!this.zzs(context, GOOGLE_PLAY_SERVICES_PACKAGE)) {
            zza2.zzvb();
            zzaar2.unregister();
            return null;
        }
        return zzaar2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @TargetApi(value=11)
    void zza(Activity object, Dialog dialog, String string, DialogInterface.OnCancelListener onCancelListener) {
        boolean bl;
        void var2_4;
        void var4_6;
        void var3_5;
        block4 : {
            try {
                bl = object instanceof FragmentActivity;
                break block4;
            }
            catch (NoClassDefFoundError noClassDefFoundError) {}
            bl = false;
        }
        if (bl) {
            FragmentManager fragmentManager = ((FragmentActivity)object).getSupportFragmentManager();
            SupportErrorDialogFragment.newInstance((Dialog)var2_4, (DialogInterface.OnCancelListener)var4_6).show(fragmentManager, (String)var3_5);
            return;
        }
        if (zzs.zzyx()) {
            android.app.FragmentManager fragmentManager = object.getFragmentManager();
            ErrorDialogFragment.newInstance((Dialog)var2_4, (DialogInterface.OnCancelListener)var4_6).show(fragmentManager, (String)var3_5);
            return;
        }
        throw new RuntimeException("This Activity does not support Fragments.");
    }

    public void zza(Context context, int n, String string) {
        this.zza(context, n, string, this.zza(context, n, 0, "n"));
    }

    @TargetApi(value=20)
    void zza(Context context, int n, String string2, PendingIntent pendingIntent) {
        if (n == 18) {
            this.zzal(context);
            return;
        }
        if (pendingIntent == null) {
            if (n == 6) {
                Log.w((String)"GoogleApiAvailability", (String)"Missing resolution for ConnectionResult.RESOLUTION_REQUIRED. Call GoogleApiAvailability#showErrorNotification(Context, ConnectionResult) instead.");
            }
            return;
        }
        String string3 = zzh.zzh(context, n);
        String string4 = zzh.zzj(context, n);
        Resources resources = context.getResources();
        if (com.google.android.gms.common.util.zzi.zzaJ(context)) {
            zzac.zzar(zzs.zzyG());
            pendingIntent = new Notification.Builder(context).setSmallIcon(context.getApplicationInfo().icon).setPriority(2).setAutoCancel(true).setContentTitle((CharSequence)string3).setStyle((Notification.Style)new Notification.BigTextStyle().bigText((CharSequence)string4)).addAction(R.drawable.common_full_open_on_phone, (CharSequence)resources.getString(R.string.common_open_on_phone), pendingIntent).build();
        } else {
            pendingIntent = new NotificationCompat.Builder(context).setSmallIcon(17301642).setTicker(resources.getString(R.string.common_google_play_services_notification_ticker)).setWhen(System.currentTimeMillis()).setAutoCancel(true).setContentIntent(pendingIntent).setContentTitle(string3).setContentText(string4).setLocalOnly(true).setStyle(new NotificationCompat.BigTextStyle().bigText(string4)).build();
        }
        switch (n) {
            default: {
                n = 39789;
                break;
            }
            case 1: 
            case 2: 
            case 3: {
                n = 10436;
                zze.zzaxp.set(false);
            }
        }
        context = (NotificationManager)context.getSystemService("notification");
        if (string2 == null) {
            context.notify(n, (Notification)pendingIntent);
            return;
        }
        context.notify(string2, n, (Notification)pendingIntent);
    }

    public void zza(Context context, ConnectionResult connectionResult, int n) {
        PendingIntent pendingIntent = this.getErrorResolutionPendingIntent(context, connectionResult);
        if (pendingIntent != null) {
            this.zza(context, connectionResult.getErrorCode(), null, GoogleApiActivity.zza(context, pendingIntent, n));
        }
    }

    public boolean zza(Activity activity, @NonNull zzaax zzaax2, int n, int n2, DialogInterface.OnCancelListener onCancelListener) {
        if ((zzaax2 = this.zza((Context)activity, n, zzi.zza(zzaax2, this.zzb((Context)activity, n, "d"), n2), onCancelListener)) == null) {
            return false;
        }
        this.zza(activity, (Dialog)zzaax2, "GooglePlayServicesErrorDialog", onCancelListener);
        return true;
    }

    @Override
    public int zzak(Context context) {
        return super.zzak(context);
    }

    void zzal(Context context) {
        new zza(context).sendEmptyMessageDelayed(1, 120000L);
    }

    @Nullable
    @Override
    public Intent zzb(Context context, int n, @Nullable String string2) {
        return super.zzb(context, n, string2);
    }

    @Deprecated
    @Nullable
    @Override
    public Intent zzcr(int n) {
        return super.zzcr(n);
    }

    @Override
    public boolean zzd(Context context, int n) {
        return super.zzd(context, n);
    }

    @SuppressLint(value={"HandlerLeak"})
    private class zza
    extends Handler {
        private final Context zzvZ;

        public zza(Context context) {
            GoogleApiAvailability.this = Looper.myLooper() == null ? Looper.getMainLooper() : Looper.myLooper();
            super((Looper)GoogleApiAvailability.this);
            this.zzvZ = context.getApplicationContext();
        }

        public void handleMessage(Message object) {
            if (object.what != 1) {
                int n = object.what;
                object = new StringBuilder(50);
                object.append("Don't know how to handle this message: ");
                object.append(n);
                Log.w((String)"GoogleApiAvailability", (String)object.toString());
                return;
            }
            int n = GoogleApiAvailability.this.isGooglePlayServicesAvailable(this.zzvZ);
            if (GoogleApiAvailability.this.isUserResolvableError(n)) {
                GoogleApiAvailability.this.showErrorNotification(this.zzvZ, n);
            }
        }
    }

}
