// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.remote.view;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.TextView;
import android.graphics.Paint;
import android.widget.SeekBar;
import de.cisha.android.ui.view.RangeSeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.LinearLayout;

public class PairingRangeSetupView extends LinearLayout implements SeekBar.OnSeekBarChangeListener, OnRangeSeekBarChangeListener<Integer>
{
    private int _avgValue;
    private SeekBar _eloRangeTimeIndicatorBar;
    private int _maxValue;
    private int _minValue;
    private Paint _paint;
    private RangeSeekBar<Integer> _rangeSeekBar;
    private TextView _textViewRangeMax;
    private TextView _textViewRangeMin;
    
    public PairingRangeSetupView(final Context context) {
        super(context);
        this.init();
    }
    
    public PairingRangeSetupView(final Context context, final AttributeSet set) {
        super(context, set);
        this.init();
    }
    
    private static String getFormattedStringFor(final Integer n) {
        final StringBuilder sb = new StringBuilder();
        String s;
        if (n >= 0) {
            s = "+";
        }
        else {
            s = "";
        }
        sb.append(s);
        sb.append(n);
        return sb.toString();
    }
    
    private void init() {
        this.setOrientation(1);
        inflate(this.getContext(), 2131427482, (ViewGroup)this);
        (this._rangeSeekBar = new RangeSeekBar<Integer>(-500, 500, this.getContext())).setOnRangeSeekBarChangeListener((RangeSeekBar.OnRangeSeekBarChangeListener<Integer>)this);
        this._rangeSeekBar.setNotifyWhileDragging(true);
        ((ViewGroup)this.findViewById(2131296652)).addView((View)this._rangeSeekBar);
        this._textViewRangeMin = (TextView)this.findViewById(2131296656);
        this._textViewRangeMax = (TextView)this.findViewById(2131296655);
        (this._eloRangeTimeIndicatorBar = (SeekBar)this.findViewById(2131296648)).setOnSeekBarChangeListener((SeekBar.OnSeekBarChangeListener)this);
        this._eloRangeTimeIndicatorBar.setMax(1000);
        this.onRangeSeekBarValuesChanged(this._rangeSeekBar, this._rangeSeekBar.getSelectedMinValue(), this._rangeSeekBar.getSelectedMaxValue());
        this.findViewById(2131296653).setBackgroundDrawable((Drawable)new EloMarkDrawable());
    }
    
    private void updateRangeTextViews(final int minValue, final int maxValue) {
        this._minValue = minValue;
        this._maxValue = maxValue;
        this._textViewRangeMin.setText((CharSequence)getFormattedStringFor(minValue));
        this._textViewRangeMax.setText((CharSequence)getFormattedStringFor(maxValue));
    }
    
    public int getMaxValue() {
        return this._maxValue;
    }
    
    public int getMinValue() {
        return this._minValue;
    }
    
    public int getSelectedMaxRating() {
        return this._rangeSeekBar.getSelectedMaxValue();
    }
    
    public int getSelectedMinRating() {
        return this._rangeSeekBar.getSelectedMinValue();
    }
    
    public void onProgressChanged(final SeekBar seekBar, int n, final boolean b) {
        if (b) {
            final int n2 = n / 2;
            final int avgValue = this._avgValue;
            final int intValue = this._rangeSeekBar.getAbsoluteMaxValue();
            n = 0;
            final int max = Math.max(avgValue + n2 - intValue, 0);
            final int n3 = this._avgValue - n2 - this._rangeSeekBar.getAbsoluteMinValue();
            if (n3 < 0) {
                n = -n3;
            }
            final int avgValue2 = this._avgValue;
            final int avgValue3 = this._avgValue;
            this._rangeSeekBar.setSelectedMinValue(avgValue2 - n2 - max);
            this._rangeSeekBar.setSelectedMaxValue(avgValue3 + n2 + n);
            this.updateRangeTextViews(this._rangeSeekBar.getSelectedMinValue(), this._rangeSeekBar.getSelectedMaxValue());
        }
    }
    
    public void onRangeSeekBarValuesChanged(final RangeSeekBar<?> rangeSeekBar, final Integer n, final Integer n2) {
        this._avgValue = (n + n2) / 2;
        this._eloRangeTimeIndicatorBar.setProgress(n2 - n);
        this.updateRangeTextViews(n, n2);
    }
    
    public void onStartTrackingTouch(final SeekBar seekBar) {
    }
    
    public void onStopTrackingTouch(final SeekBar seekBar) {
    }
    
    public void setEloRange(final int n, final int n2) {
        this._rangeSeekBar.setSelectedMinValue(n);
        this._rangeSeekBar.setSelectedMaxValue(n2);
        this.updateRangeTextViews(n, n2);
        this.onRangeSeekBarValuesChanged(this._rangeSeekBar, this._rangeSeekBar.getSelectedMinValue(), this._rangeSeekBar.getSelectedMaxValue());
    }
}
