// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.view;

import java.util.TimerTask;
import android.graphics.Typeface;
import android.view.ViewGroup;
import android.util.AttributeSet;
import android.content.Context;
import java.util.Timer;
import android.widget.TextView;
import android.widget.LinearLayout;

public class ClockView extends LinearLayout
{
    private TextView _digit1;
    private TextView _digit2;
    private TextView _digit3;
    private TextView _digit4;
    private TextView _divider;
    private String _dividerText;
    private long _firstValue;
    private Runnable _hideDivider;
    private long _secondValue;
    private Runnable _showDivider;
    private boolean _showMilliseconds;
    private Timer _timer;
    
    public ClockView(final Context context) {
        super(context);
        this._showMilliseconds = true;
        this.init();
    }
    
    public ClockView(final Context context, final AttributeSet set) {
        super(context, set);
        this._showMilliseconds = true;
        this.init();
    }
    
    private void setDividerText(final String dividerText) {
        if (this._dividerText == null || !this._dividerText.equals(dividerText)) {
            this._dividerText = dividerText;
            this._divider.setText((CharSequence)this._dividerText);
        }
    }
    
    private void setFirstValue(final long firstValue) {
        if (firstValue != this._firstValue) {
            this._firstValue = firstValue;
            final TextView digit1 = this._digit1;
            final StringBuilder sb = new StringBuilder();
            sb.append("");
            sb.append(firstValue / 10L);
            digit1.setText((CharSequence)sb.toString());
            final TextView digit2 = this._digit2;
            final StringBuilder sb2 = new StringBuilder();
            sb2.append("");
            sb2.append(firstValue % 10L);
            digit2.setText((CharSequence)sb2.toString());
        }
    }
    
    private void setSecondValue(final long secondValue) {
        if (secondValue != this._secondValue) {
            this._secondValue = secondValue;
            final TextView digit3 = this._digit3;
            final StringBuilder sb = new StringBuilder();
            sb.append("");
            sb.append(secondValue / 10L);
            digit3.setText((CharSequence)sb.toString());
            final TextView digit4 = this._digit4;
            final StringBuilder sb2 = new StringBuilder();
            sb2.append("");
            sb2.append(secondValue % 10L);
            digit4.setText((CharSequence)sb2.toString());
        }
    }
    
    protected void finalize() throws Throwable {
        super.finalize();
        this._timer.cancel();
    }
    
    public void init() {
        this._timer = new Timer();
        this._hideDivider = new Runnable() {
            @Override
            public void run() {
                ClockView.this._divider.setVisibility(4);
            }
        };
        this._showDivider = new Runnable() {
            @Override
            public void run() {
                ClockView.this._divider.setVisibility(0);
            }
        };
        this.setFlashingOn(false);
        inflate(this.getContext(), 2131427490, (ViewGroup)this);
        this._digit1 = (TextView)this.findViewById(2131296434);
        this._digit2 = (TextView)this.findViewById(2131296435);
        this._digit3 = (TextView)this.findViewById(2131296436);
        this._digit4 = (TextView)this.findViewById(2131296437);
        this._divider = (TextView)this.findViewById(2131296433);
        final Typeface fromAsset = Typeface.createFromAsset(this.getContext().getAssets(), "fonts/DS-DIGI.TTF");
        this._divider.setTypeface(fromAsset);
        this._digit1.setTypeface(fromAsset);
        this._digit2.setTypeface(fromAsset);
        this._digit3.setTypeface(fromAsset);
        this._digit4.setTypeface(fromAsset);
    }
    
    public void setFlashingOn(final boolean b) {
        if (b) {
            this._timer.cancel();
            (this._timer = new Timer()).scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    ClockView.this.post(ClockView.this._hideDivider);
                }
            }, 500L, 1000L);
            this._timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    ClockView.this.post(ClockView.this._showDivider);
                }
            }, 1000L, 1000L);
            return;
        }
        this._timer.cancel();
        this.post(this._showDivider);
    }
    
    public void setShowMilliseconds(final boolean showMilliseconds) {
        this._showMilliseconds = showMilliseconds;
    }
    
    public void setTextColor(final int textColor) {
        this._digit1.setTextColor(textColor);
        this._digit2.setTextColor(textColor);
        this._digit3.setTextColor(textColor);
        this._digit4.setTextColor(textColor);
        this._divider.setTextColor(textColor);
    }
    
    public void setTime(long n) {
        final long n2 = n / 1000L % 60L;
        final long n3 = n / 60000L % 60L;
        if (n < 0L) {
            this.setFirstValue(0L);
            this.setSecondValue(0L);
            this.setDividerText(".");
            return;
        }
        if (n < 20000L && this._showMilliseconds) {
            n /= 10L;
            this.setFirstValue(n2);
            this.setSecondValue(n % 100L);
            this.setDividerText(".");
            return;
        }
        if (n > 1200000L) {
            this.setFirstValue(n / 3600000L);
            this.setSecondValue(n3);
            this.setDividerText(":");
            return;
        }
        this.setFirstValue(n3);
        this.setSecondValue(n2);
        this.setDividerText(".");
    }
}
