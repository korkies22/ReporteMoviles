/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.ConnectivityManager
 *  android.net.NetworkInfo
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import com.google.android.gms.tagmanager.zzad;
import com.google.android.gms.tagmanager.zzas;
import com.google.android.gms.tagmanager.zzbo;
import com.google.android.gms.tagmanager.zzbt;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Locale;

class zzdf
implements zzad {
    private final Context mContext;
    private final String zzHY;
    private final zzb zzbGt;
    private final zza zzbGu;

    zzdf(Context context, zza zza2) {
        this(new zzb(){

            @Override
            public HttpURLConnection zzd(URL uRL) throws IOException {
                return (HttpURLConnection)uRL.openConnection();
            }
        }, context, zza2);
    }

    zzdf(zzb zzb2, Context context, zza zza2) {
        this.zzbGt = zzb2;
        this.mContext = context.getApplicationContext();
        this.zzbGu = zza2;
        this.zzHY = this.zza("GoogleTagManager", "4.00", Build.VERSION.RELEASE, zzdf.zzc(Locale.getDefault()), Build.MODEL, Build.ID);
    }

    static String zzc(Locale locale) {
        if (locale == null) {
            return null;
        }
        if (locale.getLanguage() != null) {
            if (locale.getLanguage().length() == 0) {
                return null;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(locale.getLanguage().toLowerCase());
            if (locale.getCountry() != null && locale.getCountry().length() != 0) {
                stringBuilder.append("-");
                stringBuilder.append(locale.getCountry().toLowerCase());
            }
            return stringBuilder.toString();
        }
        return null;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void zzM(List<zzas> var1_1) {
        block9 : {
            var6_2 = Math.min(var1_1.size(), 40);
            var3_3 = 1;
            var5_4 = 0;
            while (var5_4 < var6_2) {
                block11 : {
                    block12 : {
                        block10 : {
                            var9_13 = var1_1.get(var5_4);
                            var7_7 = this.zzd(var9_13);
                            if (var7_7 != null) break block10;
                            zzbo.zzbe("No destination: discarding hit.");
                            this.zzbGu.zzb(var9_13);
                            var2_5 = var3_3;
                            break block11;
                        }
                        var4_6 = var3_3;
                        var10_14 = this.zzbGt.zzd((URL)var7_7);
                        var2_5 = var3_3;
                        if (var3_3 == 0) ** GOTO lbl21
                        zzbt.zzbK(this.mContext);
                        var2_5 = 0;
lbl21: // 2 sources:
                        var3_3 = var2_5;
                        var10_14.setRequestProperty("User-Agent", this.zzHY);
                        var3_3 = var2_5;
                        var4_6 = var10_14.getResponseCode();
                        var3_3 = var2_5;
                        var8_11 = var10_14.getInputStream();
                        if (var4_6 == 200) ** GOTO lbl35
                        var7_7 = new StringBuilder(25);
                        var7_7.append("Bad response: ");
                        var7_7.append(var4_6);
                        zzbo.zzbe(var7_7.toString());
                        this.zzbGu.zzc(var9_13);
                        break block12;
lbl35: // 1 sources:
                        this.zzbGu.zza(var9_13);
                    }
                    if (var8_11 == null) ** GOTO lbl41
                    var4_6 = var2_5;
                    try {
                        var8_11.close();
lbl41: // 2 sources:
                        var4_6 = var2_5;
                        var10_14.disconnect();
                    }
                    catch (IOException var8_12) {
                        var7_7 = String.valueOf(var8_12.getClass().getSimpleName());
                        var7_7 = var7_7.length() != 0 ? "Exception sending hit: ".concat((String)var7_7) : new String("Exception sending hit: ");
                        zzbo.zzbe((String)var7_7);
                        zzbo.zzbe(var8_12.getMessage());
                        this.zzbGu.zzc(var9_13);
                        var2_5 = var4_6;
                    }
                }
                ++var5_4;
                var3_3 = var2_5;
            }
            return;
            catch (Throwable var7_9) {
                var8_11 = null;
                var2_5 = var3_3;
                break block9;
            }
            catch (Throwable var7_10) {}
        }
        if (var8_11 != null) {
            var4_6 = var2_5;
            var8_11.close();
        }
        var4_6 = var2_5;
        var10_14.disconnect();
        var4_6 = var2_5;
        throw var7_8;
    }

    @Override
    public boolean zzPa() {
        NetworkInfo networkInfo = ((ConnectivityManager)this.mContext.getSystemService("connectivity")).getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        zzbo.v("...no network connectivity");
        return false;
    }

    String zza(String string, String string2, String string3, String string4, String string5, String string6) {
        return String.format("%s/%s (Linux; U; Android %s; %s; %s Build/%s)", string, string2, string3, string4, string5, string6);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    URL zzd(zzas object) {
        object = object.zzPk();
        try {
            return new URL((String)object);
        }
        catch (MalformedURLException malformedURLException) {}
        zzbo.e("Error trying to parse the GTM url.");
        return null;
    }

    public static interface zza {
        public void zza(zzas var1);

        public void zzb(zzas var1);

        public void zzc(zzas var1);
    }

    static interface zzb {
        public HttpURLConnection zzd(URL var1) throws IOException;
    }

}
