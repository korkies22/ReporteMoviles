/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.content.pm.PackageManager
 *  android.net.Uri
 *  android.os.Bundle
 */
package com.google.android.gms.tagmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.gms.tagmanager.TagManager;
import com.google.android.gms.tagmanager.zzbo;

public class PreviewActivity
extends Activity {
    private void zzl(String string, String string2, String string3) {
        AlertDialog alertDialog = new AlertDialog.Builder((Context)this).create();
        alertDialog.setTitle((CharSequence)string);
        alertDialog.setMessage((CharSequence)string2);
        alertDialog.setButton(-1, (CharSequence)string3, new DialogInterface.OnClickListener(this){

            public void onClick(DialogInterface dialogInterface, int n) {
            }
        });
        alertDialog.show();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onCreate(Bundle object) {
        try {
            Object object2;
            super.onCreate((Bundle)object);
            zzbo.zzbd("Preview activity");
            object = this.getIntent().getData();
            if (!TagManager.getInstance((Context)this).zzv((Uri)object)) {
                object = String.valueOf(object);
                object2 = new StringBuilder(73 + String.valueOf(object).length());
                object2.append("Cannot preview the app with the uri: ");
                object2.append((String)object);
                object2.append(". Launching current version instead.");
                object = object2.toString();
                zzbo.zzbe((String)object);
                this.zzl("Preview failure", (String)object, "Continue");
            }
            if ((object2 = this.getPackageManager().getLaunchIntentForPackage(this.getPackageName())) != null) {
                object = String.valueOf(this.getPackageName());
                object = object.length() != 0 ? "Invoke the launch activity for package name: ".concat((String)object) : new String("Invoke the launch activity for package name: ");
                zzbo.zzbd((String)object);
                this.startActivity((Intent)object2);
                return;
            }
            object = String.valueOf(this.getPackageName());
            object = object.length() != 0 ? "No launch activity found for package name: ".concat((String)object) : new String("No launch activity found for package name: ");
            zzbo.zzbd((String)object);
            return;
        }
        catch (Exception exception) {
            String string = String.valueOf(exception.getMessage());
            string = string.length() != 0 ? "Calling preview threw an exception: ".concat(string) : new String("Calling preview threw an exception: ");
            zzbo.e(string);
            return;
        }
    }

}
