/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Message
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.Button
 */
package android.support.v7.app;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.widget.Button;

class AlertController
implements View.OnClickListener {
    AlertController() {
    }

    public void onClick(View object) {
        object = object == AlertController.this.mButtonPositive && AlertController.this.mButtonPositiveMessage != null ? Message.obtain((Message)AlertController.this.mButtonPositiveMessage) : (object == AlertController.this.mButtonNegative && AlertController.this.mButtonNegativeMessage != null ? Message.obtain((Message)AlertController.this.mButtonNegativeMessage) : (object == AlertController.this.mButtonNeutral && AlertController.this.mButtonNeutralMessage != null ? Message.obtain((Message)AlertController.this.mButtonNeutralMessage) : null));
        if (object != null) {
            object.sendToTarget();
        }
        AlertController.this.mHandler.obtainMessage(1, (Object)AlertController.this.mDialog).sendToTarget();
    }
}
