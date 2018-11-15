/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 */
package com.facebook.login;

import android.content.DialogInterface;
import com.facebook.internal.Utility;

class DeviceAuthDialog
implements DialogInterface.OnClickListener {
    final /* synthetic */ String val$accessToken;
    final /* synthetic */ Utility.PermissionsPair val$permissions;
    final /* synthetic */ String val$userId;

    DeviceAuthDialog(String string, Utility.PermissionsPair permissionsPair, String string2) {
        this.val$userId = string;
        this.val$permissions = permissionsPair;
        this.val$accessToken = string2;
    }

    public void onClick(DialogInterface dialogInterface, int n) {
        DeviceAuthDialog.this.completeLogin(this.val$userId, this.val$permissions, this.val$accessToken);
    }
}
