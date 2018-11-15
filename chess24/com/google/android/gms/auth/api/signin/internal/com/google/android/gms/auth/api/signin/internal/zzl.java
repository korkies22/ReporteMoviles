/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.text.TextUtils
 *  org.json.JSONException
 */
package com.google.android.gms.auth.api.signin.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.internal.zzac;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.json.JSONException;

public class zzl {
    private static final Lock zzajI = new ReentrantLock();
    private static zzl zzajJ;
    private final Lock zzajK = new ReentrantLock();
    private final SharedPreferences zzajL;

    zzl(Context context) {
        this.zzajL = context.getSharedPreferences("com.google.android.gms.signin", 0);
    }

    public static zzl zzaa(Context object) {
        zzac.zzw(object);
        zzajI.lock();
        try {
            if (zzajJ == null) {
                zzajJ = new zzl(object.getApplicationContext());
            }
            object = zzajJ;
            return object;
        }
        finally {
            zzajI.unlock();
        }
    }

    private String zzx(String string, String string2) {
        String string3 = String.valueOf(":");
        StringBuilder stringBuilder = new StringBuilder(0 + String.valueOf(string).length() + String.valueOf(string3).length() + String.valueOf(string2).length());
        stringBuilder.append(string);
        stringBuilder.append(string3);
        stringBuilder.append(string2);
        return stringBuilder.toString();
    }

    void zza(GoogleSignInAccount googleSignInAccount, GoogleSignInOptions googleSignInOptions) {
        zzac.zzw(googleSignInAccount);
        zzac.zzw(googleSignInOptions);
        String string = googleSignInAccount.zzqF();
        this.zzw(this.zzx("googleSignInAccount", string), googleSignInAccount.zzqH());
        this.zzw(this.zzx("googleSignInOptions", string), googleSignInOptions.zzqG());
    }

    public void zzb(GoogleSignInAccount googleSignInAccount, GoogleSignInOptions googleSignInOptions) {
        zzac.zzw(googleSignInAccount);
        zzac.zzw(googleSignInOptions);
        this.zzw("defaultGoogleSignInAccount", googleSignInAccount.zzqF());
        this.zza(googleSignInAccount, googleSignInOptions);
    }

    protected String zzcA(String string) {
        this.zzajK.lock();
        try {
            string = this.zzajL.getString(string, null);
            return string;
        }
        finally {
            this.zzajK.unlock();
        }
    }

    void zzcB(String string) {
        if (TextUtils.isEmpty((CharSequence)string)) {
            return;
        }
        this.zzcC(this.zzx("googleSignInAccount", string));
        this.zzcC(this.zzx("googleSignInOptions", string));
    }

    protected void zzcC(String string) {
        this.zzajK.lock();
        try {
            this.zzajL.edit().remove(string).apply();
            return;
        }
        finally {
            this.zzajK.unlock();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    GoogleSignInAccount zzcy(String object) {
        if (TextUtils.isEmpty((CharSequence)object)) {
            return null;
        }
        if ((object = this.zzcA(this.zzx("googleSignInAccount", (String)object))) == null) return null;
        try {
            return GoogleSignInAccount.zzcu((String)object);
        }
        catch (JSONException jSONException) {
            return null;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    GoogleSignInOptions zzcz(String object) {
        if (TextUtils.isEmpty((CharSequence)object)) {
            return null;
        }
        if ((object = this.zzcA(this.zzx("googleSignInOptions", (String)object))) == null) return null;
        try {
            return GoogleSignInOptions.zzcw((String)object);
        }
        catch (JSONException jSONException) {
            return null;
        }
    }

    public GoogleSignInAccount zzrc() {
        return this.zzcy(this.zzcA("defaultGoogleSignInAccount"));
    }

    public GoogleSignInOptions zzrd() {
        return this.zzcz(this.zzcA("defaultGoogleSignInAccount"));
    }

    public void zzre() {
        String string = this.zzcA("defaultGoogleSignInAccount");
        this.zzcC("defaultGoogleSignInAccount");
        this.zzcB(string);
    }

    protected void zzw(String string, String string2) {
        this.zzajK.lock();
        try {
            this.zzajL.edit().putString(string, string2).apply();
            return;
        }
        finally {
            this.zzajK.unlock();
        }
    }
}
