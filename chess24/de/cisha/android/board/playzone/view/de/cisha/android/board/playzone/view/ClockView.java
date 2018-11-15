/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.AssetManager
 *  android.graphics.Typeface
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package de.cisha.android.board.playzone.view;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Timer;
import java.util.TimerTask;

public class ClockView
extends LinearLayout {
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
    private boolean _showMilliseconds = true;
    private Timer _timer;

    public ClockView(Context context) {
        super(context);
        this.init();
    }

    public ClockView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    private void setDividerText(String string) {
        if (this._dividerText == null || !this._dividerText.equals(string)) {
            this._dividerText = string;
            this._divider.setText((CharSequence)this._dividerText);
        }
    }

    private void setFirstValue(long l) {
        if (l != this._firstValue) {
            this._firstValue = l;
            TextView textView = this._digit1;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(l / 10L);
            textView.setText((CharSequence)stringBuilder.toString());
            textView = this._digit2;
            stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(l % 10L);
            textView.setText((CharSequence)stringBuilder.toString());
        }
    }

    private void setSecondValue(long l) {
        if (l != this._secondValue) {
            this._secondValue = l;
            TextView textView = this._digit3;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(l / 10L);
            textView.setText((CharSequence)stringBuilder.toString());
            textView = this._digit4;
            stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(l % 10L);
            textView.setText((CharSequence)stringBuilder.toString());
        }
    }

    protected void finalize() throws Throwable {
        Object.super.finalize();
        this._timer.cancel();
    }

    public void init() {
        this._timer = new Timer();
        this._hideDivider = new Runnable(){

            @Override
            public void run() {
                ClockView.this._divider.setVisibility(4);
            }
        };
        this._showDivider = new Runnable(){

            @Override
            public void run() {
                ClockView.this._divider.setVisibility(0);
            }
        };
        this.setFlashingOn(false);
        ClockView.inflate((Context)this.getContext(), (int)2131427490, (ViewGroup)this);
        this._digit1 = (TextView)this.findViewById(2131296434);
        this._digit2 = (TextView)this.findViewById(2131296435);
        this._digit3 = (TextView)this.findViewById(2131296436);
        this._digit4 = (TextView)this.findViewById(2131296437);
        this._divider = (TextView)this.findViewById(2131296433);
        Typeface typeface = Typeface.createFromAsset((AssetManager)this.getContext().getAssets(), (String)"fonts/DS-DIGI.TTF");
        this._divider.setTypeface(typeface);
        this._digit1.setTypeface(typeface);
        this._digit2.setTypeface(typeface);
        this._digit3.setTypeface(typeface);
        this._digit4.setTypeface(typeface);
    }

    public void setFlashingOn(boolean bl) {
        if (bl) {
            this._timer.cancel();
            this._timer = new Timer();
            this._timer.scheduleAtFixedRate(new TimerTask(){

                @Override
                public void run() {
                    ClockView.this.post(ClockView.this._hideDivider);
                }
            }, 500L, 1000L);
            this._timer.scheduleAtFixedRate(new TimerTask(){

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

    public void setShowMilliseconds(boolean bl) {
        this._showMilliseconds = bl;
    }

    public void setTextColor(int n) {
        this._digit1.setTextColor(n);
        this._digit2.setTextColor(n);
        this._digit3.setTextColor(n);
        this._digit4.setTextColor(n);
        this._divider.setTextColor(n);
    }

    public void setTime(long l) {
        long l2 = l / 1000L % 60L;
        long l3 = l / 60000L % 60L;
        if (l < 0L) {
            this.setFirstValue(0L);
            this.setSecondValue(0L);
            this.setDividerText(".");
            return;
        }
        if (l < 20000L && this._showMilliseconds) {
            this.setFirstValue(l2);
            this.setSecondValue((l /= 10L) % 100L);
            this.setDividerText(".");
            return;
        }
        if (l > 1200000L) {
            this.setFirstValue(l / 3600000L);
            this.setSecondValue(l3);
            this.setDividerText(":");
            return;
        }
        this.setFirstValue(l3);
        this.setSecondValue(l2);
        this.setDividerText(".");
    }

}
