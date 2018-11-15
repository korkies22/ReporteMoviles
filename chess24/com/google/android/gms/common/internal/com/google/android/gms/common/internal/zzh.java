/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.text.TextUtils
 *  android.util.Log
 */
package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.SimpleArrayMap;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.R;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.util.zzi;
import com.google.android.gms.internal.zzacx;
import java.util.Locale;

public final class zzh {
    private static final SimpleArrayMap<String, String> zzaEg = new SimpleArrayMap();

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static String zzaB(Context context) {
        String string;
        String string2 = string = context.getApplicationInfo().name;
        if (!TextUtils.isEmpty((CharSequence)string)) return string2;
        string = context.getPackageName();
        try {
            return zzacx.zzaQ(context).zzdE(context.getPackageName()).toString();
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return string;
        }
    }

    /*
     * Exception decompiling
     */
    @Nullable
    public static String zzg(Context var0, int var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Extractable last case doesn't follow previous
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:487)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:66)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:374)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:186)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:131)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:378)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:884)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:786)
        // org.benf.cfr.reader.Main.doClass(Main.java:54)
        // org.benf.cfr.reader.Main.main(Main.java:247)
        throw new IllegalStateException("Decompilation failed");
    }

    private static String zzg(Context object, String string2, String string3) {
        Resources resources = object.getResources();
        string2 = zzh.zzu(object, string2);
        object = string2;
        if (string2 == null) {
            object = resources.getString(R.string.common_google_play_services_unknown_issue);
        }
        return String.format(resources.getConfiguration().locale, (String)object, string3);
    }

    @NonNull
    public static String zzh(Context context, int n) {
        String string2 = n == 6 ? zzh.zzu(context, "common_google_play_services_resolution_required_title") : zzh.zzg(context, n);
        String string3 = string2;
        if (string2 == null) {
            string3 = context.getResources().getString(R.string.common_google_play_services_notification_ticker);
        }
        return string3;
    }

    @NonNull
    public static String zzi(Context context, int n) {
        Resources resources = context.getResources();
        String string2 = zzh.zzaB(context);
        if (n != 5) {
            if (n != 7) {
                if (n != 9) {
                    if (n != 20) {
                        switch (n) {
                            default: {
                                switch (n) {
                                    default: {
                                        return resources.getString(R.string.common_google_play_services_unknown_issue, new Object[]{string2});
                                    }
                                    case 18: {
                                        return resources.getString(R.string.common_google_play_services_updating_text, new Object[]{string2});
                                    }
                                    case 17: {
                                        return zzh.zzg(context, "common_google_play_services_sign_in_failed_text", string2);
                                    }
                                    case 16: 
                                }
                                return zzh.zzg(context, "common_google_play_services_api_unavailable_text", string2);
                            }
                            case 3: {
                                return resources.getString(R.string.common_google_play_services_enable_text, new Object[]{string2});
                            }
                            case 2: {
                                if (zzi.zzaJ(context)) {
                                    return resources.getString(R.string.common_google_play_services_wear_update_text);
                                }
                                return resources.getString(R.string.common_google_play_services_update_text, new Object[]{string2});
                            }
                            case 1: 
                        }
                        return resources.getString(R.string.common_google_play_services_install_text, new Object[]{string2});
                    }
                    return zzh.zzg(context, "common_google_play_services_restricted_profile_text", string2);
                }
                return resources.getString(R.string.common_google_play_services_unsupported_text, new Object[]{string2});
            }
            return zzh.zzg(context, "common_google_play_services_network_error_text", string2);
        }
        return zzh.zzg(context, "common_google_play_services_invalid_account_text", string2);
    }

    @NonNull
    public static String zzj(Context context, int n) {
        if (n == 6) {
            return zzh.zzg(context, "common_google_play_services_resolution_required_text", zzh.zzaB(context));
        }
        return zzh.zzi(context, n);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @NonNull
    public static String zzk(Context context, int n) {
        context = context.getResources();
        switch (n) {
            default: {
                n = 17039370;
                do {
                    return context.getString(n);
                    break;
                } while (true);
            }
            case 3: {
                n = R.string.common_google_play_services_enable_button;
                return context.getString(n);
            }
            case 2: {
                n = R.string.common_google_play_services_update_button;
                return context.getString(n);
            }
            case 1: 
        }
        n = R.string.common_google_play_services_install_button;
        return context.getString(n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Nullable
    private static String zzu(Context object, String string2) {
        SimpleArrayMap<String, String> simpleArrayMap = zzaEg;
        synchronized (simpleArrayMap) {
            String string3 = zzaEg.get(string2);
            if (string3 != null) {
                return string3;
            }
            if ((object = GooglePlayServicesUtil.getRemoteResource((Context)object)) == null) {
                return null;
            }
            int n = object.getIdentifier(string2, "string", "com.google.android.gms");
            if (n == 0) {
                object = String.valueOf(string2);
                object = object.length() != 0 ? "Missing resource: ".concat((String)object) : new String("Missing resource: ");
                Log.w((String)"GoogleApiAvailability", (String)object);
                return null;
            }
            if (!TextUtils.isEmpty((CharSequence)(object = object.getString(n)))) {
                zzaEg.put(string2, (String)object);
                return object;
            }
            object = String.valueOf(string2);
            object = object.length() != 0 ? "Got empty resource: ".concat((String)object) : new String("Got empty resource: ");
            Log.w((String)"GoogleApiAvailability", (String)object);
            return null;
        }
    }
}
