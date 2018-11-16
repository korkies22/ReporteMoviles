// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.remote.view;

import java.util.TimerTask;
import android.content.res.Resources;
import android.view.ViewGroup;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.TextView;
import java.util.Timer;
import android.widget.LinearLayout;

public class DisconnectedDeviceView extends LinearLayout
{
    private static final String TIMER_NAME = "disconnect";
    private boolean _abort;
    private int _timeOut;
    private String _timeOutString;
    private Timer _timer;
    private TextView _timoutView;
    
    public DisconnectedDeviceView(final Context context) {
        super(context);
        this._abort = false;
        this.initLayout();
    }
    
    public DisconnectedDeviceView(final Context context, final AttributeSet set) {
        super(context, set);
        this._abort = false;
        this.initLayout();
    }
    
    private void initLayout() {
        this.setOrientation(0);
        inflate(this.getContext(), 2131427493, (ViewGroup)this);
        this._timoutView = (TextView)this.findViewById(2131296714);
    }
    
    protected String getMessageStringForSeconds(final int n, final boolean b) {
        if (!b) {
            final Resources resources = this.getContext().getResources();
            final StringBuilder sb = new StringBuilder();
            sb.append("");
            sb.append(n);
            return resources.getString(2131690177, new Object[] { sb.toString() });
        }
        final Resources resources2 = this.getContext().getResources();
        final StringBuilder sb2 = new StringBuilder();
        sb2.append("");
        sb2.append(n);
        return resources2.getString(2131690176, new Object[] { sb2.toString() });
    }
    
    public void setGameWillAborted(final boolean abort) {
        this._abort = abort;
    }
    
    public void setTimeout(final int timeOut) {
        this._timeOut = timeOut;
        if (this._timer != null) {
            this._timer.cancel();
        }
        (this._timer = new Timer("disconnect")).schedule(new UpdateTimeoutTask(), 0L, 1000L);
    }
    
    private class UpdateTimeoutTask extends TimerTask
    {
        @Override
        public void run() {
            if (DisconnectedDeviceView.this._timeOut >= 0) {
                DisconnectedDeviceView.this._timeOutString = DisconnectedDeviceView.this.getMessageStringForSeconds(DisconnectedDeviceView.this._timeOut, DisconnectedDeviceView.this._abort);
                DisconnectedDeviceView.this.post((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        DisconnectedDeviceView.this._timoutView.setText((CharSequence)DisconnectedDeviceView.this._timeOutString);
                        DisconnectedDeviceView.this._timeOut--;
                    }
                });
                return;
            }
            DisconnectedDeviceView.this._timer.cancel();
        }
    }
}
