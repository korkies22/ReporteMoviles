/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.widget.TextView
 */
package de.cisha.android.board.playzone.remote.view;

import android.widget.TextView;
import de.cisha.android.board.playzone.remote.view.DisconnectedDeviceView;
import java.util.Timer;
import java.util.TimerTask;

private class DisconnectedDeviceView.UpdateTimeoutTask
extends TimerTask {
    private DisconnectedDeviceView.UpdateTimeoutTask() {
    }

    @Override
    public void run() {
        if (DisconnectedDeviceView.this._timeOut >= 0) {
            DisconnectedDeviceView.this._timeOutString = DisconnectedDeviceView.this.getMessageStringForSeconds(DisconnectedDeviceView.this._timeOut, DisconnectedDeviceView.this._abort);
            DisconnectedDeviceView.this.post(new Runnable(){

                @Override
                public void run() {
                    DisconnectedDeviceView.this._timoutView.setText((CharSequence)DisconnectedDeviceView.this._timeOutString);
                    DisconnectedDeviceView.access$110(DisconnectedDeviceView.this);
                }
            });
            return;
        }
        DisconnectedDeviceView.this._timer.cancel();
    }

}
