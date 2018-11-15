/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.Parcelable
 */
package com.google.android.gms.common;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import com.google.android.gms.common.internal.zzac;
import java.io.Serializable;
import java.util.ArrayList;

public final class AccountPicker {
    private AccountPicker() {
    }

    public static Intent newChooseAccountIntent(Account account, ArrayList<Account> arrayList, String[] arrstring, boolean bl, String string, String string2, String[] arrstring2, Bundle bundle) {
        return AccountPicker.zza(account, arrayList, arrstring, bl, string, string2, arrstring2, bundle, false);
    }

    public static Intent zza(Account account, ArrayList<Account> arrayList, String[] arrstring, boolean bl, String string, String string2, String[] arrstring2, Bundle bundle, boolean bl2) {
        return AccountPicker.zza(account, arrayList, arrstring, bl, string, string2, arrstring2, bundle, bl2, 0, 0);
    }

    public static Intent zza(Account account, ArrayList<Account> arrayList, String[] arrstring, boolean bl, String string, String string2, String[] arrstring2, Bundle bundle, boolean bl2, int n, int n2) {
        return AccountPicker.zza(account, arrayList, arrstring, bl, string, string2, arrstring2, bundle, bl2, n, n2, null, false);
    }

    public static Intent zza(Account account, ArrayList<Account> arrayList, String[] arrstring, boolean bl, String string, String string2, String[] arrstring2, Bundle bundle, boolean bl2, int n, int n2, String string3, boolean bl3) {
        Intent intent = new Intent();
        if (!bl3) {
            boolean bl4 = string3 == null;
            zzac.zzb(bl4, (Object)"We only support hostedDomain filter for account chip styled account picker");
        }
        String string4 = bl3 ? "com.google.android.gms.common.account.CHOOSE_ACCOUNT_USERTILE" : "com.google.android.gms.common.account.CHOOSE_ACCOUNT";
        intent.setAction(string4);
        intent.setPackage("com.google.android.gms");
        intent.putExtra("allowableAccounts", arrayList);
        intent.putExtra("allowableAccountTypes", arrstring);
        intent.putExtra("addAccountOptions", bundle);
        intent.putExtra("selectedAccount", (Parcelable)account);
        intent.putExtra("alwaysPromptForAccount", bl);
        intent.putExtra("descriptionTextOverride", string);
        intent.putExtra("authTokenType", string2);
        intent.putExtra("addAccountRequiredFeatures", arrstring2);
        intent.putExtra("setGmsCoreAccount", bl2);
        intent.putExtra("overrideTheme", n);
        intent.putExtra("overrideCustomTheme", n2);
        intent.putExtra("hostedDomainFilter", string3);
        return intent;
    }
}
