/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.os.Handler
 *  android.os.Message
 */
package android.support.v7.app;

import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertController;
import java.lang.ref.WeakReference;

private static final class AlertController.ButtonHandler
extends Handler {
    private static final int MSG_DISMISS_DIALOG = 1;
    private WeakReference<DialogInterface> mDialog;

    public AlertController.ButtonHandler(DialogInterface dialogInterface) {
        this.mDialog = new WeakReference<DialogInterface>(dialogInterface);
    }

    public void handleMessage(Message message) {
        int n = message.what;
        if (n != 1) {
            switch (n) {
                default: {
                    return;
                }
                case -3: 
                case -2: 
                case -1: 
            }
            ((DialogInterface.OnClickListener)message.obj).onClick((DialogInterface)this.mDialog.get(), message.what);
            return;
        }
        ((DialogInterface)message.obj).dismiss();
    }
}
