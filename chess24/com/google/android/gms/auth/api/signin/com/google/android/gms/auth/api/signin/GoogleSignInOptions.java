/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
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
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.auth.api.signin.internal.zzf;
import com.google.android.gms.auth.api.signin.zzb;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzac;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GoogleSignInOptions
extends zza
implements Api.ApiOptions.Optional,
ReflectedParcelable {
    public static final Parcelable.Creator<GoogleSignInOptions> CREATOR;
    public static final GoogleSignInOptions DEFAULT_SIGN_IN;
    private static Comparator<Scope> zzajc;
    public static final Scope zzajd;
    public static final Scope zzaje;
    public static final Scope zzajf;
    final int versionCode;
    private Account zzagg;
    private final ArrayList<Scope> zzajg;
    private boolean zzajh;
    private final boolean zzaji;
    private final boolean zzajj;
    private String zzajk;
    private String zzajl;

    static {
        zzajd = new Scope("profile");
        zzaje = new Scope("email");
        zzajf = new Scope("openid");
        DEFAULT_SIGN_IN = new Builder().requestId().requestProfile().build();
        CREATOR = new zzb();
        zzajc = new Comparator<Scope>(){

            @Override
            public /* synthetic */ int compare(Object object, Object object2) {
                return this.zza((Scope)object, (Scope)object2);
            }

            public int zza(Scope scope, Scope scope2) {
                return scope.zzuS().compareTo(scope2.zzuS());
            }
        };
    }

    GoogleSignInOptions(int n, ArrayList<Scope> arrayList, Account account, boolean bl, boolean bl2, boolean bl3, String string, String string2) {
        this.versionCode = n;
        this.zzajg = arrayList;
        this.zzagg = account;
        this.zzajh = bl;
        this.zzaji = bl2;
        this.zzajj = bl3;
        this.zzajk = string;
        this.zzajl = string2;
    }

    private GoogleSignInOptions(Set<Scope> set, Account account, boolean bl, boolean bl2, boolean bl3, String string, String string2) {
        this(2, new ArrayList<Scope>(set), account, bl, bl2, bl3, string, string2);
    }

    @Nullable
    public static GoogleSignInOptions zzcw(@Nullable String string) throws JSONException {
        if (TextUtils.isEmpty((CharSequence)string)) {
            return null;
        }
        JSONObject jSONObject = new JSONObject(string);
        HashSet<Scope> hashSet = new HashSet<Scope>();
        string = jSONObject.getJSONArray("scopes");
        int n = string.length();
        for (int i = 0; i < n; ++i) {
            hashSet.add(new Scope(string.getString(i)));
        }
        string = jSONObject.optString("accountName", null);
        string = !TextUtils.isEmpty((CharSequence)string) ? new Account(string, "com.google") : null;
        return new GoogleSignInOptions(hashSet, (Account)string, jSONObject.getBoolean("idTokenRequested"), jSONObject.getBoolean("serverAuthRequested"), jSONObject.getBoolean("forceCodeForRefreshToken"), jSONObject.optString("serverClientId", null), jSONObject.optString("hostedDomain", null));
    }

    private JSONObject zzqI() {
        JSONObject jSONObject = new JSONObject();
        try {
            JSONArray jSONArray = new JSONArray();
            Collections.sort(this.zzajg, zzajc);
            Iterator<Scope> iterator = this.zzajg.iterator();
            while (iterator.hasNext()) {
                jSONArray.put((Object)iterator.next().zzuS());
            }
            jSONObject.put("scopes", (Object)jSONArray);
            if (this.zzagg != null) {
                jSONObject.put("accountName", (Object)this.zzagg.name);
            }
            jSONObject.put("idTokenRequested", this.zzajh);
            jSONObject.put("forceCodeForRefreshToken", this.zzajj);
            jSONObject.put("serverAuthRequested", this.zzaji);
            if (!TextUtils.isEmpty((CharSequence)this.zzajk)) {
                jSONObject.put("serverClientId", (Object)this.zzajk);
            }
            if (!TextUtils.isEmpty((CharSequence)this.zzajl)) {
                jSONObject.put("hostedDomain", (Object)this.zzajl);
            }
            return jSONObject;
        }
        catch (JSONException jSONException) {
            throw new RuntimeException((Throwable)jSONException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean equals(Object object) {
        boolean bl = false;
        if (object == null) {
            return false;
        }
        try {
            object = (GoogleSignInOptions)object;
            boolean bl2 = bl;
            if (this.zzajg.size() != object.zzqJ().size()) return bl2;
            if (!this.zzajg.containsAll(object.zzqJ())) {
                return false;
            }
            if (this.zzagg == null) {
                bl2 = bl;
                if (object.getAccount() != null) return bl2;
            } else {
                bl2 = bl;
                if (!this.zzagg.equals((Object)object.getAccount())) return bl2;
            }
            if (TextUtils.isEmpty((CharSequence)this.zzajk)) {
                bl2 = bl;
                if (!TextUtils.isEmpty((CharSequence)object.zzqN())) return bl2;
            } else {
                bl2 = bl;
                if (!this.zzajk.equals(object.zzqN())) return bl2;
            }
            bl2 = bl;
            if (this.zzajj != object.zzqM()) return bl2;
            bl2 = bl;
            if (this.zzajh != object.zzqK()) return bl2;
            boolean bl3 = this.zzaji;
            boolean bl4 = object.zzqL();
            bl2 = bl;
            if (bl3 != bl4) return bl2;
            return true;
        }
        catch (ClassCastException classCastException) {
            return false;
        }
    }

    public Account getAccount() {
        return this.zzagg;
    }

    public Scope[] getScopeArray() {
        return this.zzajg.toArray(new Scope[this.zzajg.size()]);
    }

    public int hashCode() {
        ArrayList<String> arrayList = new ArrayList<String>();
        Iterator<Scope> iterator = this.zzajg.iterator();
        while (iterator.hasNext()) {
            arrayList.add(iterator.next().zzuS());
        }
        Collections.sort(arrayList);
        return new zzf().zzq(arrayList).zzq((Object)this.zzagg).zzq(this.zzajk).zzad(this.zzajj).zzad(this.zzajh).zzad(this.zzaji).zzqV();
    }

    public void writeToParcel(Parcel parcel, int n) {
        zzb.zza(this, parcel, n);
    }

    public String zzqG() {
        return this.zzqI().toString();
    }

    public ArrayList<Scope> zzqJ() {
        return new ArrayList<Scope>(this.zzajg);
    }

    public boolean zzqK() {
        return this.zzajh;
    }

    public boolean zzqL() {
        return this.zzaji;
    }

    public boolean zzqM() {
        return this.zzajj;
    }

    public String zzqN() {
        return this.zzajk;
    }

    public String zzqO() {
        return this.zzajl;
    }

    public static final class Builder {
        private Account zzagg;
        private boolean zzajh;
        private boolean zzaji;
        private boolean zzajj;
        private String zzajk;
        private String zzajl;
        private Set<Scope> zzajm = new HashSet<Scope>();

        public Builder() {
        }

        public Builder(@NonNull GoogleSignInOptions googleSignInOptions) {
            zzac.zzw(googleSignInOptions);
            this.zzajm = new HashSet<Scope>(googleSignInOptions.zzajg);
            this.zzaji = googleSignInOptions.zzaji;
            this.zzajj = googleSignInOptions.zzajj;
            this.zzajh = googleSignInOptions.zzajh;
            this.zzajk = googleSignInOptions.zzajk;
            this.zzagg = googleSignInOptions.zzagg;
            this.zzajl = googleSignInOptions.zzajl;
        }

        private String zzcx(String string) {
            zzac.zzdv(string);
            boolean bl = this.zzajk == null || this.zzajk.equals(string);
            zzac.zzb(bl, (Object)"two different server client ids provided");
            return string;
        }

        public GoogleSignInOptions build() {
            if (this.zzajh && (this.zzagg == null || !this.zzajm.isEmpty())) {
                this.requestId();
            }
            return new GoogleSignInOptions(this.zzajm, this.zzagg, this.zzajh, this.zzaji, this.zzajj, this.zzajk, this.zzajl);
        }

        public Builder requestEmail() {
            this.zzajm.add(GoogleSignInOptions.zzaje);
            return this;
        }

        public Builder requestId() {
            this.zzajm.add(GoogleSignInOptions.zzajf);
            return this;
        }

        public Builder requestIdToken(String string) {
            this.zzajh = true;
            this.zzajk = this.zzcx(string);
            return this;
        }

        public Builder requestProfile() {
            this.zzajm.add(GoogleSignInOptions.zzajd);
            return this;
        }

        public /* varargs */ Builder requestScopes(Scope scope, Scope ... arrscope) {
            this.zzajm.add(scope);
            this.zzajm.addAll(Arrays.asList(arrscope));
            return this;
        }

        public Builder requestServerAuthCode(String string) {
            return this.requestServerAuthCode(string, false);
        }

        public Builder requestServerAuthCode(String string, boolean bl) {
            this.zzaji = true;
            this.zzajk = this.zzcx(string);
            this.zzajj = bl;
            return this;
        }

        public Builder setAccountName(String string) {
            this.zzagg = new Account(zzac.zzdv(string), "com.google");
            return this;
        }

        public Builder setHostedDomain(String string) {
            this.zzajl = zzac.zzdv(string);
            return this;
        }
    }

}
