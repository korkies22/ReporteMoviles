/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.google.android.gms.tagmanager;

import android.net.Uri;
import com.google.android.gms.tagmanager.zzbo;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

class zzcj {
    private static zzcj zzbFb;
    private volatile String zzbCS;
    private volatile zza zzbFc;
    private volatile String zzbFd;
    private volatile String zzbFe;

    zzcj() {
        this.clear();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static zzcj zzPz() {
        synchronized (zzcj.class) {
            if (zzbFb != null) return zzbFb;
            zzbFb = new zzcj();
            return zzbFb;
        }
    }

    private String zzhq(String string) {
        return string.split("&")[0].split("=")[1];
    }

    private String zzw(Uri uri) {
        return uri.getQuery().replace("&gtm_debug=x", "");
    }

    void clear() {
        this.zzbFc = zza.zzbFf;
        this.zzbFd = null;
        this.zzbCS = null;
        this.zzbFe = null;
    }

    String getContainerId() {
        return this.zzbCS;
    }

    zza zzPA() {
        return this.zzbFc;
    }

    String zzPB() {
        return this.zzbFd;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    boolean zzv(Uri object) {
        synchronized (this) {
            String string;
            block8 : {
                Object object2;
                try {
                    string = URLDecoder.decode(object.toString(), "UTF-8");
                    if (!string.matches("^tagmanager.c.\\S+:\\/\\/preview\\/p\\?id=\\S+&gtm_auth=\\S+&gtm_preview=\\d+(&gtm_debug=x)?$")) break block8;
                    object2 = String.valueOf(string);
                    object2 = object2.length() != 0 ? "Container preview url: ".concat((String)object2) : new String("Container preview url: ");
                }
                catch (UnsupportedEncodingException unsupportedEncodingException) {}
                zzbo.v(object2);
                object2 = string.matches(".*?&gtm_debug=x$") ? zza.zzbFh : zza.zzbFg;
                this.zzbFc = object2;
                this.zzbFe = this.zzw((Uri)object);
                if (this.zzbFc == zza.zzbFg || this.zzbFc == zza.zzbFh) {
                    object = String.valueOf("/r?");
                    object2 = String.valueOf(this.zzbFe);
                    object = object2.length() != 0 ? object.concat((String)object2) : new String((String)object);
                    this.zzbFd = object;
                }
                this.zzbCS = this.zzhq(this.zzbFe);
                return true;
            }
            if (string.matches("^tagmanager.c.\\S+:\\/\\/preview\\/p\\?id=\\S+&gtm_preview=$")) {
                if (!this.zzhq(object.getQuery()).equals(this.zzbCS)) {
                    return false;
                }
                object = String.valueOf(this.zzbCS);
                object = object.length() != 0 ? "Exit preview mode for container: ".concat((String)object) : new String("Exit preview mode for container: ");
                zzbo.v((String)object);
                this.zzbFc = zza.zzbFf;
                this.zzbFd = null;
                return true;
            }
            object = String.valueOf(string);
            object = object.length() != 0 ? "Invalid preview uri: ".concat((String)object) : new String("Invalid preview uri: ");
            zzbo.zzbe((String)object);
            return false;
            return false;
        }
    }

    static final class zza
    extends Enum<zza> {
        public static final /* enum */ zza zzbFf = new zza();
        public static final /* enum */ zza zzbFg = new zza();
        public static final /* enum */ zza zzbFh = new zza();
        private static final /* synthetic */ zza[] zzbFi;

        static {
            zzbFi = new zza[]{zzbFf, zzbFg, zzbFh};
        }

        private zza() {
        }

        public static zza[] values() {
            return (zza[])zzbFi.clone();
        }
    }

}
