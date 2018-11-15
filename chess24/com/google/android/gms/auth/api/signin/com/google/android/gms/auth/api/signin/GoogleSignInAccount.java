/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.net.Uri
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.google.android.gms.auth.api.signin;

import android.accounts.Account;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.auth.api.signin.zza;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.util.zzh;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GoogleSignInAccount
extends com.google.android.gms.common.internal.safeparcel.zza
implements ReflectedParcelable {
    public static final Parcelable.Creator<GoogleSignInAccount> CREATOR = new zza();
    public static zze zzaiV = zzh.zzyv();
    private static Comparator<Scope> zzajc = new Comparator<Scope>(){

        @Override
        public /* synthetic */ int compare(Object object, Object object2) {
            return this.zza((Scope)object, (Scope)object2);
        }

        public int zza(Scope scope, Scope scope2) {
            return scope.zzuS().compareTo(scope2.zzuS());
        }
    };
    final int versionCode;
    private String zzGu;
    List<Scope> zzahM;
    private String zzaiW;
    private String zzaiX;
    private Uri zzaiY;
    private String zzaiZ;
    private String zzaik;
    private String zzail;
    private String zzaix;
    private long zzaja;
    private String zzajb;

    GoogleSignInAccount(int n, String string, String string2, String string3, String string4, Uri uri, String string5, long l, String string6, List<Scope> list, String string7, String string8) {
        this.versionCode = n;
        this.zzGu = string;
        this.zzaix = string2;
        this.zzaiW = string3;
        this.zzaiX = string4;
        this.zzaiY = uri;
        this.zzaiZ = string5;
        this.zzaja = l;
        this.zzajb = string6;
        this.zzahM = list;
        this.zzaik = string7;
        this.zzail = string8;
    }

    public static GoogleSignInAccount zza(@Nullable String string, @Nullable String string2, @Nullable String string3, @Nullable String string4, @Nullable String string5, @Nullable String string6, @Nullable Uri uri, @Nullable Long l, @NonNull String string7, @NonNull Set<Scope> set) {
        if (l == null) {
            l = zzaiV.currentTimeMillis() / 1000L;
        }
        return new GoogleSignInAccount(3, string, string2, string3, string4, uri, null, l, zzac.zzdv(string7), new ArrayList<Scope>((Collection)zzac.zzw(set)), string5, string6);
    }

    @Nullable
    public static GoogleSignInAccount zzcu(@Nullable String string) throws JSONException {
        if (TextUtils.isEmpty((CharSequence)string)) {
            return null;
        }
        JSONObject jSONObject = new JSONObject(string);
        string = jSONObject.optString("photoUrl", null);
        string = !TextUtils.isEmpty((CharSequence)string) ? Uri.parse((String)string) : null;
        long l = Long.parseLong(jSONObject.getString("expirationTime"));
        HashSet<Scope> hashSet = new HashSet<Scope>();
        JSONArray jSONArray = jSONObject.getJSONArray("grantedScopes");
        int n = jSONArray.length();
        for (int i = 0; i < n; ++i) {
            hashSet.add(new Scope(jSONArray.getString(i)));
        }
        return GoogleSignInAccount.zza(jSONObject.optString("id"), jSONObject.optString("tokenId", null), jSONObject.optString("email", null), jSONObject.optString("displayName", null), jSONObject.optString("givenName", null), jSONObject.optString("familyName", null), (Uri)string, l, jSONObject.getString("obfuscatedIdentifier"), hashSet).zzcv(jSONObject.optString("serverAuthCode", null));
    }

    private JSONObject zzqI() {
        JSONObject jSONObject = new JSONObject();
        try {
            if (this.getId() != null) {
                jSONObject.put("id", (Object)this.getId());
            }
            if (this.getIdToken() != null) {
                jSONObject.put("tokenId", (Object)this.getIdToken());
            }
            if (this.getEmail() != null) {
                jSONObject.put("email", (Object)this.getEmail());
            }
            if (this.getDisplayName() != null) {
                jSONObject.put("displayName", (Object)this.getDisplayName());
            }
            if (this.getGivenName() != null) {
                jSONObject.put("givenName", (Object)this.getGivenName());
            }
            if (this.getFamilyName() != null) {
                jSONObject.put("familyName", (Object)this.getFamilyName());
            }
            if (this.getPhotoUrl() != null) {
                jSONObject.put("photoUrl", (Object)this.getPhotoUrl().toString());
            }
            if (this.getServerAuthCode() != null) {
                jSONObject.put("serverAuthCode", (Object)this.getServerAuthCode());
            }
            jSONObject.put("expirationTime", this.zzaja);
            jSONObject.put("obfuscatedIdentifier", (Object)this.zzqF());
            JSONArray jSONArray = new JSONArray();
            Collections.sort(this.zzahM, zzajc);
            Iterator<Scope> iterator = this.zzahM.iterator();
            while (iterator.hasNext()) {
                jSONArray.put((Object)iterator.next().zzuS());
            }
            jSONObject.put("grantedScopes", (Object)jSONArray);
            return jSONObject;
        }
        catch (JSONException jSONException) {
            throw new RuntimeException((Throwable)jSONException);
        }
    }

    public boolean equals(Object object) {
        if (!(object instanceof GoogleSignInAccount)) {
            return false;
        }
        return ((GoogleSignInAccount)object).zzqG().equals(this.zzqG());
    }

    @Nullable
    public Account getAccount() {
        if (this.zzaiW == null) {
            return null;
        }
        return new Account(this.zzaiW, "com.google");
    }

    @Nullable
    public String getDisplayName() {
        return this.zzaiX;
    }

    @Nullable
    public String getEmail() {
        return this.zzaiW;
    }

    @Nullable
    public String getFamilyName() {
        return this.zzail;
    }

    @Nullable
    public String getGivenName() {
        return this.zzaik;
    }

    @NonNull
    public Set<Scope> getGrantedScopes() {
        return new HashSet<Scope>(this.zzahM);
    }

    @Nullable
    public String getId() {
        return this.zzGu;
    }

    @Nullable
    public String getIdToken() {
        return this.zzaix;
    }

    @Nullable
    public Uri getPhotoUrl() {
        return this.zzaiY;
    }

    @Nullable
    public String getServerAuthCode() {
        return this.zzaiZ;
    }

    public int hashCode() {
        return this.zzqG().hashCode();
    }

    public void writeToParcel(Parcel parcel, int n) {
        zza.zza(this, parcel, n);
    }

    public boolean zza() {
        if (zzaiV.currentTimeMillis() / 1000L >= this.zzaja - 300L) {
            return true;
        }
        return false;
    }

    public GoogleSignInAccount zzcv(String string) {
        this.zzaiZ = string;
        return this;
    }

    public long zzqE() {
        return this.zzaja;
    }

    @NonNull
    public String zzqF() {
        return this.zzajb;
    }

    public String zzqG() {
        return this.zzqI().toString();
    }

    public String zzqH() {
        JSONObject jSONObject = this.zzqI();
        jSONObject.remove("serverAuthCode");
        return jSONObject.toString();
    }

}
