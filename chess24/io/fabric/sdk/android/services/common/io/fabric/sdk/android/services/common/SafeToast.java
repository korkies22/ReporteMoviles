/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.os.Handler
 *  android.os.Looper
 *  android.view.View
 *  android.widget.Toast
 */
package io.fabric.sdk.android.services.common;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;
import io.fabric.sdk.android.services.concurrency.PriorityRunnable;

public class SafeToast
extends Toast {
    public SafeToast(Context context) {
        super(context);
    }

    public static Toast makeText(Context context, int n, int n2) throws Resources.NotFoundException {
        return SafeToast.makeText(context, context.getResources().getText(n), n2);
    }

    public static Toast makeText(Context object, CharSequence charSequence, int n) {
        charSequence = Toast.makeText((Context)object, (CharSequence)charSequence, (int)n);
        object = new SafeToast((Context)object);
        object.setView(charSequence.getView());
        object.setDuration(charSequence.getDuration());
        return object;
    }

    public void show() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            super.show();
            return;
        }
        new Handler(Looper.getMainLooper()).post((Runnable)new PriorityRunnable(){

            @Override
            public void run() {
                SafeToast.super.show();
            }
        });
    }

}
