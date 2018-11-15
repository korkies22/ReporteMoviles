/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.auth.api.signin;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import java.io.Serializable;
import java.util.List;

public class zza
implements Parcelable.Creator<GoogleSignInAccount> {
    static void zza(GoogleSignInAccount googleSignInAccount, Parcel parcel, int n) {
        int n2 = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, googleSignInAccount.versionCode);
        zzc.zza(parcel, 2, googleSignInAccount.getId(), false);
        zzc.zza(parcel, 3, googleSignInAccount.getIdToken(), false);
        zzc.zza(parcel, 4, googleSignInAccount.getEmail(), false);
        zzc.zza(parcel, 5, googleSignInAccount.getDisplayName(), false);
        zzc.zza(parcel, 6, (Parcelable)googleSignInAccount.getPhotoUrl(), n, false);
        zzc.zza(parcel, 7, googleSignInAccount.getServerAuthCode(), false);
        zzc.zza(parcel, 8, googleSignInAccount.zzqE());
        zzc.zza(parcel, 9, googleSignInAccount.zzqF(), false);
        zzc.zzc(parcel, 10, googleSignInAccount.zzahM, false);
        zzc.zza(parcel, 11, googleSignInAccount.getGivenName(), false);
        zzc.zza(parcel, 12, googleSignInAccount.getFamilyName(), false);
        zzc.zzJ(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzV(parcel);
    }

    public /* synthetic */ Object[] newArray(int n) {
        return this.zzbj(n);
    }

    public GoogleSignInAccount zzV(Parcel parcel) {
        Serializable serializable;
        CharSequence charSequence;
        CharSequence charSequence2;
        StringBuilder stringBuilder;
        CharSequence charSequence3;
        CharSequence charSequence4;
        CharSequence charSequence5;
        int n = zzb.zzaU(parcel);
        CharSequence charSequence6 = null;
        CharSequence charSequence7 = charSequence6;
        CharSequence charSequence8 = charSequence3 = (serializable = (charSequence5 = (charSequence2 = (stringBuilder = (charSequence4 = (charSequence = charSequence7))))));
        int n2 = 0;
        long l = 0L;
        block14 : while (parcel.dataPosition() < n) {
            int n3 = zzb.zzaT(parcel);
            switch (zzb.zzcW(n3)) {
                default: {
                    zzb.zzb(parcel, n3);
                    continue block14;
                }
                case 12: {
                    charSequence8 = zzb.zzq(parcel, n3);
                    continue block14;
                }
                case 11: {
                    charSequence3 = zzb.zzq(parcel, n3);
                    continue block14;
                }
                case 10: {
                    serializable = zzb.zzc(parcel, n3, Scope.CREATOR);
                    continue block14;
                }
                case 9: {
                    charSequence5 = zzb.zzq(parcel, n3);
                    continue block14;
                }
                case 8: {
                    l = zzb.zzi(parcel, n3);
                    continue block14;
                }
                case 7: {
                    charSequence2 = zzb.zzq(parcel, n3);
                    continue block14;
                }
                case 6: {
                    stringBuilder = (Uri)zzb.zza(parcel, n3, Uri.CREATOR);
                    continue block14;
                }
                case 5: {
                    charSequence4 = zzb.zzq(parcel, n3);
                    continue block14;
                }
                case 4: {
                    charSequence = zzb.zzq(parcel, n3);
                    continue block14;
                }
                case 3: {
                    charSequence7 = zzb.zzq(parcel, n3);
                    continue block14;
                }
                case 2: {
                    charSequence6 = zzb.zzq(parcel, n3);
                    continue block14;
                }
                case 1: 
            }
            n2 = zzb.zzg(parcel, n3);
        }
        if (parcel.dataPosition() != n) {
            charSequence7 = new StringBuilder(37);
            charSequence7.append("Overread allowed size end=");
            charSequence7.append(n);
            throw new zzb.zza(((StringBuilder)charSequence7).toString(), parcel);
        }
        return new GoogleSignInAccount(n2, (String)charSequence6, (String)charSequence7, (String)charSequence, (String)charSequence4, (Uri)stringBuilder, (String)charSequence2, l, (String)charSequence5, (List<Scope>)((Object)serializable), (String)charSequence3, (String)charSequence8);
    }

    public GoogleSignInAccount[] zzbj(int n) {
        return new GoogleSignInAccount[n];
    }
}
