/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package de.cisha.android.board.playzone.remote.view;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Timer;
import java.util.TimerTask;

public class DisconnectedDeviceView
extends LinearLayout {
    private static final String TIMER_NAME = "disconnect";
    private boolean _abort = false;
    private int _timeOut;
    private String _timeOutString;
    private Timer _timer;
    private TextView _timoutView;

    public DisconnectedDeviceView(Context context) {
        super(context);
        this.initLayout();
    }

    public DisconnectedDeviceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.initLayout();
    }

    static /* synthetic */ int access$110(DisconnectedDeviceView disconnectedDeviceView) {
        int n = disconnectedDeviceView._timeOut;
        disconnectedDeviceView._timeOut = n - 1;
        return n;
    }

    private void initLayout() {
        this.setOrientation(0);
        DisconnectedDeviceView.inflate((Context)this.getContext(), (int)2131427493, (ViewGroup)this);
        this._timoutView = (TextView)this.findViewById(2131296714);
    }

    protected String getMessageStringForSeconds(int n, boolean bl) {
        if (!bl) {
            Resources resources = this.getContext().getResources();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(n);
            return resources.getString(2131690177, new Object[]{stringBuilder.toString()});
        }
        Resources resources = this.getContext().getResources();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(n);
        return resources.getString(2131690176, new Object[]{stringBuilder.toString()});
    }

    public void setGameWillAborted(boolean bl) {
        this._abort = bl;
    }

    public void setTimeout(int n) {
        this._timeOut = n;
        if (this._timer != null) {
            this._timer.cancel();
        }
        this._timer = new Timer(TIMER_NAME);
        this._timer.schedule((TimerTask)new UpdateTimeoutTask(), 0L, 1000L);
    }

    private class UpdateTimeoutTask
    extends TimerTask {
        private UpdateTimeoutTask() {
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

}
