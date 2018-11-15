/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.text.TextUtils
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.text.TextUtils;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.internal.zzru;
import com.google.android.gms.internal.zzrw;
import com.google.android.gms.internal.zzse;
import com.google.android.gms.internal.zztd;
import com.google.android.gms.internal.zztg;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Locale;

public class zzrr
extends zzru {
    public static boolean zzacz;
    private AdvertisingIdClient.Info zzacA;
    private final zztd zzacB;
    private String zzacC;
    private boolean zzacD = false;
    private Object zzacE = new Object();

    zzrr(zzrw zzrw2) {
        super(zzrw2);
        this.zzacB = new zztd(zzrw2.zznq());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean zza(AdvertisingIdClient.Info object, AdvertisingIdClient.Info object2) {
        String string = null;
        object2 = object2 == null ? null : object2.getId();
        if (TextUtils.isEmpty((CharSequence)object2)) {
            return true;
        }
        String string2 = this.zznw().zzop();
        Object object3 = this.zzacE;
        synchronized (object3) {
            if (!this.zzacD) {
                this.zzacC = this.zzni();
                this.zzacD = true;
            } else if (TextUtils.isEmpty((CharSequence)this.zzacC)) {
                object = object == null ? string : object.getId();
                if (object == null) {
                    object = String.valueOf(object2);
                    object2 = String.valueOf(string2);
                    object = object2.length() != 0 ? object.concat((String)object2) : new String((String)object);
                    return this.zzbN((String)object);
                }
                object = String.valueOf(object);
                string = String.valueOf(string2);
                object = string.length() != 0 ? object.concat(string) : new String((String)object);
                this.zzacC = zzrr.zzbM((String)object);
            }
            object = String.valueOf(object2);
            string = String.valueOf(string2);
            object = string.length() != 0 ? object.concat(string) : new String((String)object);
            object = zzrr.zzbM((String)object);
            if (TextUtils.isEmpty((CharSequence)object)) {
                return false;
            }
            if (object.equals(this.zzacC)) {
                return true;
            }
            object = string2;
            if (!TextUtils.isEmpty((CharSequence)this.zzacC)) {
                this.zzbO("Resetting the client id because Advertising Id changed.");
                object = this.zznw().zzoq();
                this.zza("New client Id", object);
            }
            object2 = String.valueOf(object2);
            object = (object = String.valueOf(object)).length() != 0 ? object2.concat((String)object) : new String((String)object2);
            return this.zzbN((String)object);
        }
    }

    private static String zzbM(String string) {
        MessageDigest messageDigest = zztg.zzcg("MD5");
        if (messageDigest == null) {
            return null;
        }
        return String.format(Locale.US, "%032X", new BigInteger(1, messageDigest.digest(string.getBytes())));
    }

    private boolean zzbN(String string) {
        try {
            string = zzrr.zzbM(string);
            this.zzbO("Storing hashed adid.");
            FileOutputStream fileOutputStream = this.getContext().openFileOutput("gaClientIdData", 0);
            fileOutputStream.write(string.getBytes());
            fileOutputStream.close();
            this.zzacC = string;
            return true;
        }
        catch (IOException iOException) {
            this.zze("Error creating hash file", iOException);
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private AdvertisingIdClient.Info zzng() {
        synchronized (this) {
            if (!this.zzacB.zzz(1000L)) return this.zzacA;
            this.zzacB.start();
            AdvertisingIdClient.Info info = this.zznh();
            if (!this.zza(this.zzacA, info)) {
                this.zzbS("Failed to reset client id on adid change. Not using adid");
                info = new AdvertisingIdClient.Info("", false);
            }
            this.zzacA = info;
            return this.zzacA;
        }
    }

    public boolean zzmV() {
        this.zznA();
        AdvertisingIdClient.Info info = this.zzng();
        if (info != null) {
            return info.isLimitAdTrackingEnabled() ^ true;
        }
        return false;
    }

    @Override
    protected void zzmr() {
    }

    public String zznf() {
        this.zznA();
        Object object = this.zzng();
        object = object != null ? object.getId() : null;
        if (TextUtils.isEmpty((CharSequence)object)) {
            return null;
        }
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected AdvertisingIdClient.Info zznh() {
        try {
            return AdvertisingIdClient.getAdvertisingIdInfo(this.getContext());
        }
        catch (Throwable throwable) {
            if (zzacz) return null;
            zzacz = true;
            this.zzd("Error getting advertiser id", throwable);
            return null;
        }
        catch (IllegalStateException illegalStateException) {}
        this.zzbR("IllegalStateException getting Ad Id Info. If you would like to see Audience reports, please ensure that you have added '<meta-data android:name=\"com.google.android.gms.version\" android:value=\"@integer/google_play_services_version\" />' to your application manifest file. See http://goo.gl/naFqQk for details.");
        return null;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected String zzni() {
        void var2_3;
        void var3_12;
        String string;
        block8 : {
            Object var2_1 = null;
            FileInputStream fileInputStream = this.getContext().openFileInput("gaClientIdData");
            byte[] arrby = new byte[128];
            int n = fileInputStream.read(arrby, 0, 128);
            if (fileInputStream.available() > 0) {
                this.zzbR("Hash file seems corrupted, deleting it.");
                fileInputStream.close();
                this.getContext().deleteFile("gaClientIdData");
                return null;
            }
            if (n <= 0) {
                this.zzbO("Hash file is empty.");
                fileInputStream.close();
                return null;
            }
            string = new String(arrby, 0, n);
            try {
                fileInputStream.close();
                return string;
            }
            catch (IOException iOException) {
                String string2 = string;
                IOException iOException2 = iOException;
                break block8;
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        this.zzd("Error reading Hash file, deleting it", var3_12);
        this.getContext().deleteFile("gaClientIdData");
        return var2_3;
        catch (FileNotFoundException fileNotFoundException) {
            return null;
        }
        catch (FileNotFoundException fileNotFoundException) {
            return string;
        }
    }
}
