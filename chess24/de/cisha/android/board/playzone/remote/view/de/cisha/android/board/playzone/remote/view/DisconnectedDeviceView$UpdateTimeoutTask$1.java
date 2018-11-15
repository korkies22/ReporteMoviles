/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.widget.TextView
 */
package de.cisha.android.board.playzone.remote.view;

import android.widget.TextView;
import de.cisha.android.board.playzone.remote.view.DisconnectedDeviceView;

class DisconnectedDeviceView.UpdateTimeoutTask
implements Runnable {
    DisconnectedDeviceView.UpdateTimeoutTask() {
    }

    @Override
    public void run() {
        UpdateTimeoutTask.this.this$0._timoutView.setText((CharSequence)UpdateTimeoutTask.this.this$0._timeOutString);
        DisconnectedDeviceView.access$110(UpdateTimeoutTask.this.this$0);
    }
}
